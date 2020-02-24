package org.hakuka.game.module

import org.hakuka.game.Config
import org.itheima.kotlin.game.core.Painter

class Grass(override val x: Int, override val y: Int) : View {
//    override val x: Int=200
//    override val y: Int=200
    override val width: Int=Config.block
    override val heigt: Int=Config.block

    override fun draw() {
        Painter.drawImage("img/grass.gif",x,y)
    }

}