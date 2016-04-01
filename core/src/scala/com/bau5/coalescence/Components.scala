package com.bau5.coalescence

import com.badlogic.ashley.core.Component

/**
  * Created by Rick on 3/31/16.
  */
case class PositionComponent(var x: Float, var y: Float) extends Component
case class VelocityComponent(var vx: Float, var vy: Float) extends Component
