package com.lite.generator.framework.util;

import java.io.*;

public class LoopedStreams{

    private PipedOutputStream pipedOS = new PipedOutputStream();

    private boolean keepRunning = true;

    private ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream() {
        public void close() {
            keepRunning = false;
            try {
                super.close();
                pipedOS.close();
            }
            catch(IOException e) {
                System.exit(1);
            }
        }
    };

    private PipedInputStream pipedIS = new PipedInputStream() {
        public void close() {
            keepRunning = false;
            try    {
                super.close();
            }
            catch(IOException e) {
                System.exit(1);
            }
        }
    };

    public LoopedStreams(){
        try {
            pipedOS.connect(pipedIS);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        Thread thread = new Thread(() -> {
            while(keepRunning) {
                if(byteArrayOS.size() > 0) {
                    byte[] buffer = null;
                    synchronized(byteArrayOS) {
                        buffer = byteArrayOS.toByteArray();
                        byteArrayOS.reset();
                    }
                    try {
                        pipedOS.write(buffer, 0, buffer.length);
                    }
                    catch(IOException e) {
                        System.exit(1);
                    }
                }
                else {
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {

                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public InputStream getInputStream() {
        return pipedIS;
    }

    public OutputStream getOutputStream() {
        return byteArrayOS;
    }

}