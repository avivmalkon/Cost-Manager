package il.ac.hit.costmanager.tests;

import il.ac.hit.costmanager.model.*;
import il.ac.hit.costmanager.utils.*;
import static org.junit.Assert.*;

public class DerbyCostModelTest
{
    private ICostModel derbyCostModel;
    private CostItem itemToDelete;
    private Category electronics;
    private Category apparel;


    @org.junit.Before
    public void setUp()
    {
        electronics = new Category("Electronics");
        apparel = new Category("Apparel");

        try {
            derbyCostModel = new DerbyCostModel();
            derbyCostModel.deleteTables();
            derbyCostModel.createTables();
            derbyCostModel.addCategory(electronics);
            derbyCostModel.addCategory(apparel);

            CostItem[] costItems = {
                    new CostItem(new Date(20, 2, 1997), "Sony 5", 1500, Currency.USD, electronics, "Arrived with 2 games for free"),
                    new CostItem(new Date(25, 8, 2010), "Samsung Galaxy Note 20", 1000, Currency.EUR, electronics, "some note"),
                    new CostItem(new Date(3, 10, 2020), "Backpack", 130, Currency.USD, apparel,"some more note")};

            itemToDelete = new CostItem(new Date(19, 1, 2018), "Red shirt", 100, Currency.ILS, apparel, "this item will be deleted");

            for (CostItem item: costItems)
            {
                derbyCostModel.addCostItem(item);
            }

            derbyCostModel.addCostItem(itemToDelete);


        } catch (CostManagerException e) {
            e.printStackTrace();
        }
    }


    @org.junit.After
    public void tearDown()
    {
        try {
            derbyCostModel.deleteTables();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        derbyCostModel = null;
    }


    @org.junit.Test
    public void addCostItem()
    {
        Operation result = Operation.FAILED;
        try {
            result = derbyCostModel.addCostItem(new CostItem(new Date(16, 9, 2020), "Sony 6", 2500, Currency.USD, electronics, "Arrived with 4 games for free"));
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        assertEquals(Operation.SUCCESS, result);
    }


    @org.junit.Test
    public void deleteCostItems()
    {
        Operation result = Operation.FAILED;
        try {
            int[] idsToDelete = {itemToDelete.getId()};
            result = derbyCostModel.deleteCostItems(idsToDelete);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        assertEquals(Operation.SUCCESS, result);
    }


    @org.junit.Test
    public void addCategory()
    {
        Operation result = Operation.FAILED;
        Category workingTools = new Category("Working tools");
        try {
            result = derbyCostModel.addCategory(workingTools);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        assertEquals(Operation.SUCCESS, result);
    }


    @org.junit.Test
    public void deleteCategory()
    {
        Operation result = Operation.FAILED;
        try {
            Category[] categories = new Category[]{apparel};
            result = derbyCostModel.deleteCategory(categories);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        assertEquals(Operation.SUCCESS, result);
    }


    @org.junit.Test
    public void getFilteredExpenses()
    {
        CostItem[] filteredCostItems = null;
        try {
            filteredCostItems = derbyCostModel.getFilteredExpenses(new Category[] {electronics}, new Currency[] {Currency.USD, Currency.ILS});
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        assertNotEquals(null, filteredCostItems);

    }


    @org.junit.Test
    public void getCategories()
    {
        Category[] categories = null;
        try
        {
            categories = derbyCostModel.getCategories();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        assertNotEquals(null, categories);
    }


    @org.junit.Test
    public void getExpenses()
    {
        CostItem[] costItems = null;
        try {
            costItems = derbyCostModel.getExpenses();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        assertNotEquals(null, costItems);
    }


    @org.junit.Test
    public void deleteTables()
    {
        Operation result = Operation.FAILED;
        try {
            result = derbyCostModel.deleteTables();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        assertEquals(Operation.SUCCESS, result);
    }


    @org.junit.Test
    public void createTables()
    {
        Operation result = Operation.FAILED;
        try {
            derbyCostModel.deleteTables();
            result = derbyCostModel.createTables();
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        
        assertEquals(Operation.SUCCESS, result);
    }
}
