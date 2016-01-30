package com.srstudios.weather;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by chuck on 1/30/16.
 * Controller
 */
public class XMLProcess {


    private XMLData info = new XMLData();

    public void processing(XmlPullParser parser) throws XmlPullParserException, IOException {

        int eventType = parser.getEventType();
   while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;

            String c;
            String w;
            String s;

            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    Log.d("Parse", "Document Start");
                    break;
                case XmlPullParser.START_TAG:

                    //parses xml
                    name = parser.getName();
                    if(name.equalsIgnoreCase("display_location")) {

                        //to handle nested tags
                        parser.nextTag();
                        parser.nextText();
                        parser.nextTag();
                        c = parser.nextText();
                        Log.d("Start ", c);
                        parser.nextTag();
                        s = parser.nextText();
                        Log.d("Start ", s);
                        info.setCity(c);
                        info.setState(s);
                    }

                    if(name.equalsIgnoreCase("temperature_string") ){
                        w = parser.nextText();
                        Log.d("Start ", w);
                        info.setTemp(w);
                    }
                    break;

            }

            eventType = parser.next();
        }

    }

    public String getInfo(){

        return info.dataToString();
    }

}
