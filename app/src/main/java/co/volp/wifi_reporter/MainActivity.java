package co.volp.wifi_reporter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import javax.mail.MessagingException;
import android.database.sqlite.*;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup multi threading
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    //Start Scanning Button
    public void serviceStartButton(View view){
                Toast.makeText(this, "Scan started", Toast.LENGTH_SHORT).show();
                startService(new Intent(getBaseContext(), ScanService.class));
    }

    //Stop Scanning Button
    public void serviceStopButton(View view){
        Log.v("Stop Service","Button Pressed");
        stopService(new Intent(getBaseContext(), ScanService.class));
    }



    // Define the variables for the database
    public final String database_name = "storedwifireports.db";
    public final String table_name = "networks";
    public final String column_id = "_id";
    public final String column_timestamp = "timestamp";
    public final String column_ssid = "ssid";
    public final String column_essid = "essid";
    public final String column_security = "security";
    public final int database_version = 1;


    class databaseHelper extends SQLiteOpenHelper {
        databaseHelper(Context context)
        {
            super(context, database_name, null, database_version);
            Log.v("Database","Database grabbed Success!");
        }

        public void onCreate(SQLiteDatabase db)
        {
            try
            {
                db.execSQL("create table networks (_id integer primary_key autoincrement, timestamp varchar(255), ssid varchar(255), essid varchar(255), security varchar(255));");
                Log.v("Database","Database Created Success!");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            try
            {
                db.execSQL("drop table if exists networks");
                onCreate(db);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
