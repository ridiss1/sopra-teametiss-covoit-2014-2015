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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ridiss
 */
public class FormServlet extends HttpServlet {

    private DB data = new DB();
    private boolean Conduc ;
    private boolean Notif;

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
            String Lieu = request.getParameter("Workplace");
            Time Morn = Time.valueOf(request.getParameter("HDMatin"));  //+":10"          
            Time Eve = Time.valueOf(request.getParameter("HDSoir"));
            String Dat = request.getParameter("JApplicables");
            boolean Conduc = Boolean.valueOf(request.getParameter("Conducteur"))!=null;
            boolean Notif = Boolean.valueOf(request.getParameter("Notif"))!=null;
            String pass=request.getParameter("Passe");

            /* TODO output your page here. You may use following sample code. */
            String result = data.AjoutDB(name, Prenom, Email, tel, Com, CodP, Lieu, Morn, Eve, Dat, Conduc, Notif,pass);
            
            if("Data".equals(result))
            {
                //String s=data.verifieLaDate(name,Prenom);
                // ICI je veux faire un test pour vérifier que le date rentrée est supérieure ou égale à la date courante
                String s="ok";
                if("ok".equals(s))
                {
                    RequestDispatcher rd = request.getRequestDispatcher("client.html");
                    rd.include(request, response);
                }
                else
                {
                    RequestDispatcher rd = request.getRequestDispatcher("errordate.html");
                    rd.include(request, response);
                }               
            }
            else
            {
                RequestDispatcher rd = request.getRequestDispatcher("error.html");
                rd.include(request, response);
            }
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
