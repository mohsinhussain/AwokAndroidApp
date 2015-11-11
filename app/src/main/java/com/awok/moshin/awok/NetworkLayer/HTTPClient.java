package com.awok.moshin.awok.NetworkLayer;

import android.content.SharedPreferences;
import android.util.Log;

import com.awok.moshin.awok.Util.Utilities;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by mohsin on 9/9/2015.
 */
public class HTTPClient{
    //	public String oauth_consumer_key;
//	public String oauth_signature;
    public static String TAG = "HTTPClient";
    //	public String REQUEST_TOKEN_URL ="/oauth/request_token/?";
//	public String ACCESS_TOKEN_URL = "/oauth/access_token/?";
//	public String AUTHORIZATION_URL = "/oauth/authorize/?";
//	public String oauth_token;
//	public String oauth_token_secret;
//	public String oauth_verifier;
    public String oauth_cookies;
    //	public Boolean oauth_client = true;
    SharedPreferences mSharedPrefs;
    static int TIMEOUT_VALUE = 10000;



    public HTTPClient(SharedPreferences preferences)
    {


        mSharedPrefs = preferences;
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        if (mSharedPrefs.getString("cookie", null) != null){
//			oauth_token = mSharedPrefs.getString("oauth_token", null);
//			oauth_token_secret = mSharedPrefs.getString("oauth_token_secret", null);
//			oauth_verifier = mSharedPrefs.getString("oauth_verifier", null);
            oauth_cookies = mSharedPrefs.getString("cookie", null);
//			Log.v(TAG, "OAuth Token from Shared Preferences: " + oauth_token);
//			Log.d(TAG, "Shared Preferences loaded.");
        }
    }

//	public void fetchRequestToken() throws IOException
//	{
//		String authString = "OAuth realm=\"\"," +
//				" oauth_nonce=\"" + getNonce() + "\"," +
//				" oauth_timestamp=\"" + getTimestamp() + "\"," +
//				" oauth_consumer_key=\"website\", oauth_signature_method=\"PLAINTEXT\", oauth_version=\"1.0\"," + "" +
//				" oauth_signature=\"websitesecret_%2524M%2523%2540LL-178%26\", oauth_callback=\"http%3A%2F%2Flocalhost%3A8000%2Fready\"";
//
//		URL url = new URL(Constants.API_SERVER_URL + REQUEST_TOKEN_URL);
//
//		URLConnection con = url.openConnection();
//		con.setRequestProperty("Cookie", oauth_cookies);
//		con.setRequestProperty("Authorization", authString);
//		String resp = readURL(con);
//		Log.d(TAG, resp);
//		String[] parameter = resp.split("&");
//        String[] tokensecret = parameter[0].split("=");
//
//        if (tokensecret[0].equals("oauth_token_secret")) {
//            if (tokensecret.length == 1) {
//                throw new RuntimeException("Missing parameters!");
//            }
//            Log.e(TAG, tokensecret[1]);
//        }
//        String[] oauthtoken=parameter[1].split("=");
//        if (oauthtoken[0].equals("oauth_token")) {
//            if (oauthtoken.length == 1) {
//                throw new RuntimeException("Missing parameters!");
//            }
//            System.out.println(oauthtoken[1]);
//        }
//        oauth_token_secret = tokensecret[1];
//        oauth_token = oauthtoken[1];
//	}

//	public void authorizeToken( ) throws IOException
//	{
//		Log.d(TAG, "In authorizeToken()");
//		URL url = new URL(Constants.API_SERVER_URL + AUTHORIZATION_URL + "oauth_token=" + oauth_token);
//		URLConnection con = url.openConnection();
//		con.setRequestProperty("Cookie", oauth_cookies);
//		String resp = readURL(con);
//		Log.v(TAG, "Verifier: " + resp);
//		oauth_verifier=resp;
//	}

//	public void fetchAccessToken() throws IOException
//	{
//		String websecret = "websitesecret_%2524M%2523%2540LL-178%26" + oauth_token_secret;
//		String authString2 = "OAuth realm=\"\"," +
//				" oauth_nonce=\"" + getNonce() + "\"," +
//				" oauth_timestamp=\"" + getTimestamp() + "\"," +
//				" oauth_signature_method=\"PLAINTEXT\", oauth_version=\"1.0\"," +
//				" oauth_token=\""+ oauth_token + "\"," + "oauth_verifier=" + oauth_verifier + ", oauth_consumer_key=\"website\"," +
//				" oauth_signature=" + websecret ;
//
//        Log.v(null, authString2);
//		URL url = new URL("https://api.smsall.pk/oauth/access_token/?");
//		URLConnection con = url.openConnection();
//		con.setRequestProperty("Cookie", oauth_cookies);
//		con.setRequestProperty("Authorization", authString2);
//
//		String resp = readURL(con);
//		Log.v(null, resp);
//
//        String[] parameter = resp.split("&");
//		String [] tokensecret = parameter[0].split("=");
//
//        if (tokensecret[0].equals("oauth_token_secret")) {
//            if (tokensecret.length == 1) {
//                throw new RuntimeException("missing access token");
//            }
//            Log.v(TAG, tokensecret[1]);
//        }
//        String [] ouathtoken=parameter[1].split("=");
//
//        if (ouathtoken[0].equals("oauth_token")) {
//            if (ouathtoken.length == 1) {
//                throw new RuntimeException("missing access token");
//            }
//            Log.v(TAG, ouathtoken[1]);
//        }
//        oauth_token_secret = tokensecret[1];
//        oauth_token = ouathtoken[1];
//        return;
//	}

