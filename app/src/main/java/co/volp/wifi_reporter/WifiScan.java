package co.volp.wifi_reporter;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

/**
 * Created by Niros on 19/09/2014.
 */
public class WifiScan extends Service{

    public WifiScan() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("Service", "IBinder Called");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String wifiNetworks;

    public List<ScanResult> wifiScan() {

        WifiManager mainWifiObj;
        mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiScanReceiver wifiReceiver = new WifiScanReceiver();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        //Pull scan into List object
        List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
        //Loop through List and add to string
        String email = null;
        email = "<table style=\"width:100%\">";
        for (ScanResult result : wifiScanList) {
            Log.v("Scan Results", result.SSID.toString() + "," + result.BSSID.toString());
            email += "<tr>" + "<td>" + result.SSID.toString() + "</td>" + "<td>" + result.BSSID.toString() + "</td>" + "<td>" + result.capabilities.toString() + "</td>" + "</tr>";
        }
        email += "</table>";
        Log.v("Email", email);
        //Launch Database
        //databaseHelper wifidatabase = new databaseHelper(getApplicationContext());

        wifiNetworks = wifiScanList.toString();
        //sendEmail("Wifi Networks", email);
        return wifiScanList;
    }

    class WifiScanReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
        }
    }
}
