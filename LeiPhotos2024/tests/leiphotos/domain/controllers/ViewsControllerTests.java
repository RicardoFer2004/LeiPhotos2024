package leiphotos.domain.controllers;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import leiphotos.domain.core.Library;
import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.Photo;
import leiphotos.domain.core.PhotoMetadata;
import leiphotos.domain.core.RecentlyDeletedLibrary;
import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.core.views.IViewsCatalog;
import leiphotos.domain.core.views.ViewsCatalog;
import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.facade.ViewsType;

public class ViewsControllerTests {

    private ViewsController viewsController;
    private IViewsCatalog viewsCatalog;

    private Library mainLibrary;
    private Library trashLibrary;
    private IPhoto photo1;
    private IPhoto photo2;

    @BeforeEach
    public void setUp() {
        mainLibrary = new MainLibrary();
        trashLibrary = new RecentlyDeletedLibrary();
        viewsCatalog = new ViewsCatalog((MainLibrary) mainLibrary, (TrashLibrary) trashLibrary);
        viewsController = new ViewsController(viewsCatalog);

        LocalDateTime dateOfCapture = LocalDateTime.now();
        photo1 = new Photo("AnelJVasconcelos", dateOfCapture,
                 new PhotoMetadata(dateOfCapture,null,null, null),
                 new File ("leiphotos_59823_58191/LeiPhotos2024_59823_58191/photos/AnelJVasconcelos.jpeg"));
		photo2 = new Photo("Bean", dateOfCapture,
                 new PhotoMetadata(dateOfCapture,null,null, null),
                 new File ("leiphotos_59823_58191/LeiPhotos2024_59823_58191/photos/Bean.jpeg"));
    }

    @Test
    public void testGetPhotos() {
        mainLibrary.addPhoto(photo1);
        mainLibrary.addPhoto(photo2);

        List<IPhoto> expectedPhotos = new ArrayList<>();
        expectedPhotos.add(photo1);
        expectedPhotos.add(photo2);

        ViewsType viewTypeMain = ViewsType.ALL_MAIN;
        List<IPhoto> actualPhotosMain = viewsController.getPhotos(viewTypeMain);
        assertTrue(expectedPhotos.containsAll(actualPhotosMain));

        ViewsType viewTypeRecent = ViewsType.MOST_RECENT;
        List<IPhoto> actualPhotosRecent = viewsController.getPhotos(viewTypeRecent);
        assertTrue(expectedPhotos.containsAll(actualPhotosRecent));
        //put a thread sleep in the future (chage the constant in ViewsController to not wait so much)

        ViewsType viewTypeFavourites = ViewsType.FAVOURITES_MAIN;
        photo1.toggleFavourite();
        expectedPhotos.remove(photo2);
        List<IPhoto> actualPhotosFavorites = viewsController.getPhotos(viewTypeFavourites);
        assertTrue(expectedPhotos.containsAll(actualPhotosFavorites));

        ViewsType viewTypeTrash = ViewsType.ALL_TRASH;
        mainLibrary.deletePhoto(photo1);
        List<IPhoto> actualPhotosTrash = viewsController.getPhotos(viewTypeTrash);
        assertTrue(expectedPhotos.containsAll(actualPhotosTrash));

    }

    @Test
    public void testGetMatches() {
        mainLibrary.addPhoto(photo1);
        mainLibrary.addPhoto(photo2);


        List<IPhoto> expectedPhotos = new ArrayList<>();
        expectedPhotos.add(photo1);
        expectedPhotos.add(photo2);

        String regexpMatches = "AnelJVasconcelos";
        String regexpNoMatch = "somethingSomething";

        ViewsType viewTypeMain = ViewsType.ALL_MAIN;
        List<IPhoto> actualPhotosMain = viewsController.getMatches(viewTypeMain, regexpMatches);
        List<IPhoto> noPhotosMain = viewsController.getMatches(viewTypeMain, regexpNoMatch);
        assertTrue(expectedPhotos.containsAll(actualPhotosMain));
        assertTrue(noPhotosMain.isEmpty());

        ViewsType viewTypeRecent = ViewsType.MOST_RECENT;
        List<IPhoto> actualPhotosRecent = viewsController.getMatches(viewTypeRecent, regexpMatches);
        List<IPhoto> noPhotosRecent = viewsController.getMatches(viewTypeRecent, regexpNoMatch);
        assertTrue(expectedPhotos.containsAll(actualPhotosRecent));
        assertTrue(noPhotosRecent.isEmpty());
        //put a thread sleep in the future (chage the constant in ViewsController to not wait so much)

        ViewsType viewTypeFavourites = ViewsType.FAVOURITES_MAIN;
        photo1.toggleFavourite();
        expectedPhotos.remove(photo2);
        List<IPhoto> actualPhotosFavorites = viewsController.getMatches(viewTypeFavourites, regexpMatches);
        List<IPhoto> noPhotosFavourites = viewsController.getMatches(viewTypeFavourites, regexpNoMatch);
        assertTrue(expectedPhotos.containsAll(actualPhotosFavorites));
        assertTrue(noPhotosFavourites.isEmpty());

        ViewsType viewTypeTrash = ViewsType.ALL_TRASH;
        mainLibrary.deletePhoto(photo1);
        List<IPhoto> actualPhotosTrash = viewsController.getMatches(viewTypeTrash, regexpMatches);
        List<IPhoto> noPhotosTrash = viewsController.getMatches(viewTypeTrash, regexpNoMatch);
        assertTrue(expectedPhotos.containsAll(actualPhotosTrash));
        assertTrue(noPhotosTrash.isEmpty());
    }

    @Test
    public void testSetSortingCriteria() {
        mainLibrary.addPhoto(photo1);
        mainLibrary.addPhoto(photo2);

        Comparator<IPhoto> criteria = Comparator.comparing(IPhoto::title);
        
        List<IPhoto> expectedPhotos = new ArrayList<>();
        expectedPhotos.add(photo2);
        expectedPhotos.add(photo1);
        expectedPhotos.sort(criteria);

        ViewsType viewTypeMain = ViewsType.ALL_MAIN;
        viewsController.setSortingCriteria(viewTypeMain, criteria);
        List<IPhoto> actualPhotosMain = viewsController.getPhotos(viewTypeMain);
        assertEquals(expectedPhotos, actualPhotosMain);

    }
}   
