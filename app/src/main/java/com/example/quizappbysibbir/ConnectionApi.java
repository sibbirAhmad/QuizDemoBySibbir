package com.example.quizappbysibbir;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionApi {
    String TAG ="[[[[ConnectionApi]]]] - - - > ";

    String server = null;
    Context context;

    public ConnectionApi(Context ctx, String serverUrl, ConnectionListener listener){
        this.server = serverUrl;
        this.context = ctx;
        this.listener = listener;
        //------------ todo : Executing..
        Async async = new Async();
        async.execute();

    }

    class Async extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {

                URL url = new URL(server);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String json;
                while ((json = bufferedReader.readLine()) != null) {
                    sb.append(json + "\n");
                }
                return sb.toString().trim();
            } catch (Exception e) {
                Log.e(TAG, " Error on doInBackground: "+e);
                listener.onFailure(server,e.getMessage());
                return null;

            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

                if(s!=null) {
                    listener.onSuccess(server,s);
                }else {
                    Log.e(TAG, "onPostExecute: Server response null");
                }


        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.d(TAG, "onProgressUpdate: "+values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            listener.onFailure(server,"connectionProblem");
            Log.d(TAG, "onCancelled: "+s);
        }
    }

    private ConnectionListener listener;

    public void setConnectionListener(ConnectionListener listener)
    {
        this.listener=listener;
    }

    public interface ConnectionListener
    {
        public void onSuccess(String server,String resultText);

        public void onFailure(String server,String errorMessage);
    }

}
