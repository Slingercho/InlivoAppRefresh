package com.ss.inlivo.network;

import android.os.AsyncTask;
import com.ss.inlivo.activity.MainActivity;
import com.ss.inlivo.listener.OnTaskCompletedListener;
import com.ss.inlivo.model.HealthIndex;
import com.ss.inlivo.model.Vitamins;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by NAME on 31.5.2016 г..
 */
public class HttpGetUserVitaminsTask extends AsyncTask<String, Void, Void> {

    private static final String URI = "http://inlivo.com/pressys_ftp/TEST/get.php";

    private OnTaskCompletedListener onTaskCompletedListener;
    private MainActivity mMainActivity;
    private HealthIndex mHealthIndex;
    private Vitamins mVitamins;

    public HttpGetUserVitaminsTask(MainActivity activity, OnTaskCompletedListener onTaskCompletedListener){

        this.mMainActivity = activity;
        this.onTaskCompletedListener = onTaskCompletedListener;
    }

    @Override
    protected Void doInBackground(String... params) {

        mMainActivity.showLoadingDialog();
        String module = params[0];
        String userID = params[1];

        String urlParameters = "m=" + module + "&id=" + userID;

        String result = getJsonResponse(URI, urlParameters);

        handleJsonResponse(result, module);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        mMainActivity.hideLoadingDialog();
        onTaskCompletedListener.onTaskCompleted();
    }

    private String getJsonResponse(String uri, String urlParameters) {

        HttpURLConnection con = null;
        StringBuffer response = null;
        BufferedReader reader = null;
        DataOutputStream dos = null;
        try {
            URL url = new URL(uri);

            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setDoOutput(true);

            dos = new DataOutputStream(con.getOutputStream());
            dos.writeBytes(urlParameters);

            reader = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;

            response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }catch (UnknownHostException ex){

            //Add dialog here for no internet connection

            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {

            if (con != null) {
                con.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(dos != null){
                try {
                    dos.flush();
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return response.toString();
    }

    private void handleJsonResponse(String result, String module){

        try {
            // Get top-level JSON Object
            JSONObject jsonParent = (JSONObject)new JSONTokener(result).nextValue();

            //get vitamins array
            JSONArray allVitamins = jsonParent.getJSONArray("Vitamins");

            if(MainActivity.HEALTH_MODE.equals(module)){
                mHealthIndex = new HealthIndex();
                mHealthIndex.setIndex(jsonParent.getString("HealthIndex"));
                mMainActivity.setHealthIndex(mHealthIndex);
            }

            if(allVitamins != null){

                mVitamins = new Vitamins();

                populateVitamins(allVitamins);
                mMainActivity.setVitamins(mVitamins);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void populateVitamins(JSONArray allVitamins) throws JSONException {

        mVitamins.setVitaminA(getVitaminValue(allVitamins.getString(0)));
        mVitamins.setVitaminB6(getVitaminValue(allVitamins.getString(1)));
        mVitamins.setVitaminB12(getVitaminValue(allVitamins.getString(2)));
        mVitamins.setVitaminC(getVitaminValue(allVitamins.getString(3)));
        mVitamins.setVitaminD(getVitaminValue(allVitamins.getString(4)));
        mVitamins.setVitaminE(getVitaminValue(allVitamins.getString(5)));
        mVitamins.setVitaminK(getVitaminValue(allVitamins.getString(6)));
    }

    private String getVitaminValue(String input){

        return input.substring(input.lastIndexOf(":") + 1);
    }
}
