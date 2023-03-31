package com.example.top.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static<F, T> T map(F fromObj, T toObj) {
        BeanUtils.copyProperties(fromObj, toObj);
        return toObj;
    }

    public static<F, T> ArrayList<T> mapList(List<F> listOfFromObj, T toObj) {
        var list = new ArrayList<T>();
        for (var obj : listOfFromObj) {
            var mappedObj = map(obj, toObj);
            list.add(mappedObj);
        }

        return list;
    }
}
