package cusb.itworx.com.movies1;

import android.content.Intent;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {
    Movie_obj Detailed_movie;
    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("detailed fragment started ");
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        //Intent i = getIntent();

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movie_object")) {
            Detailed_movie = (Movie_obj) intent.getSerializableExtra("movie_object");
        }
        /*
        04-26 01:21:00.740 13240-13271/? I/System.out: movie json string{"poster_path":"\/gj282Pniaa78ZJfbaixyLXnXEDI.jpg",
        "adult":false,"overview":"Katniss Everdeen reluctantly becomes the symbol of a mass rebellion against the autocratic Capitol.",
        "release_date":"2014-11-18","genre_ids":[878,12,53],"id":131631,"original_title":"The Hunger Games: Mockingjay - Part 1",
        "original_language":"en","title":"The Hunger Games: Mockingjay - Part 1","backdrop_path":"\/83nHcz2KcnEpPXY50Ky2VldewJJ.jpg",
        "popularity":12.707473,"vote_count":2780,"video":false,"vote_average":6.8}
        */
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
    return  rootView;

    }      // end of oncreateview





} //end of DetailsActivity Fragment class
