package org.hakuka.game.business

import org.hakuka.game.module.View

/*
具备攻击的能力
 */
interface Attackable : View{
    //定义所有者
    val owner:View
    //攻击力
    val attackPower:Int
    //判断是否碰撞
    fun isCollision(sufferable: Sufferable):Boolean
    fun nofifyAttack(sufferable: Sufferable)
}