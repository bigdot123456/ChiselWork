package chiselexamples

import chisel3._
import chipsalliance.rocketchip.config.{Config, Field, Parameters}

//class configexample {
//
//}
/** Key declearation */
case object MyKey1 extends Field[Int](1)
case object MyKey2 extends Field[MyParam]()
/** Key producer. */
class MyConfig extends Config((site,here,up) => {
  case MyKey1 => 2
  case MyKey2 => MyParam()
})
case class MyParam(inputWidth: Int = 3, outputWidth: Int = 3)
/** Key consumer. */
class ConfigExample(implicit p: Parameters) extends Module{
  val inputWidth = p(MyKey1)
  val outputWidth = p(MyKey2).outputWidth
  val io = IO(new Bundle{
    val output = Output(UInt(outputWidth.W))
    val input = Input(UInt(inputWidth.W))
  })
  io.output := io.input + 1.U
}
