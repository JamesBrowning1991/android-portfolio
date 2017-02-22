package com.example.james.portfolio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment{

    private static TextView contentTextView;
    private static TextView headerTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        contentTextView = (TextView)view.findViewById(R.id.contentTextView);
        headerTextView = (TextView)view.findViewById(R.id.headerTextView);

        return view;
    }

    public void changeText(String headerText, String contentText) {
        headerTextView.setText(headerText);
        contentTextView.setText(contentText);
    }
}
