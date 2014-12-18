/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormPage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ridiss
 */
public class FormServlet extends HttpServlet {
     
    private DB data= new DB();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String name = request.getParameter("Nom");
            String Prenom = request.getParameter("Prenom");
            String Email = request.getParameter("Email");
            int tel = Integer.parseInt(request.getParameter("Tel"));
            String Com = request.getParameter("Commune");
            int CodP = Integer.parseInt(request.getParameter("CodePostal"));
            String Lieu = request.getParameter("LieuDeTravail");
            Time Morn = Time.valueOf(request.getParameter("MorningTime"));
            Time Eve=Time.valueOf(request.getParameter("EveTime"));
            Date Dat = Date.valueOf(request.getParameter("DateAppli"));
            boolean Conduc = Boolean.valueOf(request.getParameter("Conducteur"));
            boolean Notif = Boolean.valueOf(request.getParameter("Notify"));
           
           
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FormServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            String result = data.AjoutDB(name, Prenom, Email,tel,Com,CodP,Lieu,Morn,Eve,Dat,Conduc,Notif);
            out.println("<h1>Vous avez saisi " +result+  "</h1>");
           // out.println("<h2>The current available data within the database is:</br>"
             //       + data.selectData() + "</h2>");

            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
