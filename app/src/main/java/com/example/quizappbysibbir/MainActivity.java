package com.example.quizappbysibbir;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.islamkhsh.CardSliderViewPager;
import com.github.islamkhsh.viewpager2.ViewPager2;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {


    CardSliderViewPager cardSliderViewPager;
    ArrayList<QModel> list;
    Button submitBTN,nextBTN,previousBTN,tryAgainBTN;
    QAdapter adapter;
    LinearLayout resultRL,questionRL;
    ProgressBar progressbar;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitBTN = findViewById(R.id.submitBTN);
        nextBTN = findViewById(R.id.nextBTN);
        previousBTN = findViewById(R.id.previousBTN);
        resultRL = findViewById(R.id.resultRL);
        questionRL = findViewById(R.id.questionRL);
        tryAgainBTN = findViewById(R.id.tryAgainBTN);
        progressbar = findViewById(R.id.progressbar);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showAbout();
                return true;
            }
        });
        initQuestion();


    }

    private void showAbout(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.about_me, null);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
    protected void initQuestion(){
        progressbar.setVisibility(View.VISIBLE);
        tryAgainBTN.setVisibility(View.GONE);
        ConnectionApi connectionApi = new ConnectionApi(this, "https://the-trivia-api.com/api/questions?limit=5",
                new ConnectionApi.ConnectionListener() {
                    @Override
                    public void onSuccess(String server, String resultText) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list = processJson(resultText);
                                progressbar.setVisibility(View.GONE);
                                loadQuestion(list);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String server, String errorMessage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Failed\nCheck your Internet Connection", Toast.LENGTH_SHORT).show();
                                progressbar.setVisibility(View.GONE);
                                tryAgainBTN.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });

    }
    protected void loadQuestion(ArrayList<QModel> list){
        resultRL.setVisibility(View.GONE);
        questionRL.setVisibility(View.VISIBLE);
        i = 0;
        adapter = new QAdapter(this,list);
        cardSliderViewPager = (CardSliderViewPager) findViewById(R.id.viewPager);
        cardSliderViewPager.setAdapter(adapter);
        cardSliderViewPager.setCurrentItem(i);
        initOnClick();
    }
    private void initOnClick(){
        cardSliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position==list.size()-1)
                {
                    submitBTN.setVisibility(View.VISIBLE);
                    nextBTN.setVisibility(View.INVISIBLE);
                }

                else
                {
                    if (submitBTN.isShown()){
                        submitBTN.setVisibility(View.INVISIBLE);
                    }
                    if (position<list.size()-1){
                        if (!nextBTN.isShown()){
                            nextBTN.setVisibility(View.VISIBLE);
                        }
                    }
                }
                if (position==0){
                    previousBTN.setVisibility(View.INVISIBLE);
                }else {
                    if (!previousBTN.isShown()){
                        previousBTN.setVisibility(View.VISIBLE);
                    }
                }
                i=position;

            }
        });
    }
    String TAG = "MainActivity";
    private ArrayList<QModel> processJson(String json){
        //String json = "[{\"category\":\"Film & TV\",\"id\":\"62573f049da29df7b05f7382\",\"correctAnswer\":\"The Lord of the Rings: The Fellowship of the Ring\",\"incorrectAnswers\":[\"Star Wars\",\"Double Indemnity\",\"Logan\"],\"question\":\"Name the movie that matches the following plot summary: 'A Hobbit and eight companions set out on a journey to destroy the powerful One Ring.'\",\"tags\":[\"film\",\"film_and_tv\"],\"type\":\"Multiple Choice\",\"difficulty\":\"easy\",\"regions\":[]},{\"category\":\"General Knowledge\",\"id\":\"6355b7fae8b368eed3a6823d\",\"correctAnswer\":\"The r in 'red'\",\"incorrectAnswers\":[\"The ch in 'cheese'\",\"The t in 'train'\",\"The sh in 'shop'\"],\"question\":\"In phonetics, which of these is an example of an approximant sound?\",\"tags\":[\"language\",\"linguistics\",\"general_knowledge\",\"science\"],\"type\":\"Multiple Choice\",\"difficulty\":\"hard\",\"regions\":[]},{\"category\":\"Geography\",\"id\":\"623740eecb85f7ce9e949d31\",\"correctAnswer\":\"Kazakhstan\",\"incorrectAnswers\":[\"Iraq\",\"Cabo Verde\",\"Brunei\"],\"question\":\"Nur-Sultan is the capital city of which country?\",\"tags\":[\"geography\"],\"type\":\"Multiple Choice\",\"difficulty\":\"hard\",\"regions\":[]},{\"category\":\"Geography\",\"id\":\"622a1c357cc59eab6f94ffaf\",\"correctAnswer\":\"Bangladesh\",\"incorrectAnswers\":[\"Bhutan\",\"Myanmar\",\"India\"],\"question\":\"Dhaka is the capital of ______\",\"tags\":[\"capital_cities\",\"geography\"],\"type\":\"Multiple Choice\",\"difficulty\":\"hard\",\"regions\":[]},{\"category\":\"Music\",\"id\":\"622a1c357cc59eab6f94fdd8\",\"correctAnswer\":\"Bruce Springsteen\",\"incorrectAnswers\":[\"Eric Clapton\",\"Neil Young\",\"Alanis Morissette\"],\"question\":\"Which American musician released the album 'Born in the U.S.A.'?\",\"tags\":[\"music_albums\",\"music\"],\"type\":\"Multiple Choice\",\"difficulty\":\"easy\",\"regions\":[]}]";
        JsonArray jsonObject = new JsonParser().parse(json).getAsJsonArray();
        ArrayList<QModel> list = new ArrayList<>();
        for (JsonElement object: jsonObject) {
            QModel model = new QModel();
            JsonObject element = object.getAsJsonObject();
            String correctAns = element.get("correctAnswer").getAsString();
            String question = element.get("question").getAsString();
            model.setCorrectAns(correctAns); //Todo : Adding Correct Ans
            model.setQuestion(question);
            JsonArray incorrect = element.getAsJsonArray("incorrectAnswers");
            ArrayList<String> options = new ArrayList<>();
            options.add(incorrect.get(0).getAsString());
            options.add(incorrect.get(1).getAsString());
            options.add(incorrect.get(2).getAsString());
            options.add(correctAns);
            Collections.shuffle(options);
            model.setOptions(options); //Todo : Adding Options
            list.add(model);
            //Toast.makeText(this, ""+incorrect, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, ""+element.get("correctAnswer"), Toast.LENGTH_SHORT).show();
        }
        return list;

    }
    ArrayList<QModel> getQ(){
        ArrayList<QModel> list = new ArrayList<>();
        for (int i = 0; i<10;i++){
            QModel model = new QModel();
            model.setQuestion("Q : "+i);
            list.add(model);
        }
        return list;
    }

    int i = 0;
    public void next(View view) {
        i++;
        if (i<list.size()){
            cardSliderViewPager.setCurrentItem(i);
        }

    }

    public void previous(View view) {
        i--;
        if (i>=0){
            cardSliderViewPager.setCurrentItem(i);
        }else {
            i=0;
        }
    }

    ArrayList<QModel> ansData;
    public void submit(View view) {
        int correct = 0,incorrect = 0;
        ansData = adapter.getSubmitResult();
        for (int i = 0 ; i <ansData.size();i++){
            String correctAns = ansData.get(i).getCorrectAns();
            String userAns = ansData.get(i).getSelectedAns();
            if (correctAns.matches(userAns)){
                correct++;
            }else {
                incorrect++;
            }

            String ss = ansData.get(i).getCorrectAns()+" ---> "+ansData.get(i).getSelectedAns();
            Log.d(TAG, "submit: "+ss);
        }
        String res = "Correct : "+correct+"\nIncorrect : "+incorrect+"\nTotal Marks : "+correct*5;
        TextView resultTV = findViewById(R.id.resultTV);
        resultTV.setText(res);
        questionRL.setVisibility(View.GONE);
        resultRL.setVisibility(View.VISIBLE);
    }
    void showResult(ArrayList<QModel> resultList){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.result_bsd,null);
        RecyclerView recyclerView = bottomSheetView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new ResultAdapter(this,resultList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

    public void retry(View view) {
        resultRL.setVisibility(View.GONE);
        initQuestion();
    }

    public void showAns(View view) {
    showResult(ansData);
    }

    public void tryAgain(View view) {
    initQuestion();
    }
}