<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<jsp:directive.include file = "../includes/includefile.jspf" />
</head>
  <body>
    <div>
      <div class="container">
        <div class="header"></div>
        <div class="menu">
          <jsp:directive.include file="../WEB-INF/menu.jspf" />
        </div>
        <h1>Dar de alta a un autor</h1>
        <div style="text-align: center; padding: 20px; border: 1px solid black; border-radius: 10px; width: 400px; margin: 0 auto; " >
        <form action="${pageContext.request.contextPath}/ControllerAdmin" method="POST">
          <p>Nombre Autor: <input type="text" placeholder="Nombre Autor" name="nombre" required/></p>
          <p>Fecha Nacimiento: <input type="date" name="fecha" required/></p>
          <input type="submit" value="Guardar" />
        </form>
        </div>
      </div>
    </div>
  </body>
</html>