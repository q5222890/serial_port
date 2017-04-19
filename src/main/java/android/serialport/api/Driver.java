package android.serialport.api;

import android.util.Log;

import java.io.File;
import java.util.Vector;

/**
 * Created by Administrator on 2017-04-19.
 */

public class Driver {

    private String mDeviceRoot;
    Vector<File> mDevices = null;
    private String mDriverName;

    public Driver(String name, String root) {
        this.mDriverName = name;
        this.mDeviceRoot = root;
    }

    public Vector<File> getDevices() {
        if (this.mDevices == null) {
            this.mDevices = new Vector<>();
            File[] arrayOfFile = new File("/dev").listFiles();
            int i = 0;
            while (i < arrayOfFile.length) {
                if (arrayOfFile[i].getAbsolutePath().startsWith(this.mDeviceRoot)) {
                    Log.d("SerialPort", "Found new device: " + arrayOfFile[i]);
                    this.mDevices.add(arrayOfFile[i]);
                }
                i += 1;
            }
        }
        return this.mDevices;
    }

    public String getName() {
        return this.mDriverName;
    }
}
