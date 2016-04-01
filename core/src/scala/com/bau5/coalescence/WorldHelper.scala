package com.bau5.coalescence

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.{PolygonShape, BodyDef, Body, World}

object WorldHelper {
  val world = new World(new Vector2(0, 0 ), true)

  def createBody(x: Float, y: Float, w: Float, h: Float): Body = {
    val bodyDef = new BodyDef
    bodyDef.position.set(new Vector2(x, y))
    val body = world.createBody(bodyDef)
    val shape = new PolygonShape()
    shape.setAsBox(w / 2, h / 2)
    body.createFixture(shape, 1.0f)
    body.resetMassData()
    shape.dispose()
    body
  }
}