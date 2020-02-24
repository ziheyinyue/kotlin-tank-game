package org.hakuka.game.module

import org.hakuka.game.Config
import org.hakuka.game.business.Attackable
import org.hakuka.game.business.AutoMovable
import org.hakuka.game.business.Destoryable
import org.hakuka.game.business.Sufferable
import org.hakuka.game.enums.Direction
import org.hakuka.game.ext.checkCollision
import org.itheima.kotlin.game.core.Painter

/*
create()函数返回两个值，是x，y
 */
class Bullet(override val owner: View,override val currentDirection:Direction,
             create:(width:Int, height:Int)->Pair<Int,Int>
)
    : AutoMovable,Destoryable,Attackable,Sufferable{

    override val width: Int
    override val heigt: Int
    override val blood: Int=1


    override var x: Int = 0
    override var y: Int = 0

    override val speed: Int=8

    private var isDestroyed=false

    override val attackPower: Int=1

    private var imagePath:String = when(currentDirection){
        Direction.UP -> "img/shot_top.gif"
        Direction.DOWN -> "img/shot_bottom.gif"
        Direction.LEFT -> "img/shot_left.gif"
        Direction.RIGHT -> "img/shot_right.gif"
    }

    init {
        //先计算高度和宽度
        val size = Painter.size(imagePath)
        width=size[0]
        heigt=size[1]

        val pair = create.invoke(width,heigt)
        x=pair.first
        y=pair.second
    }

    override fun draw() {

        Painter.drawImage(imagePath, x, y)
    }


    override fun autoMove() {
        when(currentDirection){
            Direction.UP -> y-=speed
            Direction.DOWN -> y+=speed
            Direction.LEFT -> x-=speed
            Direction.RIGHT -> x+=speed
        }
    }

    override fun isDestoyed(): Boolean {
        if(isDestroyed) return true
        if(x<-width) return true
        if(x> Config.gameWidth) return true
        if(y<-heigt) return true
        if(y>Config.gameHeight) return true
        return false
    }

    override fun isCollision(sufferable: Sufferable): Boolean {
        return checkCollision(sufferable)
    }

    override fun nofifyAttack(sufferable: Sufferable) {
        isDestroyed=true
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        return arrayOf(Blast(x,y))
    }
}