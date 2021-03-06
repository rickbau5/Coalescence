package com.bau5.coalescence;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Rick on 4/5/2016.
 */
public enum Direction {
    Up, Down, Left, Right;

    public static Direction fromDegrees(float degrees) {
        if (degrees == 0) {
            return Direction.Up;
        }
        if (degrees == 90) {
            return Direction.Right;
        }
        if (degrees == 180) {
            return Direction.Down;
        }
        if (degrees == 270) {
            return Direction.Left;
        }

        return Direction.Up;
    }

    public static float toDegrees(Direction dir) {
        switch (dir) {
            case Up:
                return 0f;
            case Down:
                return 180f;
            case Right:
                return 270f;
            case Left:
                return 90f;
        }

        return 0f;
    }

    public static Vector2 getOffsetForDirection(Direction dir) {
        int x = 0;
        int y = 0;
        switch (dir) {
            case Right:
                x += 1;
                break;
            case Left:
                x -= 1;
                break;
            case Down:
                y -= 1;
                break;
            case Up:
                y += 1;
                break;
        }
        return new Vector2(x, y);
    }
}
