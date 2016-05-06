package com.bau5.coalescence.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.bau5.coalescence.entities.events.DestructibleCollisionEvent;
import com.bau5.coalescence.world.World;

/**
 * Created by Rick on 5/6/2016.
 */
public class DestructibleObject extends TiledMapObject implements Stateful {
    private boolean intact = true;

    private TextureRegion brokenTexture;

    public DestructibleObject(World world, TextureMapObject mapObject, String intactTextureName, String brokenTextureName) {
        super(world, mapObject);

        mapObject.setTextureRegion(new TextureRegion(new Texture(Gdx.files.internal(String.format("textures/objects/%s.png", intactTextureName)))));
        this.brokenTexture = new TextureRegion(new Texture(Gdx.files.internal(String.format("textures/objects/%s.png", brokenTextureName))));
    }

    public void handleEntityCollision(DestructibleCollisionEvent event) {

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

    public void destroy() {
        intact = false;
        //SoundManager.instance.playSound("crate-destroy");
    }
}
