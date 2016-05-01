package cusb.itworx.com.movies1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {
    Movie_obj Detailed_movie;
    Button fav_btn;
    Button trailer_btn;
    SharedPreferences mPrefs ;
    String Trailer_key;
    ListView Trailers_ListView;
    ListView Reviews_ListView;
    ArrayList<Trailer_obj> Trailers_List;
    ArrayList<Review_obj> Reviews_List;
    public ArrayAdapter<String> TrailersAdapter;
    //public ArrayAdapter<String> ReviewsAdapter;
    //public ArrayAdapter<Review_obj> ReviewsAdapter;
    public ReviewAdapter ReviewsAdapter;
    ArrayList<String> Comments_List;
    ArrayList<String> Authors_List;
    Review_obj  review_temp;
    Trailer_obj trailer_temp;
    public DetailsActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Trailer_key="empty";
        Trailers_List= new ArrayList<Trailer_obj>();
         trailer_temp = new Trailer_obj();
        Reviews_List= new ArrayList<Review_obj>();
         review_temp = new Review_obj();

        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movie_object")) {
            Detailed_movie = (Movie_obj) intent.getSerializableExtra("movie_object");
            Trailer_Async trailer = new Trailer_Async();
            trailer.execute(Detailed_movie.getId());
            Reviews_Async Review = new Reviews_Async();
           Review.execute(Detailed_movie.getId());
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        //Trailer_Async trailer = new Trailer_Async();
        //trailer.execute(Detailed_movie.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("detailed fragment started ");
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        //Detailed_movie = new Movie_obj("The Hunger Games: Mockingjay - Part 1","/gj282Pniaa78ZJfbaixyLXnXEDI.jpg"
          //      ,"Katniss Everdeen reluctantly becomes the symbol of a mass rebellion against the autocratic Capitol."
        //  ,"2014-11-18","131631","12.707473","2780","6.8","null trailer");

        ((TextView) (rootView.findViewById(R.id.text_title))).setText(Detailed_movie.getTitle());
        ((TextView) (rootView.findViewById(R.id.text_rating))).setText(Detailed_movie.getAvg()+"/10");
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
        fav_btn=(Button)rootView.findViewById(R.id.button_fav);
        Trailers_ListView=(ListView)rootView.findViewById(R.id.Listview_trailers);
       Reviews_ListView=(ListView)rootView.findViewById(R.id.Listview_reviews);
       // trailer_btn=(Button)rootView.findViewById(R.id.button_trailer);
        String movie_isFav = (mPrefs.getString(Detailed_movie.getId(), "not_fav"));
        System.out.println("channel is " + movie_isFav);
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
                fav_btn.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.btn_star_big_on, 0);
                //fav_btn.setBackgroundColor(Color.CYAN);
                Detailed_movie.setFav(true);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(Detailed_movie);
                prefsEditor.putString(Detailed_movie.getId(), json);
                commited = prefsEditor.commit();
                Map<String, ?> keys = mPrefs.getAll();

                for (Map.Entry<String, ?> entry : keys.entrySet()) {
                    System.out.println("map values in details" + entry.getKey() + ": " +
                            entry.getValue().toString());

                    //  clear array list and update view , notifydataset
                }

            }
        });
        List<String> review_names = new ArrayList<String>();
        System.out.println("review size before while" +  Reviews_List.size());
        if(Reviews_List.size()==0){
            System.out.println("loading reviews toast , length of review list "+Reviews_List.size());
            Toast.makeText(getActivity(), "Loading Reviews, please wait",
                    Toast.LENGTH_SHORT).show();

        }
        System.out.println("review size after while" +  Reviews_List.size());
       // while(Reviews_List.size()!=0) {
            for (int i = 0; i < Reviews_List.size(); i++) {
                review_names.add(Reviews_List.get(i).getAuthor());
                System.out.println("review names" + review_names.get(i));
            }
             //   ReviewsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.review_item, R.id.list_item_author, review_names);
        ReviewsAdapter = new ReviewAdapter(getActivity(), R.layout.review_item, Reviews_List);
                //weekForecast.add()// lw 3izeen nzawed elements tany fy el array
                Reviews_ListView.setAdapter(ReviewsAdapter);  //linking between adapter and listview

        //}
        /*
        else
        {
            System.out.println("loading reviews toast , length of review list "+Reviews_List.size());
            Toast.makeText(getActivity(), "Loading Reviews, please wait",
                    Toast.LENGTH_LONG).show();
        }
        */
            List<String> video_names = new ArrayList<String>();
            for (int i = 0; i < Trailers_List.size(); i++) {
                video_names.add(Trailers_List.get(i).getName());

            }
            TrailersAdapter = new ArrayAdapter<String>(getActivity(), R.layout.trailer_item, R.id.list_item_trailer, video_names);
            //weekForecast.add()// lw 3izeen nzawed elements tany fy el array
            Trailers_ListView.setAdapter(TrailersAdapter);  //linking between adapter and listview
        //}
        Trailers_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Str
                String temp = TrailersAdapter.getItem(position);
                trailer_temp = Trailers_List.get(position);
