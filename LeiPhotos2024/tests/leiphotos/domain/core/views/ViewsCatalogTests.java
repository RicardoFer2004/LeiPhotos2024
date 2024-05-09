package leiphotos.domain.core.views;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.RecentlyDeletedLibrary;
import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.facade.ViewsType;

public class ViewsCatalogTests {
    private ViewsCatalog viewsCatalog;
    private MainLibrary mainLibrary;
    private TrashLibrary trashLibrary;

    @BeforeEach
    public void setUp() {
        mainLibrary = new MainLibrary();
        trashLibrary = new RecentlyDeletedLibrary();
        viewsCatalog = new ViewsCatalog(mainLibrary, trashLibrary);
    }

    @Test
    public void testGetViewAllMain() {
        ILibraryView view = viewsCatalog.getView(ViewsType.ALL_MAIN);
        assertNotNull(view);
    }

    @Test
    public void testGetViewAllTrash() {
        ILibraryView view = viewsCatalog.getView(ViewsType.ALL_TRASH);
        assertNotNull(view);
    }

    @Test
    public void testGetViewFavouritesMain() {
        ILibraryView view = viewsCatalog.getView(ViewsType.FAVOURITES_MAIN);
        assertNotNull(view);
    }

    @Test
    public void testGetViewMostRecent() {
        ILibraryView view = viewsCatalog.getView(ViewsType.MOST_RECENT);
        assertNotNull(view);
    }
}
