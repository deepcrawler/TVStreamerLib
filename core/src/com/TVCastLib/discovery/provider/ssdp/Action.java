/*
 * Action
 * TVCastLib
 * 
 * Copyright (c) 2014 Hamed Ghaderipour.
 * Copyright (c) 2011 stonker.lee@gmail.com https://code.google.com/p/android-dlna/
 * 

 */

package com.TVCastLib.discovery.provider.ssdp;

import java.util.List;


public class Action {
    /* Required. Name of action. */
    String mName;

    /* Required. */
    List<Argument> mArgumentList;

    public Action(String name) {
        this.mName = name;
    }
}