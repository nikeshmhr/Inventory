package com.nikesh.inventoryapi.constants;

import org.springframework.http.HttpMethod;

/**
 *
 * @author Nikesh Maharjan
 */
public class SystemConstants {

    /**
     * CONSTANTS OF ALL HttpMethod FOR ACCESS CONTROL ALLOW METHODS HEADER.
     */
    public static final HttpMethod[] ACCESS_CONTROL_ALLOW_METHODS = {HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};
    
    public interface TransactionTypeConstants {
        
        int PURCHASE = 1;
        int SALE = 2;
        int CASH_IN = 3;
        int CASH_OUT = 4;
        
    }

}
