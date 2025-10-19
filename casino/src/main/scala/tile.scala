//******************************************************************************
// Copyright (c) 2015, The Regents of the University of California (Regents).
// All Rights Reserved. See LICENSE for license details.
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
// RISCV Processor Tile
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//
// Christopher Celio
// 2012 Feb 5
//
// Describes a RISC-V Out-of-Order processor tile

//package casino
//
//import Chisel._
//import cde.{Parameters, Field}
//
//class CASINOTile(clockSignal: Clock = null, resetSignal: Bool = null)
//   (implicit p: Parameters) extends freechips.rocketchip.rocket.Tile(clockSignal, resetSignal)(p)
//{
//   val core = Module(new CASINOCore())
//   val icache = Module(new freechips.rocketchip.rocket.Frontend()(p.alterPartial({
//      case uncore.agents.CacheName => "L1I"
//      })))
//   val dcache = freechips.rocketchip.rocket.HellaCache(p(freechips.rocketchip.rocket.DCacheKey))(dcacheParams)
//
//   val dc_shim = Module(new DCacheShim()(dcacheParams))
//
//   val ptwPorts = collection.mutable.ArrayBuffer(icache.io.ptw, core.io.ptw_tlb)
//   val dcPorts = collection.mutable.ArrayBuffer(dc_shim.io.dmem)
//   val uncachedArbPorts = collection.mutable.ArrayBuffer(icache.io.mem)
//   val uncachedPorts = collection.mutable.ArrayBuffer[freechips.rocketchip.tilelink.ClientUncachedTileLinkIO]()
//   val cachedPorts = collection.mutable.ArrayBuffer(dcache.mem)
//   core.io.interrupts := io.interrupts
//   core.io.hartid := io.hartid
//   dc_shim.io.core <> core.io.dmem
//   icache.io.cpu <> core.io.imem
//   icache.io.resetVector := io.resetVector
//
//
//   val uncachedArb = Module(new freechips.rocketchip.tilelink.ClientUncachedTileLinkIOArbiter(uncachedArbPorts.size))
//   uncachedArb.io.in <> uncachedArbPorts
//   uncachedArb.io.out +=: uncachedPorts
//
//   // Connect the caches and RoCC to the outer memory system
//   io.uncached <> uncachedPorts
//   io.cached <> cachedPorts
//   // TODO remove nCached/nUncachedTileLinkPorts parameters and these assertions
//   require(uncachedPorts.size == nUncachedTileLinkPorts)
//   require(cachedPorts.size == nCachedTileLinkPorts)
//
//   if (p(freechips.rocketchip.rocket.UseVM))
//   {
//      val ptw = Module(new freechips.rocketchip.rocket.PTW(ptwPorts.size)(dcacheParams))
//      ptw.io.requestor <> ptwPorts
//      ptw.io.mem +=: dcPorts
//      core.io.ptw_dat <> ptw.io.dpath
//
//      // the dcache's built-in TLB will be unused, but it still needs some of the
//      // status/sret signals for things such as lr/sc
//      dcache.ptw.status <> ptw.io.requestor(1).status
//      dcache.ptw.invalidate := ptw.io.requestor(1).invalidate
//      dcache.ptw.req.ready := Bool(false)
//      dcache.ptw.resp.valid := Bool(false)
//   }
//
//   val dcArb = Module(new freechips.rocketchip.rocket.HellaCacheArbiter(dcPorts.size)(dcacheParams))
//   dcArb.io.requestor <> dcPorts
//   dcache.cpu <> dcArb.io.mem
//   dcache.cpu.invalidate_lr := core.io.dmem.invalidate_lr
//
//
//   // Cache Counters
//   core.io.counters.dc_miss := dcache.mem.acquire.fire()
//   core.io.counters.ic_miss := icache.io.mem.acquire.fire()
//}
