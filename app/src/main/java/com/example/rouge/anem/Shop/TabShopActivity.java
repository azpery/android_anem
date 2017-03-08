package com.example.rouge.anem.Shop;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rouge.anem.Entity.Produit;
import com.example.rouge.anem.R;

/**
 * Created by laureduchemin on 12/02/2017.
 */

public class TabShopActivity extends AppCompatActivity {


    private ViewPager mViewPager;

    private Produit produit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_produit);
        mViewPager = (ViewPager) findViewById(R.id.containerShop);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsShop);
        tabLayout.setupWithViewPager(mViewPager);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey("produit")) {
                this.produit = (Produit) b.getSerializable("produit");
            }
        }

    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tab_produit, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label_Shop);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }


}
