/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Controller;

import Model.DB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author UT
 */
@WebServlet(name = "LogServlet", urlPatterns = {"/LogServlet"})
public class LogServlet extends HttpServlet {
    
    private DB data = new DB();
    public static final String  COOKIE_DERNIERE_CONNEXION = "derniereConnexion";
    public static final String  FORMAT_DATE               = "dd/MM/yyyy HH:mm:ss";
    public static final String  ATT_INTERVALLE_CONNEXIONS = "intervalleConnexions";
    public static final int     COOKIE_MAX_AGE            = 60 * 60 * 24 * 365;  // 1 an
    
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
            /* TODO output your page here. You may use following sample code. */
            
            
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
        
        /* Tentative de récupération du cookie depuis la requête */
        String derniereConnexion = getCookieValue( request, COOKIE_DERNIERE_CONNEXION );
        if(derniereConnexion!=null){
            this.getServletContext().getRequestDispatcher("/Clientconnecter.jsp").forward(request, response);
            
        }else{
            /* Affichage de la page de connexion */
            this.getServletContext().getRequestDispatcher("/connexion.jsp").forward(request, response);
        }
        
        
        
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
        
        
        /* Récupération de la session depuis la requête */
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        session = request.getSession();
        
        
        String Email = request.getParameter("Mail");
        String pass=request.getParameter("Passe");
        if("admin".equals(Email) & "admin".equals(pass))
        {
            RequestDispatcher rd = request.getRequestDispatcher("admin.html");
            rd.include(request, response);
        }
        else
        {
            String S=data.verifID(Email, pass);
            if("voila".equals(S))
                
            {   //initialisation de la variable de session
                
                String name=data.recupNom(Email, pass);
                session.setAttribute( "Name", name );
                this.getServletContext().getRequestDispatcher("/Clientconnecter.jsp").forward(request, response);
                /* Récupération de la date courante */
                Date dt = new Date();
                /* Formatage de la date et conversion en texte */
                java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat( FORMAT_DATE  );
                String dateDerniereConnexion = formatter.format( dt );
                /* Création du cookie, et ajout à la réponse HTTP */
                setCookie( response, COOKIE_DERNIERE_CONNEXION, dateDerniereConnexion, COOKIE_MAX_AGE );
                
            }
            else
            {   session.setAttribute( "ID", null );
            RequestDispatcher rd = request.getRequestDispatcher("nologin.html");
            rd.include(request, response);
            
            }
            
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
    
    
    /**
     * Méthode utilitaire gérant la récupération de la valeur d'un cookie donné
     * depuis la requête HTTP.
     */
    private static String getCookieValue( HttpServletRequest request, String nom ) {
        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( Cookie cookie : cookies ) {
                if ( cookie != null && nom.equals( cookie.getName() ) ) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    /*
    * Méthode utilitaire gérant la création d'un cookie et son ajout à la
    * réponse HTTP.
    */
    private static void setCookie( HttpServletResponse response, String nom, String valeur, int maxAge ) {
        Cookie cookie = new Cookie( nom, valeur );
        cookie.setMaxAge( maxAge );
        response.addCookie( cookie );
    }
    
}
