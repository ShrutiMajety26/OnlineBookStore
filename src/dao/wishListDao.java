package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class wishListDao {
	public int addtoWishList(long bid, int cid)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		int cnt = 0;
		try
		{		
			String query="select count(*) from wishlist where isbn="+bid+ " and c_id="+cid;
			con = new connectionDao().createConnection(); //to create a connection with oracle
			System.out.println(query);
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if (rs.next())
			{
				System.out.println("query result= "+rs.getString(1));
				if (rs.getString(1).equalsIgnoreCase("1"))
				{
					return -1;
				}
				else
				{
					ps = con.prepareStatement("insert into wishlist values(?,?)");
					ps.setInt(1, cid);
					ps.setLong(2, bid);
					cnt = ps.executeUpdate(); // execute insert statement
				}
			}			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
		finally 
		{
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}

	public ArrayList<ArrayList<String>> showWishList(int cid) 
	{
		ArrayList<ArrayList<String>>arr = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
			try {
					con = new connectionDao().createConnection();
					ps = con.prepareStatement("select b.ISBN,b.title,b.author,b.price from book b,wishlist w where b.ISBN=w.ISBN and w.c_id="+cid +" order by title");
					rs = ps.executeQuery();
					if (rs.next())
					{
						arr = new ArrayList<ArrayList<String>>();					
						do
						{
							ArrayList<String> arr2=new ArrayList<String>();
							arr2.add(rs.getString(1));
							arr2.add(rs.getString(2));
							arr2.add(rs.getString(3));
							arr2.add(rs.getString(4));
							arr.add(arr2);
						}
						while (rs.next()) ;
					}
				}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally {
				try {
					ps.close();
					con.close();
				} catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			return arr;
	}
	public int removeWishList(Long bid,int cid)
	{
		Connection con = null;
		PreparedStatement ps = null;
		int cnt = 0;
		try {
			
			con = new connectionDao().createConnection(); //to create a connection with oracle 
			System.out.println("con= " + con);
			
			ps = con.prepareStatement("delete from wishlist where ISBN= "+bid +"and c_id=" + cid);
			
			cnt = ps.executeUpdate(); // execute delete statement
			System.out.println("deleted row:"+cnt);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}
}