package il.ac.hit.costmanager.model;

import il.ac.hit.costmanager.utils.*;
import java.sql.*;

public class DerbyCostModel implements ICostModel
{
    private static final String EMBEDDED_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String EMBEDDED_PROTOCOL = "jdbc:derby:";

    private Statement _statement = null;
    private Connection _connection = null;

    /**
     * Derby model Constructor. Create the connection to derby server.
     * @throws CostManagerException If the process failed.
     **/
    public DerbyCostModel() throws CostManagerException
    {
        try {
            Class.forName(EMBEDDED_DRIVER);
            _connection = DriverManager.getConnection(EMBEDDED_PROTOCOL + "derbyDB;create=true");
            setConnection();
        }
        catch (ClassNotFoundException | SQLException | CostManagerException e) {
            e.printStackTrace();
        }

        finally {
            closeConnection();
        }
    }

    /**
     * Add a cost item to the DerbyDB table.
     * @param item The CostItem object to add to the table.
     * @return Operation Failed or success.
     * @throws CostManagerException If the process failed.
     */
    @Override
    public Operation addCostItem(CostItem item) throws CostManagerException
    {
        Operation result = null;
        PreparedStatement stat;
        try {
            setConnection();
            stat = _connection.prepareStatement("INSERT INTO costTable(id,date,name,price,currency,category,note) VALUES(?,?,?,?,?,?,?)");
            stat.setString(1, String.valueOf(item.getId()));
            stat.setString(2, String.valueOf(item.getDate()));
            stat.setString(3, item.getDescription());
            stat.setString(4, String.valueOf(item.getPrice()));
            stat.setString(5, String.valueOf(item.getCurrency()));
            stat.setString(6, String.valueOf(item.getCategory()));
            stat.setString(7, String.valueOf(item.getNote()));

            int statResult = stat.executeUpdate();
            if(statResult == 0)
                result = Operation.FAILED;
            else
                result = Operation.SUCCESS;

        } catch (SQLException e) {
            result = Operation.FAILED;
            throw new CostManagerException(e.getMessage());
        }

        finally
        {
            closeConnection();
            return result;
        }

    }

    /**
     * Deletes a CostItem object from the table (if exists).
     * @param ids The ID of the CostItem object to delete from the table.
     * @return Operation failed or success.
     * @throws CostManagerException If the process failed.
     */
    @Override
    public Operation deleteCostItems(int[] ids) throws CostManagerException
    {
        ResultSet rs;
        Operation result = null;

        try
        {
            setConnection();
            rs = _statement.executeQuery("SELECT * FROM costTable ORDER BY id");
            if(rs != null)
            {
                PreparedStatement stat;
                for (var id:ids) {
                    stat = _connection.prepareStatement("DELETE FROM costTable WHERE id = (?)");
                    stat.setString(1, String.valueOf(id));
                    int statResult = stat.executeUpdate();
                    if (statResult != 0) {
                        System.out.println("Item Deleted: " + id);
                        result = Operation.SUCCESS;
                    } else {
                        System.out.println("Delete Statement Failed: " + id);
                        result = Operation.FAILED;
                    }
                }
            }
            else
            {
                result = Operation.FAILED;
                System.out.println("Item not found: ");
            }

        } catch (SQLException e) {
            result =  Operation.FAILED;
            throw new CostManagerException(e.getMessage());
        }

        finally
        {
            closeConnection();
            return result;
        }


    }

    /**
     * Adds a category to the categories table.
     * @param category The category the add.
     * @return Operation failed or success.
     * @throws CostManagerException If the process failed.
     */
    @Override
    public Operation addCategory(Category category) throws CostManagerException
    {
        Operation result = null;
        try
        {
            setConnection();
            PreparedStatement stat = _connection.prepareStatement("INSERT INTO Categories(name) VALUES(?)");
            stat.setString(1, category.getName());
            int statResult = stat.executeUpdate();
            if(statResult != 0)
                result = Operation.SUCCESS;
            else
                result = Operation.FAILED;

        } catch (SQLException e) {
            result = Operation.FAILED;
            throw new CostManagerException(e.getMessage());
        }

        finally
        {
            closeConnection();
            return result;
        }
    }

