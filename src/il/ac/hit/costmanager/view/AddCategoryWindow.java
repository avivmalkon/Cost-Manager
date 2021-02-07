package il.ac.hit.costmanager.view;

import il.ac.hit.costmanager.utils.Category;
import il.ac.hit.costmanager.viewmodel.IViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCategoryWindow
{
    private final JFrame addCategoryFrame;
    private final IViewModel viewModel;
    private final ApplicationUI applicationUI;
    private final JPanel addCostItemPanel;
    private final JButton btAddCategory;
    private final JButton btCancel;
    private final JTextField tfCategory;
    private final JLabel lbCategory;

    /**
     * Creates a window that allows the user to create new categories.
     * @param frameCategory The Jframe for the window.
     * @param viewModel The instance of the viewModel.
     * @param applicationUI The instance of the applicationUI.
     */
    public AddCategoryWindow(JFrame frameCategory, IViewModel viewModel, ApplicationUI applicationUI)
    {
        this.applicationUI = applicationUI;
        this.viewModel = viewModel;
        this.addCategoryFrame = frameCategory;
        addCostItemPanel = new JPanel();
        btAddCategory = new JButton("Add Category");
        btCancel = new JButton("Cancel");
        tfCategory = new JTextField(20);
        lbCategory = new JLabel("Category:");
    }

    /**
     * Returns a Jpanel filled with the desired window.
     * @return a Jpanel filled with the desired window.
     */
    public JPanel createWindow()
    {
        addCostItemPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 0, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        addCostItemPanel.add(lbCategory, gbc);
        gbc.gridx = 1;
        addCostItemPanel.add(tfCategory, gbc);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btAddCategory);
        buttonPanel.add(btCancel);
        addCostItemPanel.add(buttonPanel, gbc);

        btAddCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // Handling sending CostItem to the ViewModel in order to add it to the table
                String chosenCategory = tfCategory.getText();
                if(chosenCategory.replaceAll("\\s+","").equals(""))
                {
                    applicationUI.displayMessage("Wrong input, please try again.");
                }
                else {
                    Category category = new Category(chosenCategory);
                    viewModel.addCategory(category);
                    addCategoryFrame.setVisible(false);
                    addCategoryFrame.dispose();
                }
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCategoryFrame.setVisible(false);
                addCategoryFrame.dispose();
            }
        });

        return addCostItemPanel;
    }
}
