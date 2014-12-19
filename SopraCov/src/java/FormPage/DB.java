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
public class DB {
private String url = "jdbc:derby://localhost:1527/SOPRA;user=sopra;password=sopra";
private String utilisateur = "sopra";
private String motDePasse = "sopra";
private Connection conn = null;
private  Statement stmt = null;
private  String Nomtable = "USERDB";
private int id=0;

        

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
           except.printStackTrace();
       }
   }

    //Ajout Database
            
   public synchronized String AjoutDB(String nom, String prenom, String email,int tel, String commune, int codePostal,String workplace,Time HDMatin,Time HDSoir,String jrsAppli,boolean conducteur,boolean notify)
   {
       String r="Data inserted!";
       
       try
       {
           // creates a SQL Statement object in order to execute the SQL insert command
           stmt = conn.createStatement();
           stmt.execute("insert into " + Nomtable + " (Nom,Prenom,Email,Tel,Commune,CodePostal,LieuDeTravail,MorningTime,EveTime,DateAppli,Conducteur,Notify) values ('" +nom+ "','" + prenom + "','"+ email+"',"+tel+",'"+commune+"',"+codePostal+",'"+workplace+"','"+HDMatin+"','"+ HDSoir+"','"+jrsAppli+"','"+conducteur+"','"+notify+"')");
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
               r+=name + "  --  " + Prenom+"  --  " + email+ "  --  " + tel+"  --  " + commune+"  --  " + codePostal+"  --  " + workplace+
                       "  --  " + departMatin+"  --  " + departSoir+"  --  " + dateAppli+"  --  " + conducteur+"  --  " + notify+"</br>";
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
           sqlExcept.printStackTrace();
       }
   }

 
        

}
    
    
    

