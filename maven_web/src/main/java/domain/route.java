package domain;

public class route {
    private String route_id;//路线的id，用户id+创建时间戳组成
    private String route_name;//路线名称
    private String user_id;//用户的id：即email
    private String route_createTime;//创建时间
    private String route_avatar;//路线的背景图索引
    private String route_label;//路线的标签
    private String route_detail;//路线简介
    private String start_time;//开始时间
    private int spend_time;//花费时间
    private int person_number;//参与人数
    private float route_cost;//路线成本
    private int like_number;//点赞人数
    private int dislike_number;//点踩人数
    private int collect_number;//收藏人数

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRoute_createTime() {
        return route_createTime;
    }

    public void setRoute_createTime(String route_createTime) {
        this.route_createTime = route_createTime;
    }

    public String getRoute_avatar() {
        return route_avatar;
    }

    public void setRoute_avatar(String route_avatar) {
        this.route_avatar = route_avatar;
    }

    public String getRoute_label() {
        return route_label;
    }

    public void setRoute_label(String route_label) {
        this.route_label = route_label;
    }

    public String getRoute_detail() {
        return route_detail;
    }

    public void setRoute_detail(String route_detail) {
        this.route_detail = route_detail;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public int getSpend_time() {
        return spend_time;
    }

    public void setSpend_time(int spend_time) {
        this.spend_time = spend_time;
    }

    public int getPerson_number() {
        return person_number;
    }

    public void setPerson_number(int person_number) {
        this.person_number = person_number;
    }

    public float getRoute_cost() {
        return route_cost;
    }

    public void setRoute_cost(float route_cost) {
        this.route_cost = route_cost;
    }

    public int getLike_number() {
        return like_number;
    }

    public void setLike_number(int like_number) {
        this.like_number = like_number;
    }

    public int getDislike_number() {
        return dislike_number;
    }

    public void setDislike_number(int dislike_number) {
        this.dislike_number = dislike_number;
    }

    public int getCollect_number() {
        return collect_number;
    }

    public void setCollect_number(int collect_number) {
        this.collect_number = collect_number;
    }
}
