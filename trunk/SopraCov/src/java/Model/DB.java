/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ridiss
 */
public final class DB {
    
    private final String url = "jdbc:derby://localhost:1527/SOPRADB;user=sorpa;password=sopra";
    private Connection conn = null;
    private Statement stmt = null;
    private final String Nomtable = "USERDB";
    private final String RoutesTable = "ROUTES";
    private static int id = 0;
    private static int idRoutes = 0;
    private int ident = -1;
    private Boolean rechercheAv = false;
    private Boolean rechercheClas = false;
    
    public DB() {
        createConnection();
    }
    
    public void createConnection() {
        try {
            //Get a connection based on the db URL
            //conn = DriverManager.getConnection(url,utilisateur,motDePasse);
            conn = DriverManager.getConnection(url);
            System.out.println("connection ok");
        } catch (Exception except) {
            System.out.println("Exception in DB class=" + except);
        }
    }
    
    //Ajout Database
    public synchronized String AjoutDB(String nom, String prenom, String email, int tel, String commune, int codePostal, String workplace, String HDMatin, String HDSoir, boolean lun, boolean mar, boolean mer, boolean jeu, boolean ven, boolean sam, boolean dim, boolean conducteur, boolean notify, String pass, int count) {
        int inserted = 0;
        String resultRoutes = "Insertion";
        String r = "Insertion";
        String nomConduc = prenom + " " + nom;
        try {
            // creates a SQL Statement object in order to execute the SQL insert command
            stmt = conn.createStatement();
            stmt.execute("insert into " + Nomtable + " (ID,Nom,Prenom,Email,Tel,Commune,CodePostal,LieuDeTravail,MorningTime,EveTime,Lundi,Mardi,Mercredi,Jeudi,Vendredi,Samedi,Dimanche,Conducteur,Notify,Password) values (" + id + ",'" + nom + "','" + prenom + "','" + email + "'," + tel + ",'" + commune + "'," + codePostal + ",'" + workplace + "','" + HDMatin + "','" + HDSoir + "','" + lun + "','" + mar + "','" + mer + "','" + jeu + "','" + ven + "','" + sam + "','" + dim + "','" + conducteur + "','" + notify + "','" + pass + "')");
            
            //insertion des trajets
            if (lun == true) {
                resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Lundi", tel, conducteur);
                inserted++;
            }
            
            if (mar == true) {
                resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Mardi", tel, conducteur);
                inserted++;
            }
            
            if (mer == true) {
                resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Mercredi", tel, conducteur);
                inserted++;
            }
            
            if (jeu == true) {
                resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Jeudi", tel, conducteur);
                inserted++;
            }
            
            if (ven == true) {
                resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Vendredi", tel, conducteur);
                inserted++;
            }
            
            if (sam == true) {
                resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Samedi", tel, conducteur);
                inserted++;
            }
            
            if (dim == true) {
                resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Dimanche", tel, conducteur);
                inserted++;
            }
            
            stmt.close();
            if (inserted == count && resultRoutes == "DataRoutes") {
                r = "Data";
            }
            
        } catch (SQLException sqlExcept) {
            r = "USERDB : " + sqlExcept.toString();
        }
        System.out.println(r + " and " + resultRoutes);
        id++;
        return r;
    }
    
    //Pour cette methode, j'ai aussi besoin d'un id de session pour supprimer toutes les informations de l'utilisateur!!!
    public synchronized String SuppDB() {
        String r = "Data";
        try {
            // creates a SQL Statement object in order to execute the SQL insert command
            stmt = conn.createStatement();
            stmt.execute("delete" + Nomtable + " (ID,Nom,Prenom,Email,Tel,Commune,CodePostal,LieuDeTravail,MorningTime,EveTime,Lundi,Mardi,Mercredi,Jeudi,Vendredi,Samedi,Dimanche,Conducteur,Notify,Password) WHERE ID=id_session");
            stmt.close();
        } catch (SQLException sqlExcept) {
            r = sqlExcept.toString();
        }
        System.out.println(r);
        id--;
        return r;
    }
    
