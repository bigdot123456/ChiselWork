package chiselexamples
import chisel3._
import chisel3.util._

class verilogfriendlyexample extends Module{
  val width = 3
  val io = IO(new Bundle{
    val funcOutput = Output(UInt(width.W))
    val moduleOutput = Output(UInt(width.W))
    val input = Input(UInt(width.W))
  })
  def func(in: UInt, width: Int): UInt = OHToUInt(in + 1.U)
  //invoke the function directly
  io.funcOutput := func(io.input, width)
  io.moduleOutput := FuncModule(io.input, width)
}

class FuncModule(width: Int) extends Module {
  val io = IO(new Bundle{
    val in = Input(UInt(width.W))
    val output = Output(UInt(width.W))
  })
  val plusOne = io.in + 1.U
  io.output := OHToUInt(plusOne)
}
object FuncModule{
  def apply(in: UInt, width: Int): UInt = {
    val module = Module(new FuncModule(width))
    module.io.in := in
    module.io.output
  }
}