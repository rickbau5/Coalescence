package com.bau5.coalescence.entities.actions;

import com.bau5.coalescence.world.objects.triggers.TriggerableObject;

/**
 * Created by Rick on 5/3/2016.
 */
public class TriggeredAction extends Action {
    private final TriggerableObject triggeredObject;

    public TriggeredAction(TriggerableObject triggeredObject) {
        this.triggeredObject = triggeredObject;
    }

    @Override
    public void execute() {
        setRecordedTime(triggeredObject.world.getWorldStep());
        triggeredObject.trigger();
    }
}
