package com.maxpilotto.nfccardreader.reader;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.AsyncTask;

import com.maxpilotto.nfccardreader.model.EmvCard;
import com.maxpilotto.nfccardreader.parser.EmvParser;

import org.apache.commons.lang3.StringUtils;

/**
 * Create by Max Pilotto on 18/05/2019
 * github.com/maxpilotto
 */
public class NfcCardParser extends AsyncTask<Void, Void, EmvCard> {
    private NfcCardListener listener;
    private Tag tag;

    public NfcCardParser(Intent data, NfcCardListener listener) {
        this.tag = data.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        this.listener = listener;

        if (tag != null) {
            if (tag.toString().equals(NfcCardReader.A_TAG) || tag.toString().equals(NfcCardReader.B_TAG)) {
                execute();
            }
        }
    }

    @Override
    protected EmvCard doInBackground(final Void... params) {
        try {
            IsoDep mIsoDep = IsoDep.get(tag);
            Provider provider = new Provider();
            EmvParser parser;

            if (mIsoDep == null) {
                listener.onReaderError(NfcCardReader.Error.READ_ERROR);
                return null;
            }

            mIsoDep.connect();

            provider.setTag(mIsoDep);

            parser = new EmvParser(provider, true);

            return parser.readEmvCard();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(EmvCard card) {
        if (card != null) {
            if (StringUtils.isNotBlank(card.getCardNumber())) {
                listener.onReadComplete(card);
            } else if (card.isNfcLocked()) {
                listener.onReaderError(NfcCardReader.Error.READ_ERROR);
            }
        } else {
            listener.onReaderError(NfcCardReader.Error.UNKNOWN_EMV_CARD);
        }
    }
}
