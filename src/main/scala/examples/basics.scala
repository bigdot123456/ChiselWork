package chiselexamples

import chisel3._
import chisel3.util._

class MyModule extends RawModule{

  val io = IO(new Bundle{
    val in = Input(UInt(3.W))
    val out = Output(UInt(3.W))
  })

  io.out := io.in + 1.U
}

class FSMExample extends Module{
  val io = IO(new Bundle{
    val firstIn = Input(Bool())
    val secondIn = Input(Bool())
    val thirdIn = Input(Bool())
    val firstOut = Output(Bool())
    val secondOut = Output(Bool())
    val thirdOut = Output(Bool())
  })
  io.firstOut := false.B
  io.secondOut := false.B
  io.thirdOut := false.B
  val idle :: first :: second :: third :: Nil = Enum(4)
  val state = RegInit(idle)
  when(state === idle){
    when(io.firstIn){state := first}
  }.elsewhen(state === first){
    io.firstOut := true.B
    when(io.secondIn){state := second}
  }.elsewhen(state === second){
    io.secondOut := true.B
    when(io.thirdIn){state := third}
  }.elsewhen(state === third){
    io.thirdOut := true.B
    state := idle
  }
}
