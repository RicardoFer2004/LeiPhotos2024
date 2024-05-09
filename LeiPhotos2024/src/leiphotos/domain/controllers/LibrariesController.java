package leiphotos.domain.controllers;

import java.util.Optional;
import java.util.Set;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.PhotoFactory;
import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.facade.ILibrariesController;
import leiphotos.domain.facade.IPhoto;
/**
 * Represents a controller to manage libraries in the photo application.
 * implements the {@link ILibrariesController} interface
 */
public class LibrariesController implements ILibrariesController {
	private final MainLibrary mainLibrary;
	private final TrashLibrary trashLibrary;
	 /**
     * Creates a libraries controller with the given main library and trash library.
     *
     * @param mainLib  The main library.
     * @param trashLib The trash library.
     */
	public LibrariesController(MainLibrary mainLib, TrashLibrary trashLib) {
		this.mainLibrary = mainLib;
		this.trashLibrary = trashLib;
	}

	@Override
	public Optional<IPhoto> importPhoto(String title, String pathToPhotoFile) {
		try {
			IPhoto photo = PhotoFactory.INSTANCE.createPhoto(title, pathToPhotoFile);
			if(photo == null){
				throw new Exception("Something went wrong");
			}
			mainLibrary.addPhoto(photo);
			return Optional.of(photo);
		} catch (Exception e) {
		}
		return Optional.empty();
	}

	@Override
	public void deletePhotos(Set<IPhoto> selectedPhotos) {
		for(IPhoto photo : selectedPhotos){
			boolean removed = mainLibrary.deletePhoto(photo);
			if(removed){
				trashLibrary.addPhoto(photo);
			}
		}
	}

	@Override
	public void emptyTrash() {
		this.trashLibrary.deleteAll();
	}

	@Override
	public void toggleFavourite(Set<IPhoto> selectedPhotos) {
		this.mainLibrary.toggleFavourite(selectedPhotos);
	}

	@Override
	public Iterable<IPhoto> getMatches(String regExp) {
		return mainLibrary.getMatches(regExp);
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(mainLibrary);
		sb.append(System.lineSeparator());
		sb.append(trashLibrary);
		return sb.toString();
	}

}
