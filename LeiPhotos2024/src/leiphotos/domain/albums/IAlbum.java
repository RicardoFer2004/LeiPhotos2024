package leiphotos.domain.albums;

import java.util.List;
import java.util.Set;

import leiphotos.domain.core.LibraryEvent;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Listener;
/**
 * the interface for a album
 * IAlbums can listen for library events to keep them updated.
 */
public interface IAlbum extends Listener<LibraryEvent>{
    /**
     * Gets the number of photos that this album has
     * @return The number of photos present in this album
     * @ensures \result >= 0
     */
    public int numberOfPhotos();
    /**
     * Gets the name of the album
     * @return The name of the album
     * @ensures \result != null
     */
    public String getName();
    /**
     * Gets the photos present in this album
     * @return A list contain the album's photos
     * @ensures \result != null
     */
    public List<IPhoto> getPhotos();
    /**
     * Adds the photos in the given set to the album.
     * If any of the photos could not be added, it will just
     * be skipped
     * @param selectedPhotos The set containing the photos to add
     * @requires selectedPhotos != null
     * @return True if at least one photo was added successfully,
     * false otherwise
     */
    public boolean addPhotos(Set<IPhoto> selectedPhotos);
    /**
     * Removes the photos in the given set present in the album.
     * If any of the photos could not be removed, it will just
     * be skipped
     * @param selectedPhotos The set containing the photos to remove
     * @return True if at least one photo was removed successfully,
     * false otherwise
     */
    public boolean removePhotos(Set<IPhoto> selectedPhotos);
}
