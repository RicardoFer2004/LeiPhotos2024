package leiphotos.domain.core;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Utils;
/**
 * An abstract implementation of the {@link TrashLibrary} interface
 * that provides basic functionality for managing deleted photos.
 */
public abstract class ATrashLibrary implements TrashLibrary {
    
    protected final Map<IPhoto,LocalDateTime> deletedPhotos;

    /**
     * Constructs a new {@code ATrashLibrary} with the given main library.
     * 
     * @param mainLibrary the main library instance
     */
    protected ATrashLibrary(){
        this.deletedPhotos = new HashMap<>();
    }

    /**
     * Performs cleaning operations specific to the trash library implementation.
     * Implementation is left for subclasses of the ATrashLibrary class
     */
    protected abstract void clean();
    
    /**
     * Checks if it's time to perform cleaning operations.
     * Implementation is left for subclasses of the ATrashLibrary class
     * @return {@code true} if cleaning should be performed, otherwise {@code false}
     */
    protected abstract boolean cleaningTime();

    /**
     * Returns the number of photos currently in the trash library.
     * 
     * @return the number of photos
     */
    @Override
    public int getNumberOfPhotos() {
        return this.deletedPhotos.size();
    }

    /**
     * Adds the specified photo to the trash library.
     * 
     * @param photo the photo to add
     * @requires {@code photo != null}
     * @return {@code true} if the photo was added successfully, otherwise {@code false}
     */
    @Override
    public boolean addPhoto(IPhoto photo) {
        if(deletedPhotos.containsKey(photo)){
            return false;
        }
        this.deletedPhotos.put(photo,LocalDateTime.now());
        return true;
    }

    /**
     * Deletes the specified photo from the trash library.
     * @param photo the photo to delete
     * @return {@code true} if the photo was deleted successfully, otherwise {@code false}
     * @requires {@code photo != null}
     */
    @Override
    public boolean deletePhoto(IPhoto photo) {
        return this.deletedPhotos.remove(photo) != null;
    }

    /**
     * Returns a collection of photos in the trash library that match the specified regular expression.
     * 
     * @param regexp the regular expression to match
     * @return a collection of matching photos
     * @requires {@code regex != null}
     */
    @Override
    public Collection<IPhoto> getMatches(String regexp) {
        return  Utils.filterCollection(this.deletedPhotos.keySet(),(IPhoto photo) -> photo.matches(regexp));
    }
    
    @Override
    public boolean deleteAll() {
        if (deletedPhotos.isEmpty()) return false;
        deletedPhotos.clear();
        return true;
    }
    
    @Override
    public Collection<IPhoto> getPhotos() {
        clean();
        return deletedPhotos.keySet();
    }
}

