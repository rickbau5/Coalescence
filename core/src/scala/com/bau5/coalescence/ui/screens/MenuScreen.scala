package com.bau5.coalescence.ui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.{Color, GL20, Texture}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.bau5.coalescence.ui.elements.{GameButton, GameLabel}
import com.bau5.coalescence.world.Maps
import com.bau5.coalescence.{Main, SoundManager}

/**
  * Created by Rick on 5/2/2016.
  */
class MenuScreen(main: Main) extends BaseScreen(main, new Stage(new GameViewport())) {
  val background = new TextureRegion(new Texture(Gdx.files.internal("textures/background.png")))
  val white = new Color(1f, 1f, 1f, 1f)
  val black = new Color(0f, 0f, 0f, 0f)
  var lerpTo = black

  val table = new Table
  table.add(new GameButton("Start Game", GameButton.Skin)(() => main.switchToScreen(new GameScreen(main, Maps.One))))
  table.row()
  table.add(new GameButton("Information", GameButton.Skin)(() => main.switchToScreen(new InfoScreen(main))))
  table.row()
  table.add(GameButton.SelectWorld(main))
  table.row()
  table.add(GameButton.Exit)

  stage.addActor(table)
  centerTable(table)

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    if (font.getColor.equals(black)) {
      lerpTo = white
    } else if (font.getColor.equals(white)) {
      lerpTo = black
    }
    font.getColor.lerp(lerpTo, delta)
    stage.getBatch.begin()
    stage.getBatch.draw(background, 0, 0, 480, 480)
    font.draw(stage.getBatch, "Coalescence", getWidth / 7 * 3 - 6, getHeight / 4 * 3)
    stage.getBatch.end()

    stage.act(delta)
    stage.draw()
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
    font.setColor(Color.WHITE)
    super.dispose()
  }
}
