package com.crcc.exam.util;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CommonUtil {
    public static String listToString(List<String> list, String separator) {
        StringBuffer buffer = new StringBuffer();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    buffer.append(list.get(i));
                } else {
                    buffer.append(list.get(i));
                    buffer.append(separator);
                }
            }
        }

        return buffer.toString();
    }
}
