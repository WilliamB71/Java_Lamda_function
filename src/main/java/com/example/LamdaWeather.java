package com.example;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.maps.model.LatLng;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LamdaWeather implements RequestHandler<String, String> {

    @Override
    public String handleRequest(String location, Context context) {
        try {
            Locate locate = new Locate(location);
            OpenMeteo openMeteo = new OpenMeteo();
            LatLng coordinates = locate.getCoordinates();
            Map<String, Object> closestBeach = Locate.closestBeach(coordinates);

            LatLng beachLatLng = (LatLng) closestBeach.get("closestBeachLatLng");
            String beachName = (String) closestBeach.get("closestBeachName");
            String APIResponse = openMeteo.openMeteoAPI(beachLatLng);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("beachName", beachName);
            jsonObject.addProperty("APIResponse", APIResponse);

            return jsonObject.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error Retrieving Data";
        }
    }
}

// com.example.LamdaWeather::handleRequest