package etu2078.framework.servlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.*;

public class FrontServlet extends HttpServlet {

    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getPathInfo().substring(1);
        PrintWriter out = response.getWriter();

        String url = request.getRequestURL().toString();

        out.print("URL:"+url.split("/")[4]+"/"+url.split("/")[5]);

        if (action != null) {
            switch (action) {
                case "create":

                    break;
                case "update":

                    break;
                case "delete":

                    break;
                default:
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request,response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request,response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
