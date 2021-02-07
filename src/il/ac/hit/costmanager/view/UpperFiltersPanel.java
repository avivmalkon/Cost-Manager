package il.ac.hit.costmanager.view;

import il.ac.hit.costmanager.utils.Category;
import il.ac.hit.costmanager.utils.Currency;
import il.ac.hit.costmanager.viewmodel.IViewModel;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UpperFiltersPanel
{
    private final JPanel mainPanel;
    private final JTable tbCategories;
    private final JTable tbCurrencies;
    private final JTable tbYears;
    private final JTable tbMonths;
    private final JButton btApplyFilters;
    private final JButton btClearFilters;
    private IViewModel viewModel;
    private final ArrayList<Integer> years;

    /**
     * Creates the upper panel on the main frame, including the filter tables.
     */
    public UpperFiltersPanel()
    {
        years = new ArrayList<>();
        mainPanel = new JPanel();
        mainPanel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Filter By",
                TitledBorder.CENTER,
                TitledBorder.TOP));

        tbCategories = new JTable();
        tbCurrencies = new JTable();
        tbYears = new JTable();
        tbMonths = new JTable();

        btClearFilters = new JButton("Clear Filters");
        btClearFilters.setBackground(Color.orange);
        btClearFilters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clearFilters();
                applyFilters();
            }
        });

        btApplyFilters = new JButton("Apply Filters");
        btApplyFilters.setBackground(Color.cyan);
        btApplyFilters.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                applyFilters();
            }
        });
    }

    /**
     * Operates the model (via the viewModel) to get the desired expenses according to the user's filtering.
     */
    public void applyFilters()
    {
        int rows;

        // Taking all the checked categories into the selectedCategories array:
        ArrayList<Category> selectedCategoriesAL = new ArrayList<>();
        rows = tbCategories.getModel().getRowCount();
        for(int i = 0; i < rows; i++)
        {
            if ((boolean) tbCategories.getValueAt(i, 0))
            {
                String strCategory = (String) tbCategories.getValueAt(i, 1);
                Category currCategory = new Category(strCategory);
                selectedCategoriesAL.add(currCategory);
            }
        }
        Category[] selectedCategories = selectedCategoriesAL.toArray(new Category[rows]);


        // Taking all the checked currencies into the selectedCurrencies array:
        ArrayList<Currency> selectedCurrenciesAL = new ArrayList<>();
        rows = tbCurrencies.getModel().getRowCount();
        for(int i = 0; i < rows; i++)
        {
            if ((boolean) tbCurrencies.getValueAt(i, 0))
            {
                String strCurrency = (String) tbCurrencies.getValueAt(i, 1);
                Currency currCurrency = Currency.valueOf(strCurrency);
                selectedCurrenciesAL.add(currCurrency);
            }
        }
        Currency[] selectedCurrencies = selectedCurrenciesAL.toArray(new Currency[rows]);


        // Taking all the checked years into the selectedYears array:
        ArrayList<Integer> selectedYearsAL = new ArrayList<>();
        rows = tbYears.getModel().getRowCount();
        for (int i = 0; i < rows; i++)
        {
            if ((boolean) tbYears.getValueAt(i, 0))
            {
                selectedYearsAL.add(Integer.valueOf((String) tbYears.getValueAt(i, 1)));
            }
        }
        int[] selectedYears = selectedYearsAL.stream().mapToInt(Integer::intValue).toArray();


        // Taking all the checked months into the selectedMonths array:
        ArrayList<Integer> selectedMonthsAL = new ArrayList<>();
        rows = tbMonths.getModel().getRowCount();
        for (int i = 0; i < rows; i++)
        {
            if ((boolean) tbMonths.getValueAt(i, 0))
            {
                selectedMonthsAL.add(i+1);
            }
        }
        int[] selectedMonths = selectedMonthsAL.stream().mapToInt(Integer::intValue).toArray();


        UpperFiltersPanel.this.viewModel.showFilteredExpenses(selectedCategories, selectedCurrencies, selectedYears, selectedMonths);
    }

    /**
     * Returns a Jpanel filled with the desired window.
     * @return a Jpanel filled with the desired window.
     */
    public JPanel createWindow()
    {
        mainPanel.setLayout(new GridBagLayout());

        String[] columns = {"", "Categories"};

        Object[][] data = null;

        settingTable(tbCategories, columns, data);

        columns = new String[]{"", "Currencies"};
        data = new Object[][]{
                {false, "ILS"},
                {false, "USD"},
                {false, "EUR"},
                {false, "GBP"}};
        settingTable(tbCurrencies, columns, data);


        columns = new String[]{"", "Years"};
        data = null;

        settingTable(tbYears, columns, data);

        columns = new String[]{"", "Months"};
        data = new Object[][]{
                {false, "January"},
                {false, "February"},
                {false, "March"},
                {false, "April"},
                {false, "May"},
                {false, "June"},
                {false, "July"},
                {false, "August"},
                {false, "September"},
                {false, "October"},
                {false, "November"},
                {false, "December"},};
        settingTable(tbMonths, columns, data);



        GridBagConstraints btApplyGridBag = new GridBagConstraints();
        //btApplyGridBag.weighty = 5;
        btApplyGridBag.gridx = 0;
        btApplyGridBag.insets = new Insets(8, 10, 4, 10);
        btApplyGridBag.gridwidth = GridBagConstraints.CENTER;
        btApplyGridBag.fill = GridBagConstraints.CENTER;

        JPanel filtersButtonsPanel = new JPanel();
        filtersButtonsPanel.setLayout(new GridLayout());
        filtersButtonsPanel.add(btClearFilters);
        filtersButtonsPanel.add(Box.createHorizontalStrut(0));
        filtersButtonsPanel.add(btApplyFilters);

        mainPanel.add(filtersButtonsPanel, btApplyGridBag);

        return mainPanel;
    }

    private void settingTable(JTable table, String[] columns, Object[][] data)
    {
        GridBagConstraints tablesGridBag = new GridBagConstraints();
        tablesGridBag.weightx = 0;
        tablesGridBag.insets = new Insets(0, 5, 0, 5);
        tablesGridBag.gridwidth = GridBagConstraints.HORIZONTAL;
        tablesGridBag.fill = GridBagConstraints.CENTER;


        DefaultTableModel model = new DefaultTableModel(data, columns)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                return getValueAt(0, columnIndex).getClass();
            }
        };

        table.setModel(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        table.getColumnModel().getColumn(0).setMaxWidth(25);
        table.getColumnModel().getColumn(0).setResizable(false);

        table.setRowHeight(22);

        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setPreferredSize(new Dimension(235, 155));
        jScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

        mainPanel.add(jScrollPane, tablesGridBag);
    }

    /**
     * Adds category to the categories filter table.
     * @param category The category to add.
     */
    public void addCategory(Category category)
    {
        DefaultTableModel defaultTableModel = (DefaultTableModel)tbCategories.getModel();
        Object[] row = new Object[2];

        row[0] = false;
        row[1] = category.getName();
        defaultTableModel.addRow(row);

        tbCategories.setModel(defaultTableModel);
    }

    /**
     * Adds year to the years arrayList and filter table (if not already exist).
     * @param year The year to add to the years arrayList filter table.
     */
    public void addYear(int year)
    {
        boolean yearExist = false;

        if (years != null)
        {
            for (Integer integer : years)
            {
                if (year == integer)
                {
                    yearExist = true;
                    break;
                }
            }

            if (!yearExist)
            {
                years.add(year);

                DefaultTableModel defaultTableModel = (DefaultTableModel) tbYears.getModel();
                Object[] row =  new Object[2];

                row[0] = false;
                row[1] = String.valueOf(year);

                defaultTableModel.addRow(row);
                tbYears.setModel(defaultTableModel);
            }
        }
    }

    /**
     * Adds category to the categories filter table (if not already exist).
     * @param categories The category to add to the filter table.
     */
    public void updateCategoriesData(Category[] categories)
    {
        DefaultTableModel defaultTableModel = (DefaultTableModel)tbCategories.getModel();
        Object[] row = new Object[2];
        for (Category category : categories)
        {
            row[0] = false;
            row[1] = category.getName();

            defaultTableModel.addRow(row);
        }
        tbCategories.setModel(defaultTableModel);
    }

    /**
     * Adds years to the years arrayList and filter table (if not already exist).
     * @param yearsToUpdate An array of years to add to the years arrayList filter table.
     */
    public void updateYears(int[] yearsToUpdate)
    {
        for (int j : yearsToUpdate)
        {
            addYear(j);
        }
    }

    /**
     * Sets the viewModel instance.
     * @param viewModel The viewModel to set.
     */
    public void setViewModel(IViewModel viewModel)
    {
        this.viewModel = viewModel;
    }

    /**
     * Resets all the filters.
     */
    public void clearFilters()
    {
        int rows;

        rows = tbCategories.getModel().getRowCount();
        for(int i = 0; i < rows; i++)
        {
            tbCategories.setValueAt(false, i, 0);
        }

        rows = tbCurrencies.getModel().getRowCount();
        for(int i = 0; i < rows; i++)
        {
            tbCurrencies.setValueAt(false, i, 0);
        }

        rows = tbYears.getModel().getRowCount();
        for(int i = 0; i < rows; i++)
        {
            tbYears.setValueAt(false, i, 0);
        }

        rows = tbMonths.getModel().getRowCount();
        for(int i = 0; i < rows; i++)
        {
            tbMonths.setValueAt(false, i, 0);
        }
    }

    /**
     * Operates the model (via the viewModel) to delete the user's selected expenses, and removes it from the categories filter table.
     */
    public void deleteSelectedCategories()
    {
        ArrayList<Category> selectedCategoriesAL = new ArrayList<>();
        ArrayList<Integer> indexToDelete = new ArrayList<>();
        int rows = tbCategories.getModel().getRowCount();
        for(int i = 0; i < rows; i++)
        {
            if ((boolean) tbCategories.getValueAt(i, 0))
            {
                String strCategory = (String) tbCategories.getValueAt(i, 1);
                Category currCategory = new Category(strCategory);
                selectedCategoriesAL.add(currCategory);
                indexToDelete.add(i);
            }
        }
        Category[] selectedCategories = selectedCategoriesAL.toArray(new Category[rows]);
        viewModel.deleteCategories(selectedCategories,indexToDelete);
    }

    /**
     * Removes categories from the categories filter table.
     * @param indexesToDelete An arrayList of the indexes of the categories to delete from the categories filter table.
     */
    public void removeCategories(ArrayList<Integer> indexesToDelete)
    {

        DefaultTableModel defaultTableModel = (DefaultTableModel)tbCategories.getModel();
        for (int i = indexesToDelete.size() - 1; i >= 0; i--)
        {
            defaultTableModel.removeRow(indexesToDelete.get(i));
        }
        tbCategories.setModel(defaultTableModel);
    }
}
