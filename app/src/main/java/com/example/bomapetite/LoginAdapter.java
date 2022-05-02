package com.example.bomapetite;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;
    int tabs;

    public LoginAdapter(FragmentManager fm,Context context, int tabs){
    super(fm);
    this.context=context;
    this.tabs=tabs;
    }


    public Fragment getItem(int position) {
        switch (position){
            case 0:
                LoginFragment loginFragment = new LoginFragment();
                return loginFragment;
            case 1:
                RegistrarFragment registrarFragment = new RegistrarFragment();
                return registrarFragment;
            default:
                return null;
        }

    }


    public int getCount() {
        return tabs;
    }
}
