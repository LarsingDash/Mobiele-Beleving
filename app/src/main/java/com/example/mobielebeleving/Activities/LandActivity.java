package com.example.mobielebeleving.Activities;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mobielebeleving.Data.Land;
import com.example.mobielebeleving.R;
import com.example.mobielebeleving.Views.LandAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class LandActivity extends AppCompatActivity {
    private final ArrayList<Land> lands = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land);
        getWindow().setWindowAnimations(0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Adding all Lands
        Collections.addAll(lands, new Land("INFO"), new Land(Land.Legendeland), new Land(Land.Stoerland), new Land(Land.Fabelwoud));

        //Binding Adapter
        LandAdapter adapter = new LandAdapter(this, lands);
        ViewPager2 pager = findViewById(R.id.landPager);
        pager.setAdapter(adapter);

        //Find all pages
        ArrayList<ConstraintLayout> pages = new ArrayList<>();
        Collections.addAll(pages, findViewById(R.id.pageInfo), findViewById(R.id.page1), findViewById(R.id.page2), findViewById(R.id.page3));

        //Initialize pages to gray
        for (ConstraintLayout page : pages) {
            page.setBackgroundColor(getResources().getColor(R.color.gray));
            page.setOnClickListener(view -> pager.setCurrentItem(pages.indexOf(page)));
        }

        //Get notified when the user changes pages
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                //Change the page the user went to to dark gray, make the others gray
                for (int i = 0; i < pages.size(); i++) {
                    if (i == position) {
                        pages.get(i).setBackgroundColor(getResources().getColor(R.color.dark_gray));
                    } else {
                        pages.get(i).setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                }
            }
        });
    }
}