    /**
     * Deletes a category from the categories table.
     * @param categories The categories array to delete.
     * @return Operation failed or success.
     * @throws CostManagerException If the process failed.
     */
    @Override
    public Operation deleteCategory(Category[] categories) throws CostManagerException
    {
        ResultSet rs;
        Operation result = null;
        try
        {
            setConnection();
            rs = _statement.executeQuery("SELECT name FROM Categories");

            if(rs != null){
                for (Category category: categories) {
                    PreparedStatement stat = _connection.prepareStatement("DELETE FROM Categories WHERE name = (?)");
                    stat.setString(1, category.getName());
                    int statResult = stat.executeUpdate();
                    if (statResult != 0) {
                        System.out.println("Category Item Deleted: " + category.getName());
                        result = Operation.SUCCESS;
                    } else {
                        System.out.println("Category Item Not Deleted: " + category.getName());
                        result = Operation.FAILED;
                    }
                }
            }
            else
            {
                System.out.println("Categories not found: ");
                result = Operation.FAILED;
            }


        } catch (SQLException e) {
            result = Operation.FAILED;
            throw new CostManagerException(e.getMessage());
        }

        finally
        {
            closeConnection();
            return result;
        }
    }

    /**
     * Gets costItems from the database according to the filters params.
     * @param categories An array of desired categories to filter by.
     * @param currencies An array of desired currencies to filter by.
     * @return CostItem[] The result array of costItems.
     * @throws CostManagerException If the process failed.
     */
    @Override
    public CostItem[] getFilteredExpenses(Category[] categories, Currency[] currencies) throws CostManagerException
    {
        boolean needsAnd = false;
        ResultSet rs;
        StringBuilder sqlQueryBuilder = new StringBuilder("SELECT * FROM costTable");

        // Filtering by categories:
        if (categories[0] != null)
        {
            sqlQueryBuilder.append(" WHERE (category='").append(categories[0]).append("'");

            for(int i = 1; i < categories.length; i++)
            {
                if(categories[i] != null)
                {
                    sqlQueryBuilder.append(" OR category='").append(categories[i]).append("'");
                }
            }
            sqlQueryBuilder.append(")");
            needsAnd = true;
        }


        // Filtering by currencies:
        if (currencies[0] != null)
        {
            if(needsAnd)
                sqlQueryBuilder.append(" AND");
            else
                sqlQueryBuilder.append(" WHERE");

            sqlQueryBuilder.append(" (currency='").append(currencies[0]).append("'");

            for(int i = 1; i < currencies.length; i++)
            {
                if(currencies[i] != null)
                {
                    sqlQueryBuilder.append(" OR currency='").append(currencies[i]).append("'");
                }
            }
            sqlQueryBuilder.append(")");
        }


        // Applying the query to the database:
        sqlQueryBuilder.append(" ORDER BY id");
        try
        {
            setConnection();
            rs = _statement.executeQuery(sqlQueryBuilder.toString());

            return getCostItemsFromResultSet(rs);

        } catch (SQLException e) {
            throw new CostManagerException(e.getMessage());
        }

        finally
        {
            closeConnection();
        }
    }

    /**
     * Gets all CostItems from the database table as costItems array.
     * @return CostItem[] The result array of costItems.
     * @throws CostManagerException If the process failed.
     */
    @Override
    public CostItem[] getExpenses() throws CostManagerException
    {
        ResultSet rs;

        try
        {
            setConnection();
            rs = _statement.executeQuery("SELECT * FROM costTable ORDER BY id");

            return getCostItemsFromResultSet(rs);

        } catch (SQLException e) {
            throw new CostManagerException(e.getMessage());
        }

        finally
        {
            closeConnection();
        }
    }

