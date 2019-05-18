package com.maxpilotto.nfccardreaderdemo;

import android.os.Bundle;
import android.widget.Toast;

import com.maxpilotto.nfccardreader.activities.NfcReaderActivity;
import com.maxpilotto.nfccardreader.model.EmvCard;
import com.maxpilotto.nfccardreader.reader.NfcCardReader;

public class MainActivity extends NfcReaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public void onReaderError(NfcCardReader.Error error) {
        Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override public void onReadComplete(EmvCard card) {
        Toast.makeText(this,card.getCardNumber(),Toast.LENGTH_LONG).show();
    }
}
