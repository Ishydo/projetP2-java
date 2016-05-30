package com.company.packets;

/**
 * Created by diogo on 5/23/16.
 */
public class StatePacket extends BasePacket {

    public enum states {
        HELLO,
        READY,
        GO_TO_ON_STS,
        GO_TO_END_STS,
        GO_TO_NEW_STS,
    }

    public states state;

    public StatePacket(){}

    public StatePacket(states state) {
        super();
        this.state = state;
    }
}
