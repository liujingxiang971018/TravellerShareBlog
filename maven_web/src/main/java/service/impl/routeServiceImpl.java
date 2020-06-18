package service.impl;

import dao.impl.routeDaoImpl;
import dao.impl.userDaoImpl;
import dao.routeDao;
import dao.userDao;
import domain.*;
import service.routeService;
import util.CityMapUtils;
import util.ObjectSortUtils;

import java.util.*;

public class routeServiceImpl implements routeService {
    private routeDao dao=new routeDaoImpl();

    //获取物品的余弦相似度矩阵
    @Override
    public List<route> getItemSimilarityMatrix(String email) {
        //需要获取路线收集表中的用户倒排索引表
        Map<String, List<String>> ItemsMap = dao.getItemsMap();

        //获取键集合
        Set<String> users = ItemsMap.keySet();
        //定义物品的集合
        Set<String> items=new HashSet<>();
        for(String user:users){
            List<String> list = ItemsMap.get(user);
            items.addAll(list);
        }
        //set转list
        List<String> itemsList=new ArrayList<String>(items);

        //获取物品集合的长度
        int item_number=itemsList.size();
        //获取相似度矩阵
        double[][] N_matrix=new double[item_number][item_number];
        for(int i=0;i<item_number;i++){
            for(int j=0;j<item_number;j++){
                String route_id_row = itemsList.get(i);
                String route_id_col=itemsList.get(j);
                if(route_id_row.equals(route_id_col)){
                    continue;
                }

                int count_all=0;
                int count_i=0;
                int count_j=0;
                for(String user:users){
                    List<String> list = ItemsMap.get(user);
                    //同时包含两个
                    if(list.contains(route_id_row) && list.contains(route_id_col)){
                        count_all+=1;
                    }
                    //包含i
                    if(list.contains(route_id_row)){
                        count_i+=1;
                    }
                    //包含j
                    if(list.contains(route_id_col)){
                        count_j+=1;
                    }
                }
                double Wij=(count_all*1.0)/(Math.sqrt(count_i*count_j));

                N_matrix[i][j]=Wij;
            }
        }

        //计算推荐列表
        //最终推荐列表
        List<route> recommendItems=new ArrayList<>();
        //待推荐列表
        Map<String,Double> filterRecommendItems=new HashMap<>();
        //用户的喜欢列表
        List<String> itemLists = ItemsMap.get(email);
        if(itemLists==null){
            //当用户还没有喜好时，推荐点赞数量最高的4个
            filterRecommendItems=dao.selectRoutesByLikeCount();
        }else{
            for(String item:itemLists){
                //找到item对应的三个相似度最高的三个路线
                for(int i=0;i<item_number;i++){
                    String route_id=itemsList.get(i);
                    if(route_id.equals(item)){
                        Map<Integer,Double> sort = dao.arraySort(N_matrix[i]);
                        Set<Integer> keySet = sort.keySet();
                        int count=0;
                        for(Integer key:keySet){
                            count+=1;

                            String route_id_j=itemsList.get(key);
                            //如果待推荐路线已经在用户喜欢的列表汇总，则跳过
                            if(itemLists.contains(route_id_j)){
                                continue;
                            }else{
                                Set<String> filterKeySet = filterRecommendItems.keySet();
                                if(filterKeySet.contains(route_id_j)){
                                    double value=filterRecommendItems.get(route_id_j);
                                    value+=N_matrix[i][key];
                                    filterRecommendItems.put(route_id_j,value);
                                }else{
                                    filterRecommendItems.put(route_id_j,N_matrix[i][key]);
                                }
                            }
                            //只取前三个
                            if(count==3){
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
        //对filterRecommendItems中的value进行倒排
        List<Map.Entry<String,Double>> list = new ArrayList<>(filterRecommendItems.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String,Double>>() {
            @Override
            public int compare(Map.Entry<String,Double> o1, Map.Entry<String,Double> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });
        //只返回4个
        int recommendRouteCount=0;
        for (Map.Entry<String,Double> entry : list) {
            route route=dao.selectRouteById(entry.getKey());
            recommendItems.add(route);
            recommendRouteCount+=1;
            if(recommendRouteCount==4){
                break;
            }
        }
        //如果推荐数小于4个，则用热门路线代替
        if(recommendRouteCount<4){
            System.out.println("小于4个");
        }
        return recommendItems;
    }

    //获取大众喜欢的5个路线
    @Override
    public List<route> getPopularItems(int currentPage) {
        //先查询数据库，返回值
        return dao.selectRoutesByPopular(currentPage);
    }
    //获取当前用户是否点赞的状态
    @Override
    public boolean getLikeStatus(String email, String route_id) {
        return dao.selectlike_route(email,route_id);
    }
    //获取当前用户是否点踩的状态
    @Override
    public boolean getDislikeStatus(String email, String route_id) {
        return dao.selectdislike_route(email,route_id);
    }
    //获取当前用户是否收藏的状态
    @Override
    public boolean getCollectStatus(String email, String route_id) {
        return dao.selectcollect_route(email,route_id);
    }
    //查询city_info数据表中的route_id,根据route_id查询route_info数据表
    @Override
    public List<route> searchRouteItemsByCity(String inputText, int currentPage, String sortType) {
        List<String> routeIdList=dao.searchRouteIdByName(inputText);
        System.out.println("routeIdList:"+routeIdList);
        List<route> routeList=new ArrayList<>();
        for(String routeId:routeIdList){
            routeList.add(dao.selectRouteById(routeId));
        }
        List<route> filterRouteList= ObjectSortUtils.sort(routeList,currentPage,sortType);
        return filterRouteList;
    }
    //查询scenery_info数据表的city_id,根据city_id查询city_info数据表中的route_id,根据route_id查询route_info数据表
    @Override
    public List<route> searchRouteItemsByScenery(String inputText, int currentPage, String sortType) {
        List<String> cityIdList=dao.searchCityIdByName(inputText);
        List<String> routeIdList=new ArrayList<>();
        for(String cityId:cityIdList){
            routeIdList.add(dao.searchRouteIdById(cityId));
        }
        List<route> routeList=new ArrayList<>();
        for(String routeId:routeIdList){
            routeList.add(dao.selectRouteById(routeId));
        }
        List<route> filterRouteList= ObjectSortUtils.sort(routeList,currentPage,sortType);
        return filterRouteList;
    }
    //综合以上两种方法
    @Override
    public List<route> searchRouteItemsByAll(String inputText, int currentPage, String sortType) {
        Map<String, String[]> cityMap = CityMapUtils.getCityMap();
        boolean isCity=false;
        Set<String> keySet = cityMap.keySet();
        for(String key:keySet){
            List<String> citys = Arrays.asList(cityMap.get(key));
            if(citys.contains(inputText)){
                isCity=true;
                break;
            }
        }

        List<route> routeList=new ArrayList<>();
        if(isCity){
            //输入的是城市
            List<String> routeIdList=dao.searchRouteIdByName(inputText);
            for(String routeId:routeIdList){
                routeList.add(dao.selectRouteById(routeId));
            }
        }else{
            //输入的是地点
            List<String> cityIdList=dao.searchCityIdByName(inputText);
            List<String> routeIdList=new ArrayList<>();
            for(String cityId:cityIdList){
                routeIdList.add(dao.searchRouteIdById(cityId));
            }
            for(String routeId:routeIdList){
                routeList.add(dao.selectRouteById(routeId));
            }
        }
        List<route> filterRouteList= ObjectSortUtils.sort(routeList,currentPage,sortType);
        return filterRouteList;
    }

    @Override
    public List<route> searchRoutesByClassify(String city, String type, int currentPage, String sortType) {
        List<route> routeList=new ArrayList<>();
        //查询所有路线
        List<String> routeIdList=dao.selectRouteIdList();
        //获取省份-城市列表
        Map<String, String[]> cityMap = CityMapUtils.getCityMap();
        Set<String> provinces = cityMap.keySet();
        //判断用户城市是否属于中国
        List<String> nationCityList=new ArrayList<>();
        boolean isLocal=false;
        switch (type){
            case "Local":
                //获取每条路线所包含的城市名称集合，集合中的元素只能包含用户所在城市内。
                for(String routeId:routeIdList){
                    List<String> cityNameList=dao.selectCityNameListByRouteId(routeId);
                    isLocal= cityNameList.size() == 1 && cityNameList.contains(city);
                    if(isLocal){
                        //只包含当地城市,根据route_id查询该路线
                        route route = dao.selectRouteById(routeId);
                        routeList.add(route);
                    }
                }
                break;
            case "Province":
                for(String province:provinces){
                    List<String> cityList = Arrays.asList(cityMap.get(province));
                    if(cityList.contains(city)){
                        //说明用户在该省
                        //查询所有路线，获取每条路线所包含的城市名称集合，集合中的元素必须都在用户所在省份内。
                        for(String routeId:routeIdList){
                            List<String> cityNameList=dao.selectCityNameListByRouteId(routeId);
                            isLocal= cityList.containsAll(cityNameList) && cityNameList.size()!=0;
                            if(isLocal){
                                //包含当地城市的省份,根据route_id查询该路线
                                route route = dao.selectRouteById(routeId);
                                routeList.add(route);
                            }
                        }
                        break;
                    }
                }
                break;
            case "Nation":
                for(String province:provinces) {
                    List<String> cityList = Arrays.asList(cityMap.get(province));
                    nationCityList.addAll(cityList);
                    if(cityList.contains(city)) {
                        //说明用户在该国
                        isLocal=true;
                    }
                }
                if(isLocal){
                    //查询所有路线，获取每条路线所包含的城市名称集合，集合中的元素必须都在用户所在省份内。
                    for(String routeId:routeIdList){
                        List<String> cityNameList=dao.selectCityNameListByRouteId(routeId);
                        if(nationCityList.containsAll(cityNameList) && cityNameList.size()!=0){
                            route route = dao.selectRouteById(routeId);
                            routeList.add(route);
                        }
                    }
                }
                break;
            case "World":
                for(String province:provinces) {
                    List<String> cityList = Arrays.asList(cityMap.get(province));
                    nationCityList.addAll(cityList);
                    if(cityList.contains(city)) {
                        //说明用户在该国
                        isLocal=true;
                    }
                }
                if(isLocal){
                    //查询所有路线，获取每条路线所包含的城市名称集合，集合中的元素必须都在用户所在省份内。
                    for(String routeId:routeIdList){
                        List<String> cityNameList=dao.selectCityNameListByRouteId(routeId);
                        if(!nationCityList.containsAll(cityNameList) && cityNameList.size()!=0){
                            route route = dao.selectRouteById(routeId);
                            routeList.add(route);
                        }
                    }
                }
                break;
        }
        List<route> filterRouteList= ObjectSortUtils.sort(routeList,currentPage,sortType);
        return filterRouteList;

        /*return routeList;*/
    }

    @Override
    public List<route> getLastestItems(int currentPage) {
        return dao.selectRoutesByLastest(currentPage);
    }

    @Override
    public void addRouteLikeNumber(String routeId) {
        dao.updateRouteLikeNumber(routeId,1);
    }

    @Override
    public void delRouteLikeNumber(String routeId) {
        dao.updateRouteLikeNumber(routeId,-1);
    }

    @Override
    public void addLikeRoute(String email, String routeId) {
        dao.addLikeRoute(email,routeId);
    }

    @Override
    public void delLikeRoute(String email, String routeId) {
        dao.delLikeRoute(email,routeId);
    }

    @Override
    public void addRouteDislikeNumber(String routeId) {
        dao.updateRouteDislikeNumber(routeId,1);
    }

    @Override
    public void delRouteDislikeNumber(String routeId) {
        dao.updateRouteDislikeNumber(routeId,-1);
    }

    @Override
    public void addDislikeRoute(String email, String routeId) {
        dao.addDislikeRoute(email,routeId);
    }

    @Override
    public void delDislikeRoute(String email, String routeId) {
        dao.delDislikeRoute(email,routeId);
    }

    @Override
    public void addRouteCollectNumber(String routeId) {
        dao.updateRouteCollectNumber(routeId,1);
    }

    @Override
    public void delRouteCollectNumber(String routeId) {
        dao.updateRouteCollectNumber(routeId,-1);
    }

    @Override
    public void addCollectRoute(String email, String routeId) {
        dao.addCollectRoute(email,routeId);
    }

    @Override
    public void delCollectRoute(String email, String routeId) {
        dao.delCollectRoute(email,routeId);
    }

    @Override
    public void updateRoute(route route) {
        dao.updateRouteInfo(route);
    }

    @Override
    public void deleteCityAndScenery(route route) {
        List<String> cityIdList=dao.selectCityIdList(route.getRoute_id());
        for(String cityId:cityIdList){
            //删除scenery_info中city_id=cityId的记录
            dao.deleteScenery(cityId);
        }
        //再删除city_info中route_id=routeId的记录
        dao.deleteCity(route.getRoute_id());
    }

    @Override
    public void updateCity(city city) {

        dao.updateCityInfo(city);
    }

    @Override
    public void updateScenery(scenery scenery) {
        dao.updateSceneryInfo(scenery);
    }

    @Override
    public List<route> selectMyRoutes(String email,String sortType) {
        int currentPage=1;
        List<route> routeList=dao.selectRoutesByUserId(email);
        List<route> filterRouteList= ObjectSortUtils.sort(routeList,currentPage,sortType);
        return filterRouteList;
    }

    @Override
    public route getRouteInfoByRouteId(String route_id) {
        return dao.selectRouteById(route_id);
    }

    @Override
    public List<city> getCityInfoByRouteId(String route_id) {
        return dao.selectCityListByRouteId(route_id);
    }

    @Override
    public void delLikeRoutes(String route_id) {
        dao.delLikeRoutes(route_id);
    }

    @Override
    public void delDislikeRoutes(String route_id) {
        dao.delDislikeRoutes(route_id);
    }

    @Override
    public void delCollectRoutes(String route_id) {
        dao.delCollectRoutes(route_id);
    }

    @Override
    public void delSceneryInfoByCityId(String city_id) {
        dao.delSceneryInfoByCityId(city_id);
    }

    @Override
    public void delCityInfoByRouteId(String route_id) {
        dao.delCityInfoByRouteId(route_id);
    }

    @Override
    public void delRouteInfoByRouteId(String route_id) {
        dao.delRouteInfoByRouteId(route_id);
    }

    @Override
    public List<route> selectMyFavorites(String email, String sortType) {
        int currentPage=1;
        List<collect_route> collectRouteList=dao.selectCollectRoutesByUserId(email);
        List<route> routeList=new ArrayList<>();
        for(int i=0;i<collectRouteList.size();i++){
            String route_id=collectRouteList.get(i).getRoute_id();
            route route = dao.selectRouteById(route_id);
            routeList.add(route);
        }

        List<route> filterRouteList= ObjectSortUtils.sort(routeList,currentPage,sortType);
        return filterRouteList;
    }

    @Override
    public void subRouteCollectNumber(String route_id) {
        dao.subRouteCollectNumber(route_id);
    }

    @Override
    public List<scenery> getSceneryInfoByCityId(String city_id) {
        return dao.getSceneryInfoByCityId(city_id);
    }

    @Override
    public scenery getSceneryInfoBySceneryId(String scenery_id) {
        return dao.getSceneryInfoByScenertId(scenery_id);
    }

    @Override
    public List<scenery_comment> getSceneryCommentListBySceneryId(String scenery_id) {
        return dao.getSceneryCommentListBySceneryId(scenery_id);
    }

    @Override
    public void updateComment(scenery_comment comment) {
        dao.updateComment(comment);
    }

    @Override
    public void delSceneryComment(String scenery_date) {
        dao.delSceneryComment(scenery_date);
    }
}
