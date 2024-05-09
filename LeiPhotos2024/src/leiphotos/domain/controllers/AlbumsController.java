package leiphotos.domain.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import leiphotos.domain.albums.IAlbumsCatalog;
import leiphotos.domain.facade.IAlbumsController;
import leiphotos.domain.facade.IPhoto;
/**
 * Represents a controller to manages albums in the photo application.
 * implements the {@link IAlbumsController} interface
 */
public class AlbumsController implements IAlbumsController {
	private final IAlbumsCatalog catalog;
	private String selectedAlbum;
	/**
	 * Creates an albums controller that controlls the given catalog.
	 * No album is selected initially
	 * @param albumsCatalog The given albums catalog
	 */
	public AlbumsController(IAlbumsCatalog albumsCatalog) {
		this.catalog = albumsCatalog;
	}
	
	@Override
	public boolean createAlbum(String name) {
		return catalog.createAlbum(name);
	}

	@Override
	public void removeAlbum() {
		if(!isAlbumSelected()){
			return;
		}
		catalog.deleteAlbum(selectedAlbum);
		selectedAlbum = null;
	}

	@Override
	public void selectAlbum(String name) {
		if(catalog.containsAlbum(name)){
			this.selectedAlbum = name;
		}
	}

	@Override
	public void addPhotos(Set<IPhoto> selectedPhotos) {
		if(!isAlbumSelected()){
			return;
		}
		catalog.addPhotos(selectedAlbum,selectedPhotos);
	}

	@Override
	public void removePhotos(Set<IPhoto> selectedPhotos) {
		if(!isAlbumSelected()){
			return;
		}
		catalog.removePhotos(selectedAlbum,selectedPhotos);
	}

	@Override
	public List<IPhoto> getPhotos() {
		if(!isAlbumSelected()){
			return new ArrayList<>();
		}
		return catalog.getPhotos(selectedAlbum);
	}

	@Override
	public Optional<String> getSelectedAlbum() {
		return isAlbumSelected() ? Optional.of(selectedAlbum) 
		: Optional.empty();
	}

	@Override
	public boolean createSmartAlbum(String name, Predicate<IPhoto> criteria) {
		//We dont have the SmartAlbum class. It should only designed, not implemented, according to the given task.
		return false;
	}

	@Override
	public Set<String> getAlbumNames() {
		return catalog.getAlbumsNames();
	}
	/**
	 * Determines whether or not an album is selected
	 * @return True if an album is selected, false othwerwise.
	 */
	protected boolean isAlbumSelected(){
		return this.selectedAlbum != null;
	}
	@Override
	public String toString(){
		return catalog.toString();
	}
}
