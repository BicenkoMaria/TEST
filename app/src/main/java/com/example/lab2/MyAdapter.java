package com.example.lab2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.Db.AppDb;
import com.example.lab2.Db.FavoriteRecepts;
import com.example.lab2.Db.Recept;
import com.example.lab2.Db.User;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

   Context context;
   List<com.example.lab2.Db.Recept> recipsArrayList;
   private ItemClickListener itemListener;
   public View Recept;

    public MyAdapter(Context context, List<Recept> receptList) {
        this.context = context;
        this.recipsArrayList = receptList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Recept recept = recipsArrayList.get(position);
        holder.recipe.setText(recept.title);
        holder.recipe.setOnClickListener(view -> {
            itemListener.onItemClick(recipsArrayList.get(position));
        });

        holder.recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                String title = recept.title;
                String description = recept.description;

                bundle.putString("title", title);
                bundle.putString("description", description);

                Navigation.findNavController(v).navigate(R.id.navigation_dashboard, bundle);
            }
        });

        holder.recipe.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                AppDb db = AppDb.getInstance(context);
                String userEmail = MainActivity2.userEmail;
                User user = db.userDao().getUserByEmail(userEmail);
                List<FavoriteRecepts> favoriteRecepts = db.FavoriteReceptsDao().getUserFavoriteReceptsById(user.id);
                FavoriteRecepts favoriteRecept = new FavoriteRecepts();

                builder.setCancelable(true)
                        .setMessage(recept.description)
                        .setTitle(recept.title)
                        .setPositiveButton("Добавить рецепт в избранное", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for(int i = 0; i < favoriteRecepts.size(); i++) {
                                    if (favoriteRecepts.get(i).receptId == recept.id) {
                                        return;
                                    }
                                }

                                favoriteRecept.userId = user.id;
                                favoriteRecept.receptId = recept.id;
                                db.FavoriteReceptsDao().insert(favoriteRecept);
                            }
                        })
                        .setNeutralButton("Выйти", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i("AlertDialog", "Выйти");
                                    }
                                }).setView(R.layout.alert_dialog);

                AlertDialog alertDialog = builder.create();

                alertDialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipsArrayList.size();
    }

    public interface ItemClickListener{
        void onItemClick(Recept recipe);
    }

    public static class MyViewHolder extends
            RecyclerView.ViewHolder{

       TextView recipe;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe = itemView.findViewById(R.id.recipe);
        }
    }
}




