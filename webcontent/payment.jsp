<!DOCTYPE html>
<html>
<head>
<title>671 BOOKS | Secure Payment Form</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link href="css/BP-SHPF2.css" rel="stylesheet" type="text/css"/>
<style>
	#page_title {
	margin-left : 3px;
	margin-top : 13px;
	width : 180px;
	height : 44px;
	float : left;
	
}

#page_title img {
	position : absolute;
	top : -3px;
	margin-left :5px;
}
</style>

	<script type="text/javascript">
		/* function setAmount() {
			document.forms.mainform.AMOUNT.value=document.forms.mainform.AMOUNT.value.replace(/[^\d\.\-\ ]/g, '');
		} */
		
		function changeFlag(buttonName,event)
		{
			//document.getElementById("makePay").disabled=true;
			
			/* if(document.getElementById("amount").value==null || document.getElementById("amount").value == ''){
				alert('Amount not entered');
				return false;
			}
			if(document.getElementById("cardnum").value==null || document.getElementById("cardnum").value == ''){
				alert('Please enter Card Number');
				return false;
			}
			if(document.getElementById("cvv").value==null || document.getElementById("cvv").value == ''){
				alert('Please enter a 3 digit CVV number');
				return false;
			}
			if(document.getElementById("CC_EXPIRES_MONTH").value==null || document.getElementById("pageFlag").value == '')
			{
				alert('Please select a month');
				return false;
			}
			if(document.getElementById("CC_EXPIRES_YEAR").value==null || document.getElementById("pageFlag").value == ''){
				alert('Please select a year');
				return false;
			}
			if(document.getElementById("name").value==null || document.getElementById("pageFlag").value == ''){
				alert('Enter your name on card');
				return false;
			} */
			//event.preventDefault();
			document.getElementById("pageFlag").value=buttonName;
			document.getElementById("payDiv").style.display='block';
			document.getElementById("makePay").style.display='none';
			document.getElementById("back").style.display='none';
			
			document.mainform.action="paymentServlet";
			document.mainform.method="POST";
			document.mainform.submit(); 
			//document.getElementById("mainform").submit();
			return true;
		}
		</script> 
		
</head>
<body>
<div id="wrap-border">
<div id="wrap">

    <div id="header">
     
        <div >
			<div id="page_title"><h1><img src="images/bookLogo2.png" height="58px" width=200px /></h1></div>  
			<div style="clear: both;"></div>				
        </div>
    </div>
	<h4 align="center"><font color="red"><%out.println(request.getAttribute("flag")==null?"":request.getAttribute("flag")); %></font></h4>
    <div id="form-table" style="margin-top:-170px; ">
	
     <!-- <form id="mainform" class="mainform" action="./paymentServlet" method="post" > -->
     <form id="mainform" name="mainform" class="mainform"  onsubmit="return changeFlag('makepayment');" >
     <table id="payment_info">
              
          <tbody>
            <tr>
              <td colspan="3"></td>
            </tr>
			     
			<tr>
              <td style="vertical-align:middle;"><b><u>Payment Information:</u></b></td>			  
              <td colspan="3" style="vertical-align:middle;"><img src="${SHPF_STATIC_PATH}images/visa.gif" alt="visa"/><img src="${SHPF_STATIC_PATH}images/mc.gif" alt="mastercard"/><img src="${SHPF_STATIC_PATH}images/discvr.gif" alt="discover"/><img src="${SHPF_STATIC_PATH}images/amex.gif" alt="amex"/></td>			  
			</tr>
                         
              <tr>              
              <td>Amount ($):</td>
			  <td><input type="number" class="form" name="amount" id="amount" autocomplete="off" readonly="readonly" value="${totalAmount}" required="required"  /></td>    
              </tr>
			
             <tr>
			  <td>Card Number:<font color="red">*</font></td>
			  <td><input type="text"  class="form"  name="cardnum" title="Enter 16 digit Card Number" placeholder="Enter 16 dight Card No." id="cardnum" autocomplete="off" maxlength="16" required="required"/></td>    
            </tr>        
			  
			 <tr>
             	<!--<td>CVV2: <a href="${SHPF_STATIC_PATH}images/cvv.jpg" onClick="return popup(this, 'notes')">What's this?</A></td>-->
			  <td>CVV :<font color="red">*</font> </td>
			  <td><input type="text"  class="form" name="cvv" id="cvv" maxlength="3" autocomplete="off"  required="required"/></td>
            </tr>
			 <tr>
			  <td>Expiration Date:<font color="red">*</font> </td>
			  <td><select name="CC_EXPIRES_MONTH" id="CC_EXPIRES_MONTH" required="required"  >
				<option value="" SELECTED>--Month--</option>
				<option value="01">01</option>
				<option value="02">02</option>
				<option value="03">03</option>
				<option value="04">04</option>
				<option value="05">05</option>
				<option value="06">06</option>
				<option value="07">07</option>
				<option value="08">08</option>
				<option value="09">09</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				</select> /
				<select name="CC_EXPIRES_YEAR" id="CC_EXPIRES_YEAR">
				<option value="" SELECTED>--Year--</option>
				<option value="15">2015</option>
				<option value="16">2016</option>
				<option value="17">2017</option>
				<option value="18">2018</option>
				<option value="19">2019</option>
				<option value="20">2020</option>
				<option value="19">2021</option>
				<option value="20">2022</option>
				</select>
				</td>
			 </tr>
            <tr>
             <td colspan="4" style="height: 50px; vertical-align:bottom;"><b><u>Billing Information:</u></b></td>
            </tr>
            
            <tr>
              <td>Name On Card:<font color="red">*</font></td>
	      <td><input type="text" class="form"  name="name" id="name" autocomplete="off" required="required" /></td>  
            </tr>
            <c:forEach var="customer" items="customerInfo" >	
            <tr>
              <td>Billing Address:<font color="red">*</font></td>   
	      <td><textarea rowspan="2" class="form"  name="address" id="address" required >${customerInfo[5]}</textarea></td>
            </tr>
             <!-- <tr>If you wish to ship some other address please enter Address</tr> -->
          <tr>
               <td>Email Address:</td>
		<td><input type="email" name="email" class="form" id="email" autocomplete="off" readonly="readonly" value="${customerInfo[4]}"/></td>
	  </tr>
	  <tr>
		<td>Phone: </td>
		<td colspan="3"><input type="tel" class="form"  name="phone" id="phone" maxlength="10" autocomplete="off" readonly="readonly"  value="${customerInfo[2]}"/></td>
		
      </tr>
            </c:forEach>
            <tr>
            <td colspan="4"><br></td>
            </tr>
            
            <tr>
            <td> <div id="payDiv" style="display: none;"><font color="blue">Please wait...&nbsp;Payment is being Processed</font><br/><font color="red">Do Not Refresh the window!!</font></div>
            <td colspan="3" align="center"><input type="submit" value="Make Payment" class="button"  id="makePay" />
            <input type="button" value="Back" class="button"  id="back" onclick="window.location.href='cartServlet?pageFlag=display'" />
            <input type="hidden" name="pageFlag" id="pageFlag" required="required" value=""/></td> 
            </tr>
	  		
        </tbody>
      </table>             
    </form>  
    </div>  
       
    <div id="footer">       
    </div>
        
</div></div>
</body>
</html>