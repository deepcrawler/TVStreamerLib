/*
 * CastServiceChannel
 * TVCastLib
 * 
 * Copyright (c) 2014 Hamed Ghaderipour.
 * Created by Hamed Ghaderipour on 24 Feb 2014
 * 

 */

package com.TVCastLib.service.google_cast;

import android.support.annotation.NonNull;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.CastDevice;

import com.TVCastLib.core.Util;
import com.TVCastLib.service.sessions.CastWebAppSession;
import com.TVCastLib.service.sessions.WebAppSessionListener;

import org.json.JSONException;
import org.json.JSONObject;

public class CastServiceChannel implements Cast.MessageReceivedCallback{
    final String webAppId;
    final CastWebAppSession session;

    public CastServiceChannel(String webAppId, @NonNull CastWebAppSession session) {
        this.webAppId = webAppId;
        this.session = session;
    }

    public String getNamespace() {
        return "urn:x-cast:com.TVCastLib";
    }

    @Override
    public void onMessageReceived(CastDevice castDevice, String namespace, final String message) {
        final WebAppSessionListener webAppSession = session.getWebAppSessionListener();
        if (webAppSession == null) {
            return;
        }

        JSONObject messageJSON = null;

        try {
            messageJSON = new JSONObject(message);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        final JSONObject mMessage = messageJSON;

        Util.runOnUI(new Runnable() {

            @Override
            public void run() {
                if (mMessage == null) {
                    webAppSession.onReceiveMessage(session, message);
                } else {
                    webAppSession.onReceiveMessage(session, mMessage);
                }
            }
        });
    }
}
