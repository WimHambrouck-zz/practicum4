package org.hambrouck.wim;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Scanner;

import org.json.JSONObject;
import sun.rmi.runtime.Log;

/**
 * @author Wim Hambrouck
 */
public class Logica {

    public static final String CLIENT_ID = "995245065637-3ou2j8prpc9j619hv0c55oamf9s72cc4.apps.googleusercontent.com";
    public static final String CLIENT_SECRET = "mm-NneMlHBmkxID4-0HcJ_C0";
    public static final String APPLICATION_NAME = "practicum4";
    public static final String REDIRECT_URI = "http://localhost:8080/practicum4-web-1.0-SNAPSHOT/response";
    public static final String PEOPLE_API_URL = "https://www.googleapis.com/plus/v1/people/me";

    /**
     * The OpenID Connect protocol requires the use of multiple endpoints for authenticating users,
     * and for requesting resources including tokens, user information, and public keys.
     * <p>
     * To simplify implementations and increase flexibility, OpenID Connect allows the use of a "Discovery document,"
     * a JSON document found at a well-known location containing key-value pairs which provide details about
     * the OpenID Connect provider's configuration, including the URIs of the authorization, token, userinfo,
     * and public-keys endpoints. The Discovery document for Google's OpenID Connect service may be retrieved from:
     */
    public static final String DISCOVERY_URL = "https://accounts.google.com/.well-known/openid-configuration";

    public static String getToken(HttpServletRequest request) throws FileNotFoundException {
        // Create a state token to prevent request forgery.
        // Store it in the session for later validation.
        String state = new BigInteger(130, new SecureRandom()).toString(32);

        request.getSession().setAttribute("state", state);
        //req.session().attribute("state", state);
        // Read index.html into memory, and set the client ID,
        // token state, and application name in the HTML before serving it.
        /*return new Scanner(new File("index.html"), "UTF-8")
                .useDelimiter("\\A").next()
                .replaceAll("[{]{2}\\s*CLIENT_ID\\s*[}]{2}", CLIENT_ID)
                .replaceAll("[{]{2}\\s*STATE\\s*[}]{2}", state)
                .replaceAll("[{]{2}\\s*APPLICATION_NAME\\s*[}]{2}", APPLICATION_NAME);*/

        return state;
    }

    //http://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java#4308662
    public static JSONObject getJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String getStringFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            return readAll(rd);
        } finally {
            is.close();
        }
    }

    public static JSONObject getJsonFromPost(String url, String urlParameters) throws IOException {
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //request header
        con.setRequestMethod("POST");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        if(responseCode != 200)
        {
            return new JSONObject("{Fout! Response: " + responseCode + "}");
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return new JSONObject(response.toString());
    }
}
