package in.motivation.ui.dashboard;

public class VideoList{
    int video_id;
    int cat_id;

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getThum_url() {
        return thum_url;
    }

    public void setThum_url(String thum_url) {
        this.thum_url = thum_url;
    }

    String video_url;
    String thum_url;
    String desc;
    String  title;
}
