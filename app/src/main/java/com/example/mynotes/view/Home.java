package com.example.mynotes.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mynotes.R;
import com.google.android.material.navigation.NavigationView;

public class Home extends Fragment {
    private NavigationView nave;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = view.findViewById(R.id.appBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        nave = view.findViewById(R.id.navigation);
        drawerLayout = view.findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new NotesFragment()).commit();
        nave.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.notes:
                        temp = new NotesFragment();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.reminder:
                        temp = new ReminderFragment();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.archive:
                        temp = new ArchiveFragment();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.trash:
                        temp = new TrashFragment();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, temp).commit();
                return true;
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.res_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.searchbar:
                Toast.makeText(getContext(), "search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.grid_view:
                Toast.makeText(getContext(), "grid", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profileView:
                opendialog();
                break;
        }
        return true;
    }
    public void opendialog(){
        DialogFragment_profile dialogFragment = new DialogFragment_profile();
        dialogFragment.show(getActivity().getSupportFragmentManager(),"dialog");
    }
}