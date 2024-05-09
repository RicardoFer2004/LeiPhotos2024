package leiphotos.domain.metadatareader;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.*;

/**
 * This class tests the JpegMetadataReaderFactory
 */
public class JpegMetadataReaderFactoryTests {
    @Test
    public void createMetadataReaderTest_NonNullReturn() throws Exception{
        File file = getDummyFile1();
        try{
            JpegMetadataReader reader = JpegMetadataReaderFactory.INSTANCE.createMetadataReader(file);
            assertFalse(reader == null);
        }catch(Exception e){
            throw e;
        }
    }
    @Test
    public void createMetadataReaderTest_NonexistentFile(){
        File nonexistentFile = new File("");
        assertThrows(FileNotFoundException.class,
        () -> JpegMetadataReaderFactory.INSTANCE.createMetadataReader(nonexistentFile));
    }
    private File getDummyFile1(){
        //Might need refactoring
        String path = getClass().getResource("TestFiles" + File.separator + "DummyFile1.txt").getFile();
        path = path.replace("%5c",File.separator);//Necessary since the \ are replaced with %5c
        path = path.replace("%20"," ");//Necessary since the spaces present in a filePath are replaced with %20
        File file = new File(path);
        return file;
    }
}
