package br.com.silver.easycheck.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.NetworkOnMainThreadException;
import android.preference.PreferenceManager;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.com.silver.easycheck.R;

/**
 * Created by silver on 25/04/16.
 */
public class SilverHttp {

    public static final String BASE_URL = "http://ouirsvp.drivecomunicacao.com.br/scan/porta/recepcao";
    public static final String ARG_USERNAME = "username";
    public static final String ARG_PASSWORD = "password";
    public static final String PREF_LOGIN = "login" ;

    public static String authInvite(Activity act, String code){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(act);
        boolean otherServer = prefs.getBoolean(act.getString(R.string.pref_server), false);
        String serverAdress = prefs.getString(act.getString(R.string.pref_server_address), "");
        String url = otherServer ? serverAdress : BASE_URL;

        SharedPreferences prefsLogin = act.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
        String user = prefsLogin.getString("user", null);
        String pass = prefsLogin.getString("pass", null);

        try{
            OkHttpClient client = new OkHttpClient();
            client.setReadTimeout(15, TimeUnit.SECONDS);
            client.setConnectTimeout(15, TimeUnit.SECONDS);

            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            String jsonSend = "{\"inputLogin\":\""+user+"\",\"inputSenha\":\""+pass+"\",\"inputCodigo\":\""+code+"\"}";
            RequestBody body = RequestBody.create(mediaType, jsonSend);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            String jsonResp = response.body().string();
            return jsonResp;
        } catch (NetworkOnMainThreadException note){
            return note.getMessage();
        } catch (Exception e){
            return e.getMessage();
        }

    }

    public static boolean authLogin(Activity act, String username, String password){
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(5, TimeUnit.SECONDS);
        client.setConnectTimeout(10, TimeUnit.SECONDS);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(act);
        boolean otherServer = prefs.getBoolean(act.getString(R.string.pref_server), false);
        String serverAdress = prefs.getString(act.getString(R.string.pref_server_address),"");
        String url = otherServer ? serverAdress : BASE_URL;

        try{
            Request request = new Request.Builder()
                    .url(String.format("%s?%s=%s&%s=%s", url, ARG_USERNAME, username, ARG_PASSWORD, password))
                    .build();

            //Response response = client.newCall(request).execute();
            //String json = response.body().string();

            SimpleDateFormat sdf =  new  SimpleDateFormat ("yyyyMMdd");
            String currDate = sdf.format(new Date());

            SharedPreferences prefsLogin = act.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefsLogin.edit();
            editor.putString("user", username);
            editor.putString("pass", MD5(password+currDate));
            editor.commit();
            return true;

        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public static boolean hasConnection(Activity act){
        ConnectivityManager cm = (ConnectivityManager)
                act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static String MD5 ( String md5 )  {
        try  {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte [] array = md.digest(md5.getBytes());
            StringBuffer sb =  new  StringBuffer();
            for(int i=0;i<array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e )  {
        }
        return  null;
    }
}
