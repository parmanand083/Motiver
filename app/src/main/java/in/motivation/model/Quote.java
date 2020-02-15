package in.motivation.model;



/*
has all  attribute of a quotes

*/
public  class Quote{
    int id;
    String lang;
    String url;
    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getId() {
        return id;
    }
    public String getUrl() {
        return url;
    }
}