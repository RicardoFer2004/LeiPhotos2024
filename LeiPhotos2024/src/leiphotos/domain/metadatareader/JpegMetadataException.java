package leiphotos.domain.metadatareader;
/**
 * This class is an extension of the Exception class and represents
 * any and all exceptions related to JpegMetaData
 */
public class JpegMetadataException extends Exception{
    /**
     * Creates a JpegMetaDataException instance
     */
    public JpegMetadataException(){
        super();
    }
    /**
     * Creates a JpegMetaDataException instance with the given string as
     * the exception's message
     * 
     * @param message The exception's message
     */
    public JpegMetadataException(String message){
        super(message);
    }
}
