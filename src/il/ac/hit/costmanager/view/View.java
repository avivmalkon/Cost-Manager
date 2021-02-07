package il.ac.hit.costmanager.view;

import il.ac.hit.costmanager.utils.Category;
import il.ac.hit.costmanager.utils.CostItem;
import il.ac.hit.costmanager.viewmodel.IViewModel;
import javax.swing.*;
import java.util.ArrayList;

public class View implements IView
{
    private IViewModel viewModel;
    private ApplicationUI applicationUI;

    public View()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                applicationUI = new ApplicationUI();
                applicationUI.start();
            }
        });
    }

    /**
     * Connects a ViewModel objects with this View object.
     * @param viewModel The ViewModel object to connect.
     */
    @Override
    public void setViewModel(IViewModel viewModel)
    {
        this.viewModel = viewModel;
        applicationUI.setViewModel(viewModel);
    }

    /**
     * Displays a message window on the user's UI.
     * @param message A string message to show on the message window.
     */
    @Override
    public void displayMessage(String message)
    {
        if (SwingUtilities.isEventDispatchThread()) // Checking if the current thread is the UI thread. If not, calling the invokeLater method to show the message with UI thread
            applicationUI.displayMessage(message);
        else
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    applicationUI.displayMessage(message);
                }
            });
        }

    }

    /**
     * Updates UI table from CostItem array.
     * @param costItems An array of CostItem objects, to update the UI table from.
     */
    @Override
    public void updateTable(CostItem[] costItems)
    {
        if (SwingUtilities.isEventDispatchThread()) // Checking if the current thread is the UI thread. If not, calling the invokeLater method to show the message with UI thread
        {
            applicationUI.updateCostItemData(costItems);
        }
        else
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    applicationUI.updateCostItemData(costItems);
                }
            });
        }
    }

    /**
     * Updates UI table from Category array.
     * @param categories An array of Category objects, to update the UI table from.
     */
    @Override
    public void updateCategories(Category[] categories)
    {
        if (SwingUtilities.isEventDispatchThread()) // Checking if the current thread is the UI thread. If not, calling the invokeLater method to show the message with UI thread
        {
            applicationUI.updateCategoriesData(categories);
        }
        else
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    applicationUI.updateCategoriesData(categories);
                }
            });
        }
    }

    /**
     * Updates the View table with a new CostItem object.
     * @param costItem the CostItem object to add to the UI table.
     */
    @Override
    public void addCostItemToUI(CostItem costItem)
    {
        if (SwingUtilities.isEventDispatchThread()) // Checking if the current thread is the UI thread. If not, calling the invokeLater method to show the message with UI thread
        {
            applicationUI.addCostItemToTable(costItem);
        }
        else
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    applicationUI.addCostItemToTable(costItem);
                }
            });
        }
    }

    /**
     * Updates the View table with a new Category object.
     * @param category the Category object to add to the UI table.
     */
    @Override
    public void addCategoryToUI(Category category)
    {
        if (SwingUtilities.isEventDispatchThread()) // Checking if the current thread is the UI thread. If not, calling the invokeLater method to show the message with UI thread
        {
            applicationUI.addCategoryToTable(category);
        }
        else
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    applicationUI.addCategoryToTable(category);
                }
            });
        }
    }

    /**
     * Removes all the rows from the table in the UI.
     */
    @Override
    public void removeAllRows()
    {
        if (SwingUtilities.isEventDispatchThread()) // Checking if the current thread is the UI thread. If not, calling the invokeLater method to show the message with UI thread
        {
            applicationUI.removeAllRows();
        }
        else
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    applicationUI.removeAllRows();
                }
            });
        }
    }

    /**
     * Removes a group of categories from the UI table.
     * @param indexToDelete An arrayList of integers with the indexes of the categories to from the UI.
     */
    @Override
    public void deleteCategories(ArrayList<Integer> indexToDelete)
    {
        if (SwingUtilities.isEventDispatchThread()) // Checking if the current thread is the UI thread. If not, calling the invokeLater method to show the message with UI thread
        {
            applicationUI.removeCategories(indexToDelete);
        }
        else
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    applicationUI.removeCategories(indexToDelete);
                }
            });
        }

    }

    /**
     * Removes a group of costItems from the UI table.
     * @param indexesToDeleteAL An arrayList of integers with the indexes of the costItems to delete from the UI.
     */
    @Override
    public void deleteCostItems(ArrayList<Integer> indexesToDeleteAL)
    {
        if (SwingUtilities.isEventDispatchThread()) // Checking if the current thread is the UI thread. If not, calling the invokeLater method to show the message with UI thread
        {
            applicationUI.removeCostItems(indexesToDeleteAL);
        }
        else
        {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    applicationUI.removeCostItems(indexesToDeleteAL);
                }
            });
        }

    }
}
