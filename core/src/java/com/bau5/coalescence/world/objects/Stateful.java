package com.bau5.coalescence.world.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Rick on 4/18/2016.
 */
public interface Stateful {
    TextureRegion getCurrentTexture();
    void reset();
}
