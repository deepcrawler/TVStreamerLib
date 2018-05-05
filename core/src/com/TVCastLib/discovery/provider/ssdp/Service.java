/*
 * Service
 * TVCastLib
 * 
 * Copyright (c) 2014 Hamed Ghaderipour.
 * Copyright (c) 2011 stonker.lee@gmail.com https://code.google.com/p/android-dlna/
 * 

 */

package com.TVCastLib.discovery.provider.ssdp;

import java.util.List;

//import com.TVCastLib.core.upnp.parser.Parser;

public class Service {
    public static final String TAG = "service";
    public static final String TAG_SERVICE_TYPE = "serviceType";
    public static final String TAG_SERVICE_ID = "serviceId";
    public static final String TAG_SCPD_URL = "SCPDURL";
    public static final String TAG_CONTROL_URL = "controlURL";
    public static final String TAG_EVENTSUB_URL = "eventSubURL";

    public String baseURL;
    /* Required. UPnP service type. */
    public String serviceType;
    /* Required. Service identifier. */
    public String serviceId;
    /* Required. Relative URL for service description. */
    public String SCPDURL;
    /* Required. Relative URL for control. */
    public String controlURL;
    /* Relative. Relative URL for eventing. */
    public String eventSubURL;

    public List<Action> actionList;
    public List<StateVariable> serviceStateTable;

    /*
     * We don't get SCPD, control and eventSub descriptions at service creation.
     * So call this method first before you use the service.
     */
    public void init() {
//        Parser parser = Parser.getInstance();
    }
}