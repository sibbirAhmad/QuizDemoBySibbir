package com.example.quizappbysibbir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultAdapter  extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {
    Context context;
    ArrayList<QModel> list;
    ResultAdapter(Context context, ArrayList<QModel> list){
        this.context  = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.q_model_result, parent, false);
        return new MyViewHolder(view);
    }
    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        QModel model = list.get(position);
        holder.questionTV.setText((position+1)+". "+model.getQuestion());
       int selectedOption =model.getSelectedOption();
        //Todo : Setting options
        ArrayList<String> options = model.getOptions();
        holder.option0.setText(options.get(0));
        holder.option1.setText(options.get(1));
        holder.option2.setText(options.get(2));
        holder.option3.setText(options.get(3));
        if (selectedOption>-1){
            switch (selectedOption){
                case 0:
                    holder.option0.setSelected(true);
                    break;
                case 1:
                    holder.option1.setSelected(true);
                    break;
                case 2:
                    holder.option2.setSelected(true);
                    break;
                case 3:
                    holder.option3.setSelected(true);
                    break;

            }
        }
        String correctResult  = model.getCorrectAns();
        String userAns = model.getSelectedAns();
        if (correctResult.matches(userAns)){
            RadioButton radioButton = getRBByOptionIndex(selectedOption,holder);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                radioButton.setTextColor(context.getColor(R.color.green));

            }
        }else {
            RadioButton radioButton = getRBByOptionText(options,correctResult,holder);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                radioButton.setTextColor(context.getColor(R.color.green));
            }
            if (selectedOption>-1){
                RadioButton inrb = getRBByOptionIndex(selectedOption,holder);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    inrb.setTextColor(context.getColor(R.color.red));
                    inrb.setSelected(true);
                }
            }
        }


    }
    RadioButton getRBByOptionIndex(int index,MyViewHolder holder){
        RadioButton radioButton = null;
        switch (index){
            case 0:
                radioButton = holder.option0;
                break;
            case 1:
                radioButton = holder.option1;
                break;
            case 2:
                radioButton = holder.option2;
                break;
            case 3:
                radioButton = holder.option3;
                break;

        }
        return radioButton;
    }
    RadioButton getRBByOptionText(ArrayList<String>options,String option,MyViewHolder holder){
        RadioButton radioButton = null;
        int index = -1;
        for (int i = 0; i< options.size();i++){
            if (options.get(i).matches(option)){
                index = i;
            }
        }
        return getRBByOptionIndex(index,holder);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RadioGroup optionRG;
        RadioButton option0,option1,option2,option3;
        TextView questionTV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTV = itemView.findViewById(R.id.questionTV);
            optionRG = itemView.findViewById(R.id.optionRG);
            option0 = itemView.findViewById(R.id.option0);
            option1 = itemView.findViewById(R.id.option1);
            option2 = itemView.findViewById(R.id.option2);
            option3 = itemView.findViewById(R.id.option3);

        }
    }
}
