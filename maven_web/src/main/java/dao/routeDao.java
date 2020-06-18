package dao;

import domain.*;

import java.util.List;
import java.util.Map;

public interface routeDao {
    Map<String, List<String>> getItemsMap();

    //对数组排序
    Map<Integer,Double> arraySort(double[] array);

    route selectRouteById(String key);

    List<route> selectRoutesByPopular(int currentPage);

    boolean selectcollect_route(String email, String route_id);

    boolean selectdislike_route(String email, String route_id);

    boolean selectlike_route(String email, String route_id);

    List<String> searchRouteIdByName(String inputText);

    Map<String, Double> selectRoutesByLikeCount();

    List<String> searchCityIdByName(String inputText);

    String searchRouteIdById(String cityId);

    List<String> selectRouteIdList();

    List<String> selectCityNameListByRouteId(String routeId);

    List<route> selectRoutesByLastest(int currentPage);

    void updateRouteLikeNumber(String routeId, int i);

    void addLikeRoute(String email, String routeId);

    void delLikeRoute(String email, String routeId);

    void updateRouteDislikeNumber(String routeId, int i);

    void addDislikeRoute(String email, String routeId);

    void delDislikeRoute(String email, String routeId);

    void updateRouteCollectNumber(String routeId, int i);

    void addCollectRoute(String email, String routeId);

    void delCollectRoute(String email, String routeId);

    void updateRouteInfo(route route);

    void updateCityInfo(city city);

    void updateSceneryInfo(scenery scenery);

    List<String> selectCityIdList(String route_id);

    void deleteScenery(String cityId);

    void deleteCity(String route_id);

    List<route> selectRoutesByUserId(String email);

    List<city> selectCityListByRouteId(String route_id);

    void delLikeRoutes(String route_id);

    void delDislikeRoutes(String route_id);

    void delCollectRoutes(String route_id);

    void delSceneryInfoByCityId(String city_id);

    void delCityInfoByRouteId(String route_id);

    void delRouteInfoByRouteId(String route_id);

    List<collect_route> selectCollectRoutesByUserId(String email);

    void subRouteCollectNumber(String route_id);

    List<scenery> getSceneryInfoByCityId(String city_id);

    scenery getSceneryInfoByScenertId(String scenery_id);

    List<scenery_comment> getSceneryCommentListBySceneryId(String scenery_id);

    void updateComment(scenery_comment comment);

    void delSceneryComment(String scenery_date);
}
