package com.nikesh.inventoryapi.constants;

import org.springframework.http.HttpMethod;

/**
 *
 * @author Nikesh Maharjan
 */
public class HeaderMethodConstants {

    /**
     * CONSTANTS OF ALL HttpMethod FOR ACCESS CONTROL ALLOW METHODS HEADER.
     */
    public static final HttpMethod[] ACCESS_CONTROL_ALLOW_METHODS = {HttpMethod.GET, HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

}
