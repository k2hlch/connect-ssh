<%@ page language = "java" import = "com.oracle.demo.proxy.*"  %>
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
<!-- //get an instance of the proxy ..change this if using another proxy -->
<jsp:usebean  id='adminstub' scope='session' class='com.oracle.demo.proxy.IHotelAdminProxy' />
<A href="RoomStatus.jsp">check room status</A><br>
<A href="HotelAdmin.jsp?viewHotel_main=true">look at hotel_main table</A><br>
<A href="HotelAdmin.jsp?viewHotel_room=true">look at hotel_room table</A><br>
<A href="HotelAdd.jsp">add a new hotel</A>
<%

 String hotelname = request.getParameter("hotelname");
 String city = request.getParameter("city");
 String state = request.getParameter("state");
 String type = request.getParameter("type");
 String rate = request.getParameter("rate");
 String ameneties = request.getParameter("ameneties");
 String address = request.getParameter("address");

 if (hotelname == null)
   hotelname = "";
 if (city == null)
   city = "";
 if (state == null)
  state = "";
 if (type == null)
   type = "";
 if (rate == null)
   rate = "";
 if (ameneties == null)
  ameneties = "";
 if (address == null)
  address= "";

%>
<form action="HotelAdd.jsp" method="get">



  <table border="0" summary="Hotel Search" >
      <tr> 
      <td > 
        <div align="right"><b><font face="Arial" size="2">HotelName :</font></b></div>
      </td>
      <td  valign="top"> 
        <input type="text" name="hotelname" size="14" value="<%=hotelname%>"/>
      </td>
    </tr>
    <tr>
      <td > 
        <div align="right"><b><font face="Arial" size="2">Address :</font></b></div>
      </td>
      <td  valign="top" > 
        <input type="text" name="address" size="14" value="<%= address%>"/>
      </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td > 
        <div align="right"><b><font face="Arial, Helvetica, sans-serif" size="2">City :</font></b></div>
    
      </td>
      <td valign="top"> 
            <input type="text" name="city" size="14" value="<%= city%>"/>
      </td>
      <td  valign="top"> 
        <div align="left"><b><font face="Arial, Helvetica, sans-serif" size="2">State :</font></b></div>
      </td>
      <td > 
      <input type="text" name="state" size="14" value="<%=state %>"/>
      </td>
    </tr>
    <tr>
     <td > 
        <div align="right"><b><font face="Arial, Helvetica, sans-serif" size="2">Type :</font></b></div>
      </td>
      <td valign="top" > 
        <input type="text" name="type" size="14" value="<%= type%>"/>
      </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>     
    </tr>
    <tr> 
      <td> 
        <div align="right"><b><font face="Arial, Helvetica, sans-serif" size="2">Ameneties :</font></b></div>
      </td>
      <td valign="top"> 
         <input type="text" name="ameneties" size="14" value="<%=ameneties%>"/>
      </td>
      <td > 
        <div align="right"><b><font face="Arial, Helvetica, sans-serif" size="2">Rate :</font></b></div>
      </td>
      <td  valign="top"> 
        <input type="text" name="rate" size="14" value="<%=rate%>"/>
      </td>
    </tr>
    <tr>
      <td width="132" colspan="5"> 
        <input type="Submit" value="Click to Add" name="Add"/>
      </td>      
    </tr>
    
  </table>
</form>
<br>
<%
     
    if (!hotelname.equals("") &&!city.equals("") && !state.equals("") && 
     !type.equals("") && !rate.equals("") &&!ameneties.equals("") && !address.equals("") )
    {
      String result ="";

      try {
        
       result = adminstub.addNewHotel(hotelname,city,state,type,new Float(rate),
                                                            ameneties,address);
      }
      catch (Exception e) {       
         out.println("<B><FONT face='arial' size='2' color='red'>Could not add the hotel Exception : "+e+"</FONT></B>");
     }
      out.println("<B><FONT face='arial' size='2' color='blue'>"+result+"</font></B>");
   }else{
    out.println("Please fill all the fields and press enter ");
   }
  %>
</table>

<p>&nbsp;</p>
</body>
</html>

