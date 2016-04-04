package com.bau5.coalescence

import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.{InputEvent, InputListener}
import com.bau5.coalescence.entities.GameEntity.Direction
import com.bau5.coalescence.entities.actions.MoveAction

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
    val trans = stage.stageToMapCoordinates(new Vector2(x, y))
    stage.player.performAction(new MoveAction(trans), true)
    true
  }

  override def keyTyped(event: InputEvent, character: Char): Boolean = character match {
    // Player movement
    case 'w' =>
      stage.player.moveInDirection(Direction.Up)
      true
    case 'a' =>
      stage.player.moveInDirection(Direction.Left)
      true
    case 's' =>
      stage.player.moveInDirection(Direction.Down)
      true
    case 'd' =>
      stage.player.moveInDirection(Direction.Right)
      true

    // Force playback (debug)
    case 'r' =>
      stage.reset()
      true
    case 'p' =>
      stage.beginPlayback()
      true
    case _ => false
  }


  override def keyUp(event: InputEvent, keycode: Int): Boolean = keycode match {
    case Input.Keys.ESCAPE =>
      Gdx.app.exit()
      true;
    case _ => super.keyUp(event, keycode)
  }

  def getMouseLocation: Vector2 = mouseLocation.cpy()
}
