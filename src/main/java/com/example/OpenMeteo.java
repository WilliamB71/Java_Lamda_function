package com.example;

import com.google.maps.model.LatLng;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class OpenMeteo {

    public static void main(String args[]) {
    }

    public String openMeteoAPI(LatLng coordinates) {
        String APIPattern = "https://marine-api.open-meteo.com/v1/marine?latitude={0}&longitude={1}&hourly=wave_height,wave_direction,wave_period,wind_wave_height,wind_wave_direction,wind_wave_period,wind_wave_peak_period,swell_wave_height,swell_wave_direction,swell_wave_period,swell_wave_peak_period";
        String requestUrl = APIPattern.replace("{0}", String.valueOf(coordinates.lat)).replace("{1}",
                String.valueOf(coordinates.lng));

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            String JsonResponse = response.toString();
            conn.disconnect();
            return JsonResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error Retrieving Data";
    }
}