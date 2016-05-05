package com.bau5.coalescence.ui.elements

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent
import com.bau5.coalescence.ui.screens.{BaseScreen, GameScreen, MenuScreen, WorldSelectScreen}
import com.bau5.coalescence.{GameStage, Main}


/**
  * Created by Rick on 5/2/2016.
  */

object GameButton {
  val Font = new BitmapFont()
  val Skin = {
    val skn = new Skin
    val pixmap: Pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888)
    pixmap.setColor(Color.WHITE)
    pixmap.fill()
    skn.add("white", new Texture(pixmap))
    skn.add("default", Font)
    val buttonStyle: TextButton.TextButtonStyle = new TextButton.TextButtonStyle
    buttonStyle.up = skn.newDrawable("white", Color.DARK_GRAY)
    buttonStyle.down = skn.newDrawable("white", Color.DARK_GRAY)
    buttonStyle.over = skn.newDrawable("white", Color.LIGHT_GRAY)
    buttonStyle.font = Font
    skn.add("default", buttonStyle)
    skn
  }

  def Exit = new GameButton("Exit", Skin)(() => Gdx.app.exit())
  def MainMenu(main: Main) = new GameButton("Main Menu", Skin)(() => main.switchToScreen(new MenuScreen(main)))
  def ReloadWorld(main: Main) = new GameButton("Reload", Skin)(() => main.switchToScreen(new GameScreen(main, main.getScreen.asInstanceOf[BaseScreen].stage.asInstanceOf[GameStage].getLoadedWorld)))
  def SelectWorld(main: Main) = new GameButton("Map Select", GameButton.Skin)(() => main.switchToScreen(new WorldSelectScreen(main)))
}
class GameButton(text: String, skin: Skin)(callback: () => Unit) extends TextButton(s"  $text  ", skin) {
  this.addListener(new ButtonListener(callback))
}

class ButtonListener(callback: () => Unit) extends ChangeListener {
  override def changed(event: ChangeEvent, actor: Actor): Unit = callback.apply()
}
