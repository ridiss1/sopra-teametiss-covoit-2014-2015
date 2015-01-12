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
 * @author kengne
 */
@WebServlet(name = "DelAcServlet", urlPatterns = {"/DelAcServlet"})
public class DelAcServlet extends HttpServlet {
    
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
            HttpSession session = request.getSession();
            String name = (String) session.getAttribute("Name");
            //String name = request.getParameter("Nom");
            System.out.println(name+"<<<<=====nom");
            String pass = request.getParameter("Passe");
            System.out.println(pass+"<<<<=====pass");            
            String vide="";
            String verif=database.verifData(name,pass);
            if(pass.equals(vide))
            {
                RequestDispatcher rd = request.getRequestDispatcher("errorSup1.html");
                rd.include(request, response);
            }
            else 
            {
               
                if("voila".equals(verif))
                {
                    String r=database.SuppDB(name,pass);
                    if("Data".equals(r))
                   {
                       RequestDispatcher rd = request.getRequestDispatcher("confirmSupp.html");
                       rd.include(request, response);   
                   }
                   else
                   {
                       RequestDispatcher rd = request.getRequestDispatcher("errorSup.html");
                       rd.include(request, response);  
                   }   
                }
                else
                {
                    RequestDispatcher rd = request.getRequestDispatcher("errorSup2.html");
                    rd.include(request, response);               
                }               
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
