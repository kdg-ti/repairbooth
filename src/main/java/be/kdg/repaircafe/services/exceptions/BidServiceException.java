package be.kdg.repaircafe.services.exceptions;

public class BidServiceException extends RuntimeException
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/presentation-layer/error-handling/

    public BidServiceException()
    {
        super();
    }

    public BidServiceException(String message)
    {
        super(message);
    }

    public BidServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public BidServiceException(Throwable cause)
    {
        super(cause);
    }

    protected BidServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

