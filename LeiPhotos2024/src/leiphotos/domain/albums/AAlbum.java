package leiphotos.domain.albums;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import leiphotos.domain.core.Library;
import leiphotos.domain.core.LibraryEvent;
import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.PhotoDeletedLibraryEvent;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Utils;
/**
 * Represents an abstract album implementation of the {@link IAlbum} interface.
 */
public abstract class AAlbum implements IAlbum{
    protected MainLibrary library;
    protected final String name;
    protected final Collection<IPhoto> photos;
    /**
     * Creates an empty album with no the given name as the album's name
     * and registers itself as a listener of the given library 
     * @param name The album's name
     * @param library The given library
     * @requires {@code name != null && library != null}
     */
    protected AAlbum(String name,MainLibrary library){
        this.name = name;
        this.photos = new HashSet<>();
        this.library = library;
        library.registerListener(this);
    }
    /**
     * Creates an album with no the given name as the album's name
     * and registers itself as a listener of the given library.
     * The album starts with the photos present in the given colection
     * 
     * This constructor should only be used for testing purposes.
     * With this constructor, it is possible to have repeated photos.
     * @param name The album's name
     * @param library The given library
     * @param collection A collection containing the photos to add to the album
     * @requires {@code name != null && library != null && collection != null}
     */
    protected AAlbum(String name,MainLibrary library,Collection<IPhoto> collection){
        this.name = name;
        this.photos = collection;
        library.registerListener(this);
    }
    @Override
    public int numberOfPhotos() {
       return this.photos.size();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<IPhoto> getPhotos() {
        return Utils.collectionToList(this.photos);
    }

    @Override
    public boolean addPhotos(Set<IPhoto> selectedPhotos) {
        return Utils.mapOverCollection(selectedPhotos,
              this::addPhoto);
    }

    @Override
    public boolean removePhotos(Set<IPhoto> selectedPhotos) {
        return Utils.mapOverCollection(selectedPhotos,
               this::removePhoto);
    }
    /**
     * Adds the given photo,if possible, to the album
     * @param photo The given photo
     * @return True if the photo could be added, false otherwise.
     */
    protected boolean addPhoto(IPhoto photo){
        return this.photos.add(photo);
    }
    /**
     * Removes the given photo,if possible, from the album
     * @param photo The given photo
     * @return True if the photo could be added, false otherwise.
     */
    protected boolean removePhoto(IPhoto photo){
        return this.photos.remove(photo);
    }
    @Override
    public void processEvent(LibraryEvent e) {
        Library lib = e.getLibrary();
        if(!lib.equals(this.library)){
            return;
        }
        if(e instanceof PhotoDeletedLibraryEvent event){
            IPhoto photo = event.getPhoto();
            this.removePhoto(photo);
        }
    } 
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("***** Album " + this.name + ": " + 
        this.numberOfPhotos() + " photos *****");
        for(IPhoto photo : photos){
            sb.append(System.lineSeparator());
            sb.append(photo.file().getPath());
        }
        return sb.toString();
    }
    @Override
    public boolean equals(Object obj){
        if(obj instanceof AAlbum other){
            return this.hashCode() == other.hashCode();
        }
        return false;
    }
    @Override
    public int hashCode(){
        return this.getName().hashCode();
    }
}
