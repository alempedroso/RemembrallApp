package edu.umkc.amp95.remembrallapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;


public class NewProgram extends ActionBarActivity {

    EditText name;
    EditText day;
    EditText time;
    EditText channel;
    EditText notification;

    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newprogram);

        name = (EditText) findViewById(R.id.name);
        day = (EditText) findViewById(R.id.day);
        time = (EditText) findViewById(R.id.time);
        channel = (EditText) findViewById(R.id.textViewChannel);
        notification = (EditText) findViewById(R.id.notification);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_newprogram, menu);
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

    public void saveData (View view){
        HashMap<String, String> queryValues = new HashMap<String, String>();

        queryValues.put("name", name.getText().toString());
        queryValues.put("day", day.getText().toString());
        queryValues.put("time", time.getText().toString());
        queryValues.put("channel", channel.getText().toString());
        queryValues.put("notification", notification.getText().toString());

        db.insertSeries(queryValues);

        this.callMainActivity(view);
    }

    public void callMainActivity(View view){
        Intent mainIntent = new Intent(getApplication(), MainActivity.class);
        startActivity(mainIntent);

    }
}
