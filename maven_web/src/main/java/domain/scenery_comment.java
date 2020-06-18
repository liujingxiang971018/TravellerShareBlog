package domain;

public class scenery_comment {
    private int comment_id;
    private String user_id;
    private String scenery_id;
    private String comment_date;
    private String comment_detail;

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getScenery_id() {
        return scenery_id;
    }

    public void setScenery_id(String scenery_id) {
        this.scenery_id = scenery_id;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public String getComment_detail() {
        return comment_detail;
    }

    public void setComment_detail(String comment_detail) {
        this.comment_detail = comment_detail;
    }
}
