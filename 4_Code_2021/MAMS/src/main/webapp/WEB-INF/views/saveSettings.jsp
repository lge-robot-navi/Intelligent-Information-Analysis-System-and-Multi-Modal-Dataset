<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="java.io.*,java.util.Date" %>
<%
request.setCharacterEncoding("euc-kr");
String name = request.getParameter("name");

Date date = new Date();
Long time = date.getTime();

String fileName = time + ".txt";
String result;
PrintWriter writer = null;
try{
 String filePath = application.getRealPath("/WEB-INF/views/"+ fileName);
 writer = new PrintWriter(filePath);
 writer.printf("±Û¾´ÀÌ : %s %n",name);

 result ="SUCCESS";
 
 
}catch(Exception e){
 result = "FAIL";
}finally{
 try{
  writer.close();
 }catch(Exception e){
  
 }
}

response.sendRedirect("saveSettingsResult.jsp?RESULT="+result);
%>