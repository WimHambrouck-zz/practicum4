/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hambrouck.wim;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import sun.rmi.runtime.Log;

/**
 *
 * @author Wim Hambrouck
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class StartAuthServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter uit = response.getWriter();

        /**nodige endpoint url's uit discovery url JSON halen*/
        JSONObject json = Logica.getJsonFromUrl(Logica.DISCOVERY_URL);
        //authorizatie url, hebben we zo meteen nodig
        String authURL = json.get("authorization_endpoint").toString();
        //url waar we token naar moeten sturen, hebben we straks nodig, dus die mag even in de sessie
        request.getSession().setAttribute("token_endpoint", json.get("token_endpoint").toString());

        String temp = authURL + "?client_id=" + Logica.CLIENT_ID + "&" +
                "response_type=code&scope=openid%20email&" +
                "redirect_uri=" + Logica.REDIRECT_URI + "&" +
                "state=" + Logica.getToken(request) + "&";

        //Google geeft HTML pagina van login terug, dit laten zien
        uit.println(Logica.getStringFromUrl(temp));
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

}
