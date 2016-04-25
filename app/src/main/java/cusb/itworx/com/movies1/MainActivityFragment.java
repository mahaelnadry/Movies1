package cusb.itworx.com.movies1;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

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

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG = FetchMovieTask.class.getSimpleName();
    public  GridAdapter movie_Adapter;
    public ArrayList <Movie_obj>movie_obj_array;
    public GridView movies_Grid;
    public void UpdateMovie() {
        System.out.println("updatemovie is called ");
        FetchMovieTask weatherTask= new FetchMovieTask();

        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
       // String location = settings.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));

        weatherTask.execute("popular");
        System.out.println("after execute in update movie ");
    }

    public MainActivityFragment() {
    }
    /*
    @Overrideupd
    public void onStart() {
        super.onStart();
        UpdateMovie();
    }
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movie_obj_array= new ArrayList<Movie_obj>();

        UpdateMovie();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final String[] List1 = {"/aBBQSC8ZECGn6Wh92gKDOakSC8p.jpg",
                "/aBBQSC8ZECGn6Wh92gKDOakSC8p.jpg",
                "/aBBQSC8ZECGn6Wh92gKDOakSC8p.jpg",
                "/aBBQSC8ZECGn6Wh92gKDOakSC8p.jpg",
                "/aBBQSC8ZECGn6Wh92gKDOakSC8p.jpg",
                "/aBBQSC8ZECGn6Wh92gKDOakSC8p.jpg",
        };
        // List<String> weekForecast = new ArrayList<String>(Arrays.asList(List1));
//R.layout.grid_item, R.id.movieImage_view,
        //final ArrayList<String> movie_array = new ArrayList<String>(Arrays.asList(List1));
       // GridAdapter ;
        if(movie_obj_array==null)
        {
            System.out.println("entered if movie array=null ");
            ArrayList<Movie_obj> temp_array = new ArrayList<Movie_obj>();
            Movie_obj one;
            one = new Movie_obj("titanic","/aBBQSC8ZECGn6Wh92gKDOakSC8p.jpg"," "," "," "," "," "," empty ","trailer hard coded ");
            temp_array.add(one);
            movie_Adapter = new GridAdapter(getActivity(), R.layout.grid_item,temp_array);
        }
        else {
            System.out.println("entered  movie array not null ");
            movie_Adapter = new GridAdapter(getActivity(), R.layout.grid_item, movie_obj_array);
        }
       // System.out.println("movie adapter count is"+movie_Adapter.getCount());
          movies_Grid = (GridView) rootView.findViewById(R.id.movieGrid_view);
        movies_Grid.setAdapter(movie_Adapter);

        /*
        movies_Grid.post(new Runnable() {
            public void run() {
               // movie_Adapter.updateValues(movie_array);
           //     movie_Adapter.notifyDataSetChanged();
                //movie_Adapter.updateValues(movie_array);
            }

        });
        */
       // movie_Adapter.getView(1,inflater.inflate(R.layout.fragment_main, container, false),)
        //movies_Grid.setAdapter(movie_Adapter);  //linking between adapter and listview
        return inflater.inflate(R.layout.fragment_main, container, false);
    }



    public class FetchMovieTask extends AsyncTask<String, Void,ArrayList<Movie_obj>> {


        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();


        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         * <p/>
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */

        @Override
        protected ArrayList<Movie_obj> doInBackground(String... params) {
            // protected Void doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            System.out.println("start of do in background");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String MovieJsonStr = null;

            Log.v(LOG_TAG, "before try");
            try {

                Log.v(LOG_TAG, "inside try");
                final String MOVIE_BASE_URL =
                        "https://api.themoviedb.org/3/movie";
                //http://api.themoviedb.org/3/movie/popular?api_key
                //  "https://api.themoviedb.org/3/movie/?api_key=b7e2c81ec5a30ec647b2a6e26affb201";
                //name , pic, rate , desc
                final String FIND_PARAM = "popular";
                String api_key="b7e2c81ec5a30ec647b2a6e26affb201";
                final String API_KEY=api_key;
                final String API="api_key";
                //.appendQueryParameter(FORMAT_PARAM, format)
                Log.v(LOG_TAG, "before URI");
                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendEncodedPath(params[0])
                        .appendQueryParameter(API,api_key)
                        .build();
                Log.v(LOG_TAG, "before url");
                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "after url");
                /*
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM,params[0])
                        .appendQueryParameter(FORMAT_PARAM,format)
               */
                Log.v(LOG_TAG, "BUILT URI" + builtUri.toString());
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
                //java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
                //String theString= s.hasNext() ? s.next() : "";
                //System.out.println("inputstream  is "+theString);
                //String theString = IOUtils.toString(inputStream, encoding);
                if (inputStream == null) {
                    // Nothing to do.
                    System.out.println("input stream is null");
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    System.out.println("reader in system"+line+"\n");
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    System.out.println("buffer length is zero "+"\n");
                    return null;
                }
                MovieJsonStr = buffer.toString();
                System.out.println( "Movie JSON String:" + MovieJsonStr);
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
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                ArrayList<Movie_obj> results=new ArrayList<Movie_obj>();
                results=getmovieDataFromJson(MovieJsonStr);
                //System.out.println("get count results"+results.ge);
                System.out.println("end of do in background return results");
                return results;
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            System.out.println("end of do in background return null");
            return null;
        }

        private ArrayList<Movie_obj> getmovieDataFromJson(String movieJsonStr)
                throws JSONException {

            System.out.println("start of getmovie data");
            // These are the names of the JSON objects that need to be extracted.
            final String OWM_IMG = "poster_path";
            final String OWM_DESC="overview";
            final String OWM_DATE="release_date";
            final String OWM_ID="id";
            final String OWM_TITLE="original_title";
            final String OWM_POPULAR = "popularity";
            final String OWM_COUNT = "vote_count";
            final String OWM_AVG = "vote_average";

           // JSONArray movieArray = new JsonArray(movieJsonStr);
            //JSONObject movieJson = new JSONObject(movieArray);

            JSONObject AllJson = new JSONObject(movieJsonStr);
            JSONArray resultsArray = AllJson.getJSONArray("results");

            //String[] imgStrs = new String[resultsArray.length()];
           // ArrayList<editTextString> list = new ArrayList<editTextString>();
            //movie_obj_array = new ArrayList <Movie_obj>();
            for (int i = 0; i < resultsArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                // Get the JSON object representing the day
                JSONObject movieJson=resultsArray.getJSONObject(i);
                System.out.println("movie json string" +movieJson.toString());
                System.out.println("object to string" + movieJson.toString());

                Movie_obj temp=new Movie_obj(movieJson.getString(OWM_TITLE),movieJson.getString(OWM_IMG),
                        movieJson.getString(OWM_DESC),movieJson.getString(OWM_DATE),
                        movieJson.getString(OWM_ID),movieJson.getString(OWM_POPULAR),
                        movieJson.getString(OWM_COUNT),
                        movieJson.getString(OWM_AVG),
                        "   ");
               movie_obj_array.add(temp);
               // movie_obj_array.set(i,temp);
                /*
                movie_obj_array[i].img_path=movieJson.getString(OWM_IMG);
                movie_obj_array[i].desc=movieJson.getString(OWM_DESC);
                movie_obj_array[i].date=movieJson.getString(OWM_DATE);
                movie_obj_array[i].title=movieJson.getString(OWM_TITLE);
                movie_obj_array[i].count=movieJson.getString(OWM_COUNT);
                movie_obj_array[i].avg=movieJson.getString(OWM_AVG);
                */
                System.out.println("img path   "+movie_obj_array.get(i).getImg_path()     );
                System.out.println("img desc   "+movie_obj_array.get(i).getDesc()    );
                //imgStrs[i] =  movie_obj_array[i].img_path;

            }
            //String[] resultStrs = new String[10];
            /*
            for (String s : imgStrs) {
                System.out.println("img string   "+s);

            }
            */
            System.out.println("end of get movie data");
            return movie_obj_array;

        }  //end of get movie data


        @Override
        protected void onPostExecute(ArrayList<Movie_obj> result) {    //result da el rage3 mn do in background
            System.out.println("start of onpostexecture");
            if (result != null) {
                System.out.println("results of onpostexecture not null");
                System.out.println("size of results is"+result.size());
             //   movie_Adapter.clear();
                /*
                for (Movie_obj movieobj : result) {
                    movie_Adapter.add(movieobj);   // 3shan n7ot el data el rag3a mn el server fy el array adapter
                    System.out.println("img url in post   " + movieobj.getImg_path());
                }
                */
                for(int i=0 ; i<movie_Adapter.getCount() ; i++){
                    Movie_obj s = movie_Adapter.getItem(i);
                    System.out.println("img url in adapter   " + s.getImg_path());
                }

            }
            System.out.println("end of onpostexecute");
        }
    }

}

