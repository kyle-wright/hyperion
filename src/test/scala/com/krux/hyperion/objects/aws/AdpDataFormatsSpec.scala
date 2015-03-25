package com.krux.hyperion.objects.aws

import org.scalatest.WordSpec
import org.json4s.JsonDSL._


class AdpDataFormatsSpec extends WordSpec {
  "TsvDataFormat" should {
    "converts to Json" in {
      val testObj = AdpTsvDataFormat(
        "tsv",
        None,
        None,
        Some("\\")
      )
      val objShouldBe = ("id" -> "tsv") ~
        ("escapeChar" -> "\\") ~
        ("type" -> "TSV")

      assert(AdpJsonSerializer(testObj) === objShouldBe)
    }
  }
}