    public synchronized String AjoutRoutesDB(int idUser, String commune, int codePostal, String workplace, String conducteur, String jour, int tel, Boolean conduc) {
        String route = "DataRoutes";
        
        try {
            if (conduc) {
                stmt.execute("insert into " + RoutesTable + " (ID,IDUser,Commune,CodePostal,LieuDeTravail,Conducteur,Jour,Tel,Password) values (" + idRoutes + "," + idUser + ",'" + commune + "'," + codePostal + ",'" + workplace + "','" + conducteur + "','" + jour + "'," + tel + ")");
            } else {
                stmt.execute("insert into " + RoutesTable + " (ID,IDUser,Commune,CodePostal,LieuDeTravail,Jour,Tel) values (" + idRoutes + "," + idUser + ",'" + commune + "'," + codePostal + ",'" + workplace + "','" + jour + "'," + tel + ")");
            }
        } catch (SQLException sqlExcept) {
            route = "ROUTES " + sqlExcept.toString();
        }
        System.out.println(route);
        idRoutes++;
        return route;
    }
    
    // prendre des données de la DB
    public String selectData() {
        String r = "";
        try {
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            // the SQL select command will provide a ResultSet containing the query results
            ResultSet results = stmt.executeQuery("select * from " + Nomtable);
            // the ResultSetMetaData object will provide information about the columns
            // for instance the number of columns, their labels, etc.
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i = 1; i <= numberCols; i++) {
                //print Column Names
                r += rsmd.getColumnLabel(i) + "  --  ";
            }
            r += "</br>";
            while (results.next()) {
                // the name and age values are retrieved from the appropiate column
                String name = results.getString(results.findColumn("Nom"));
                String Prenom = results.getString(results.findColumn("Prenom"));
                String email = results.getString(results.findColumn("Email"));
                int tel = results.getInt(results.findColumn("Tel"));
                String commune = results.getString(results.findColumn("Commune"));
                int codePostal = results.getInt(results.findColumn("CodePostal"));
                String workplace = results.getString(results.findColumn("LieuDeTravail"));
                Time departMatin = results.getTime(results.findColumn("MorningTime"));
                Time departSoir = results.getTime(results.findColumn("EveTime"));
                Date dateAppli = results.getDate(results.findColumn("DateAppli"));
                boolean conducteur = results.getBoolean(results.findColumn("Conducteur"));
                boolean notify = results.getBoolean(results.findColumn("Notify"));
                String pass = results.getString(results.findColumn("Password"));
                r += name + "  --  " + Prenom + "  --  " + email + "  --  " + tel + "  --  " + commune + "  --  " + codePostal + "  --  " + workplace
                        + "  --  " + departMatin + "  --  " + departSoir + "  --  " + dateAppli + "  --  " + conducteur + "  --  " + notify + "  --  " + pass + "</br>";
                
            }
            results.close();
            stmt.close();
        } catch (SQLException sqlExcept) {
            r = sqlExcept.toString();
        }
        return r;
    }
    
    // Verifie si on existe dans la DB
    public String verifData(String nom, String pass) {
        String r = "";
        try {
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            // the ResultSetMetaData object will provide information about the columns
            // for instance the number of columns, their labels, etc.
            try ( // the SQL select command will provide a ResultSet containing the query results
                    ResultSet results = stmt.executeQuery("SELECT * FROM " + Nomtable + " WHERE (" + Nomtable + ".Nom='" + nom + "') AND (" + Nomtable + ".Password='" + pass + "')")) {
                // the ResultSetMetaData object will provide information about the columns
                // for instance the number of columns, their labels, etc.
                ResultSetMetaData rsmd = results.getMetaData();
                System.out.println("le res"+results);
                int ID = -1;
                while (results.next()) {
                    ID = results.getInt(results.findColumn("ID"));
                    System.out.println("le id="+ID);
                }
                if (ID != -1) {
                    r = "voila";
                }
            }
            stmt.close();
        } catch (SQLException sqlExcept) {
            r = sqlExcept.toString();
        }
        System.out.println("le r "+r);
        return r;
    }
    
    
    
    public String ModifPswDB(String pass) {
        String r = "Data";
        try {
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            int entier;
            // the SQL select command will provide a ResultSet containing the query results
            entier = stmt.executeUpdate("UPDATE * FROM " + Nomtable + " WHERE (" + Nomtable + ".Password='" + pass + "')");
            // the ResultSetMetaData object will provide information about the columns
            // for instance the number of columns, their labels, etc.
            stmt.close();
        } catch (SQLException sqlExcept) {
            r = sqlExcept.toString();
        }
        System.out.println(r);
        return r;
    }
    
    // Verifie si on existe dans la DB
    public String verifDataE(String pass) {
        String r = "";
        try {
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            // the SQL select command will provide a ResultSet containing the query results
            //===<<<< associer la requette a un user, ce qui na pas encore ete fait >>>>
            ResultSet results = stmt.executeQuery("SELECT * FROM " + Nomtable + " WHERE (" + Nomtable + ".Password='" + pass + "')");
            // the ResultSetMetaData object will provide information about the columns
            // for instance the number of columns, their labels, etc.
            ResultSetMetaData rsmd = results.getMetaData();
            System.out.println("Results==========" + results);
            int ID = -1;
            while (results.next()) {
                ID = results.getInt(results.findColumn("ID"));
            }
            if (ID != -1) {
                r = "existe";
                this.ident = ID;
                System.out.println("ID=========" + ident);
            }
            
            results.close();
            stmt.close();
        } catch (SQLException sqlExcept) {
            r = sqlExcept.toString();
        }
        System.out.println("r1===============" + r);
        return r;
        
    }
    
    public String ModifPswDB(String pass, String pass1) {
        String r = "Data";
        try {
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            
            // the SQL select command will provide a ResultSet containing the query results
            stmt.executeUpdate("UPDATE " + Nomtable + " SET Password='" + pass + "' WHERE (" + Nomtable + ".Password='" + pass1 + "')");
            // the ResultSetMetaData object will provide information about the columns
            // for instance the number of columns, their labels, etc.
            stmt.close();
            // the ResultSetMetaData object will provide information about the columns
            // for instance the number of columns, their labels, etc.
        } catch (SQLException sqlExcept) {
            r = sqlExcept.toString();
        }
        
        System.out.println("r2===============" + r);
        id++;
        return r;
    }
    
    public String Recherche(String conducteur, String commune, int codePostal, String workplace) {
        String avancee = "Vide";
        rechercheAv = false;
        try {
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            // the SQL select command will provide a ResultSet containing the query results
            ResultSet results = stmt.executeQuery("SELECT * FROM " + RoutesTable + " WHERE (" + RoutesTable + ".Conducteur='" + conducteur + "') AND (" + RoutesTable + ".Commune='" + commune + "') AND (" + RoutesTable + ".CodePostal =" + codePostal + ") AND (" + RoutesTable + ".LieuDeTravail ='" + workplace + "')");
            // the ResultSetMetaData object will provide information about the columns
            // for instance the number of columns, their labels, etc.
            ResultSetMetaData rsmd = results.getMetaData();
            System.out.println("Results==========" + results);
            
            /*/on place le curseur sur le dernier tuple
            results.last();
            //on récupère le numéro de la ligne
            int nombreLignes = results.getRow();
            //on repace le curseur avant la première ligne
            results.beforeFirst();*/
            /*if(nombreLignes > 0)
            {*/
            int nbr = 0;
            avancee = "<tr class='odd'><td>";
            while (results.next()) {
                // the name and age values are retrieved from the appropiate column
                String conducteurDB = results.getString(results.findColumn("Conducteur"));
                String communeDB = results.getString(results.findColumn("Commune"));
                int codePostalDB = results.getInt(results.findColumn("CodePostal"));
                String workplaceDB = results.getString(results.findColumn("LieuDeTravail"));
                int telDB = results.getInt(results.findColumn("Tel"));
                String jourDB = results.getString(results.findColumn("Jour"));
                avancee += communeDB + "</td><th/><th/></th><th/><th/><td>" + workplaceDB + "</td><th/><th/></th><th/><th/><td >" + conducteurDB + "</td><th/><th/></th><th/><th/><td >" + telDB + "</td><th/><th/></th><th/><th/><td >" + jourDB + "</td><th/><th/></th><th/><th/><td align='center'><a style='background-color:#26ceff;' href=\"https://www.google.fr/maps/dir/" + communeDB + " " + codePostalDB + "/" + workplaceDB + "/\">Cliquez</a></td><th/><th/></th><th/><th/></tr>";
                nbr++;
                //if(nbr < nombreLignes)avancee+="<tr class='odd'>";
                avancee += "<tr class='odd'><td>";
            }
            if (nbr > 0) {
                avancee += "</tr>";
                rechercheAv = true;
            }
            
            //}
            results.close();
            stmt.close();
        } catch (SQLException sqlExcept) {
            avancee = sqlExcept.toString();
        }
        System.out.println("r1=============== Resultat non vide = " + rechercheAv);
        System.out.println("r1===============" + avancee);
        return avancee;
    }
    /**
     *
     * @param session
     * @return
     */
    
    public String RechercheClassique(String session) {
        String classic = "Vide";
        rechercheClas = true;
        try {
            System.out.println("ss"+session);
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            // the SQL select command will provide a ResultSet containing the query results
            ResultSet results = stmt.executeQuery("SELECT * FROM " + RoutesTable + " WHERE " + RoutesTable + ".LieuDeTravail IN ( SELECT LieuDeTravail FROM " +Nomtable+ " WHERE " +Nomtable+ ".Nom ='"+session+"')");;;;
            // the ResultSetMetaData object will provide information about the columns
            // for instance the number of columns, their labels, etc.
            ResultSetMetaData rsmd = results.getMetaData();
            System.out.println("Results==========" + results);
            
            int nbr = 0;
            classic = "<tr class='odd'><td>";
            while (results.next()) {
                // the name and age values are retrieved from the appropiate column
                String conducteurDB = results.getString(results.findColumn("Conducteur"));
                String communeDB = results.getString(results.findColumn("Commune"));
                int codePostalDB = results.getInt(results.findColumn("CodePostal"));
                String workplaceDB = results.getString(results.findColumn("LieuDeTravail"));
                int telDB = results.getInt(results.findColumn("Tel"));
                String jourDB = results.getString(results.findColumn("Jour"));
                classic += communeDB + "</td><th/><th/></th><th/><th/><td>" + workplaceDB + "</td><th/><th/></th><th/><th/><td >" + conducteurDB + "</td><th/><th/></th><th/><th/><td >" + telDB + "</td><th/><th/></th><th/><th/><td >" + jourDB + "</td><th/><th/></th><th/><th/><td align='center'><a style='background-color:#26ceff;' href=\"https://www.google.fr/maps/dir/" + communeDB + " " + codePostalDB + "/" + workplaceDB + "/\">Cliquez</a></td><th/><th/></th><th/><th/></tr>";
                nbr++;
                //if(nbr < nombreLignes)avancee+="<tr class='odd'>";
                classic += "<tr class='odd'><td>";
            }
            if (nbr > 0) {
                classic += "</tr>";
                rechercheClas = true;
            }
            
            results.close();
            stmt.close();
        } catch (SQLException sqlExcept) {
            classic = sqlExcept.toString();
        }
        System.out.println("r1=============== Resultat non vide = " + rechercheClas);
        System.out.println("r1===============" + classic);
        return classic;
    }
    
    //fermeture de la connection
    public void shutdown() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException sqlExcept) {
            System.out.println("SQLException in DB class=" + sqlExcept);
        }
    }
    
    /**
     * @return the rechercheAv
     */
    public Boolean getRechercheAv() {
        return rechercheAv;
    }
    
    /**
     *
     * @return
     */
    public Boolean getrechercheClas() {
        return rechercheClas;
    }
    
    
    
}
