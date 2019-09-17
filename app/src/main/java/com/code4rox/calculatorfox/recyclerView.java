package com.code4rox.calculatorfox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class recyclerView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.rec_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] items={"item1","item2","item3","item4","item5","item6","item7","item8","item9"};
        recyclerView.setAdapter(new ProgrammingAdapter(items));
    }
}
