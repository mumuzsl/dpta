package com.cqjtu.dpta.common.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * author: mumu
 * date: 2021/5/5
 */
@Data
public class XyVo {

    List<Object> xdata;

    List<Object> ydata;

    public static XyVo of(int size) {
        XyVo xyVo = new XyVo();
        xyVo.setXdata(new ArrayList<>(size));
        xyVo.setYdata(new ArrayList<>(size));
        return xyVo;
    }
}
