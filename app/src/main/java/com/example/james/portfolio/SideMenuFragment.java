package com.example.james.portfolio;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class SideMenuFragment extends Fragment {

    SideMenuListener activityCommander;

    public interface SideMenuListener {
        public void changeMainTxt(String headerText, String contentText);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCommander = (SideMenuListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(activityCommander.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.side_menu, container, false);

        final ImageButton homeButton = (ImageButton)view.findViewById(R.id.homeButton);
        final Button button1 = (Button)view.findViewById(R.id.button1);
        final Button button2 = (Button)view.findViewById(R.id.button2);
        final Button button3 = (Button)view.findViewById(R.id.button3);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.changeMainTxt("About me header", "About me content");
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.changeMainTxt("Button 1 clicked", "");
                System.out.println("B1CLICK");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.changeMainTxt("Button 2 clicked", "");
                System.out.println("B2CLICK");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCommander.changeMainTxt("Button 3 clicked", "");
                System.out.println("B3CLICK");
            }
        });

        return view;
    }

    //private void changeColourTemp

}
