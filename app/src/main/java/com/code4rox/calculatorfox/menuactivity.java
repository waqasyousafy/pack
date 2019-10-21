package com.code4rox.calculatorfox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class menuactivity extends AppCompatActivity {
    String[] titles={"Calculator","Image Downloader","Music Player","Bottom Navigation","Chatting Area","Constraint Set","Contacts","Constraint Layout","Services"};
RecyclerView recyclerView;
   private List<MenuModel> menulist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuactivity);
        menulist=new ArrayList<>();
  for(String title:titles){
      menulist.add(new MenuModel(title));
  }
       recyclerView=(RecyclerView) findViewById(R.id.menu_list_view);
  recyclerView.setLayoutManager(new GridLayoutManager(this,2));
       MenuAdapter recyclerviewAdapter=new MenuAdapter(this,menulist);
       recyclerView.setAdapter(recyclerviewAdapter);


    }
    }

