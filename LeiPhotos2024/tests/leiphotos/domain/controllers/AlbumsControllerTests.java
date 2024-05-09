package leiphotos.domain.controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import leiphotos.domain.albums.AlbumsCatalog;
import leiphotos.domain.albums.IAlbumsCatalog;
import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.MockFile;
import leiphotos.domain.core.Photo;
import leiphotos.domain.core.PhotoMetadata;
import leiphotos.domain.facade.IAlbumsController;
import leiphotos.domain.facade.IPhoto;

public class AlbumsControllerTests {
    private static File file;
    private static File file2;
    private static LocalDateTime date;
    private static IPhoto photo;
    private static IPhoto photo2;

    @BeforeAll
    public static void init(){
        file = new MockFile("path", 10);
        file2 = new MockFile("path2", 10);
        date = LocalDateTime.of(2024, 3, 24, 20, 35, 0);
        PhotoMetadata data = new PhotoMetadata(date, "camera", "manufacturer", null);
        photo = new Photo("title",date, data, file);
        photo2 = new Photo("title2",date, data, file2);
    }

    @Test
    public void createAlbumTest(){
        MainLibrary library = new MainLibrary();
        IAlbumsCatalog catalog = new AlbumsCatalog(library);
        IAlbumsController controller = new AlbumsController(catalog);
        assertTrue(controller.createAlbum("album"));
    }
    @Test
    public void selectAlbumTestNonExistentAlbum(){
        MainLibrary library = new MainLibrary();
        IAlbumsCatalog catalog = new AlbumsCatalog(library);
        IAlbumsController controller = new AlbumsController(catalog);
        controller.selectAlbum("album");
        Optional<String> op = Optional.empty();
        assertNotNull(controller.getSelectedAlbum());
        assertEquals(controller.getSelectedAlbum(),op);
    }
    @Test
    public void selectAlbumTestExistingAlbum(){
        MainLibrary library = new MainLibrary();
        IAlbumsCatalog catalog = new AlbumsCatalog(library);
        IAlbumsController controller = new AlbumsController(catalog);
        controller.createAlbum("album");
        controller.selectAlbum("album");
        Optional<String> op = Optional.of("album");
        assertNotNull(controller.getSelectedAlbum());
        assertEquals(controller.getSelectedAlbum(),op);
    }
    @Test
    public void removeAlbumTestNonSelectedAlbum(){
        MainLibrary library = new MainLibrary();
        IAlbumsCatalog catalog = new AlbumsCatalog(library);
        IAlbumsController controller = new AlbumsController(catalog);
        controller.createAlbum("album");
        controller.removeAlbum();
        Optional<String> op = Optional.empty();
        assertNotNull(controller.getSelectedAlbum());
        assertEquals(controller.getSelectedAlbum(), op);
    }
    @Test
    public void removeAlbumTestSelectedAlbum(){
        MainLibrary library = new MainLibrary();
        IAlbumsCatalog catalog = new AlbumsCatalog(library);
        IAlbumsController controller = new AlbumsController(catalog);
        controller.createAlbum("album");
        controller.selectAlbum("album");
        controller.removeAlbum();
        Optional<String> op = Optional.empty();
        assertNotNull(controller.getSelectedAlbum());
        assertEquals(controller.getSelectedAlbum(), op);
        controller.selectAlbum("album");
        assertEquals(controller.getSelectedAlbum(), op);
    }
    @Test
    public void addPhotosTest(){
        MainLibrary library = new MainLibrary();
        IAlbumsCatalog catalog = new AlbumsCatalog(library);
        IAlbumsController controller = new AlbumsController(catalog);
        controller.createAlbum("album");
        controller.selectAlbum("album");
        Set<IPhoto> set = new HashSet<>();
        set.add(photo);
        set.add(photo2);
        controller.addPhotos(set);
        List<IPhoto> photos = controller.getPhotos();
        assertNotNull(photos);
        assertTrue(photos.contains(photo));
        assertTrue(photos.contains(photo2));
    }
    @Test
    public void removePhotosTest(){
        MainLibrary library = new MainLibrary();
        IAlbumsCatalog catalog = new AlbumsCatalog(library);
        IAlbumsController controller = new AlbumsController(catalog);
        controller.createAlbum("album");
        controller.selectAlbum("album");
        Set<IPhoto> set = new HashSet<>();
        set.add(photo);
        set.add(photo2);
        controller.addPhotos(set);
        controller.removePhotos(set);
        List<IPhoto> photos = controller.getPhotos();
        assertNotNull(photos);
        assertEquals(photos.size(),0);
    }
    @Test
    public void getPhotosTest(){
        MainLibrary library = new MainLibrary();
        IAlbumsCatalog catalog = new AlbumsCatalog(library);
        IAlbumsController controller = new AlbumsController(catalog);
        controller.createAlbum("album");
        controller.selectAlbum("album");
        Set<IPhoto> set = new HashSet<>();
        set.add(photo);
        set.add(photo2);
        controller.addPhotos(set);
        List<IPhoto> photos = controller.getPhotos();
        assertNotNull(photos);
        assertEquals(photos.size(),2);
    }
    @Test
    public void getAlbumNamesTest(){
        MainLibrary library = new MainLibrary();
        IAlbumsCatalog catalog = new AlbumsCatalog(library);
        IAlbumsController controller = new AlbumsController(catalog);
        List<String> albums = List.of("album","album2","album3");
        for(String str : albums){
            controller.createAlbum(str);
        }
        Set<String> names = controller.getAlbumNames();
        assertNotNull(names);
        for(String str : albums){
            assertTrue(names.contains(str));
        }
    }
    public void isAlbumSelectedTest(){
        MainLibrary library = new MainLibrary();
        IAlbumsCatalog catalog = new AlbumsCatalog(library);
        AlbumsController controller = new AlbumsController(catalog);
        controller.createAlbum("album");
        controller.selectAlbum("album");
        assertTrue(controller.isAlbumSelected());
        controller.removeAlbum();
        assertFalse(controller.isAlbumSelected());
    }
}
