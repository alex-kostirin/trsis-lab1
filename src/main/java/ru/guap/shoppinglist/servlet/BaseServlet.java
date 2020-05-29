package ru.guap.shoppinglist.servlet;

import ru.guap.shoppinglist.Storage;

import javax.servlet.ServletException;

public class BaseServlet extends javax.servlet.http.HttpServlet {
    static final String ACTION_PARAM = "action";
    static final String CREATE_ACTION = "create";
    static final String DELETE_ACTION = "delete";

    protected Storage storage;

    @Override
    public void init() throws ServletException {
        this.storage = new Storage();
    }

    @Override
    public void destroy() {
        if (this.storage != null) {
            this.storage.close();
            this.storage = null;
        }
    }
}
