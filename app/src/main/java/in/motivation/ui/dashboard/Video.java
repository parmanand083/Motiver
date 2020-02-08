package in.motivation.ui.dashboard;

public class Video {

    /*
     it has all attribute related to videos
     such as id,url ...
     */

    private
    int video_id;
    int cat_id;
    String talent_pic;
    String talent_id;
    String talent_name;
    String video_url;
    String thum_url;
    String desc;
    String title;

    public String getTalent_pic() {
        return talent_pic;
    }

    public void setTalent_pic(String talent_pic) {
        this.talent_pic = talent_pic;
    }

    public String getTalent_id() {
        return talent_id;
    }
    public void setTalent_id(String talent_id) {
        this.talent_id = talent_id;
    }

    public String getTalent_name() {
        return talent_name;
    }

    public void setTalent_name(String talent_name) {
        this.talent_name = talent_name;
    }

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


}
