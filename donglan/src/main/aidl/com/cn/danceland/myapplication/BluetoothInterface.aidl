// BluetoothInterface.aidl
package com.cn.danceland.myapplication;

// Declare any non-default types here with import statements
interface BluetoothInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    boolean connect(String address);
    void disconnect();
    void close();
    boolean initialize();
}
