package org.hakuka.game.module

import org.hakuka.game.Config
import org.hakuka.game.business.Attackable
import org.hakuka.game.business.Blockable
import org.hakuka.game.business.Movable
import org.hakuka.game.business.Sufferable
import org.hakuka.game.enums.Direction
import org.itheima.kotlin.game.core.Painter

class Tank(override var x: Int, override var y: Int) :
    Movable,Blockable,Sufferable {

    override val width: Int = Config.block
    override val heigt: Int = Config.block

    //方向
    override var currentDirection: Direction = Direction.UP
    //速度
    override val speed: Int = 8
    //坦克不可以走的方向
    private var badDirection: Direction? = null

    override var blood: Int=20

    override fun draw() {
        //根据坦克的方向绘制
        val imagePath: String = when (currentDirection) {
            Direction.UP -> "img/tank_u.gif"
            Direction.DOWN -> "img/tank_d.gif"
            Direction.LEFT -> "img/tank_l.gif"
            Direction.RIGHT -> "img/tank_r.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }

    fun move(direction: Direction) {
        //判断是否是要往碰撞的方向走
        if (direction == badDirection) {
            return
        }
        if (this.currentDirection != direction) {
            this.currentDirection = direction
            return
        }


        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        //越界判断
        if (x < 0) x = 0
        if (x > Config.gameWidth - width) x = Config.gameWidth - width
        if (y < 0) y = 0
        if (y > Config.gameHeight - heigt) x = Config.gameHeight - heigt
    }



    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        //接收到碰撞信息
        this.badDirection = direction
    }

    fun shot(): Bullet {

        return Bullet(this,currentDirection) { bulletWidth, bulletHeight ->
            //计算子弹真是坐标
            var tankX = x
            var tankY = y
            val tankWidth = width
            val tankHeight = heigt
            var bulletX = 0
            var bulletY = 0

            when (currentDirection) {
                Direction.UP -> {
                    bulletX = tankX + (tankWidth - bulletWidth) / 2
                    bulletY = tankY - bulletHeight / 2
                }
                Direction.DOWN -> {
                    bulletX = tankX + (tankWidth - bulletWidth) / 2
                    bulletY = tankY + tankHeight - bulletHeight / 2
                }
                Direction.LEFT -> {
                    bulletX = tankX - bulletWidth / 2
                    bulletY = tankY + (tankHeight - bulletHeight) / 2
                }
                Direction.RIGHT -> {
                    bulletX = tankX + tankWidth - bulletWidth / 2
                    bulletY = tankY + (tankHeight - bulletHeight) / 2
                }
            }
            Pair(bulletX, bulletY)
        }
    }
    override fun notifySuffer(attackable: Attackable): Array<View>? {
        blood-=attackable.attackPower
        return arrayOf(Blast(x,y))
    }
}