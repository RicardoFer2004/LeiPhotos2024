package leiphotos.domain.core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.Predicate;

import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Utils;
/**
 * Represents a library for recently deleted photos with automatic cleaning functionality.
 * Extends the {@link ATrashLibrary} class to inherit common functionality.
 */
public class RecentlyDeletedLibrary extends ATrashLibrary{
	
	/** The maximum time in seconds that a photo can remain in the recently deleted library
     since its deletion before it is permanently removed. */
	private static final int SECONDS_IN_TRASH = 15;

	/**  The time interval in seconds after which the recently deleted library should be cleaned. */
	private static final int SECONDS_TO_CHECK = 10;

	//The date and time of the last cleanup operation performed on the recently deleted library.
	private LocalDateTime dateOfLastCleanUp;

	/**
     * Constructs a new RecentlyDeletedLibrary object.
     * Initializes the date of the last cleanup operation to the current date and time.
     *
     * @param mainLibrary The main library to which this recently deleted library belongs.
     */
	public RecentlyDeletedLibrary() {
		super();
		this.dateOfLastCleanUp = LocalDateTime.now();
	}

	/**
     * Cleans up the recently deleted photos if it's time for cleaning.
     * Removes photos from the deleted photos collection whose duration since addition
     * exceeds the specified time to live.
     */
	@Override
	public void clean() {
		if (!cleaningTime()) {
			return;
		}
		Predicate<IPhoto> pred = photo -> Duration.between(deletedPhotos.get(photo), LocalDateTime.now()).getSeconds() >= SECONDS_IN_TRASH;
		Collection<IPhoto> photosToRemove = Utils.filterCollection(deletedPhotos.keySet(),pred);
		for(IPhoto photo : photosToRemove){
			deletedPhotos.remove(photo);
		}
	}

	/**
     * Checks if it's time to perform a cleanup operation.
     *
     * @return True if it's time to perform cleaning, otherwise false.
     */
	@Override
	public boolean cleaningTime() {
		return Duration.between(this.dateOfLastCleanUp, LocalDateTime.now()).getSeconds() >= SECONDS_TO_CHECK;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
		Collection<IPhoto> photos = deletedPhotos.keySet();//Cannot call the getPhotos method since it performs the cleaning operation which is not supposed to in the toString.
        sb.append("***** TRASH PHOTO LIBRARY: " + getNumberOfPhotos() + "  photos *****");
        for(IPhoto photo : photos){
            sb.append(System.lineSeparator());
            sb.append(photo);
        }
        return sb.toString();
    }
}
