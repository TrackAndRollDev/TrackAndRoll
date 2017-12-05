package com.neos.trackandroll.utils;

import android.content.Context;

public class DimUtils {
    public static int dipToPixel(Context ctx, float dips) {
        return (int) (dips * ctx.getResources().getDisplayMetrics().density + 0.5f);
    }
}
