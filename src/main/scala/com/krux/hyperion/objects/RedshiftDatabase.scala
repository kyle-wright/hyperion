package com.krux.hyperion.objects

import com.krux.hyperion.objects.aws.{AdpJsonSerializer, AdpRedshiftDatabase}

/**
 * Redshift Database Trait, to use this please extend with an object.
 */
trait RedshiftDatabase extends PipelineObject {

  def clusterId: String

  def username: String

  def `*password`: String

  def databaseName: String

  def serialize = AdpRedshiftDatabase(
      id, Some(id), clusterId, None, Some(databaseName), None, `*password`, username
    )

}
