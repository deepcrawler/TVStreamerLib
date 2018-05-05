/*
 * SSDPDevice
 * TVCastLib
 * 
 * Copyright (c) 2014 Hamed Ghaderipour.
 * Created by Hamed Ghaderipour on 6 Jan 2015
 * 

 */

package com.TVCastLib.discovery.provider.ssdp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class SSDPDevice {
    /* Required. UPnP device type. */
    public String deviceType;
    /* Required. Short description for end user. */
    public String friendlyName;
    /* Required. Manufacturer's name. */
    public String manufacturer;
//    /* Optional. Web site for manufacturer. */
//    public String manufacturerURL;
    /* Recommended. Long description for end user. */
    public String modelDescription;
    /* Required. Model name. */
    public String modelName;
    /* Recommended. Model number. */
    public String modelNumber;
//    /* Optional. Web site for model. */
//    public String modelURL;
//    /* Recommended. Serial number. */
//    public String serialNumber;
    /* Required. Unique Device Name. */
    public String UDN;
//    /* Optional. Universal Product Code. */
//    public String UPC;
    /* Required. */
//    List<Icon> iconList = new ArrayList<Icon>();
    public String locationXML;
    /* Optional. */
    public List<Service> serviceList = new ArrayList<Service>();

    public String ST;
    public String applicationURL;

    public String serviceURI;

    public String baseURL;
    public String ipAddress;
    public int port;
    public String UUID;

    public Map<String, List<String>> headers;

    public SSDPDevice(String url, String ST) throws IOException, ParserConfigurationException, SAXException {
        this(new URL(url), ST);
    }

    public SSDPDevice(URL urlObject, String ST) throws IOException, ParserConfigurationException, SAXException {
        if (urlObject.getPort() == -1) {
            baseURL = String.format("%s://%s", urlObject.getProtocol(), urlObject.getHost());
        } else {
            baseURL = String.format("%s://%s:%d", urlObject.getProtocol(), urlObject.getHost(), urlObject.getPort());
        }
        ipAddress = urlObject.getHost();
        port = urlObject.getPort();
        UUID = null;

        serviceURI = String.format("%s://%s",  urlObject.getProtocol(), urlObject.getHost());

        parse(urlObject);
    }

    public void parse(URL url) throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;

        SSDPDeviceDescriptionParser parser = new SSDPDeviceDescriptionParser(this);

        URLConnection urlConnection = url.openConnection();

        applicationURL = urlConnection.getHeaderField("Application-URL");
        if (applicationURL != null && !applicationURL.substring(applicationURL.length() - 1).equals("/")) {
            applicationURL = applicationURL.concat("/");
        }

        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        Scanner s = null;
        try {
            s = new Scanner(in).useDelimiter("\\A");
            locationXML = s.hasNext() ? s.next() : "";

            saxParser = factory.newSAXParser();
            saxParser.parse(new ByteArrayInputStream(locationXML.getBytes()), parser);
        } finally {
            in.close();
            if (s != null)
                s.close();
        }

        headers = urlConnection.getHeaderFields();
    }

    @Override
    public String toString() {
        return friendlyName;
    }
}
