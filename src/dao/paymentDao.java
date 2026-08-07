package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class paymentDao {

	public ArrayList<String> fillPayment(int cid) 
	{
		ArrayList<String> arr = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;
		String query = "select address,email,phone from customer where 1=1 ";
		try {
			if(cid!=0)
				query = query + " and c_id=" + cid;			
			con = new connectionDao().createConnection();
			System.out.println("===query---"+query);
			System.out.println("con= " + con);
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				arr = new ArrayList<String>();
				arr.add(rs.getString(1));
				arr.add(rs.getString(2));
				arr.add(rs.getString(3));
			}
			System.out.println(arr);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return arr;
	}

	public int submitOrder(int cid,String address,float totalPrice)
	{
		Connection con=null;
		PreparedStatement ps=null;
		int cnt=0;
		try
		{	System.out.println("in submit order");
			con=new connectionDao().createConnection();	
			//System.out.println("uname "+uname+ " phnNo= "+phnNo+" email= "+email+" gender= "+gender+" address= "+address+" password= "+password);
			ps=con.prepareStatement("insert into SHIPPING values(SHIP_SEQ.nextval,?,SYSDATE,SYSDATE+3,?,?,'P')");		    
		    ps.setInt(1,cid); // set input parameter 1
		    ps.setFloat(2,totalPrice); // set input parameter 2
		    ps.setString(3,address); // set input parameter 4
			      
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
	}//insert method for shipping
	
	public int getOid(){
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		int oid=0;
		//int cnt;
		try
		{	System.out.println("in getoid");
			con=new connectionDao().createConnection();	
			ps=con.prepareStatement("select O_ID from(select O_ID from Shipping order by DOI desc) where ROWNUM=1");		    
			rs = ps.executeQuery(); // execute select statement	
			while(rs.next())
			oid=rs.getInt(1);
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
		
		return oid;
	}
	
	public int addShipItem(int oid,int cid)
	{
		Connection con=null;
		PreparedStatement ps1=null;
		PreparedStatement ps2=null;
		PreparedStatement ps3=null;
		int cnt1=0;
		int cnt2=0;
		int cnt3=0;
		int cnt4=0;
		String query1 = "insert into Ship_Item (select c.ISBN,?,c.c_QTY from Cart c where c.C_ID=?)";
		String query2 = "DELETE FROM CART WHERE C_ID="+cid;
		String query3="update BOOK set BOOK.BOOK_QTY=BOOK.BOOK_QTY-(select SHIP_ITEM.O_QTY from SHIP_ITEM where BOOK.ISBN=SHIP_ITEM.ISBN and SHIP_ITEM.O_ID=?) where BOOK.ISBN in (select SHIP_ITEM.ISBN from SHIP_ITEM where Ship_Item.O_Id=?)";
		//delete from Cart where C_ID=1001;
		try
		{	System.out.println("in submit order =1");
			con=new connectionDao().createConnection();			    
		    
		    //ps.setString(3,); // set input parameter 4
			      
		    // execute insert statement	
		   
		    ps1 = con.prepareStatement(query1);
		    ps1.setInt(1,oid); // set input parameter 1
		    ps1.setInt(2,cid); // set input parameter 2
			cnt1 = ps1.executeUpdate();
			System.out.println("Finished executing insert into ship_item");
			ps2 = con.prepareStatement(query2);
			cnt2 = ps2.executeUpdate();
			System.out.println("Finished executing delete in the CART");
			ps3 = con.prepareStatement(query3);
			ps3.setInt(1,oid);
			ps3.setInt(2,oid);
			cnt4=ps3.executeUpdate();
			System.out.println("Finished updating quantity in book");
			cnt3=cnt1+cnt2+cnt4;
			
			System.out.println("cnt1+cnt2+cnt4= "+cnt3);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				ps1.close();
				ps2.close();
				con.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}			
		}
		return cnt3;
	}
	public ArrayList<ArrayList<String>> getbookDataAfterPayment (int oid,int cid) throws Exception, SQLException
	{
		ArrayList<ArrayList<String>> arr=null;
		ArrayList<String> arr2=null;
		Connection con = null;
		Statement stmt=null;
		ResultSet rset =null;
		String query=null;
		try
		{
			con = new connectionDao().createConnection();
			stmt = con.createStatement();
			
				query="Select O_Id,Title,O_Qty,Price*O_Qty As Price,Total_Price From Book Join Ship_Item Using (Isbn) Join  Shipping Using (O_Id) " +
						"Where  o_id="+oid +" and c_id="+cid;
						
			System.out.println(query);
			rset= stmt.executeQuery(query);					
			arr=new ArrayList<ArrayList<String>>();
			while (rset.next())
			{			
				//System.out.println("adding to arr "+rset.getString(1));
				arr2=new ArrayList<String>();
				arr2.add(rset.getString(1));
				arr2.add(rset.getString(2));	
				arr2.add(rset.getString(3));	
				arr2.add(rset.getString(4));
				arr2.add(rset.getString(5));			
				arr.add(arr2);
			}			
			//System.out.println(" arr size= "+arr.size()+" arr in doa= "+arr);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			rset.close();
			stmt.close();
			con.close();
		}
		return arr;
	}
}