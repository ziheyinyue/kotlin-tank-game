package org.hakuka.game

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.hakuka.game.business.*
import org.hakuka.game.enums.Direction
import org.hakuka.game.module.*
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window
import sun.security.krb5.internal.crypto.Des
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.CopyOnWriteArrayList

class GameWindow : Window(
    title = "坦克大战1.0",
    icon = "img/symbol.gif",
    width = Config.gameWidth,
    height = Config.gameHeight
) {

    //线程安全的集合
    private val views = CopyOnWriteArrayList<View>()

    private lateinit var tank: Tank

    //游戏是否结束
    private var gameOver: Boolean = false

    //敌方的数量
    private var enemyTotalSize = 20
    //敌方坦克最多显示几个
    private var enemyActiveSize = 4
    //敌方出生点
    private val enemyBornLocation = arrayListOf<Pair<Int, Int>>()
    //出生地点下标
    private var bornIndex = 0

    override fun onCreate() {
//        val file = File(javaClass.getResource("/map/1.map").path)
        val resourceAsStream = javaClass.getResourceAsStream("/map/1.map")
        val reader = BufferedReader(InputStreamReader(resourceAsStream, "utf-8"))
        val lines = reader.readLines()

        var lineNum = 0
        lines.forEach { line ->
            var columnNum = 0
            line.toCharArray().forEach { column ->
                when (column) {
                    '砖' -> views.add(Wall(columnNum * Config.block, lineNum * Config.block))
                    '铁' -> views.add(Steel(columnNum * Config.block, lineNum * Config.block))
                    '草' -> views.add(Grass(columnNum * Config.block, lineNum * Config.block))
                    '水' -> views.add(Water(columnNum * Config.block, lineNum * Config.block))
                    '敌' -> enemyBornLocation.add(Pair(columnNum * Config.block, lineNum * Config.block))
                }
                columnNum++
            }
            lineNum++
        }

        //添加我方坦克

        tank = Tank(Config.block * 10, Config.block * 12)
        views.add(tank)

        //添加大本营
        views.add(Camp(Config.gameWidth / 2 - Config.block, Config.gameHeight - 96))
    }


    override fun onDisplay() {
        views.forEach {
            it.draw()
        }


    }

    override fun onKeyPressed(event: KeyEvent) {
        //用户操作时
        if (!gameOver) {
            when (event.code) {
                KeyCode.W -> {
                    tank.move(Direction.UP)
                }
                KeyCode.S -> {
                    tank.move(Direction.DOWN)
                }
                KeyCode.A -> {
                    tank.move(Direction.LEFT)
                }
                KeyCode.D -> {
                    tank.move(Direction.RIGHT)
                }
                KeyCode.SPACE -> {
                    //发射子弹
                    val bullet = tank.shot()
                    views.add(bullet)
                }
            }
        }

    }

    override fun onRefresh() {
        //业务逻辑
        //检测销毁
        views.filter { it is Destoryable }.forEach {
            if ((it as Destoryable).isDestoyed()) {
                views.remove(it)

                if (it is Enemy) {
                    enemyTotalSize--
                }

                val destory = it.showDestory()
                destory?.let {
                    views.addAll(destory)
                }
            }

        }

        if (gameOver) return
        //判断运动物体与阻塞物体是否发生碰撞

        views.filter { it is Movable }.forEach { move ->
            move as Movable
            var badDirection: Direction? = null
            var badBlock: Blockable? = null

            //不要和自己比较
            views.filter { (it is Blockable) and (move != it) }.forEach blockTag@{ block ->

                block as Blockable
                val direction = move.willCollision(block)
                direction?.let {
                    badDirection = direction
                    badBlock = block
                    return@blockTag
                }
            }
            //找到额move碰撞的block，找到会碰撞的方向
            move.notifyCollision(badDirection, badBlock)
        }

        //检测自动移动能力的物体，让他们自己动起来
        views.filter { it is AutoMovable }.forEach {
            (it as AutoMovable).autoMove()
        }

        //检测具备攻击能力和被攻击能力的物体间是否产生碰撞
        views.filter { it is Attackable }.forEach { attack ->
            attack as Attackable
            //过滤 攻击方的源不能是发射方
            views.filter { (it is Sufferable) and (attack.owner != it) and (attack != it) }
                .forEach sufferTag@{ suffer ->
                    suffer as Sufferable
                    if (attack.isCollision(suffer)) {
                        //产生碰撞
                        //通知攻击者 产生碰撞
                        attack.nofifyAttack(suffer)
                        //通知受攻击者 产生碰撞
                        val sufferView = suffer.notifySuffer(attack)
                        sufferView?.let {
                            //显示挨打效果
                            views.addAll(sufferView)
                        }
                        return@sufferTag
                    }
                }
        }

        //检测自动射击
        views.filter { it is AutoShot }.forEach {
            it as AutoShot
            val shot: View? = it.autoShot()
            shot?.let {
                views.add(shot)
            }
        }

        //检测游戏是否结束
        if ((views.filter { it is Camp }.isEmpty()) or (enemyTotalSize <= 0)) {
            gameOver = true
        }


        //检测敌方出生
        if ((enemyTotalSize > 0) and (views.filter { it is Enemy }.size < enemyActiveSize)) {
            val index = bornIndex % enemyBornLocation.size
            val pair = enemyBornLocation[index]
            views.add(Enemy(pair.first, pair.second))

            bornIndex++

        }

    }
}