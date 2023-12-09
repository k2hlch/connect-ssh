package com.oracle.demo.db;
/* $Header: DatabaseUtil.java 06-sep-2002.11:22:43 erajkovi Exp $ */

/* Copyright (c) 2002, Oracle Corporation.  All rights reserved.  */

/*
   DESCRIPTION
    Database utility methods - uses datasource :jdbc/OracleHotelDS

   PRIVATE CLASSES
   -none

   NOTES
    Does Hotel search , room reservation 

   MODIFIED    (MM/DD/YY)
    erajkovi   09/06/02 - erajkovi_copy_samples
    anchatte   06/07/02 - anchatte_hotel_reservation
    Anirban    03/08/03 - Creation
 */

/**
 *  @version $Header: DatabaseUtil.java 06-sep-2002.11:22:43 erajkovi Exp $
 *  @author  Anirban Chatterjee
 *  @since   
 */

import com.oracle.demo.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;


public class DatabaseUtil 
{
  public DatabaseUtil()
  {
  }

  private static final String dsName = "jdbc/OracleHotelDS";

  //hard coded for non data source usage  
  private static final String jdbcURL = "jdbc:oracle:thin:@machine:portNum:sid";
  


  public static Connection getConnection() throws SQLException
  {
    try{
       return getConnection(dsName);
    }catch(NamingException ne){
          if(Hotel.DEBUG)System.out.println(ne);
    //    use hard coded driver to get the connection   
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        return DriverManager.getConnection(jdbcURL,"SCOTT","TIGER");
    }
  
  }

  public static  Connection getConnection(String dsName)
       throws SQLException ,NamingException
  {
    DataSource ds = getDataSource(dsName);
    try{
    return ds.getConnection();
    }catch(SQLException sqe){
      throw new SQLException("..some problem with datasource specifications  "+sqe.getMessage());
    }
  }

  private  static DataSource getDataSource(String dsName)
       throws NamingException
  {
    DataSource ds = null;
    Context ic = new InitialContext();
    ds = (DataSource) ic.lookup(dsName);
    return ds;
  }


 

  
  
}