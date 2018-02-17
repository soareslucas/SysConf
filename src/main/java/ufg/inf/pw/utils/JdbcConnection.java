
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
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/confeccao", "root", "");
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
