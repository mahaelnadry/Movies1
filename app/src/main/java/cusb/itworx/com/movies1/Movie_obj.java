package cusb.itworx.com.movies1;

import java.io.Serializable;

/**
 * Created by mahae_000 on 4/23/2016.
 */
public class Movie_obj implements Serializable{
    String img_path;
    String desc;
    String date;
    String id;
    String title;
    String popularity;
    String count;

    String avg;
    String trailer;
    String reviews;
    boolean fav;
    public Movie_obj() {
    }

    public Movie_obj(String title, String img_path, String desc, String date, String id, String popularity, String count, String avg, String trailer,String reviews,boolean fav) {
        this.title = title;
        this.img_path = img_path;
        this.desc = desc;
        this.date = date;
        this.id = id;
        this.popularity = popularity;
        this.count = count;
        this.avg = avg;
        this.trailer = trailer;
        this.reviews=reviews;
        this.fav=fav;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
}
