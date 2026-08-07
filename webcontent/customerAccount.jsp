<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css" /> 
	<link href="css/paginationStyle.css" rel="stylesheet" type="text/css" />
	<link href="css/style.css" rel="stylesheet" type="text/css" />	 
	<style>
		.not-active { 	  	
   		cursor:not-allowed;
   		pointer-events:none;
   		text-decoration: line-through;
		}

	</style>
	<%
	if(request.getSession(false) ==null){
		out.println("Session does not exists!"); }%>	 
</head>
<body>
	<div id="page_header">
		<div id="page_title"><h1><img src="images/bookLogo2.png" height="58px" width=200px /></h1></div>
		<div id="header_search" style="background-image:none;">
			<form method="post" action="">				
				<div><h3>Welcome <%out.println("<font color=green>"+(session.getAttribute("userName")==null?("guest"):(session.getAttribute("userName").toString().toUpperCase()))+"</font>"); %> </h3>
					<% if(session.getAttribute("userName")!=null){ %>
						<a href="loginServlet?loginFlag=logOut" style="font-size:12px" >Logout</a><%} %>				
				</div>				
			</form>
			<div style="float:right;margin-top:-18px">
				<a href="cartServlet?pageFlag=display"><img src="images/Shopping_Cart.png"  title="Your Cart" style="margin-left:-100px;"></img></a>
			</div>			
		</div>
	</div>
	<div id="page_menu">
		<ul id="menu">
			<li><a href="customerAccount.jsp?EditPageFlag=notnull" style="text-decoration:none;font-size:13px;text-align:center;color:white;">Home</a></li>
			<li><a href="customerAccount.jsp" style="text-decoration:none;font-size:13px;text-align:center;color:white;">Bestsellers</a></li>
			<li><a href="customerAccount.jsp?bookFlag=newRelease" style="text-decoration:none;font-size:13px;text-align:center;color:white;">New Releases</a></li>
			<li><a href="customerAccount.jsp?EditPageFlag=notnull" style="text-decoration:none;font-size:13px;text-align:center;color:white;"><%=session.getAttribute("userName")!=null?session.getAttribute("userName"):""  %> Account</a></li>			 
			<li><a href="customerAccount.jsp?bookFlag=contactUs" style="text-decoration:none;font-size:13px;text-align:center;color:white;">Contact us</a></li>			
		</ul>
	</div>
	<div id="page_wrapper"><!-- BEGIN :: LEFT SIDEBAR -->
		<div id="page_leftcol">
			<div class="borderbox">
				<div class="leftcol_box">
					<div id="books_search">
						<h2><span>Books Search</span></h2>
						<div class="content">
							<form method="post" action="searchServlet?searchPageFlag=true">
								<div> 
									<input  type="search"  name="name" id="names" />
									<select name="what" id="what">
									<option value="">--Select--</option>
									<option value="title">Title</option>
									<option value="author">Author</option>
									<option value="Genre">Genre</option>
									<option value="publisher">Publisher</option>
									<option value="publishing_year">Publishing Year</option>
								</select>
									<input src="images/booksearch_button.gif" class="submit" type="image" /> 
								</div>
							</form>
							<div class="footnote"> <a href="#">Advanced Search</a> </div>
						</div>
					</div>
				</div>
			</div>
			<div class="borderbox">
				<div class="leftcol_box">
					<div id="catalog">
						<h2><span>Customer Account</span></h2>
							<div class="content">
								<ul style="background:none;height:157px;">
									<li style="margin-bottom:8px; "><a href="customerServlet?pageFlag=edit" style="color:black;text-decoration:underline;">View/Edit profile</a></li>									
									<li style="margin-bottom:8px; "><a href="shippingServlet?pageFlag=display" style="color:black;text-decoration: underline;">Your Orders</a></li>									
									<li style="margin-bottom:8px; "><a href="wishListServlet?pageFlag=display" style="color:black;text-decoration: underline;">Your WishList</a></li>
								</ul>			
							</div>
					</div>
				</div>
			</div>
			<div id="knowmore">
				<h2><span style="display: none;">Know More</span></h2>
					<p>All books are divisible into two classes, the books of the <span class="image">&nbsp;</span>hour, and the books of all time. </p>
