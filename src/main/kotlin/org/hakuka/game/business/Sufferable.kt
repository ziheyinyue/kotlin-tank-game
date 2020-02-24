package org.hakuka.game.business

import org.hakuka.game.module.View

/*
遭受攻击的能力
 */
interface Sufferable : View{
    /*
    具有生命值
     */
    val blood:Int
    fun notifySuffer(attackable: Attackable):Array<View>?
}