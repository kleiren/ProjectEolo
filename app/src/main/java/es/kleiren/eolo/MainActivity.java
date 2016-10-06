package es.kleiren.eolo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private MyLocationNewOverlay mLocationOverlay;

    private Context context;
    private GeoPoint currentLocation;
    private LocationManager locationManager;
    private CompassOverlay mCompassOverlay;

    private Button btn_forceBattle;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        btn_forceBattle = (Button) findViewById(R.id.button);

        btn_forceBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Battle.class);
                startActivity(intent);
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //important! set your user agent to prevent getting banned from the osm servers
        org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);

        MapView map = (MapView) findViewById(R.id.map);
//        map.setTileSource(TileSourceFactory.MAPNIK);

       // GpsMyLocationProvider me = new GpsMyLocationProvider(this);


        this.mLocationOverlay = new MyLocationNewOverlay( new GpsMyLocationProvider(context),map);
        map.getOverlays().add(this.mLocationOverlay);

        this.mCompassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), map);
        map.getOverlays().add(this.mCompassOverlay);






        // lon = -3.7678304314613342
        // lat = 40.33183226746575


     //   Drawable marker = getDrawable(R.drawable.center);
      //  ItemizedIconOverlay myItemizedOverlay = new ItemizedIconOverlay();


       // myItemizedOverlay.addItem(myPoint1, "myPoint1", "myPoint1");

        final IMapController mapController = map.getController();
        mapController.setZoom(19);
        map.setTilesScaledToDpi(true);

        mLocationOverlay.enableMyLocation();

        mCompassOverlay.enableCompass();
        mapController.animateTo(new GeoPoint(40.33183226746575, -3.7678304314613342));

        mLocationOverlay.enableFollowLocation();
        mLocationOverlay.runOnFirstFix(new Runnable(){
            public void run() {
                mapController.animateTo(mLocationOverlay
                        .getMyLocation());
            }
        });

/*
        //your items
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

//the overlay
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        return true;
                    }
                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, context);
        mOverlay.setFocusItemsOnTap(true);

        map.getOverlays().add(mOverlay);*/
    }
}
