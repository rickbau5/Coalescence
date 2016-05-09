package com.bau5.coalescence.ui.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.{GL20, Texture}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.bau5.coalescence.ui.elements.{GameButton, GameLabel}
import com.bau5.coalescence.{Main, SoundManager}

/**
  * Created by Rick on 5/9/16.
  */
class VictoryScreen(main: Main) extends BaseScreen(main, new Stage(new GameViewport())) {
  val background = new TextureRegion(new Texture(Gdx.files.internal("textures/background.png")))
  val table = new Table
  table.add(new GameLabel("You have achieved victory!"))
  table.row()
  table.add(new GameLabel(""))
  table.row()
  table.add(new GameLabel(""))
  table.row()
  table.add(GameButton.MainMenu(main))
  stage.addActor(table)
  centerTable(table)


  override def initialize(): Unit = {
    super.initialize()

    SoundManager.instance.playMusic("theme")
  }

  override def render(delta: Float): Unit = {
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    stage.getBatch.begin()
    stage.getBatch.draw(background, 0, 0, 480, 480)
    stage.getBatch.end()

    stage.act(delta)
    stage.draw()
  }
}
