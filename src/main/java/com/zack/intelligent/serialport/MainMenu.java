package com.zack.intelligent.serialport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.main);
        ((Button) findViewById(R.id.ButtonSetup)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                MainMenu.this.startActivity(new Intent(MainMenu.this, SerialPortPreferences.class));
            }
        });
        ((Button) findViewById(R.id.ButtonConsole)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                MainMenu.this.startActivity(new Intent(MainMenu.this, ConsoleActivity.class));
            }
        });
        ((Button) findViewById(R.id.ButtonLoopback)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                MainMenu.this.startActivity(new Intent(MainMenu.this, LoopbackActivity.class));
            }
        });
        ((Button) findViewById(R.id.ButtonAbout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                builder.setTitle("About");
                builder.setMessage(R.string.about_msg);
                builder.show();
            }
        });
        ((Button) findViewById(R.id.ButtonQuit)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                MainMenu.this.finish();
            }
        });
    }
}
