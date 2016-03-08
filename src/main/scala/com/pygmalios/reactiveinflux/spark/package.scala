package com.pygmalios.reactiveinflux

import com.pygmalios.reactiveinflux.command.write.PointNoTime
import com.pygmalios.reactiveinflux.spark.utils.Utils
import com.pygmalios.reactiveinflux.sync.SyncReactiveInfluxDb
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.DStream

import scala.concurrent.duration.Duration

package object spark {
  implicit def toRDDFunctions[T <: PointNoTime](rdd: RDD[T]): RDDExtensions = RDDExtensions(rdd)
  implicit def toDStreamFunctions[T](rdd: DStream[T]): DStreamExtensions[T] = DStreamExtensions[T](rdd)
  implicit def withInflux[S](action: (SyncReactiveInfluxDb) => S)
                            (implicit reactiveInfluxDbParams: ReactiveInfluxDbParams,
                             awaitAtMost: Duration): S = Utils.withInflux[S](action)
}
