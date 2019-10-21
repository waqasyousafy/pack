package com.code4rox.calculatorfox;

public class MenuModel {
    private String title;
    MenuModel(String title){
        this.title=title;
    }
    public String get_title(){
        return title;
    }
    public void set_title(String title){
        this.title=title;
    }
}
