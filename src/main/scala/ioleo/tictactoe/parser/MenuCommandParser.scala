package ioleo.tictactoe.parser

import ioleo.tictactoe.domain.MenuCommand
import zio.ZIO
import zio.macros.annotation.{accessible, mockable}

@accessible(">")
@mockable
trait MenuCommandParser {
  val menuCommandParser: MenuCommandParser.Service[Any]
}

object > extends MenuCommandParser.Service[MenuCommandParser] {
  def parse(input: String) : ZIO[MenuCommandParser, Nothing, MenuCommand] =
    ZIO.accessM(_.menuCommandParser.parse(input))
}

object MenuCommandParser {
  trait Service[R] {
    def parse(input: String): ZIO[R, Nothing, MenuCommand]
  }
}

