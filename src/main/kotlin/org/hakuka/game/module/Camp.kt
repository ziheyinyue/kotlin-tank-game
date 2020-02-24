package org.hakuka.game.module

import org.hakuka.game.Config
import org.hakuka.game.business.Attackable
import org.hakuka.game.business.Blockable
import org.hakuka.game.business.Destoryable
import org.hakuka.game.business.Sufferable
import org.itheima.kotlin.game.core.Painter

/*
大本营
 */
class Camp(override var x: Int, override var y: Int) : Blockable,Sufferable,Destoryable{

    override var width: Int= Config.block*2
    override var heigt: Int=Config.block+32

    override var blood: Int=12

    override fun draw() {

        if (blood<=3){
            width=Config.block
            heigt=Config.block
            x=(Config.gameWidth-Config.block)/2
            y=Config.gameHeight-Config.block
            Painter.drawImage("img/symbol.gif", x , y )

        }else if (blood<=6){
            Painter.drawImage("img/wall_small.gif", x, y)
            Painter.drawImage("img/wall_small.gif", x + 32, y)
            Painter.drawImage("img/wall_small.gif", x + 64, y)
            Painter.drawImage("img/wall_small.gif", x + 96, y)

            Painter.drawImage("img/wall_small.gif", x, y + 32)
            Painter.drawImage("img/wall_small.gif", x, y + 64)

            Painter.drawImage("img/wall_small.gif", x + 96, y + 32)
            Painter.drawImage("img/wall_small.gif", x + 96, y + 64)

            Painter.drawImage("img/symbol.gif", x + 32, y + 32)

        }else {
            Painter.drawImage("img/steel_small.gif", x, y)
            Painter.drawImage("img/steel_small.gif", x + 32, y)
            Painter.drawImage("img/steel_small.gif", x + 64, y)
            Painter.drawImage("img/steel_small.gif", x + 96, y)

            Painter.drawImage("img/steel_small.gif", x, y + 32)
            Painter.drawImage("img/steel_small.gif", x, y + 64)

            Painter.drawImage("img/steel_small.gif", x + 96, y + 32)
            Painter.drawImage("img/steel_small.gif", x + 96, y + 64)

            Painter.drawImage("img/symbol.gif", x + 32, y + 32)
        }
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        blood-=attackable.attackPower

        if (blood==6 || blood==3){
            val x=x-32
            val y=y-32
            return arrayOf(Blast(x,y),
                Blast(x+32,y),
                Blast(x+Config.block,y),
                Blast(x+Config.block+32,y),
                Blast(x+Config.block*2,y),
                Blast(x,y+32),
                Blast(x,y+Config.block),
                Blast(x,y+Config.block+32),
                Blast(x+Config.block*2,y+32),
                Blast(x+Config.block*2,y+Config.block),
                Blast(x+Config.block*2,y+Config.block+32))
        }
        return null
    }

    override fun isDestoyed(): Boolean = blood<=0
    override fun showDestory(): Array<View>? {
        return arrayOf(Blast(x-32,y-32),
            Blast(x,y-32),
            Blast(x+32,y-32),
            Blast(x-32,y),
            Blast(x,y),
            Blast(x+32,y),
            Blast(x-32,y+32),
            Blast(x,y+32),
            Blast(x+32,y+32))
    }
}