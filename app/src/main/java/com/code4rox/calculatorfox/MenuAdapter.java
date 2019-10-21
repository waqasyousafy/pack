package com.code4rox.calculatorfox;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder>{
    @NonNull
     private List<MenuModel> mData;
    private Context context;
    public MenuAdapter(Context context,List<MenuModel> mData) {
        this.context=context;
        this.mData=mData;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_design,parent,false);
        MenuViewHolder vh=new MenuViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
holder.btn.setText(mData.get(position).get_title());
holder.btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       switch (position){
           case 0:
               Intent intent1=new Intent(context,Calculator.class);
               context.startActivity(intent1);
               break;
           case 1:
               Intent intent2=new Intent(context,Image_Downloader.class);
               context.startActivity(intent2);
               break;
           case 2:
               Intent intent3=new Intent(context,MusicPlayer.class);
               context.startActivity(intent3);
               break;
           case 3:
               Intent intent4=new Intent(context,bottom_navigation.class);
               context.startActivity(intent4);
               break;
           case 4:
               Intent intent5=new Intent(context,Tabsactivity.class);
               context.startActivity(intent5);
               break;
           case 5:
               Intent intent6=new Intent(context,Constraintset_example_paractice_2.class);
               context.startActivity(intent6);
               break;
           case 6:
               Intent intent7=new Intent(context,contacts.class);
               context.startActivity(intent7);
               break;
           case 7:
               Intent intent8=new Intent(context,Constraintlayout.class);
               context.startActivity(intent8);
               break;
           case 8:
               Intent intent9=new Intent(context,Song_Downloader.class);
               context.startActivity(intent9);
               break;
       }
    }
});
}


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  class MenuViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        Button btn;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
             btn=itemView.findViewById(R.id.book_title_id);
             cardView=itemView.findViewById(R.id.cardview_id);
        }
    }
}
