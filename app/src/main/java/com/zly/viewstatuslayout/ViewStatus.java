package com.zly.viewstatuslayout;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Cerated by xiaoyehai
 * Create date : 2020/11/11 14:32
 * description :
 */
@IntDef({ViewStatus.EMPTY,ViewStatus.HASDATA, ViewStatus.NODATA,
        ViewStatus.NONETWORK, ViewStatus.LOADING_START, ViewStatus.LOADING_END})
@Retention(RetentionPolicy.SOURCE)
public @interface ViewStatus {
    int HASDATA = 0x01;
    int NODATA = 0x02;
    int NONETWORK = 0x04;
    int LOADING_START = 0x08;
    int LOADING_END = 0x10;
    int EMPTY = 0x20;
}