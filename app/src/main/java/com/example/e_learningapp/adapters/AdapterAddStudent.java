package com.example.e_learningapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_learningapp.R;
import com.example.e_learningapp.models.ModelMembers;
import com.example.e_learningapp.utils.Helper;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterAddStudent extends RecyclerView.Adapter<AdapterAddStudent.Holder> {


    private ArrayList<ModelMembers> list ;
    private OnDelete onDelete ;

    public void setList(ArrayList<ModelMembers> list) {
        this.list = list;
    }

    public void setOnDelete(OnDelete onDelete) {
        this.onDelete = onDelete;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_members , parent,false) ;

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {


        holder.textEmail.setText(Helper.putDotBackForUi(list.get(position).getStudentEmail()));

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView textEmail ;
        CircleImageView btnDelete ;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textEmail = itemView.findViewById(R.id.text_email);
            btnDelete = itemView.findViewById(R.id.btn_delete) ;

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDelete != null){
                        onDelete.onDeleteClick(list.get(getAdapterPosition()).getStudentEmail()
                                ,list.get(getAdapterPosition()).getCourseId());
                    }
                }
            });

        }
    }

    public interface OnDelete {

        void onDeleteClick (String studentEmail , String courseId);
    }
}
