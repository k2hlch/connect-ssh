<%@ page language = "java" import = "com.oracle.demo.proxy.*" %>
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
<!--  //Change this line to use any other proxy  -->
<jsp:usebean  id='adminstub' scope='session' class='com.oracle.demo.proxy.IHotelAdminProxy' />
<jsp:usebean  id='hstatus' class='com.oracle.demo.proxy.com_oracle_demo_struct_HotelStatus' />

<A href="RoomStatus.jsp">check room status</A><br>
<A href="HotelAdmin.jsp?viewHotel_main=true">look at hotel_main table</A><br>
<A href="HotelAdmin.jsp?viewHotel_room=true">look at hotel_room table</A><br>
<A href="HotelAdd.jsp">add a new hotel</A>

<% 
 
 boolean viewHotelMain = new Boolean(request.getParameter("viewHotel_main")).booleanValue();;
 boolean viewHotelRoom = new Boolean(request.getParameter("viewHotel_room")).booleanValue();
//change this line to use a different proxy
// IHotelAdminProxy stub = new IHotelAdminProxy();
 
  // if its view HOTEL_MAIN
  
  if(viewHotelMain)
     {
%>
   <TABLE  BORDER="0" CELLPADDING="2" CELLSPACING="1" SUMMARY="Results">
    <TR VALIGN=MIDDLE BGCOLOR="#000066"> 
     <TD>&nbsp;</td>
     <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
     HOTELNAME</font></B></td>
      <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
     ADDRESS </font></B></TD>
     <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
      STATE</font></B></TD>
     <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
     CITY</font></B></TD>    
     <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
      TYPE&nbsp;&nbsp; </font></B></TD>
     <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
      RATE </font></B></TD>
     <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
      AMENETIES </font></B></TD>        
    </TR>


 <%
      //Change this line to use any other proxy 
     com_oracle_demo_struct_HotelDetails[] detailsArray = null;
     try{
        detailsArray = adminstub.peekHotelMain();
     
        for (int i=0;i<detailsArray.length;i++) {
               if (!(detailsArray[i].equals("")) && detailsArray[i] != null) {
                   com_oracle_demo_struct_HotelDetails hd = detailsArray[i];

 %>
      <TR VALIGN="top"> 
       <TD>
       <B><font  face="Arial, Helvetica, sans-serif" size="2"> <%=(i+1)%></font></B>
       </TD>
       <TD><B><font  face="Arial, Helvetica, sans-serif" size="2"> 
        <%=hd.getHotelName()%></font></B></TD>
          <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
        <%=hd.getAddress()%></font></B></TD>
          <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=hd.getState()%></font></B></TD>
          <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=hd.getCity()%></font></B></TD>
         <TD><B><font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=hd.getType()%></font></B></TD>
          <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=hd.getRate().floatValue()%></font></B></TD>
          <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=hd.getAmeneties()%>
          </font></B></TD>
        </TR>
<%
                  
                 }
           }
        
     }catch(Exception e){
            out.println("<B><FONT face='arial' size='2' color='red'>Sorry, could not show the HOTEL_MAN table details "+e.getMessage()+"</FONT></B>");
     } 
   }//end of if view hotel_main
   if(viewHotelRoom)
   {
     %>
 <TABLE  BORDER="0" CELLPADDING="2" CELLSPACING="1" SUMMARY="Results">

  <TR VALIGN=MIDDLE BGCOLOR="#000066"> 
    <TD>&nbsp;</td>
     <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
     HOTELNAME</font></B></td>
     <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
      STATE</font></B></TD>
      <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
      CITY</font></B>
    </TD>    
    <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
      ROOMTYPE&nbsp;&nbsp; </font></B></TD>
     <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
     CHECKIN </font></B></TD>
   <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
     CHECKOUT </font></B></TD>
    <TD><B> <font color="#FFFFFF" face="Arial" size="2"> 
     BOOKED BY </font></B></TD>
        
  </TR>

     <%
    
     com_oracle_demo_struct_HotelStatus[] statusArray = null;

     try{
     statusArray = adminstub.peekHotelRoom();
  
       for (int i=0;i<statusArray.length;i++) {
            if (!statusArray[i].equals("") && statusArray[i]!= null) {
                   hstatus = statusArray[i];

      %>
      <TR VALIGN="top"> 
       <TD><B><font  face="Arial, Helvetica, sans-serif" size="2"> <%=(i+1)%>
        </font></B>
        </TD>
        <TD><B><font  face="Arial, Helvetica, sans-serif" size="2"> 
        <%=hstatus.getHotelName()%></font></B></TD>
        <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=hstatus.getState()%></font></B></TD>
         <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=hstatus.getCity()%></font></B></TD>
         <TD><B><font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=hstatus.getRoomType()%></font></B></TD>
         <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=hstatus.getCheckin()%></font></B></TD>
         <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=hstatus.getCheckout()%></font></B></TD>
         <TD><B> <font  face="Arial, Helvetica, sans-serif" size="2"> 
         <%=hstatus.getBookedBy()%></font></B></TD>
       </TR>
      <%      
                   }
              }

        }catch(Exception e){
            out.println("<B><FONT face='arial' size='2' color='red'>Sorry, could not show the HOTEL_ROOM table details "+e.getMessage()+"</FONT></B>");
        } 
     
      } //end of if view hotel_room
  %>

  
</table>
<p>&nbsp;</p>
</body>
</html>

