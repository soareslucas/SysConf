
package ufg.inf.pw.utils;

import java.sql.*;

public class JdbcConnection
{

    public JdbcConnection()
    {
    }

    public static Connection getConnection()
    {
        try
        {
            return DriverManager.getConnection("jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3252298", "sql3252298", "DLb4wm9Bn9");
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
