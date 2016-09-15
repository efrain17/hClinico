package ec.com.altura.aclient.util;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by InvitadoX on 22/02/2016.
 */
public class Server {
    public org.apache.http.client.HttpClient httpClient;
    public Server(){
        httpClient = new org.apache.http.impl.client.DefaultHttpClient();
    }
    public JSONObject getJSON(String url,URLData urlData) throws IOException, NoSuchAlgorithmException, JSONException {
        //org.apache.http.client.methods.HttpGet get = new org.apache.http.client.methods.HttpGet(url+"?q="+urlData.encodeURI());
        //org.apache.http.client.methods.HttpGet get = new org.apache.http.client.methods.HttpGet(url+"?"+urlData.encodeURI());

        HttpPost get = new HttpPost (url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("VARIABLE","1314842673"));
        get.setEntity(new UrlEncodedFormEntity(params));


        HttpResponse response = httpClient.execute(get);
        String type = response.getEntity().getContentType().getValue();
        String charset = "utf8";
        StringBuilder sb = new StringBuilder();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            InputStream istream = (response.getEntity() != null) ? response.getEntity().getContent() : null;
            if (istream != null) {
                BufferedReader ireader = new BufferedReader(new InputStreamReader(istream, charset));
                String line;
                while ((line = ireader.readLine()) != null) {
                    sb.append(line);
                }
                ireader.close();
            }
        }
        return new JSONObject(sb.toString());
    }



    public String getJSON2(String url,List<NameValuePair> params) throws IOException, NoSuchAlgorithmException, JSONException {
        //org.apache.http.client.methods.HttpGet get = new org.apache.http.client.methods.HttpGet(url+"?q="+urlData.encodeURI());
        //org.apache.http.client.methods.HttpGet get = new org.apache.http.client.methods.HttpGet(url+"?"+urlData.encodeURI());

        HttpPost post = new HttpPost (url);

        post.setEntity(new UrlEncodedFormEntity(params));

        HttpResponse response = httpClient.execute(post);
        String type = response.getEntity().getContentType().getValue();
        String charset = "utf8";
        StringBuilder sb = new StringBuilder();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            InputStream istream = (response.getEntity() != null) ? response.getEntity().getContent() : null;
            if (istream != null) {
                BufferedReader ireader = new BufferedReader(new InputStreamReader(istream, charset));
                String line;
                while ((line = ireader.readLine()) != null) {
                    sb.append(line);
                }
                ireader.close();
            }
        }
        return sb.toString();
    }


}
