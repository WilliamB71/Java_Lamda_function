package com.example;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Locate {

    private String userinputname;

    public Locate(String user_input) {
        this.userinputname = user_input;
    }

    public LatLng getCoordinates() {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("API_KEY")
                .build();

        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, this.userinputname).await();

            if (results != null && results.length > 0) {
                LatLng latLng = results[0].geometry.location;
                return latLng;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> closestBeach(LatLng userLatLng) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get("BeachnameCoords.json")));
            JSONObject jsonObject = new JSONObject(jsonContent);
            JSONArray dataArray = jsonObject.getJSONArray("Data");


            double closestDistance = Double.POSITIVE_INFINITY;
            LatLng closestBeach = null;
            String closestBeachName = null;

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject locationObject = dataArray.getJSONObject(i);
                String beachName = locationObject.keys().next();
                JSONObject beachlocation = locationObject.getJSONObject(beachName);
                
                double beachLat = beachlocation.getDouble("lat");
                double beachLng = beachlocation.getDouble("lon");
                LatLng beachLatLng = new LatLng(beachLat, beachLng);
            

                if (Math.abs(java.lang.Math.sqrt(Math.pow(userLatLng.lat - beachLat, 2) + Math.pow(userLatLng.lng - beachLng, 2))) < closestDistance) {
                            closestDistance = Math.abs(java.lang.Math.sqrt(Math.pow(userLatLng.lat - beachLat, 2) + Math.pow(userLatLng.lng - beachLng, 2)));
                            closestBeach = beachLatLng;
                            closestBeachName = beachName;
                }
            }
            Map<String, Object> closestBeachMap = new HashMap<>();
            closestBeachMap.put("closestBeachLatLng", closestBeach);
            closestBeachMap.put("closestBeachName", closestBeachName);
            return closestBeachMap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
