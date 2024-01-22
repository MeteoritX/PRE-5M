package com.example.weckerapp;

import android.util.ArrayMap;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class XMLMedLingu {
    public static ArrayMap<String, String> XMLMed() throws XmlPullParserException, IOException {
        ArrayMap<String, String> dic = null;
        if(dic == null){
            dic = XML.readXML("anatomie.xml", "Latein", "Deutsch");
        }
        return dic;
    }

    public static ArrayMap<String, String> XMLLingu() throws XmlPullParserException, IOException {
        ArrayMap<String, String> dic = null;
        if(dic == null){
            dic = XML.readXML("englisch.xml", "Englisch", "Deutsch");
        }
        return dic;
    }
}
