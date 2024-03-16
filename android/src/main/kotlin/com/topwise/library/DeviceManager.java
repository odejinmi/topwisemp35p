package com.topwise.library;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.AidlDeviceService.Stub;
import com.topwise.cloudpos.aidl.camera.AidlCameraScanCode;
import com.topwise.cloudpos.aidl.card.AidlCheckCard;
import com.topwise.cloudpos.aidl.emv.level2.AidlAmex;
import com.topwise.cloudpos.aidl.emv.level2.AidlEmvL2;
import com.topwise.cloudpos.aidl.emv.level2.AidlEntry;
import com.topwise.cloudpos.aidl.emv.level2.AidlPaypass;
import com.topwise.cloudpos.aidl.emv.level2.AidlPaywave;
import com.topwise.cloudpos.aidl.emv.level2.AidlPure;
import com.topwise.cloudpos.aidl.emv.level2.AidlQpboc;
import com.topwise.cloudpos.aidl.iccard.AidlICCard;
import com.topwise.cloudpos.aidl.led.AidlLed;
import com.topwise.cloudpos.aidl.pinpad.AidlPinpad;
import com.topwise.cloudpos.aidl.printer.AidlPrinter;
import com.topwise.cloudpos.aidl.rfcard.AidlRFCard;
import com.topwise.cloudpos.aidl.shellmonitor.AidlShellMonitor;
import com.topwise.cloudpos.aidl.system.AidlSystem;
import com.topwise.sdk.emv.EmvDeviceManager;

