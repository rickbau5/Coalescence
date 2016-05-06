package com.bau5.coalescence.ui.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bau5.coalescence.Constants;

/**
 * Created by Rick on 5/5/2016.
 */
public class GameViewport extends ScalingViewport {
    public GameViewport() {
        this(Scaling.fit, Constants.sizeX, Constants.sizeY);
    }

    public GameViewport(Scaling scaling, float worldWidth, float worldHeight) {
        super(scaling, worldWidth, worldHeight, new OrthographicCamera(worldWidth, worldHeight));

        getCamera().setToOrtho(false, Constants.sizeX, Constants.sizeY);
        getCamera().update();
    }

    @Override
    public OrthographicCamera getCamera() {
        return ((OrthographicCamera) super.getCamera());
    }
}
