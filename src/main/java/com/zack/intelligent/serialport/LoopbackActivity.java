package com.zack.intelligent.serialport;

import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

public class LoopbackActivity extends SerialPortActivity {

    int mIncoming;
    int mOutgoing;
    SendingThread mSendingThread;
    TextView mTextViewIncoming;
    TextView mTextViewOutgoing;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.loopback);
        this.mTextViewOutgoing = ((TextView) findViewById(R.id.TextViewOutgoingValue));
        this.mTextViewIncoming = ((TextView) findViewById(R.id.TextViewIncomingValue));
        if (this.mSerialPort != null) {
            this.mSendingThread = new SendingThread();
            this.mSendingThread.start();
        }
    }

    protected void onDataReceived(byte[] paramArrayOfByte, int paramInt) {
        this.mIncoming += paramInt;
        runOnUiThread(new Runnable() {
            public void run() {
                LoopbackActivity.this.mTextViewIncoming.setText(new Integer(LoopbackActivity.this.mIncoming).toString());
            }
        });
    }

    protected void onDestroy() {
        if (this.mSendingThread != null) {
            this.mSendingThread.interrupt();
        }
        super.onDestroy();
    }

    private class SendingThread extends Thread {
        private SendingThread() {
        }

        public void run() {
            byte[] arrayOfByte = new byte[1024];
            int i = 0;
            while (i < arrayOfByte.length) {
                arrayOfByte[i] = 85;
                i += 1;
            }
            for (; ; ) {
                if (!isInterrupted()) {
                    try {
                        if (LoopbackActivity.this.mOutputStream != null) {
                            LoopbackActivity.this.mOutputStream.write(arrayOfByte);
                            LoopbackActivity localLoopbackActivity = LoopbackActivity.this;
                            localLoopbackActivity.mOutgoing += arrayOfByte.length;
                            LoopbackActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    LoopbackActivity.this.mTextViewOutgoing.setText(
                                            new Integer(LoopbackActivity.this.mOutgoing).toString());
                                }
                            });
                        }
                    } catch (IOException localIOException) {
                        localIOException.printStackTrace();
                    }
                }
            }
        }
    }
}
