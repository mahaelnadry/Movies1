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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {
    Movie_obj Detailed_movie;
    Button fav_btn;
    Button trailer_btn;
    SharedPreferences mPrefs ;
    String Trailer_key="empty";
    public DetailsActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

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
        trailer_btn=(Button)rootView.findViewById(R.id.button_trailer);
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


        trailer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trailer_Async trailer = new Trailer_Async();
                trailer.execute(Detailed_movie.getId());
                while(Trailer_key=="null"){}
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + Trailer_key));
                System.out.println("youtube link"+"http://www.youtube.com/watch?v="+Trailer_key);
                startActivity(intent);

            }});






        return  rootView;
            } // end of oncreateview




    public class Trailer_Async extends AsyncTask<String, Void,String> {
        @Override
        protected String doInBackground(String... params) {
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
                String results;
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

        public String getTrailerDataFromJson(String movieJsonStr) throws JSONException {
            String youtube_key;
            System.out.println("start of getmovie data");
            // These are the names of the JSON objects that need to be extracted.
            final String OWM_VIDEO_KEY = "key";
            JSONObject AllJson = new JSONObject(movieJsonStr);
            JSONArray resultsArray = AllJson.getJSONArray("results");
            JSONObject movieJson=resultsArray.getJSONObject(0);
            System.out.println("movie json string" +movieJson.toString());
            System.out.println("object to string" + movieJson.toString());
            youtube_key=movieJson.getString(OWM_VIDEO_KEY);
            System.out.println("youtube key in do in background"+youtube_key);
            return youtube_key;
        }


        @Override
        protected void onPostExecute(String s) {
            Trailer_key=s;
            System.out.println("youtube key in onpost"+Trailer_key);
        }
    }// end of Trailer async task

    }     //end of DetailsActivity Fragment class

