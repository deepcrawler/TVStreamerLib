/*
 * NetcastTVServiceConfig
 * TVCastLib
 * 
 * Copyright (c) 2014 Hamed Ghaderipour.
 * Created by Hamed Ghaderipour on 19 Jan 2014
 * 

 */

package com.TVCastLib.service.config;

import org.json.JSONException;
import org.json.JSONObject;

public class NetcastTVServiceConfig extends ServiceConfig {
    public static final String KEY_PAIRING = "pairingKey";
    String pairingKey;

    public NetcastTVServiceConfig(String serviceUUID) {
        super(serviceUUID);
    }

    public NetcastTVServiceConfig(String serviceUUID, String pairingKey) {
        super(serviceUUID);
        this.pairingKey = pairingKey;
    }

    public NetcastTVServiceConfig(JSONObject json) {
        super(json);

        pairingKey = json.optString(KEY_PAIRING, null);
    }

    public String getPairingKey() {
        return pairingKey;
    }

    public void setPairingKey(String pairingKey) {
        this.pairingKey = pairingKey;
        notifyUpdate();
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObj = super.toJSONObject();

        try {
            jsonObj.put(KEY_PAIRING, pairingKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObj;
    }

}
