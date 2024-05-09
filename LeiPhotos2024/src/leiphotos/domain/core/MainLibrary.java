package leiphotos.domain.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.AbsSubject;
import leiphotos.utils.Utils;
/**
 * This class represents the main library of photos
 * This class provides methods that allow
 * photos to be added and removed, the retrieval of the
 * library's photos whilst also providing various other features.
 */
public class MainLibrary extends AbsSubject<LibraryEvent> implements Library{
    private final Collection<IPhoto> photos;
    /**
     * Creates a MainLibrary object with no photos
     */
    public MainLibrary(){
        this.photos = new HashSet<>();
    }
    /**
     * Creates a MainLibrary object with
     * all the photos that could be added
     * @param photoCollection The collection of photos
     * @requires {@code photoCollection != null photoCollection.get(i) != null, 0 <= i < photoCollection.size()}
     * @ensures The ammount of photos in the library will be equal to 
     * the ammount of photos that were successfully added
     */
    public MainLibrary(Collection<IPhoto> photoCollection){
        this.photos = new HashSet<>();
        for(IPhoto photo : photoCollection){
            this.addPhoto(photo);
        }
    }
    @Override
    public int getNumberOfPhotos() {
        return this.photos.size();
    }

    @Override
    public boolean addPhoto(IPhoto photo) {
        boolean added = this.photos.add(photo);
        if(added){
            LibraryEvent event = new PhotoAddedLibraryEvent(photo, this);
            this.emitEvent(event);
        }
        return added;
    }

    @Override
    public boolean deletePhoto(IPhoto photo) {
        boolean removed = this.photos.remove(photo);
        if(removed){
            LibraryEvent event = new PhotoDeletedLibraryEvent(photo, this);
            this.emitEvent(event);
        }
        return removed;
    }

    @Override
    public Collection<IPhoto> getPhotos() {
        return this.photos;
    }

    @Override
    public Collection<IPhoto> getMatches(String regexp) {
        return Utils.filterCollection(this.photos,(IPhoto photo) -> photo.matches(regexp));
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("***** MAIN PHOTO LIBRARY: " + 
        getNumberOfPhotos() + "  photos *****");
        List<IPhoto> list = Utils.collectionToList(photos);
        list.sort((x,y) ->x.file().compareTo(y.file()));
        for(IPhoto photo : list){
            sb.append(System.lineSeparator());
            sb.append(photo);
        }
        return sb.toString();
    }
    /**
	 * Toggles the favorite status of the selected photos that exist in the library.
	 * Selected photos that do not exist in this library are ignored
	 * 
	 * @param selectedPhotos The set of selected photos 
     * to toggle the favorite status.
	 */
    public void toggleFavourite(Set<IPhoto> selectedPhotos){
        selectedPhotos.forEach((IPhoto photo) ->{
            if(!this.photos.contains(photo)){
                return;
            }
            photo.toggleFavourite();
            LibraryEvent event = new PhotoChangedLibraryEvent(photo, this);
            this.emitEvent(event);
        });
    }

}
