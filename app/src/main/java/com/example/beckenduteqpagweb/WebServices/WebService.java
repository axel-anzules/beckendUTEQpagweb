package com.example.beckenduteqpagweb.WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.beckenduteqpagweb.WebServices.Asynchtask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class WebService extends AsyncTask<String, Long, String> {

    private Map<String, String> datos;
    private String url;
    private Context actividad;
    private String xml = null;
    private Asynchtask callback = null;

    ProgressDialog progDailog;

    public WebService(String urlWebService, Map<String, String> data, Context activity, Asynchtask callback) {
        this.url = urlWebService;
        this.datos = data;
        this.actividad = activity;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = new ProgressDialog(actividad);
        progDailog.setMessage("Cargando Web Service...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try {
            // Trust all certificates
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

            URL url = new URL(this.url);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(params[0]); // "GET", "POST", etc.
            conn.setDoInput(true);

            // Si viene el parámetro "Bearer" y el token, construye el header correctamente
            if (params.length >= 3 && params[1].equalsIgnoreCase("Bearer")) {
                String authHeader = "Bearer " + params[2]; // ESPACIO CORRECTO
                conn.setRequestProperty("Authorization", authHeader);
            }

            int responseCode = conn.getResponseCode();
            InputStream inputStream = (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            result = sb.toString();
            reader.close();
            inputStream.close();
            conn.disconnect();

        } catch (Exception e) {
            Log.e("WebServiceSecure", "Error: " + e.getMessage(), e);
            result = "{\"error\": true, \"message\": \"" + e.getMessage() + "\"}";
        }

        return result;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        this.xml = response;
        progDailog.dismiss();
        try {
            callback.processFinish(this.xml);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}