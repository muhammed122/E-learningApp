package com.example.e_learningapp.adapters;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningapp.R;
import com.example.e_learningapp.data.MySharedPrefrance;
import com.example.e_learningapp.models.ModelChat;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerChat extends RecyclerView.Adapter<AdapterRecyclerChat.Holder> {

    private ArrayList<ModelChat> list ;


    public void setList(ArrayList<ModelChat> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        if (list.get(position).getSenderId().equals(MySharedPrefrance.getUserId())){
            holder.textMassage.setGravity(Gravity.RIGHT);
        }else {
            holder.textMassage.setGravity(Gravity.LEFT);
        }
        holder.textMassage.setText(list.get(position).getMassage());

    }

    @Override
    public int getItemCount() {
        return list == null?0 :list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView textMassage;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textMassage = itemView.findViewById(R.id.massage);

        }
    }

}
