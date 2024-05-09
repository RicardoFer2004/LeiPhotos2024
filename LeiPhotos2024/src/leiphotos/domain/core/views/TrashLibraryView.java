package leiphotos.domain.core.views;

import java.util.function.Predicate;

import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.facade.IPhoto;

/**
 * Represents a view of the TrashLibrary, displaying photos that have been moved to the trash.
 * This class provides functionality to filter and access photos within the trash library.
 * Extends {@link ALibraryView}
 */
public class TrashLibraryView extends ALibraryView {
    /**
     * Constructs a TrashLibraryView with the specified predicate and trash library.
     *
     * @param predicate The predicate used to filter photos in the view.
     * @param library   The TrashLibrary instance associated with the view.
     */
    public TrashLibraryView(Predicate<IPhoto> predicate, TrashLibrary library) {
        super(predicate, library);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(IPhoto photo : getPhotos()){
            sb.append(System.lineSeparator());
            sb.append(photo.file());
        }
        if(sb.length() > 0){
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

}
