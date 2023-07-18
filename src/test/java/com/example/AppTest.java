package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.google.maps.model.LatLng;
import org.junit.Test;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void locationTest() {
        Locate locate = new Locate("London");
        LatLng coordinates = locate.getCoordinates();
        double latitude = coordinates.lat;
        double longitude = coordinates.lng;
        assertEquals(latitude, 51.5072178, 0.01);
        assertEquals(longitude, -0.1275862, 0.01);

    }

    @Test
    public void beachLocationTest() {
        LatLng userLatLng = new LatLng(50.71521266262516, -1.8743369443723459);
        Map<String, Object> closestBeach = Locate.closestBeach(userLatLng);
        String beachname = closestBeach.get("closestBeachName").toString();
        assertEquals(beachname, "Bournemouth");
    }

    @Test
    public void openMeteoTest() {
        OpenMeteo openMeteo = new OpenMeteo();
        LatLng coordinates = new LatLng(50.719164, -1.875684);
        String APIResponse = openMeteo.openMeteoAPI(coordinates);
        assertTrue(APIResponse.contains("wave_height"));

    }
}
