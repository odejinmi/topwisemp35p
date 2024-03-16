package com.topwise.library.activity;

import static com.topwise.cloudpos.aidl.system.AidlSystem.Stub.asInterface;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.Keep;
import com.topwise.cloudpos.aidl.AidlDeviceService;
import com.topwise.cloudpos.aidl.AidlDeviceService.Stub;
import com.topwise.cloudpos.aidl.card.AidlCheckCard;
import com.topwise.cloudpos.aidl.card.AidlCheckCardListener;
import com.topwise.cloudpos.aidl.emv.PCardLoadLog;
import com.topwise.cloudpos.aidl.emv.PCardTransLog;
import com.topwise.cloudpos.aidl.led.AidlLed;
import com.topwise.cloudpos.aidl.magcard.TrackData;
import com.topwise.cloudpos.aidl.pinpad.AidlPinpad;
import com.topwise.cloudpos.aidl.pinpad.GetPinListener;
import com.topwise.cloudpos.aidl.printer.AidlPrinter;
import com.topwise.cloudpos.aidl.printer.AidlPrinterListener;
import com.topwise.cloudpos.aidl.printer.Align;
import com.topwise.cloudpos.aidl.printer.PrintTemplate;
import com.topwise.cloudpos.aidl.printer.TextUnit;
import com.topwise.cloudpos.aidl.system.AidlSystem;
import com.topwise.library.DeviceManager;
import com.topwise.library.model.CardAccountType;
import com.topwise.library.model.DebitCardRequestDto;
import com.topwise.library.util.BCDASCII;
import com.topwise.library.util.HexUtil;
import com.topwise.library.util.TripleDES;
import com.topwise.library.util.Utility;
import com.topwise.library.util.emv.DeviceState;
import com.topwise.library.util.emv.TransactionMonitor;
import com.topwise.sdk.emv.EmvDeviceManager;
import com.topwise.sdk.emv.EmvManager;
import com.topwise.sdk.emv.EmvTransData;
import com.topwise.sdk.emv.OnEmvProcessListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0011\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0017\u0018\u00002\u00020\u0001:\u0002{|B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\u0002\u0010\u000bJ\u000e\u0010L\u001a\u00020\n2\u0006\u0010M\u001a\u00020\u0003J\b\u0010N\u001a\u00020\nH\u0002J\u0006\u0010O\u001a\u00020\nJ\u001a\u0010P\u001a\u0004\u0018\u00010Q2\u0006\u0010M\u001a\u00020\u00032\b\u0010R\u001a\u0004\u0018\u00010QJ\b\u0010S\u001a\u00020\nH\u0002J\u0010\u0010T\u001a\u00020\u00132\b\u0010U\u001a\u0004\u0018\u00010\u0013J\b\u0010V\u001a\u00020\u0013H\u0002J\b\u0010W\u001a\u00020\u0013H\u0002J\u0010\u0010X\u001a\u00020\u00132\b\u0010U\u001a\u0004\u0018\u00010\u0013J\u0010\u0010Y\u001a\u00020\u00132\b\u0010U\u001a\u0004\u0018\u00010\u0013J\u0010\u0010Z\u001a\u00020[2\u0006\u0010\\\u001a\u00020]H\u0002J\b\u0010^\u001a\u00020\u0013H\u0002J\u0006\u0010_\u001a\u00020\u0013J\u000e\u0010`\u001a\u00020\u00132\u0006\u0010U\u001a\u00020\u0013J\u001e\u0010a\u001a\u00020b2\u0006\u0010c\u001a\u00020\u00132\u0006\u0010d\u001a\u00020\u00052\u0006\u0010e\u001a\u00020\u0005J\u001b\u0010f\u001a\u00020g2\f\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00130iH\u0002¢\u0006\u0002\u0010jJ\b\u0010k\u001a\u00020\u0013H\u0002J\u000e\u0010l\u001a\u00020\u00132\u0006\u0010m\u001a\u00020\rJ\b\u0010n\u001a\u00020\nH\u0002J\u0006\u0010o\u001a\u00020\nJ\u000e\u0010p\u001a\u00020\n2\u0006\u0010q\u001a\u00020rJ\u0010\u0010s\u001a\u00020\u00132\u0006\u0010t\u001a\u00020\u0013H\u0002J\u0010\u0010u\u001a\u00020\n2\u0006\u0010v\u001a\u00020\u0013H\u0002J\u0010\u0010w\u001a\u00020\n2\u0006\u0010v\u001a\u00020\u0013H\u0002J\u0010\u0010x\u001a\u00020\n2\u0006\u0010y\u001a\u00020\u0013H\u0016J\u000e\u0010z\u001a\u00020\n2\u0006\u0010M\u001a\u00020\u0003R\u0014\u0010\f\u001a\u00020\rX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\rX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u0013X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\"X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001a\u0010'\u001a\u00020(X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u000e\u0010/\u001a\u000200X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00101\u001a\u00020\u0013X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\u0016\"\u0004\b3\u00104R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\n\n\u0002\u00106\u001a\u0004\b\u0004\u00105R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\n\n\u0002\u00106\u001a\u0004\b\u0006\u00105R\u001a\u00107\u001a\u000208X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u000e\u0010=\u001a\u00020\rX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\rX\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010?\u001a\u0004\u0018\u00010@X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010A\u001a\u0004\u0018\u00010BX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010C\u001a\u0004\u0018\u00010DX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010F\"\u0004\bG\u0010HR\u001a\u0010I\u001a\u00020\u0013X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010\u0016\"\u0004\bK\u00104¨\u0006}"},
        d2 = {"Lcom/topwise/library/activity/TopWiseDevice;", "", "activity", "Landroid/content/Context;", "isPrinter", "", "isSystemService", "callback", "Lkotlin/Function1;", "Lcom/topwise/library/util/emv/TransactionMonitor;", "", "(Landroid/content/Context;Ljava/lang/Boolean;Ljava/lang/Boolean;Lkotlin/jvm/functions/Function1;)V", "DIALOG_EXIT_APP", "", "getDIALOG_EXIT_APP", "()I", "SEARCH_CARD_TIME", "getSEARCH_CARD_TIME", "TAG", "", "TOPWISE_SERVICE_ACTION", "getTOPWISE_SERVICE_ACTION", "()Ljava/lang/String;", "getActivity", "()Landroid/content/Context;", "setActivity", "(Landroid/content/Context;)V", "aidlCard", "Lcom/topwise/cloudpos/aidl/card/AidlCheckCard;", "getAidlCard", "()Lcom/topwise/cloudpos/aidl/card/AidlCheckCard;", "setAidlCard", "(Lcom/topwise/cloudpos/aidl/card/AidlCheckCard;)V", "aidlPboc", "Lcom/topwise/sdk/emv/EmvManager;", "getAidlPboc", "()Lcom/topwise/sdk/emv/EmvManager;", "setAidlPboc", "(Lcom/topwise/sdk/emv/EmvManager;)V", "aidlPin", "Lcom/topwise/cloudpos/aidl/pinpad/AidlPinpad;", "getAidlPin", "()Lcom/topwise/cloudpos/aidl/pinpad/AidlPinpad;", "setAidlPin", "(Lcom/topwise/cloudpos/aidl/pinpad/AidlPinpad;)V", "getCallback", "()Lkotlin/jvm/functions/Function1;", "conn", "Landroid/content/ServiceConnection;", "generatedPinBlock", "getGeneratedPinBlock", "setGeneratedPinBlock", "(Ljava/lang/String;)V", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "mListen", "Lcom/topwise/cloudpos/aidl/printer/AidlPrinterListener;", "getMListen", "()Lcom/topwise/cloudpos/aidl/printer/AidlPrinterListener;", "setMListen", "(Lcom/topwise/cloudpos/aidl/printer/AidlPrinterListener;)V", "mMainKeyIndex", "mWorkKeyIndex", "printerDev", "Lcom/topwise/cloudpos/aidl/printer/AidlPrinter;", "systemService", "Lcom/topwise/cloudpos/aidl/system/AidlSystem;", "transData", "Lcom/topwise/sdk/emv/EmvTransData;", "getTransData", "()Lcom/topwise/sdk/emv/EmvTransData;", "setTransData", "(Lcom/topwise/sdk/emv/EmvTransData;)V", "transactionAmount", "getTransactionAmount", "setTransactionAmount", "bindService", "context", "closeLed", "configureTerminal", "createExplicitFromImplicitIntent", "Landroid/content/Intent;", "implicitIntent", "downloadParam", "getAcquiringInstitutionIdCode", "track2Data", "getConsume55", "getCurTime", "getExpiryDate", "getPan", "getPinParam", "Landroid/os/Bundle;", "pinType", "", "getSeqNum", "getSerialNo", "getServiceCode", "getTextUnit", "Lcom/topwise/cloudpos/aidl/printer/TextUnit;", "txt", "isLarge", "isCenter", "getTlv", "", "tags", "", "([Ljava/lang/String;)[B", "getTrack2", "getprintErrorInfo", "errorCode", "injectKeys", "onDestroy", "printDoc", "template", "Lcom/topwise/cloudpos/aidl/printer/PrintTemplate;", "processTrack2", "track", "showMessage", "tip", "showResult", "startEmv", "transAmount", "unbindLibService", "EmvListener", "MyGetPinListener", "app_release"}
)
@Keep
public class TopWiseDevice {
    @Nullable
    private EmvTransData transData;
    private int DIALOG_EXIT_APP = 0;
    private int SEARCH_CARD_TIME = 0;
    private int mMainKeyIndex = 0;
    private int mWorkKeyIndex = 0;
    @NotNull
    public String transactionAmount;
    @NotNull
    public String generatedPinBlock;
    @NotNull
    public EmvManager aidlPboc;
    @NotNull
    public AidlPinpad aidlPin;
    @NotNull
    public AidlCheckCard aidlCard;
    @NotNull
    private Context activity;
    private final String TAG = "";
    private AidlPrinter printerDev;
    private AidlSystem systemService;
//    @NotNull
//    private final String TOPWISE_SERVICE_ACTION;
//    private final ServiceConnection conn;
    @NotNull
    private AidlPrinterListener mListen;
    @Nullable
    private Boolean isPrinter = null;
    @Nullable
    private Boolean isSystemService = null;
    @NotNull
    private TransactionMonitor callback;

    @Nullable
    public final EmvTransData getTransData() {
        return this.transData;
    }

    public final void setTransData(@Nullable EmvTransData var1) {
        this.transData = var1;
    }

    public final int getDIALOG_EXIT_APP() {
        return this.DIALOG_EXIT_APP;
    }

    public final int getSEARCH_CARD_TIME() {
        return this.SEARCH_CARD_TIME;
    }

    @NotNull
    public final String getTransactionAmount() {
        String var10000 = this.transactionAmount;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transactionAmount");
        }

        return var10000;
    }

    public final void setTransactionAmount(@NotNull String var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.transactionAmount = var1;
    }

    @NotNull
    public final String getGeneratedPinBlock() {
        String var10000 = this.generatedPinBlock;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("generatedPinBlock");
        }

        return var10000;
    }

    public final void setGeneratedPinBlock(@NotNull String var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.generatedPinBlock = var1;
    }

    @NotNull
    public final EmvManager getAidlPboc() {
        EmvManager var10000 = this.aidlPboc;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("aidlPboc");
        }

        return var10000;
    }

    public final void setAidlPboc(@NotNull EmvManager var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.aidlPboc = var1;
    }

    @NotNull
    public final AidlPinpad getAidlPin() {
        AidlPinpad var10000 = this.aidlPin;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("aidlPin");
        }

        return var10000;
    }

    public final void setAidlPin(@NotNull AidlPinpad var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.aidlPin = var1;
    }

    @NotNull
    public final AidlCheckCard getAidlCard() {
        AidlCheckCard var10000 = this.aidlCard;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("aidlCard");
        }

        return var10000;
    }

    public final void setAidlCard(@NotNull AidlCheckCard var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.aidlCard = var1;
    }

    @NotNull
    public final Context getActivity() {
        return this.activity;
    }

    public final void setActivity(@NotNull Context var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.activity = var1;
    }

//    public final void configureTerminal() {
//        this.downloadParam();
//        this.injectKeys();
//    }

//    private final void downloadParam() {
//        Log.e("DOWNLOAD", "PARAM");
//        boolean updateResult = false;
//        EmvManager mPbocManager = EmvManager.getInstance();
//
//        try {
//            mPbocManager.updateAID(3, (String)null);
//            mPbocManager.updateCAPK(3, (String)null);
//            InputStream ins = this.activity.getAssets().open("icparam/ic_param.txt");
//            if (ins != null && ins.available() != 0) {
//                Log.e("AVAILABLE", "Not null");
//                BufferedReader br = new BufferedReader((Reader)(new InputStreamReader(ins)));
//                Object line = (String)null;
//
//                while(true) {
//                    String var6 = br.readLine();
//                    boolean $i$f$toTypedArray = false;
//                    boolean var8 = false;
//                    int var10 = 0;
//                    if (var6 == null) {
//                        break;
//                    }
//
//                    if (var6 != null) {
//                        if (var6 == null) {
//                            Intrinsics.throwNpe();
//                        }
//
//                        byte var12;
//                        Object[] var13;
//                        Collection $this$toTypedArray$iv;
//                        Object[] var10000;
//                        boolean var16;
//                        if (StringsKt.startsWith$default(var6, "AID", false, 2, (Object)null)) {
//                            if (var6 == null) {
//                                Intrinsics.throwNpe();
//                            }
//
//                            $this$toTypedArray$iv = (Collection)StringsKt.split$default((CharSequence)var6, new String[]{"="}, false, 0, 6, (Object)null);
//                            var12 = 1;
//                            $i$f$toTypedArray = false;
//                            var10000 = $this$toTypedArray$iv.toArray(new String[0]);
//                            if (var10000 == null) {
//                                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
//                            }
//
//                            var13 = var10000;
//                            var16 = mPbocManager.updateAID(var12, ((String[])var13)[1]);
//                        } else {
//                            if (var6 == null) {
//                                Intrinsics.throwNpe();
//                            }
//
//                            $this$toTypedArray$iv = (Collection)StringsKt.split$default((CharSequence)var6, new String[]{"="}, false, 0, 6, (Object)null);
//                            var12 = 1;
//                            $i$f$toTypedArray = false;
//                            var10000 = $this$toTypedArray$iv.toArray(new String[0]);
//                            if (var10000 == null) {
//                                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
//                            }
//
//                            var13 = var10000;
//                            var16 = mPbocManager.updateCAPK(var12, ((String[])var13)[1]);
//                        }
//
//                        updateResult = var16;
//                    }
//                }
//            } else {
//                Log.e("NOT AVAILABLE", "Not null");
//            }
//        } catch (IOException var14) {
//            var14.printStackTrace();
//        }
//
//        if (updateResult) {
//            this.showResult("Capk && Aid Download Success");
//        } else {
//            this.showResult("Capk Or Aid Download Fail");
//        }
//
//    }

    private final void injectKeys() {
        DeviceManager var10000 = DeviceManager.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(var10000, "DeviceManager.getInstance()");
        AidlPinpad pinpadManager = var10000.getPinpadManager();
        byte[] tmk = BCDASCII.hexStringToBytes("89F8B0FDA2F2896B9801F131D32F986D89F8B0FDA2F2896B");
        byte[] tak = BCDASCII.hexStringToBytes("92B1754D6634EB22");
        byte[] tpk = BCDASCII.hexStringToBytes("B5E175AC5FD8DD8A03AD23A35C5BAB6B");
        byte[] trk = BCDASCII.hexStringToBytes("744185122EEC284830694CAD383B4F7A");
        boolean mIsSuccess = false;

        try {
            pinpadManager.loadMainkey(this.mMainKeyIndex, tmk, (byte[])null);
            mIsSuccess = pinpadManager.loadWorkKey(1, this.mMainKeyIndex, this.mWorkKeyIndex, tpk, (byte[])null);
        } catch (RemoteException var8) {
            var8.printStackTrace();
        }

        if (mIsSuccess) {
            this.showResult("Download Keys Success");
        } else {
            this.showResult("Download Keys Success Fail");
        }

    }

//    public void startEmv(@NotNull String transAmount) {
//        Intrinsics.checkParameterIsNotNull(transAmount, "transAmount");
//        boolean var3 = false;
//        BigDecimal var5 = new BigDecimal(transAmount);
//        String var10001 = var5.toString();
//        Intrinsics.checkExpressionValueIsNotNull(var10001, "(transAmount.toBigDecimal()).toString()");
//        this.transactionAmount = var10001;
//        this.callback.invoke(new TransactionMonitor(DeviceState.INSERT_CARD, "INSERT CARD", true, (DebitCardRequestDto)null));
//
//        try {
//            EmvDeviceManager var10000 = EmvDeviceManager.getInstance();
//            Intrinsics.checkExpressionValueIsNotNull(var10000, "EmvDeviceManager.getInstance()");
//            var10000.getDetectCardManager().checkCard(true, true, true, this.SEARCH_CARD_TIME, (AidlCheckCardListener)(new AidlCheckCardListener.Stub() {
//                public void onFindMagCard(@NotNull TrackData trackData) throws RemoteException {
//                    Intrinsics.checkParameterIsNotNull(trackData, "trackData");
//                    TopWiseDevice.this.showResult("find mag card");
//                }
//
//                public void onSwipeCardFail() throws RemoteException {
//                }
//
//                public void onFindICCard() throws RemoteException {
//                    TopWiseDevice.this.setTransData(new EmvTransData(2, (byte)0, 1, false));
//                    TopWiseDevice.this.getAidlPboc().startEmvProcess(TopWiseDevice.this.getTransData(), (OnEmvProcessListener)(TopWiseDevice.this.new EmvListener()));
//                }
//
//                public void onFindRFCard() throws RemoteException {
//                    TopWiseDevice.this.setTransData(new EmvTransData(4, (byte)0, 1, false));
//                    TopWiseDevice.this.getAidlPboc().startEmvProcess(TopWiseDevice.this.getTransData(), (OnEmvProcessListener)(TopWiseDevice.this.new EmvListener()));
//                }
//
//                public void onTimeout() throws RemoteException {
//                    TopWiseDevice.this.showResult("Time Out");
//                    TopWiseDevice.this.getCallback().invoke(new TransactionMonitor(DeviceState.READCARD_FAILED, "TIME OUT", true, (DebitCardRequestDto)null));
//                }
//
//                public void onCanceled() throws RemoteException {
//                    TopWiseDevice.this.showResult("User cancel search card");
//                    TopWiseDevice.this.getCallback().invoke(new TransactionMonitor(DeviceState.READCARD_FAILED, "CANCELLED", true, (DebitCardRequestDto)null));
//                }
//
//                public void onError(int i) throws RemoteException {
//                    TopWiseDevice.this.getAidlCard().cancelCheckCard();
//                    TopWiseDevice.this.getCallback().invoke(new TransactionMonitor(DeviceState.READCARD_FAILED, "check card error " + i, true, (DebitCardRequestDto)null));
//                    TopWiseDevice.this.showResult("check card error " + i);
//                }
//            }));
//        } catch (RemoteException var6) {
//            var6.printStackTrace();
//            this.callback.invoke(new TransactionMonitor(DeviceState.READCARD_FAILED, "check card error " + var6.getMessage(), true, (DebitCardRequestDto)null));
//        }
//
//    }

//    private final Bundle getPinParam(byte pinType) {
//        Bundle bundle = new Bundle();
//        bundle.putInt("wkeyid", this.mWorkKeyIndex);
//        bundle.putInt("keytype", pinType);
//        bundle.putInt("inputtimes", 1);
//        bundle.putInt("minlength", 4);
//        bundle.putInt("maxlength", 12);
//        bundle.putString("pan", this.getPan(this.getTrack2()));
//        bundle.putString("tips", "RMB:2000.00");
//        bundle.putString("input_pin_mode", "0,4,5,6");
//        return bundle;
//    }

    private final String getSeqNum() {
        Log.i("MainPageActivity.TAG", "getSeqNum()");
        String[] seqNumTag = new String[]{"5F34"};
        byte[] seqNumTlvList = this.getTlv(seqNumTag);
        String cardSeqNum = "";
        if (seqNumTlvList != null) {
            String var10000 = BCDASCII.bytesToHexString(seqNumTlvList);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "BCDASCII.bytesToHexString(seqNumTlvList)");
            cardSeqNum = var10000;
            int var5 = cardSeqNum.length() - 2;
            int var6 = cardSeqNum.length();
            boolean var7 = false;
            if (cardSeqNum == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var10000 = cardSeqNum.substring(var5, var6);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            cardSeqNum = var10000;
        }

        Log.d("MainPageActivity.TAG", "setSeqNum : " + cardSeqNum);
        return cardSeqNum;
    }

//    private final String getTrack2() {
//        Log.i("MainPageActivity.TAG", "getTrack2()");
//        String[] track2Tag = new String[]{"57"};
//        byte[] track2TlvList = this.getTlv(track2Tag);
//        byte[] temp = new byte[track2TlvList.length - 2];
//        System.arraycopy(track2TlvList, 2, temp, 0, temp.length);
//        String var10001 = BCDASCII.bytesToHexString(temp);
//        Intrinsics.checkExpressionValueIsNotNull(var10001, "BCDASCII.bytesToHexString(temp)");
//        return this.processTrack2(var10001);
//    }

//    private final String processTrack2(String track) {
//        Log.i("MainPageActivity.TAG", "processTrack2()");
//        StringBuilder builder = new StringBuilder();
//        String subStr = (String)null;
//        String resultStr = (String)null;
//        int i = 0;
//
//        for(int var6 = track.length(); i < var6; ++i) {
//            int var8 = i + 1;
//            boolean var9 = false;
//            if (track == null) {
//                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
//            }
//
//            String var10000 = track.substring(i, var8);
//            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
//            subStr = var10000;
//            if (!String.endsWith$default(subStr, "F", false, 2, (Object)null)) {
//                builder.append(subStr);
//            }
//        }
//
//        resultStr = builder.toString();
//        return resultStr;
//    }

    private final String getConsume55() {
        Log.i("MainPageActivity.TAG", "getConsume55()");
        String[] consume55Tag = new String[]{"9F26", "9F27", "9F10", "9F37", "9F36", "95", "9A", "9C", "9F02", "5F2A", "5F34", "82", "9F1A", "9F03", "9F33", "84", "9F34", "9F35", "9F41"};
        byte[] consume55TlvList = this.getTlv(consume55Tag);
        String filed55 = BCDASCII.bytesToHexString(consume55TlvList);
        Log.d("MainPageActivity.TAG", "setConsume55 consume55TlvList : " + filed55);
        Intrinsics.checkExpressionValueIsNotNull(filed55, "filed55");
        return filed55;
    }

    private final byte[] getTlv(String[] tags) {
        StringBuilder sb = new StringBuilder();
        String[] var5 = tags;
        int var6 = tags.length;

        for(int var4 = 0; var4 < var6; ++var4) {
            String tag = var5[var4];
            EmvManager var10000 = this.aidlPboc;
            if (var10000 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("aidlPboc");
            }

            byte[] tempByte = var10000.getTlv(tag);
            String strResult = BCDASCII.bytesToHexString(tempByte);
            sb.append(strResult);
        }

        byte[] var9 = BCDASCII.hexStringToBytes(sb.toString());
        Intrinsics.checkExpressionValueIsNotNull(var9, "BCDASCII.hexStringToBytes(sb.toString())");
        return var9;
    }

    private final void closeLed() {
        DeviceManager var10000 = DeviceManager.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(var10000, "DeviceManager.getInstance()");
        AidlLed mAidlLed = var10000.getLedManager();

        try {
            if (mAidlLed != null) {
                mAidlLed.setLed(0, false);
            }
        } catch (RemoteException var3) {
            var3.printStackTrace();
        }

    }

    @NotNull
    public final String getAcquiringInstitutionIdCode(@Nullable String track2Data) {
        if (track2Data == null) {
            Intrinsics.throwNpe();
        }

        byte var3 = 0;
        byte var4 = 6;
        boolean var5 = false;
        if (track2Data == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String var10000 = track2Data.substring(var3, var4);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return var10000;
        }
    }

//    @NotNull
//    public final String getPan(@Nullable String track2Data) {
//        if (track2Data == null) {
//            Intrinsics.throwNpe();
//        }
//
//        int indexOfToken = StringsKt.indexOf$default((CharSequence)track2Data, "D", 0, false, 6, (Object)null);
//        byte var4 = 0;
//        boolean var5 = false;
//        String var10000 = track2Data.substring(var4, indexOfToken);
//        Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
//        return var10000;
//    }

//    @NotNull
//    public final String getExpiryDate(@Nullable String track2Data) {
//        if (track2Data == null) {
//            Intrinsics.throwNpe();
//        }
//
//        int indexOfToken = StringsKt.indexOf$default((CharSequence)track2Data, "D", 0, false, 6, (Object)null);
//        int indexOfExpiryDate = indexOfToken + 1;
//        int lengthOfExpiryDate = 4;
//        int var6 = indexOfExpiryDate + lengthOfExpiryDate;
//        boolean var7 = false;
//        String var10000 = track2Data.substring(indexOfExpiryDate, var6);
//        Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
//        return var10000;
//    }

//    @NotNull
//    public final String getServiceCode(@NotNull String track2Data) {
//        Intrinsics.checkParameterIsNotNull(track2Data, "track2Data");
//        int indexOfToken = StringsKt.indexOf$default((CharSequence)track2Data, "D", 0, false, 6, (Object)null);
//        int indexOfServiceCode = indexOfToken + 5;
//        int lengthOfServiceCode = 3;
//        int var6 = indexOfServiceCode + lengthOfServiceCode;
//        boolean var7 = false;
//        String var10000 = track2Data.substring(indexOfServiceCode, var6);
//        Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
//        return var10000;
//    }

//    @NotNull
//    public final String getTOPWISE_SERVICE_ACTION() {
//        return this.TOPWISE_SERVICE_ACTION;
//    }

//    public final void bindService(@NotNull Context context) {
//        Intrinsics.checkParameterIsNotNull(context, "context");
//        Log.d("TAG", "bindService: enter");
//        Intent intent = new Intent();
//        intent.setAction(this.TOPWISE_SERVICE_ACTION);
//        Intent eintent = new Intent(this.createExplicitFromImplicitIntent(context, intent));
//        boolean flag = context.bindService(eintent, this.conn, 1);
//        Log.d(this.TAG, "bindService: " + flag);
//        this.callback.invoke(new TransactionMonitor(DeviceState.PRINTER_BIND_SERVICE, "PRINTER_BIND_SERVICE", true, (DebitCardRequestDto)null));
//        if (flag) {
//            Log.d(this.TAG, "服务绑定成功");
//        } else {
//            Log.d(this.TAG, "服务绑定失败");
//        }
//
//        Log.d(this.TAG, "bindService: exit");
//    }

//    public final void unbindLibService(@NotNull Context context) {
//        Intrinsics.checkParameterIsNotNull(context, "context");
//        context.unbindService(this.conn);
//    }

    @NotNull
//    public final String getSerialNo() {
//        AidlSystem var10000 = systemService;
//        String var1;
//        if (var10000 != null) {
//            var1 = var10000.getSerialNo();
//            if (var1 != null) {
//                return var1;
//            }
//        }
//
//        var1 = "";
//        return var1;
//    }

    public final void onDestroy() {
        try {
            AidlCheckCard var10000 = this.aidlCard;
            if (var10000 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("aidlCard");
            }

            if (var10000 != null) {
                var10000 = this.aidlCard;
                if (var10000 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("aidlCard");
                }

                var10000.cancelCheckCard();
            }

            EmvManager var3 = this.aidlPboc;
            if (var3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("aidlPboc");
            }

            var3.endEmv();
        } catch (RemoteException var2) {
            var2.printStackTrace();
        }

    }

    @NotNull
    public final TextUnit getTextUnit(@NotNull String txt, boolean isLarge, boolean isCenter) {
        Intrinsics.checkParameterIsNotNull(txt, "txt");
        return isLarge ? new TextUnit(txt, 32, Align.CENTER) : (isCenter ? new TextUnit(txt, 22, Align.CENTER) : new TextUnit(txt, 22, Align.LEFT));
    }

    public final void printDoc(@NotNull PrintTemplate template) {
        Intrinsics.checkParameterIsNotNull(template, "template");
        Log.e("PRINTER", "3");

        try {
            template.add(new TextUnit("\n\n\n\n"));
            AidlPrinter var10000 = this.printerDev;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            var10000.addRuiImage(template.getPrintBitmap(), 0);
            var10000 = this.printerDev;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            var10000.printRuiQueue(this.mListen);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    @NotNull
    public final AidlPrinterListener getMListen() {
        return this.mListen;
    }

    public final void setMListen(@NotNull AidlPrinterListener var1) {
        Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
        this.mListen = var1;
    }

    @NotNull
    public final String getprintErrorInfo(int errorCode) {
        String var10000;
        switch (errorCode) {
            case 1:
                var10000 = "Out of paper, please load paper. error code：" + errorCode;
                break;
            case 2:
                var10000 = "Printing error, high temperature! error code:" + errorCode;
                break;
            case 3:
                var10000 = "Printing error, unknown error! error code:" + errorCode;
                break;
            case 4:
                var10000 = "Printing error, device not open! error code:" + errorCode;
                break;
            case 5:
                var10000 = "Printing error, the device is busy! error code:" + errorCode;
                break;
            case 6:
                var10000 = "Printing error, print bitmap width overflow! error code:" + errorCode;
                break;
            case 7:
                var10000 = "Printing error, print bitmap error! error code:" + errorCode;
                break;
            case 8:
                var10000 = "Printing error, printing barcode error! error code:" + errorCode;
                break;
            case 9:
                var10000 = "Printing error, parameter error! error code:" + errorCode;
                break;
            case 10:
                var10000 = "Print error, print text error! Error code:" + errorCode;
                break;
            case 11:
                var10000 = "Printing error, mac verification error! error code:" + errorCode;
                break;
            default:
                var10000 = "Printing error, unknown error! error code:" + errorCode;
        }

        return var10000;
    }

    @Nullable
    public final Intent createExplicitFromImplicitIntent(@NotNull Context context, @Nullable Intent implicitIntent) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        PackageManager pm = context.getPackageManager();
        List resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        Log.e("PRINTER", "1");
        if (resolveInfo != null && resolveInfo.size() == 1) {
            Log.e("PRINTER", "2");
            ResolveInfo serviceInfo = (ResolveInfo)resolveInfo.get(0);
            String packageName = serviceInfo.serviceInfo.packageName;
            String className = serviceInfo.serviceInfo.name;
            ComponentName component = new ComponentName(packageName, className);
            Intent explicitIntent = new Intent(implicitIntent);
            explicitIntent.setComponent(component);
            return explicitIntent;
        } else {
            return null;
        }
    }

    private final String getCurTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String var10000 = format.format(date);
        Intrinsics.checkExpressionValueIsNotNull(var10000, "format.format(date)");
        return var10000;
    }

    private final void showResult(String tip) {
        Log.e("MainPageActivity.TAG", tip);
        Timber.e(tip, new Object[0]);
    }

    private final void showMessage(String tip) {
        Timber.e(tip, new Object[0]);
    }

    @Nullable
    public final Boolean isPrinter() {
        return this.isPrinter;
    }

    @Nullable
    public final Boolean isSystemService() {
        return this.isSystemService;
    }

    @NotNull
    public final TransactionMonitor getCallback() {
        return this.callback;
    }

//    public TopWiseDevice(@NotNull Context activity, @Nullable Boolean isPrinter, @Nullable Boolean isSystemService, @NotNull TransactionMonitor callback) {
//        super();
//        Intrinsics.checkParameterIsNotNull(activity, "activity");
//        Intrinsics.checkParameterIsNotNull(callback, "callback");
//        this.isPrinter = isPrinter;
//        this.isSystemService = isSystemService;
//        this.callback = callback;
//        this.DIALOG_EXIT_APP = 100;
//        this.SEARCH_CARD_TIME = 30000;
//        this.activity = activity;
////
//        try {
//            Boolean var10000 = this.isPrinter;
//            if (var10000 == null) {
//                Intrinsics.throwNpe();
//            }
//
//            if (!var10000) {
//                var10000 = this.isSystemService;
//                if (var10000 == null) {
//                    Intrinsics.throwNpe();
//                }
//
//                if (!var10000) {
//                    EmvManager var10001 = EmvManager.getInstance();
//                    Intrinsics.checkExpressionValueIsNotNull(var10001, "EmvManager.getInstance()");
//                    this.aidlPboc = var10001;
//                    EmvDeviceManager var8 = EmvDeviceManager.getInstance();
//                    Intrinsics.checkExpressionValueIsNotNull(var8, "EmvDeviceManager.getInstance()");
//                    AidlCheckCard var9 = var8.getDetectCardManager();
//                    Intrinsics.checkExpressionValueIsNotNull(var9, "EmvDeviceManager.getInstance().detectCardManager");
//                    this.aidlCard = var9;
//                }
//            }
//        } catch (Exception var6) {
//            this.callback.invoke(new TransactionMonitor(DeviceState.FAILED, "device error " + var6.getMessage(), true, (DebitCardRequestDto)null));
//        } catch (IllegalStateException var7) {
//            this.callback.invoke(new TransactionMonitor(DeviceState.FAILED, "device error " + var7.getMessage(), true, (DebitCardRequestDto)null));
//        }
//
//        this.TAG = "TPW-TestPrintActivity";
//        this.TOPWISE_SERVICE_ACTION = "topwise_cloudpos_device_service";
//        this.conn = (ServiceConnection)(new ServiceConnection() {
//            public void onServiceConnected(@NotNull ComponentName name, @NotNull IBinder serviceBinder) {
//                Intrinsics.checkParameterIsNotNull(name, "name");
//                Intrinsics.checkParameterIsNotNull(serviceBinder, "serviceBinder");
//                Log.d("TAG", "aidlService服务连接成功");
//                Log.e("SEERE", "DDDDD");
//                AidlDeviceService serviceManager = Stub.asInterface(serviceBinder);
//
//                try {
//                    TopWiseDevice var10000;
//                    if (Intrinsics.areEqual(TopWiseDevice.this.isPrinter(), true)) {
//                        var10000 = TopWiseDevice.this;
//                        Intrinsics.checkExpressionValueIsNotNull(serviceManager, "serviceManager");
//                        var10000.printerDev = com.topwise.cloudpos.aidl.printer.AidlPrinter.Stub.asInterface(serviceManager.getPrinter());
//                        TopWiseDevice.this.getCallback().invoke(new TransactionMonitor(DeviceState.PRINTER_SERVICE_CONNECTION, "PRINTER_SERVICE_CONNECTION", true, (DebitCardRequestDto)null));
//                    } else {
//
//                        TransactionMonitor var10001;
////                        DeviceState var10003;
//                        var10000 = TopWiseDevice.this;
//                        Intrinsics.checkExpressionValueIsNotNull(serviceManager, "serviceManager");
////                        var10000.systemService = asInterface(serviceManager.getSystemService());
//                        Function1 var6 = TopWiseDevice.this.getCallback();
////                        TransactionMonitor var10001 = new TransactionMonitor;
//                        DeviceState var10003 = DeviceState.DEVICE_INFO;
//                        AidlSystem var10004 = TopWiseDevice.this.systemService;
//                        if (var10004 == null) {
//                            Intrinsics.throwNpe();
//                        }
//
//                        String var7 = var10004.getSerialNo();
//                        Intrinsics.checkExpressionValueIsNotNull(var7, "systemService!!.serialNo");
////                        var10001.<init>(var10003, var7, true, (DebitCardRequestDto)null);
////                        var6.invoke(var10001);
//                    }
//                } catch (RemoteException var5) {
//                    var5.printStackTrace();
//                }
//
//                Log.e("SEERE", "hghghg");
//            }
//
//            public void onServiceDisconnected(@NotNull ComponentName name) {
//                Intrinsics.checkParameterIsNotNull(name, "name");
//                Log.d("TAG", "AidlService服务断开了");
//            }
//        });
//        this.mListen = (AidlPrinterListener)(new AidlPrinterListener.Stub() {
//            public void onError(int i) throws RemoteException {
//                TopWiseDevice.this.showMessage("PRINTER ERROR" + i);
//                TopWiseDevice.this.getCallback().invoke(new TransactionMonitor(DeviceState.PRINTER_ERROR, TopWiseDevice.this.getprintErrorInfo(i), true, (DebitCardRequestDto)null));
//            }
//
//            public void onPrintFinish() throws RemoteException {
//                String endTime = TopWiseDevice.this.getCurTime();
//                TopWiseDevice.this.showMessage("END TIME" + endTime);
//                TopWiseDevice.this.getCallback().invoke(new TransactionMonitor(DeviceState.PRINTER_SUCCESS, "PRINTER_SUCCESS", true, (DebitCardRequestDto)null));
//            }
//        });
//    }

    public final AidlPrinter getPrinterDev() {
        return this.printerDev;
    }

//    @Metadata(
//            mv = {1, 1, 16},
//            bv = {1, 0, 3},
//            k = 1,
//            d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0016J+\u0010\u000b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016¢\u0006\u0002\u0010\u0010J(\u0010\u0011\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u0007H\u0016J\u001b\u0010\u0013\u001a\u00020\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00140\u000eH\u0016¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0004H\u0016J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0016J#\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0016¢\u0006\u0002\u0010\u001aJ\b\u0010\u001b\u001a\u00020\u0004H\u0016J\u0010\u0010\u001c\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0007H\u0016J\u0010\u0010\"\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0007H\u0016J\u0018\u0010#\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\n2\u0006\u0010%\u001a\u00020\u0007H\u0016¨\u0006&"},
//            d2 = {"Lcom/topwise/library/activity/TopWiseDevice$EmvListener;", "Lcom/topwise/sdk/emv/OnEmvProcessListener;", "(Lcom/topwise/library/activity/TopWiseDevice;)V", "finalAidSelect", "", "onConfirmCardInfo", "cardNo", "", "onError", "arg0", "", "onReadCardLoadLog", "arg1", "arg2", "", "Lcom/topwise/cloudpos/aidl/emv/PCardLoadLog;", "(Ljava/lang/String;Ljava/lang/String;[Lcom/topwise/cloudpos/aidl/emv/PCardLoadLog;)V", "onReadCardOffLineBalance", "arg3", "onReadCardTransLog", "Lcom/topwise/cloudpos/aidl/emv/PCardTransLog;", "([Lcom/topwise/cloudpos/aidl/emv/PCardTransLog;)V", "onRequestOnline", "onTransResult", "requestAidSelect", "times", "(I[Ljava/lang/String;)V", "requestEcashTipsConfirm", "requestImportAmount", "requestImportPin", "type", "lastFlag", "", "amount", "requestTipsConfirm", "requestUserAuth", "certType", "certno", "app_release"}
//    )
//    public final class EmvListener implements OnEmvProcessListener {
//        public void requestImportAmount(int arg0) throws RemoteException {
//            Timber.e("requestImportAmount", new Object[0]);
//            TopWiseDevice.this.getAidlPboc().importAmount(TopWiseDevice.this.getTransactionAmount());
//        }
//
//        public void requestAidSelect(int times, @NotNull String[] arg1) throws RemoteException {
//            Intrinsics.checkParameterIsNotNull(arg1, "arg1");
//            TopWiseDevice.this.showResult("please choice application");
//            String str = "";
//            int i = 0;
//
//            for(int var5 = arg1.length; i < var5; ++i) {
//                str = str + arg1[i];
//            }
//
//            TopWiseDevice.this.getAidlPboc().importAidSelectRes(1);
//        }
//
//        public void finalAidSelect() throws RemoteException {
//            TopWiseDevice.this.showResult("finalAidSelect");
//            TopWiseDevice.this.getAidlPboc().setTlv("9F1A", BCDASCII.hexStringToBytes("0566"));
//            TopWiseDevice.this.getAidlPboc().setTlv("5F2A", BCDASCII.hexStringToBytes("0566"));
//            TopWiseDevice.this.getAidlPboc().setTlv("9f3c", BCDASCII.hexStringToBytes("0566"));
//            TopWiseDevice.this.getAidlPboc().importFinalAidSelectRes(true);
//        }
//
//        public void onConfirmCardInfo(@NotNull String cardNo) throws RemoteException {
//            Intrinsics.checkParameterIsNotNull(cardNo, "cardNo");
//            Log.v("MainPageActivity.TAG", "onConfirmCardInfo");
//            TopWiseDevice.this.showResult("Card No: " + cardNo);
//            TopWiseDevice.this.showResult("Confirm");
//            TopWiseDevice.this.getAidlPboc().importConfirmCardInfoRes(true);
//        }
//
//        @Override
//        public void requestImportPin(int type, boolean lasttimeFlag, String amt, int pinRetryTimes) throws RemoteException {
//
//        }
//
//        public void requestImportPin(int type, boolean lastFlag, @NotNull String amount) throws RemoteException {
//            Intrinsics.checkParameterIsNotNull(amount, "amount");
//            Log.v("MainPageActivity.TAG", "requestImportPin");
//            TopWiseDevice.this.showResult("please input Pin");
//            TopWiseDevice.this.getCallback().invoke(new TransactionMonitor(DeviceState.INPUT_PIN, "Input PIN", true, (DebitCardRequestDto)null));
//            byte pinTypex = false;
//            byte pinType = type == 3 ? 0 : 1;
//            Bundle bundle = TopWiseDevice.this.getPinParam((byte)pinType);
//            TopWiseDevice var10000 = TopWiseDevice.this;
//            DeviceManager var10001 = DeviceManager.getInstance();
//            Intrinsics.checkExpressionValueIsNotNull(var10001, "DeviceManager.getInstance()");
//            AidlPinpad var7 = var10001.getPinpadManager();
//            Intrinsics.checkExpressionValueIsNotNull(var7, "DeviceManager.getInstance().pinpadManager");
//            var10000.setAidlPin(var7);
//            TopWiseDevice.this.getAidlPin().getPin(bundle, (GetPinListener)(TopWiseDevice.this.new MyGetPinListener()));
//        }
//
//        public void onRequestOnline() throws RemoteException {
//            TopWiseDevice.this.showResult("Request online");
//            String seqNum = TopWiseDevice.this.getSeqNum();
//            TopWiseDevice.this.showResult("seqNum " + seqNum);
//            String track2 = TopWiseDevice.this.getTrack2();
//            TopWiseDevice.this.showResult("track2 " + track2);
//            final String filed55Data = TopWiseDevice.this.getConsume55();
//            TopWiseDevice.this.showResult("filed55Data " + filed55Data);
//            new Date();
//            new SimpleDateFormat("MMddkkmmss", Locale.getDefault());
//            new SimpleDateFormat("kkmmss", Locale.getDefault());
//            String stan = StringsKt.takeLast(String.valueOf(System.currentTimeMillis()), 6);
//            String var9 = String.valueOf(System.currentTimeMillis());
//            boolean var10 = false;
//            if (var9 == null) {
//                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
//            } else {
//                String rrn = StringsKt.take(StringsKt.reversed((CharSequence)var9).toString(), 12);
//                final Ref.ObjectRef panSequenceNumber = new Ref.ObjectRef();
//                String var10001 = seqNum;
//                if (seqNum == null) {
//                    var10001 = "001";
//                }
//
//                panSequenceNumber.element = var10001;
//                Function0 var10000 = new Function0() {
//                    // $FF: synthetic method
//                    // $FF: bridge method
//                    public Object invoke() {
//                        this.invoke();
//                        return Unit.INSTANCE;
//                    }
//
//                    public final void invoke() {
//                        String var2 = filed55Data;
//                        boolean var3 = false;
//                        if (var2 == null) {
//                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
//                        } else {
//                            String var10000 = var2.toUpperCase();
//                            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toUpperCase()");
//                            int index = StringsKt.indexOf$default((CharSequence)var10000, "5F34", 0, false, 6, (Object)null);
//                            if (index >= 0) {
//                                var2 = filed55Data;
//                                int var8 = index + 6;
//                                int var4 = index + 8;
//                                Ref.ObjectRef var6 = panSequenceNumber;
//                                boolean var5 = false;
//                                if (var2 == null) {
//                                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
//                                }
//
//                                var10000 = var2.substring(var8, var4);
//                                Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
//                                String var7 = var10000;
//                                var6.element = var7;
//                            }
//
//                        }
//                    }
//                };
//
//            String getTrack2PanSequenceNumber1 = new String() {
//                    // $FF: synthetic method
//                    // $FF: bridge method
//                    public Object invoke() {
//                        return this.invoke();
//                    }
//
//                    @NotNull
//                    public final String invoke() {
//                        String var10000 = Utility.padLeft((String)panSequenceNumber.element, 3, '0');
//                        Intrinsics.checkExpressionValueIsNotNull(var10000, "Utility.padLeft(panSequenceNumber, 3, '0')");
//                        return var10000;
//                    }
//                };
//                Log.e("MainPageActivity.TAG", "pinblock1");
//                String decryptedStringPinBlock = TripleDES.threeDesDecrypt(TopWiseDevice.this.getGeneratedPinBlock(), "D55DEAF4850BA81610FB1A9101F8CE67");
//                Log.e("MainPageActivity.TAG", "pinblock2");
//                String cardPan = TopWiseDevice.this.getPan(track2);
//                if (cardPan.length() >= 4) {
//                    int var16 = cardPan.length() - 4;
//                    boolean var17 = false;
//                    if (cardPan == null) {
//                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
//                    }
//
//                    Intrinsics.checkExpressionValueIsNotNull(cardPan.substring(var16), "(this as java.lang.String).substring(startIndex)");
//                } else {
//                    String var66 = "";
//                }
//
//                Function1 var68 = TopWiseDevice.this.getCallback();
//                DeviceState var69 = DeviceState.INFO;
//                String var10011 = TopWiseDevice.this.getExpiryDate(track2);
////                String var10013 = $fun$getTrack2PanSequenceNumber1$2.invoke();
//                String var10014 = TopWiseDevice.this.getServiceCode(track2);
//                String var10019 = TopWiseDevice.this.getAcquiringInstitutionIdCode(track2);
//                String var15 = TopWiseDevice.this.getTransactionAmount();
//                String var41 = var10019;
//                String var40 = "CUSTOMER";
//                String var39 = "N/A";
//                String var38 = "N/A";
//                String var37 = "566";
//                String var36 = var10014;
////                String var35 = var10013;
//                String var34 = "N/A";
//                String var33 = var10011;
//                String var32 = "510101511344101";
//                boolean var23 = true;
//                String var22 = "Card read successfully";
//                DeviceState var21 = var69;
//                Function1 var18 = var68;
//                boolean var67 = false;
//                BigDecimal var42 = new BigDecimal(var15);
//                CardAccountType var43 = CardAccountType.SAVINGS;
//                DebitCardRequestDto var61 = new DebitCardRequestDto(cardPan, stan, rrn, decryptedStringPinBlock, filed55Data, track2, var32, var33, var34, var35, var36, var37, var38, var39, var40, var41, var42, var43);
//                var18.invoke(new TransactionMonitor(var21, var22, var23, var61));
//                TopWiseDevice.this.getAidlPboc().importOnlineResp(true, "00", (String)null);
//            }
//        }
//
//        public void onTransResult(int arg0) throws RemoteException {
//            Log.e("MainPageActivity.TAG", "onTransResult(+" + arg0 + ')');
//            DeviceManager var10000 = DeviceManager.getInstance();
//            Intrinsics.checkExpressionValueIsNotNull(var10000, "DeviceManager.getInstance()");
//            var10000.getRFCard().close();
//            TopWiseDevice.this.closeLed();
//            switch (arg0) {
//                case 1:
//                    TopWiseDevice.this.showResult("Allow trading");
//                    break;
//                case 2:
//                    TopWiseDevice.this.showResult("Refuse to deal");
//                    break;
//                case 3:
//                    TopWiseDevice.this.getCallback().invoke(new TransactionMonitor(DeviceState.READCARD_FAILED, "Card reading failed, try again.", true, (DebitCardRequestDto)null));
//                    TopWiseDevice.this.showResult("Stop trading");
//                    break;
//                case 4:
//                    TopWiseDevice.this.showResult("Downgrade");
//                    break;
//                case 5:
//                case 6:
//                    TopWiseDevice.this.getCallback().invoke(new TransactionMonitor(DeviceState.READCARD_FAILED, "Unknown exception, try again.", true, (DebitCardRequestDto)null));
//                    TopWiseDevice.this.showResult("Unknown exception");
//                    break;
//                default:
//                    TopWiseDevice.this.getCallback().invoke(new TransactionMonitor(DeviceState.READCARD_FAILED, "Unknown exception, try again.", true, (DebitCardRequestDto)null));
//                    TopWiseDevice.this.showResult("Unknown exception");
//            }
//
//        }
//
//        public void requestUserAuth(int certType, @NotNull String certno) throws RemoteException {
//            Intrinsics.checkParameterIsNotNull(certno, "certno");
//            TopWiseDevice.this.showResult("requestUserAuth");
//            TopWiseDevice.this.getAidlPboc().importUserAuthRes(true);
//        }
//
//        @Override
//        public void onRequestOnline() throws RemoteException {
//
//        }
//
//        public void requestTipsConfirm(@NotNull String arg0) throws RemoteException {
//            Intrinsics.checkParameterIsNotNull(arg0, "arg0");
//            TopWiseDevice.this.showResult("requestTipsConfirm");
//            TopWiseDevice.this.getAidlPboc().importMsgConfirmRes(true);
//        }
//
//        public void requestEcashTipsConfirm() throws RemoteException {
//            TopWiseDevice.this.showResult("requestEcashTipsConfirm");
//            TopWiseDevice.this.getAidlPboc().importECashTipConfirmRes(false);
//        }
//
//        public void onReadCardTransLog(@NotNull PCardTransLog[] arg0) throws RemoteException {
//            Intrinsics.checkParameterIsNotNull(arg0, "arg0");
//        }
//
//        public void onReadCardOffLineBalance(@NotNull String arg0, @NotNull String arg1, @NotNull String arg2, @NotNull String arg3) throws RemoteException {
//            Intrinsics.checkParameterIsNotNull(arg0, "arg0");
//            Intrinsics.checkParameterIsNotNull(arg1, "arg1");
//            Intrinsics.checkParameterIsNotNull(arg2, "arg2");
//            Intrinsics.checkParameterIsNotNull(arg3, "arg3");
//        }
//
//        public void onReadCardLoadLog(@NotNull String arg0, @NotNull String arg1, @NotNull PCardLoadLog[] arg2) throws RemoteException {
//            Intrinsics.checkParameterIsNotNull(arg0, "arg0");
//            Intrinsics.checkParameterIsNotNull(arg1, "arg1");
//            Intrinsics.checkParameterIsNotNull(arg2, "arg2");
//        }
//
//        public void onError(int arg0) throws RemoteException {
//            TopWiseDevice.this.showResult("onError " + arg0);
//            Log.v("MainPageActivity.TAG", "onError " + arg0);
//            TopWiseDevice.this.getAidlPboc().endEmv();
//            if (TopWiseDevice.this.getAidlCard() != null) {
//                TopWiseDevice.this.getAidlCard().cancelCheckCard();
//            }
//
//        }
//    }

//    @Metadata(
//            mv = {1, 1, 16},
//            bv = {1, 0, 3},
//            k = 1,
//            d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0004H\u0016¨\u0006\u0010"},
//            d2 = {"Lcom/topwise/library/activity/TopWiseDevice$MyGetPinListener;", "Lcom/topwise/cloudpos/aidl/pinpad/GetPinListener$Stub;", "(Lcom/topwise/library/activity/TopWiseDevice;)V", "onCancelKeyPress", "", "onConfirmInput", "arg0", "", "onError", "errorCode", "", "onInputKey", "len", "arg1", "", "onStopGetPin", "app_release"}
//    )
//    public final class MyGetPinListener extends GetPinListener.Stub {
//        public void onStopGetPin() throws RemoteException {
//            Timber.e("onStopGetPin ", new Object[0]);
//        }
//
//        public void onInputKey(int len, @NotNull String arg1) throws RemoteException {
//            Intrinsics.checkParameterIsNotNull(arg1, "arg1");
//            Log.v("MainPageActivity.TAG", "onInputKey len " + len + " arg1 " + arg1);
//            Timber.e("input pin " + arg1, new Object[0]);
//            TopWiseDevice.this.getCallback().invoke(new TransactionMonitor(DeviceState.PIN_DATA, String.valueOf(arg1), true, (DebitCardRequestDto)null));
//        }
//
//        public void onError(int errorCode) throws RemoteException {
//            Log.v("MainPageActivity.TAG", "onError " + errorCode);
//            Timber.e("input error code " + errorCode, new Object[0]);
//        }
//
//        public void onConfirmInput(@NotNull byte[] arg0) throws RemoteException {
//            Intrinsics.checkParameterIsNotNull(arg0, "arg0");
//            Log.v("MainPageActivity.TAG", "input success");
//            Log.e("ADFASDFSADF", HexUtil.bcd2str(arg0) + " =====>");
//            Timber.e("get Pin " + HexUtil.bcd2str(arg0), new Object[0]);
//            TopWiseDevice var10000 = TopWiseDevice.this;
//            String var10001 = HexUtil.bcd2str(arg0);
//            Intrinsics.checkExpressionValueIsNotNull(var10001, "HexUtil.bcd2str(arg0)");
//            var10000.setGeneratedPinBlock(var10001);
//            TopWiseDevice.this.getAidlPboc().importPin(HexUtil.bcd2str(arg0));
//        }
//
//        public void onCancelKeyPress() throws RemoteException {
//            Log.v("MainPageActivity.TAG", "onCancelKeyPress ");
//            Timber.e("onCancelKeyPress ", new Object[0]);
//        }
//    }


}
