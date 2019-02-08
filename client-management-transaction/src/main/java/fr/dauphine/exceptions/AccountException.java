/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.dauphine.exceptions;

/**
 *
 * @author jihane and zouhair
 */
public class AccountException extends Exception{

    public AccountException() {
        super("there is a problem with the concerned account");
    }
    
    public AccountException(String message) {
        super(message);
    }
    
    
    
}
