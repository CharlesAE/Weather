package com.srstudios.weather;

/**
 * Created by chuck on 1/29/16.
 * Model
 */
public class XMLData {

    String temp;
    String city;
    String state;

    public void setCity(String c){
        city = c;

    }

    public void setState(String s){
        state = s;
    }

    public void setTemp(String t){
        temp = t;
    }

    public String dataToString(){
        return "In " + city + ", " + state + " the current temperature is " + temp;
    }

}
