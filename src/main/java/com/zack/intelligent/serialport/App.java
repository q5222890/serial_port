package com.zack.intelligent.serialport;

import android.app.Application;
import android.content.SharedPreferences;
import android.serialport.SerialPort;
import android.serialport.SerialPortFinder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * Created by Administrator on 2017-04-18.
 */

public class App extends Application {

    private SerialPort mSerialPort = null;
    public SerialPortFinder mSerialPortFinder = new SerialPortFinder();

    public void closeSerialPort() {
        if (this.mSerialPort != null) {
            this.mSerialPort.close();
            this.mSerialPort = null;
        }
    }

    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (this.mSerialPort == null) {
            SharedPreferences localSharedPreferences = getSharedPreferences("com.zack.intelligent.serialport_preferences", 0);
            //获取串口设备
            String str = localSharedPreferences.getString("DEVICE", "");
            Log.i("APP", "getSerialPort 串口: "+str);
            //获取波特率
            int i = Integer.decode(localSharedPreferences.getString("BAUDRATE", "-1")).intValue();
            Log.i("APP", "getSerialPort 波特率： "+i);
            if ((str.length() == 0) || (i == -1)) {
                throw new InvalidParameterException();
            }
            this.mSerialPort = new SerialPort(new File(str), i);
        }
        return this.mSerialPort;
    }
}
