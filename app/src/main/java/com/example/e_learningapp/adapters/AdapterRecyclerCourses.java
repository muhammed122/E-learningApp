package com.example.e_learningapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningapp.R;
import com.example.e_learningapp.models.ModelCourse;

import java.util.ArrayList;

public class AdapterRecyclerCourses extends RecyclerView.Adapter<AdapterRecyclerCourses.Holder> {

    private ArrayList<ModelCourse> list ;

    private OnClick onClick ;
    public void setList(ArrayList<ModelCourse> list) {
        this.list = list;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subjec_or_student, parent, false);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.textCourseName.setText(list.get(position).getCourseName());

    }

    @Override
    public int getItemCount() {
        return list == null?0 :list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView textCourseName ;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textCourseName = itemView.findViewById(R.id.text_name);
            if (onClick != null){

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClick.onItemClick(list.get(getAdapterPosition()).getCourseId() , list.get(getAdapterPosition()).getCourseName());
                    }
                });
            }
        }
    }

    public interface OnClick {

        void onItemClick(String id , String name);
    }
}
