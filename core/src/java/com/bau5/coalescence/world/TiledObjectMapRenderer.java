package com.bau5.coalescence.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.bau5.coalescence.world.objects.Stateful;
import com.bau5.coalescence.world.objects.TiledMapObject;


/**
 * Created by Rick on 4/5/2016.
 */
public class TiledObjectMapRenderer extends OrthogonalTiledMapRenderer {
    private final World world;

    public TiledObjectMapRenderer(World world, float scale) {
        super(world.getMap(), scale);

        this.world = world;
    }

    @Override
    public void renderObjects(MapLayer layer) {
        if (layer != null) {
            for (TiledMapObject tiledMapObject : world.getTiledMapObjects()) {
                renderTiledMapObject(tiledMapObject);
            }
        }
    }

    public void renderTiledMapObject(TiledMapObject tiledMapObject) {
        TextureMapObject texturedObject = tiledMapObject.getMapObject();

        TextureRegion textureRegion;
        if (tiledMapObject instanceof Stateful) {
            textureRegion = ((Stateful) tiledMapObject).getCurrentTexture();
        } else {
            textureRegion = texturedObject.getTextureRegion();
        }
        batch.setColor(Color.WHITE);
        batch.draw(
                textureRegion,
                texturedObject.getX() * 2, texturedObject.getY() * 2,
                0f, 0f,
                16f, 16f,
                2f, 2f,
                360 - texturedObject.getRotation()
        );
    }
}
