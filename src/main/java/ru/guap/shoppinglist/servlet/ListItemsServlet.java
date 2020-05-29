package ru.guap.shoppinglist.servlet;

import ru.guap.shoppinglist.model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "ListItemsServlet", urlPatterns = {"/lists/*"})
public class ListItemsServlet extends BaseServlet {
    static final String ITEM_NAME_PARAM = "item-name";
    static final String ITEM_COUNT_PARAM = "item-count";
    static final String LIST_ID_PARAM = "list-id";
    static final String ID_PARAM = "id";

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        var action = request.getParameter(ACTION_PARAM);
        switch (action) {
            case CREATE_ACTION -> {
                var itemName = request.getParameter(ITEM_NAME_PARAM);
                if (itemName == null || itemName.trim().isEmpty()) {
                    response.sendError(400, "item name must be filled");
                    return;
                }
                var itemCountStr = request.getParameter(ITEM_COUNT_PARAM);
                if (itemCountStr == null) {
                    response.sendError(400, "item count must be filled");
                    return;
                }
                int itemCount;
                try {
                    itemCount = Integer.parseInt(itemCountStr);
                } catch (NumberFormatException e) {
                    response.sendError(400, "count format error");
                    return;
                }
                var listIdStr = request.getParameter(LIST_ID_PARAM);
                if (listIdStr == null) {
                    response.sendError(400, "list id must be filled");
                    return;
                }
                int listId;
                try {
                    listId = Integer.parseInt(listIdStr);
                } catch (NumberFormatException e) {
                    response.sendError(400, "list id format error");
                    return;
                }
                var list = this.storage.findList(listId);
                if (list.isEmpty()) {
                    response.sendError(404, "list not found");
                    return;
                }
                var item = new Item(itemName, itemCount);

                this.storage.saveItem(new Item(itemName, itemCount, list.get()));
                this.renderItem(request, response);
            }
            case DELETE_ACTION -> {
                var itemIdStr = request.getParameter(ID_PARAM);
                if (itemIdStr == null) {
                    response.sendError(400, "item id must be filled");
                    return;
                }
                int itemId;
                try {
                    itemId = Integer.parseInt(itemIdStr);
                } catch (NumberFormatException e) {
                    response.sendError(400, "item id format error");
                    return;
                }
                var item = this.storage.findItem(itemId);
                if (item.isEmpty()) {
                    response.sendError(404, "item not found");
                    return;
                }
                this.storage.deleteItem(item.get());
                this.renderItem(request, response);
            }
            default -> response.sendError(400, "unknown action");
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        this.renderItem(request, response);
    }

    private void renderItem(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        try {
            var path = request.getPathInfo();
            if (path == null) {
                response.sendError(404, "not found");
                return;
            }
            var id = Integer.parseInt(path.substring(1));
            var list = this.storage.findList(id);
            if (list.isEmpty()) {
                response.sendError(404, "not found");
                return;
            }
            request.setAttribute("list", list.get());
            getServletContext().getRequestDispatcher("/list.jsp").forward(
                    request, response);
        } catch (NumberFormatException e) {
            response.sendError(404, "not found");
        }
    }
}
