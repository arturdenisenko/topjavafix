<%--
  Created by IntelliJ IDEA.
  User: XXX
  Date: 07.03.2020
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Form</title>
</head>
<body>
<form method="GET" action="meals">
    <input type="hidden" name="action" value="getAll">
    <label>Choose user here
        <select name="userId">
            <option value="1">User 1</option>
            <option value="2">User 2</option>
            <option value="3">User 3 for test</option>
        </select>
    </label>
    <button type="submit">show</button>
</form>

</body>
</html>
