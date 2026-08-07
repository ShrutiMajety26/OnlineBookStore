package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class connectionDao 
{
	Connection con=null;
	public   Connection createConnection()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@SomuComp:1521:XE","hr","hr");
		}
		catch(Exception e)	
		{
			System.err.println(e);
		}
		finally
		{
			//rs.close();
			
		}
		return con;
	}
}