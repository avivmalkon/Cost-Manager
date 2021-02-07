package il.ac.hit.costmanager.view;

import il.ac.hit.costmanager.utils.Category;
import il.ac.hit.costmanager.utils.CostItem;
import il.ac.hit.costmanager.viewmodel.IViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ApplicationUI
{
    private final JFrame frame;
    private final JPanel topPanel;
    private final JPanel upperBarPanel;
    private final JPanel centerPanel;
    private final JPanel bottomPanel;
    private final JButton btAddItemWindowOpen;
    private final JButton btDeleteExpenses;
    private final JButton btGetPieChart;
    private final JButton btAddCategory;
    private final JButton btDeleteCategory;
    private JTable costItemTable;
    private final JLabel lbTop;
    private final UpperFiltersPanel upperPanel;
    private IViewModel viewModel;
    private ArrayList<CostItem> costItemsAL;
    private ArrayList<Category> categoriesAL;

    /**
     * The ApplicationUI constructor. Initializing the variable.
     */
    public ApplicationUI()
    {
        frame = new JFrame("Cost Manager");
        upperPanel = new UpperFiltersPanel();
        upperBarPanel = upperPanel.createWindow();
        upperBarPanel.setPreferredSize(new Dimension(1000, 220));
        centerPanel = new JPanel();
        bottomPanel = new JPanel();
        topPanel = new JPanel();
        lbTop = new JLabel("The Cost Manager");
        btAddItemWindowOpen = new JButton("Add an expense (+)");
        btAddItemWindowOpen.setBackground(Color.green);
        btDeleteExpenses = new JButton("Delete expenses");
        btDeleteExpenses.setBackground(Color.red);
        btGetPieChart = new JButton("Get a pie chart");
        btGetPieChart.setBackground(Color.yellow);
        btAddCategory = new JButton("Add a category (+)");
        btAddCategory.setBackground(Color.green);
        btDeleteCategory = new JButton("Delete categories");
        btDeleteCategory.setBackground(Color.red);
    }

    /**
     * Displays the application's window on the user's screen
     */
    public void start()
    {
        // Config the bottom Panel
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(btAddCategory);
        bottomPanel.add(btDeleteCategory);
        bottomPanel.add(Box.createHorizontalStrut(120));
        bottomPanel.add(btGetPieChart);
        bottomPanel.add(Box.createHorizontalStrut(120));
        bottomPanel.add(btAddItemWindowOpen);
        bottomPanel.add(btDeleteExpenses);

        btAddItemWindowOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) // Handling the add costItem button pressed
            {
                if(categoriesAL.size() == 0) // Handling a situation where there are no categories
                {
                    JFrame frameNoCategoriesWindow = new JFrame("No Categories");
                    MessageWindow noCategoriesWindow = new MessageWindow(frameNoCategoriesWindow, "Please create at least one category before creating expenses.");
                    JPanel panelWindowMessage = noCategoriesWindow.createWindow();

                    frameNoCategoriesWindow.setLayout(new BorderLayout());
                    frameNoCategoriesWindow.add(panelWindowMessage, BorderLayout.CENTER);
                    frameNoCategoriesWindow.setSize(400, 125);
                    frameNoCategoriesWindow.setResizable(false);
                    frameNoCategoriesWindow.setLocationRelativeTo(frame);
                    frameNoCategoriesWindow.setVisible(true);
                }
                else
                {
                    createAddCostItemFrame();
                }
            }
        });

        btAddCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) // Handling the add category button pressed
            {
                createCategoryFrame();
            }
        });
        btDeleteCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) // Handling the add category button pressed
            {
                deleteCategories();
            }
        });

        btGetPieChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPieChartByCategory();// Handling creating the pie chart
            }
        });

        btDeleteExpenses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) // Handling deleting selected expenses
            {
                ArrayList<Integer> itemsToDeleteAL = new ArrayList<>();
                ArrayList<Integer> indexesToDeleteAL = new ArrayList<>();

                int rows = costItemsAL.size();
                for(int i = 0; i < rows; i++)
                {
                    if((boolean)costItemTable.getValueAt(i, 0)) //Deleting costItems where checkboxes is check
                    {
                        //itemsToDeleteAL.add((int)costItemTable.getValueAt(i, 1));
                        itemsToDeleteAL.add(costItemsAL.get(i).getId());
                        indexesToDeleteAL.add(i);
                    }
                }

                int[] itemsToDelete = new int[itemsToDeleteAL.size()];
                for(int i = 0 ; i < itemsToDelete.length; i++) //for(int i = itemsToDelete.length ; i > 0 ; i--)
                {
                    itemsToDelete[i] = itemsToDeleteAL.get(i);
                }

                viewModel.deleteCostItems(itemsToDelete,indexesToDeleteAL);
            }
        });

        //Config the Center Panel & table
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Expenses",
                TitledBorder.CENTER,
                TitledBorder.TOP));

        String[] headers = {"",/*"ID",*/ "Date (dd/mm/yyyy)", "Description","Price","Currency","Category","Note"};
        Object[][] data = null;
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, headers) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return getValueAt(0, columnIndex).getClass();
            }
        };

        costItemTable = new JTable();
        costItemTable.setModel(defaultTableModel);

        costItemTable.setRowHeight(22);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 1; i < headers.length; i++)
        {
            costItemTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        costItemTable.getColumnModel().getColumn(0).setMaxWidth(25);
        costItemTable.getColumnModel().getColumn(0).setResizable(false);

        JScrollPane jScrollPane = new JScrollPane(costItemTable);
        jScrollPane.setPreferredSize(new Dimension(980, 275));

        centerPanel.add(jScrollPane);


        topPanel.add(lbTop);
        //Config All Window
        frame.setLayout(new BorderLayout());
        frame.add(upperBarPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel,BorderLayout.SOUTH);

        frame.setMinimumSize(new Dimension(1010, 600));
        frame.setSize(new Dimension(1010, 600));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() // handling application exit
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                JFrame frameCloseWindow = new JFrame("Close Application");
                JPanel closePanel = ApplicationUI.CloseWindow.ShowExitAppDialog(frame, frameCloseWindow,"Are you sure you would like to exit ?");
                frameCloseWindow.setLayout(new BorderLayout());
                frameCloseWindow.add(closePanel, BorderLayout.CENTER);
                frameCloseWindow.setSize(300, 150);
                frameCloseWindow.setResizable(false);
                frameCloseWindow.setLocationRelativeTo(frame);
                frameCloseWindow.setVisible(true);
            }
        });
    }

    /**
     * Inserts the param costItem to the UI table.
     * @param costItem The costItem to insert to the UI table.
     */
    public void addCostItemToTable(CostItem costItem)
    {
        upperPanel.addYear(costItem.getDate().getYear());
        upperPanel.clearFilters();
        upperPanel.applyFilters();
        CostItem[] costItemArr = {costItem};
        updateCostItemData(costItemArr);
    }

    /**
     * Inserts the param categoryToAdd to the UI table.
     * @param categoryToAdd The category to insert to the UI table.
     */
    public void addCategoryToTable(Category categoryToAdd)
    {
        this.categoriesAL.add(categoryToAdd);
        upperPanel.addCategory(categoryToAdd);
    }

    /**
     * Deletes the checked (checkboxes) categories from the model and from the UI.
     */
    public void deleteCategories()
    {
        upperPanel.deleteSelectedCategories();
    }

    /**
     * Inserts a group of categories to the UI table.
     * @param categories The categories array to insert to the UI table.
     */
    public void updateCategoriesData(Category[] categories)
    {
        categoriesAL = new ArrayList<>(Arrays.asList(categories));
        upperPanel.updateCategoriesData(categories);
    }

    /**
     * Inserts a group of costItems to the UI table.
     * @param costItems The costItems array to insert to the UI table.
     */
    public void updateCostItemData(CostItem[] costItems)
    {
        costItemsAL = new ArrayList<>(Arrays.asList(costItems));
        int[] years = new int[costItems.length];
        for (int i = 0; i < costItems.length; i++)
        {
            years[i] = costItems[i].getDate().getYear();
        }
        upperPanel.updateYears(years);

        DefaultTableModel defaultTableModel1 = (DefaultTableModel)costItemTable.getModel();
        Object[] row = new Object[8];
        for (CostItem costItem : costItems) {
            row[0] = false;
            //row[1] = costItem.getId();
            row[1] = costItem.getDate();
            row[2] = costItem.getDescription();
            row[3] = costItem.getPrice();
            row[4] = costItem.getCurrency();
            row[5] = costItem.getCategory();
            row[6] = costItem.getNote();
            defaultTableModel1.addRow(row);
        }
        costItemTable.setModel(defaultTableModel1);
    }

    private void createAddCostItemFrame()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Category[] sizeArr = new Category[categoriesAL.size()]; // An empty array to convert the categoriesAL to array.

                JFrame frameAddCostItem = new JFrame("Add an expense");
                AddCostItemWindow costItemWindow = new AddCostItemWindow(frameAddCostItem, categoriesAL.toArray(sizeArr), viewModel, ApplicationUI.this);
                JPanel panelAddCostItem = costItemWindow.createWindow();
                frameAddCostItem.setLayout(new BorderLayout());
                frameAddCostItem.add(panelAddCostItem, BorderLayout.CENTER);
                frameAddCostItem.setSize(400, 250);
                frameAddCostItem.setResizable(false);
                frameAddCostItem.setLocationRelativeTo(frame);
                frameAddCostItem.setVisible(true);
            }
        });
    }

    private void createCategoryFrame()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frameAddCostItem = new JFrame("Create new Category");
                AddCategoryWindow addCategoryWindow = new AddCategoryWindow(frameAddCostItem, viewModel, ApplicationUI.this);
                JPanel panelAddCostItem = addCategoryWindow.createWindow();
                frameAddCostItem.setLayout(new BorderLayout());
                frameAddCostItem.add(panelAddCostItem, BorderLayout.CENTER);
                frameAddCostItem.setSize(350, 125);
                frameAddCostItem.setResizable(false);
                frameAddCostItem.setLocationRelativeTo(frame);
                frameAddCostItem.setVisible(true);
            }
        });
    }

    /**
     * Sets the viewModel instance.
     * @param viewModel The viewModel instance to set.
     */
    public void setViewModel(IViewModel viewModel)
    {
        this.viewModel = viewModel;
        upperPanel.setViewModel(viewModel);
    }

    /**
     * Gets an arrayList of all existing costItems.
     * @return All existing costItems.
     */
    public ArrayList<CostItem> getCostItemsAL()
    {
        return costItemsAL;
    }

    /**
     * Displays a messages window for the user
     * @param message The string message to display.
     */
    public void displayMessage(String message)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                JFrame frameWindowMessage = new JFrame("Warning");
                MessageWindow messageWindow = new MessageWindow(frameWindowMessage, message);
                JPanel panelWindowMessage = messageWindow.createWindow();

                frameWindowMessage.setLayout(new BorderLayout());
                frameWindowMessage.add(panelWindowMessage, BorderLayout.CENTER);
                frameWindowMessage.setSize(400, 125);
                frameWindowMessage.setResizable(false);
                frameWindowMessage.setLocationRelativeTo(frame);
                frameWindowMessage.setVisible(true);
            }
        });
    }

    /**
     * Deletes categories from the the UI table.
     * @param indexToDelete The indexes of the categories to delete from UI table.
     */
    public void removeCategories(ArrayList<Integer> indexToDelete)
    {
        for (int index : indexToDelete) {
            categoriesAL.remove(index);
        }
        upperPanel.removeCategories(indexToDelete);
    }

    /**
     * Deletes costItems from the the UI table.
     * @param indexesToDeleteAL The indexes of the costItems to delete from UI table.
     */
    public void removeCostItems(ArrayList<Integer> indexesToDeleteAL)
    {
        DefaultTableModel defaultTableModel = (DefaultTableModel)costItemTable.getModel();
        Integer[] intArray = indexesToDeleteAL.toArray(new Integer[0]);

        for(int i=intArray.length-1; i>=0; i--)
        {
            int index = intArray[i];
            defaultTableModel.removeRow(index);
            costItemsAL.remove(index);
        }

        costItemTable.setModel(defaultTableModel);
    }

    /**
     * Deletes all costItems from the UI table.
     */
    public void removeAllRows()
    {
        DefaultTableModel dm = (DefaultTableModel)costItemTable.getModel();
        int rowCount = dm.getRowCount();

        for (int i = rowCount - 1; i >= 0; i--)
        {
            dm.removeRow(i);
        }
    }

    /**
     * Creates a pie chart separated by categories.
     */
    private void createPieChartByCategory()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                DefaultPieDataset pieDataset = new DefaultPieDataset();
                Map<String, Integer> categoriesMap = new HashMap<>();
                categoriesMap.clear();
                for (CostItem costItem : costItemsAL)
                {
                    String categoryName = costItem.getCategory().getName();
                    if(categoriesMap.containsKey(categoryName))
                    {
                        categoriesMap.put(categoryName,categoriesMap.get(categoryName)+1);
                    }
                    else {
                        categoriesMap.put(categoryName, 1);
                    }
                }
                categoriesMap.forEach((k,v) -> pieDataset.setValue(k+"= "+v.toString(),v));
                JFreeChart chart = ChartFactory.createPieChart(
                        "Category Summery",
                        pieDataset,
                        true,
                        true,
                        false
                );
                ChartFrame picChartFrame = new ChartFrame("Pie Chart By Categories",chart);
                picChartFrame.setVisible(true);
                picChartFrame.setSize(450,500);
                picChartFrame.setResizable(false);
                picChartFrame.setLocationRelativeTo(frame);
            }
        });
    }

    /**
     * A class for creating a confirmation window when exiting the application.
     */
    private static class CloseWindow
    {
        /**
         * Displays a window to confirm user's intention the exis the app.
         * @param mainFrame The main Jframe of the app.
         * @param thisFrame The current Jframe.
         * @param message A string message to display on the window.
         * @return JPanel for the wanted window.
         */
        public static JPanel ShowExitAppDialog(JFrame mainFrame, JFrame thisFrame, String message)
        {
            final JFrame _thisFrame = thisFrame;
            final JFrame _mainFrame = mainFrame;
            JLabel lbMessage = new JLabel(message);
            JPanel messageWindowPanel = new JPanel();
            JButton btOk = new JButton("OK");
            JButton btCancel = new JButton("Cancel");

            messageWindowPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0;
            gbc.gridy = 0;
            messageWindowPanel.add(lbMessage, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(btOk);
            buttonPanel.add(btCancel);
            messageWindowPanel.add(buttonPanel, gbc);

            btOk.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    _thisFrame.setVisible(false);
                    _mainFrame.setVisible(false);
                    _thisFrame.dispose();
                    _mainFrame.dispose();
                    System.exit(0);
                }
            });

            btCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    _thisFrame.setVisible(false);
                    _thisFrame.dispose();
                }
            });

            return messageWindowPanel;
        }
    }
}
