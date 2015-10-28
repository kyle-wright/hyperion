package com.krux.hyperion

import com.github.nscala_time.time.Imports._
import com.krux.hyperion.common.S3Uri.S3StringContext
import com.krux.hyperion.common.{Memory, S3Uri, PipelineObjectId}
import com.krux.hyperion.datanode.S3DataNode
import com.krux.hyperion.expression.DurationBuilder
import com.krux.hyperion.expression._
import scala.language.implicitConversions
import org.json4s.DefaultFormats

/**
 * The implicit conversions used in DataPipeline
 */
object Implicits {

  implicit val jsonFormats = DefaultFormats

  implicit def string2DateTime(day: String): DateTime = new DateTime(day)

  implicit def int2DpPeriod(n: Int): DurationBuilder = new DurationBuilder(n)

  implicit def dateTimeRef2dateTimeExp(dtRef: DateTimeRuntimeSlot): DateTimeExp = new DateTimeExp(dtRef.toString)

  implicit def expression2String(exp: Expression): String = exp.toString

  implicit def string2S3DataNode(s3path: String): S3DataNode = S3DataNode(S3Uri(s3path))

  implicit def string2S3Uri(s3path: String): S3Uri = S3Uri(s3path)

  implicit def s3Uri2S3DataNode(s3path: S3Uri): S3DataNode = S3DataNode(s3path)

  implicit def string2UniquePipelineId(prefix: String): PipelineObjectId = PipelineObjectId(prefix)

  implicit def stringContext2S3UriHelper(sc: StringContext): S3StringContext = S3StringContext(sc)

  implicit class Int2Memory(n: Int) {
    def kilobytes = Memory(n, "K")
    def megabytes = Memory(n, "M")
    def gigabytes = Memory(n, "G")
  }

  implicit class Long2Memory(n: Long) {
    def kilobytes = Memory(n, "K")
    def megabytes = Memory(n, "M")
    def gigabytes = Memory(n, "G")
  }
}
