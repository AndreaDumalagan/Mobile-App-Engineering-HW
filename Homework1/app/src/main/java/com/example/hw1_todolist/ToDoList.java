package com.example.hw1_todolist;

/*
 *
 * TODOLIST CLASS
 *
 * Create new object that represents a new item in the TODOList
 */

public class ToDoList {
    private String title;
    private String descrip;

    //Constructor
    public ToDoList(String title, String descrip){
        this.title = title;
        this.descrip = descrip;
    }

    public String getTitle(){
        return title;
    }

    public String getDescrip(){
        return descrip;
    }
}
