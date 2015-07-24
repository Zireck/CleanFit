package com.zireck.projectk.helper;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zireck on 24/07/2015.
 */
public class LimitedDecimalsInputFilter implements InputFilter {

    Pattern mPattern;

    public LimitedDecimalsInputFilter(int digitsBeforeZero,int digitsAfterZero) {
        mPattern=Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher=mPattern.matcher(dest);

        if(!matcher.matches())
            return "";

        return null;
    }
}
