package com.thd.base.exception;

public class TempLockedException extends RuntimeException{
    public TempLockedException(String message) {super(message);}
    public TempLockedException(String message, Exception exception) {super(message, exception);}
}
