package com.bau5.coalescence.ui.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, Widget}
import com.bau5.coalescence.Main
import com.bau5.coalescence.ui.elements.{GameButton, GameLabel}

/**
  * Created by Rick on 5/8/2016.
  */
class InfoScreen(main: Main) extends BaseScreen(main, new Stage(new GameViewport())) {
  var table = new Table
  populateTable()

  stage.addActor(table)
  centerTable(table)

  def populateTable(): Unit = {
    addRow(new GameLabel("W-A-S-D: Movement keys"))
    addRow(new GameLabel("SPACE: Use Ability"))
    addRow(new GameLabel("R: Begin Replay"))
    addRow(new GameLabel("ESC: Pause/Menu"))
    addRow(new GameLabel(""))
    addRow(new GameLabel("To damage enemies, move into them."))
    addRow(new GameLabel("To restart the level, hit ESC then \"Reload\"."))
    addRow(new GameLabel("To progress, reach the ladder/door at the end of the level."))
    addRow(new GameLabel(""))
    table.add(GameButton.MainMenu(main))
    table.row()
  }

  private def addRow(widget: Widget): Unit = {
    table.add(widget)
    table.row()
  }
}
