package leiphotos.domain.core.views;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import leiphotos.domain.core.Library;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Utils;
/**
 * Abstract class representing a view of a library.
 * Provides common functionality for views of different types of libraries.
 * implements the {@link ILibraryView} interface
 */
public abstract class ALibraryView implements ILibraryView {
    /** Predicate to filter photos in the view. */ 
    protected Predicate<IPhoto> predicate;

    /** The library to which the view refers. */
    protected Library library;

    /** Comparator to define the sorting criteria for photos in the view. */
    protected Comparator<IPhoto> comparator;

    /**
     * Constructs a new {@code ALibraryView} with the given predicate and a given library
     *
     * @param predicate The predicate to filter photos in the view.
     * @param library The library to which the view refers.
     */
    protected ALibraryView(Predicate<IPhoto> predicate, Library library) {
        this.predicate = predicate;
        this.library = library;
        this.comparator = Comparator.comparingLong(IPhoto::size);
    }


    @Override
    public void setComparator(Comparator<IPhoto> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int numberOfPhotos() {
        return this.getPhotos().size();
    }

    @Override
    public List<IPhoto> getPhotos() {
        List<IPhoto> photos = Utils.collectionToList(Utils.filterCollection(library.getPhotos(), predicate));
        photos.sort(comparator);
        return photos;
    }

    @Override
    public List<IPhoto> getMatches(String regexp) {
        return  Utils.collectionToList(Utils.filterCollection(this.getPhotos(),(IPhoto photo) -> photo.matches(regexp)));
    }
    
}
