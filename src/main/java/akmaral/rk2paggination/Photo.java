package akmaral.rk2paggination;

public class Photo {

    private String filename;
    private String url;
    private String email;


    public Photo(String filename, String url) {
        this.filename = filename;
        this.url = url;
    }


    public Photo(String filename, String url, String email) {
        this.filename = filename;
        this.url = url;
        this.email = email;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}