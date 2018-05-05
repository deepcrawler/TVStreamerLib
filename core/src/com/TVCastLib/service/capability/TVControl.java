/*
 * TVControl
 * TVCastLib
 * 
 * Copyright (c) 2014 Hamed Ghaderipour.
 * Created by Hamed Ghaderipour on 19 Jan 2014
 * 

 */

package com.TVCastLib.service.capability;

import com.TVCastLib.core.ChannelInfo;
import com.TVCastLib.core.ProgramInfo;
import com.TVCastLib.core.ProgramList;
import com.TVCastLib.service.capability.listeners.ResponseListener;
import com.TVCastLib.service.command.ServiceSubscription;

import java.util.List;

public interface TVControl extends CapabilityMethods {
    public final static String Any = "TVControl.Any";

    public final static String Channel_Get = "TVControl.Channel.Get";
    public final static String Channel_Set = "TVControl.Channel.Set";
    public final static String Channel_Up = "TVControl.Channel.Up";
    public final static String Channel_Down = "TVControl.Channel.Down";
    public final static String Channel_List = "TVControl.Channel.List";
    public final static String Channel_Subscribe = "TVControl.Channel.Subscribe";
    public final static String Program_Get = "TVControl.Program.Get";
    public final static String Program_List = "TVControl.Program.List";
    public final static String Program_Subscribe = "TVControl.Program.Subscribe";
    public final static String Program_List_Subscribe = "TVControl.Program.List.Subscribe";
    public final static String Get_3D = "TVControl.3D.Get";
    public final static String Set_3D = "TVControl.3D.Set";
    public final static String Subscribe_3D = "TVControl.3D.Subscribe";

    public final static String[] Capabilities = {
        Channel_Get,
        Channel_Set,
        Channel_Up,
        Channel_Down,
        Channel_List,
        Channel_Subscribe,
        Program_Get,
        Program_List,
        Program_Subscribe,
        Program_List_Subscribe,
        Get_3D,
        Set_3D,
        Subscribe_3D
    };

    public TVControl getTVControl();
    public CapabilityPriorityLevel getTVControlCapabilityLevel();

    public void channelUp(ResponseListener<Object> listener);
    public void channelDown(ResponseListener<Object> listener);

    public void setChannel(ChannelInfo channelNumber, ResponseListener<Object> listener);

    public void getCurrentChannel(ChannelListener listener);
    public ServiceSubscription<ChannelListener> subscribeCurrentChannel(ChannelListener listener);

    public void getChannelList(ChannelListListener listener);

    public void getProgramInfo(ProgramInfoListener listener);
    public ServiceSubscription<ProgramInfoListener> subscribeProgramInfo(ProgramInfoListener listener);

    public void getProgramList(ProgramListListener listener);
    public ServiceSubscription<ProgramListListener> subscribeProgramList(ProgramListListener listener);

    public void get3DEnabled(State3DModeListener listener);
    public void set3DEnabled(boolean enabled, ResponseListener<Object> listener);
    public ServiceSubscription<State3DModeListener> subscribe3DEnabled(State3DModeListener listener);

    /**
     * Success block that is called upon successfully getting the TV's 3D mode
     *
     * Passes a Boolean to see Whether 3D mode is currently enabled on the TV
     */
    public static interface State3DModeListener extends ResponseListener<Boolean> { }

    /**
     * Success block that is called upon successfully getting the current channel's information.
     *
     * Passes a ChannelInfo object containing information about the current channel
     */
    public static interface ChannelListener extends ResponseListener<ChannelInfo>{ }

    /**
     * Success block that is called upon successfully getting the channel list.
     *
     * Passes a List of ChannelList objects for each available channel on the TV
     */
    public static interface ChannelListListener extends ResponseListener<List<ChannelInfo>>{ }

    /**
     * Success block that is called upon successfully getting the current program's information.
     *
     * Passes a ProgramInfo object containing information about the current program
     */
    public static interface ProgramInfoListener extends ResponseListener<ProgramInfo> { }

    /**
     * Success block that is called upon successfully getting the program list for the current channel.
     *
     * Passes a ProgramList containing a ProgramInfo object for each available program on the TV's current channel
     */
    public static interface ProgramListListener extends ResponseListener<ProgramList> { }
}
