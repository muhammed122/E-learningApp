package com.example.e_learningapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningapp.R;
import com.example.e_learningapp.models.ModelGrads;
import com.example.e_learningapp.models.ModelMembers;
import com.example.e_learningapp.utils.Helper;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecyclerGrads extends RecyclerView.Adapter<AdapterRecyclerGrads.Holder> {


    private ArrayList<ModelMembers> list ;


    public void setList(ArrayList<ModelMembers> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterRecyclerGrads.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_grads , parent,false) ;

        return new AdapterRecyclerGrads.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerGrads.Holder holder, int position) {


        holder.textQuiz.setText(list.get(position).getQuizGrade() +"");
        holder.textAttend.setText(list.get(position).getAttendanceGrade()+"");
        holder.textEmail.setText(Helper.putDotBackForUi(list.get(position).getStudentEmail()));

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView textEmail ;

        TextView textQuiz ;
        TextView textAttend ;



        public Holder(@NonNull View itemView) {
            super(itemView);
            textEmail = itemView.findViewById(R.id.text_email);
            textQuiz = itemView.findViewById(R.id.text_quiz);
            textAttend = itemView.findViewById(R.id.text_attendance);

        }
    }


}