package com.bau5.coalescence

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.{InputEvent, InputListener}

/**
  * Created by Rick on 4/1/16.
  */
class InputHandler(stage: GameStage) extends InputListener {
  private val mouseLocation = new Vector2()

  override def mouseMoved(event: InputEvent, x: Float, y: Float): Boolean = {
    mouseLocation.set(x, y)
    true
  }

  override def touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean = {
    val pos = stage.testEntity.getComponent[PositionComponent](classOf[PositionComponent])
    val trans = stage.stageToMapCoordinates(new Vector2(x, y))
    pos.x = trans.x
    pos.y = trans.y
    true
  }

  def getMouseLocation: Vector2 = mouseLocation.cpy()
}
