package il.ac.hit.costmanager.utils;

import il.ac.hit.costmanager.model.*;
import il.ac.hit.costmanager.view.*;
import il.ac.hit.costmanager.viewmodel.*;

/**
 * A class that creates a View, Model and a ViewModel, and connects them with each other, with the function connect().
 */
public class ComponentsBundle
{
    private IView view;
    private ICostModel model;
    private IViewModel viewModel;

    /**
     * The ComponentsBundle constructor. Creates view, model and viewModel instances.
     */
    public ComponentsBundle()
    {
        view = null;
        model = null;
        viewModel = null;
    }

    /**
     * Connects the View, Model and ViewModel with each other.
     * @return Operation - Success or failed.
     */
    public Operation connect() // connects all app components (MVVM)
    {
        Operation result = Operation.SUCCESS;

        view = new View();

        try {
            model = new DerbyCostModel();
        } catch (CostManagerException e) {
            result = Operation.FAILED;
            e.printStackTrace();
        }

        viewModel = new ViewModel(view, model);

        view.setViewModel(viewModel);

        if(view == null || model == null)
            result = Operation.FAILED;

        return result;
    }

    /**
     * Gets the view instance.
     * @return The view instance.
     */
    public IView getView() {
        return view;
    }

    /**
     * Gets the model instance.
     * @return The model instance.
     */
    public ICostModel getModel() {
        return model;
    }

    /**
     * Gets the viewModel instance.
     * @return The viewModel instance.
     */
    public IViewModel getViewModel() {
        return viewModel;
    }
}
