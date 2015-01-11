package Model;

import java.io.IOException;
import java.sql.Time;
import javax.servlet.RequestDispatcher;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ridiss
 */
public class Utilisateur {
           private DB data = new DB();
    
    
    public void creationUSER(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
            int count = 0;
            String name = request.getParameter("Nom");
            String Prenom = request.getParameter("Prenom");
            String Email = request.getParameter("Email");
            int tel = Integer.parseInt(request.getParameter("Tel"));
            String Com = request.getParameter("Commune");
            int CodP = Integer.parseInt(request.getParameter("CodePostal"));
            String Lieu = request.getParameter("Workplace");
            String Morn = ""+Time.valueOf(request.getParameter("HDMatin")+":00");            
            String Eve = ""+Time.valueOf(request.getParameter("HDSoir")+":00");
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
                count++;
            }
            if("on".equals(M))
            {
                Mar =true;
                count++;
            }
            if("on".equals(Me))
            {
                Mer =true;
                count++;
            }
            if("on".equals(J))
            {
                Jeu =true;
                count++;
            }
            if("on".equals(V))
            {
                Ven =true;
                count++;
            }
             if("on".equals(S))
            {
                Sam =true;
                count++;
            }
             if("on".equals(D))
            {
                Dim =true;
                count++;
            }
            if("on".equals(Con))
            {
                Conduc =true;
            }
            if("on".equals(Not))
            {
                Notif =true;
            }
            String pass=request.getParameter("Passe");
            /* TODO output your page here. You may use following sample code. */
            String result = data.AjoutDB(name, Prenom, Email, tel, Com, CodP, Lieu, Morn, Eve,Lun,Mar,Mer,Jeu,Ven,Sam,Dim,Conduc, Notif,pass,count);
            
            if("Data".equals(result))
            {
                //String s=data.verifieLaDate(name,Prenom);
                // ICI je veux faire un test pour vérifier que le date rentrée est supérieure ou égale à la date courante
                String s="ok";
                if("ok".equals(s))
                {
                    RequestDispatcher rd = request.getRequestDispatcher("Clientconnecters.jsp");
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
