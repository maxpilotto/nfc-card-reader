package com.maxpilotto.nfccardreader.reader;

import android.nfc.tech.IsoDep;

import com.maxpilotto.nfccardreader.parser.IProvider;

/**
 * Create by Max Pilotto on 18/05/2019
 * github.com/maxpilotto
 */
public class Provider implements IProvider {
    private IsoDep tag;

    public IsoDep getTag() {
        return tag;
    }

    public void setTag(IsoDep tag) {
        this.tag = tag;
    }

    @Override
    public byte[] transceive(byte[] pCommand) {
        byte[] ret = null;

        try {
            ret = tag.transceive(pCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
