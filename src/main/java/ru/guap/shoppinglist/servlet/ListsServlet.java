package ru.guap.shoppinglist.servlet;

import ru.guap.shoppinglist.model.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "ListsServlet", urlPatterns = {""})
public class ListsServlet extends BaseServlet {
    static final String LIST_NAME_PARAM = "list-name";
    static final String ID_PARAM = "id";

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        var action = request.getParameter(ACTION_PARAM);
        switch (action) {
            case CREATE_ACTION -> {
                var listName = request.getParameter(LIST_NAME_PARAM);
                if (listName == null || listName.trim().isEmpty()) {
                    response.sendError(400, "list name must be filled");
                    return;
                }
                this.storage.saveList(new List(listName));
                this.renderLists(request, response);
            }
            case DELETE_ACTION -> {
                var listIdStr = request.getParameter(ID_PARAM);
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
                this.storage.deleteList(list.get());
                this.renderLists(request, response);

            }
            default -> response.sendError(400, "unknown action");
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        this.renderLists(request, response);
    }

    private void renderLists(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        var lists = this.storage.findAllLists();
        request.setAttribute("lists", lists);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
