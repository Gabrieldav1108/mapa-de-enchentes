package com.example.maps2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maps2.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {
    SeekBar seekBar;
    GoogleMap mMap;
    TextView textViewWaterLevel;
    ArrayList<StreetsPolygon> list = new ArrayList<>();

    private Map<StreetsPolygon, Polygon> polygonMap = new HashMap<>();

    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_DARK_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_LIGHT_GREEN_ARGB = 0x8000ff00;
    private static final int COLOR_DARK_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_LIGHT_ORANGE_ARGB = 0x80FFA500;
    private static final int COLOR_LIGHT_RED_ARGB = 0xFFFF6666;
    private static final int COLOR_DARK_RED_ARGB = 0xFF8B0000;
    private static final int COLOR_BLACK_ARGB = 0xff000000;


    private static final int POLYGON_STROKE_WIDTH_PX = 1;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final int PATTERN_GAP_LENGTH_PX = 20;

    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dash.
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);

    double waterLevelStreets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maps2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        seekBar = findViewById(R.id.seekBar);
        textViewWaterLevel = findViewById(R.id.textViewWaterLevel);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                double fVal = getConvertedValue(progress);
                String result = String.format("%.2f", fVal);
                textViewWaterLevel.setText(result+"m");
                waterLevelStreets = fVal;

                List<StreetsStatus> stretsAnalyzeds = checkWaterLevelOnStreets(list, fVal);

                for(StreetsStatus status : stretsAnalyzeds){
                    StreetsPolygon street = status.getStreetsPolygon();
                    Polygon polygon = polygonMap.get(street);
                    if (polygon != null) {
                        switch (status.getWaterLevelOnStreets()) {
                            case LOW:
                                polygon.setFillColor(Color.argb(128, 0, 255, 0));
                                break;
                            case MEDIUM:
                                polygon.setFillColor(Color.argb(128, 255, 255, 0));
                                break;
                            case HIGH:
                                polygon.setFillColor(Color.argb(128, 255, 0, 0));
                                break;
                        }
                    }
                }
            }
            //

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        loadData();
        //abrir em rolante
        LatLng rolante = new LatLng(-29.64721, -50.57048);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rolante, 14));

        createPolygonsOnMap(googleMap);
    }

    //metodo para converter o progresso do seekBar para decimal
    public double getConvertedValue(int intVal){
        double floatVal = 0.0;
        floatVal = .10f * intVal;
        return floatVal;
    }

    //metodo para colocar as coordenadas que serao utilizadas pra criar os poligonos no mapa e o valor maximo de agua que a rua atinge
    public void loadData(){
        StreetsPolygon p1 = new StreetsPolygon(
                -29.661164,
                -50.593472,
                -29.660169,
                -50.59330,
                -29.660027,
                -50.594399,
                -29.660998,
                -50.594753,
                4.3
        );
        StreetsPolygon p2 = new StreetsPolygon(
                -29.66019,
                -50.59324,
                -29.66029,
                -50.59260,
                -29.66114,
                -50.59284,
                -29.66143,
                -50.59342,
                2
        );
        StreetsPolygon p3 = new StreetsPolygon(
                -29.66031,
                -50.59250,
                -29.66037,
                -50.59197,
                -29.66135,
                -50.59211,
                -29.66133,
                -50.59268,
                9
        );

        list.add(p1);
        list.add(p2);
        list.add(p3);
    }

    //faz a verificacao do nivel da agua do rio comparada ao nivel maximo de agua na rua e retorna uma nova lista com as ruas atingidas.
    private List<StreetsStatus> checkWaterLevelOnStreets(List<StreetsPolygon> polygons, double riverWaterLevel){
        List<StreetsStatus> result = new ArrayList<>();
        for(StreetsPolygon polygon : polygons){

            if(polygon.getWaterLevel() < riverWaterLevel){

                double difference = riverWaterLevel - polygon.getWaterLevel();
                WaterLevelOnStreets waterLevel;

                if(difference > 5){
                    waterLevel = WaterLevelOnStreets.HIGH;
                }else if( difference <= 5 && difference >= 3){
                    waterLevel = WaterLevelOnStreets.MEDIUM;
                }else{
                    waterLevel = WaterLevelOnStreets.LOW;
                }

                result.add(new StreetsStatus(polygon, waterLevel));
            }
        }
        return result;
    }

    //metodo que cria os poligonos no mapa
    private void createPolygonsOnMap(GoogleMap googleMap){
        for (StreetsPolygon p: list){
            Polygon polygon = googleMap.addPolygon(new PolygonOptions().add(
                    new LatLng(p.getFirstPointLat(), p.getFirstPointLng()),
                    new LatLng(p.getSecondPointLat(), p.getSecondPointLng()),
                    new LatLng(p.getThirdPointLat(), p.getThirdPointLng()),
                    new LatLng(p.getFourthPointLat(), p.getFourthPointLng())
            ).strokeWidth(1)
            );

            polygonMap.put(p, polygon);
        }
    }

    /**
     * Styles the polygon, based on type.
     * @param polygon The polygon object that needs styling.
     */
    private void stylePolygon(Polygon polygon) {
        String type = "";
        // Get the data object stored with the polygon.
        if (polygon.getTag() != null) {
            type = polygon.getTag().toString();
        }else {
            type = "empty";
        }

        List<PatternItem> pattern = null;
        int strokeColor = COLOR_BLACK_ARGB;
        int fillColor = COLOR_WHITE_ARGB;

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "empty":
                pattern = PATTERN_POLYGON_BETA;
                break;
            case "LOW":
                // Apply a stroke pattern to render a dashed line, and define colors.
                pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_DARK_GREEN_ARGB;
                fillColor = COLOR_LIGHT_GREEN_ARGB;
                break;
            case "MEDIUM":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_BETA;
                strokeColor = COLOR_DARK_ORANGE_ARGB;
                fillColor = COLOR_LIGHT_ORANGE_ARGB;
                break;
            case "HIGH":
                pattern = PATTERN_POLYGON_BETA;
                strokeColor = COLOR_DARK_RED_ARGB;
                fillColor = COLOR_LIGHT_RED_ARGB;
                break;
        }

        //polygon.setStrokePattern(pattern);
        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
        polygon.setStrokeColor(strokeColor);
        polygon.setFillColor(fillColor);
    }
}