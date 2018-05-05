/*
 * ServiceCommandError
 * TVCastLib
 * 
 * Copyright (c) 2014 Hamed Ghaderipour.
 * Created by Hamed Ghaderipour on 19 Jan 2014
 * 

 */

package com.TVCastLib.service.command;

/**
 * This class implements base service error which is based on HTTP response codes
 */
public class ServiceCommandError extends Error {

    private static final long serialVersionUID = 4232138682873631468L;

    protected int code;

    protected Object payload;

    public ServiceCommandError() {
    }

    public ServiceCommandError(String detailMessage) {
        super(detailMessage);
    }

    public ServiceCommandError(int code, String detailMessage) {
        super(detailMessage);
        this.code = code;
    }

    public ServiceCommandError(int code, String desc, Object payload) {
        super(desc);
        this.code = code;
        this.payload = payload;
    }

    /**
     * Create an error which indicates that feature is not supported by a service
     * @return NotSupportedServiceCommandError
     */
    public static ServiceCommandError notSupported() {
        return new NotSupportedServiceCommandError();
    }

    /**
     * Create an error from HTTP response code
     * @param code HTTP response code
     * @return ServiceCommandError
     */
    public static ServiceCommandError getError(int code) {
        String desc = null;
        if (code == 400) {
            desc = "Bad Request";
        }
        else if (code == 401) {
            desc = "Unauthorized";
        }
        else if (code == 500) {
            desc = "Internal Server Error";
        }
        else if (code == 503) {
            desc = "Service Unavailable";
        }
        else {
            desc = "Unknown Error";
        }

        return new ServiceCommandError(code, desc, null);
    }

    public int getCode() {
        return code;
    }

    public Object getPayload() {
        return payload;
    }
}
