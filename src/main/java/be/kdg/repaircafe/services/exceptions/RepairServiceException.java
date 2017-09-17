/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.kdg.repaircafe.services.exceptions;

/**
 * @author wouter
 */
public class RepairServiceException extends RuntimeException
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/presentation-layer/error-handling/

    /**
     * This exception is thrown when a Repair couldn't be found i.e.
     *
     * @param message
     */
    public RepairServiceException(String message)
    {
        super(message);
    }

    public RepairServiceException(String message, IllegalStateException e)
    {
        super(message, e);
    }

}
