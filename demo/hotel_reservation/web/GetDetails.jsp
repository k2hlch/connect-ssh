<%@ page language = "java"  import = "com.oracle.demo.proxy.*" %>
<html>
<head>
<title>Hotel Services Inc.</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table summary="Banner">
  <tr> 
    <td><img src="hotelIntro.gif" alt="Logo" width="150" height="85"></td>
    <td>&nbsp;</td>
  </tr>
</table>
<H1><font face="Arial, Helvetica, sans-serif" size="4">Hotel Search</font></H1>
<!--//get an instance of the proxy ..change this if using another proxy -->
<jsp:usebean  id='stub' scope='session' class='com.oracle.demo.proxy.IHotelProxy' />
<jsp:usebean  id='hd' scope='page' class='com.oracle.demo.proxy.com_oracle_demo_struct_HotelDetails' />
<%
 String hotelName = request.getParameter("hotelname");
 String city = request.getParameter("city");
 String state = request.getParameter("state");
 String checkin = request.getParameter("checkin");
 String checkout = request.getParameter("checkout");
 String occupants = request.getParameter("occupants");
 String occuName = request.getParameter("occupantName");
 boolean toBook = (new Boolean(request.getParameter("toBook"))).booleanValue();
 String type = "";
 String rate = "";
 String ameneties="";
 String address = "";
 String bookingResult = "successfull";
// com_oracle_demo_struct_HotelDetails hd = null;
 if(occuName == null)
  occuName = "";
 if (city == null || state == null || hotelName == null)
  out.println("<B><FONT face=\"arial\" size=\"2\" color=\"red\">no hotel information available to show details of</FONT></B>");
 else {
   try {

       hd = stub.getDetails(hotelName,city,state);
       if(hd==null)
          throw new NullPointerException("room details ="+ hd);
      
  
%>
<form action="GetDetails.jsp" method="get">
<input type="hidden" name="toBook" value="true">
<input type="hidden" name="hotelname" value="<%=hotelName %>">
<input type="hidden" name="city" value="<%=city %>">
<input type="hidden" name="state" value="<%=state %>">
<input type="hidden" name="checkin" value="<%=checkin %>">
<input type="hidden" name="checkout" value="<%=checkout %>">
<input type="hidden" name="occupants" value="<%=occupants %>">


  <table border="0" summary="Hotel Search" >
      <tr> 
      <td > 
        <div align="right"><b><font face="Arial" size="2">HotelName :</font></b></div>
      </td>
      <td  valign="top"> 
        <div align="left"><b><font face="Arial" size="2" color="#9999CC"><%=hotelName%></font></b></div>
      </td>
    </tr>
    <tr>
      <td > 
        <div align="right"><b><font face="Arial" size="2">Address :</font></b></div>
      </td>
      <td  valign="top" > 
        <div align="left"><b><font face="Arial" size="2" color="#9999CC"><%= hd.getAddress() %></font></b></div>
      </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td > 
        &nbsp;
      </td>
      <td valign="top"> 
        <div align="left"><b><font face="Arial, Helvetica, sans-serif" size="2" color="#9999CC"><%=city %></font></b></div>
      </td>
      <td  valign="top"> 
        <div align="left"><b><font face="Arial, Helvetica, sans-serif" size="2" color="#9999CC"><%=state %></font></b></div>
      </td>
      <td > 
        &nbsp;
      </td>
    </tr>
    <tr>
     <td > 
        <div align="right"><b><font face="Arial, Helvetica, sans-serif" size="2">Type :</font></b></div>
      </td>
      <td valign="top" > 
        <div align="left"><b><font face="Arial, Helvetica, sans-serif" size="2" color="#9999CC"><%= hd.getType() %></font></b></div>
      </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
     
    </tr>
    <tr> 
      <td> 
        <div align="right"><b><font face="Arial, Helvetica, sans-serif" size="2">Ameneties :</font></b></div>
      </td>
      <td valign="top"> 
        <div align="left"><b><font face="Arial, Helvetica, sans-serif" size="2" color="#9999CC"><%=hd.getAmeneties() %></font></b></div>
      </td>
      <td > 
        <div align="right"><b><font face="Arial, Helvetica, sans-serif" size="2">Rate :</font></b></div>
      </td>
      <td  valign="top"> 
        <div align="left"><b><font face="Arial, Helvetica, sans-serif" size="2" color="#9999CC"><%=hd.getRate().floatValue() %></font></b></div>
      </td>

     
    </tr>
    <tr>
      <td > 
        <div align="right"><b><font face="Arial" size="2">Checkin Date:</font></b></div>
      </td>
      <td  valign="top"> 
        <div align="left"><b><font face="Arial" size="2" color="#9999CC"><%=checkin %></font></b></div>
      </td>
      <td > 
        <div align="right"><b><font face="Arial" size="2">Checkout Date:</font></b></div>
      </td>
      <td  valign="top"> 
        <div align="left"><b><font face="Arial" size="2" color="#9999CC"><%=checkout %>
        </font></b></div>
      </td>
    </tr> 
    <tr>
      <td > 
        <div align="right"><b><font face="Arial" size="2">Occupants:</font></b></div>
      </td>
      <td  valign="top"> 
        <div align="left"><b><font face="Arial" size="2" color="#9999CC"><%=occupants %>
        </font></b></div>
      </td>
      <td colspan="3">&nbsp;</td>
    </tr> 
    <tr>
      <td colspan="5"> 
        <div align="left"><b><font face="Arial" size="2">Room to be booked for :</font></b></div><br>
          <input type="text" name="occupantName" size="45" value="<%=occuName %>"><br>
      </td>
    </tr> 
    
    
   <%
     }catch (Exception e) {       
         out.println("<B><FONT face=\"arial\" size=\"2\" color=\"#CC3333\">Sorry, Room details not found"+e.getMessage()+"</FONT></b>");
     }
     if (!toBook) 
     {
   %><tr>
      <td  colspan="5"> 
        <input type="Submit" value="Click to Book" name="Book"/>
      </td>
    </tr>
   <%
    }
   %>
      
    
    
  </table>
</form>
<br>
<%
   }//end of if
    
    if (toBook) {
   
        try {
 
         //change this line if using a different proxy
         //IHotelProxy stub = new IHotelProxy();
         bookingResult = stub.reserveHotel(hotelName,city,state,
                            new Integer(occupants),checkin,checkout,occuName);
         
        }
        catch (Exception e) {       
         out.println("<B><FONT face=\"arial\" size=\"2\">Sorry, could not book Try again."+e+"</FONT></B>");
        }
    
         
%>

<TABLE WIDTH="460" BORDER="0" CELLPADDING="2" CELLSPACING="1" SUMMARY="Results">
  <TR VALIGN=MIDDLE BGCOLOR="gray"> 
    <TD><B> <font color="#FFFFFF" face="Arial, Helvetica, sans-serif" size="2"> 
      <%=bookingResult%></font></B></TD>
  </TR>
 <% 
 }
 %>
</table>

<p>&nbsp;</p>
</body>
</html>
