package il.ac.hit.costmanager.view;

import il.ac.hit.costmanager.utils.*;
import il.ac.hit.costmanager.utils.Currency;
import il.ac.hit.costmanager.utils.Date;
import il.ac.hit.costmanager.viewmodel.IViewModel;
import org.jdatepicker.impl.*;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class AddCostItemWindow
{
    private final JFrame _frameAddCostItem;
    private final IViewModel _viewModel;
    private final ApplicationUI _applicationUI;
    private final JPanel addCostItemPanel;
    private final JButton btAddCostItem;
    private final JButton btCancel;
    private final JTextField tfItemDescription;
    private final JTextField tfItemPrice;
    private final JTextField tfItemNote;
    private final JLabel lbItemCurrency;
    private final JLabel lbItemCategory;
    private final JLabel lbItemDate;
    private final JLabel lbItemDescription;
    private final JLabel lbItemPrice;
    private final JLabel lbItemNote;
    private final JDatePickerImpl datePicker;
    private final JComboBox<Currency> cbItemCurrency;
    private final JComboBox<Category> cbItemCategory;
    private final Category[] categories;
    private final Currency[] currencies = {Currency.ILS, Currency.USD, Currency.EUR, Currency.GBP};
    private Date chosenDate;
    private Category chosenCategory;
    private Currency chosenCurrency = Currency.ILS;

    /**
     * Creates a window that allows the user to create new costItems.
     * @param frameAddCostItem The Jframe for the window.
     * @param categories An array of the existing categories to choose from, when creating new costItem.
     * @param viewModel The instance of the viewModel.
     * @param applicationUI The instance of the applicationUI.
     */
    public AddCostItemWindow(JFrame frameAddCostItem, Category[] categories, IViewModel viewModel, ApplicationUI applicationUI)
    {
        _applicationUI = applicationUI;
        _viewModel = viewModel;
        _frameAddCostItem = frameAddCostItem;
        this.categories = categories;
        addCostItemPanel = new JPanel();
        btAddCostItem = new JButton("Add Cost Item");
        btCancel = new JButton("Cancel");
        tfItemDescription = new JTextField(20);
        tfItemPrice = new JTextField(8);
        tfItemNote = new JTextField(20);
        lbItemDescription = new JLabel("Description:");
        lbItemPrice = new JLabel("Price:");
        lbItemNote = new JLabel("Note:");
        lbItemCurrency = new JLabel("Currency:");
        cbItemCurrency = new JComboBox<>(currencies);
        lbItemCategory = new JLabel("Category:");
        cbItemCategory = new JComboBox<>(this.categories);
        lbItemDate = new JLabel("Date:");
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        ((AbstractDocument)tfItemPrice.getDocument()).setDocumentFilter(new NumbersOnlyDocumentFilter()); // setting filter for price which lets the user type only numbers in the price text field

        cbItemCurrency.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) // Handling the user's choice of currency
            {
                chosenCurrency = currencies[cbItemCurrency.getSelectedIndex()];
            }
        });

        cbItemCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) // // Handling the user's choice of category
            {
                chosenCategory = categories[cbItemCategory.getSelectedIndex()];
            }
        });
    }

    /**
     * Returns a Jpanel filled with the desired window.
     * @return a Jpanel filled with the desired window.
     */
    public JPanel createWindow()
    {
        chosenCategory = categories[0];
        chosenDate = null;
        addCostItemPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 0, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        addCostItemPanel.add(lbItemDescription, gbc);
        gbc.gridx = 1;
        addCostItemPanel.add(tfItemDescription, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        addCostItemPanel.add(lbItemPrice, gbc);
        gbc.gridx = 1;
        addCostItemPanel.add(tfItemPrice, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        addCostItemPanel.add(lbItemCurrency, gbc);
        gbc.gridx = 1;
        addCostItemPanel.add(cbItemCurrency, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        addCostItemPanel.add(lbItemCategory, gbc);
        gbc.gridx = 1;
        addCostItemPanel.add(cbItemCategory, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        addCostItemPanel.add(lbItemNote, gbc);
        gbc.gridx = 1;
        addCostItemPanel.add(tfItemNote, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        addCostItemPanel.add(lbItemDate,gbc);
        gbc.gridx = 1;
        addCostItemPanel.add(datePicker,gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btAddCostItem);
        buttonPanel.add(btCancel);
        addCostItemPanel.add(buttonPanel, gbc);

        btAddCostItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) // Handling sending CostItem to the ViewModel in order to add it to the table
            {
                String chosenDescription = tfItemDescription.getText();
                String chosenPriceStr = tfItemPrice.getText();

                if(chosenDescription.replaceAll("\\s+","").equals("") || chosenPriceStr.replaceAll("\\s+","").equals(""))
                {
                    _applicationUI.displayMessage("Wrong input, please try again.");
                }
                else
                {
                    String chosenNote = tfItemNote.getText();
                    double chosenPrice = Double.parseDouble(chosenPriceStr);

                    chosenDate = new Date(datePicker.getModel().getDay(), datePicker.getModel().getMonth() + 1, datePicker.getModel().getYear());

                    CostItem costItemToAdd = new CostItem(chosenDate, chosenDescription, chosenPrice, chosenCurrency, chosenCategory, chosenNote);
                    _viewModel.addCostItem(costItemToAdd);
                    _frameAddCostItem.setVisible(false);
                }
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _frameAddCostItem.setVisible(false);
                _frameAddCostItem.dispose();
            }
        });

        return addCostItemPanel;
    }

    private static class NumbersOnlyDocumentFilter extends DocumentFilter
    {
        private final Pattern regEx;

        public NumbersOnlyDocumentFilter()
        {
            regEx = Pattern.compile("\\d*");
        }


        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
        {
            Matcher matcher = regEx.matcher(text);
            if (!matcher.matches()) {
                return;
            }
            try {
                super.replace(fb, offset, length, text, attrs);
            } catch (BadLocationException e)
            {
                e.printStackTrace();
                try {
                    throw new CostManagerException(e.getMessage());
                } catch (CostManagerException costManagerException)
                {
                    costManagerException.printStackTrace();
                }
            }
        }
    }

    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter
    {

        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException
        {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value)
        {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }
    }
}



