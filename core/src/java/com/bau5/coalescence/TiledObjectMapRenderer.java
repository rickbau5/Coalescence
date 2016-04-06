package com.bau5.coalescence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


/**
 * Created by Rick on 4/5/2016.
 */
public class TiledObjectMapRenderer extends OrthogonalTiledMapRenderer {
    public TiledObjectMapRenderer(TiledMap map, float scale) {
        super(map, scale);
    }

    @Override
    public void renderObject(MapObject object) {
        if (object instanceof TextureMapObject) {
            TextureMapObject texturedObject = ((TextureMapObject) object);
            batch.setColor(Color.WHITE);
            batch.draw(
                texturedObject.getTextureRegion(),
                texturedObject.getX() * 2, texturedObject.getY() * 2,
                0f, 0f,
                16f, 16f,
                2f, 2f,
                360 - texturedObject.getRotation()
            );
        }
    }
}
