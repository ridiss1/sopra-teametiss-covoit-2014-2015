/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormPage;

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
private final String url = "jdbc:derby://localhost:1527/SopraDB;user=sopra;password=sopra";
private Connection conn = null;
private  Statement stmt = null;
private final  String Nomtable = "UserDB";
private static int id=0;

        

public DB() {
         createConnection();
    }


   public void createConnection()
   {
       try
       {
           //Get a connection based on the db URL
           //conn = DriverManager.getConnection(url,utilisateur,motDePasse);
           conn = DriverManager.getConnection(url);
           System.out.println("connection ok");
       }
       catch (Exception except)
       {
           System.out.println("Exception in DB class="+except);
       }
   }

    //Ajout Database
            
   public synchronized String AjoutDB(String nom, String prenom, String email,int tel, String commune, int codePostal,String workplace,String HDMatin,String HDSoir,boolean lun,boolean mar,boolean mer,boolean jeu,boolean ven,boolean sam,boolean dim,boolean conducteur,boolean notify,String pass)
   {
       String r="Data";
       
       try
       {
           // creates a SQL Statement object in order to execute the SQL insert command
           stmt = conn.createStatement();
           stmt.execute("insert into " + Nomtable + " (ID,Nom,Prenom,Email,Tel,Commune,CodePostal,LieuDeTravail,MorningTime,EveTime,Lundi,Mardi,Mercredi,Jeudi,Vendredi,Samedi,Dimanche,Conducteur,Notify,Password) values (" +id +",'" +nom+ "','" + prenom + "','"+ email +"',"+tel+",'"+commune+"',"+codePostal+",'"+workplace+"','"+HDMatin+"','"+ HDSoir+"','"+lun+"','"+mar+"','"+mer+"','"+jeu+"','"+ven+"','"+sam+"','"+dim+"','"+conducteur+"','"+notify+"','"+pass+"')");
           stmt.close();
//           +"','"+tel+"','"+commune+"','"+codePostal+"','"+workplace+"','"+HDMatin+"','"+ HDSoir+"','"+jrsAppli+"','"+conducteur+"','"+notify
//             ,Tel,Commune,CodePostal,LieuDeTravail,MorningTime,EveTime,DateAppli,Conducteur,Notify
       }
       catch (SQLException sqlExcept)
       {
           r=sqlExcept.toString();
       }
       System.out.println(r);
       id++;
       return r;             
   }
      
      //Pour cette methode, j'ai aussi besoin d'un id de session pour supprimer toutes les informations de l'utilisateur!!!
      public synchronized String SuppDB()
      {
       String r="Data";
       
       try
       {
           // creates a SQL Statement object in order to execute the SQL insert command
           stmt = conn.createStatement();
           stmt.execute("delete" + Nomtable + " (ID,Nom,Prenom,Email,Tel,Commune,CodePostal,LieuDeTravail,MorningTime,EveTime,Lundi,Mardi,Mercredi,Jeudi,Vendredi,Samedi,Dimanche,Conducteur,Notify,Password) WHERE ID=id_session");
           stmt.close();

       }
       catch (SQLException sqlExcept)
       {
           r=sqlExcept.toString();
       }
       System.out.println(r);
       id--;
       return r;             
   }
    
   // prendre des données de la DB
   public String selectData()
   {
       String r="";
       try
       {
           // creates a SQL Statement object in order to execute the SQL select command
           stmt = conn.createStatement();
           // the SQL select command will provide a ResultSet containing the query results
           ResultSet results = stmt.executeQuery("select * from " + Nomtable);
           // the ResultSetMetaData object will provide information about the columns
           // for instance the number of columns, their labels, etc.
           ResultSetMetaData rsmd = results.getMetaData();
           int numberCols = rsmd.getColumnCount();
           for (int i=1; i<=numberCols; i++)
           {
               //print Column Names
               r+=rsmd.getColumnLabel(i)+"  --  ";
           }
           r+="</br>";
           while(results.next())
           {
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
               String pass=results.getString(results.findColumn("Password"));
               r+=name + "  --  " + Prenom+"  --  " + email+ "  --  " + tel+"  --  " + commune+"  --  " + codePostal+"  --  " + workplace+
                       "  --  " + departMatin+"  --  " + departSoir+"  --  " + dateAppli+"  --  " + conducteur+"  --  " + notify+"  --  " +pass+"</br>";

           }
           results.close();
           stmt.close();
       }
       catch (SQLException sqlExcept)
       {
           r=sqlExcept.toString();
       }
       return r;
   }

    // Verifie si on existe dans la DB
   public String verifData(String nom,String pass)
   {
       String r="";
       try
       {
           // creates a SQL Statement object in order to execute the SQL select command
           stmt = conn.createStatement();
           // the SQL select command will provide a ResultSet containing the query results           
           ResultSet results = stmt.executeQuery("SELECT * FROM "+Nomtable+" WHERE ("+Nomtable+".Nom='"+nom+"') AND ("+Nomtable+".Password='"+pass+"')");
           // the ResultSetMetaData object will provide information about the columns
           // for instance the number of columns, their labels, etc.
           ResultSetMetaData rsmd = results.getMetaData();
           int ID = -1;
           while(results.next())
           {               
               ID = results.getInt(results.findColumn("ID")); 
           }
           if(ID!=-1)
           {
                r="voila";
           }
          
           results.close();
           stmt.close();
       }
       catch (SQLException sqlExcept)
       {
           r=sqlExcept.toString();
       }
       return r;
   }
   
    // Verifie si on existe dans la DB
   public String verifData(String pass)
   {
       String r="";
       try
       {
           // creates a SQL Statement object in order to execute the SQL select command
           stmt = conn.createStatement();
           // the SQL select command will provide a ResultSet containing the query results           
           ResultSet results = stmt.executeQuery("SELECT * FROM "+Nomtable+" WHERE "+Nomtable+".Password='"+pass+"'");
           // the ResultSetMetaData object will provide information about the columns
           // for instance the number of columns, their labels, etc.
           ResultSetMetaData rsmd = results.getMetaData();
           int ID = -1;
           while(results.next())
           {               
               ID = results.getInt(results.findColumn("ID")); 
           }
           if(ID!=-1)
           {
                r="voila";
           }
          
           results.close();
           stmt.close();
       }
       catch (SQLException sqlExcept)
       {
           r=sqlExcept.toString();
       }
       return r;
   }
   
   public String ModifPswDB(String pass)
   {
       String r="Data";
       try
       {
           // creates a SQL Statement object in order to execute the SQL select command
           stmt = conn.createStatement();
           int entier;
           // the SQL select command will provide a ResultSet containing the query results           
           entier = stmt.executeUpdate("UPDATE * FROM "+Nomtable+" WHERE ("+Nomtable+".Password='"+pass+"')");
           // the ResultSetMetaData object will provide information about the columns
           // for instance the number of columns, their labels, etc.
           stmt.close();
       }
       catch (SQLException sqlExcept)
       {
           r=sqlExcept.toString();
       }
       System.out.println(r);
       return r;
   }
   
    
   //fermeture de la connection
   
   public void shutdown()
   {
       try
       {
           if (stmt != null)
               stmt.close();
           if (conn != null)
               conn.close();
       }
       catch (SQLException sqlExcept)
       {
           System.out.println("SQLException in DB class="+sqlExcept);
       }
   }

 
        

}
    
    
    

