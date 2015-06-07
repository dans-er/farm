package nl.knaw.dans.farm;

public class FarmException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 5703687767149096055L;

    public FarmException()
    {
    }

    public FarmException(String message)
    {
        super(message);
    }

    public FarmException(Throwable cause)
    {
        super(cause);
    }

    public FarmException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public FarmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
