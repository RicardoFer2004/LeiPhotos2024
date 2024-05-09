package leiphotos.domain.controllers;

import java.util.Comparator;
import java.util.List;

import leiphotos.domain.core.views.IViewsCatalog;
import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.facade.IViewsController;
import leiphotos.domain.facade.ViewsType;
/**
 * Controller responsible for managing views of photos in the LeiPhotos application.
 * Implements the {@link IViewsController} interface.
 */
public class ViewsController implements IViewsController {

	/** the view catalog used to retrieve view of photos */
	private final IViewsCatalog views;

	/**
     * Constructs a ViewsController with the specified views catalog.
     *
     * @param views the views catalog to use
     */
	public ViewsController(IViewsCatalog views) {
		this.views = views;
	}

	@Override
	public List<IPhoto> getPhotos(ViewsType viewType) {
		return this.views.getView(viewType).getPhotos();
	}

	@Override
	public List<IPhoto> getMatches(ViewsType viewType, String regexp) {
		return this.views.getView(viewType).getMatches(regexp);
	}

	@Override
	public void setSortingCriteria(ViewsType viewType, Comparator<IPhoto> criteria) {
		this.views.getView(viewType).setComparator(criteria);
	}
	@Override
	public String toString() {
		return this.views.toString();
	}
}
