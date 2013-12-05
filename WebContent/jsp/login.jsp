<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="" %>
<jsp:useBean id="obj" class="me.ericbarber.sales.Account" />
<%
String username=request.getParameter("username");
String password=request.getParameter("password");
boolean loggedIn = false;
loggedIn=obj.login(username,password);

if(loggedIn == true)
{
session.setAttribute("isLoggedIn", true);
response.sendRedirect("../adminpanel.jsp"); // redirect to homepage if login is success

}
else
{
session.setAttribute("loginFail", true);
session.setAttribute("isLoggedIn", false);
response.sendRedirect("../admin.jsp"); // redirect to index page if login fails.
} 

%>