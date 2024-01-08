package com.example.weckerapp;

import android.content.res.AssetManager;
import android.util.ArrayMap;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class XML {
    public static ArrayMap<String, String> readXML(String filename, String sprache1, String sprache2) throws IOException, XmlPullParserException {
        AssetManager assetManager = MainActivity.ctx.getAssets();
        InputStream stream = assetManager.open(filename);
        ArrayMap<String, String> dic = new ArrayMap<>();



        XmlPullParser xmlParser = Xml.newPullParser();
        xmlParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xmlParser.setInput(stream, null);
        xmlParser.nextTag();

        String temp = "";
        xmlParser.require(XmlPullParser.START_TAG, null, "Liste");
        xmlParser.getAttributeValue(null, "Liste");
        while(xmlParser.next() != XmlPullParser.END_TAG){
            if(xmlParser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            xmlParser.require(XmlPullParser.START_TAG, null, "Begriff");
            dic.put(xmlParser.getAttributeValue(null, sprache1),xmlParser.getAttributeValue(null, sprache2));
            xmlParser.nextTag();
            xmlParser.require(XmlPullParser.END_TAG, null, "Begriff");

        }
        stream.close();
        assetManager.close();
        return dic;
    }
}
