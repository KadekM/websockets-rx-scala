package websockets.rx

import java.net.URI

import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft
import org.java_websocket.handshake.ServerHandshake
import rx.lang.scala.{ Subscription, Observable }

import collection.JavaConverters._

object WebSocket {
  def observe(serverUri: URI,
    draft: Draft,
    headers: Map[String, String] = Map.empty[String, String],
    connectionTimeout: Int = 0): Observable[SocketMessage] =
    Observable.create[SocketMessage] { observer ⇒
      val socket = new WebSocketClient(serverUri, draft, mapAsJavaMapConverter(headers).asJava, connectionTimeout) {
        override def onError(ex: Exception): Unit = observer.onError(ex)
        override def onMessage(message: String): Unit = observer.onNext(Message(message))
        override def onClose(code: Int, reason: String, remote: Boolean): Unit = {
          observer.onNext(Close(code, reason, remote))
          observer.onCompleted()
        }
        override def onOpen(handshakedata: ServerHandshake): Unit = observer.onNext(Open(handshakedata))
      }

      socket.connectBlocking()

      Subscription { socket.closeBlocking() }
    }.publish.refCount
}
