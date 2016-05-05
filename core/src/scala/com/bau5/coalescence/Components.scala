package com.bau5.coalescence

import com.badlogic.ashley.core.Component

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
    case 0 | 1 =>
      new AttributeComponent(16, 16, 0f)
    case 2 =>
      new AttributeComponent(32, 45, 0f);
    case _ =>
      new AttributeComponent(4, 4, 0f)
  }
}
case class AttributeComponent(var width: Int, var height: Int, var rotation: Float) extends Component

object CharacterStats {
  def forType(typ: Int): StatsComponent = typ match {
    case 0 =>
      // Dwarf warrior
      new StatsComponent(40, 5)
    case 1 =>
      // Mage - projectile: 1
      new StatsComponent(20, 10)
  }
}
object EnemyStats {
  def forType(typ: Int): StatsComponent = typ match {
    case 0 =>
      new StatsComponent(20, 5)
    case 1 =>
      new StatsComponent(10, 5);
    case 2 =>
      new StatsComponent(30, 20);
  }
}
object ProjectileStats {
  def forType(typ: Int): StatsComponent = typ match {
    case 0 =>
      new StatsComponent(1, 10)
    case 1 =>
      new StatsComponent(1, 15)
  }
}
case class StatsComponent(getMaxHealth: Int, attackDamage: Int) extends Component