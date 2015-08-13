# websockets-rx-scala
Just quickly rx-wrapped websockets - toy project.

Use `RxWebSocket` for both way communication. It is basically a WebSocket which has additional field `events` which
can be used to observations. It is mutable state of RxWebSocket - `Subject[SocketMessage]` and thus not very nice.

Some examples:
```scala
val rx = new RxWebSocket(new URI("ws://echo.websocket.org"), new Draft_17)
val subscription = rx.events.subscribe(x => println(x))

rx.connectBlocking()

for (i <- 1 to 5) {
  rx.send("go")
  Thread.sleep(1000)
  if (i == 3) subscription.unsubscribe()
}

rx.closeBlocking()
```

Use `WebSocket.observe(serverUri, draft, headers, connectionTimeout, transformationFunction)` to just observe.
Transformation function is one to apply side effects to WebSocket (such as setting proxy or setting socket factory).

```scala
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
```
