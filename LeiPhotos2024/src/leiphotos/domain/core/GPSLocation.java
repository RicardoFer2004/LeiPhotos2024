package leiphotos.domain.core;

import leiphotos.domain.facade.GPSCoordinates;
import leiphotos.utils.RegExpMatchable;
/**
 * This record represents a gps location
 * Since it is a record, once an object of this
 * type is created, all its atributes are immutable
 */
public record GPSLocation(
    double latitude,
    double longitude,
    String description
)implements GPSCoordinates,RegExpMatchable{
    /**
     * Creates a GPSLocation object with the specified
     * coordinates and a description
     * @param latitude The latitude
     * @param longitude The longitude
     * @param description The description
     * @ensures {@code description != null}
     */
    public GPSLocation(double latitude,double longitude,String description){
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description == null ? "" : description;
    }
    /**
     * Creates a GPSLocation object with the specified
     * coordinates and no description
     * @param latitude The latitude
     * @param longitude The longitude
     * @param description The description
     * @ensures {@code description == ""}
     */
    public GPSLocation(double latitude,double longitude){
        this(latitude,longitude,"");
    }
    @Override
    public boolean matches(String regex) {
        return description.matches(regex);
    }
    @Override
    public String toString(){
        String lat = String.format("%.2f",this.latitude);
        String longi = String.format("%.2f",this.longitude);
        return "{Lat:" + lat + " Long:" + longi + " Desc:" + description + "}";
    }
}
