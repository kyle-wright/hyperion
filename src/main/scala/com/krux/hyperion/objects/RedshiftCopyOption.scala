package com.krux.hyperion.objects

trait RedshiftCopyOption {
  def repr: Seq[String]
}

object RedshiftCopyOption {

  def gzip = new RedshiftCopyOption {
    def repr = Seq("GZIP")
  }

  def delimiter(delChar: String) = new RedshiftCopyOption {
    def repr = Seq("DELIMITER", s"'$delChar'")
  }

  def escape = new RedshiftCopyOption {
    def repr = Seq("ESCAPE")
  }

}
