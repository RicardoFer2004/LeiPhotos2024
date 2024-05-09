package leiphotos.domain.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PhotoMetaDataTests {
    private static LocalDateTime date;
    @BeforeAll
    public static void init(){
        date = LocalDateTime.of(2024, 3, 21, 11, 10, 0);
    }
    @Test
    public void constructorTestNonNullTypes(){
        String camera = "camera";
        String manufacturer = "manufacturer";
        GPSLocation location = new GPSLocation(20.1,30.5);
        PhotoMetadata metaData = new PhotoMetadata(date, camera, manufacturer, location);
        assertEquals(date,metaData.dateOfCapture());
        assertEquals(camera,metaData.camera());
        assertEquals(manufacturer,metaData.manufacturer());
        assertEquals(location,metaData.location());
    }
    @Test
    public void constructorTestWithNullTypes(){
        GPSLocation location = new GPSLocation(20.1,30.5);
        PhotoMetadata metaData = new PhotoMetadata(date,location);
        assertEquals(date,metaData.dateOfCapture());
        assertEquals("",metaData.camera());
        assertEquals("",metaData.manufacturer());
        assertEquals(location,metaData.location());
        System.out.println(metaData.toString());
    }
    @Test
    public void matchesCamera(){
        String camera = "camera";
        PhotoMetadata metaData = new PhotoMetadata(date, camera, null, null);
        String regex = "cam.*";
        assertTrue(metaData.matches(regex));
    }
    @Test
    public void matchesManufacturer(){
        String manufacturer = "manufacturer";
        PhotoMetadata metaData = new PhotoMetadata(date, null, manufacturer, null);
        String regex = "manu.*";
        assertTrue(metaData.matches(regex));
    }
    @Test
    public void matchesCapturedDate(){
        LocalDateTime d = LocalDateTime.of(2024, 3, 23, 3, 0, 0);
        PhotoMetadata metaData = new PhotoMetadata(d, null, null, null);
        String regex = "2024-.*";
        assertTrue(metaData.matches(regex));
    }
    @Test
    public void matcheLocation(){
        GPSLocation location = new GPSLocation(0, 0,"Lisbon");
        PhotoMetadata metaData = new PhotoMetadata(date, null, null, location);
        String regex = "Lis.*";
        assertTrue(metaData.matches(regex));
    }
}
