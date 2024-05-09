package leiphotos.domain.core.views;

import java.time.LocalDateTime;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.facade.ViewsType;
/**
 * Catalog of views for different types of libraries.
 * Implements {@link IViewsCatalog}.
 */
public class ViewsCatalog implements IViewsCatalog {
	
      
    //Duration indicating the recent time window.
	private static final int MONTHS_UNTIL_NOT_RECENT = 12;
    //The view representing the main library.
	private final MainLibraryView mainLibraryView;
    //The view representing the trash library.
    private final TrashLibraryView trashLibraryView;
    //The view representing the favorites in the main library.
	private final MainLibraryView favouritesMainLibraryView;
    //The view representing the most recent photos in the main library.
    private final MainLibraryView mostRecentLibraryView;
    /**
     * Constructs a ViewsCatalog with a main library view and trash library view.
     * a favorites Main Library View and a most recent library view 
     * @param mainLib  The main library
     * @param trashLib The trash library
     */
	public ViewsCatalog(MainLibrary mainLib, TrashLibrary trashLib) {
		this.mainLibraryView = new MainLibraryView(photo -> true, mainLib);
		this.trashLibraryView = new TrashLibraryView(photo -> true, trashLib);
		this.favouritesMainLibraryView = new MainLibraryView(IPhoto::isFavourite, mainLib);
        this.mostRecentLibraryView = new MainLibraryView(photo -> photo.capturedDate().isAfter(LocalDateTime.now().minusMonths((long) MONTHS_UNTIL_NOT_RECENT - 1)), mainLib);
	}

	@Override
	public ILibraryView getView(ViewsType t) {
		switch (t) {
            case ALL_MAIN:
                return mainLibraryView;
            case ALL_TRASH:
                return trashLibraryView;
            case FAVOURITES_MAIN:
                return favouritesMainLibraryView;
            case MOST_RECENT:
                return mostRecentLibraryView;
            default:
                return null;
        }
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String closingMessage = " photos *****";
        sb.append("***** VIEWS *****");
        sb.append(System.lineSeparator());
        sb.append("***** VIEW ALL_MAIN: " + mainLibraryView.numberOfPhotos() + closingMessage);
        sb.append(System.lineSeparator());
        sb.append(mainLibraryView);
        sb.append(System.lineSeparator());
        sb.append("***** VIEW ALL_TRASH: " + trashLibraryView.numberOfPhotos() + closingMessage);
        sb.append(trashLibraryView);
        sb.append(System.lineSeparator());
        sb.append("***** VIEW FAVOURITES_MAIN: " + favouritesMainLibraryView.numberOfPhotos() + closingMessage);
        sb.append(favouritesMainLibraryView);
        sb.append(System.lineSeparator());
        sb.append("***** VIEW MOST_RECENT: " + mostRecentLibraryView.numberOfPhotos() + closingMessage);
        sb.append(mostRecentLibraryView);
        return sb.toString();
    }
}
