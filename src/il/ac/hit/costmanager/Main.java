package il.ac.hit.costmanager;

import il.ac.hit.costmanager.utils.*;

public class Main
{
    public static void main(String[] args)
    {
        ComponentsBundle bundle = new ComponentsBundle();

        if(bundle.connect() == Operation.FAILED)
        {
            // Handling failure with app components connections
            bundle.getView().displayMessage("An error occurred. Please try to restart the app.");
        }
        else
        {
            // App components connections succeed
            bundle.getViewModel().updateTable();
            bundle.getViewModel().updateCategories();
        }
    }
}
