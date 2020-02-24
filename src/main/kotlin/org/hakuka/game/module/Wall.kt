package org.hakuka.game.module

import org.hakuka.game.Config
import org.hakuka.game.business.Attackable
import org.hakuka.game.business.Blockable
import org.hakuka.game.business.Destoryable
import org.hakuka.game.business.Sufferable
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter

/*
砖墙
 */
class Wall(override val x: Int, override val y: Int) : Blockable,Sufferable,Destoryable{

    override var blood: Int=3
    override val width: Int=Config.block
    override val heigt: Int=Config.block

    override fun draw() {
        Painter.drawImage("img/wall.gif",x,y)
    }

    override fun isDestoyed(): Boolean = blood<=0

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        blood-=attackable.attackPower
        //播放音效
        Composer.play("snd/hit.wav")
        return arrayOf(Blast(x,y))
    }

}