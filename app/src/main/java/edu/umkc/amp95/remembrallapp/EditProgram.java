package edu.umkc.amp95.remembrallapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;


public class EditProgram extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    EditText name;
    EditText time;
    EditText channel;
    CheckBox sunday;
    CheckBox monday;
    CheckBox tuesday;
    CheckBox wednesday;
    CheckBox thursday;
    CheckBox friday;
    CheckBox saturday;
    Spinner notification;

    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprogram);

        name = (EditText) findViewById(R.id.name);
        time = (EditText) findViewById(R.id.time);
        channel = (EditText) findViewById(R.id.channel);

        notification = (Spinner) findViewById(R.id.comboNotification);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.notification_levels, R.layout.support_simple_spinner_dropdown_item);
        notification.setAdapter(adapter);
        notification.setOnItemSelectedListener(this);

        sunday = (CheckBox)findViewById(R.id.boxSunday);
        monday = (CheckBox)findViewById(R.id.boxMonday);
        tuesday = (CheckBox)findViewById(R.id.boxTuesday);
        wednesday = (CheckBox)findViewById(R.id.boxWednesday);
        thursday = (CheckBox)findViewById(R.id.boxThursday);
        friday = (CheckBox)findViewById(R.id.boxFriday);
        saturday = (CheckBox)findViewById(R.id.boxSaturday);

        Intent myIntent = getIntent();

        String id = myIntent.getStringExtra("id");

        HashMap<String, String> programInfo = db.selectShow(id);
        ArrayList<String> days = db.selectDays(id);

        if(programInfo.size() != 0){
            name.setText(programInfo.get("name"));
            time.setText(programInfo.get("time"));
            channel.setText(programInfo.get("channel"));
            //notification.setText(programInfo.get("notification"));

            for(String day : days)
            {
                if(day.equals(new String("Sunday")))
                {
                    sunday.setChecked(true);
                }
                if(day.equals(new String("Monday")))
                {
                    monday.setChecked(true);
                }
                if(day.equals(new String("Tuesday")))
                {
                    tuesday.setChecked(true);
                }
                if(day.equals(new String("Wednesday")))
                {
                    wednesday.setChecked(true);
                }
                if(day.equals(new String("Thursday")))
                {
                    thursday.setChecked(true);
                }
                if(day.equals(new String("Friday")))
                {
                    friday.setChecked(true);
                }
                if(day.equals(new String("Saturday")))
                {
                    saturday.setChecked(true);
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editprogram, menu);
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

        name = (EditText) findViewById(R.id.name);
        time = (EditText) findViewById(R.id.time);
        channel = (EditText) findViewById(R.id.channel);
        notification = (Spinner) findViewById(R.id.comboNotification);

        Intent myIntent = getIntent();

        String id = myIntent.getStringExtra("id");

        queryValues.put("id", id);
        queryValues.put("name", name.getText().toString());
        queryValues.put("time", time.getText().toString());
        queryValues.put("channel", channel.getText().toString());
        queryValues.put("notification", notification.getSelectedItem().toString());

        db.updateShow(queryValues);

        ArrayList<String> days = new ArrayList<String>();

        if(sunday.isChecked()){
            days.add("Sunday");
        }

        if(monday.isChecked()){
            days.add("Monday");
        }

        if(tuesday.isChecked()){
            days.add("Tuesday");
        }

        if(wednesday.isChecked()){
            days.add("Wednesday");
        }

        if(thursday.isChecked()){
            days.add("Thursday");
        }

        if(friday.isChecked()){
            days.add("Friday");
        }

        if(saturday.isChecked()){
            days.add("Saturday");
        }

        db.updateDays(id, days);

        this.callMainActivity(view);

    }

    public void callMainActivity(View view){
        Intent mainIntent = new Intent(getApplication(), MainActivity.class);
        startActivity(mainIntent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
