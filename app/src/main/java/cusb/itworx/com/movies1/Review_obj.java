package cusb.itworx.com.movies1;

/**
 * Created by mahae_000 on 4/30/2016.
 */
public class Review_obj {
    String author;
    String comment;

    public Review_obj(String author, String comment) {
        this.author = author;
        this.comment = comment;
    }

    public Review_obj() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
