package com.bau5.coalescence.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.bau5.coalescence.world.World;

/**
 * Created by Rick on 5/5/2016.
 */
public class Crate extends TiledMapObject implements Stateful, Destructible {
    private boolean intact = true;

    private TextureRegion brokenTexture;

    public Crate(World world, TextureMapObject mapObject) {
        super(world, mapObject);

        mapObject.setTextureRegion(new TextureRegion(new Texture(Gdx.files.internal("textures/objects/crate.png"))));
        this.brokenTexture = new TextureRegion(new Texture(Gdx.files.internal("textures/objects/crate_broken.png")));
    }

    @Override
    public TextureRegion getCurrentTexture() {
        return intact ? getMapObject().getTextureRegion() : brokenTexture;
    }

    @Override
    public void reset() {
        intact = true;
    }

    @Override
    public boolean isCollidable() {
        return intact;
    }

    @Override
    public void destroy() {
        intact = false;
        //SoundManager.instance.playSound("crate-destroy");
    }
}
