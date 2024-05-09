package leiphotos.domain.core;

import java.time.LocalDateTime;
import java.util.Arrays;

import leiphotos.utils.RegExpMatchable;
import leiphotos.utils.Utils;

public record PhotoMetadata(
    LocalDateTime dateOfCapture,
    String camera,
    String manufacturer,
    GPSLocation location
) implements RegExpMatchable{
    /**
     * Creates a PhotoMetaData object with the given
     * date,camera,manufacturer and location.
     * @param dateOfCapture The date and time when the photo was taken
     * @param camera The camera which took the photo
     * @param manufacturer The manufacturer
     * @param location The location where the photo was taken
     * @requires {@code dateOfCapture != null} , 
     * @ensures {@code camera != null && manufacturer != null}.
     * If the given camera is null, the camera's value will be "".
     * If the given manufacturer is null, the camera's value will be "". 
     * Note: If the dateOfCapture or location is changed on the client side, 
     * it will also be changed in this class.This class is not responsible
     * if this situation were to occurr.
     */
    public PhotoMetadata(LocalDateTime dateOfCapture,String camera,String manufacturer,GPSLocation location){
        this.dateOfCapture = dateOfCapture;
        this.camera = camera == null ? "" : camera;
        this.manufacturer = manufacturer == null ? "" : manufacturer;
        this.location = location;
    }
    /**
     * Creates a PhotoMetaData object with
     * the dateOfCapture,camera and manufacturer set
     * as null
     *  
     * @param location The location where the photo was taken
     * @requires {@code dateOfCapture != null} , 
     * @ensures  @ensures {@code camera != null && manufacturer != null}.
     * If the given camera is null, the camera's value will be "".
     * If the given manufacturer is null, the camera's value will be "".
     * Note: If location is changed on the client side, it will also be changed
     * in this class. This class is not responsible if this were to occurr
     */
    protected PhotoMetadata(LocalDateTime dateOfCapture,GPSLocation location){
        this(dateOfCapture,null,null,location);
    }
    @Override
    public boolean matches(String regexp) {
        if(location != null && location.matches(regexp)){
            return true;
        }
        return dateOfCapture.toString().matches(regexp) || camera.matches(regexp) || manufacturer.matches(regexp);
    }
    @Override
    public String toString(){
        String[] arr = new String[4];
        arr[0] = location == null ? "No Location" : location.toString();
        arr[1] = Utils.formatString(dateOfCapture.toString(),"....-..-.. ..:..");
        arr[2] = camera;
        arr[3] = manufacturer;
        return Arrays.toString(arr);
    }
}
