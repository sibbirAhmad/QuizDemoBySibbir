package com.example.quizappbysibbir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.islamkhsh.CardSliderAdapter;

import java.util.ArrayList;

public class QAdapter extends CardSliderAdapter<QAdapter.MyViewHolder> {

    Context context;
    ArrayList<QModel> list;
    QAdapter(Context context, ArrayList<QModel> list){
        this.context = context;
        this.list = list;
    }
    int selectedOption;
    @Override
    public void bindVH(@NonNull MyViewHolder holder, int position) {
        QModel model = list.get(position);
        holder.questionTV.setText((position+1)+". "+model.getQuestion());
         selectedOption =model.getSelectedOption();
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
        holder.optionRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                selectedOption = -1;
                switch (id) {
                    case R.id.option0:
                        selectedOption = 0;
                        break;
                    case R.id.option1:
                        selectedOption = 1;
                        break;
                    case R.id.option2:
                        selectedOption = 2;
                        break;
                    case R.id.option3:
                        selectedOption = 3;
                        break;
                }
                model.setSelectedAns(options.get(selectedOption));
                model.setSelectedOption(selectedOption);
                list.set(position,model);
                //notifyItemChanged(position);
                
            }
        });
    }
    public ArrayList<QModel> getSubmitResult(){
        return list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.q_model, parent, false);
        return new MyViewHolder(view);
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
