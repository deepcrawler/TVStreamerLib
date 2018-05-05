package com.TVCastLib;

import java.util.HashMap;

public class DefaultPlatform {



    public DefaultPlatform() {
    }

    public static HashMap<String, String> getDeviceServiceMap() { 
        HashMap<String, String> devicesList = new HashMap<String, String>();
        devicesList.put("com.TVCastLib.service.WebOSTVService", "com.TVCastLib.discovery.provider.SSDPDiscoveryProvider");
        devicesList.put("com.TVCastLib.service.NetcastTVService", "com.TVCastLib.discovery.provider.SSDPDiscoveryProvider");
        devicesList.put("com.TVCastLib.service.DLNAService", "com.TVCastLib.discovery.provider.SSDPDiscoveryProvider");
        devicesList.put("com.TVCastLib.service.DIALService", "com.TVCastLib.discovery.provider.SSDPDiscoveryProvider");
        devicesList.put("com.TVCastLib.service.RokuService", "com.TVCastLib.discovery.provider.SSDPDiscoveryProvider");
        devicesList.put("com.TVCastLib.service.CastService", "com.TVCastLib.discovery.provider.CastDiscoveryProvider");
        devicesList.put("com.TVCastLib.service.AirPlayService", "com.TVCastLib.discovery.provider.ZeroconfDiscoveryProvider");
        devicesList.put("com.TVCastLib.service.FireTVService", "com.TVCastLib.discovery.provider.FireTVDiscoveryProvider");
        return devicesList;
    }

}
