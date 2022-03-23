    <%--
      Created by IntelliJ IDEA.
      User: Darkm
      Date: 10/18/2021
      Time: 7:58 PM
      To change this template use File | Settings | File Templates.
    --%>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                <div class="menu_section">
                <h3>General</h3>
                <ul class="nav side-menu">
                <c:forEach items="${sMenuSession}" var="item">
                    <li id="${item.idMenu}"><a><i class="fa fa-home"></i> ${item.recentMenu.name} <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                    <c:forEach items="${item.child}" var="item2">
                        <li id="${item2.idMenu}"><a href="<c:url value='${item2.recentMenu.url}'/>"> ${item2.recentMenu.name} </a></li>
                    </c:forEach>
                    </ul>
                    </li>
                </c:forEach>
                </ul>
                </div>
        </div>