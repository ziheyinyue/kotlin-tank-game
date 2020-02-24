import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window

class MyWindow : Window(){
    override fun onCreate() {
        println("窗体创建")
    }

    override fun onDisplay() {
        /*
        窗体渲染时不停被执行
        绘制图片
         */

    }

    override fun onKeyPressed(event: KeyEvent) {
    when(event.code){
        KeyCode.ENTER -> println("keypress")
    }
    }

    override fun onRefresh() {
    //刷新
    }
}

fun main() {
    Application.launch(MyWindow::class.java)
}