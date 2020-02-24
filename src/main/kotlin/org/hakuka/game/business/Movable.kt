package org.hakuka.game.business

import org.hakuka.game.Config
import org.hakuka.game.enums.Direction
import org.hakuka.game.module.View

interface Movable : View{
    //移动方向
    val currentDirection:Direction
    //移动速度
    val speed:Int
    /*
    判断移动物体是否和阻塞物体发生碰撞
    @return 要碰撞的方法，如果为null说明没有碰撞
     */
    fun willCollision(block:Blockable):Direction?{
        //未来的坐标
        var x = this.x
        var y = this.y
        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        //边界检测
        if (x < 0) return Direction.LEFT
        if (x > Config.gameWidth - width) return Direction.RIGHT
        if (y < 0) return Direction.UP
        if (y > Config.gameHeight - heigt) return Direction.DOWN

        //检测碰撞 下一步是否碰撞
        var collision = checkCollision(
            block.x, block.y, block.width, block.heigt,
            x, y, width, heigt
        )

        return if (collision) currentDirection else null
    }

    //通知碰撞
    fun notifyCollision(direction: Direction?,block: Blockable?)
}