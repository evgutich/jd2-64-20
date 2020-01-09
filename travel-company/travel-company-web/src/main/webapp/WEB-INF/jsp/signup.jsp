<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="messages"/>

<html >
<head>
    <meta charset="UTF-8">
    <title>BlackPineapple.by | <fmt:message key="signup.page.title"/></title>
</head>
<body>
<%@include file="include/header.jsp" %>
<%@include file="include/menu.jsp" %>

<c:if test ="${errorCode==1}"><p style="color: red; text-align: center"><fmt:message key="error.code.1"/></p></c:if>
<c:if test ="${errorCode==3}"><p style="color: red; text-align: center"><fmt:message key="error.code.3"/></p></c:if>
<c:if test ="${errorCode==4}"><p style="color: red; text-align: center"><fmt:message key="error.code.4"/></p></c:if>

<form method="POST" action="${pageContext.request.contextPath}/signup">
    <p align="center"><fmt:message key="signup.page.title"/>:</p>
    <table align="center" border="0" bord >
        <tr>
            <td><fmt:message key="signup.page.user.name"/></td>
            <td><input type="text" name="userName" value="${userName}"/></td>
        </tr>

        <tr>
            <td><fmt:message key="signup.page.user.password"/></td>
            <td><input type="password" name="password" value=""/></td>
        </tr>
        <tr>
            <td><fmt:message key="signup.page.user.password.repeat"/></td>
            <td><input type="password" name="passwordRepeat" value=""/></td>
        </tr>


        <tr>
            <td colspan="2">
                <input type="submit" value="<fmt:message key="signup.page.submit"/>"/>
                <a href="${pageContext.request.contextPath}/"><fmt:message key="signup.page.cancel"/>
                </a>
            </td>
        </tr>

    </table>
</form>

<%@include file="include/footer.jsp" %>
</body>
</html>
