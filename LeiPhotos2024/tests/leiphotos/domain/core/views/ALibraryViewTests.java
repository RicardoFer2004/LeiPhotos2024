package leiphotos.domain.core.views;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import leiphotos.domain.core.Library;
import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.Photo;
import leiphotos.domain.core.PhotoMetadata;
import leiphotos.domain.core.RecentlyDeletedLibrary;
import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.facade.ViewsType;


public class ALibraryViewTests {
    private MainLibraryView mainView;
    private TrashLibraryView trashView;

    private ViewsCatalog catalog;

    private Library mainLibrary;
    private Library trashLibrary;
    private Predicate<IPhoto> predicate;
    private IPhoto photo1;
    private IPhoto photo2;

    @BeforeEach
    public void setUp() {
        mainLibrary = new MainLibrary();
        trashLibrary = new RecentlyDeletedLibrary();
        predicate = photo -> true; // A simple predicate that accepts all photos
        mainView = new MainLibraryView(predicate,(MainLibrary) mainLibrary);
        trashView = new TrashLibraryView(predicate,(TrashLibrary) trashLibrary);
        catalog = new ViewsCatalog((MainLibrary)  mainLibrary, (TrashLibrary) trashLibrary);


        LocalDateTime dateOfCapture = LocalDateTime.now();
        photo1 = new Photo("AnelJVasconcelos", dateOfCapture,
                 new PhotoMetadata(dateOfCapture.minusMonths(14),null,null, null),
                 new File ("leiphotos_59823_58191/LeiPhotos2024_59823_58191/photos/AnelJVasconcelos.jpeg"));
		photo2 = new Photo("Bean", dateOfCapture,
                 new PhotoMetadata(dateOfCapture,null,null, null),
                 new File ("leiphotos_59823_58191/LeiPhotos2024_59823_58191/photos/Bean.jpeg"));
    }

    @Test
    public void testSetComparator() {
        Comparator<IPhoto> comparator = Comparator.comparing(IPhoto::capturedDate);
        mainView.setComparator(comparator);
        trashView.setComparator(comparator);

        assertTrue(comparator.equals(mainView.comparator));
        assertTrue(comparator.equals(trashView.comparator));
    }

    @Test
    public void testNumberOfPhotos() {
        mainLibrary.addPhoto(photo1);
        mainLibrary.addPhoto(photo2);

        assertEquals(2, mainView.numberOfPhotos());
        mainLibrary.deletePhoto(photo1);
        assertEquals(1, mainView.numberOfPhotos());
        trashLibrary.addPhoto(photo1);
        assertEquals(1, trashView.numberOfPhotos());     
        trashLibrary.deletePhoto(photo1);
        assertEquals(0, trashView.numberOfPhotos());

        assertEquals(1, catalog.getView(ViewsType.MOST_RECENT).numberOfPhotos());

        assertEquals(0, catalog.getView(ViewsType.FAVOURITES_MAIN).numberOfPhotos());
        Set<IPhoto> favourites = new HashSet<>();
        favourites.add(photo2);
        favourites.add(photo1);
        ((MainLibrary) mainLibrary).toggleFavourite(favourites);
        assertEquals(1, catalog.getView(ViewsType.FAVOURITES_MAIN).numberOfPhotos());

    }

    @Test
    public void testGetPhotos() {
        mainLibrary.addPhoto(photo1);
        mainLibrary.addPhoto(photo2);
        /*
         * Change from ::size to ::hashCode since the photos have the same size and thus, can be in any order.
         * Since this test does not account for that, I decided to use the simpler option.
         */
        Comparator<IPhoto> criteria = Comparator.comparingLong(IPhoto::hashCode);
        mainView.setComparator(criteria);

        List<IPhoto> expectedPhotos = new ArrayList<>();
        expectedPhotos.add(photo1);
        expectedPhotos.add(photo2);
        expectedPhotos.sort(criteria);

        assertEquals(expectedPhotos, mainView.getPhotos());

        assertEquals(2, mainView.getPhotos().size());
        mainLibrary.deletePhoto(photo1);
        assertEquals(1, mainView.getPhotos().size());
        mainLibrary.deletePhoto(photo2);
        assertEquals(0, mainView.getPhotos().size());

        mainLibrary.addPhoto(photo1);
        mainLibrary.addPhoto(photo2);
        assertFalse(catalog.getView(ViewsType.MOST_RECENT).getPhotos().contains(photo1));
        assertTrue(catalog.getView(ViewsType.MOST_RECENT).getPhotos().contains(photo2));

        expectedPhotos.add(photo1);
        Set<IPhoto> favourites = new HashSet<>();
        favourites.add(photo2);
        ((MainLibrary) mainLibrary).toggleFavourite(favourites);
        assertTrue(catalog.getView(ViewsType.FAVOURITES_MAIN).getPhotos().contains(photo2));
        assertFalse(catalog.getView(ViewsType.FAVOURITES_MAIN).getPhotos().contains(photo1));
    }

    @Test
    public void testGetMatches() {
        mainLibrary.addPhoto(photo1);
        mainLibrary.addPhoto(photo2);

        assertEquals(1, mainView.getMatches("AnelJVasconcelos").size());
        assertEquals(1, mainView.getMatches("Bean").size());
        assertEquals(0, mainView.getMatches("filezinho").size());


        mainLibrary.deletePhoto(photo1);
        assertEquals(0, mainView.getMatches("AnelJVasconcelos").size());
        assertEquals(1, mainView.getMatches("Bean").size());

        assertEquals(0, trashView.getMatches("Bean").size());
        trashLibrary.addPhoto(photo1);
        assertEquals(1, trashView.getMatches("AnelJVasconcelos").size());

        trashLibrary.deletePhoto(photo1);
        assertEquals(0, trashView.getMatches("AnelJVasconcelos").size());
    }
}
