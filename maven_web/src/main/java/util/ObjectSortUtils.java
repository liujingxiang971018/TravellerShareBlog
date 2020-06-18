package util;

import domain.city;
import domain.route;
import domain.scenery;
import domain.scenery_comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
* 将对象按照其某属性进行排序
* */
public class ObjectSortUtils {
    public static List<route> sort(List<route> routeList, int currentPage, String sortType) {
        List<route> filterRouteList=new ArrayList<>();
        //根据sortType进行排序，然后取currentPage*5个
        switch (sortType){
            case "createTime":
                //按照创建时间进行排序
                Collections.sort(routeList, new Comparator<route>() {
                    @Override
                    public int compare(route route, route t1) {
                        int compare=route.getRoute_createTime().compareTo(t1.getRoute_createTime());
                        return -compare;
                    }
                });
                break;
            case "collectCount":
                //按照收藏数量进行排序
                Collections.sort(routeList, new Comparator<route>() {
                    @Override
                    public int compare(route route, route t1) {
                        int compare=route.getCollect_number()-(t1.getCollect_number());
                        return -compare;
                    }
                });
                break;
            case "spendTime":
                //按照花费时间进行排序
                Collections.sort(routeList, new Comparator<route>() {
                    @Override
                    public int compare(route route, route t1) {
                        int compare=route.getSpend_time()-(t1.getSpend_time());
                        return -compare;
                    }
                });
                break;
            case "routeCost":
                //按照路线成本进行排序
                Collections.sort(routeList, new Comparator<route>() {
                    @Override
                    public int compare(route route, route t1) {
                        int compare= (int) Math.floor(route.getRoute_cost()-(t1.getRoute_cost()));
                        return -compare;
                    }
                });
                break;
        }
       /* //取对应页码的5个
        int i=(currentPage-1)*5;
        if(i+5>routeList.size()){
            //如果取最后一页
            for(int j=i;j<routeList.size();j++){
                filterRouteList.add(routeList.get(j));
            }
        }else{
            for(int j=i;j<i+5;j++){
                filterRouteList.add(routeList.get(j));
            }
        }
        return filterRouteList;*/
       return routeList;
    }

    //先根据start_time进行排序,再根据start_count进行排序
    public static List<city> sortCity(List<city> cityList){
        Collections.sort(cityList, new Comparator<city>() {
            @Override
            public int compare(city city, city t1) {
                int compare=city.getStart_time()-t1.getStart_time();
                if(compare!=0){
                    return compare;
                }else{
                    int newCompare=city.getStart_count()-t1.getStart_count();
                    return newCompare;
                }
            }
        });
        return cityList;
    }

    public static List<scenery> sortScenery(List<scenery> sceneryList) {
        Collections.sort(sceneryList, new Comparator<scenery>() {
            @Override
            public int compare(scenery scenery, scenery t1) {
                return scenery.getStart_count()-t1.getStart_count();
            }
        });
        return sceneryList;
    }

    public static List<scenery_comment> sortComment(List<scenery_comment> sceneryCommentList) {
        Collections.sort(sceneryCommentList, new Comparator<scenery_comment>() {
            @Override
            public int compare(scenery_comment scenery_comment, scenery_comment t1) {
                return -(scenery_comment.getComment_id()-t1.getComment_id());
            }
        });
        return sceneryCommentList;
    }
}
