package com.mtoliv.iot.server.message;

public class ResponseMessage {
    private int intValue;

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "intValue=" + intValue +
                '}';
    }
}
