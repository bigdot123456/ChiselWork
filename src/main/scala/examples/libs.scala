package chiselexamples

import chisel3._
import chisel3.util._

class bundleExample extends Bundle{
  val number = UInt(3.W)
}
class ArbExample extends Module{
  val io = IO(new Bundle{
    val requests = Flipped(Vec(4, Decoupled(new bundleExample)))
    val a = Decoupled(new bundleExample)
  })
  val arbiter = Module(new Arbiter(new bundleExample, 4))
  arbiter.io.in <> io.requests
  io.a <> arbiter.io.out
}

class MapExample extends Module{
  val io = IO(new Bundle{
    val inputs = Flipped(Vec(5, Valid(UInt(3.W))))
    val out = Output(UInt(3.W))
  })
  /** reduce Or valid input values */
  val inputVld = VecInit(io.inputs.map(_.valid)).asUInt().orR()
  val outbits = VecInit(io.inputs.map(in =>
    Mux(in.valid, in.bits, 0.U))).asUInt().orR()
  when(inputVld){
    io.out := outbits
  }.otherwise{
    io.out := 0.U
  }
}

class ForeachExample extends Module {
  val io = IO(new Bundle{
    val output = Output(Bool())
  })
  val outputVec = Wire(Vec(6, UInt(3.W)))
  val entries = Seq.tabulate(6)(i => i).map(_ => Module(new MyModule))
  entries.zipWithIndex.foreach{ case (e, i) =>
    e.io.in := i.U
    outputVec(i) := e.io.out
  }
  io.output := outputVec.asUInt().orR()
}