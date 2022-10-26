package com.khazana.hrm.Adapter.RequestPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.khazana.hrm.Fragment.Personalinfo.ContactsFragment;
import com.khazana.hrm.Fragment.Personalinfo.ExperienceFragment;
import com.khazana.hrm.Fragment.Personalinfo.PersonalFragment;
import com.khazana.hrm.Fragment.Personalinfo.QualificationFragment;
import com.khazana.hrm.Fragment.Personalinfo.UserSkillsFragment;


public class PersonalPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int code = 1;
    public PersonalPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:

                PersonalFragment tab1 = new PersonalFragment();

                return tab1;
            case 1:

                QualificationFragment tab2 = new QualificationFragment();

                return tab2;

            case 2:

                ExperienceFragment tab3 = new ExperienceFragment();
                return tab3;

            case 3:
                ContactsFragment tab4 = new ContactsFragment();
                return tab4;

            case 4:
                UserSkillsFragment tab5 = new UserSkillsFragment();
                return tab5;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}