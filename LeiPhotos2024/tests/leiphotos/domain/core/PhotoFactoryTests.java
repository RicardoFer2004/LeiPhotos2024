package leiphotos.domain.core;

import static org.junit.Assert.assertThrows;

import java.io.FileNotFoundException;

import org.junit.Test;


public class PhotoFactoryTests {
    @Test
    public void createPhotoFileNotFoundExceptionTest(){
        String title = "title";
        String path = "Path";
        assertThrows(FileNotFoundException.class,() -> PhotoFactory.INSTANCE.createPhoto(title,path));
    }
}
