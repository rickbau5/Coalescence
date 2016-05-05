package com.bau5.coalescence.world.objects.triggers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.bau5.coalescence.SoundManager;
import com.bau5.coalescence.world.World;

/**
 * Created by Rick on 5/4/2016.
 */
public class RopeObject extends TriggerObject {
    public RopeObject(World world, TextureMapObject mapObject) {
        super(world, mapObject, new TextureRegion(new Texture(Gdx.files.internal("textures/rope_broken.png"))));
    }

    @Override
    public void onTrigger() {
        super.onTrigger();
        SoundManager.instance.playSound("rope-break");
    }
}
