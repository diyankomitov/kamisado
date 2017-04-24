package com.team11.kamisado.util;

import com.team11.kamisado.controllers.GameController;

/**
 * Created by Diyan on 22/04/2017.
 */
public interface Mode {
    void move(GameController controller);
    String getName();
}
