package pl.edu.pb.lepszeduolingo.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.db.DatabaseFacade;

public class AdminActivity extends AppCompatActivity {
    FragmentTransaction fragmentTransaction;
    Fragment mainFragment = new AdminFragment();
    DatabaseFacade databaseFacade = new DatabaseFacade(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flAdmin, mainFragment, "main").commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}