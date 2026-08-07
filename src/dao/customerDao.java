package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class customerDao 
{
	public int validateCustomer(String uname,String pass)
	{
		ResultSet rs=null;
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			con=new connectionDao().createConnection();		
			ps=con.prepareStatement("select c_id from customer where upper(cname)=upper('"+uname+"') and password='"+pass+"'");	
		    rs=ps.executeQuery();		    	
			while (rs.next())
				return Integer.parseInt(rs.getString("c_id"));			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				rs.close();
				ps.close();
				con.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}			
		}
		return -1;
	}
	
	public int addCustomer(String uname,String phnNo,String gender,String email,String address,String password)
	{
		Connection con=null;
		PreparedStatement ps=null;
		int cnt=0;
		try
		{
			con=new connectionDao().createConnection();	
			System.out.println("uname "+uname+ " phnNo= "+phnNo+" email= "+email+" gender= "+gender+" address= "+address+" password= "+password);
			ps=con.prepareStatement("insert into customer values(CUSTOMER_SEQ.nextval,?,?,?,?,?,?)");		    
		    ps.setString(1,uname); // set input parameter 1
		    ps.setString(2,phnNo); // set input parameter 2
		    ps.setString(3,gender); // set input parameter 4
		    ps.setString(4,email); // set input parameter 3		      
		    ps.setString(5,address); // set input parameter 5
		    ps.setString(6,password); //set input parameter 6		      
		    cnt= ps.executeUpdate(); // execute insert statement		      
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
		return cnt;
	}
	public 	ArrayList<String> getCustomer(int cid)
	{
		ArrayList<String> arr=null;
		ResultSet rs=null;
		Connection con=null;
		PreparedStatement ps=null;
		try
		{
			System.out.println("customer id= "+cid);
			con=new connectionDao().createConnection();		
			ps=con.prepareStatement("Select * from customer where c_id="+cid);	
		    rs=ps.executeQuery();		 
			while (rs.next())
			{					
					arr=new ArrayList<String>();
					arr.add(rs.getString(1));
					arr.add(rs.getString(2));
					arr.add(rs.getString(3));
					arr.add(rs.getString(4));
					arr.add(rs.getString(5));
					arr.add(rs.getString(6));
					arr.add(rs.getString(7));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				rs.close();
				ps.close();
				con.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}			
		}
		return arr;
	}
	public int editCustomer(int cid,String uname,double phnNo,String gender,String email,String address,String password)
	{
		Connection con=null;
		PreparedStatement ps=null;
		int cnt=0;
		try
		{
			con=new connectionDao().createConnection();	
			System.out.println("con= "+con);
			ps=con.prepareStatement("update customer set cname=?, phone = ?, email=?, gender=?, address=?,password=? where c_id="+cid);		    
		    ps.setString(1,uname); // set input parameter 1
		    ps.setDouble(2,phnNo); // set input parameter 2
		    ps.setString(3,email); // set input parameter 4
		    ps.setString(4,gender); // set input parameter 3		      
		    ps.setString(5,address); // set input parameter 5
		    ps.setString(6,password); //set input parameter 6		      
		    cnt= ps.executeUpdate(); // execute insert statement		      
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
		return cnt;
	}
	public int delete(int cid) 
	{
		int cnt=0;
		Connection con = null;
		PreparedStatement ps = null;
		String query = "delete from CUSTOMER where 1=1 ";
		try {
			if(cid!=0)
				query = query + " and c_id=" + cid;
			con = new connectionDao().createConnection();
			System.out.println("===query---"+query);
			ps = con.prepareStatement(query);
			cnt = ps.executeUpdate();	
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return cnt;
	}
}