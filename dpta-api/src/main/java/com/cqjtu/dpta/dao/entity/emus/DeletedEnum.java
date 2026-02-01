package com.cqjtu.dpta.dao.entity.emus;

/**
 * author: mumu
 * date: 2021/5/13
 */
public enum DeletedEnum {
    NOT_DELETE(0),
    DELETED(1);

    int value;

    DeletedEnum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
