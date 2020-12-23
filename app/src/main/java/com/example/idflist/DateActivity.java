package com.example.idflist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DateActivity extends AppCompatActivity {
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Auth) {
            intent = new Intent(this, Authentication.class);
        } else if (id == R.id.Date) {
            intent = new Intent(this, DateActivity.class);
        } else if (id == R.id.Img) {
            intent = new Intent(this, ImageActivity.class);
        } else if (id == R.id.Reg) {
            intent = new Intent(this, Register.class);
        }
        startActivity(intent);
        return true;
    }

}