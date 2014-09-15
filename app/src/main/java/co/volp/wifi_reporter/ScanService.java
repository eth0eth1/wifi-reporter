package co.volp.wifi_reporter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ScanService extends Service {
    public ScanService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("Service", "IBinder Called");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("Service", "Service Created");
    }
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        //Start Service
        Log.v("Service", "Starting Service");
        Toast.makeText(this,"Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this,"Service Stopped", Toast.LENGTH_LONG).show();
    }
}
