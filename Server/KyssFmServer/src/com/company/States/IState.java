package com.company.States;

import com.company.Server;
import com.esotericsoftware.kryonet.Connection;

/**
 * Created by diogo on 09.05.16.
 */
public interface IState {

    void handleState(Server context, Object in, Connection connection);

}
