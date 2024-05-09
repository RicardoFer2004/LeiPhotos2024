package leiphotos.domain.albums;

import leiphotos.domain.core.MainLibrary;
/**
 * This class represents an album. Extends the abstract {@link AAlbum} class.
 */
public class Album extends AAlbum{
    /**
     * Creates an album from a library. 
     * The album's name will be the given name
     * @param name The given name
     * @param library The library
     */
    public Album(String name, MainLibrary library) {
        super(name, library);
    }
    
}
