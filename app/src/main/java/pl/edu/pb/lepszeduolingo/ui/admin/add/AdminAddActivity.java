package pl.edu.pb.lepszeduolingo.ui.admin.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.net.URI;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.ui.admin.AdminFragment;

public class AdminAddActivity extends AppCompatActivity {
    private static final int UPLOAD_ID = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);

        Fragment addWordFragment = new AddWordFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flAdminAdd, addWordFragment).commit();
    }
}