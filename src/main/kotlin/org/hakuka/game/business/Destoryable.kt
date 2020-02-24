package org.hakuka.game.business

import org.hakuka.game.module.View
/*
销毁的能力
 */
interface Destoryable : View{
    fun isDestoyed():Boolean

    fun showDestory():Array<View>?{
        return null
    }
}