package com.zack.intelligent.serialport;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.android.serialport.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

public abstract class SerialPortActivity extends AppCompatActivity {

    protected App mApplication;
    private InputStream mInputStream;
    protected OutputStream mOutputStream;
    private ReadThread mReadThread;
    protected SerialPort mSerialPort;

    private void DisplayError(int paramInt) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle("Error");
        localBuilder.setMessage(paramInt);
        localBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                SerialPortActivity.this.finish();
            }
        });
        localBuilder.show();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        this.mApplication = ((App) getApplication());
        try {
            this.mSerialPort = this.mApplication.getSerialPort();
            this.mOutputStream = this.mSerialPort.getOutputStream();
            this.mInputStream = this.mSerialPort.getInputStream();
            this.mReadThread = new ReadThread();
            this.mReadThread.start();
            return;
        } catch (SecurityException e) {
            DisplayError(R.string.error_security);
            return;
        } catch (IOException e) {
            DisplayError(R.string.error_unknown);
            return;
        } catch (InvalidParameterException e) {
            DisplayError(R.string.error_configuration);
        }
    }

    protected abstract void onDataReceived(byte[] paramArrayOfByte, int paramInt);

    protected void onDestroy() {
        if (this.mReadThread != null) {
            this.mReadThread.interrupt();
        }
        this.mApplication.closeSerialPort();
        this.mSerialPort = null;
        super.onDestroy();
    }

    private class ReadThread extends Thread {
        private ReadThread() {
        }

         public void run() {
            super.run();
            for (; ; ) {
                if (!isInterrupted()) {
                    try {
                        byte[] arrayOfByte = new byte[64];
                        if (SerialPortActivity.this.mInputStream == null) {
                            return;
                        }
                        int i = SerialPortActivity.this.mInputStream.read(arrayOfByte);
                        if (i > 0) {
                            SerialPortActivity.this.onDataReceived(arrayOfByte, i);
                        }
                    } catch (IOException localIOException) {
                        localIOException.printStackTrace();
                    }
                }
            }
        }
    }
}
