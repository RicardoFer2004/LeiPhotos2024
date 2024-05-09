package leiphotos.domain.metadatareader;

import java.io.File;
import java.io.FileNotFoundException;
/**
 * This class's represents a factory of JpegMetadataReader objects
 */
public enum JpegMetadataReaderFactory {
    INSTANCE;
    /**
     * Creates a JpegMetadataReader instance with the specified file
     * @param file The Jpeg image
     * @requires {@code file != null}
     * @return A JpegMetadataReader object
     * @throws FileNotFoundException If the file does not exist
     * @throws JpegMetaDataException If any exception ,except FileNotFoundException, arises
     * @ensures \return != null
     */
    public JpegMetadataReader createMetadataReader(File file) throws FileNotFoundException,JpegMetadataException{
        return new JavaXTMetadataReaderAdapter(file);
    }
}
