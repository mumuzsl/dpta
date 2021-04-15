package com.cqjtu.dpta.common.exception;

/**
 * author: mumu
 * date: 2021/4/14
 */
public class OptionException extends RuntimeException {

    protected OptionException() {
        super("意料之外的option", null, false, false);
    }
}
