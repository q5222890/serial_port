package android.serialport;

import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017-04-19.
 */

public class SerialPort {

    private static final String TAG = "SerialPort";
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    static {
        System.loadLibrary("serial_port");
    }

    public SerialPort(File paramFile, int paramInt)
            throws SecurityException, IOException {
        if ((!paramFile.canRead()) || (!paramFile.canWrite())) {
            try {
                Process localProcess = Runtime.getRuntime().exec("/system/bin/su");
                String str = "chmod 666 " + paramFile.getAbsolutePath() + "\n" + "exit\n";
                localProcess.getOutputStream().write(str.getBytes());
                if ((localProcess.waitFor() != 0) || (!paramFile.canRead()) || (!paramFile.canWrite())) {
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }
        this.mFd = open(paramFile.getAbsolutePath(), paramInt);
        if (this.mFd == null) {
            Log.e("SerialPort", "native open returns null");
            throw new IOException();
        }
        this.mFileInputStream = new FileInputStream(this.mFd);
        this.mFileOutputStream = new FileOutputStream(this.mFd);
    }

    private static native FileDescriptor open(String paramString, int paramInt);

    public native void close();

    public InputStream getInputStream() {
        return this.mFileInputStream;
    }

    public OutputStream getOutputStream() {
        return this.mFileOutputStream;
    }
}
