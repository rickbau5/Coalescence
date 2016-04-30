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

object EnemyAttributes {
  def forType(typ: Int): AttributeComponent = typ match {
    case 0 =>
      new AttributeComponent(16, 16, Color.BLACK)
    case _ =>
      new AttributeComponent(4, 4, Color.BROWN)
  }
}
case class AttributeComponent(var width: Int, var height: Int, var color: Color) extends Component


object CharacterStats {
  def forType(typ: Int): StatsComponent = typ match {
    case 0 =>
      new StatsComponent(20, 5)
    case _ =>
      new StatsComponent(20, 5)
  }
}
object EnemyStats {
  def forType(typ: Int): StatsComponent = typ match {
    case 0 =>
      new StatsComponent(20, 5)
    case _ =>
      new StatsComponent(20, 5)
  }
}
case class StatsComponent(getMaxHealth: Int, attackDamage: Int) extends Component