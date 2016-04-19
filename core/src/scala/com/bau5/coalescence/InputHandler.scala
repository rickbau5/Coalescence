package com.bau5.coalescence

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.{InputEvent, InputListener}
import com.badlogic.gdx.{Gdx, Input}
import com.bau5.coalescence.entities.{PlayableCharacter, ProjectileEntity}

import scala.collection.JavaConversions._

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

    val entity = Option(stage.getWorld.getEntityAt(trans))

    if (stage.getWorld.getActivePlayer == null) {
      entity foreach {
        case pl: PlayableCharacter => stage.getWorld.setActivePlayer(pl)
        case _ => ;
      }
    }

    true
  }

  override def keyTyped(event: InputEvent, character: Char): Boolean = {
    val handledMovement = if (!stage.isPaused && stage.getWorld.getActivePlayer != null) {
      character match {
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
        case _ =>
          false
      }
    } else {
      false
    }
    val handledOther = if (!handledMovement) {
      character match {
        // Reset all to beginning
        case 'r' =>
          stage.getWorld.replay()
          true

        case _ => false
      }
    } else false

    handledMovement || handledOther
  }

  override def keyUp(event: InputEvent, keycode: Int): Boolean = keycode match {
    case Input.Keys.ESCAPE =>
      Gdx.app.exit()
      true

    case Input.Keys.SPACE =>
      stage.togglePaused()
      true

    case _ => super.keyUp(event, keycode)
  }

  def getMouseLocation: Vector2 = mouseLocation.cpy()
}
