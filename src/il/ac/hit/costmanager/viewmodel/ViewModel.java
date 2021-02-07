package il.ac.hit.costmanager.viewmodel;

import il.ac.hit.costmanager.model.*;
import il.ac.hit.costmanager.utils.*;
import il.ac.hit.costmanager.view.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel implements IViewModel
{
    private IView view;
    private ICostModel model;
    private final ExecutorService pool;

    /**
     * The ViewModel constructor. Creates table in the model (if not exist).
     * @param view The view instance to connect with the viewModel.
     * @param model The model instance to connect with the viewModel.
     */
    public ViewModel(IView view, ICostModel model)
    {
        setView(view);
        setModel(model);

        pool = Executors.newFixedThreadPool(10);
    }

    /**
     * Gets all costItems from the database and updates them in the View.
     */
    @Override
    public void updateTable()
    {
        final CostItem[][] costItems = new CostItem[1][];

        pool.submit(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    costItems[0] = model.getExpenses();

                    view.updateTable(costItems[0]);
                } catch (CostManagerException e)
                {
                    e.printStackTrace();
                    view.displayMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * Gets all categories from the database and updates them in the View.
     */
    @Override
    public void updateCategories()
    {
        final Category[][] categories = new Category[1][];

        pool.submit(new Runnable() {
            @Override
            public void run()
            {
                try {
                    categories[0] = model.getCategories();
                    view.updateCategories(categories[0]);
                } catch (CostManagerException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * Writes the costItem param to the database, and updates the View with the new costItem.
     * @param costItemToAdd The costItem to write to the database.
     */
    @Override
    public void addCostItem(CostItem costItemToAdd)
    {
        pool.submit(new Runnable() {
            @Override
            public void run()
            {
                try {
                    model.addCostItem(costItemToAdd);
                    view.addCostItemToUI(costItemToAdd);
                } catch (CostManagerException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Deletes a group of costItems from the database and from the view.
     * @param itemsToDeleteAL An arrayList of the ID's of the costItems to delete.
     * @param indexesToDeleteAL An arrayList of the indexes to delete from the costItems arrayList in the view.
     */
    @Override
    public void deleteCostItems(int[] itemsToDeleteAL, ArrayList<Integer> indexesToDeleteAL)
    {
        pool.submit(new Runnable()
        {
            @Override
            public void run() {
                try {
                    model.deleteCostItems(itemsToDeleteAL);
                    view.deleteCostItems(indexesToDeleteAL);
                } catch (CostManagerException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Adds a new category to the database, and update the View with the new category.
     * @param categoryToAdd The category to add to the database.
     */
    @Override
    public void addCategory(Category categoryToAdd)
    {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addCategory(categoryToAdd);
                    view.addCategoryToUI(categoryToAdd);
                } catch (CostManagerException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Gets costItems from the database according to the filters params, and updates the UI with those costItems.
     * @param categories An array of desired categories to filter by.
     * @param currencies An array of desired currencies to filter by.
     * @param years An array of desired years to filter by.
     * @param months An array of desired months to filter by.
     */
    @Override
    public void showFilteredExpenses(Category[] categories, Currency[] currencies, int[] years, int[] months)
    {
        final CostItem[][] costItems = new CostItem[1][];

        pool.submit(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    costItems[0] = model.getFilteredExpenses(categories, currencies);

                    if (costItems[0].length > 0)
                        costItems[0] = filterByYearsAndMonths(costItems[0], years, months);

                    view.removeAllRows();
                    view.updateTable(costItems[0]);

                } catch (CostManagerException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Deletes a group of categories from the database and from the view.
     * @param selectedCategories An array of the categories to delete.
     * @param indexToDelete An arrayList of the indexes to delete from the categories arrayList in the view.
     */
    @Override
    public void deleteCategories(Category[] selectedCategories, ArrayList<Integer> indexToDelete)
    {
        pool.submit(new Runnable()
        {
            @Override
            public void run() {
                try {
                    model.deleteCategory(selectedCategories);
                    view.deleteCategories(indexToDelete);
                } catch (CostManagerException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Filters the costItem array param by the years and months params, and returns the the filtered costItems array.
     * @param costItems The array to filter from.
     * @param years The years to filter by.
     * @param months The months to filter by.
     * @return CostItem[] The filtered costItems array.
     */
    private CostItem[] filterByYearsAndMonths(CostItem[] costItems, int[] years, int[] months)
    {
        if (costItems.length == 0 || (years.length == 0 && months.length == 0)) // No filtering required
        {
            return costItems;
        }
        else if (years.length > 0 && months.length == 0) // filter only by years
        {
            return filterByYears(costItems, years);
        }
        else if (years.length == 0 /* && months.length > 0 */) // filter only by months (accept all years)
        {
            return filterByMonths(costItems, months);
        }
        else // if (years.length > 0 && months.length > 0) // filter by years and months
        {
            ArrayList<CostItem> filteredCostItemsAL = new ArrayList<>();

            for (int i = 0; i < costItems.length; i++) // passing through all costItems
            {
                CostItem currentCostItem = costItems[i];

                for (int j = 0; j < years.length; j++) // passing through all years
                {
                    if (currentCostItem.getDate().getYear() == years[j])
                    {
                        for (int k = 0; k < months.length; k++) // passing through all months at the current year
                        {
                            if(currentCostItem.getDate().getMonth() == months[k])
                            {
                                filteredCostItemsAL.add(currentCostItem);
                                break; // check this !!!
                            }
                        }
                    }
                }
            }

            return filteredCostItemsAL.toArray(new CostItem[0]);
        }
    }

    /**
     * Filters the costItem array param by the years param, and returns the the filtered costItems array.
     * @param costItems The array to filter from.
     * @param years The years to filter by.
     * @return CostItem[] The filtered costItems array.
     */
    private CostItem[] filterByYears(CostItem[] costItems, int[] years)
    {
        ArrayList<CostItem> filteredCostItemsAL = new ArrayList<>();

        for (int i = 0; i < costItems.length; i++) // passing through all costItems
        {
            CostItem currentCostItem = costItems[i];

            if (years.length > 0) // filter by years and months
            {
                for (int j = 0; j < years.length; j++) // passing through all years
                {
                    if (currentCostItem.getDate().getYear() == years[j])
                    {
                        filteredCostItemsAL.add(currentCostItem);
                        //break; // check this !!!
                    }
                }
            }
        }

        return filteredCostItemsAL.toArray(new CostItem[0]);
    }

    /**
     * Filters the costItem array param by the months param, and returns the the filtered costItems array.
     * @param costItems The array to filter from.
     * @param months The months to filter by.
     * @return CostItem[] The filtered costItems array.
     */
    private CostItem[] filterByMonths(CostItem[] costItems, int[] months)
    {
        ArrayList<CostItem> filteredCostItemsAL = new ArrayList<>();

        for (int i = 0; i < costItems.length; i++)
        {
            CostItem currentCostItem = costItems[i];

            for (int j = 0; j < months.length; j++)
            {
                if (currentCostItem.getDate().getMonth() == months[j])
                {
                    filteredCostItemsAL.add(currentCostItem);
                    break; // check this !!!
                }
            }
        }

        return filteredCostItemsAL.toArray(new CostItem[0]);
    }

    private void setView(IView view)
    {
        this.view = view;
    }

    private void setModel(ICostModel model)
    {
        this.model = model;
    }
}
