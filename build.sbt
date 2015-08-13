name := "websockets-scala-rx"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

organization := "com.marekkadek"

version := "0.1"

scalaVersion := "2.11.7"

libraryDependencies += "org.java-websocket" % "Java-WebSocket" % "1.3.0"

libraryDependencies += "io.reactivex" %% "rxscala" % "0.25.0"
    