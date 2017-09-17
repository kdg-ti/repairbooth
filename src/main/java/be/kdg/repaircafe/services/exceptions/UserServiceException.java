/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.kdg.repaircafe.services.exceptions;

/**
 * @author deketelw
 */
public class UserServiceException extends RuntimeException
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/presentation-layer/error-handling/

    /**
     * Deze exception wordt gesmeten wanneer iets fout gaat met gebruikers
     * Bijvoorbeeld: fout password of foute gebruikersnaam
     *
     * @param message
     */
    public UserServiceException(String message)
    {
        super(message);
    }
}
