package com.example.top.util;

import org.springframework.beans.BeanUtils;

import java.util.List;

public class Mapper {

    public static<F, T> T map(F fromObj, T toObj) {
        BeanUtils.copyProperties(fromObj, toObj);
        return toObj;
    }

    public static<F, T> void mapList(List<F> listOfFromObj, T toObj) {
        BeanUtils.copyProperties(listOfFromObj, toObj);
    }
}
