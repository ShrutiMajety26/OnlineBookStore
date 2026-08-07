package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class shippingDao 
{
	public ArrayList<ArrayList<String>> viewOrder(int cid) 
	{
		ArrayList<ArrayList<String>>arr = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
			try {
					con = new connectionDao().createConnection();
					//System.out.println("con= " + con);
					ps = con.prepareStatement("Select sp.o_id,bk.title,bk.price,sh.o_qty,to_char(sp.doi,'mm-dd-yyyy'),to_char(sp.dod,'mm-dd-yyyy'),bk.price*sh.o_qty as total_Price,(case when sp.status='C' then 'Cancelled' when sp.status='D' then 'Delivered' when sp.status='P' then 'Processing' end) as status from ship_item sh,shipping sp,book bk where sp.o_id = sh.o_id and sh.ISBN=bk.ISBN and sp.c_id="+cid +" order by o_id ");
					rs = ps.executeQuery();
					
					arr = new ArrayList<ArrayList<String>>();
					
					while (rs.next()) 
					{	
						ArrayList<String> arr2=new ArrayList<String>();
						arr2.add(rs.getString(1));
						arr2.add(rs.getString(2));
						arr2.add(rs.getString(3));
						arr2.add(rs.getString(4));
						arr2.add(rs.getString(5));
						arr2.add(rs.getString(6));
						arr2.add(rs.getString(7));
						arr2.add(rs.getString(8));
						arr.add(arr2);						
					}
					//System.out.println(arr);
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
}