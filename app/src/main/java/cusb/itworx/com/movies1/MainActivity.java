package cusb.itworx.com.movies1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends ActionBarActivity implements MovieListener{
boolean isTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout flpanel2 =(FrameLayout)findViewById(R.id.FLpanel2);
            if(flpanel2==null)
                isTwoPane=false;
            else
            isTwoPane=true;
        if (savedInstanceState == null) {
            System.out.println("saved instance is null");
            MainActivityFragment mainFragment=new MainActivityFragment();
            mainFragment.setListener(this);
            getSupportFragmentManager().beginTransaction().add(R.id.main, mainFragment).commit();
            //R.id.fragment is the id of  xml of activity_main.xml
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void setSelectedMovie(Movie_obj selected) {
        if(isTwoPane){
         DetailsActivityFragment details_fragment = new DetailsActivityFragment();
            Bundle extras = new Bundle();
            extras.putSerializable("movie_object",selected);
            //extras.putString("movie_object",selected);
            details_fragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction().replace(R.id.FLpanel2,details_fragment).commit(); // to inflate fragment 2
            // we call replace instead of add in order not to draw fragments over each other

        }

            else  //single pane
        {
            Intent i = new Intent(this, DetailsActivity.class);
            i.putExtra("movie_object", selected);
            startActivity(i);

        }
    }
}
