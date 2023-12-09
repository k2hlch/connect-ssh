<%@ page language = "java"  import = "com.oracle.demo.proxy.*" %>
<html>
<head>
<title>Admin Services Inc.</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table summary="Banner">
  <tr> 
    <td><img src="hotelIntro.gif" alt="Logo" width="120" height="83"></td>
    <td>&nbsp;</td>
  </tr>
</table>
<H1><font face="Arial, Helvetica, sans-serif" size="4">Hotel Admin</font></H1>
<!--//get an instance of the proxy ..change this if using another proxy-->
<jsp:usebean  id='adminstub' scope='session' class='com.oracle.demo.proxy.IHotelAdminProxy' />
<jsp:usebean  id='hs' scope='page' class='com.oracle.demo.proxy.com_oracle_demo_struct_HotelStatus' />

<A href="RoomStatus.jsp">check room status</A><br>
<A href="HotelAdmin.jsp?viewHotel_main=true">look at hotel_main table</A><br>
<A href="HotelAdmin.jsp?viewHotel_room=true">look at hotel_room table</A><br>
<A href="HotelAdd.jsp">add a new hotel</A>

<%
 boolean getStatus = (new Boolean(request.getParameter("getStatus"))).booleanValue(); 
 String hotelname = request.getParameter("hotelName");
 String city = request.getParameter("city");
 String state = request.getParameter("state");
 //get an instance of the proxy 
// IHotelAdminProxy stub = new IHotelAdminProxy();

if (hotelname == null)
   hotelname = "";
if (city  == null)
   city  = "";
if (state == null)
  state = "";
 
%>
<form action="RoomStatus.jsp" method="get">
<input type="hidden" name="getStatus" value="true">
  <table border="0" summary="Hotel Search" >
     <tr> 
      <td > 
        <div align="right"><b><font face="Arial" size="2">Hotel Name :</font></b></div>
      </td>
      <td  valign="top">       
         <input type="text" name="hotelName" size="14" value="<%=hotelname %>">
      </td>
    </tr>
    <tr>
      <td > 
        <div align="right"><b><font face="Arial" size="2">city :</font></b></div>
      </td>
      <td  valign="top" > 
        <input type="text" name="city" size="14" value="<%=city %>">
      </td>
      <td > 
        <div align="right"><b><font face="Arial" size="2">state :</font></b></div>
      </td>
      <td  valign="top" > 
        <input type="text" name="state" size="3" value="<%=state %>">
      </td>
    </tr>
    <tr>
      <td width="132" colspan="5"> 
        <input type="Submit" value="View Status" name="Search"/>
      </td>
     </tr>    
  </table>
</form>
<br>
<%
       if (getStatus) {
          //Change this line to use any other proxy 
          com_oracle_demo_struct_HotelStatus[] statusArray = null;       
   
          try {
                
          statusArray = adminstub.getHotelStatus(hotelname,city,state);
         
           }
          catch (Exception e) {       
               out.println("<B><FONT face=\"arial\" size=\"2\" color=\"red\">Sorry, could not get the status"+e.getMessage()+"</FONT></B>");
           }
    
         
%>

<TABLE  BORDER="0" CELLPADDING="2" CELLSPACING="1" SUMMARY="Results">
  <TR VALIGN=MIDDLE BGCOLOR="#003366"> 
    <TD colspan="5"><B> <font color="#FFFFFF" face="Arial, Helvetica, sans-serif" size="2"> 
      Hotel Status :</font></B></TD>
  </TR>
  <TR VALIGN=MIDDLE BGCOLOR="#000066"> 
     <TD>&nbsp;</td>
    <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
     Room Type</font></B>
    </TD>
    <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
      Booked By</font></B></TD>
          <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
      Checkin</font></B></TD>
          <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
      Check Out</font></B></TD>
       
  </TR>
  <%       
       for (int i=0;i<statusArray.length;i++) {
               if (!(statusArray[i].equals("")) && statusArray[i] != null) {
                    hs = statusArray[i];

  %>
  <TR VALIGN="top"> 
       <TD><B><font  face="Arial, Helvetica, sans-serif" size="2"> <%=(i+1)%>
        </font></B>
       </TD>
       <TD><B> 
        <font  face="Arial, Helvetica, sans-serif" size="2"> 
        <%=hs.getRoomType()%></font></B></TD>
          <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
        <%=hs.getBookedBy()%></font></B></TD>
          <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=hs.getCheckin()%></font></B></TD>
          <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=hs.getCheckout()%></font></B></TD>
  </TR>
  <% 
     } 
    }
 if(statusArray.length ==0)
        out.println("<B><font  face=\"Arial\" size=\"2\">No bookings for this hotel</font></B>");
             
  }
  
  %>

  
</table>
<p>&nbsp;</p>
</body>
</html>

