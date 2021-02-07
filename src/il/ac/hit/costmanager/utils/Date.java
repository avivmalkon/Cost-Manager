package il.ac.hit.costmanager.utils;

public class Date
{
    private int year;
    private int month;
    private int day;

    /**
     * Creates new date.
     * @param day The day in the month (1-31).
     * @param month The month (1-12).
     * @param year The year.
     */
    public Date(int day, int month, int year)
    {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    /**
     * Creates new date.
     * @param date The date as string (format: "dd/mm/yyyy").
     */
    public Date(String date)
    {
        StringBuilder element = new StringBuilder();
        int day, month, year;

        int i = 0;
        while (i < date.length() && date.charAt(i) != '/')
        {
            element.append(date.charAt(i));
            i++;
        }
        i++;
        day = Integer.parseInt(element.toString());

        element = new StringBuilder();

        while (i < date.length() && date.charAt(i) != '/')
        {
            element.append(date.charAt(i));
            i++;
        }
        i++;
        month = Integer.parseInt(element.toString());

        element = new StringBuilder();

        while (i < date.length())
        {
            element.append(date.charAt(i));
            i++;
        }
        year = Integer.parseInt(element.toString());

        //this(day, month, year);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Returns the date's year.
     * @return the date's year.
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Sets a new year to the date.
     * @param year The new year.
     */
    private void setYear(int year)
    {
        if(year > 0)
            this.year = year;
    }

    /**
     * Returns the date's month.
     * @return the date's month.
     */
    public int getMonth()
    {
        return month;
    }

    /**
     * Sets a new month to the date.
     * @param month The new month (1-12).
     */
    private void setMonth(int month)
    {
        if(month >= 1 && month <= 12)
            this.month = month;
    }

    /**
     * Returns the date's day.
     * @return the date's day.
     */
    public int getDay()
    {
        return day;
    }

    /**
     * Sets a new day to the date.
     * @param day The new day (1-31).
     */
    private void setDay(int day)
    {
        if(day >= 1 && day <= 31)
            this.day = day;
    }

    /**
     * Returns a string representation of the date.
     * @return a string representation of the date.
     */
    @Override
    public String toString()
    {
        StringBuilder day = new StringBuilder();
        StringBuilder month = new StringBuilder();

        if(getDay() <= 9)
            day.append("0");
        day.append(getDay());

        if(getMonth() <= 9)
            month.append("0");
        month.append(getMonth());

        return day.toString() + "/" + month.toString() + "/" + getYear();
    }
}
