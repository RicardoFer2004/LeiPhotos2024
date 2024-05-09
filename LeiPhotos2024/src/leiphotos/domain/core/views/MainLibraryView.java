package leiphotos.domain.core.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import leiphotos.DataStructures.CustomTree;
import leiphotos.domain.core.LibraryEvent;
import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.PhotoAddedLibraryEvent;
import leiphotos.domain.core.PhotoChangedLibraryEvent;
import leiphotos.domain.core.PhotoDeletedLibraryEvent;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Listener;
/**
 * Represents a view of the main library.
 * This class provides functionality to filter, sort, and cache photos from a main library.
 * Extends {@link ALibraryView}
 */
public class MainLibraryView extends ALibraryView implements Listener<LibraryEvent>{
    // Cache to store photos sorted by the current sorting criteria
    private CustomTree<IPhoto> cache;
    private final Set<IPhoto> photos;//Contains all the view's photos.Useful when it is needed to checked if a photo is present in the view. Also useful to get the number of photos
    private boolean sorted;

    /**
     * Constructs a MainLibraryView object with the specified predicate and main library.
     * Initializes the cache and registers the view as a listener to library events.
     *
     * @param predicate The predicate used to filter photos in the view.
     * @param library   The main library to which the view is associated.
     */
    public MainLibraryView(Predicate<IPhoto> predicate, MainLibrary library) {
        super(predicate, library);
        this.cache = new CustomTree<>(this.comparator);
        library.registerListener(this);
        this.photos = new HashSet<>();
        sorted = true;
    }

    /**
     * Clears the cache in response to library events in order to keep it updated
     *
     * @param e The library event.
     */
    @Override
    public void processEvent(LibraryEvent e) {
        if(e instanceof PhotoAddedLibraryEvent event){
            IPhoto photo = event.getPhoto();
            if(predicate.test(photo)){
                addPhoto(photo);
            }
        }else if(e instanceof PhotoDeletedLibraryEvent event){
            IPhoto photo = event.getPhoto();
            if(!photos.contains(photo)){
                return;
            }
            cache.remove(photo);
            photos.remove(photo);
        }else if(e instanceof PhotoChangedLibraryEvent event){
            IPhoto photo = event.getPhoto();
            if(!photos.contains(photo)){
                if(predicate.test(photo)){
                    addPhoto(photo);
                }
                return;
            }
            if(!predicate.test(photo)){
                photos.remove(photo);
                //Does not remove from the cache since it would be an O(log n) operation
            }
        }
    }

    /**
     * Retrieves the number of photos in the view.
     * decided to override this method in order to make
     * use of the cache when retrivig the nemberOfPhotos
     *
     * @return The number of photos in the view.
     */
    @Override
    public int numberOfPhotos() {
        return this.photos.size();
    }

    /**
     * Sets the comparator to define the sorting criteria for photos in the view.
     * Clears the cache to ensure data consistency.
     *
     * @param comparator The comparator to set.
     */
    @Override
    public void setComparator(Comparator<IPhoto> comparator) {
        super.setComparator(comparator);
        sorted = false;
    }
    /**
     * Retrieves the photos in the view.
     * if the photos were not cached yet it caches the result
     *
     * @return A list containing the photos in the view.
     */
    @Override
    public List<IPhoto> getPhotos() {
        if(!sorted){
            this.cache = new CustomTree<>(this.comparator);
            for(IPhoto photo : photos){
                cache.add(photo);
            }
            sorted = true;
        }
        if(numberOfPhotos() == 0){
            Collection<IPhoto> photoList = library.getPhotos();
            for(IPhoto photo : photoList){
                addPhoto(photo);
            }
        }
        List<IPhoto> cachedPhotos = this.cache.toList();
        List<IPhoto> result = new ArrayList<>();
        /* Necessary, since the cache can have photos that do not belong to the view.
         * This comes from the fact that when a photo is removed, the photo is not removed
         * from the cache, but rather from the hashset.
         * This was done in order to make the remove operation O(1) instead of O(log (n))
         */
        for(IPhoto photo : cachedPhotos){
            if(photos.contains(photo)){
                result.add(photo);
            }
        }
        return result;
    }
    /**
     * Adds the photo to the view if it verifies the predicate
     * 
     * @return True if it verifies the predicate and does not belong to the view (i.e. is not present in the photos set), false otherwise.
     * Note: Even if the photo already exists in the cache, as long as it does not exist in the photos set, 
     * it is considered as it not belonging to the view. This stems from previous implementation decisions that were taken in order to reduce
     * time complexity.
     */
    private boolean addPhoto(IPhoto photo){
        if(!predicate.test(photo)){
            return false;
        }
        cache.add(photo);
        return photos.add(photo);
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
