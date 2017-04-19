package com.zack.intelligent.serialport;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class ConsoleActivity extends SerialPortActivity {
    private EditText mReception;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.console);
        this.mReception = ((EditText) findViewById(R.id.EditTextReception)); //接收
        //发送
        ((EditText) findViewById(R.id.EditTextEmission)).setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        CharSequence text = v.getText();
                        char[] chars = new char[text.length()];
                        int i = 0;

                        while (i < text.length()) {
                            chars[i] = text.charAt(i);
                            i += 1;
                        }
                        try {
                            ConsoleActivity.this.mOutputStream.write(new String(chars).getBytes());
                            ConsoleActivity.this.mOutputStream.write(10);
                            return false;
                        } catch (IOException e) {
                            for (; ; ) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    protected void onDataReceived(final byte[] paramArrayOfByte, final int paramInt) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (ConsoleActivity.this.mReception != null) {
                    ConsoleActivity.this.mReception.append(new String(paramArrayOfByte, 0, paramInt));
                }
            }
        });
    }
}
