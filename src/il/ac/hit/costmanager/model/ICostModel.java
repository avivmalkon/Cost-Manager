package il.ac.hit.costmanager.model;

import il.ac.hit.costmanager.utils.*;

public interface ICostModel
{
    /**
     * Add a cost item to the DerbyDB table.
     * @param item The CostItem object to add to the table.
     * @return Operation Failed or success.
     * @throws CostManagerException If the process failed.
     */
    Operation addCostItem(CostItem item) throws CostManagerException;

    /**
     * Deletes a CostItem object from the table (if exists).
     * @param ids The ID of the CostItem object to delete from the table.
     * @return Operation failed or success.
     * @throws CostManagerException If the process failed.
     */
    Operation deleteCostItems(int[] ids) throws CostManagerException;

    /**
     * Adds a category to the categories table.
     * @param category The category the add.
     * @return Operation failed or success.
     * @throws CostManagerException If the process failed.
     */
    Operation addCategory(Category category) throws CostManagerException;

    /**
     * Deletes a category from the categories table.
     * @param categories The categories array to delete.
     * @return Operation failed or success.
     * @throws CostManagerException If the process failed.
     */
    Operation deleteCategory(Category[] categories) throws CostManagerException;

    /**
     * Gets all CostItems from the database table as costItems array.
     * @return CostItem[] The result array of costItems.
     * @throws CostManagerException If the process failed.
     */
    CostItem[] getExpenses() throws CostManagerException;

    /**
     * Deletes costTable from the database.
     * @return Operation failed or success.
     * @throws CostManagerException If the process failed.
     **/
    Operation deleteTables() throws CostManagerException;

    /**
     * Create costTable Table at derby server
     * @return Operation failed or success.
     * @throws CostManagerException If the process failed.
     */
    Operation createTables() throws CostManagerException;

    /**
     * Gets all Categories from the database.
     * @return Category[] The result array of categories.
     * @throws CostManagerException If the process failed.
     */
    Category[] getCategories() throws CostManagerException;

    /**
     * Gets costItems from the database according to the filters params.
     * @param categories An array of desired categories to filter by.
     * @param currencies An array of desired currencies to filter by.
     * @return CostItem[] The result array of costItems.
     * @throws CostManagerException If the process failed.
     */
    CostItem[] getFilteredExpenses(Category[] categories, Currency[] currencies) throws CostManagerException;
}
