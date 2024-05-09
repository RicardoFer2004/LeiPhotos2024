package leiphotos.domain.albums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.MockFile;
import leiphotos.domain.core.Photo;
import leiphotos.domain.core.PhotoMetadata;
import leiphotos.domain.facade.IPhoto;

/**
 * The test class for the AlbumsCatalog class
 */
public class AlbumsCatalogTests {
    private static File file;
    private static LocalDateTime date;
    private static IPhoto photo;
    @BeforeAll
    public void init(){
        file = new MockFile("path", 10);
        date = LocalDateTime.of(2024, 3, 24, 20, 35, 0);
        PhotoMetadata data = new PhotoMetadata(date, "camera", "manufacturer", null);
        photo = new Photo("title",date, data, file);
    }
    @Test
    public void createAlbumTest(){
        MainLibrary library = new MainLibrary();
        AlbumsCatalog catalog = new AlbumsCatalog(library);
        assertTrue(catalog.createAlbum("album"));
    }
    @Test
    public void deleteAlbumTest(){
        MainLibrary library = new MainLibrary();
        AlbumsCatalog catalog = new AlbumsCatalog(library);
        catalog.createAlbum("album");
        assertTrue(catalog.deleteAlbum("album"));
    }
    @Test
    public void containsAlbumTest(){
        MainLibrary library = new MainLibrary();
        AlbumsCatalog catalog = new AlbumsCatalog(library);
        catalog.createAlbum("album");
        assertTrue(catalog.containsAlbum("album"));
    }
    @Test
	public void addPhotosTestInexistentAlbum() {
		MainLibrary library = new MainLibrary();
        AlbumsCatalog catalog = new AlbumsCatalog(library);
        Set<IPhoto> set = new HashSet<>();
        assertFalse(catalog.addPhotos("album", set));
	}
    @Test
	public void addPhotosTestAlbumExistsWithoutPhotos() {
		MainLibrary library = new MainLibrary();
        AlbumsCatalog catalog = new AlbumsCatalog(library);
        catalog.createAlbum("album");
        Set<IPhoto> set = new HashSet<>();
        assertFalse(catalog.addPhotos("album", set));
	}
    @Test
	public void addPhotosTestAlbumExistsWithPhotos() {
		MainLibrary library = new MainLibrary();
        AlbumsCatalog catalog = new AlbumsCatalog(library);
        catalog.createAlbum("album");
        Set<IPhoto> set = new HashSet<>();
        set.add(photo);
        assertTrue(catalog.addPhotos("album", set));
        List<IPhoto> photos = catalog.getPhotos("album");
        assertEquals(photos.size(),1);
        assertEquals(photos.get(0),photo);
	}
    @Test
	public void removePhotosTestInexistentAlbum() {
		MainLibrary library = new MainLibrary();
        AlbumsCatalog catalog = new AlbumsCatalog(library);
        Set<IPhoto> set = new HashSet<>();
        assertFalse(catalog.removePhotos("album", set));
	}
    @Test
	public void removePhotosTestAlbumExistsWithoutPhotos() {
		MainLibrary library = new MainLibrary();
        AlbumsCatalog catalog = new AlbumsCatalog(library);
        catalog.createAlbum("album");
        Set<IPhoto> set = new HashSet<>();
        assertFalse(catalog.removePhotos("album", set));
	}
    @Test
	public void removePhotosTestAlbumExistsWithPhotos() {
		MainLibrary library = new MainLibrary();
        AlbumsCatalog catalog = new AlbumsCatalog(library);
        catalog.createAlbum("album");
        Set<IPhoto> set = new HashSet<>();
        set.add(photo);
        catalog.addPhotos("album", set);
        assertTrue(catalog.removePhotos("album", set));
        assertEquals(catalog.getPhotos("album").size(),0);
	}
    @Test
    public void getPhotosTestWithOutPhotos(){
        MainLibrary library = new MainLibrary();
        AlbumsCatalog catalog = new AlbumsCatalog(library);
        catalog.createAlbum("album");
        Set<IPhoto> set = new HashSet<>();
        catalog.addPhotos("album", set);
        List<IPhoto> photos = catalog.getPhotos("album");
        assertNotNull(photos);
        assertEquals(photos.size(),0);
    }
    @Test
    public void getPhotosTestWithPhotos(){
        MainLibrary library = new MainLibrary();
        AlbumsCatalog catalog = new AlbumsCatalog(library);
        catalog.createAlbum("album");
        Set<IPhoto> set = new HashSet<>();
        set.add(photo);
        catalog.addPhotos("album", set);
        List<IPhoto> photos = catalog.getPhotos("album");
        assertNotNull(photos);
        assertEquals(photos.size(),1);
        assertEquals(photos.get(0),photo);
    }
    @Test
    public void getAlbumsNamesTest(){
        MainLibrary library = new MainLibrary();
        AlbumsCatalog catalog = new AlbumsCatalog(library);
        String album1 = "album1";
        String album2 = "album2";
        catalog.createAlbum(album1);
        catalog.createAlbum(album2);
        Set<String> names = catalog.getAlbumsNames();
        assertEquals(names.size(),2);
        assertTrue(names.contains(album1));
        assertTrue(names.contains(album2));
    }
}
