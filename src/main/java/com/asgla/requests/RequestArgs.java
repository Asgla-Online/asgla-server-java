package com.asgla.requests;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RequestArgs {

    private final List<String> args = new LinkedList<>();
    public int length;

    public static RequestArgs parse(String[] params) {
        RequestArgs args = new RequestArgs();

        for (String param : params) {
            if (NumberUtils.isParsable(param)) {
                try {
                    args.add(Integer.parseInt(param));
                } catch (NumberFormatException e) {
                    args.add(Float.parseFloat(param));
                }
            } else {
                args.add(param);
            }
        }

        args.length = args.list().size();

        return args;
    }

    public Integer getInt(int argIndex) {
        return Integer.parseInt(args.get(argIndex));
    }

    public String getStr(int argIndex) {
        return String.valueOf(args.get(argIndex));
    }

    public Float getFloat(int argIndex) {
        return Float.parseFloat(args.get(argIndex));
    }

    public String[] getArray(int argIndex) {
        return args.get(argIndex).split(",");
    }

    public List<String> list() {
        return Collections.unmodifiableList(args);
    }

    @Override
    public String toString() {
        return Arrays.toString(list().toArray());
    }

    private void add(Object argObj) {
        args.add(argObj.toString());
    }
}