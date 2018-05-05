/*
 * SSDPPacket
 * TVCastLib
 * 
 * Copyright (c) 2014 Hamed Ghaderipour.
 * Created by Hamed Ghaderipour on 6 Jan 2015
 * 

 */

package com.TVCastLib.discovery.provider.ssdp;

import java.net.DatagramPacket;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class SSDPPacket {
    DatagramPacket datagramPacket;
    Map<String, String> data = new HashMap<String, String>();
    String type;
    static final Charset ASCII_CHARSET = Charset.forName("US-ASCII");
    static final String CRLF = "\r\n";
    static final String LF = "\n";

    public SSDPPacket(DatagramPacket datagramPacket) {
        this.datagramPacket = datagramPacket;

        String text = new String(datagramPacket.getData(), ASCII_CHARSET);

        int pos = 0;

	        int eolPos;

        if ((eolPos = text.indexOf(CRLF)) != -1) {
            pos = eolPos + CRLF.length();
        }
        else if ((eolPos = text.indexOf(LF)) != -1) {
            pos = eolPos + LF.length();
        }
        else 
            return;

        // Get first line
        type = text.substring(0, eolPos);

        while (pos < text.length()) {
            String line;
            if ((eolPos = text.indexOf(CRLF, pos)) != -1) {
                line = text.substring(pos, eolPos);
                pos = eolPos + CRLF.length();
            }
            else if ((eolPos = text.indexOf(LF, pos)) != -1) {
                line = text.substring(pos, eolPos);
                pos = eolPos + LF.length();
            }
            else 
                break;

            int index = line.indexOf(':');
            if (index == -1) {
                continue;
            }

            String key = asciiUpper(line.substring(0, index));
            String value = line.substring(index + 1).trim();

            data.put(key, value);
        }
    }

    // Fast toUpperCase for ASCII strings
    private static String asciiUpper(String text) {
        char [] chars = text.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            chars[i] = (c >= 97 && c <= 122) ? (char) (c - 32) : c;
        }

        return new String(chars);
    }

    public DatagramPacket getDatagramPacket() {
        return datagramPacket;
    }

    public Map<String, String> getData() {
        return data;
    }

    public String getType() {
        return type;
    }
}
