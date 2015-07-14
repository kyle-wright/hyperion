package com.krux.hyperion.activity

import com.krux.hyperion.HyperionContext

/**
 * A spark step that runs on Spark Cluster
 */
case class SparkStep private (
  jar: Option[String],
  mainClass: Option[String],
  args: Seq[String],
  scriptRunner: Option[String],
  jobRunner: Option[String]
) {

  def withJar(jar: String) = this.copy(jar = Option(jar))
  def withMainClass(mainClass: Any): SparkStep = mainClass match {
    case mc: String => this.copy(mainClass = Option(mc.stripSuffix("$")))
    case mc: Class[_] => this.withMainClass(mc.getName)
    case mc => this.withMainClass(mc.getClass)
  }
  def withArguments(arg: String*) = this.copy(args = args ++ arg)

  override def toString: String = (scriptRunner.toSeq ++ jobRunner.toSeq ++ jar.toSeq ++ mainClass.toSeq ++ args).mkString(",")

}

object SparkStep {

  def apply()(implicit hc: HyperionContext): SparkStep = SparkStep(
    jar = None,
    mainClass = None,
    args = Seq(),
    scriptRunner = Option("s3://elasticmapreduce/libs/script-runner/script-runner.jar"),
    jobRunner = Option(s"${hc.scriptUri}run-spark-step.sh")
  )

}