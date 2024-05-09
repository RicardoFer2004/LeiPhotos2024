package leiphotos.domain.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import leiphotos.domain.facade.IPhoto;
/**
 * Test class for MainLibrary class
 */
public class MainLibraryTests {
    private Photo photo;
    @BeforeAll
    public void init(){
        File file = new MockFile("Path", 10);
        photo = new Photo(file);
    }
    @Test
    public void emptyConstructorTest(){
        MainLibrary lib = new MainLibrary();
        assertEquals(lib.getNumberOfPhotos(),0);
    }
    @Test
    public void collectionConstructorTest(){
        Collection<IPhoto> photos = new ArrayList<>();
        photos.add(photo);
        MainLibrary lib = new MainLibrary(photos);
        assertEquals(lib.getNumberOfPhotos(),1);
    }
    @Test
    public void addPhotoTest(){
        MainLibrary lib = new MainLibrary();
        boolean success = lib.addPhoto(photo);
        assertTrue(success);
    }
    @Test
    public void deletePhotoTest(){
        MainLibrary lib = new MainLibrary();
        lib.addPhoto(photo);
        boolean success = lib.deletePhoto(photo);
        assertTrue(success);
    }
    @Test
    public void getNumberOfPhotosTest(){
        MainLibrary lib = new MainLibrary();
        lib.addPhoto(photo);
        lib.addPhoto(photo);
        assertEquals(lib.getNumberOfPhotos(),1);
    }
    @Test
    public void getPhotosTest(){
        MainLibrary lib = new MainLibrary();
        lib.addPhoto(photo);
        Collection<IPhoto> collection = lib.getPhotos();
        assertEquals(collection.size(),1);
        assertTrue(collection.contains(photo));
    }
    @Test
    public void getMatchesTest(){
        MainLibrary lib = new MainLibrary();
        PhotoMetadata data = new PhotoMetadata(LocalDateTime.now(), null);
        IPhoto match = new Photo(null, LocalDateTime.now(), data, new MockFile("Path", 10));
        IPhoto nonMatch = new Photo(null, LocalDateTime.now(), data, new MockFile("a", 10));
        lib.addPhoto(match);
        lib.addPhoto(nonMatch);
        String regex = "Pa.*";
        Collection<IPhoto> collection = lib.getMatches(regex);
        assertEquals(collection.size(),1);
        assertTrue(collection.contains(match));
    }
    @Test
    public void toggleFavouriteTest(){
        MainLibrary lib = new MainLibrary();
        IPhoto favourite = new Photo(new MockFile("path", 0));
        IPhoto nonFavourite = new Photo(new MockFile("path2", 0));
        lib.addPhoto(favourite);
        lib.addPhoto(nonFavourite);
        favourite.toggleFavourite();
        Set<IPhoto> set = new HashSet<>();
        set.add(favourite);
        set.add(nonFavourite);
        lib.toggleFavourite(set);
        assertFalse(favourite.isFavourite());
        assertTrue(nonFavourite.isFavourite());
        
    }
}
