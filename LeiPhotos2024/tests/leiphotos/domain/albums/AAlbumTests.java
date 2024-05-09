package leiphotos.domain.albums;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.MockFile;
import leiphotos.domain.core.Photo;
import leiphotos.domain.core.PhotoMetadata;
import leiphotos.domain.facade.IPhoto;

public class AAlbumTests {
    private static IPhoto photo;
    private static IPhoto photo2;
    @BeforeAll
    public static void init(){
        File file = new MockFile("path", 10);
        File file2 = new MockFile("path2", 10);
        LocalDateTime date = LocalDateTime.of(2024, 3, 24, 20, 35, 0);
        PhotoMetadata data = new PhotoMetadata(date, "camera", "manufacturer", null);
        photo = new Photo("title",date, data, file);
        photo2 = new Photo("title2",date, data, file2);
    }
    @Test
    public void numberOfPhotosTest(){
        MainLibrary lib = new MainLibrary();
        List<IPhoto> list = new ArrayList<>();
        list.add(photo);
        list.add(photo2);
        AAlbum album = new AAlbum("album",lib,list) {
        };
        assertEquals(album.numberOfPhotos(),2);
    }
    @Test
    public void addPhotoTest(){
        MainLibrary lib = new MainLibrary();
        AAlbum album = new AAlbum("album",lib) {
        };
        boolean added = album.addPhoto(photo);
        assertTrue(added);
        assertEquals(album.numberOfPhotos(),1);
        assertEquals(album.getPhotos().get(0),photo); 
    }
    @Test
    public void removePhotoTest(){
        MainLibrary lib = new MainLibrary();
        AAlbum album = new AAlbum("album",lib) {
        };
        album.addPhoto(photo);
        boolean success = album.removePhoto(photo);
        assertTrue(success);
        assertEquals(album.numberOfPhotos(),0);
    }
    @Test
    public void getNameTest(){
        MainLibrary lib = new MainLibrary();
        String name = "album";
        AAlbum album = new AAlbum(name,lib) {
        };
        assertEquals(album.getName(),name);
    }
    @Test
    public void getPhotosTest(){
        MainLibrary lib = new MainLibrary();
        List<IPhoto> list = new ArrayList<>();
        list.add(photo);
        list.add(photo2);
        AAlbum album = new AAlbum("album",lib,list) {
        };
        List<IPhoto> photos = album.getPhotos();
        assertNotEquals(photos,null);
        assertEquals(photos.size(),list.size());
        for(IPhoto p : list){
            photos.contains(p);
        }
    }
    @Test
    public void addPhotosTest(){
        MainLibrary lib = new MainLibrary();
        AAlbum album = new AAlbum("album",lib) {};
        Set<IPhoto> set = new HashSet<>();
        set.add(photo);
        set.add(photo2);
        assertTrue(album.addPhotos(set));
        assertEquals(album.numberOfPhotos(),2);
        List<IPhoto> photos = album.getPhotos();
        assertTrue(photos.contains(photo));
        assertTrue(photos.contains(photo2));
    }
    @Test
    public void addExistingPhotosTest(){
        MainLibrary lib = new MainLibrary();
        AAlbum album = new AAlbum("album",lib) {};
        Set<IPhoto> set = new HashSet<>();
        assertFalse(album.addPhotos(set));
        set.add(photo);
        set.add(photo2);
        assertTrue(album.addPhotos(set));
        assertEquals(album.numberOfPhotos(),2);
        assertFalse(album.addPhotos(set));
        List<IPhoto> photos = album.getPhotos();
        assertTrue(photos.contains(photo));
        assertTrue(photos.contains(photo2));
    }
    @Test
    public void removePhotosTest(){
        MainLibrary lib = new MainLibrary();
        AAlbum album = new AAlbum("album",lib) {
        };
        Set<IPhoto> set = new HashSet<>();
        set.add(photo);
        set.add(photo2);
        album.addPhotos(set);
        assertTrue(album.removePhotos(set));
        assertEquals(album.numberOfPhotos(),0);
    }
    @Test
    public void processEventTest(){
        MainLibrary lib = new MainLibrary();
        AAlbum album = new AAlbum("album",lib) {
        };
        lib.addPhoto(photo);
        album.addPhoto(photo);
        assertEquals(album.numberOfPhotos(),1);
        lib.deletePhoto(photo);
        assertEquals(album.numberOfPhotos(),0);
    }
}
