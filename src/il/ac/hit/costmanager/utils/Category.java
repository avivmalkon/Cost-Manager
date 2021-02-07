package il.ac.hit.costmanager.utils;

public class Category
{
    private String name;

    /**
     * Category constructor. Creates a new category.
     * @param name The name or description for the category.
     */
    public Category(String name)
    {
        setName(name);
    }

    /**
     * Gets the name of the category.
     * @return The name of the category.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets a new name for existing category.
     * @param name The new name for the category.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString()
    {
        return name;
    }
}
