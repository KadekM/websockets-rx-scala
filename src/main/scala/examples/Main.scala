package examples

import java.net.URI

import org.java_websocket.drafts.Draft_17
import websockets.rx.RxWebSocket

object Main {
  def main(args: Array[String]): Unit = {
    //val z = WebSocket.observe(new URI("ws://echo.websocket.org"), new Draft_17)
    //z.subscribe(x => println(x))

    val rx = new RxWebSocket(new URI("ws://echo.websocket.org"), new Draft_17)
    rx.events.subscribe(x => println(x))

    rx.connectBlocking()

    for (i <- 1 to 5) {
      rx.send("go")
      Thread.sleep(1000)
    }

    rx.closeBlocking()
    readLine()
  }
}
