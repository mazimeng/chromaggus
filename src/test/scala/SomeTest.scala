import org.scalatest.FunSuite

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class SomeTest extends FunSuite {
  test("experiments") {
    val m = mutable.Map[String, ListBuffer[String]]()
    var lb = m.getOrElseUpdate("a", ListBuffer[String]())
    lb += "1"

    lb = m.getOrElseUpdate("a", ListBuffer[String]())
    lb += "2"

    for (elem <- m) {
      val (k, l) = elem
      println(k)
      println(l)
    }

  }
}