<div class="readmore"><a href="#">-John Ruskin</a></div>
			</div>
			<div class="borderbox">
				<div class="leftcol_box">
					<div id="newsletter">
						<h2><span>Newsletter Signup</span></h2>
						<div class="content">
							<form method="post" action="#">
									<div> 
										<input value="Enter Email Here" type="text" />
										<input src="images/newsletter_button.gif" class="submit" type="image" />
										<div class="clearthis">.</div>
									</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	<!-- //END :: LEFT SIDEBAR --><!-- BEGIN :: MAIN COL -->
	
	<div id="page_maincol">
		<div id="maincol_top">
			<div class="sideimg">&nbsp;</div>
			<div class="content">
				<h2>Welcome to 671 Books</h2>
				<p>This is a place for book lovers with a passion for books and a great eye for good books.
					A world of good books for young and old, inspiration for every age. For the discerning reader enrich your life with books 
					from 671Books with new books arriving daily, which are always worth a browse. </p>
					<div class="readmore"><a href="#">Thankyou for visiting!</a></div>
			</div>
		</div>
		<div class="borderbox">
			<div class="maincol_box">
					<div id="bestsellers" >
				<%			
			if(request.getAttribute("EditPageFlag")==null && request.getAttribute("customerArray")!=null)
			{ %>			
						<h2 style="background-image:none; ;color:white;text-align:center;float:left;font-family:tahoma,sans-serif;font-size:11px"><span style="display: block;margin-top:6px;">View/Edit Customer Profile</span></h2>
						<div class="content" style="height:300px;">						
							<div class="wrapper" >		<% request.setAttribute("customerArray", request.getAttribute("customerArray")); %>
								<jsp:include page="editCustomer.jsp" />
							</div>
						</div>					
				<%
			}
			else if (request.getAttribute("searchList")==null && request.getParameter("bookFlag")!=null && request.getParameter("bookFlag").equals("contactUs"))
				{%>
					<h2 style="height:18px;background-image:none;font-size:12px;color:white;text-align: center;padding-top:5px;padding-bottom:5px;"><span>Contact Us</span></h2><br/>
					<jsp:include page="/contactUs.jsp" flush="false">
						<jsp:param value="newRelease" name="pageFlag" />
					</jsp:include><%
				}	
			else if (request.getParameter("orderFlag")!=null && request.getParameter("orderFlag").equalsIgnoreCase("yes"))
			{System.out.println("order flag1= "+request.getParameter("orderFlag"));%>
			<jsp:include page="viewShip.jsp" />
			<% }			
			else if (request.getAttribute("searchList")==null && request.getParameter("bookFlag")!=null && request.getParameter("bookFlag").equals("newRelease"))
			{%>
				<h2 style="height:18px;background-image:none;font-size:12px;color:white;text-align: center;padding-top:5px;padding-bottom:5px;"><span>New Releases</span></h2><br/>
				<jsp:include page="/searchServlet" flush="true">
					<jsp:param value="newRelease" name="pageFlag" />
				</jsp:include><%
			}
			else if (request.getParameter("wishFlag")!=null && request.getParameter("wishFlag").equalsIgnoreCase("yes"))
			{System.out.println("wish flag1= "+request.getParameter("wishFlag"));%>
			<jsp:include page="wishList.jsp" />
			<% } 
			else if (request.getAttribute("searchList")==null)
			{%>	
				<h2 style="height:18px;background-image:none;font-size:12px;color:white;text-align: center;padding-top:5px;padding-bottom:5px;"><span><%out.println("Top Bestsellers");%></span></h2><br/>							
				<jsp:include page="/searchServlet" flush="true">
					<jsp:param value="bestSellers" name="pageFlag" />
				</jsp:include><%
			}
			 
			
			else
			{%>		
						<div class="content" style="width:360px;height:445px;border:12px;" id="mainDiv">
								<h2 style="height:18px;background-image:none;font-size:12px;color:white;text-align: center;padding-top:5px;padding-bottom:5px;"><span>Search Results</span></h2><br/><%
							}
							%>	
								<h3 style="font-size:12px;" align="center"><%=request.getParameter("name")==null?"":("Search Results for - <font style ='color:red'>"+(request.getParameter("name").toString().length()==0?"All":request.getParameter("name"))+"</font>") %></h3>
								<h3 style="font-size:12px;" align="center"><%=request.getParameter("what")==(null)?"": request.getParameter("what").toString().length()==0?"": ("on - <font style ='color:red'>"+request.getParameter("what")+"</font>") %></h3><%
								ArrayList<ArrayList<String>> arr=null;
								int j=1;
								boolean divFlag=false;
								arr = ((ArrayList<ArrayList<String>>)request.getAttribute("searchList"));
								if (arr!=null && arr.size()>0)
								{%> 
									<div id="paginationdemo"><%																	
									for (int i=0; i< arr.size();i++)
									{
										divFlag=false;
										if (i%2==0)
										{ %>
											<div  class="<%=(j==1?"pagedemo _current":"pagedemo")%>" style="<%=(j==1?"":"display:none;")%>" id="p<%=j++%>"><%
										}%>						
										<table style="padding:10px;"><%
											ArrayList<String> arr2=arr.get(i);	%>
											<tr>
												<td style="border:0px;"  valign="middle"><img src="searchServlet?bookId=<%=arr2.get(0)%>" height=99 width=65 style="margin-top:10px "></td>
												<td >
													<table style="font-size:12px;color:5C5E5F;font-weight: bold;border:0px;width:280px;">
														<tr><td height="30%" style="color:#7EB610 ;text-decoration: underline;" colspan="2"><b><%out.println(arr2.get(1)); %></b></td></tr>
														<tr><td style="border:0px;" colspan="2">ISBN: <%out.println(arr2.get(0)); %></td></tr>
														<tr><td class="price" style="float:left;">Price: $ <%out.println(arr2.get(2)); %></td></tr>
														<tr><td colspan="2">Author: <%out.println(arr2.get(3)); %></td></tr>
														<tr><td colspan="2">Publisher: <%out.println(arr2.get(4)); %></td></tr>
														<tr>
															<td colspan="2" align="center" background="green"> <div class="buynow"> <a href="cartServlet?bid=<%=arr2.get(0)%>&pageFlag=add" class=<%=Integer.parseInt(arr2.get(6))<=0?"not-active":"" %>>Buy Now</a></div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
															<a href="wishListServlet?bid=<%=arr2.get(0)%>&pageFlag=add" style="color:orange;" class=<%=Integer.parseInt(arr2.get(6))<=0?"not-active":"" %> >Add to Wishlist</a></td>
														</tr>
													</table>								
												</td>
											</tr>
										</table><%
										if (i%2!=0)
										{
											divFlag=true;%>
											</div><%
										}
									} 
									if (divFlag==false || arr.size()==1)
										{out.println("</div>");}									
									System.out.println("arr.size()= "+arr.size()+" java.lang.Math.floor(arr.size()/2) = "+(arr.size()/2));
									%>
								</div>	<br/>
								<div id="demo3" style="margin-left:80px; "></div>											
								<script src="js/jquery-1.3.2.js"></script>
								<script src="js/jquery.paginate.js"></script>
								<script type="text/javascript">
									$("#demo3").paginate({
										count 		: <%=arr.size()%>,
										start 		: <%=arr.size()==0?0:1%>,
										display     : <%=arr.size()==0?0:(arr.size()/2)==0?1:((arr.size()+1)/2)%>,
										border					: true,
										border_color			: '#7EB610',
										text_color  			: '#E4881D',
										background_color    	: '#E3F2E1',
										border_hover_color		: '#68BA64',
										text_hover_color  		: 'black',
										background_hover_color	: '#CAE6C6', 
										rotate      : false,
										images		: false,
										mouse		: 'press',
										onChange     			: function(page){
																$('._current','#paginationdemo').removeClass('_current').hide();
																$('#p'+page).addClass('_current').show(); }
									});				         	
								</script><%}
							if (request.getAttribute("searchList")!=null && arr.size()==0 )
							{%>
									<br/><br/><h3 style="font-size:20px;color:red;" align="center">No Results found!!</h3>
							<%}	%>
						</div>
					</div>
				</div>
				<div class="clearthis">.</div>		
		</div>
	</div>
	</div>
