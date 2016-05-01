package cusb.itworx.com.movies1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahae_000 on 4/30/2016.
 */
public class ReviewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Review_obj> Review_obj_array ;
    private int resource;
    private Object mLock;



    public ReviewAdapter( Context movie_context,int resource_layout,ArrayList<Review_obj> movie_array) {
        context=movie_context;
        Review_obj_array=movie_array;
        resource=resource_layout;
    }

    @Override
    public int getCount() {
        System.out.println("get count is called, count is "+Review_obj_array.size()   );
        return this.Review_obj_array.size();

    }

    @Override
    public Review_obj getItem(int position) {
        System.out.println("get item is called");
        return this.Review_obj_array.get(position);
    }

    @Override
    public long getItemId(int position) {
        System.out.println("get item id is called");
        return this.Review_obj_array.indexOf(position);
    }


    public boolean add(Review_obj item)
    {
        boolean added =false;
        added=this.Review_obj_array.add(item);
        System.out.println("add is called");
        return added;
    }
    /*
    public void clear()
    {    System.out.println("count before clear is"+this.movies_obj_array.size());
        System.out.println("clear is called");
       this. movies_obj_array.clear();
        System.out.println("count after clear is"+this.movies_obj_array.size());
    }
    */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("get view is called");

        View row =convertView;
        ViewHolder holder =null;

        if (row== null) {
            row = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.textView_author = (TextView) row.findViewById(R.id.list_item_author);
            holder.textView_comment = (TextView) row.findViewById(R.id.list_item_comment);
            row.setTag(holder);
        }
             else {
                holder = (ViewHolder) row.getTag();
            }
        Review_obj review = Review_obj_array.get(position);
        holder.textView_author.setText(review.getAuthor());
        holder.textView_comment.setText(review.getComment());
        return row;
    }

    public void updateValues(ArrayList<Review_obj> elements) {
        System.out.println("update is called");
        synchronized (mLock) {
            this.Review_obj_array = elements;
        }
       // notifyDataSetChanged();
    }
    static class ViewHolder
    {
        TextView textView_author;
        TextView textView_comment;

    }
}
