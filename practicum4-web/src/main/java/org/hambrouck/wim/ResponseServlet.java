package org.hambrouck.wim;

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
            Map<String, String[]> params = request.getParameterMap();

            for (Map.Entry<String, String[]> pair : params.entrySet()) {
                uit.println(pair.getKey() + " = " + pair.getValue()[0]);
            }
        }

    }
}
