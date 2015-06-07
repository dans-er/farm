package nl.knaw.dans.farm;

public class ProcessingException extends FarmException
{

    /**
     * 
     */
    private static final long serialVersionUID = -5372959925631698268L;

    public ProcessingException()
    {
    }

    public ProcessingException(String message)
    {
        super(message);
    }

    public ProcessingException(Throwable cause)
    {
        super(cause);
    }

    public ProcessingException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