/*
                if (trailer_temp == null) {  // because since the user pressed on trailer , it means that it is not null
                    Toast.makeText(getActivity(), "Loading Trailer in one click , please wait",
                            Toast.LENGTH_LONG).show();
                } else {
  */
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer_temp.key));
                    System.out.println("youtube link" + "http://www.youtube.com/watch?v=" + trailer_temp.key);
                    startActivity(intent);
    //            }
            }
        });
        return  rootView;
            } // end of oncreateview

    public class Reviews_Async extends AsyncTask<String, Void,ArrayList <Review_obj>> {
        @Override
        protected ArrayList <Review_obj> doInBackground(String... params) {
            System.out.println("start of review do in background");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String ReviewsJsonStr = null;

            try {

                final String MOVIE_BASE_URL =
                        "https://api.themoviedb.org/3/movie";
                //https://api.themoviedb.org/3/movie/281957/videos?api_key=b7e2c81ec5a30ec647b2a6e26affb201

                final String reviews ="reviews";
                String api_key="b7e2c81ec5a30ec647b2a6e26affb201";
                final String API_KEY=api_key;
                final String API="api_key";

                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendEncodedPath(params[0]).appendEncodedPath(reviews)
                        .appendQueryParameter(API, api_key)
                        .build();
                URL url = new URL(builtUri.toString());
                System.out.println("built uri for reviews from system.out: " + builtUri.toString());
                // Create the request to OpenWeatherMap, and open the connection
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    System.out.println("urlconnection " + urlConnection.getContentType());
                }
                catch(Exception e)
                {
                    System.out.println("urlconnection not connected");
                }

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    System.out.println("input stream is null");
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("reader in system"+line+"\n");
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    System.out.println("buffer length is zero "+"\n");
                    return null;
                }
               ReviewsJsonStr = buffer.toString();
                System.out.println( "reviews JSON String:" + ReviewsJsonStr);
            } catch (IOException e) {
                System.out.println("Error "+ e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        System.out.println("Error closing stream"+e);
                    }
                }
            }
            try {
                ArrayList <Review_obj> results;
                results=getReviewsDataFromJson(ReviewsJsonStr);
                System.out.println("end of  review do in background return results of length"+results.size());
                return results;
            } catch (JSONException e) {
                System.out.println(e.getMessage()+ e);
                e.printStackTrace();
            }
            System.out.println("end of review do in background return null");
            return null;
        }

        public ArrayList<Review_obj> getReviewsDataFromJson(String movieJsonStr) throws JSONException {

            String author,comment;
            System.out.println("start of getreview data");
            System.out.println("review json str in get review data"+movieJsonStr);
            // These are the names of the JSON objects that need to be extracted.
            final String OWM_REVIEW_AUTHOR = "author";
            final String OWM_REVIEW_COMMENT = "content";
            JSONObject AllJson = new JSONObject(movieJsonStr);
            System.out.println(" review json obj " + AllJson.toString());
            JSONArray resultsArray = new JSONArray();
                    resultsArray=AllJson.getJSONArray("results");
            System.out.println(" review results array length"+resultsArray.length());
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject movieJson = resultsArray.getJSONObject(i);
                System.out.println("reviews to string" + movieJson.toString());
                author = movieJson.getString(OWM_REVIEW_AUTHOR);
                comment = movieJson.getString(OWM_REVIEW_COMMENT);
                Review_obj temp=new Review_obj(author,comment);
                Reviews_List.add(temp);

                System.out.println("Review list author element i" +i+ Reviews_List.get(i).getAuthor());
            }
            for (int i = 0; i < Reviews_List.size(); i++) {

                System.out.println("review list item at end of getmovie " + Reviews_List.get(i));
            }
            return  Reviews_List;
        }

        @Override
        protected void onPostExecute(ArrayList<Review_obj> result) {    //result da el rage3 mn do in background
            System.out.println("start of onpostexecture");
            super.onPostExecute(result);
            if (result != null) {
                ReviewsAdapter.notifyDataSetChanged();
                System.out.println("results of onpostexecture not null");
                System.out.println("size of results is"+result.size());

                for(int i=0 ; i<ReviewsAdapter.getCount() ; i++){
                    Review_obj s = ReviewsAdapter.getItem(i);
                    System.out.println("author in adapter   " + s.getAuthor());
                }

            }
            System.out.println("end of onpostexecute");
        }

    }// end of review async task


    public class Trailer_Async extends AsyncTask<String, Void,ArrayList <Trailer_obj>> {
        @Override
        protected ArrayList <Trailer_obj> doInBackground(String... params) {
            System.out.println("start of Trailer do in background");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String TrailerJsonStr = null;

            try {

                final String MOVIE_BASE_URL =
                        "https://api.themoviedb.org/3/movie";
                //https://api.themoviedb.org/3/movie/281957/videos?api_key=b7e2c81ec5a30ec647b2a6e26affb201

                final String videos ="videos";
                String api_key="b7e2c81ec5a30ec647b2a6e26affb201";
                final String API_KEY=api_key;
                final String API="api_key";

                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendEncodedPath(params[0]).appendEncodedPath(videos)
                        .appendQueryParameter(API, api_key)
                        .build();
                URL url = new URL(builtUri.toString());
                System.out.println("built uri from system.out: " + builtUri.toString());
                // Create the request to OpenWeatherMap, and open the connection
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    System.out.println("urlconnection " + urlConnection.getContentType());
                }
                catch(Exception e)
                {
                    System.out.println("urlconnection not connected");
                }

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    System.out.println("input stream is null");
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("reader in system"+line+"\n");
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    System.out.println("buffer length is zero "+"\n");
                    return null;
                }
                TrailerJsonStr = buffer.toString();
                System.out.println( "Trailer JSON String:" + TrailerJsonStr);
            } catch (IOException e) {
                System.out.println("Error "+ e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        System.out.println("Error closing stream"+e);
                    }
                }
            }
            try {
                ArrayList <Trailer_obj> results;
                results=getTrailerDataFromJson(TrailerJsonStr);
                System.out.println("end of do in background return results");
                return results;
            } catch (JSONException e) {
                System.out.println(e.getMessage()+ e);
                e.printStackTrace();
            }
            System.out.println("end of do in background return null");
            return null;
        }

        public ArrayList<Trailer_obj> getTrailerDataFromJson(String movieJsonStr) throws JSONException {

            String youtube_key,youtube_name;
            System.out.println("start of gettrailer data");
            // These are the names of the JSON objects that need to be extracted.
            final String OWM_VIDEO_KEY = "key";
            final String OWM_VIDEO_NAME = "name";
            JSONObject AllJson = new JSONObject(movieJsonStr);
            JSONArray resultsArray = AllJson.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject movieJson = resultsArray.getJSONObject(i);
                System.out.println("movie json string" + movieJson.toString());
                System.out.println("object to string" + movieJson.toString());
                youtube_key = movieJson.getString(OWM_VIDEO_KEY);
                youtube_name = movieJson.getString(OWM_VIDEO_NAME);
                Trailer_obj temp=new Trailer_obj(youtube_name,youtube_key);
                Trailers_List.add(temp);
                System.out.println("youtube key in do in background" + youtube_key);
            }

            return Trailers_List;
        }


        @Override
        protected void onPostExecute(ArrayList <Trailer_obj> s) {
            //Trailer=s;
           // super.onPostExecute();

            System.out.println("youtube key in onpost is called");
            super.onPostExecute(s);
            if (s != null) {
                ReviewsAdapter.notifyDataSetChanged();
                System.out.println("results of onpostexecture not null");
                System.out.println("size of results is"+s.size());


            }
        }
    }// end of Trailer async task

    }     //end of DetailsActivity Fragment class

