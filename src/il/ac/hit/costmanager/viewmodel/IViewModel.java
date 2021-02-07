package il.ac.hit.costmanager.viewmodel;

import il.ac.hit.costmanager.utils.Category;
import il.ac.hit.costmanager.utils.CostItem;
import il.ac.hit.costmanager.utils.Currency;
import java.util.ArrayList;

public interface IViewModel
{
    /**
    * Gets all categories from the database and updates them in the View.
    */
    void updateCategories();

    /**
     * Gets all costItems from the database and updates them in the View.
     */
    void updateTable();

    /**
     * Writes the costItem param to the database, and updates the View with the new costItem.
     * @param costItemToAdd The costItem to write to the database.
     */
    void addCostItem(CostItem costItemToAdd);

    /**
     * Adds a new category to the database, and update the View with the new category.
     * @param categoryToAdd The category to add to the database.
     */
    void addCategory(Category categoryToAdd);

    /**
     * Gets costItems from the database according to the filters params, and updates the UI with those costItems.
     * @param categories An array of desired categories to filter by.
     * @param currencies An array of desired currencies to filter by.
     * @param years An array of desired years to filter by.
     * @param months An array of desired months to filter by.
     */
    void showFilteredExpenses(Category[] categories, Currency[] currencies, int[] years, int[] months);

    /**
     * Deletes a group of costItems from the database and from the view.
     * @param itemsToDeleteAL An arrayList of the ID's of the costItems to delete.
     * @param indexesToDeleteAL An arrayList of the indexes to delete from the costItems arrayList in the view.
     */
    void deleteCostItems(int[] itemsToDeleteAL, ArrayList<Integer> indexesToDeleteAL);

    /**
     * Deletes a group of categories from the database and from the view.
     * @param selectedCategories An array of the categories to delete.
     * @param indexToDelete An arrayList of the indexes to delete from the categories arrayList in the view.
     */
    void deleteCategories(Category[] selectedCategories, ArrayList<Integer> indexToDelete);
}
