package com.example.assignment_3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_3.model.Question;
import com.example.assignment_3.model.QuestionBank;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   /* public MainActivity(){
        LocaleUtils.updateConfig(this);
    }*/

    TextView tv_true, tv_false;
    LinearProgressIndicator progress_bar;
    QuestionBank questionBank;
    ArrayList<Question> questions = new ArrayList<>();
    ArrayList<Question> color_array = new ArrayList<>();
    int position = 0;
    int score = 0;
    int question_no = 10;
    int attrmpt_no = 1;

    /*@Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(MyApp.localeManager.setLocale(base));
    }*/

   /* @Override
    protected void attachBaseContext(Context newBase) {


      *//*  Locale localeToSwitchTo = new Locale(Locale.getDefault().getLanguage());
        ContextWrapper localeUpdatedContext = ContextUtils.updateLocale(newBase, localeToSwitchTo);
        super.attachBaseContext(localeUpdatedContext);*//*

     *//* Locale locale = new Locale("ta");
        Locale.setDefault(locale);
        Resources resources = newBase.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
//        config.setLayoutDirection(locale);
        createConfigurationContext(config);
//        resources.updateConfiguration(config, resources.getDisplayMetrics());
        super.attachBaseContext(newBase);*//*
    }*/


    @Override
    protected void onRestart() {
        super.onRestart();
        MyApp.localeManager.setNewLocale(this, Locale.getDefault().getLanguage());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MyApp.localeManager.setNewLocale(this, Locale.getDefault().getLanguage());


        findView();

        if (Build.VERSION.SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                Intent getpermission = new Intent();
                getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(getpermission);
            }
        }
        for (int i = 0; i < questionBank.question_array.length; i++) {
            questions.add(new Question(getString(questionBank.question_array[i]), questionBank.answer_array[i]));
            color_array.add(new Question(questionBank.color_array[i]));
        }

        loadFragment(new FirstFragment(), position);


    }

    /*@Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Locale localeToSwitchTo = new Locale(Locale.getDefault().getLanguage());
        ContextWrapper localeUpdatedContext = ContextUtils.updateLocale(this, localeToSwitchTo);
        super.onConfigurationChanged(newConfig);
    }*/

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_getAverage:
                readfile();
                break;
            case R.id.menu_selectQN:
                break;
            case R.id.menu_reset:
                attrmpt_no = 0;
                writetofile("");
                break;
        }
        return true;
    }

    private void findView() {

        questionBank = new QuestionBank();

        tv_true = findViewById(R.id.tv_true);
        tv_false = findViewById(R.id.tv_false);
        progress_bar = findViewById(R.id.progress_bar);

        tv_true.setOnClickListener(this);
        tv_false.setOnClickListener(this);

    }

    private void loadFragment(Fragment fragment, int position) {

// create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragment = (FirstFragment) FirstFragment.newInstance(questions.get(position),color_array.get(position));

        fragmentTransaction.replace(R.id.fv_layout, fragment);
        fragmentTransaction.commit();



    }

   /* public Question getMyData() {
        return questions.get(position);
    }

    public Question getMyColorData() {
        return color_array.get(position);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_true:
                msgshowMethod(1);
                position = position + 1;
                if (position < questions.size()) {
                    loadFragment(new FirstFragment(), position);
                } else {
                    showResultDialog();
                }
                break;
            case R.id.tv_false:
                msgshowMethod(0);
                position = position + 1;
                if (position < questions.size()) {
                    loadFragment(new FirstFragment(), position);
                } else {
                    showResultDialog();
                }
                break;
        }

    }

    private void showResultDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(getString(R.string.result))
                .setMessage(getString(R.string.string_result1) + " " + score + " " + getString(R.string.string_result2) + " " + questions.size())
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(R.string.ignore, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        writetofile(score + "&"+"1"+"_");

                    }
                })
                .setCancelable(false)
                .show();
    }

    private void showAttemptsDialog(int rightanswersum, int attempt) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(getString(R.string.string_correctanswer)+" "+rightanswersum +" "+ getString(R.string.string_in) + " "+attempt + " "+getString(R.string.string_attempts) )
                // A null listener allows the button to dismiss the dialog and take no further action.

                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        writetofile(score + "&");

                    }
                })
                .setCancelable(false)
                .show();
    }

    private void readfile() {
        try {
            // open the file for reading
            InputStream instream = openFileInput("quiz.txt");

            // if file the available for reading
            if (instream != null) {
                // prepare the file for reading
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);

                String line;

                // read every line of the file into the line-variable, on
                // line at the time
                if ((line = buffreader.readLine()) != null) {
                    // do something with the settings from the file
//                    Toast.makeText(MainActivity.this, line, Toast.LENGTH_LONG).show();
                    int intialchar = 0;
                    int rightanswersum = 0;
                    int attempt = 0;
                    for(int i = intialchar ; i < line.length();i++){
                        if(line.charAt(i) == '&'){
                            rightanswersum = rightanswersum+Integer.parseInt(line.substring(intialchar,i));
                            intialchar = i+1;

                        }else if(line.charAt(i) == '_'){
                            attempt = attempt+Integer.parseInt(line.substring(intialchar,i));
                            intialchar = i+1;

                        }
                    }
                    showAttemptsDialog(rightanswersum,attempt);

                }else{
                    Toast.makeText(MainActivity.this, R.string.string_nothing, Toast.LENGTH_LONG).show();
                }
            }

            // close the file again
            instream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void writetofile(String content) {


        try {
            File file = new File(Environment.getExternalStorageDirectory(), "quiz.txt");
// check if file exists
            if (file.exists()) {

                if(content.equals("")){
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("quiz.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.append("");
                    outputStreamWriter.close();
                }else {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("quiz.txt", Context.MODE_APPEND));
                    outputStreamWriter.write(content);
                    outputStreamWriter.close();
                }

            } else {
// create an new file
                file.createNewFile();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("quiz.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write(content);
                outputStreamWriter.close();
            }

            if(!content.equals("")){
                position = 0;
                score = 0;
                attrmpt_no++;
                progress_bar.setProgress(0);
                Collections.shuffle(questions);
                Collections.shuffle(color_array);
                loadFragment(new FirstFragment(), position);
            }

        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

    }

    private void msgshowMethod(int i) {
        if (position < questions.size()) {
            progress_bar.setProgress(((position + 1) * 100) / questions.size());
            if (questions.get(position).getAnswer() == i) {
                score++;
                Toast.makeText(MainActivity.this, R.string.correct, Toast.LENGTH_LONG).show();
            } else Toast.makeText(MainActivity.this, R.string.incorrect, Toast.LENGTH_LONG).show();
        }
    }
}