package edu.umkc.amp95.remembrallapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;


public class ActivitySplash extends Activity {

    private static String TAG = ActivitySplash.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_activity_splash);

        IntentLauncher intent = new IntentLauncher();
        intent.start();
    }

    public class IntentLauncher extends Thread {

        public void run()
        {
            try
            {
                sleep(3000);
            }
            catch (Exception e)
            {
                Log.e(TAG, e.getMessage());
            }

            Intent intent = new Intent(ActivitySplash.this, MainActivity.class);
            ActivitySplash.this.startActivity(intent);
            ActivitySplash.this.finish();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
