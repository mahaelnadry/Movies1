package cusb.itworx.com.movies1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {
    Movie_obj Detailed_movie;
    Button fav_btn;
    SharedPreferences mPrefs ;
  //  = getSharedPreferences("CASPreferences", .MODE_PRIVATE);

    //To Save:


    /*
    To Retreive:

    Gson gson = new Gson();
    String json = mPrefs.getString("MyObject", "");
    MyObject obj = gson.fromJson(json, MyObject.class);
    ----------

*/



    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        System.out.println("detailed fragment started ");
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);


        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movie_object")) {
            Detailed_movie = (Movie_obj) intent.getSerializableExtra("movie_object");
        }
        //Detailed_movie = new Movie_obj("The Hunger Games: Mockingjay - Part 1","/gj282Pniaa78ZJfbaixyLXnXEDI.jpg"
          //      ,"Katniss Everdeen reluctantly becomes the symbol of a mass rebellion against the autocratic Capitol."
        //  ,"2014-11-18","131631","12.707473","2780","6.8","null trailer");

        ((TextView) (rootView.findViewById(R.id.text_title))).setText(Detailed_movie.getTitle());
        ((TextView) (rootView.findViewById(R.id.text_rating))).setText(Detailed_movie.getAvg());
        ((TextView) (rootView.findViewById(R.id.text_date))).setText(Detailed_movie.getDate());
        ((TextView) (rootView.findViewById(R.id.text_desc))).setText(Detailed_movie.getDesc());
        String size = "w185/";
        String full_url = "http://image.tmdb.org/t/p/" + size + Detailed_movie.getImg_path();
        ImageView img = (ImageView) rootView.findViewById(R.id.img_poster);
        Picasso.with(getActivity())
                .load(full_url)
                .placeholder(R.drawable.poster_loading)
                .error(R.drawable.poster_loading_error)
                .fit()
                .into(img);
       mPrefs=PreferenceManager.getDefaultSharedPreferences(getActivity());
       // mPrefs = getActivity().getSharedPreferences("fav_movies_id", Context.MODE_PRIVATE);
        //mPrefs = getActivity().getSharedPreferences("fav_movies_data", Context.MODE_PRIVATE);
        fav_btn=(Button)rootView.findViewById(R.id.button_fav);

        String movie_isFav = (mPrefs.getString(Detailed_movie.getId(), "not_fav"));
        System.out.println("channel is "+movie_isFav);
        if(movie_isFav!="not_fav")
        {
            fav_btn.setText("Added to");
            //fav_btn.setBackgroundColor(Color.CYAN);
            fav_btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.btn_star_big_on, 0);

        }
        fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean commited;
                fav_btn.setText("Added to");
                fav_btn.setCompoundDrawablesWithIntrinsicBounds(0,0,android.R.drawable.btn_star_big_on,0);
                //fav_btn.setBackgroundColor(Color.CYAN);
                Detailed_movie.setFav(true);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(Detailed_movie);
                prefsEditor.putString(Detailed_movie.getId(), json);
                commited=prefsEditor.commit();
                Map<String,?> keys =mPrefs.getAll();

                for(Map.Entry<String,?> entry : keys.entrySet()){
                    System.out.println("map values in details"+ entry.getKey() + ": " +
                            entry.getValue().toString());

                    //  clear array list and update view , notifydataset
                }

            }});

        return  rootView;
            } // end of oncreateview





    }     //end of DetailsActivity Fragment class

