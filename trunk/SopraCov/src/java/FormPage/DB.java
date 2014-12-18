/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author Ridiss
 */
public class DB {
private String url = "jdbc:derby://localhost:1527/sopcov";
private String utilisateur = "idriss";
private String motDePasse = "idriss";
private Connection conn = null;
private  Statement stmt = null;
private  String Nomtable = "SOPRA";

        

public DB() {
         createConnection();
    }


   public void createConnection()
   {
       try
       {
           //Get a connection based on the db URL
           conn = DriverManager.getConnection(url,"idirss","idriss");
           System.out.println("connection ok");
       }
       catch (Exception except)
       {
           except.printStackTrace();
       }
   }

    //Ajout Database
            
   public synchronized String AjoutDB(String nom, String prenom, String email)
   {
       String r="Data inserted!";
       try
       {
           // creates a SQL Statement object in order to execute the SQL insert command
           stmt = conn.createStatement();
           stmt.execute("insert into " + Nomtable + " (Nom,Prenom) values ('" +nom+ "','" + prenom + "','"+ email +"')");
           stmt.close();
       }
       catch (SQLException sqlExcept)
       {
           r=sqlExcept.toString();
       }
       System.out.println(r);
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
               r+=name + "  --  " + Prenom+"</br>";
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
    
    
    

