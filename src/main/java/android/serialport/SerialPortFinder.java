package android.serialport;

import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Administrator on 2017-04-19.
 */

public class SerialPortFinder {

    private static final String TAG = "SerialPort";
    private Vector<Driver> mDrivers = null;

    public String[] getAllDevices() {
        Vector<String> devices = new Vector<String>();
        // Parse each driver
        Iterator<Driver> itdriv;
        try {
            itdriv = getDrivers().iterator();
            while (itdriv.hasNext()) {
                Driver driver = itdriv.next();
                Iterator<File> itdev = driver.getDevices().iterator();
                while (itdev.hasNext()) {
                    String device = itdev.next().getName();
                    String value = String.format("%s (%s)", device,
                            driver.getName());
                    devices.add(value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return devices.toArray(new String[devices.size()]);
    }

    public String[] getAllDevicesPath() {
        Vector<String> devices = new Vector<String>();
        // Parse each driver
        Iterator<Driver> itdriv;
        try {
            itdriv = getDrivers().iterator();
            while (itdriv.hasNext()) {
                Driver driver = itdriv.next();
                Iterator<File> itdev = driver.getDevices().iterator();
                while (itdev.hasNext()) {
                    String device = itdev.next().getAbsolutePath();
                    devices.add(device);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return devices.toArray(new String[devices.size()]);
    }

    private Vector<Driver> getDrivers() throws IOException {
        LineNumberReader localLineNumberReader = null;
        if (this.mDrivers == null) {
            this.mDrivers = new Vector<>();
            localLineNumberReader = new LineNumberReader(new FileReader("/proc/tty/drivers"));
        }
        for (; ; ) {
            String local = null;
            if (localLineNumberReader != null) {
                local = localLineNumberReader.readLine();
            }
            if (local == null) {
                if (localLineNumberReader != null) {
                    localLineNumberReader.close();
                }
                return this.mDrivers;
            }
            String str = local.substring(0, 21).trim();
            String[] w = local.split(" +");
            if ((w.length >= 5) && (w[(w.length - 1)].equals("serial"))) {
                Log.d("SerialPort", "Found new driver " + str + " on " + w[(w.length - 4)]);
                this.mDrivers.add(new Driver(str, w[(w.length - 4)]));
            }
        }
    }


    public class Driver {
        private String mDeviceRoot;
        Vector<File> mDevices = null;
        private String mDriverName;

        public Driver(String name, String root) {
            this.mDriverName = name;
            this.mDeviceRoot = root;
        }

        File[] arrayOfFile;
        int i;

        public Vector<File> getDevices() {
            if (this.mDevices == null) {
                this.mDevices = new Vector<>();
                arrayOfFile = new File("/dev").listFiles();
                i = 0;
            }
            for (; ; ) {
                if (i >= arrayOfFile.length) {
                    return this.mDevices;
                }
                if (arrayOfFile[i].getAbsolutePath().startsWith(this.mDeviceRoot)) {
                    Log.d("SerialPort", "Found new device: " + arrayOfFile[i]);
                    this.mDevices.add(arrayOfFile[i]);
                }
                i += 1;
            }
        }

        public String getName() {
            return this.mDriverName;
        }
    }
}
