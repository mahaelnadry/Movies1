package cusb.itworx.com.movies1;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahae_000 on 3/25/2016.
 */
public class GridAdapter extends BaseAdapter {
    private  Context context;
    private List<Movie_obj> movies_obj_array ;
    private int resource;
    private Object mLock;

    public GridAdapter( Context movie_context,int resource_layout,List<Movie_obj> movie_array) {
        context=movie_context;
movies_obj_array=movie_array;
        resource=resource_layout;
    }

    @Override
    public int getCount() {
        System.out.println("get count is called, count is "+movies_obj_array.size()   );
        return this.movies_obj_array.size();

    }

    @Override
    public Movie_obj getItem(int position) {
        System.out.println("get item is called");
        return this.movies_obj_array.get(position);
    }

    @Override
    public long getItemId(int position) {
        System.out.println("get item id is called");
        return this.movies_obj_array.indexOf(position);
    }


    public boolean add(Movie_obj item)
    {
        boolean added =false;
        added=this.movies_obj_array.add(item);
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

        ImageView img=(ImageView)convertView;
        if (img == null) {
          //  img = new ImageView(context);
        //    convertView=img;
            System.out.println("img view is null");
            img=(ImageView) LayoutInflater.from(context).inflate(resource, parent, false);
          //  img.setImageResource(R.drawable.picture1);
        }

//        img =(ImageView) convertView;
        //ImageView iconView = (ImageView) convertView.findViewById(R.id.movieImage_view);
        //iconView.setImageResource(R.drawable.picture1);

    //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        String size="w185";
String full_url="http://image.tmdb.org/t/p/"+size+this.movies_obj_array.get(position).getImg_path();

        Picasso.with(context)
                .load(full_url)
                .placeholder(R.drawable.picture1)
                .error(R.drawable.icon)
                .fit()
                .into(img);


        return img;
    }
    public void updateValues(List<Movie_obj> elements) {
        System.out.println("update is called");
        synchronized (mLock) {
            this.movies_obj_array = elements;
        }
        notifyDataSetChanged();
    }
}
