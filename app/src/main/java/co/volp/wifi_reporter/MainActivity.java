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
import java.util.List;
import java.util.concurrent.BlockingDeque;
import javax.mail.MessagingException;
import android.database.sqlite.*;


public class MainActivity extends Activity {

    private String wifiNetworks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void wifiScan(View view) {
        WifiManager mainWifiObj;
        mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        WifiScanReceiver wifiReceiver = new WifiScanReceiver();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        List<ScanResult> wifiScanList = mainWifiObj.getScanResults();

        wifiNetworks = wifiScanList.toString();

        for (ScanResult result: wifiScanList){

            Log.v("Scan Results",result.SSID.toString()+","+result.BSSID.toString());
        }


    }

    class WifiScanReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
        }
    }


    public void sendEmail(View view) {
        Mail m;
        //m = new Mail("vumx@outlook.com", "ZzTEBiUxcURMEAc9");
        m = new Mail();
        String[] toArr = {"9212227@gmail.com"}; // This is an array, you can add more emails, just separate them with a coma
        m.setTo(toArr); // load array to setTo function
        m.setFrom("zemix3@yahoo.com"); // who is sending the email
        m.setSubject("Wifi Scan Results");
        m.setBody(wifiNetworks);

        try {
            //m.addAttachment("/sdcard/myPicture.jpg");  // path to file you want to attach
            if(m.send()) {
                // success
                Toast.makeText(MainActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
            } else {
                // failure
                Toast.makeText(MainActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
            }
        } catch(Exception e) {
            // some other problem
            Toast.makeText(MainActivity.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

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
        }

        public void onCreate(SQLiteDatabase db)
        {

        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {

        }
    }

}
