package org.hakuka.game.ext

import org.hakuka.game.module.View

fun View.checkCollision(view: View):Boolean{
    return checkCollision(x,y,width,heigt,view.x,view.y,view.width,view.heigt)
}