package com.company.States;

import com.company.KBaseApp;
import com.company.KServer;
import com.esotericsoftware.kryonet.Connection;

/**
 * Created by diogo on 09.05.16.
 */
public interface IState {
    void handleState(KBaseApp context);
}
