<%@ page language = "java"  import = "com.oracle.demo.proxy.*"%>
<html>
<head>
<title>Hotel Services Inc.</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body bgcolor="#FFFFFF" text="#000000">
<table summary="Banner">
  <tr> 
    <td><img src="hotelIntro.gif" alt="Logo" width="120" height="83"></td>
    <td>&nbsp;</td>
  </tr>
</table>
<H1><font face="Arial, Helvetica, sans-serif" size="4">Hotel Search</font></H1>
<!-- //change this line of code to use any other proxy Class -->
<jsp:usebean  id='stub' scope='session' class='com.oracle.demo.proxy.IHotelProxy' />
<jsp:usebean  id='hshort' scope='page' class='com.oracle.demo.proxy.com_oracle_demo_struct_HotelShort' />
<%
String city = request.getParameter("city");
String state = request.getParameter("state");
String checkin = request.getParameter("checkin");
String checkout = request.getParameter("checkout");
String occupants = request.getParameter("occupants");
if (city == null)
   city = "";
if (state == null)
  state = "";
if (checkin == null)
   checkin = "";
if (checkout == null)
  checkout = "";
if (occupants == null)
  occupants = "1";
%>
<form action="MainPage.jsp" method="get">
  <table border="0" summary="Hotel Search" >
    <tr> 
      <td > 
        <div align="right"><b><font face="Arial" size="2">City :</font></b></div>
      </td>
      <td valign="top"> 
        <input type="text" name="city" size="14" value="<%=city %>">
      </td>
      <td > 
        <div align="right"><b><font face="Arial" size="2">State :</font></b></div>
      </td>
      <td  valign="top"> 
        <input type="text" name="state" size="14" value="<%=state %>">
      </td>
    </tr>
    <tr>
      <td > 
        <div align="right"><b><font face="Arial" size="2">Checkin Date:</font></b></div>
      </td>
      <td  > 
        <input type="text" name="checkin" size="14" value="<%=checkin %>"><br>
        <font face="Arial" size="1">(MM/DD/YYYY)</font>
      </td>
      <td > 
        <div align="right"><b><font face="Arial" size="2">Checkout Date:</font></b></div>
      </td>
      <td  > 
        <input type="text" name="checkout" size="14" value="<%=checkout %>"><br>
        <font face="Arial" size="1">(MM/DD/YYYY)</font>
      </td>
    </tr>
    <tr>
      <td > 
        <div align="right"><b><font face="Arial" size="2">Occupants:</font></b></div>
      </td>
      <td > 
        <input type="text" name="occupants" size="14" value="<%=occupants %>">
      
      </td>
     
    </tr>
    <tr> 
      <td colspan ="4" align="middle"> 
        <input type="Submit" value="Search for Hotels" name="Search"/>
      </td>
    </tr>
  </table>
</form>
<br>
<%
   com_oracle_demo_struct_HotelShort[] hotels = null;
    if (!city.equals("") && !state.equals("") && !checkin.equals("") && 
        !checkout.equals("")) {
       
       try {
     
       hotels= stub.getList(city,state,checkin,checkout,
                          new Integer(occupants));
          }
       catch (Exception e) {       
         out.println("<B><FONT face=\"arial\" size=\"2\">Could not get the hotel names : "+e.getMessage()+"</FONT></b>");
          }
    
         
%>

<TABLE WIDTH="460" BORDER="0" CELLPADDING="2" CELLSPACING="1" SUMMARY="Results">
  <TR VALIGN=MIDDLE BGCOLOR="#003366"> 
    <TD colspan="5"><B> <font color="#FFFFFF" face="Arial, Helvetica, sans-serif" size="2"> 
      Hotels :</font></B></TD>
  </TR>
  <TR VALIGN=MIDDLE BGCOLOR="#000066"> 
  <TD>&nbsp;</td>
    <TD><B> <font color="#FFFFFF" face="Arial, Helvetica, sans-serif" size="2"> 
      Name</font></B></TD>
          <TD><B> <font color="#FFFFFF" face="Arial, Helvetica, sans-serif" size="2"> 
      Type :</font></B></TD>
          <TD><B> <font color="#FFFFFF" face="Arial, Helvetica, sans-serif" size="2"> 
      Rate:</font></B></TD>
        <TD>&nbsp;</td>
  </TR>
  <%       
    if(hotels != null ){
      if (hotels.length==0)
          out.println("<FONT face=\"arial\" size=\"2\">"+
               "No Hotels found , change a search criteria</FONT>");
      for (int i=0;i<hotels.length;i++) {
               if (hotels[i]!=null) {
                   hshort = hotels[i];

  %>
  <TR VALIGN="top"> 
       <TD><%=(i+1)%></TD>
       <TD><B> 
        <font  face="Arial, Helvetica, sans-serif" size="2"> 
        <%=hshort.getHotelName()%></font></B></TD>
          <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
        <%=hshort.getType()%></font></B></TD>
          <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=(hshort.getRate().floatValue())%></font></B></TD>
  
       <TD>
       <A href="GetDetails.jsp?hotelname=<%=hshort.getHotelName()%>&city=<%=city%>&state=<%=state%>&checkin=<%=checkin%>&checkout=<%=checkout%>&occupants=<%=occupants%>">detail</A>
       </TD>
  </TR>
  <% 
     } 
    }
    }
   }
  %>
</table>

<p>&nbsp;</p>

</body>
</html>
