package il.ac.hit.costmanager.utils;

public class CostItem
{
    private static int counter = 1;
    private int id;
    private String description;
    private double price;
    private Currency currency;
    private Category category;
    private String note;
    private Date date;

    /**
     * Creates new costItem.
     * @param date The date that the expense was made.
     * @param description A name for the expense.
     * @param price The price of the expense.
     * @param currency The currency the expense paid in.
     */
    public CostItem(String description, double price, Currency currency, Date date)
    {
        setId(counter);
        setDescription(description);
        setPrice(price);
        setCurrency(currency);
        setDate(date);
    }

    /**
     * Creates new costItem.
     * @param id The existing id of the expense in the database
     * @param date The date that the expense was made.
     * @param description A name for the expense.
     * @param price The price of the expense.
     * @param currency The currency the expense paid in.
     * @param category The category of the expense.
     * @param note Free text to describe the expense.
     */
    public CostItem(int id, String date, String description, double price, String currency, String category, String note)
    {
        setId(id);
        setDescription(description);
        setPrice(price);
        setCurrency(currency);
        setCategory(category);
        setNote(note);
        setDate(date);
    }

    /**
     * Creates new costItem.
     * @param date The date that the expense was made.
     * @param description A name for the expense.
     * @param price The price of the expense.
     * @param currency The currency the expense paid in.
     * @param category The category of the expense.
     * @param note Free text to describe the expense.
     */
    public CostItem(Date date, String description, double price, Currency currency,Category category,String note)
    {
        setId(counter);
        setDescription(description);
        setPrice(price);
        setCurrency(currency);
        setCategory(category);
        setNote(note);
        setDate(date);
    }

    /**
     * Returns the date of the costItem.
     * @return the date of the costItem.
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * Sets a new date for the costItem.
     * @param date The new date.
     */
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
     * Sets a new date for the costItem.
     * @param date The new date as string (format: "dd/mm/yyyy").
     */
    public void setDate(String date)
    {
        if(date.equals("null"))
        {
            this.date = new Date(1,1,1970);
            return;
        }
        this.date = new Date(date);
    }

    /**
     * Returns the category of the costItem.
     * @return the category of the costItem.
     */
    public Category getCategory()
    {
        return category;
    }

    /**
     * Sets a new category for the costItem.
     * @param category The new category.
     */
    public void setCategory(Category category)
    {
        this.category = category;
    }

    /**
     * Sets a new category for the costItem.
     * @param category The new category as a string.
     */
    public void setCategory(String category)
    {
        this.category = new Category(category);
    }

    /**
     * Returns the note of the costItem.
     * @return the note of the costItem.
     */
    public String getNote()
    {
        return note;
    }

    /**
     * Sets a new note for the costItem.
     * @param note The new note.
     */
    public void setNote(String note)
    {
        this.note = note;
    }

    /**
     * Returns the id of the costItem.
     * @return the id of the costItem.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets a new id for the costItem.
     * @param id The new id.
     */
    public void setId(int id)
    {
        this.id = id;
        counter++;
    }

    /**
     * Sets a new id for the costItem.
     * @param id The new id as string.
     */
    public void setId(String id)
    {
        this.id = Integer.parseInt(id);
    }

    /**
     * Returns the name of the costItem.
     * @return the name of the costItem.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets a new name for the costItem.
     * @param description The new name.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the price of the costItem.
     * @return the price of the costItem.
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * Sets a new price for the costItem.
     * @param price The nwe price.
     */
    public void setPrice(double price)
    {
        if(price < 0)
        {
            System.out.println(this.description + " Has wrong price");
            return;
        }
        this.price = price;
    }

    /**
     * Sets a new price for the costItem.
     * @param price The new price as string.
     */
    public void setPrice(String price)
    {
        double dblPrice = Double.parseDouble(price);
        setPrice(dblPrice);
    }

    /**
     * Returns the currency of the costItem.
     * @return the currency of the costItem.
     */
    public Currency getCurrency()
    {
        return currency;
    }

    /**
     * Sets a new currency for the costItem.
     * @param currency The new currency.
     */
    public void setCurrency(Currency currency)
    {
        switch (currency)
        {
            case ILS, USD, EUR, GBP -> this.currency = currency;
            default -> System.out.println("Wrong Currency");
        }
    }

    /**
     * Sets a new currency for the costItem.
     * @param currency The new currency as string.
     */
    public void setCurrency(String currency)
    {
        switch (currency)
        {
            case "ILS" -> this.currency = Currency.ILS;
            case "USD" -> this.currency = Currency.USD;
            case "GBP" -> this.currency = Currency.GBP;
            case "EUR" -> this.currency = Currency.EUR;
        }
    }

    /**
     * Returns a string representation of the costItem.
     * @return a string representation of the costItem.
     */
    @Override
    public String toString()
    {
        return "Cost Item - "+ description +
                " Price: " + price +
                " Currency: " + currency +
                " Category: " + category +
                " Note: "+ note +'}';
    }
}
