/*
 * ExternalInputControl
 * TVCastLib
 * 
 * Copyright (c) 2014 Hamed Ghaderipour.
 * Created by Hamed Ghaderipour on 19 Jan 2014
 * 

 */

package com.TVCastLib.service.capability;

import java.util.List;

import com.TVCastLib.core.ExternalInputInfo;
import com.TVCastLib.service.capability.Launcher.AppLaunchListener;
import com.TVCastLib.service.capability.listeners.ResponseListener;
import com.TVCastLib.service.sessions.LaunchSession;

public interface ExternalInputControl extends CapabilityMethods {
    public final static String Any = "ExternalInputControl.Any";

    public final static String Picker_Launch = "ExternalInputControl.Picker.Launch";
    public final static String Picker_Close = "ExternalInputControl.Picker.Close";
    public final static String List = "ExternalInputControl.List";
    public final static String Set = "ExternalInputControl.Set";

    public final static String[] Capabilities = {
        Picker_Launch,
        Picker_Close,
        List,
        Set
    };

    public ExternalInputControl getExternalInput();
    public CapabilityPriorityLevel getExternalInputControlPriorityLevel();

    public void launchInputPicker(AppLaunchListener listener);
    public void closeInputPicker(LaunchSession launchSessionm, ResponseListener<Object> listener);

    public void getExternalInputList(ExternalInputListListener listener);
    public void setExternalInput(ExternalInputInfo input, ResponseListener<Object> listener);

    /**
     * Success block that is called upon successfully getting the external input list.
     *
     * Passes a list containing an ExternalInputInfo object for each available external input on the device
     */
    public interface ExternalInputListListener extends ResponseListener<List<ExternalInputInfo>> { }
}
