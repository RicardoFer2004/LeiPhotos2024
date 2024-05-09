package leiphotos.domain.albums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.facade.IPhoto;
/**
 * Represents an abstract implementation of the {@link IAlbumsCatalog} interface
 */
public class AlbumsCatalog implements IAlbumsCatalog {
	private final MainLibrary library;
	private final Map<String,IAlbum> albums;
	/**
	 * Creates an empty album catalog from the given library
	 * @param mainLib The library
	 */
	public AlbumsCatalog(MainLibrary mainLib) {
		this.library = mainLib;
		this.albums = new HashMap<>();
	}
	@Override
	public boolean createAlbum(String albumName) {
		if(containsAlbum(albumName)){
			return false;
		}
		IAlbum album = new Album(albumName,library);
		albums.put(albumName,album);
		return true;
	}

	@Override
	public boolean deleteAlbum(String albumName) {
		if(!containsAlbum(albumName)){
			return false;
		}
		albums.remove(albumName);
		return true;
	}

	@Override
	public boolean containsAlbum(String albumName) {
		return this.albums.containsKey(albumName);
	}

	@Override
	public boolean addPhotos(String albumName, Set<IPhoto> selectedPhotos) {
		if(!containsAlbum(albumName)){
			return false;
		}
		IAlbum album = albums.get(albumName);
		return album.addPhotos(selectedPhotos);
	}

	@Override
	public boolean removePhotos(String albumName, Set<IPhoto> selectedPhotos) {
		if(!containsAlbum(albumName)){
			return false;
		}
		IAlbum album = albums.get(albumName);
		return album.removePhotos(selectedPhotos);
	}
	@Override
	public List<IPhoto> getPhotos(String albumName) {
		List<IPhoto> list = new ArrayList<>();
		if(containsAlbum(albumName)){
			IAlbum album = albums.get(albumName);
			list = album.getPhotos();
		}
		return list;
	}
	/**
	 * @ensures \result != null
	 */
	@Override
	public Set<String> getAlbumsNames() {
		Set<String> albumNames = new HashSet<>();
		for(String album : albums.keySet()){
			albumNames.add(album);
		}
		return albumNames;
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("***** ALBUMS *****");
		for(String albumName : albums.keySet()){
			IAlbum album = albums.get(albumName);
			sb.append(System.lineSeparator() + album);
		}
		return sb.toString();
	}
}
