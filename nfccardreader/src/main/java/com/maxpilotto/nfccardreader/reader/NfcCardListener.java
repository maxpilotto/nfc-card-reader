package com.maxpilotto.nfccardreader.reader;

import com.maxpilotto.nfccardreader.model.EmvCard;

/**
 * Create by Max Pilotto on 18/05/2019
 * github.com/maxpilotto
 */
public interface NfcCardListener {
    /**
     * Called by the NfcCardReader class when an error has occurred
     * while parsing or reading the card
     *
     * @param error Error, see {@link NfcCardReader.Error}
     */
    void onReaderError(NfcCardReader.Error error);

    /**
     * Called by the NfcCardParser class when the read was successful
     *
     * @param card EmvCard instance from which data can be retrieved
     */
    void onReadComplete(EmvCard card);
}
