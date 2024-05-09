package leiphotos.domain.albums;

import java.util.List;
import java.util.Set;

import leiphotos.domain.facade.IPhoto;
/**
 * The interface for a catalog that manages albums.
 */
public interface IAlbumsCatalog {
    /**
     * Creates an album with the given name and adds it
     * to the catalog, if possible.
     * An album can be created if and only if there are no other albums
     * with the same name
     * @param albumName The album's name
     * @requires {@code albumName != null}
     * @return True if the album could be created,false otherwise.
     */
    public boolean createAlbum(String albumName);
    /**
     * Removes an album with the given name from the catalog, if possible.
     * @param albumName The album's name
     * @requires {@code albumName != null}
     * @return True if the album exists,false otherwise.
     */
    public boolean deleteAlbum(String albumName);
    /**
     * Determines whether or not the album with the given name
     * exists in the catalog
     * @param albumName The album's name
     * @requires {@code albumName != null}
     * @return True if the album exists in the catalog,false otherwise.
     */
    public boolean containsAlbum(String albumName);
    /**
     * Adds the photos in selectedPhotos to the 
     * album with the given name, if possible.
     * @param albumName The album's name
     * @param selectedPhotos A set containing the photos to add
     * @requires {@code albumName != null && selectedPhotos != null}
     * @return True if the album with albumName exists 
     * and at least one photo was successfully added,false otherwise.
     */
    public boolean addPhotos(String albumName, Set<IPhoto> selectedPhotos);
    /**
     * Removes the photos in selectedPhotos from the 
     * album with the given name, if possible.
     * @param albumName The album's name
     * @param selectedPhotos A set containing the photos to remove
     * @requires {@code albumName != null && selectedPhotos != null}
     * @return True if the album with albumName exists 
     * and at least one photo was successfully remove,false otherwise.
     */
    public boolean removePhotos(String albumName, Set<IPhoto> selectedPhotos);
    /**
     * Retrieves the a list containing all the photos
     * present in the album with the given name
     * @param albumName The albbum's name
     * @return A list containing all 
     * the photos present in the album 
     * @ensures \result != null && \result.size() >= 0
     */
    public List<IPhoto> getPhotos(String albumName);
    /**
     * Gets the names of all the albums 
     * present in the catalog
     * @return A set containing all the albums' 
     * names present in this catalog
     */
    public Set<String> getAlbumsNames();
}