package cusb.itworx.com.movies1;

/**
 * Created by mahae_000 on 4/30/2016.
 */
public class Trailer_obj {
    String name;
    String key;

    public Trailer_obj() {

    }
    public Trailer_obj(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