    /**
     * Gets all Categories from the database.
     * @return Category[] The result array of categories.
     * @throws CostManagerException If the process failed.
     */
    @Override
    public Category[] getCategories() throws CostManagerException
    {
        ResultSet rs;

        try
        {
            setConnection();
            rs = _statement.executeQuery("SELECT * FROM Categories");

            int amountOfCostItems = 0;
            while(rs.next())
            {
                amountOfCostItems++;
            }

            final Category[] categories = new Category[amountOfCostItems];
            rs.beforeFirst();

            int i = 0;
            while(rs.next())
            {
                categories[i] = new Category(rs.getString("name"));
                i++;
            }

            return categories;

        } catch (SQLException e)
        {
            throw new CostManagerException(e.getMessage());
        }

        finally
        {
            closeConnection();
        }
    }

    /**
     * Deletes costTable from the database.
     * @return Operation failed or success.
     * @throws CostManagerException If the process failed.
     **/
    @Override
    public Operation deleteTables() throws CostManagerException
    {
        Operation result = null;
        try
        {
            setConnection();
            boolean costTableResult = _statement.execute("DROP TABLE costTable");
            boolean categoryTableResult = _statement.execute("DROP TABLE Categories");
            System.out.println("Table deleted");

            if(!costTableResult && !categoryTableResult)
                result = Operation.SUCCESS;
            else
                result = Operation.FAILED;

        } catch (SQLException e) {
            result = Operation.FAILED;
            throw new CostManagerException(e.getMessage());
        }

        finally
        {
            closeConnection();
            return result;
        }
    }

    /**
     * Create costTable Table at derby server
     * @return Operation failed or success.
     * @throws CostManagerException If the process failed.
     */
    @Override
    public Operation createTables() throws CostManagerException
    {
        Operation result = null;
        try
        {
            setConnection();
            boolean costTableResult = _statement.execute("CREATE TABLE costTable(id INT,date VARCHAR(255),name VARCHAR(255),price DOUBLE,currency VARCHAR(255),category VARCHAR(255),note VARCHAR(255))");
            boolean categoryResult = _statement.execute("CREATE TABLE Categories(name VARCHAR(255))");
            System.out.println("Tables created");

            if(!costTableResult && !categoryResult)
                result = Operation.SUCCESS;
            else
                result = Operation.FAILED;

        } catch (SQLException e) { // Probably the tables are already exist
            result = Operation.FAILED;
            throw new CostManagerException(e.getMessage());
        }

        finally
        {
            closeConnection();
            return result;
        }
    }

    /**
     * Create the connection and sets the statement.
     * @throws CostManagerException If the process failed.
     **/
    private void setConnection() throws CostManagerException
    {
        try{
            _statement = _connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            throw new CostManagerException(e.getMessage());
        }
    }

    /**
     * Closes the derby server connection.
     * @throws CostManagerException If the process failed.
     **/
    private void closeConnection() throws CostManagerException
    {
        try{
            if(_statement != null)
                _statement.close();
        } catch (SQLException e) {
            throw new CostManagerException(e.getMessage());
        }
    }

    /**
     * Extracts all CostItems from ResultSet.
     * @param rs The resultSet to extract from.
     * @return CostItem[] The costItems result array.
     * @throws CostManagerException If the process failed.
     **/
    private CostItem[] getCostItemsFromResultSet(ResultSet rs) throws CostManagerException
    {
        try
        {
            int amountOfCostItems = 0;
            while(rs.next())
            {
                amountOfCostItems++;
            }
            final CostItem[] costItems = new CostItem[amountOfCostItems];
            rs.beforeFirst();
            int i = 0;
            while(rs.next())
            {
                costItems[i] = new CostItem(rs.getInt("id"), rs.getString("date"), rs.getString("name"), rs.getDouble("price"), rs.getString("currency"), rs.getString("category"), rs.getString("note"));
                i++;
            }

            return costItems;

        } catch (SQLException e) {
            throw new CostManagerException(e.getMessage());
        }
    }
}