package il.ac.hit.costmanager.view;

import il.ac.hit.costmanager.utils.Category;
import il.ac.hit.costmanager.utils.CostItem;
import il.ac.hit.costmanager.viewmodel.IViewModel;
import java.util.ArrayList;

public interface IView
{
    /**
     * Connects a ViewModel objects with this View object.
     * @param viewModel The ViewModel object to connect.
     */
    void setViewModel(IViewModel viewModel);

    /**
     * Displays a message window on the user's UI.
     * @param message A string message to show on the message window.
     */
    void displayMessage(String message);

    /**
     * Updates UI table from CostItem array.
     * @param costItems An array of CostItem objects, to update the UI table from.
     */
    void updateTable(CostItem[] costItems);

    /**
     * Updates UI table from Category array.
     * @param categories An array of Category objects, to update the UI table from.
     */
    void updateCategories(Category[] categories);

    /**
     * Updates the View table with a new CostItem object.
     * @param costItem the CostItem object to add to the UI table.
     */
    void addCostItemToUI(CostItem costItem);

    /**
     * Updates the View table with a new Category object.
     * @param category the Category object to add to the UI table.
     */
    void addCategoryToUI(Category category);

    /**
     * Removes all the rows from the table in the UI.
     */
    void removeAllRows();

    /**
     * Removes a group of categories from the UI table.
     * @param indexToDelete An arrayList of integers with the indexes of the categories to from the UI.
     */
    void deleteCategories(ArrayList<Integer> indexToDelete);

    /**
     * Removes a group of costItems from the UI table.
     * @param indexesToDeleteAL An arrayList of integers with the indexes of the costItems to delete from the UI.
     */
    void deleteCostItems(ArrayList<Integer> indexesToDeleteAL);
}
