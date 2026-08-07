package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask 
{
	public void run()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println("**********Last Updated*******"+dateFormat.format(date)+"\n");
		
		Connection con = null;
		PreparedStatement ps = null;
		String query = null;
		try 
		{			
			query = "update shipping set status='D' , dod=sysdate where status<>'C' and status<>'D' ";	
			System.out.println("query= "+query);
			con = new connectionDao().createConnection();			
			ps = con.prepareStatement(query);
			int cnt= ps.executeUpdate(); // execute insert statement
			System.out.println("Staus updated "+cnt);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
		finally 
		{
			try 
			{
				ps.close();
				con.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	public static void main(String args[]) throws InterruptedException
	{		 
		Timer time = new Timer(); // Instantiate Timer Object
		ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class
		time.schedule(st,0,900000 ); // every 15 mins
	}
}