<!-- //END :: MAIN COL --><!-- BEGIN :: RIGHT SIDEBAR -->
	<div id="page_rightcol">
			<div class="borderbox">
				<div class="rightcol_box">
					<div id="latest">
						<h2><span style="display: none;">Latest Releases &amp; News</span></h2>
						<div class="wrapper">
							<h3>June 22nd, 2005</h3>							
							<p style="font-weight: bold;">Profits Rose 14% at B&N in Third Quarter </p>
							<p>A large decline in the net loss in its Nook division helped net income increased to 14% at Barnes & Noble in the third quarter ended January 31, 2015 over the comparable period in fiscal 2014. </p>
							<h3>March 12th, 2015</h3>
							<p style="font-weight: bold;">ALAN TURING: THE ENIGMA </p>
							<p>by Andrew Hodges. (Princeton University.) The presiding mathematician and decoding force at Bletchley Park, the center that cracked the German Enigma code; the inspiration for the film "The Imitation Game." </p>
							<h3>March 15th, 2015</h3>
							<p style="font-weight: bold;">Friction:The Girl on the Train by Paula Hawkins </p>
							<p>Rachel takes the same commuter train every morning. Every day she rattles down the track, flashes past a stretch of cozy suburban homes, 
							and stops at the signal that allows her to daily watch the same couple breakfasting on their deck. </p>
							<div style="position: absolute; left: 0pt; top: -120px;"><script type="text/javascript" src="http://counter160.com/visits.php"></script><a href="http://www.000webhost.com/affiliate-program"><img src="http://www.000webhost.com/images/icons/affiliate.gif" alt="best affiliate programs" /></a></div>
							<p>She's even started to feel like she knows them. Their life as she sees it is perfect.
							Not unlike the life she recently lost.And then she sees something shocking.</p>
							<div class="readmore"><a href="#">Read More</a></div>
						</div>
					</div>
				</div>
			</div>
			<div class="clearthis">.</div>
		</div>
<!-- //END :: RIGHT SIDEBAR --></div>
<div id="page_spacing">
<div id="page_footer">
<div id="rights">© Copyright 671 Books </div>
<div id="links"><span><a href="http://www.freewebsitetemplates.com">Home</a></span>
<span><a href="home.jsp">Bestsellers</a></span>
<span><a href="#">New Releases</a></span>
<span><a href="#">Contact</a></span>
</div>
</div>
</div>

<div class="clearthis">.</div>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
		<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
		<script type="text/javascript">
			$(function() {
			$("#names").autocomplete({
				source: function(request, response) {
				$.ajax({
				url: "searchName.jsp",
			    type: "POST",
				dataType: "json",
				data: {	name: request.term,
						what:  $("#what").val()	},
				success: function( data ) 
				{		
					response( $.map( data, function( item ) 
					{
					return {
						label: item.name,
						value: item.value
					};
					}));
				},
				error: function (xhr, status, error) 
				{
			       alert('error= '+xhr.responseText+" staus= "+status);
			    }
				});
				},
				minLength:1,
				select: function (event, ui)
				{
					document.getElementById('what').value="title"; 
				}
				});
			});
		</script>
</body></html>