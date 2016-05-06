package com.bau5.coalescence.ui.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.bau5.coalescence.ui.elements.GameButton
import com.bau5.coalescence.world.Maps
import com.bau5.coalescence.{Main, SoundManager}

/**
  * Created by Rick on 5/2/2016.
  */
class MenuScreen(main: Main) extends BaseScreen(main, new Stage(new GameViewport())) {
  val table = new Table
  table.add(new GameButton("Start Game", GameButton.Skin)(() => main.switchToScreen(new GameScreen(main, Maps.One))))
  table.row()
  table.add(GameButton.SelectWorld(main))
  table.row()
  table.add(GameButton.Exit)

  stage.addActor(table)
  centerTable(table)


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
