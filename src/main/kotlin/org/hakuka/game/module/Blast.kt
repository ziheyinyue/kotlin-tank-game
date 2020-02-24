package org.hakuka.game.module

import org.hakuka.game.Config
import org.hakuka.game.business.Destoryable
import org.itheima.kotlin.game.core.Painter

/*
爆炸物
 */
class Blast(override val x: Int, override val y: Int) : Destoryable {

    override val width: Int= Config.block
    override val heigt: Int=Config.block

    private val imagePath= arrayListOf<String>()
    private var index:Int=0

    init {
        (1..32).forEach {
            imagePath.add("img/blast_${it}.png")
        }
    }

    override fun draw() {
        val i=index % imagePath.size
        Painter.drawImage(imagePath[i],x,y)
        index++

    }

    override fun isDestoyed(): Boolean {
        return index>=imagePath.size
    }

}