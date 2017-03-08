package com.example.rouge.anem.Etudiant;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rouge.anem.Entity.Utilisateur;
import com.example.rouge.anem.Etudiant.NewEtudiantActivity;
import com.example.rouge.anem.R;
import com.example.rouge.anem.Stage.StageActivity;

public class TabEtudiantActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;

    private Utilisateur etudiant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_etudiant);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mSectionsPagerAdapter.context = this;
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey("etudiant")) {
                this.etudiant = (Utilisateur) b.getSerializable("etudiant");
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
            View rootView = inflater.inflate(R.layout.fragment_tab_etudiant, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public TabEtudiantActivity context;

        @Override
        public Fragment getItem(int position) {
            Fragment f = new Fragment();
            switch (position) {
                case 0:
                    NewEtudiantActivity n = new NewEtudiantActivity();
                    n.etudiant = etudiant;
                    return n;
                case 1:
                    StageActivity s = new StageActivity();
                    s.setEtudiant(etudiant);
                    return s;
            }
            return f;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Informations générales";
                case 1:
                    return "Les stages";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
