package es.kleiren.eolo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements LocationListener {

    private MyLocationNewOverlay mLocationOverlay;
    private Context context;
    private LocationManager locationManager;
    private CompassOverlay mCompassOverlay;
    private MapView map;
    private ItemizedOverlayWithFocus<OverlayItem> mOverlay;
    private Button btn_forceBattle;
    private IMapController mapController;
    private GeoPoint monsterLocation;
    private GeoPoint currentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        btn_forceBattle = (Button) findViewById(R.id.button);

        btn_forceBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBattle();
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100,
                this);

        //Important! set your user agent to prevent getting banned from the osm servers
        org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);

        // Map definition
        map = (MapView) findViewById(R.id.map);

        this.mLocationOverlay = new MyLocationNewOverlay( new GpsMyLocationProvider(context),map);
        map.getOverlays().add(this.mLocationOverlay);

        this.mCompassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), map);
        map.getOverlays().add(this.mCompassOverlay);

        // lon = -3.7678304314613342
        // lat = 40.33183226746575


        mapController = map.getController();
        mapController.setZoom(19);
        map.setTilesScaledToDpi(true);
        mLocationOverlay.enableMyLocation();
        mapController.stopPanning();
        mCompassOverlay.enableCompass();
        mapController.animateTo(new GeoPoint(40.33183226746575, -3.7678304314613342));
    }

    public void addMonsters(GeoPoint currentLocation){
        //your items
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        Drawable newMarker = this.getResources().getDrawable(R.drawable.ic_asrobot_pix);
        monsterLocation = getLocation(currentLocation, 100);
        OverlayItem monster = new OverlayItem("ASROBOT", "Hold to fight", monsterLocation);
        monster.setMarker(newMarker);

        items.add(monster);

        //the overlay
        mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        
                        if (mLocationOverlay.getMyLocation().distanceTo(monsterLocation) < 50)
                        startBattle();
                        else
                            Toast.makeText(context, "You are too far away! Get near", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, context);


        map.getOverlays().add(mOverlay);


    }

    public void startBattle(){
        mOverlay.removeAllItems();
        Intent intent = new Intent(getApplicationContext(), Battle.class);
        startActivityForResult(intent, 1);
    }


    public static GeoPoint getLocation(GeoPoint location,  int radius) {
        GeoPoint result = new GeoPoint(location);
        Random random = new Random();

        // Convert radius from meters to degrees
        double radiusInDegrees = radius / 111000f;

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Adjust the x-coordinate for the shrinking of the east-west distances
        double new_x = x / Math.cos(location.getLatitude());

        result.setLongitude(new_x + location.getLongitude());
        result.setLatitude(y + location.getLatitude());
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mapController.animateTo(currentLocation);
        addMonsters(currentLocation);
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = new GeoPoint(location);
        mapController.animateTo(currentLocation);
        mapController.setCenter(currentLocation);
        if (monsterLocation != null && mLocationOverlay.getMyLocation().distanceTo(monsterLocation) > 150){
            if (mOverlay.isEnabled())  mOverlay.removeAllItems();
            addMonsters(currentLocation);
        } else if (monsterLocation == null){
            addMonsters(currentLocation);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
