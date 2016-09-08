package com.forecast.weather.activities;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.forecast.weather.R;

/**
 * Created by Ilanthirayan on 7/9/16.
 */
public class MapActivity extends AppCompatActivity {

    ImageButton map_rain;
    ImageButton map_wind;
    ImageButton map_temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        map_rain = (ImageButton) findViewById(R.id.map_rain);
        map_wind = (ImageButton) findViewById(R.id.map_wind);
        map_temperature = (ImageButton) findViewById(R.id.map_temperature);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String apiKey = sp.getString("apiKey", getResources().getString(R.string.apiKey));

        final WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/map.html?lat=" + prefs.getFloat("latitude", 0) + "&lon=" + prefs.getFloat("longitude", 0) + "&appid=" + apiKey);

        map_rain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:map.removeLayer(windLayer);map.removeLayer(tempLayer);map.addLayer(rainLayer);");
                map_rain.setImageDrawable(getTintedDrawable(R.drawable.ic_water_grey600_24dp, R.color.colorPrimary));
                map_wind.setImageDrawable(getTintedDrawable(R.drawable.ic_weather_windy_grey600_24dp, android.R.color.darker_gray));
                map_temperature.setImageDrawable(getTintedDrawable(R.drawable.ic_thermometer_grey600_24dp, android.R.color.darker_gray));
            }
        });

        map_wind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:map.removeLayer(rainLayer);map.removeLayer(tempLayer);map.addLayer(windLayer);");
                map_rain.setImageDrawable(getTintedDrawable(R.drawable.ic_water_grey600_24dp, android.R.color.darker_gray));
                map_wind.setImageDrawable(getTintedDrawable(R.drawable.ic_weather_windy_grey600_24dp, R.color.colorPrimary));
                map_temperature.setImageDrawable(getTintedDrawable(R.drawable.ic_thermometer_grey600_24dp, android.R.color.darker_gray));
            }
        });

        map_temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:map.removeLayer(windLayer);map.removeLayer(rainLayer);map.addLayer(tempLayer);");
                map_rain.setImageDrawable(getTintedDrawable(R.drawable.ic_water_grey600_24dp, android.R.color.darker_gray));
                map_wind.setImageDrawable(getTintedDrawable(R.drawable.ic_weather_windy_grey600_24dp, android.R.color.darker_gray));
                map_temperature.setImageDrawable(getTintedDrawable(R.drawable.ic_thermometer_grey600_24dp, R.color.colorPrimary));
            }
        });

    }


    public Drawable getTintedDrawable(@DrawableRes int drawableResId, @ColorRes int colorResId) {
        Drawable drawable = getResources().getDrawable(drawableResId);
        int color = getResources().getColor(colorResId);
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return drawable;
    }
}
