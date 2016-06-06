package networking.States;

import networking.KBaseApp;

/**
 * Created by diogo on 09.05.16.
 */
public interface IState {
    void handleState(KBaseApp context);
}
