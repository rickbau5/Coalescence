package com.bau5.coalescence

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Color

/**
  * Created by Rick on 3/31/16.
  */
case class PositionComponent(var x: Int, var y: Int) extends Component
case class VelocityComponent(var vx: Float, var vy: Float) extends Component
case class AttributeComponent(var width: Int, var height: Int, var color: Color) extends Component