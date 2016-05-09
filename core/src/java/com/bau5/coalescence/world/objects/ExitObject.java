package com.bau5.coalescence.world.objects;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.bau5.coalescence.SoundManager;
import com.bau5.coalescence.world.World;

/**
 * Created by Rick on 4/19/16.
 */
public class ExitObject extends ControlObject {
    private final String level;

    public ExitObject(World world, TextureMapObject mapObject) {
        super(world, mapObject);

        this.level = (String)mapObject.getProperties().get("level");
    }

    @Override
    public void executeControl() {
        SoundManager.instance.playSound("ladder");
        world.getStage().moveToLevel(level);
    }
}
