package leiphotos.domain.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * This class tests the GPSLaction class
 */
public class GPSLocationTests {
    @ParameterizedTest(name = "Latitude: {0} Longitude: {1}")
    @CsvSource({
        "1.0,2.0",
        "2.1,3.5",
        "-50.13,-21.29"
    })
    public void constructorTest(double latitude,double longitude){
        GPSLocation location = new GPSLocation(latitude,longitude,"desc");
        final double errorMargin = 0.000001;
        assertEquals("Expected: " + latitude + " Result: " + location.latitude(),latitude,location.latitude(),errorMargin);
        assertEquals("Expected: " + longitude + " Result: " + location.longitude(),longitude,location.longitude(),errorMargin);
        assertEquals(location.description(),"desc");
    }
    @ParameterizedTest(name = "Lat: {0} Long: {1} regex: {2}")
    @CsvSource({
        "19.4,-21.3,abc,.[abc].",
        "21.3,41.4,Lisbon,.*isb.*",
        "10.1,200.402,Madrid,Mad.*"
    })
    public void expressionMatches(double latitude,double longitude,String description,String regex){
        GPSLocation location = new GPSLocation(latitude, longitude,description);
        assertTrue(location.matches(regex));
    }
    @ParameterizedTest(name = "Lat: {0} Long: {1} regex: {2}")
    @CsvSource({
        "19.4,-21.3,a,.*19.5*",
        "21.3,41.4,bcd,.*La:21.3.*",
        "10.1,200.402,,.*Lon:200[.].*"
    })
    public void expressionDoesNotMatch(double latitude,double longitude,String description,String regex){
        GPSLocation location = new GPSLocation(latitude, longitude,description);
        assertFalse(location.matches(regex));
    }
}
