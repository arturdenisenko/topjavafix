<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>

<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <form method="GET" action="meals">
        <input type="hidden" name="action" value="filter">
        <input type="hidden" name="userId" value="${userId}">
        <table border="0">
            <tr>
                <th>From Date</th>
                <th>To Date</th>
                <th>From Time</th>
                <th>To Time</th>
            </tr>
            <td><input type="date" value="" name="fromDate"></td>
            <td><input type="date" value="" name="toDate"></td>
            <td><input type="time" value="" name="fromTime"></td>
            <td><input type="time" value="" name="toTime"></td>
        </table>
        <br>
        <button type="submit">Filter</button>
    </form>
    <a href="meals?action=create&userId=${userId}">Add Meal</a>
    <br><br>


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


    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}&userId=${userId}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}&userId=${userId}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>