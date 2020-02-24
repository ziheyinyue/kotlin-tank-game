package org.hakuka.game.business

import org.hakuka.game.enums.Direction
import org.hakuka.game.module.View

interface AutoMovable : View{
    val currentDirection:Direction
    val speed:Int
    fun autoMove()
}