package com.srstudios.weather;

/**
 * Created by chuck on 1/29/16.
 * Model
 */
public class XMLData {

    String temp;
    String city;
    String state;
    String localTime;
    String img_cond;
    String condition;
    String error;
    String error_state;


    public void setCity(String c){
        city = c;
    }

    public void setCondition(String cond) {
        condition = cond;
    }

    public void setLocalTime(String lt) {
        localTime = lt;
    }

    public void setState(String s){
        state = s;
    }

    public void setTemp(String t){
        temp = t;
    }

    public void setImg_cond(String con){
        img_cond = con;
    }

    public void setError_state(String es) {
        error_state = es;
    }

    public void setError(String ec) {
        error = ec;
    }

    public List<String> dataToString(){


        List list = new ArrayList<String>();

        if(error_city != null || (city == null && state == null))
        {
            list.add(0,error_city);
		//Upload your custom error icon
            list.add(1,"http://UPLOAD/YOUR/CUSTOM/ERROR/ICON/AND/PLACE/HERE.png");

        }

        if( error_state != null)
        {
            list.add(0,"Error getting data, did you mean "+ city + ", " +error_state +"?");
            list.add(1,"http://cdn2.iconfinder.com/data/icons/windows-8-metro-style/512/error.png");

        }
        else
        if(error_city == null)
        {
            list.add(0,"It is " + localTime +". " + city + ", " + state + " is currently "+ condition +  " the temperature is " + temp );
            list.add(1, img_cond);
        }


        return list;

    }



}

