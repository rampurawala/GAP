package com.example.hp.gap.APIresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToDoListResponse {

    @SerializedName("todo_list_id")
    @Expose
    private String todoListId;
    @SerializedName("u_id")
    @Expose
    private String uId;
    @SerializedName("list_name")
    @Expose
    private String listName;

    @SerializedName("item_date")
    @Expose
    private String item_date;


    public String getTodoListId() {
        return todoListId;
    }

    public void setTodoListId(String todoListId) {
        this.todoListId = todoListId;
    }

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListDate() {
        return item_date;
    }

    public void setListDate(String item_date) {
        this.item_date = item_date;
    }
}