public class DeviceManager {
    private static String DEVICE_SERVICE_PACKAGE_NAME = "com.android.topwise.topusdkservice";
    private static String DEVICE_SERVICE_CLASS_NAME = "com.android.topwise.topusdkservice.service.DeviceService";
    private static String ACTION_DEVICE_SERVICE = "topwise_cloudpos_device_service";
    private static DeviceManager mDeviceServiceManager;
    private static Context mContext;
    private static AidlDeviceService mDeviceService;
    private static ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            DeviceManager.mDeviceService = Stub.asInterface(service);
            Log.i("topwise", "onServiceConnected  :  " + DeviceManager.mDeviceService);
            EmvDeviceManager.getInstance().init(DeviceManager.mDeviceService);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("jeremy", "onServiceDisconnected  :  " + DeviceManager.mDeviceService);
            DeviceManager.mDeviceService = null;
        }
    };

    public DeviceManager(Context context) {
        mContext = context;
    }

    public static DeviceManager getInstance() {
        if (null == mDeviceServiceManager) {
            mDeviceServiceManager = new DeviceManager(mContext);
        }

        return mDeviceServiceManager;
    }

    public boolean bindService() {
        Log.i("jeremy", "");
        Intent intent = new Intent();
        intent.setAction(ACTION_DEVICE_SERVICE);
        intent.setClassName(DEVICE_SERVICE_PACKAGE_NAME, DEVICE_SERVICE_CLASS_NAME);

        try {
            boolean bindResult = mContext.bindService(intent, mConnection, 1);
            Log.i("jeremy", "bindResult = " + bindResult);
            return bindResult;
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    public void unBindDeviceService() {
        Log.i("jeremy", "");

        try {
            mContext.unbindService(mConnection);
        } catch (Exception var2) {
            Log.i("jeremy", "unbind DeviceService service failed : " + var2);
        }

    }

    public IBinder getSystemService() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getSystemService();
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlSystem getSystemManager() {
        AidlSystem aidlSystem = com.topwise.cloudpos.aidl.system.AidlSystem.Stub.asInterface(this.getSystemService());
        return aidlSystem;
    }

    public IBinder getPinPad() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getPinPad(0);
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlPinpad getPinpadManager() {
        AidlPinpad aidlPinpad = com.topwise.cloudpos.aidl.pinpad.AidlPinpad.Stub.asInterface(this.getPinPad());
        return aidlPinpad;
    }

    public AidlLed getLedManager() {
        AidlLed aidlLed = com.topwise.cloudpos.aidl.led.AidlLed.Stub.asInterface(this.getLed());
        return aidlLed;
    }

    public IBinder getLed() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getLed();
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public IBinder getPrinter() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getPrinter();
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlPrinter getPrintManager() {
        AidlPrinter aidlPrinter = com.topwise.cloudpos.aidl.printer.AidlPrinter.Stub.asInterface(this.getPrinter());
        return aidlPrinter;
    }

    public IBinder getCheckCard() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getCheckCard();
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlCheckCard getDetectCardManager() {
        AidlCheckCard aidlCheckCard = com.topwise.cloudpos.aidl.card.AidlCheckCard.Stub.asInterface(this.getCheckCard());
        return aidlCheckCard;
    }

    public AidlRFCard getRFCard() {
        AidlRFCard aidlRFCard = com.topwise.cloudpos.aidl.rfcard.AidlRFCard.Stub.asInterface(this.getRFIDReader());
        return aidlRFCard;
    }

    public IBinder getRFIDReader() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getRFIDReader();
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public IBinder getPSAMReader(int devid) {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getPSAMReader(devid);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
        }

        return null;
    }

    public IBinder getSerialPort(int port) {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getSerialPort(port);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
        }

        return null;
    }

    public AidlShellMonitor getShellMonitor() {
        try {
            if (mDeviceService != null) {
                return com.topwise.cloudpos.aidl.shellmonitor.AidlShellMonitor.Stub.asInterface(mDeviceService.getShellMonitor());
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public IBinder getCPUCard() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getCPUCard();
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public IBinder getPedestal() {
        try {
            if (mDeviceService != null) {
                return mDeviceService.getPedestal();
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlICCard getICCardMoniter() {
        try {
            if (mDeviceService != null) {
                return com.topwise.cloudpos.aidl.iccard.AidlICCard.Stub.asInterface(mDeviceService.getInsertCardReader());
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlRFCard getRFCardMoniter() {
        try {
            if (mDeviceService != null) {
                return com.topwise.cloudpos.aidl.rfcard.AidlRFCard.Stub.asInterface(mDeviceService.getRFIDReader());
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlCameraScanCode getScanAidl() {
        try {
            if (mDeviceService != null) {
                return com.topwise.cloudpos.aidl.camera.AidlCameraScanCode.Stub.asInterface(mDeviceService.getCameraManager());
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlEmvL2 getEmvL2() {
        try {
            if (mDeviceService != null) {
                return com.topwise.cloudpos.aidl.emv.level2.AidlEmvL2.Stub.asInterface(mDeviceService.getL2Emv());
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlPure getL2Pure() {
        try {
            if (mDeviceService != null) {
                return com.topwise.cloudpos.aidl.emv.level2.AidlPure.Stub.asInterface(mDeviceService.getL2Pure());
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlPaypass getL2Paypass() {
        try {
            if (mDeviceService != null) {
                return com.topwise.cloudpos.aidl.emv.level2.AidlPaypass.Stub.asInterface(mDeviceService.getL2Paypass());
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlPaywave getL2Paywave() {
        try {
            if (mDeviceService != null) {
                return com.topwise.cloudpos.aidl.emv.level2.AidlPaywave.Stub.asInterface(mDeviceService.getL2Paywave());
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlEntry getL2Entry() {
        try {
            if (mDeviceService != null) {
                return com.topwise.cloudpos.aidl.emv.level2.AidlEntry.Stub.asInterface(mDeviceService.getL2Entry());
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlAmex getL2Amex() {
        try {
            if (mDeviceService != null) {
                return com.topwise.cloudpos.aidl.emv.level2.AidlAmex.Stub.asInterface(mDeviceService.getL2Amex());
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }

    public AidlQpboc getL2Qpboc() {
        try {
            if (mDeviceService != null) {
                return com.topwise.cloudpos.aidl.emv.level2.AidlQpboc.Stub.asInterface(mDeviceService.getL2Qpboc());
            }
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

        return null;
    }
}