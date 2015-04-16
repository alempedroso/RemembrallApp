package edu.umkc.amp95.remembrallapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;


public class ProgramInfo extends ActionBarActivity {

    TextView name;
    TextView time;
    TextView channel;
    TextView days;
    TextView notification;

    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_program_info);

        name = (TextView) findViewById(R.id.name);
        days = (TextView) findViewById(R.id.days);
        time = (TextView) findViewById(R.id.time);
        channel = (TextView) findViewById(R.id.channel);
        notification = (TextView) findViewById(R.id.notification);

        Intent myIntent = getIntent();
        String id = myIntent.getStringExtra("id");

        HashMap<String, String> programInfo = db.selectShow(id);

        if(programInfo.size() != 0){
            name.setText(programInfo.get("name"));
            time.setText(programInfo.get("time"));
            days.setText(programInfo.get("days"));
            channel.setText(programInfo.get("channel"));
            notification.setText(programInfo.get("notification"));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_program_info, menu);
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

    public void callMainActivity(View view){
        Intent mainIntent = new Intent(getApplication(), MainActivity.class);
        startActivity(mainIntent);
    }

    public void editProgram(View view){
        Intent myIntent = getIntent();

        String id = myIntent.getStringExtra("id");

        Intent editIntent = new Intent(getApplication(), EditProgram.class);

        editIntent.putExtra("id", id);

        startActivity(editIntent);
    }

    public void deleteData(View view){

        Intent myIntent = getIntent();
        String id = myIntent.getStringExtra("id");

        db.deleteShow(id);

        this.callMainActivity(view);

    }
}
