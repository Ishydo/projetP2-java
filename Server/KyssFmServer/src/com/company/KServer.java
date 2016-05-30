package com.company;

import com.company.States.ServerStates.ServerNewGameState;
import com.company.packets.*;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;

public class KServer extends KBaseApp {

    private Server server;
    private HashMap<String,EntityInfo> playersInfo = new HashMap<>();

    public static void main(String[] args) throws IOException {
	    new KServer(5555,5559);
    }

    public KServer(int tcpPort, int udpPort) throws IOException {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        init();
    }

    private void init() throws IOException {
        server = new Server();
        kryoSerializer = server.getKryo();
        kryoSerializer.register(String.class);
        kryoSerializer.register(BasePacket.class);
        kryoSerializer.register(blockChair.class);
        kryoSerializer.register(EntityInfo.class);
        kryoSerializer.register(onChairPacket.class);
        kryoSerializer.register(PlayersPosition.class);
        kryoSerializer.register(RoundInfo.class);
        kryoSerializer.register(StatePacket.class);
        kryoSerializer.register(EntityInfo[].class);
        kryoSerializer.register(StatePacket.states.class);
        server.start();
        server.bind(tcpPort, udpPort);
        endPoint = (EndPoint)server;
        run();
    }

    void run(){
        currentState = new ServerNewGameState();
        while(!stop){
            currentState.handleState(this);
            sleep();
        }
    }

    public HashMap<String, EntityInfo> getPlayersInfo() {
        return playersInfo;
    }

    public void setPlayersInfo(HashMap<String, EntityInfo> playersInfo) {
        this.playersInfo = playersInfo;
    }
}
