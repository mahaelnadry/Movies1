package cusb.itworx.com.movies1;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            System.out.println("saved instance is null");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main, new MainActivityFragment())
                    .commit();
            //R.id.fragment is the id of  xml of activity_main.xml
        }
    }

}
