package org.hakuka.game.module

import org.hakuka.game.Config
import org.hakuka.game.business.Blockable
import org.itheima.kotlin.game.core.Painter

class Water(override val x: Int, override val y: Int) : Blockable {
    override val width: Int= Config.block
    override val heigt: Int= Config.block

    override fun draw() {
        Painter.drawImage("img/water.gif",x,y)
    }
}