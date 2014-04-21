package com.wselwood.mpcreader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wselwood on 18/04/14.
 */
public enum OrbitType {
    ATEN,
    APOLLO,
    AMOR,
    OBJECTWITHQLESSTHAN1POINT665AU,
    HUNGARIA,
    PHOCAEA,
    HILDA,
    JUPITERTROJAN,
    CENTAUR,
    PLUTINO,
    OTHERRESONANTTNO,
    CUBEWANO,
    SCATTEREDDISK;

    public static Map<Integer, OrbitType> lookUp = new HashMap<>();

    static {
        lookUp.put(2, OrbitType.ATEN);
        lookUp.put(3, OrbitType.APOLLO);
        lookUp.put(4, OrbitType.AMOR);
        lookUp.put(5, OrbitType.OBJECTWITHQLESSTHAN1POINT665AU);
        lookUp.put(6, OrbitType.HUNGARIA);
        lookUp.put(7, OrbitType.PHOCAEA);
        lookUp.put(8, OrbitType.HILDA);
        lookUp.put(9, OrbitType.JUPITERTROJAN);
        lookUp.put(10, OrbitType.CENTAUR);
        lookUp.put(14, OrbitType.PLUTINO);
        lookUp.put(15, OrbitType.OTHERRESONANTTNO);
        lookUp.put(16, OrbitType.CUBEWANO);
        lookUp.put(17, OrbitType.SCATTEREDDISK);
    }
}
