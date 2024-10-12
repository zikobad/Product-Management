package com.alten.product.management.utils;

import java.util.function.Consumer;

public class AppUtils {

    public static <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
