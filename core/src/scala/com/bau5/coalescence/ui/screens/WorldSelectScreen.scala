package com.bau5.coalescence.ui.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.bau5.coalescence.{GameStage, Main}
import com.bau5.coalescence.ui.elements.GameButton
import com.bau5.coalescence.world.Maps

/**
  * Created by Rick on 5/2/2016.
  */
class WorldSelectScreen(main: Main) extends BaseScreen(main, new Stage) {
  val table = new Table
  table.add(GameButton.MainMenu(main))
  table.row()
  table.row()
  for (i <- Maps.values()) {
    table.add(new GameButton(i.name(), GameButton.Skin)(() => main.switchToScreen(new GameScreen(main, i))))
    table.row()
  }

  table.setPosition(stage.getViewport.getScreenWidth / 2, stage.getViewport.getScreenHeight / 2)
  stage.addActor(table)
}
