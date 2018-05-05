

package com.TVCastLib.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProgramList implements JSONSerializable {
    ChannelInfo channel;
    JSONArray programList;

    public ProgramList(ChannelInfo channel, JSONArray programList) {
        this.channel = channel;
        this.programList = programList;
    }

    public ChannelInfo getChannel() {
        return channel;
    }

    public JSONArray getProgramList() {
        return programList;
    }

    @Override
    public JSONObject toJSONObject() throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put("channel", channel != null ? channel.toString() : null);
        obj.put("programList", programList != null ? programList.toString() : null);

        return obj;
    }
}