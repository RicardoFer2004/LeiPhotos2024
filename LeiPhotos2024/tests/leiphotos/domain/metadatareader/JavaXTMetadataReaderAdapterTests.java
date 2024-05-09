package leiphotos.domain.metadatareader;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import org.junit.Test;

public class JavaXTMetadataReaderAdapterTests {
    @Test
    public void constructorTest_NonNullReturn() throws Exception{
        File file = getDummyFile1();
        try{
            JpegMetadataReader reader = new JavaXTMetadataReaderAdapter(file);
            assertFalse(reader == null);
        }catch(Exception e){
            throw e;
        }
    }
    @Test
    public void constructorTest_NonexistentFile(){
        File nonexistentFile = new File("");
        assertThrows(FileNotFoundException.class,
        () -> new JavaXTMetadataReaderAdapter(nonexistentFile));
    }
    @Test
    public void getDateTest_NonValidDate() throws FileNotFoundException,JpegMetadataException{
        File file = getDummyFile1();
        JpegMetadataReader reader = new JavaXTMetadataReaderAdapter(file);
        LocalDateTime date = reader.getDate();
        assertEquals(date,null);
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
