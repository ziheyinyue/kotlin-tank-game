package org.hakuka.game.module

/*
显示的视图，定义显示规范
 */
interface View {

    //位置
    val x: Int
    val y: Int
    //宽高
    val width: Int
    val heigt: Int
    //显示
    fun draw()

    //检测碰撞
    fun checkCollision(
        x1: Int, y1: Int, w1: Int, h1: Int,
        x2: Int, y2: Int, w2: Int, h2: Int
    ): Boolean {
        return when {
            y2 + h2 <= y1 -> false
            y1 + h1 <= y2 -> false
            x2 + w2 <= x1 -> false
            else -> x1 + w1 > x2
        }

    }


}