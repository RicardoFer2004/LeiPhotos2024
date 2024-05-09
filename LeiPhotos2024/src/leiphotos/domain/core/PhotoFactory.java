package leiphotos.domain.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import leiphotos.domain.metadatareader.JpegMetadataException;
import leiphotos.domain.metadatareader.JpegMetadataReader;
import leiphotos.domain.metadatareader.JpegMetadataReaderFactory;

public enum PhotoFactory {
    INSTANCE;
    /**
     * Creates a Photo object
     * @param title The title of the photo
     * @param pathToPhotoFile The path to the file
     * @return A photo object if the operation was successful. Returns null otherwise
     * @throws FileNotFoundException If the specified path does not point to any file
     */
    public Photo createPhoto(String title, String pathToPhotoFile) throws FileNotFoundException {
        File file = new File(pathToPhotoFile);
        JpegMetadataReader reader;
        try{
            reader = JpegMetadataReaderFactory.INSTANCE.createMetadataReader(file);
        }catch(JpegMetadataException e){
            return null;
        }
        PhotoMetadata metadata = convertToMetadata(reader);
        
        return new Photo(title, LocalDateTime.now(),metadata, file);
    }
    /**
     * Copies the reader's data onto a new PhotoMetadata object
     * The state of the reader's attributes are not changed whatsoever 
     * @param reader The metadata reader
     * @return A PhotoMetadata reader object if the operation was successful.Null otherwise.
     */
    private PhotoMetadata convertToMetadata(JpegMetadataReader reader){
        String camera = reader.getCamera();
        String manufacturer = reader.getManufacturer();
        LocalDateTime dateOfCapture = reader.getDate() != null ? reader.getDate() : LocalDateTime.of(1970, 1, 1, 0, 0, 0);
        double[] location = reader.getGpsLocation();
        GPSLocation gpsLocation = null;
        if(location != null){
            gpsLocation = new GPSLocation(location[1],location[0]);
        }
        
        return new PhotoMetadata(dateOfCapture,camera,manufacturer,gpsLocation);
    }
}
