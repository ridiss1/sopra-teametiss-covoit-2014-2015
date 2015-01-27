/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mb Yann
 */
@WebServlet(name = "JourServlet", urlPatterns = {"/JourServlet"})
public class JourServlet extends HttpServlet {

    private DB database = new DB();
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
        HttpSession session = request.getSession();
            String name = (String) session.getAttribute("Name");
            ////////////////////////////////////////////////////////////////////////////
            boolean Lun = false,Mar = false,Mer = false,Jeu = false,Ven = false,Sam = false,Dim = false,Conduc = false,Notif =false;
            /////////////////////////////////////////////////////////////////////////////
            String L=request.getParameter("Lundi");
            String M=request.getParameter("Mardi");
            String Me=request.getParameter("Mercredi");
            String J=request.getParameter("Jeudi");
            String V=request.getParameter("Vendredi");
            String S=request.getParameter("Samedi");
            String D=request.getParameter("Dimanche");
            String Con=request.getParameter("Conducteur");
            String Not=request.getParameter("Notif");
            /////////////////////////////////////////////////////////////////////////////
            if("on".equals(L))
            {
                Lun =true;                
            }
            if("on".equals(M))
            {
                Mar =true;                
            }
            if("on".equals(Me))
            {
                Mer =true;                
            }
            if("on".equals(J))
            {
                Jeu =true;                
            }
            if("on".equals(V))
            {
                Ven =true;                
            }
             if("on".equals(S))
            {
                Sam =true;                
            }
             if("on".equals(D))
            {
                Dim =true;                
            }
            if("on".equals(Con))
            {
                Conduc =true;
            }
            if("on".equals(Not))
            {
                Notif =true;
            }
            
            
                int id=(int) session.getAttribute("ID");
                String ex=database.ModifJour(Lun, Mar, Mer, Jeu, Ven, Sam, Dim,Notif,Conduc,id);         
                //Si l'utilisateur entre un mot de passe deja utilisé
                if("Data".equals(ex))
                {
                    // on insere son nouveau mot de passe dans la base de donnée                    
                    RequestDispatcher rd = request.getRequestDispatcher("Clientconnecter.jsp");
                    rd.include(request, response);
                }
                else
                {
                    RequestDispatcher rd = request.getRequestDispatcher("Jourerror.html");
                    rd.include(request, response);
                }
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
