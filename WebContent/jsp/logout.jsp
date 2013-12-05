<%@ page contentType="text/html; charset=iso-8859-1" language="java"  errorPage="" %>

<%
session.setAttribute("isLoggedIn", false);
response.sendRedirect("../index.html");
%>