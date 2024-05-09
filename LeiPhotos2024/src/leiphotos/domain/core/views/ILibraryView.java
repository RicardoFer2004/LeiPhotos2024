package leiphotos.domain.core.views;

import java.util.Comparator;
import java.util.List;

import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Listener;

/**
 * Represents views of libraries.
 * Extends {@link Listener} to handle library events.
 */
public interface ILibraryView {

    /**
     * Sets the comparator to define the sorting criteria for the photos in the view.
     *
     * @param comparator the comparator to set
     */
    void setComparator(Comparator<IPhoto> comparator);

    /**
     * Returns the number of photos belonging to the view.
     *
     * @return the number of photos
     * @ensures \return >= 0
     */
    int numberOfPhotos();

    /**
     * Retrieves the photos in the view according to the current sorting criteria.
     *
     * @return a list of photos in the view, sorted according to the current criteria
     * @ensures \return != null
     */
    List<IPhoto> getPhotos();

    /**
     * Retrieves the photos in the view that match the given regular expression,
     * sorted according to the current sorting criteria.
     *
     * @param regexp the regular expression to match against photo names
     * @return a list of photos in the view that match the given regular expression,
     *         sorted according to the current criteria
     */
    List<IPhoto> getMatches(String regexp);
}
