package org.hakuka.game.module

import org.hakuka.game.Config
import org.hakuka.game.business.Attackable
import org.hakuka.game.business.Blockable
import org.hakuka.game.business.Sufferable
import org.itheima.kotlin.game.core.Painter

class Steel(override val x: Int, override val y: Int) : Blockable,Sufferable {
    override val blood: Int=1

    override val width: Int= Config.block
    override val heigt: Int= Config.block

    override fun draw() {
        Painter.drawImage("img/steel.gif",x,y)
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        return null
    }
}