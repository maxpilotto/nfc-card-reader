package com.maxpilotto.nfccardreader.reader;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcA;
import android.support.annotation.NonNull;

/**
 * Create by Max Pilotto on 18/05/2019
 * github.com/maxpilotto
 */
public class NfcCardReader {
    private static final IntentFilter[] INTENT_FILTER = new IntentFilter[]{
            new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED),
            new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
    };
    private static final String[][] TECH_LIST = new String[][]{{
            NfcA.class.getName(),
            IsoDep.class.getName()
    }};
    public final static String A_TAG = "TAG: Tech [android.nfc.tech.IsoDep, android.nfc.tech.NfcA]";
    public final static String B_TAG = "TAG: Tech [android.nfc.tech.IsoDep, android.nfc.tech.NfcB]";
    public static final int REQUEST_CODE = 94125;

    public enum Error {
        UNKNOWN_EMV_CARD,
        READ_ERROR
    }

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private NfcCardListener cardListener;
    private Activity activity;

    /**
     * Creates a NfcCardReader instance
     *
     * @param activity     Activity that will handle the scan
     * @param cardListener Scan listener
     */
    public NfcCardReader(@NonNull Activity activity, @NonNull NfcCardListener cardListener) {
        this.activity = activity;
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        this.cardListener = cardListener;

        Intent intent = new Intent(activity, activity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        this.pendingIntent = PendingIntent.getActivity(
                activity,
                REQUEST_CODE,
                intent,
                0
        );
    }

    /**
     * Returns whether or not the adapter is enabled or not (On/Off
     *
     * @return True if the adapter is turned on/enabled, False otherwise
     */
    public boolean isAdapterEnabled() {
        return nfcAdapter != null && nfcAdapter.isEnabled();
    }

    /**
     * Returns whether the adapter is available or not
     * If the adapter is not available it means that the device doesn't support it
     *
     * @return True if the adapter is available, False otherwise
     */
    public boolean isAdapterAvailable() {
        return nfcAdapter != null;
    }

    /**
     * Disables the foreground dispatch
     * This should be called inside the Activity's onPause method
     */
    public void disableDispatch() {
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(activity);
        }
    }

    /**
     * Enables the foreground dispatch
     * This should be called inside the Activity's onResume method
     */
    public void enableDispatch() {
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(
                    activity,
                    pendingIntent,
                    INTENT_FILTER,
                    TECH_LIST
            );
        }
    }

    /**
     * Parses the NFC data stored inside the intent
     * This method will run all the tasks in background and
     * when the results are ready you can retrieve them using the {@link NfcCardListener} interface
     *
     * @param intent Intent obtained from Activity's onNewIntent method
     */
    public void parse(Intent intent) {
        boolean hasExtra = intent.hasExtra(NfcAdapter.EXTRA_TAG);

        if (isAdapterAvailable() && isAdapterEnabled() && hasExtra){
            new NfcCardParser(intent, cardListener);
        }
    }
}
