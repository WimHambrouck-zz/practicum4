package org.hambrouck.wim;

import org.json.JSONArray;
import org.json.JSONObject;
import sun.rmi.runtime.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wim Hambrouck on 05/01/2016.
 */
@WebServlet("/response")
public class ResponseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ensure that there is no request forgery going on, and that the user
        // sending us this connect request is the user that was supposed to.
        String test = request.getSession().getAttribute("state").toString();


        if(!request.getParameter("state").equals(request.getSession().getAttribute("state")))
        {
            response.setStatus(401);
        } else {
            response.setContentType("text/html");
            PrintWriter uit = response.getWriter();

            //post naar token_endpoint om token te krijgen voor Google+ API
            String urlParameters = "code=" + request.getParameter("code") + "&" +
                    "client_id=" + Logica.CLIENT_ID + "&" +
                    "client_secret=" + Logica.CLIENT_SECRET + "&" +
                    "redirect_uri=" + Logica.REDIRECT_URI + "&" +
                    "grant_type=authorization_code";
            JSONObject json = Logica.getJsonFromPost(request.getSession().getAttribute("token_endpoint").toString(), urlParameters);
            //access_token uit respons halen
            String access_token = json.get("access_token").toString();

            JSONObject eindelijk = Logica.getJsonFromUrl(Logica.PEOPLE_API_URL + "?access_token=" + access_token);



            uit.println(eindelijk.getString("displayName"));
        }

    }
}
