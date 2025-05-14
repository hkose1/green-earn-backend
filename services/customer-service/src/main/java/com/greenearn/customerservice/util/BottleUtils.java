package com.greenearn.customerservice.util;


import com.greenearn.customerservice.enums.BottleSizeType;

public class BottleUtils {

    private static final Integer SMALL_BOTTLE_POINT = 5;
    private static final Integer MEDIUM_BOTTLE_POINT = 10;
    private static final Integer LARGE_BOTTLE_POINT = 15;

    public static Integer getBottlePointByType(BottleSizeType bottleSizeType) {
        switch (bottleSizeType) {
            case SMALL:
                return SMALL_BOTTLE_POINT;
            case MEDIUM:
                return MEDIUM_BOTTLE_POINT;
            case LARGE:
                return LARGE_BOTTLE_POINT;
            default:
                return 0;
        }
    }
}
