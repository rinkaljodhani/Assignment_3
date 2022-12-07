package com.example.assignment_3;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment_3.model.Question;

public class FirstFragment extends Fragment {

    Question question;
    Question question1;
    TextView tv_question;
    View view;


    public static Fragment newInstance(Question question, Question question1)
    {
        FirstFragment myFragment = new FirstFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("questionlist",question);
        bundle.putSerializable("colorlist",question1);
        myFragment.setArguments(bundle);
        return myFragment;
    }

   /* public FirstFragment(Question question, Question question1) {
        this.question = question;
        this.question1 = question1;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_name, container, false);

        if(getArguments()!=null) {
            question = (Question) getArguments().getSerializable("questionlist");
            question1 = (Question) getArguments().getSerializable("colorlist");
        }

/*
        MainActivity activity = (MainActivity) getActivity();
        question = activity.getMyData();
        question1 = activity.getMyColorData();*/

        findView(view);
        return view;
    }


    private void findView(View view) {
        tv_question = view.findViewById(R.id.tv_question);
        tv_question.setText(question.getQuestion());
        tv_question.setBackgroundColor(getResources().getColor(question1.getColor()));
    }
}