package com.bau5.coalescence.entities

import com.bau5.coalescence.entities.living.{BossEntity, EnemyEntity, ReflectorEnemy}

/**
  * Created by Rick on 5/6/2016.
  */
object Enemy {
  def forType(typ: Int, xPos: Float, yPos: Float): EnemyEntity = typ match {
    case 3 =>
      new ReflectorEnemy(typ, xPos, yPos)
    case 4 =>
      new BossEntity(typ, xPos, yPos)
    case _ =>
      new EnemyEntity(typ, xPos, yPos)
  }
}
