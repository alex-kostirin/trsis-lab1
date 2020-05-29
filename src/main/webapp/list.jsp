<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<t:base>
    <%--    <h1 class="flow-text" style="text-align: center"></h1>--%>
    <ul class="collection with-header flow-text">
            <%--@elvariable id="list" type="ru.guap.shoppinglist.model.List"--%>
        <li class="collection-header" style="text-align: center"><h2><c:out value="${list.name}"/></h2></li>
        <c:forEach items="${list.items}" var="item">
            <li class="collection-item">
                <div>
                    <c:out value="${item.name}    (${item.count})"/>
                    <form class="secondary-content" method="post">
                        <button class="waves-effect waves-teal btn-flat" type="submit">
                            <i class="material-icons">delete</i>
                        </button>
                        <input id="item-delete-action" name="action" type="hidden" value="delete">
                        <input id="item-delete-id" name="id" type="hidden" value="<c:out value="${item.id}"/>">
                    </form>
                </div>
            </li>
        </c:forEach>
        <li class="collection-item">
            <form method="post">
                <div class="input-field inline">
                    <input class="validate" id="item-name" name="item-name" type="text">
                    <label for="item-name">what to buy?</label>
                </div>
                <div class="input-field inline">
                    <input class="validate" id="item-count" name="item-count" type="number">
                    <label for="item-count">how many?</label>
                </div>
                <button class="waves-effect waves-teal btn-flat secondary-content" type="submit">
                    <i class="material-icons">save</i>
                </button>
                <input id="item-create-list-id" name="list-id" type="hidden" value="<c:out value="${list.id}"/>">
                <input id="item-create-action" name="action" type="hidden" value="create">
            </form>
        </li>
    </ul>
</t:base>
