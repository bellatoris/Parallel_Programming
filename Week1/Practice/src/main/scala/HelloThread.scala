/**
  * Created by DoogieMin on 2017. 2. 4..
  */
class HelloThread extends Thread {
  override def run(): Unit = {
    println("Hello")
    println("world!")
  }
}