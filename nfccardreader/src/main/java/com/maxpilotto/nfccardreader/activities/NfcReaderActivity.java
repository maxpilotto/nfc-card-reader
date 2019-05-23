package com.maxpilotto.nfccardreader.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.maxpilotto.nfccardreader.reader.NfcCardListener;
import com.maxpilotto.nfccardreader.reader.NfcCardReader;

/**
 * Create by Max Pilotto on 18/05/2019
 * <p>
 * github.com/maxpilotto
 */
public abstract class NfcReaderActivity extends AppCompatActivity implements NfcCardListener {
    private NfcCardReader reader;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reader = new NfcCardReader(this, this);
        reader.parse(getIntent());
    }

    @Override protected void onResume() {
        super.onResume();

        reader.enableDispatch();
    }

    @Override protected void onPause() {
        super.onPause();

        reader.disableDispatch();
    }

    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        reader.parse(intent);
    }

    public NfcCardReader getReader() {
        return reader;
    }
}
