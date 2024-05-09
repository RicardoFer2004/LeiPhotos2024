package leiphotos.domain.core.views;

import leiphotos.domain.facade.ViewsType;
/**
 * Represents a catalog of views for different types of libraries.
 * This interface provides functionality to retrieve views based on the specified type.
 */
public interface IViewsCatalog {
    /**
     * Retrieves the view associated with the specified ViewsType.
     *
     * @param t The ViewsType representing the type of view to retrieve.
     * @return The corresponding library view, or null if not found.
     */
    public ILibraryView getView(ViewsType t);
}
