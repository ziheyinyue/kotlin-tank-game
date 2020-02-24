package org.hakuka.game.module

import org.hakuka.game.Config
import org.hakuka.game.business.*
import org.hakuka.game.enums.Direction
import org.itheima.kotlin.game.core.Painter
import java.util.*

class Enemy(override var x: Int, override var y: Int) :
    Movable,AutoMovable,Blockable,AutoShot,Sufferable,Destoryable{

    override var currentDirection: Direction=Direction.DOWN
    override val speed: Int=8

    override val width: Int=Config.block
    override val heigt: Int=Config.block

    //坦克不可以走的方向
    private var badDirection: Direction? = null

    private var lastShotTime=0L
    private var shotFrequency=700

    private var lastMoveTime=0L
    private var moveFrequency=50

    override var blood: Int = 2

    override fun draw() {
        //根据坦克的方向绘制
        val imagePath: String = when (currentDirection) {
            Direction.UP -> "img/enemy_1_u.gif"
            Direction.DOWN -> "img/enemy_1_d.gif"
            Direction.LEFT -> "img/enemy_1_l.gif"
            Direction.RIGHT -> "img/enemy_1_r.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }



    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        badDirection=direction
    }

    override fun autoMove() {
        //频率检测
        val currentTime=System.currentTimeMillis()
        if(currentTime-lastMoveTime<moveFrequency) return
        lastMoveTime=currentTime

        if (currentDirection==badDirection){
            currentDirection=rdmDirection(badDirection)

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
        if (y > Config.gameHeight - heigt) y = Config.gameHeight - heigt
    }

    private fun rdmDirection(bad:Direction?):Direction{
        val i = Random().nextInt(4)
        val direction = when (i) {
            0 -> Direction.UP
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.RIGHT
            else -> Direction.RIGHT
        }
        if (direction==bad){
            return rdmDirection(bad)
        }
        return direction
    }

    override fun autoShot(): View? {
        val currentTime=System.currentTimeMillis()
        if(currentTime-lastShotTime<shotFrequency) return null
        lastShotTime=currentTime

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
        if(attackable.owner is Enemy){
            return null
        }
        blood-=attackable.attackPower
        return arrayOf(Blast(x,y))
    }

    override fun isDestoyed(): Boolean = blood<=0


}