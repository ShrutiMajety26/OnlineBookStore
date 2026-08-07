package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class orderDao 
{
	public Map<Integer,List<String>> getOrders() 
	{
		ArrayList<String> arr = null;
		Map<Integer,List<String>> ordersList = new HashMap<Integer,List<String>>();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		String query = "Select S.O_Id,C.C_Id,C.CNAME,to_char(S.DOI,'mm-dd-yyyy'),(case when S.STATUS='P' then 'Processing'  when S.STATUS='C' then 'Cancelled' when S.STATUS='D' then 'Delivered'end) as status From SHIPPING S, CUSTOMER C where C.C_ID = S.C_ID order by S.status ";
		try
		{
			con = new connectionDao().createConnection();
			System.out.println("===query---"+query);
			//System.out.println("con= " + con);
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			System.out.println("resultaset1 "+rs.getMetaData());
			int count = 1;
			while (rs.next())
			{
				arr = new ArrayList<String>();
				arr.add(rs.getString(1));
				arr.add(rs.getString(2));
				arr.add(rs.getString(3));
				arr.add(rs.getString(4));
				arr.add(rs.getString(5));
				ordersList.put(count,arr);
				count++;
			}
			System.out.println("size= "+ordersList.size());
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
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return ordersList;
	}
	
	public Map<Integer,List<String>> getOrderbyId(String orderId)
	{
		Map<Integer,List<String>> orderInfoMap = new HashMap<Integer,List<String>>();
		List<String> orderInfo = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		String query = "Select S.O_Id,C.C_Id,C.CNAME,to_char (S.DOI,'mm-dd-yyyy'),to_char(S.DOD,'mm-dd-yyyy'),(case when S.STATUS='P' then 'Processing'  when S.STATUS='C' then 'Cancelled' when S.STATUS='D' then 'Delivered'end) as status ,SI.O_QTY,B.TITLE " +
				" From SHIPPING S, CUSTOMER C ,SHIP_ITEM SI,BOOK B where S.O_ID = SI.O_ID AND " +
				"SI.ISBN =B.ISBN AND C.C_ID = S.C_ID AND S.O_ID =?";
		try
		{
			con = new connectionDao().createConnection();
			System.out.println("===query---"+query);
			//System.out.println("con= " + con);
			ps = con.prepareStatement(query);
			ps.setString (1,orderId);
			rs = ps.executeQuery();
			int count = 1;
			while (rs.next())
			{
				orderInfo = new ArrayList<String>();
				orderInfo.add(rs.getString(1));
				orderInfo.add(rs.getString(2));
				orderInfo.add(rs.getString(3));
				orderInfo.add(rs.getString(4));
				orderInfo.add(rs.getString(5));
				orderInfo.add(rs.getString(6));
				orderInfo.add(rs.getString(7));
				orderInfo.add(rs.getString(8));
				orderInfoMap.put(count,orderInfo);
				count++;
			}
			System.out.println("size= "+orderInfoMap.size());
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
		return orderInfoMap;
	}

	public int cancelOrder(String orderId) 
	{
		int cnt=0,cnt1=0,cnt2=0,cnt3=0;
		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		
		String query1="update Shipping set STATUS='C' where O_ID="+orderId;
		String queryNew="select book_qty+O_QTY,isbn from SHIP_ITEM join book using(isbn)where  SHIP_ITEM.O_ID="+orderId;
		
		
		//String query2 = "update BOOK set BOOK.BOOK_QTY=BOOK.BOOK_QTY+(select SHIP_ITEM.O_QTY from SHIP_ITEM where BOOK.ISBN=SHIP_ITEM.ISBN and SHIP_ITEM.O_ID=?) where BOOK.ISBN in (select SHIP_ITEM.ISBN from SHIP_ITEM where Ship_Item.O_Id=?)";
		String query2 = "update BOOK set book_qty=? where isbn=?";
		String query3 = "update SHIP_ITEM set O_QTY=0 WHERE O_ID="+orderId;
		try 
		{
			ArrayList<ArrayList<String>> arr=new ArrayList<ArrayList<String>>();
			con = new connectionDao().createConnection();
			System.out.println("===query---"+query1);
			ps1 = con.prepareStatement(query1);
			cnt1 = ps1.executeUpdate();			
			ps1.close();
			ps1=con.prepareStatement(queryNew);
			ResultSet rs=ps1.executeQuery();
			while (rs.next())
			{
				ArrayList<String> arr2=new ArrayList<String>();
				arr2.add(Integer.parseInt(rs.getString(1))>99 ?"99":rs.getString(1)); //qty added
				arr2.add(rs.getString(2)); //isbn
				arr.add(arr2);
			}
			System.out.println("arr== "+arr);
			ps2 = con.prepareStatement(query2);
			for (int i=0;i<arr.size();i++)
			{
				ArrayList<String> arr3=arr.get(i);
				ps2.setString(1,arr3.get(0));
				ps2.setString(2,arr3.get(1));				
			}			
			
			cnt2 = ps2.executeUpdate();
			ps3 = con.prepareStatement(query3);
			cnt3 = ps3.executeUpdate();
			System.out.println("finished updating book");
			cnt=cnt1+cnt2+cnt3;
			System.out.println("deleted.... cnt= "+cnt);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				ps1.close();
				ps2.close();
				ps3.close();
				con.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return cnt;
	}	
}