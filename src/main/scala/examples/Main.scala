package examples

import java.net.URI

import org.java_websocket.drafts.Draft_17
import websockets.rx.{ WebSocket, RxWebSocket }

object Main {
  def main(args: Array[String]): Unit = {
    val socket = WebSocket.observe(new URI("ws://echo.websocket.org"), new Draft_17)

    val first = socket.subscribe(x ⇒ println(1, x))
    val second = socket.subscribe(x ⇒ println(2, x))

    first.unsubscribe()
    // connection still open

    second.unsubscribe()
    // connection automatically closed

    // connection open
    val last = socket.subscribe(x ⇒ println(3, x))
    last.unsubscribe()
    // closed

    val rx = new RxWebSocket(new URI("ws://echo.websocket.org"), new Draft_17)
    val subscription = rx.events.subscribe(x => println(x))

    rx.connectBlocking()

    for (i <- 1 to 5) {
      rx.send("go")
      Thread.sleep(1000)
      if (i == 3) subscription.unsubscribe()
    }

    rx.closeBlocking()
    readLine()
  }
}
