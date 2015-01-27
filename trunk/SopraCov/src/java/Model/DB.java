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
    private int id = -1;
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
        String jours = "";
        try {
            // creates a SQL Statement object in order to execute the SQL insert command
            stmt = conn.createStatement();

            stmt.execute("insert into " + Nomtable + " (Nom,Prenom,Email,Tel,Commune,CodePostal,LieuDeTravail,MorningTime,EveTime,Lundi,Mardi,Mercredi,Jeudi,Vendredi,Samedi,Dimanche,Conducteur,Notify,Password) values (" + "'" + nom + "','" + prenom + "','" + email + "'," + tel + ",'" + commune + "'," + codePostal + ",'" + workplace + "','" + HDMatin + "','" + HDSoir + "','" + lun + "','" + mar + "','" + mer + "','" + jeu + "','" + ven + "','" + sam + "','" + dim + "','" + conducteur + "','" + notify + "','" + pass + "')");
            ResultSet rese = stmt.executeQuery("SELECT ID FROM " + Nomtable + " WHERE (" + Nomtable + ".Nom='" + nom + "') AND (" + Nomtable + ".Password='" + pass + "')");
            //id = rese.findColumn("ID");
            rese.next(); //pour aller à la ligne du résultat
            setId(rese.getInt(rese.findColumn("ID")));
            System.out.println("ID=============== " + id);
            stmt.close();
           //insertion des trajets
            if (lun == true) {
                //resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Lundi", tel, conducteur);
                jours="Lundi";
                inserted++;
            }

            if (mar == true) {
                //resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Mardi", tel, conducteur);
                if(inserted > 0) jours+=", Mardi";
                else jours="Mardi";
                inserted++;
            }

            if (mer == true) {
                //resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Mercredi", tel, conducteur);
                if(inserted > 0) jours+=", Mercredi";
                else jours="Mercredi";
                inserted++;
            }

            if (jeu == true) {
                //resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Jeudi", tel, conducteur);
                if(inserted > 0) jours+=", Jeudi";
                else jours="Jeudi";
                inserted++;
            }

            if (ven == true) {
                //resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Vendredi", tel, conducteur);
                if(inserted > 0) jours+=", Vendredi";
                else jours="Vendredi";
                inserted++;
            }

            if (sam == true) {
                //resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Samedi", tel, conducteur);
                if(inserted > 0) jours+=", Samedi";
                else jours="Samedi";
                inserted++;
            }

            if (dim == true) {
                //resultRoutes = AjoutRoutesDB(id, commune, codePostal, workplace, nomConduc, "Dimanche", tel, conducteur);
                if(inserted > 0) jours+=", Dimanche";
                else jours="Dimanche";
                inserted++;
            }
            
            if (inserted == count)
            {
                System.out.println(" ***************id in routesDB = " + getId());
                resultRoutes = AjoutRoutesDB(getId(), commune, codePostal, workplace, nomConduc, jours, tel, conducteur,HDMatin,HDSoir);
            }
            if (inserted == count && resultRoutes == "DataRoutes") {
                r = "Data";
            }

        } catch (SQLException sqlExcept) {
            r = "USERDB : " + sqlExcept.toString();
        }
        System.out.println(r + " and " + resultRoutes);
        return r;
    }

    //Pour cette methode, j'ai aussi besoin d'un id de session pour supprimer toutes les informations de l'utilisateur!!!
    public synchronized String SuppDB(String nom, String pass) {
        String r = "Data";
        try {
            // creates a SQL Statement object in order to execute the SQL insert command
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM " + Nomtable + " WHERE (" + Nomtable + ".Prenom='" + nom + "') AND (" + Nomtable + ".Password='" + pass + "')");
            int id_session = 0;
            while (results.next()) {
                id_session = results.getInt(results.findColumn("ID"));
            }

            stmt.execute("DELETE FROM " + RoutesTable + " WHERE CAST(" + RoutesTable + ".IDUser AS INTEGER)=" + id_session);
            stmt.execute("DELETE FROM " + Nomtable + " WHERE CAST(" + Nomtable + ".ID AS INTEGER)=" + id_session);
            stmt.close();
        } catch (SQLException sqlExcept) {
            r = sqlExcept.toString();
        }
        System.out.println("supression==================" + r);
        return r;
    }

    public synchronized String AjoutRoutesDB(int idUser, String commune, int codePostal, String workplace, String conducteur, String jours, int tel, Boolean conduc,String HDMatin,String HDSoir) {
        String route = "DataRoutes";

        try 
        {
             // creates a SQL Statement object in order to execute the SQL insert command
            stmt = conn.createStatement();
            if (conduc) {
                stmt.execute("insert into " + RoutesTable + " (IDUser,Commune,CodePostal,LieuDeTravail,Conducteur,Jours,Tel,MorningTime,EveTime) values (" + idUser + ",'" + commune + "'," + codePostal + ",'" + workplace + "','" + conducteur + "','" + jours + "'," + tel+",'" + HDMatin + "','" + HDSoir + "')");
            } else {
                stmt.execute("insert into " + RoutesTable + " (IDUser,Commune,CodePostal,LieuDeTravail,Jours,Tel,MorningTime,EveTime) values (" + idUser + ",'" + commune + "'," + codePostal + ",'" + workplace + "','" + jours + "'," + tel+",'" + HDMatin + "','" + HDSoir + "')");
            }
            stmt.close();
        } 
        catch (SQLException sqlExcept) 
        {
            route = "ROUTES " + sqlExcept.toString();
        }
        System.out.println(route);
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
                    ResultSet results = stmt.executeQuery("SELECT * FROM " + Nomtable + " WHERE (" + Nomtable + ".Prenom='" + nom + "') AND (" + Nomtable + ".Password='" + pass + "')")) {
                // the ResultSetMetaData object will provide information about the columns
                // for instance the number of columns, their labels, etc.
                ResultSetMetaData rsmd = results.getMetaData();
                System.out.println("le res" + results);
                int ID = -5;
                while (results.next()) {
                    ID = results.getInt(results.findColumn("ID"));
                    System.out.println("le id=" + ID);
                }
                if (ID != -5) {
                    r = "voila";
                }
            }
            stmt.close();
        } catch (SQLException sqlExcept) {
            r = sqlExcept.toString();
        }
        System.out.println("le r " + r);
        return r;
    }

    // Verifie si on existe dans la DB
    public String verifID(String Email, String pass) {
        String r = "";
        try {
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            // the ResultSetMetaData object will provide information about the columns
            // for instance the number of columns, their labels, etc.
            try ( // the SQL select command will provide a ResultSet containing the query results
                    ResultSet results = stmt.executeQuery("SELECT * FROM " + Nomtable + " WHERE (" + Nomtable + ".Email='" + Email + "') AND (" + Nomtable + ".Password='" + pass + "')")) {
                // the ResultSetMetaData object will provide information about the columns
                // for instance the number of columns, their labels, etc.
                ResultSetMetaData rsmd = results.getMetaData();
                System.out.println("le res"+results);
                int ID = -5;
                while (results.next()) {
                    ID = results.getInt(results.findColumn("ID"));
                    System.out.println("le id="+ID);
                }
                if (ID != -5) {
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
        return r;
    }
	
	public String ModifHorair(String matin, String soir,int id) {
        String r = "Data";
        try {
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            String vide="";            
            String s1 = "" + matin;
            String s2 = "" + soir;
            if(!vide.equals(s1))
            {
                matin=s1+":00";
                stmt.executeUpdate("UPDATE " + RoutesTable + " SET MorningTime='" + matin + "' WHERE CAST(" + RoutesTable + ".IDUser AS INTEGER)=" +id );
                stmt.executeUpdate("UPDATE " + Nomtable + " SET MorningTime='" + matin + "' WHERE CAST(" + Nomtable + ".ID AS INTEGER)=" +id );
            }
            if(!vide.equals(s2))
            {
                soir=s2+":00";
                stmt.executeUpdate("UPDATE " + RoutesTable + " SET EveTime='" + soir + "'  WHERE CAST(" + RoutesTable + ".IDUser AS INTEGER)=" +id );            
                stmt.executeUpdate("UPDATE " + Nomtable + " SET EveTime='" + soir + "'  WHERE CAST(" + Nomtable + ".ID AS INTEGER)=" +id );
            }
            // the SQL select command will provide a ResultSet containing the query results
            //ResultSet rese = stmt.executeQuery("SELECT ID FROM " + Nomtable + " WHERE (" + Nomtable + ".Prenom='" + prenom + "')");
            //id = rese.findColumn("ID");            
            //stmt.executeUpdate("UPDATE " + Nomtable + " SET MorningTime='" + matin + "',EveTime='" + soir + "'  WHERE (" + Nomtable + ".Prenom='" +prenom +"')");
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
    
    public String ModifDomicil(String Domicil, int CodeP, String lieu,int id,String tel) {
        String r = "Data";
        try {
            // creates a SQL Statement object in order to execute the SQL select command
            String vide="";
            String s1=""+Domicil;
            String s2=""+lieu;
            String s3=""+tel;
            stmt = conn.createStatement();

            // the SQL select command will provide a ResultSet containing the query results
            //ResultSet rese = stmt.executeQuery("SELECT ID FROM " + Nomtable + " WHERE (" + Nomtable + ".Prenom='" + prenom + "')");
            //id = rese.findColumn("ID");
            if(!s1.equals(vide))
            {                
              stmt.executeUpdate("UPDATE " + RoutesTable + " SET Commune='" + Domicil + "' WHERE CAST(" + RoutesTable + ".IDUser AS INTEGER)=" +id );
              stmt.executeUpdate("UPDATE " + RoutesTable + " SET CodePostal=" + CodeP + "  WHERE CAST(" + RoutesTable + ".IDUser AS INTEGER)=" +id );
              stmt.executeUpdate("UPDATE " + Nomtable + " SET Commune='" + Domicil + "' WHERE CAST(" + Nomtable + ".ID AS INTEGER)=" +id );
              stmt.executeUpdate("UPDATE " + Nomtable + " SET CodePostal=" + CodeP + "  WHERE CAST(" + Nomtable + ".ID AS INTEGER)=" +id );
            }
            if(!s2.equals(vide))
            {               
              stmt.executeUpdate("UPDATE " + RoutesTable + " SET LieuDeTravail='" + lieu + "'  WHERE CAST(" + RoutesTable + ".IDUser AS INTEGER)=" +id );              
              stmt.executeUpdate("UPDATE " + Nomtable + " SET LieuDeTravail='" + lieu + "'  WHERE CAST(" + Nomtable + ".ID AS INTEGER)=" +id );              
            }
            if(!s3.equals(vide))
            {
                int x=Integer.parseInt(tel);              
              stmt.executeUpdate("UPDATE " + RoutesTable+ " SET Tel=" + x + "  WHERE CAST(" + RoutesTable + ".IDUser AS INTEGER)=" +id );              
              stmt.executeUpdate("UPDATE " + Nomtable + " SET Tel=" + x + "  WHERE CAST(" + Nomtable + ".ID AS INTEGER)=" +id );              
            }
            //stmt.executeUpdate("UPDATE " + Nomtable + " SET Commune='" + Domicil + "',CodePostal=" + CodeP + ",LieuDeTravail='" + lieu + "',Email='"+Email+"',Tel="+tel+" WHERE (" + Nomtable + ".Prenom='" + prenom + "')");
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

    public String ModifJour(boolean l,boolean m,boolean me,boolean j,boolean v,boolean s,boolean d,boolean not,boolean con,int id) {
        String r = "Data";
        try {
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            String jour=" ";
            String name;
            if(l)
            {
                jour+=" Lundi";
            }
            if(m)
            {
                jour+=" Mardi";
            }
            if(me)
            {
                jour+=" Mercredi";
            }
            if(j)
            {
                jour+=" Jeudi";
            }
            if(v)
            {
                jour+=" vendredi";
            }
            if(s)
            {
                jour+=" Samedi";
            }
            if(d)
            {
                jour+=" Dimanche";
            }
            
            
            if(con==false)
            {
                name="-";
                stmt.executeUpdate("UPDATE " + RoutesTable + " SET Jours='"+jour+"',Conducteur='"+name+"' WHERE CAST(" + RoutesTable + ".IDUser AS INTEGER)=" +id );
            }
            else
            {
             stmt.executeUpdate("UPDATE " + RoutesTable + " SET Jours='"+jour+"' WHERE CAST(" + RoutesTable + ".IDUser AS INTEGER)=" +id );   
            }

            // the SQL select command will provide a ResultSet containing the query results
            //ResultSet rese = stmt.executeQuery("SELECT ID FROM " + Nomtable + " WHERE (" + Nomtable + ".Prenom='" + prenom + "')");
            //id = rese.findColumn("ID");
            stmt.executeUpdate("UPDATE " + Nomtable + " SET Lundi='"+l+"',Mardi='"+m+"',Mercredi='"+me+"',Jeudi='"+j+"',Vendredi='"+v+"',Samedi='"+s+"',Dimanche='"+d+"',Conducteur='"+con+"',Notify='"+not+"' WHERE CAST(" + Nomtable + ".ID AS INTEGER)=" +id );
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
        String vide = "";
        try {
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            // the SQL select command will provide a ResultSet containing the query results
            ResultSet results;
           // the SQL select command will provide a ResultSet containing the query results 
           if(conducteur.equals(vide))
           {
              results = stmt.executeQuery("SELECT *  FROM "+RoutesTable+" WHERE ("+RoutesTable+".Commune='"+commune+"') AND ("+RoutesTable+".CodePostal ="+codePostal+") AND ("+RoutesTable+".LieuDeTravail ='"+workplace+"')"); 
           }
           else
           {
              results = stmt.executeQuery("SELECT  *  FROM "+RoutesTable+" WHERE ("+RoutesTable+".Conducteur='"+conducteur+"') AND ("+RoutesTable+".Commune='"+commune+"') AND ("+RoutesTable+".CodePostal ="+codePostal+") AND ("+RoutesTable+".LieuDeTravail ='"+workplace+"')");  
           }     
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
            String conducteurDB, ici;
            while (results.next()) {
                // the name and age values are retrieved from the appropiate column
                conducteurDB = results.getString(results.findColumn("Conducteur"));
                System.out.println("***************" + conducteurDB + "---");
                String s = "null";
                String s1 = "" + conducteurDB;
                if (s.equals(s1)) {
                    ici = "-";
                    System.out.println("ici1===");
                } else {
                    ici = conducteurDB;
                    System.out.println("ici2====");
                }
                String communeDB = results.getString(results.findColumn("Commune"));
                int codePostalDB = results.getInt(results.findColumn("CodePostal"));
                String workplaceDB = results.getString(results.findColumn("LieuDeTravail"));
                int telDB = results.getInt(results.findColumn("Tel"));
                String jourDB = results.getString(results.findColumn("Jours"));
                String HorairesDB = "Matin : "+results.getString(results.findColumn("MorningTime"));
                HorairesDB +=" <br>"+"Soir : "+results.getString(results.findColumn("EveTime"));
                avancee += communeDB + "</td><th/><th/></th><th/><th/><td>" + workplaceDB + "</td><th/><th/></th><th/><th/><td >" + ici + "</td><th/><th/></th><th/><th/><td >" + telDB + "</td><th/><th/></th><th/><th/><td >" + jourDB + "</td><th/><th/></th><th/><th/><td >" + HorairesDB + "</td><th/><th/></th><th/><th/><td align='center'><a style='background-color:#26ceff;' href=\"https://www.google.fr/maps/dir/" + communeDB + " " + codePostalDB + "/" + workplaceDB + "/\">Cliquez</a></td><th/><th/></th><th/><th/></tr>";
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
    @SuppressWarnings("empty-statement")
    public String RechercheClassique(String session,int ID) {
        String classic = "Vide";
        rechercheClas = true;
        try {
            System.out.println("ss" + session);
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            // the SQL select command will provide a ResultSet containing the query results
            ResultSet results = stmt.executeQuery("SELECT * FROM " + RoutesTable + " WHERE " + RoutesTable + ".LieuDeTravail IN ( SELECT LieuDeTravail FROM " + Nomtable + " WHERE " + Nomtable + ".Prenom ='" + session + "' AND "+ Nomtable + ".ID="+ID+")");
            // the ResultSetMetaData object will provide information about the columns
            // for instance the number of columns, their labels, etc.
            ResultSetMetaData rsmd = results.getMetaData();
            System.out.println("Results==========" + results);

            int nbr = 0;
            classic = "<tr class='odd'><td>";
            String conducteurDB, ici;
            while (results.next()) {
                // the name and age values are retrieved from the appropiate column
                conducteurDB = results.getString(results.findColumn("Conducteur"));
                System.out.println("***************" + conducteurDB + "---");
                String s = "null";
                String s1 = "" + conducteurDB;
                if (s.equals(s1)) {
                    ici = "-";
                    System.out.println("ici1===");
                } else {
                    ici = conducteurDB;
                    System.out.println("ici2====");
                }
                String communeDB = results.getString(results.findColumn("Commune"));
                int codePostalDB = results.getInt(results.findColumn("CodePostal"));
                String workplaceDB = results.getString(results.findColumn("LieuDeTravail"));
                int telDB = results.getInt(results.findColumn("Tel"));
                String jourDB = results.getString(results.findColumn("Jours"));
                String HorairesDB = "Matin : "+results.getString(results.findColumn("MorningTime"));
                HorairesDB +=" <br>"+"Soir : "+results.getString(results.findColumn("EveTime"));
                classic += communeDB + "</td><th/><th/></th><th/><th/><td>" + workplaceDB + "</td><th/><th/></th><th/><th/><td >" + ici + "</td><th/><th/></th><th/><th/><td >" + telDB + "</td><th/><th/></th><th/><th/><td >" + jourDB + "</td><th/><th/></th><th/><th/><td >" + HorairesDB + "</td><th/><th/></th><th/><th/><td align='center'><a style='background-color:#26ceff;' href=\"https://www.google.fr/maps/dir/" + communeDB + " " + codePostalDB + "/" + workplaceDB + "/\">Cliquez</a></td><th/><th/></th><th/><th/></tr>";
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
     * @param aId the id to set
     */
    public void setId(int aId) {
        id = aId;
    }

    /**
     *
     * @return
     */
    public Boolean getrechercheClas() {
        return rechercheClas;
    }

    // Verifie si on existe dans la DB
    public String recupNom(String mail, String pass) {
        String r = "";
        try {
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            // the SQL select command will provide a ResultSet containing the query results
            //===<<<< associer la requette a un user, ce qui na pas encore ete fait >>>>
            ResultSet results = stmt.executeQuery("SELECT Prenom FROM " + Nomtable + " WHERE (" + Nomtable + ".Email='" + mail + "') AND (" + Nomtable + ".Password ='" + pass + "')");
            // the ResultSetMetaData object will provide information about the columns
            // for instance the number of columns, their labels, etc.
            ResultSetMetaData rsmd = results.getMetaData();
            System.out.println("Results==========" + results);
            String nm = "";
            String vide = "";
            while (results.next()) {
                nm = results.getString(results.findColumn("Prenom"));
            }
            if (!vide.equals(nm)) {
                r = nm;
            }

            results.close();
            stmt.close();
        } catch (SQLException sqlExcept) {
            r = sqlExcept.toString();
        }
        System.out.println("ok===============" + r);
        return r;

    }
    
    // Verifie si on existe dans la DB
    public String recupID(String mail, String pass) {
        String r = "";
        try {
            // creates a SQL Statement object in order to execute the SQL select command
            stmt = conn.createStatement();
            // the SQL select command will provide a ResultSet containing the query results
            //===<<<< associer la requette a un user, ce qui na pas encore ete fait >>>>
            ResultSet results = stmt.executeQuery("SELECT ID FROM " + Nomtable + " WHERE (" + Nomtable + ".Email='" + mail + "') AND (" + Nomtable + ".Password ='" + pass + "')");
            // the ResultSetMetaData object will provide information about the columns
            // for instance the number of columns, their labels, etc.
            ResultSetMetaData rsmd = results.getMetaData();
            System.out.println("Results==========" + results);
            String nm = "";
            String vide = "";
            while (results.next()) {
                nm = results.getString(results.findColumn("ID"));
            }
            if (!vide.equals(nm)) {
                r = nm;
            }

            results.close();
            stmt.close();
        } catch (SQLException sqlExcept) {
            r = sqlExcept.toString();
        }
        System.out.println("ok===============" + r);
        return r;

    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
}