    public String get(String reqapi, String params) throws IOException
    {
        URL url = new URL(reqapi);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(2000);
        con.setDoInput(true);

        con.setRequestMethod("GET");
//        con.setRequestProperty("Cookie", oauth_cookies);
        con.setRequestProperty("Parameters", params);
//		if (oauth_client)
//			con.setRequestProperty("Authorization", getAuthString());
//        con.setRequestProperty("Content-Length", "0");

        String resp = readURL(con);

        Log.v(TAG, "GET Response: " + resp);
        return resp;
    }

    public String post(String reqapi,String params) throws IOException
    {
//        URL url = new URL(reqapi);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setConnectTimeout(2000);
//
//
//        con.setDoOutput(true);
//        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
////        con.setRequestProperty("Cookie", oauth_cookies);
//        //con.setRequestProperty("Parameters", params);
//
//        con.setRequestMethod("POST");
//        OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
//        wr.write(params.toString());

        URL url;
        URLConnection urlConn;
        DataOutputStream printout;
        DataInputStream input;
        url = new URL (reqapi);
        urlConn = url.openConnection();
        urlConn.setDoInput(true);
        urlConn.setDoOutput(true);
        urlConn.setUseCaches(false);
        urlConn.setRequestProperty("Content-Type","application/json");
//        urlConn.setRequestProperty("Host", "android.schoolportal.gr");
        urlConn.connect();
//Create JSONObject here
//        JSONObject jsonParam = new JSONObject();
//        jsonParam.put("ID", "25");
//        jsonParam.put("description", "Real");
//        jsonParam.put("enable", "true");
//        The part which you missed is in the the following... i.e., as follows..
//
//// Send POST output.
//        printout = new DataOutputStream(urlConn.getOutputStream ());
//        printout.write(Integer.parseInt(params.toString()));
//        printout.flush();
//        printout.close ();

        DataOutputStream wr = new DataOutputStream(urlConn.getOutputStream ());
        wr.writeBytes(params.toString());

        wr.flush();
        wr.close();

        System.out.println("VHFGHDFHFhf" + urlConn.getURL().toString());
        System.out.println("VHFGHDFHFhf"+ params.toString());
        System.out.println("GO"+urlConn.getContentType());
        String resp = readURL(urlConn);
        Log.v(TAG, "POST Response: " + resp);
        return resp;
    }

    public String put(String reqapi,String params) throws IOException
    {
       /* URL url = new URL(reqapi);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(2000);
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestMethod("PUT");

        con.setRequestProperty("Parameters", params);
        con.setRequestProperty("Content-Length", "0");

        String resp = readURL(con);

        Log.v(TAG, "PUT Response: " + resp);
        return resp;*/
        URL url = new URL(reqapi);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(2000);
        con.setDoInput(true);

        con.setRequestMethod("PUT");
//        con.setRequestProperty("Cookie", oauth_cookies);
        if(params!=null){
            con.setRequestProperty("Parameters", params);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream ());
            wr.writeBytes(params.toString());

            wr.flush();
            wr.close();
        }

//		if (oauth_client)
//			con.setRequestProperty("Authorization", getAuthString());
//        con.setRequestProperty("Content-Length", "0");

        //String resp = readURL(con);


        System.out.println("VHFGHDFHFhf" + con.getURL().toString());
//        System.out.println("VHFGHDFHFhf"+ params.toString());
        System.out.println("GO"+con.getContentType());
        String resp = readURL(con);
        Log.v(TAG, "POST Response: " + resp);
        return resp;
    }

    public String delete(String reqapi, String params) throws IOException
    {
        URL url = new URL(reqapi);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        //con.setDoOutput(true);
        con.setRequestMethod("DELETE");
//        con.setRequestProperty("Cookie", oauth_cookies);
        con.setRequestProperty("Content-Length", "0");
        con.setRequestProperty("Parameters", params);
//		if (oauth_client)
//			con.setRequestProperty("Authorization", getAuthString());

        String resp = readURL(con);

        Log.v(TAG, "DELETE Response: " + resp);
       /* System.out.println("VHFGHDFHFhf" + con.getURL().toString());
        System.out.println("VHFGHDFHFhf"+ params.toString());
        System.out.println("GO" + con.getContentType());*/
        return resp;
    }

