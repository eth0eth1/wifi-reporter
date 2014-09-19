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
import android.os.Looper;
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

    public int onStartCommand(Intent intent, int flags, int startId) {
//        Start Service
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.v("Service", "Starting Service");



            }

        }).start();

        return START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }


    public void sendEmail(String email_subject, String email_body) {
        SendMail m;
        //m = new SendMail("vumx@outlook.com", "ZzTEBiUxcURMEAc9");
        m = new SendMail();
        String[] toArr = {"9212227@gmail.com"}; // This is an array, you can add more emails, just separate them with a coma
        m.setTo(toArr); // load array to setTo function
        //m.setFrom("zemix3@yahoo.com"); // who is sending the email
        m.setSubject(email_subject);
        m.setBody(email_body);

        try {
            //m.addAttachment("/sdcard/myPicture.jpg");  // path to file you want to attach
            if (m.send()) {
                // success
                //Toast.makeText(MainActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                Log.v("Background Email", "Email Sent");
            } else {
                // failure
                //Toast.makeText(MainActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                Log.v("Background Email", "Email Failed");
            }
        } catch (Exception e) {
            // some other problem
            //Toast.makeText(MainActivity.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
            Log.v("Background Email", "Email Failed in a worse way");
            e.printStackTrace();
        }

    }

}