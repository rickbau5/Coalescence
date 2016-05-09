package com.bau5.coalescence.ui.screens

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{Label, Table, Widget}
import com.bau5.coalescence.Main
import com.bau5.coalescence.ui.elements.GameButton

/**
  * Created by Rick on 5/8/2016.
  */
class InfoScreen(main: Main) extends BaseScreen(main, new Stage(new GameViewport())) {
  var table = new Table
  populateTable()

  stage.addActor(table)
  centerTable(table)

  def populateTable(): Unit = {
    addRow(new Label("W-A-S-D: Movement keys", GameButton.Skin, "labelStyle"))
    addRow(new Label("SPACE: Use Ability", GameButton.Skin, "labelStyle"))
    addRow(new Label("R: Begin Replay", GameButton.Skin, "labelStyle"))
    addRow(new Label("ESC: Pause/Menu", GameButton.Skin, "labelStyle"))
    addRow(new Label("", GameButton.Skin, "labelStyle"))

    addRow(new Label("To damage enemies, move into them.", GameButton.Skin, "labelStyle"))
    addRow(new Label("To restart the level, hit ESC then \"Reload\".", GameButton.Skin, "labelStyle"))
    addRow(new Label("To progress, reach the ladder/door at the end of the level.", GameButton.Skin, "labelStyle"))

    addRow(new Label("", GameButton.Skin, "labelStyle"))
    table.add(GameButton.MainMenu(main))
    table.row()
  }

  private def addRow(widget: Widget): Unit = {
    table.add(widget)
    table.row()
  }
}
