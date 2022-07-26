package chiselexamples
import freechips.rocketchip.config._
import freechips.rocketchip.devices.tilelink._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.tilelink._
import chisel3._
import chisel3.util._
class diplomacyexample(implicit p: Parameters) extends LazyModule{ //注2
  val testRam = LazyModule(new TLTestRAM(
    AddressSet(0x80000000L, 0xffffff), true, 32))
  val xbar = LazyModule(new TLXbar())
  val master = TLClientNode(Seq(TLMasterPortParameters.v1(
    Seq(TLMasterParameters.v1(
      name = s"master",
      sourceId = IdRange(0, 16),
      supportsProbe = TransferSizes(64)
    )))))(ValName(s"master"))
  testRam.node := xbar.node := TLWidthWidget(64) := master //注3
  lazy val module = new LazyModuleImp(this){ //注4
    master.makeIOs()(ValName("master"))
  }
}

