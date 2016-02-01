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
            String lt;
            String cond;
            String cond_img;
            String error;
            String error_s;

            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    Log.d("Start ", "Document Start");
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
                        Log.d("City Found ", c);
                        parser.nextTag();
                        s = parser.nextText();
                        Log.d("State Found ", s);
                        info.setCity(c);
                        info.setState(s);
                    }

                    if(name.equalsIgnoreCase("temperature_string") ){
                        w = parser.nextText();
                        Log.d("Temperature ", w);
                        info.setTemp(w);
                    }

                    if(name.equalsIgnoreCase("local_time_rfc822") ){
                        lt = parser.nextText();
                        Log.d("Local Time ", lt);
                        info.setLocalTime(lt);
                    }

                    if(name.equalsIgnoreCase("icon") ){
                        cond = parser.nextText();
                        Log.d("Condition ", cond);
                        info.setCondition(cond);
                    }

                    if(name.equalsIgnoreCase("icon_url") ){
                        cond_img = parser.nextText();
                        Log.d("Icon ", cond_img);
                        info.setImg_cond(cond_img);
                    }

                    if(name.equalsIgnoreCase("results")) {
                        //error result if query returns incorrect state but correct city
                        parser.nextTag();
                        parser.nextTag();
                        parser.nextText();
                        parser.nextTag();
                        c = parser.nextText();
                        Log.d("City found ", c);
                        parser.nextTag();
                        error_s = parser.nextText();
                        Log.d("Error in state ", error_s);
                        parser.nextTag();
                        info.setError_state(error_s);
                        info.setCity(c);
                    }

                    if(name.equalsIgnoreCase("description") ){
                        //error result if incorrect query is entered
                        error = parser.nextText();
                        Log.d("Error in query ", error);
                        info.setError(error);
                    }

                    break;

            }

            eventType = parser.next();
        }

    }

    public List<String> getInfo(){

        return info.dataToString();
    }

}

