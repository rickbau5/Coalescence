package com.bau5.coalescence.entities.actions;

import com.bau5.coalescence.world.objects.TriggerObject;

/**
 * Created by Rick on 4/18/2016.
 */
public class TriggerObjectAction extends Action {
    private final TriggerObject triggerObject;

    public TriggerObjectAction(TriggerObject triggerObject) {
        this.triggerObject = triggerObject;

        setRecordedTime(triggerObject.world.getWorldStep());
    }

    @Override
    public void execute() {
        triggerObject.replayActivateTrigger();
    }
}
