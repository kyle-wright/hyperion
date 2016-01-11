package com.krux.hyperion.action

import com.krux.hyperion.adt.HString
import com.krux.hyperion.common.{ PipelineObjectId, NamedPipelineObject, PipelineObject,
  BaseFields }
import com.krux.hyperion.aws.{ AdpSnsAlarm, AdpRef }
import com.krux.hyperion.expression.RuntimeNode
import com.krux.hyperion.HyperionContext

/**
 * Sends an Amazon SNS notification message when an activity fails or finishes successfully.
 */
case class SnsAlarm private (
  baseFields: BaseFields,
  subject: HString,
  message: HString,
  role: HString,
  topicArn: HString
) extends Action with NamedPipelineObject {

  type Self = SnsAlarm

  def updateBaseFields(fields: BaseFields) = copy(baseFields = fields)

  def objects: Iterable[PipelineObject] = None

  def withSubject(subject: HString) = copy(subject = subject)
  def withMessage(message: HString) = copy(message = message)
  def withRole(role: HString) = copy(role = role)
  def withTopicArn(arn: HString) = copy(topicArn = arn)

  lazy val serialize = new AdpSnsAlarm(
    id = id,
    name = id.toOption,
    subject = subject.serialize,
    message = message.serialize,
    topicArn = topicArn.serialize,
    role = role.serialize
  )

  override def ref: AdpRef[AdpSnsAlarm] = AdpRef(serialize)

}

object SnsAlarm {

  val defaultSubject = s"[hyperion] (${RuntimeNode.PipelineId}) ${RuntimeNode.Status}: ${RuntimeNode.Name}"

  val defaultMessage = s"""Data pipeline: ${RuntimeNode.PipelineId}
    |Node: ${RuntimeNode.Name}
    |Status: ${RuntimeNode.Status}""".stripMargin

  def apply()(implicit hc: HyperionContext): SnsAlarm = apply(hc.snsTopic.get)

  def apply(topicArn: HString)(implicit hc: HyperionContext): SnsAlarm = new SnsAlarm(
    baseFields = BaseFields(PipelineObjectId(SnsAlarm.getClass)),
    subject = defaultSubject,
    message = defaultMessage,
    topicArn = topicArn,
    role = hc.snsRole.get
  )


}