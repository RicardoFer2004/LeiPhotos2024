package leiphotos.domain.metadatareader;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import leiphotos.services.JavaXTJpegMetadataReader;
/**
 * This class represents one implementantion of a JpegMetaDataReader
 */
public class JavaXTMetadataReaderAdapter implements JpegMetadataReader{
    private final JavaXTJpegMetadataReader reader;
    /**
     * Creates an JavaXTMetadataReaderAdapter instance with the given file
     * If the file does not exist,a FileNotFoundException will be thrown
     * If any error occurs(i.e. any exception ,except FileNotFoundException, arises),a JpegMetaDataException will be thrown
     * 
     * @param file The Jpeg image
     * @requires {@code file != null}
     * @throws FileNotFoundException If the file does not exist
     * @throws JpegMetaDataException If any exception ,except FileNotFoundException, arises
     */
    public JavaXTMetadataReaderAdapter(File file) throws FileNotFoundException , JpegMetadataException{
        try{
            if(!file.exists()){
                throw new FileNotFoundException("File could not be found");
            }
            reader = new JavaXTJpegMetadataReader(file);
        }catch(FileNotFoundException e){
            throw e;
        }catch(Exception e){
            throw new JpegMetadataException(e.getMessage());
        }
    }
    @Override
    public String getCamera() {
        return reader.getCamara();
    }

    @Override
    public String getManufacturer() {
        return reader.getManufacturer();
    }

    @Override
    public LocalDateTime getDate() {
        String dateString = reader.getDate();
        if(dateString == null){
            return null;
        }
        String[] tokens = dateString.split(" ");
        StringBuilder sb = new StringBuilder();
        sb.append(tokens[0].replace(":","-"));
        sb.append("T");
        sb.append(tokens[1]);
        return LocalDateTime.parse(sb.toString());
    }

    @Override
    public String getAperture() {
        return reader.getAperture();
    }

    @Override
    public double[] getGpsLocation() {
        return reader.getGPS();
    }
    
}
