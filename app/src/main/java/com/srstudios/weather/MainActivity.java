package com.srstudios.weather;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


//VIEW

public class MainActivity extends AppCompatActivity {
    TextView weather;
    EditText state, city;
    Button btn;

	//replace xxxapikeyxxx with your api key
    static final String baseURL = "http://api.wunderground.com/api/xxxapikeyxxx/conditions/q/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weather = (TextView) findViewById(R.id.txt_weather);
        state = (EditText) findViewById(R.id.txt_state);
        city = (EditText) findViewById(R.id.txt_city);
        btn = (Button) findViewById(R.id.wbtn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

		//validation to ensure data is entered for City and State
                boolean validationError = false;
                StringBuilder validationErrorMessage =
                        new StringBuilder(getResources().getString(R.string.error_intro));

                if (isEmpty(state) && isEmpty(city)) {
                    validationError = true;
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_blank_city));
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                        validationErrorMessage.append(getResources().getString(R.string.error_blank_state));
                    }
                } else if (isEmpty(city) && !isEmpty(state)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_city));
                } else if (isEmpty(state) && !isEmpty(city)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_state));
                }

                if (validationError) {
                    Snackbar.make(v, validationErrorMessage.toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    return;
                }

                String txt_city = city.getText().toString();
                String txt_state = state.getText().toString();


                if (weather.getText() != null ){
                    weather.setText("");
                }
                StringBuilder URL = new StringBuilder(baseURL);

                URL.append(Uri.encode(txt_state + "/" + txt_city + ".xml", "/"));
                String fullURL = URL.toString();

                new weatherTask().execute(fullURL);
            }
        });
    }
      

       /* weatherTask method redone to return an array list of strings instead of a single string
	public class weatherTask extends AsyncTask<String, Integer, String> {

	//doInBackground method 
        @Override
        protected String doInBackground(String... params) {
            //opens connection to document
            String downloadURL = params[0];
            try {
                URL url = new URL(downloadURL);
		
                XMLProcess processTheXML = new XMLProcess();


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream inputStream = conn.getInputStream();

		

                XmlPullParserFactory xmlFac = XmlPullParserFactory.newInstance();
                XmlPullParser parser = xmlFac.newPullParser();
                parser.setInput(inputStream, null);

		
                processTheXML.processing(parser);


                String info = processTheXML.getInfo();
                return info;


            } catch (Exception e) {

                e.printStackTrace();
            }

            return null;
        }


		AsyncTasks method onPostExecute() is run on the main thread, not the background thread
		the UI can be updated within it.
        @Override
        protected void onPostExecute(String result) {
              weather.setText(result);
            super.onPostExecute(result);
        }
    }*/




		/*AsyncTask to handle networking off of the main thread
		The first parameter specifies the type of your input parameters.
		The second parameter specifies the type for sending progress updates.
		The third parameter specifies the type of result produced by AsyncTask. 
		It sets the type of value returned by doInBackground(...)*/
	public class weatherTask extends AsyncTask<String, Integer, List<String>> {

        final ProgressDialog dlg = new ProgressDialog(MainActivity.this);
        XMLProcess processTheXML = new XMLProcess();

        @Override
        protected void onPreExecute(){
		//Progress dialog with custom-colored spinning circle
            dlg.setIndeterminate(true);
            dlg.setIndeterminateDrawable(getDrawable(R.drawable.progress_state));
            dlg.setMessage("Retrieving Data...");
            dlg.show();
            super.onPreExecute();

        }



        @Override
        protected List<String> doInBackground(String... params) {
            //opens connection to document
            String downloadURL = params[0];

            try {
                URL url = new URL(downloadURL);



                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream inputStream = conn.getInputStream();

		//XmlPullParser is an interface you used to pull parse events off of a stream of XML.
                XmlPullParserFactory xmlFac = XmlPullParserFactory.newInstance();
                XmlPullParser parser = xmlFac.newPullParser();
                parser.setInput(inputStream, null);
                processTheXML.processing(parser);


		//XmlPullParser is then sent to class XMLProcess to...process the xml
                List <String> info = processTheXML.getInfo();

                return info;


            } catch (Exception e) {

                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(List<String> result) {
            dlg.dismiss();
            String weatherpred = result.get(0);
            weather.setText(weatherpred);

	    //Picasso image loading utility added to ease the process if adding an icon to match the weather conditions
            Picasso.with(MainActivity.this).load(result.get(1)).resize(50, 50).into(pic_w);
            super.onPostExecute(result);
        }
    }

    private boolean isEmpty(EditText etxt) {
        if (etxt.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }


}
