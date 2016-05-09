package com.bau5.coalescence.ui.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.bau5.coalescence.ui.elements.GameButton
import com.bau5.coalescence.world.Maps
import com.bau5.coalescence.{GameStage, Main, SoundManager}

/**
  * Created by Rick on 5/2/2016.
  */
class MenuScreen(main: Main) extends BaseScreen(main, new Stage(new GameViewport())) {
  val white = new Color(1f, 1f, 1f, 1f)
  val black = new Color(0f, 0f, 0f, 0f)
  var lerpTo = black

  val table = new Table
  table.add(new GameButton("Start Game", GameButton.Skin)(() => main.switchToScreen(new GameScreen(main, Maps.One))))
  table.row()
  table.add(GameButton.SelectWorld(main))
  table.row()
  table.add(GameButton.Exit)

  stage.addActor(table)
  centerTable(table)

  override def render(delta: Float): Unit = {
    super.render(delta)

    stage.getBatch.begin()
    if (font.getColor.equals(black)) {
      lerpTo = white
    } else if (font.getColor.equals(white)) {
      lerpTo = black
    }

    font.getColor.lerp(lerpTo, delta)
    font.draw(stage.getBatch, "Coalescence", getWidth / 7 * 3, getHeight / 4 * 3)
    stage.getBatch.end()
  }

  override def initialize(): Unit = {
    super.initialize()

    SoundManager.instance.playMusic("theme")
  }

  override def resize(width: Int, height: Int): Unit = {
    super.resize(width, height)
    centerTable(table)
  }

  override def dispose(): Unit = {
    super.dispose()
  }
}
