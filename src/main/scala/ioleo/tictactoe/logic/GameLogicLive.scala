package ioleo.tictactoe.logic
import ioleo.tictactoe.domain.Piece.{Cross, Nought}
import ioleo.tictactoe.domain.{Board, Field, GameResult, Piece}
import zio.{IO, UIO, ZIO}

trait GameLogicLive extends GameLogic {
  override val gameLogic = new GameLogic.Service[Any] {
    override def putPiece(board: Map[Field, Piece], field: Field, piece: Piece): ZIO[Any, Unit, Map[Field, Piece]] =
      board.get(field) match {
        case None => IO.succeed(board.updated(field, piece))
        case _ => IO.fail()
      }

    override def gameResult(board: Map[Field, Piece]): ZIO[Any, Nothing, GameResult] = {
      val pieces: Map[Piece, Set[Field]] = board
        .groupBy(_._2)
        .view.mapValues(_.keys.toSet)
        .iterator.to(Map)
        .withDefaultValue(Set.empty[Field])

      val crossWin: Boolean  = Board.wins.exists(_ subsetOf pieces(Piece.Cross))
      val noughtWin: Boolean = Board.wins.exists(_ subsetOf pieces(Piece.Nought))
      val boardFull: Boolean = board.size == 9

      if (crossWin && noughtWin) ZIO.die(new IllegalStateException("It should not be possible for both players to meet winning conditions."))
      else if (crossWin) UIO.succeed(GameResult.Win(Piece.Cross))
      else if (noughtWin) UIO.succeed(GameResult.Win(Piece.Nought))
      else if (boardFull) UIO.succeed(GameResult.Draw)
      else UIO.succeed(GameResult.Ongoing)
    }

    override def nextTurn(currentTurn: Piece): ZIO[Any, Nothing, Piece] =
      UIO.succeed(currentTurn) map {
        case Cross => Nought
        case Nought => Cross
      }
  }
}
