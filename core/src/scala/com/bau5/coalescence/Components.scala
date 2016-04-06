package com.bau5.coalescence

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Color

/**
  * Created by Rick on 3/31/16.
  */
case class PositionComponent(var x: Float, var y: Float) extends Component {
  def add(dx: Float, dy: Float): Unit = {
    x += dx
    y += dy
  }
}
case class VelocityComponent(var vx: Float, var vy: Float) extends Component {
  def add(dx: Float, dy: Float): Unit = {
    vx += dx
    vy += dy
  }
}
case class AttributeComponent(var width: Int, var height: Int, var color: Color) extends Component