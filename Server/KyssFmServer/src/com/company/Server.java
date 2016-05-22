package com.company;

import com.company.States.IState;
import com.company.packets.RoundInfo;

import java.util.ArrayList;

public class Server {

    private IState currentState;
    private int tcpPort;
    private int udpPort;
    private ArrayList<RoundInfo> playedRounds;

    public static void main(String[] args) {
	    new Server(5555,5559);
    }

    public Server(int tcpPort, int udpPort) {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
    }

    public IState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(IState currentState) {
        this.currentState = currentState;
    }

    public ArrayList<RoundInfo> getPlayedRounds() {
        return playedRounds;
    }

    public void setPlayedRounds(ArrayList<RoundInfo> playedRounds) {
        this.playedRounds = playedRounds;
    }
}
