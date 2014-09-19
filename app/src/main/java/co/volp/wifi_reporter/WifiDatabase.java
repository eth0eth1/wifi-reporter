package co.volp.wifi_reporter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Niros on 19/09/2014.
 */
public class WifiDatabase {
    ///
    /// DATABASE STUFF
    ///
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
            Log.v("Database", "Database grabbed Success!");
            WifiScan Scan = new WifiScan();
            Scan.wifiScan();
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
