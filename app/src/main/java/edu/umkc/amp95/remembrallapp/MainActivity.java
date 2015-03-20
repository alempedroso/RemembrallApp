package edu.umkc.amp95.remembrallapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {

    DBHelper database = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<HashMap<String, String>> programsList = database.selectSeries();

        if(programsList.size() != 0)
        {
            ListView lv = (ListView) findViewById(R.id.list);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long ID) {
                    TextView id = (TextView) view.findViewById(R.id.id);

                    String idValue = id.getText().toString();

                    Intent editIntent = new Intent(getApplication(), ProgramInfo.class);

                    editIntent.putExtra("id", idValue);

                    startActivity(editIntent);
                }
            });

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, programsList, R.layout.activity_programs_listview,
                    new String[] {"id", "name", "time", "day"},
                    new int[] {R.id.id, R.id.name, R.id.time, R.id.day});

            lv.setAdapter(adapter);

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void displayNewProgram(MenuItem item){
        Intent intent = new Intent(this, NewProgram.class);
        startActivity(intent);
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
