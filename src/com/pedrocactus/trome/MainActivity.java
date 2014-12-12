
package com.pedrocactus.trome;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.pedrocactus.trome.DaoMaster.DevOpenHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity implements LocationListener {

    private TextView coos;
    private TextView stationCoos;
    private TextView stationName;
    private TextView stationCorrespondances;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private StationDao stationDao;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coos = (TextView) findViewById(R.id.gps_coos);
        stationCoos = (TextView) findViewById(R.id.station_coos);
        stationName = (TextView) findViewById(R.id.station_name);
        stationCorrespondances = (TextView) findViewById(R.id.station_corres);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean isIniated = Boolean.valueOf(TromeApp.getInstance().getPreferences("init"));
        if(!isIniated){
        try {
            InputStream is;
            is = getAssets().open("raw_data.csv");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line = "";
            String tableName = "STATION";
            String columns = "_id,name,type,latitude,longitude";
            String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
            String str2 = ");";

            DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "station-db", null);
            db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
            stationDao = daoSession.getNoteDao();

            db.beginTransaction();
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split("#");
                sb.append(str[0] + ",'");
                sb.append(str[3] + ",'");
                sb.append(str[5] + "',");
                sb.append(str[2] + ",");
                sb.append(str[1]);
                sb.append(str2);
                db.execSQL(sb.toString());
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            TromeApp.getInstance().setPreferences("init","true");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        coos.setText("User location is : latitude:" + location.getLatitude() + " longitude : "
                + location.getLongitude());

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
}
