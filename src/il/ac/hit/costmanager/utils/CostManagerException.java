package il.ac.hit.costmanager.utils;

public class CostManagerException extends Exception
{
    /**
     * Creates new CostManagerException.
     * @param message The exception message.
     */
    public CostManagerException(String message)
    {
        super(message);
    }

    /**
     * Creates new CostManagerException.
     * @param message The exception String message.
     * @param cause The exception Throwable cause.
     */
    public CostManagerException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