//    public Boolean login(String num , String pswd) throws IOException
//    {
//
//        String userName = "";
//        String fullName = "";
//        if (mSharedPrefs.getString("oauth_token", null) != null) {
////			oauth_token = mSharedPrefs.getString("oauth_token", null);
////			oauth_token_secret = mSharedPrefs.getString("oauth_token_secret", null);
////			oauth_verifier = mSharedPrefs.getString("oauth_verifier", null);
//            oauth_cookies = mSharedPrefs.getString("cookie", null);
//            Constants.USER_NAME = mSharedPrefs.getString("chatname", null);
//            Constants.FULL_NAME = mSharedPrefs.getString("fullname", null);
//            Log.v(TAG, "Shared prederences found");
//
//            return true;//mSharedPrefs.getString("profile", null);
//        }
//
//
//        Log.v(TAG, "I am in Login Function");
////		SharedPreferences.Editor editor1 = mSharedPrefs.edit();
////		editor1.clear();
//
//        URL url = new URL(Constants.API_SERVER_URL + "/accounts/login?username=%2B" + num + "&password=" + pswd);
//        URLConnection con = url.openConnection();
//        con.setConnectTimeout(2000);
//        String resp = readURL(con);
//        HttpsURLConnection c = (HttpsURLConnection) con;
//        if (c.getResponseCode() != 200)
//            return false;
//
//        Log.v(TAG, "Sign In Response: "+resp);
//
//        try {
//            JSONObject respJsonObject = new JSONObject(resp);
//
//            userName = respJsonObject.getString("username");
//            Log.v(TAG, "UserName: "+ userName);
//            fullName = respJsonObject.getString("fullname");
//            Log.v(TAG, "FullName: "+ fullName);
//            Constants.USER_NAME = userName;
//            Constants.FULL_NAME = fullName;
//
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        oauth_cookies = con.getHeaderField("set-cookie");
////		fetchRequestToken();
////		Log.v(TAG, "Request token");
////    	authorizeToken();
////    	Log.v(TAG, "Authorize");
////    	fetchAccessToken();
////    	Log.v(TAG, "Access Token");
//
//        Log.v(TAG, "Saving shared preferences...");
//
//        SharedPreferences.Editor editor = mSharedPrefs.edit();
//        editor.putString("username", num);
//        editor.putString("password", pswd);
////    	editor.putString("verifier", oauth_verifier);
////    	editor.putString("oauth_token_secret", oauth_token_secret);
////    	editor.putString("oauth_token", oauth_token);
//        editor.putString("profile", resp);
//        editor.putString("cookie", oauth_cookies);
//        editor.putString("chatname", userName);
//        editor.putString("fullname", fullName);
//        editor.commit();
//        return true;
//    }

//    public void logout() throws IOException
//    {
//        SharedPreferences.Editor editor = mSharedPrefs.edit();
//        editor.clear();
//        editor.commit();
//        URL url = new URL(Constants.API_SERVER_URL + "/accounts/logout");
//        URLConnection con = url.openConnection();
//        readURL(con);
//    }
//
//	public String getNonce()
//	{
//		Random r = new Random();
//		int nonce = r.nextInt(10000000);
//		return Integer.toString(nonce);
//	}

//	public String getTimestamp()
//	{
//		return Integer.toString((int)(System.currentTimeMillis() / 1000));
//	}

//	public String getAuthString()
//	{
//		return "OAuth realm=\"\"," +
//				" oauth_nonce=\"" + getNonce() + "\"," +
//				" oauth_timestamp=\"" + getTimestamp() + "\"," +
//				" oauth_signature_method=\"PLAINTEXT\", oauth_version=\"1.0\"," +
//				" oauth_token=\""+ oauth_token + "\"," + "oauth_verifier=" + oauth_verifier +
//				", oauth_consumer_key=\"website\"," +
//				" oauth_signature=" + "websitesecret_%2524M%2523%2540LL-178%26" + oauth_token_secret;
//	}

    private static String readURL(URLConnection connection){

        String str = new String();
        try
        {

            HttpURLConnection urlcon = (HttpURLConnection)connection;

            urlcon.setConnectTimeout(TIMEOUT_VALUE);
            urlcon.setReadTimeout(TIMEOUT_VALUE);
//            Log.v(TAG, "urlcon Response URL: "+urlcon.getURL());
//            Log.v(TAG, "urlcon Response Code: "+urlcon.getResponseCode());
//            Log.v(TAG, "urlcon Response Message: "+urlcon.getResponseMessage());
//            Log.v(TAG, "urlcon Response Message: "+urlcon.getResponseMessage());
            InputStream is = urlcon.getInputStream();
            str = streamToString(is);
            return str;
        }
        catch (SocketTimeoutException e) {
            Log.i(TAG, "Socket More than " + TIMEOUT_VALUE + " elapsed.");
            return "";
        }
        catch (ConnectTimeoutException e){
            Log.i(TAG, "Connect More than " + TIMEOUT_VALUE + " elapsed.");
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "Error: "+e.toString());
            Log.e(TAG, "Message: "+e.getMessage().toString());
        }
        return str;
    }

    // Reads an InputStream and converts it to a String.
    private static String streamToString(InputStream stream) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(stream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
