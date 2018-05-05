/*
 * PowerControl
 * TVCastLib
 * 
 * Copyright (c) 2014 Hamed Ghaderipour.
 * Created by Hamed Ghaderipour on 19 Jan 2014
 * 

 */

package com.TVCastLib.service.capability;

import com.TVCastLib.service.capability.listeners.ResponseListener;

public interface PowerControl extends CapabilityMethods {
    public final static String Any = "PowerControl.Any";

    public final static String Off = "PowerControl.Off";
    public final static String On = "PowerControl.On";

    public final static String[] Capabilities = {
        Off,
        On
    };

    public PowerControl getPowerControl();
    public CapabilityPriorityLevel getPowerControlCapabilityLevel();

    public void powerOff(ResponseListener<Object> listener);
    public void powerOn(ResponseListener<Object> listener);
}
