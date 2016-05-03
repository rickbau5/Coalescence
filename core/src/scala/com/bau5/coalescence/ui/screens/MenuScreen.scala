package com.bau5.coalescence.ui.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.bau5.coalescence.Main
import com.bau5.coalescence.ui.elements.GameButton
import com.bau5.coalescence.world.Maps

/**
  * Created by Rick on 5/2/2016.
  */
class MenuScreen(main: Main) extends BaseScreen(main, new Stage) {
  val table = new Table
  table.add(new GameButton("Start Game", GameButton.Skin)(() => main.switchToScreen(new GameScreen(main, Maps.One))))
  table.row()
  table.add(new GameButton("Map Select", GameButton.Skin)(() => main.switchToScreen(new WorldSelectScreen(main))))
  table.row()
  table.add(GameButton.Exit)

  stage.addActor(table)
  table.setPosition(stage.getViewport.getScreenWidth / 2, stage.getViewport.getScreenHeight / 2)
}
