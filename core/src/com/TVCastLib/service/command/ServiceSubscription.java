/*
 * ServiceSubscription
 * TVCastLib
 * 
 * Copyright (c) 2014 Hamed Ghaderipour.
 * Created by Hamed Ghaderipour on 19 Jan 2014
 * 

 */

package com.TVCastLib.service.command;

import java.util.List;

public interface ServiceSubscription<T> {
    public void unsubscribe();

    public T addListener(T listener);

    public void removeListener(T listener);

    public List<T> getListeners();
}
