package dao.impl;

import dao.routeDao;
import domain.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class routeDaoImpl implements routeDao {
    //申明成员变量jdbcTemplate
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    //计算物品相似度的第一步
    @Override
    public Map<String, List<String>> getItemsMap() {
        String sql="select * from collect_route";
        List<collect_route> items=template.query(sql,new BeanPropertyRowMapper<collect_route>(collect_route.class));
        Map<String, List<String>> map=new HashMap<String,List<String>>();

        //先获取用户的集合
        Set<String> userSet=new HashSet<String>();
        for(collect_route item:items){
            String user_id=item.getUser_id();
            userSet.add(user_id);
        }
        //制作用户-路线倒排索引表
        Iterator<String> iterator=userSet.iterator();
        while (iterator.hasNext()){
            String user_id= iterator.next();
            List<String> routeList=new ArrayList<String>();
            for (collect_route item:items){
                String id=item.getUser_id();
                if(user_id.equals(id)){
                    routeList.add(item.getRoute_id());
                }
            }
            map.put(user_id,routeList);
        }
        return map;
    }

    @Override
    public Map<String, Double> selectRoutesByLikeCount() {
        String sql="select * from route_info order by like_number desc limit 5 offset 0";
        List<route> routes = template.query(sql, new BeanPropertyRowMapper<route>(route.class));
        Map<String, Double> map=new HashMap<>();
        for(route route:routes){
            map.put(route.getRoute_id(), (double) route.getLike_number());
        }
        return map;
    }

    //对数组倒排序,返回
    @Override
    public Map<Integer,Double> arraySort(double[] array) {
        Map<Integer,Double> map=new HashMap<>();
        for(int i=0;i<array.length;i++){
            map.put(i,array[i]);
        }
        //降序排序
        List<Map.Entry<Integer,Double>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer,Double>>() {
            @Override
            public int compare(Map.Entry<Integer,Double> o1, Map.Entry<Integer,Double> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<Integer,Double> returnMap = new HashMap<Integer,Double>();
        for (Map.Entry<Integer,Double> entry : list) {
            returnMap.put(entry.getKey(), entry.getValue());
        }

        return returnMap;
    }

    //根据路线id查询路线
    @Override
    public route selectRouteById(String key) {
        String sql="select * from route_info where route_id=?";
        route route = template.queryForObject(sql, new BeanPropertyRowMapper<route>(route.class), key);
        return route;
    }

    //根据页数查询范围内热门的5条路线
    @Override
    public List<route> selectRoutesByPopular(int currentPage) {
        String sql="select * from route_info order by collect_number desc limit 5 offset ?";
        List<route> items=template.query(sql,new BeanPropertyRowMapper<route>(route.class),(currentPage-1)*5);
        return items;
    }

    @Override
    public boolean selectlike_route(String email, String route_id) {
        String sql="select * from like_route where user_id=? and route_id=?";
        try {
            template.queryForObject(sql, new BeanPropertyRowMapper<like_route>(like_route.class), email, route_id);
            return true;
        } catch (DataAccessException e) {
            return false;
        }

    }

    @Override
    public boolean selectdislike_route(String email, String route_id) {
        String sql="select * from dislike_route where user_id=? and route_id=?";
        try {
            template.queryForObject(sql, new BeanPropertyRowMapper<dislike_route>(dislike_route.class), email, route_id);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean selectcollect_route(String email, String route_id) {
        String sql="select * from collect_route where user_id=? and route_id=?";
        try {
            template.queryForObject(sql, new BeanPropertyRowMapper<collect_route>(collect_route.class), email, route_id);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public List<String> searchRouteIdByName(String inputText) {
        String sql="select route_id from city_info where city_name = ?";
        List<String> routeIdList = null;
        try {
            routeIdList = template.queryForList(sql,String.class,inputText);
            return routeIdList;
        } catch (DataAccessException e) {
            System.out.println("查询结果为空");
            return null;
        }
    }

    @Override
    public List<String> searchCityIdByName(String inputText) {
        String sql="select city_id from scenery_info where scenery_name = ?";
        List<String> cityIdlist= null;
        try {
            cityIdlist = template.queryForList(sql,String.class,inputText);
            return cityIdlist;
        } catch (DataAccessException e) {
            System.out.println("查询结果为空");
            return null;
        }
    }

    @Override
    public String searchRouteIdById(String cityId) {
        String sql="select route_id from city_info where city_id=?";
        return template.queryForObject(sql,String.class,cityId);
    }

    @Override
    public List<String> selectRouteIdList() {
        String sql="select route_id from route_info";
        List<String> routeIdList=template.queryForList(sql,String.class);
        return routeIdList;
    }

    @Override
    public List<String> selectCityNameListByRouteId(String routeId) {
        String sql="select city_name from city_info where route_id=?";
        List<String> cityNameList=template.queryForList(sql,String.class,routeId);
        return cityNameList;
    }

    @Override
    public List<route> selectRoutesByLastest(int currentPage) {
        String sql="select * from route_info order by route_createTime desc limit 5 offset ?";
        List<route> routeList=template.query(sql,new BeanPropertyRowMapper<route>(route.class),(currentPage-1)*5);
        return routeList;
    }

    @Override
    public void updateRouteLikeNumber(String routeId, int i) {
        //先查询like_number的值
        String selectsql="select * from route_info where route_id=?";
        int like_Number = template.queryForObject(selectsql, new BeanPropertyRowMapper<route>(route.class), routeId).getLike_number();

        String sql="update route_info set like_number=? where route_id=?";
        template.update(sql,like_Number+i,routeId);
    }

    @Override
    public void addLikeRoute(String email, String routeId) {
        String sql="insert into like_route values(null,?,?)";
        template.update(sql,email,routeId);
    }

    @Override
    public void delLikeRoute(String email, String routeId) {
        String sql="delete from like_route where user_id=? and route_id=?";
        template.update(sql,email,routeId);
    }

    @Override
    public void updateRouteDislikeNumber(String routeId, int i) {
        //先查询like_number的值
        String selectsql="select * from route_info where route_id=?";
        int dislike_Number = template.queryForObject(selectsql, new BeanPropertyRowMapper<route>(route.class), routeId).getDislike_number();

        String sql="update route_info set dislike_number=? where route_id=?";
        template.update(sql,dislike_Number+i,routeId);
    }

    @Override
    public void addDislikeRoute(String email, String routeId) {
        String sql="insert into dislike_route values(null,?,?)";
        template.update(sql,email,routeId);
    }

    @Override
    public void delDislikeRoute(String email, String routeId) {
        String sql="delete from dislike_route where user_id=? and route_id=?";
        template.update(sql,email,routeId);
    }

    @Override
    public void updateRouteCollectNumber(String routeId, int i) {
        //先查询like_number的值
        String selectsql="select * from route_info where route_id=?";
        int collect_Number = template.queryForObject(selectsql, new BeanPropertyRowMapper<route>(route.class), routeId).getCollect_number();

        String sql="update route_info set collect_number=? where route_id=?";
        template.update(sql,collect_Number+i,routeId);
    }

    @Override
    public void addCollectRoute(String email, String routeId) {
        //获取当前日期
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(date);
        String sql="insert into collect_route values(null,?,?,?)";
        template.update(sql,email,routeId,format);
    }

    @Override
    public void delCollectRoute(String email, String routeId) {
        String sql="delete from collect_route where user_id=? and route_id=?";
        template.update(sql,email,routeId);
    }

    @Override
    public void updateRouteInfo(route route) {
        String route_id=route.getRoute_id();
        //先查询数据库中是否存在route_id
        String selectSql="select * from route_info where route_id=?";
        boolean isExistRouteId;
        try {
            template.queryForObject(selectSql, new BeanPropertyRowMapper<route>(route.class), route_id);
            isExistRouteId=true;
        } catch (DataAccessException e) {
            isExistRouteId=false;
        }
        String sql=null;
        if(isExistRouteId){
            //有，则更新
            sql="update route_info set route_name=? , route_avatar=? , route_label=? , route_detail=? , start_time=? , spend_time=? , person_number=? , route_cost=? where route_id=?";
            template.update(sql,route.getRoute_name(),route.getRoute_avatar(),route.getRoute_label(),route.getRoute_detail(),route.getStart_time(),route.getSpend_time(),route.getPerson_number(),route.getRoute_cost(),route_id);
        }else{
            //没有，则直接插入
            sql="insert into route_info values(?,?,?,?,?,?,?,?,?,?,?,0,0,0)";
            template.update(sql,route_id,route.getRoute_name(),route.getUser_id(),route.getRoute_createTime(),route.getRoute_avatar(),route.getRoute_label(),route.getRoute_detail(),route.getStart_time(),route.getSpend_time(),route.getPerson_number(),route.getRoute_cost());
        }
    }

    @Override
    public void updateCityInfo(city city) {
        String city_id=city.getCity_id();
        //创建
        String updatesql="insert into city_info values(?,?,?,?,?)";
        template.update(updatesql,city_id,city.getCity_name(),city.getRoute_id(),city.getStart_time(),city.getStart_count());

    }

    @Override
    public void updateSceneryInfo(scenery scenery) {
        String scenery_id=scenery.getScenery_id();
        //创建
        String updatesql="insert into scenery_info values(?,?,?,?,?,?,?,?,?,?)";
        template.update(updatesql,scenery_id,scenery.getScenery_name(),scenery.getCity_id(),scenery.getStart_count(),scenery.getScenery_score(),scenery.getScenery_avatar(),scenery.getScenery_detail(),scenery.getScenery_location(),scenery.getScenery_traffic(),scenery.getScenery_telephone());

    }

    @Override
    public List<String> selectCityIdList(String route_id) {
        String sql="select city_id from city_info where route_id=?";
        return template.queryForList(sql,String.class,route_id);
    }

    @Override
    public void deleteCity(String route_id) {
        String sql="delete from city_info where route_id=?";
        template.update(sql,route_id);
    }

    @Override
    public void deleteScenery(String cityId) {
        String sql="delete from scenery_info where city_id=?";
        template.update(sql,cityId);
    }

    @Override
    public List<route> selectRoutesByUserId(String email) {
        String sql="select * from route_info where user_id=?";
        List<route> routeList=null;
        try {
            routeList=template.query(sql,new BeanPropertyRowMapper<route>(route.class),email);
            return routeList;
        } catch (DataAccessException e) {
            System.out.println("查询结果为空");
            return null;
        }
    }

    @Override
    public List<city> selectCityListByRouteId(String route_id) {
        String sql="select * from city_info where route_id=?";
        return template.query(sql,new BeanPropertyRowMapper<city>(city.class),route_id);
    }

    @Override
    public void delLikeRoutes(String route_id) {
        String sql="delete from like_route where route_id=?";
        template.update(sql,route_id);
    }

    @Override
    public void delDislikeRoutes(String route_id) {
        String sql="delete from dislike_route where route_id=?";
        template.update(sql,route_id);
    }

    @Override
    public void delCollectRoutes(String route_id) {
        String sql="delete from collect_route where route_id=?";
        template.update(sql,route_id);
    }

    @Override
    public void delSceneryInfoByCityId(String city_id) {
        String sql="delete from scenery_info where city_id=?";
        template.update(sql,city_id);
    }

    @Override
    public void delCityInfoByRouteId(String route_id) {
        String sql="delete from city_info where route_id=?";
        template.update(sql,route_id);
    }

    @Override
    public void delRouteInfoByRouteId(String route_id) {
        String sql="delete from route_info where route_id=?";
        template.update(sql,route_id);
    }

    @Override
    public List<collect_route> selectCollectRoutesByUserId(String email) {
        String sql="select * from collect_route where user_id=?";
        try {
            List<collect_route> routeList=template.query(sql,new BeanPropertyRowMapper<collect_route>(collect_route.class),email);
            return routeList;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void subRouteCollectNumber(String route_id) {
        //先查询数量
        String selectsql="select * from route_info where route_id=?";
        int collect_Number = template.queryForObject(selectsql, new BeanPropertyRowMapper<route>(route.class), route_id).getCollect_number();

        String sql="update route_info set collect_number=? where route_id=?";
        template.update(sql,collect_Number-1,route_id);
    }

    @Override
    public List<scenery> getSceneryInfoByCityId(String city_id) {
        String sql="select * from scenery_info where city_id=?";
        return template.query(sql,new BeanPropertyRowMapper<scenery>(scenery.class),city_id);
    }

    @Override
    public scenery getSceneryInfoByScenertId(String scenery_id) {
        String sql="select * from scenery_info where scenery_id=?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<scenery>(scenery.class),scenery_id);
    }

    @Override
    public List<scenery_comment> getSceneryCommentListBySceneryId(String scenery_id) {
        String sql="select *from scenery_comment where scenery_id=?";
        return template.query(sql,new BeanPropertyRowMapper<scenery_comment>(scenery_comment.class),scenery_id);
    }

    @Override
    public void updateComment(scenery_comment comment) {
        String sql="insert into scenery_comment values(null,?,?,?,?)";
        template.update(sql,comment.getUser_id(),comment.getScenery_id(),comment.getComment_date(),comment.getComment_detail());
    }

    @Override
    public void delSceneryComment(String scenery_date) {
        String sql="delete from scenery_comment where comment_date=? ";
        template.update(sql,scenery_date);
    }
}

