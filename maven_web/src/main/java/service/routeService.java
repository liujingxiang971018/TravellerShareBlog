package service;

import domain.*;

import java.util.List;

public interface routeService {
    List<route> getItemSimilarityMatrix(String email);

    List<route> getPopularItems(int currentPage);

    boolean getLikeStatus(String email, String route_id);

    boolean getCollectStatus(String email, String route_id);

    boolean getDislikeStatus(String email, String route_id);

    List<route> searchRouteItemsByCity(String inputText, int currentPage, String sortType);

    List<route> searchRouteItemsByScenery(String inputText, int currentPage, String sortType);

    List<route> searchRouteItemsByAll(String inputText, int currentPage, String sortType);

    List<route> searchRoutesByClassify(String city, String type, int currentPage, String sortType);

    List<route> getLastestItems(int currentPage);


    void addLikeRoute(String email, String routeId);

    void delLikeRoute(String email, String routeId);

    void addRouteLikeNumber(String routeId);

    void delRouteLikeNumber(String routeId);

    void addRouteDislikeNumber(String routeId);

    void delRouteDislikeNumber(String routeId);

    void addDislikeRoute(String email, String routeId);

    void delDislikeRoute(String email, String routeId);


    void addRouteCollectNumber(String routeId);

    void delRouteCollectNumber(String routeId);

    void addCollectRoute(String email, String routeId);

    void delCollectRoute(String email, String routeId);

    void updateRoute(route route);

    void updateCity(city city);

    void updateScenery(scenery scenery);

    void deleteCityAndScenery(route route);

    List<route> selectMyRoutes(String email,String sortType);

    route getRouteInfoByRouteId(String route_id);

    List<city> getCityInfoByRouteId(String route_id);

    void delLikeRoutes(String route_id);

    void delDislikeRoutes(String route_id);

    void delCollectRoutes(String route_id);

    void delSceneryInfoByCityId(String city_id);

    void delCityInfoByRouteId(String route_id);

    void delRouteInfoByRouteId(String route_id);

    List<route> selectMyFavorites(String email, String sortType);

    void subRouteCollectNumber(String route_id);

    List<scenery> getSceneryInfoByCityId(String city_id);

    scenery getSceneryInfoBySceneryId(String scenery_id);

    List<scenery_comment> getSceneryCommentListBySceneryId(String scenery_id);

    void updateComment(scenery_comment comment);

    void delSceneryComment(String scenery_date);
}
