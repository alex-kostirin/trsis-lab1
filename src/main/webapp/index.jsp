<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:base>
    <%--    <h1 class="flow-text" style="text-align: center"></h1>--%>
    <ul class="collection with-header flow-text">
        <li class="collection-header" style="text-align: center"><h2>Shopping lists 🛒</h2></li>
            <%--@elvariable id="lists" type="java.util.List<ru.guap.shoppinglist.model.List>"--%>
        <c:forEach items="${lists}" var="list">
            <li class="collection-item">
                <a href="lists/<c:out value="${list.id}"/>"><c:out value="${list.name}"/></a>
                <form class="secondary-content" method="post">
                    <button class="waves-effect waves-teal btn-flat" type="submit">
                        <i class="material-icons">delete</i>
                    </button>
                    <input id="list-delete-action" name="action" type="hidden" value="delete">
                    <input id="list-delete-id" name="id" type="hidden" value="<c:out value="${list.id}"/>">
                </form>
            </li>
        </c:forEach>
        <li class="collection-item">
            <form method="post">
                <div class="input-field inline">
                    <input class="validate" id="list-name" name="list-name" type="text">
                    <label for="list-name">your awesome list name</label>
                </div>
                <button class="waves-effect waves-teal btn-flat secondary-content" type="submit">
                    <i class="material-icons">save</i>
                </button>
                <input id="list-create-action" name="action" type="hidden" value="create">
            </form>
        </li>
    </ul>
</t:base>
