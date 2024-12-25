package list_5.ex6_2.logic

import list_5.ex6_2.utils.Coords
import list_5.ex6_2.board.Board
import list_5.ex6_2.pieces.Piece

class ActionValidator(private val board: Board) {
    private val collisionDetector = CollisionDetector(board)

    fun hasSuchAction(piece: Piece, origin: Coords, destination: Coords): Boolean {
        return destination in piece.getMoves(origin.first, origin.second) ||
                destination in piece.getAttackMoves(origin.first, origin.second)
    }

    fun canMoveLikeThat(piece: Piece, origin: Coords, destination: Coords): Boolean {
        return destination in piece.getMoves(origin.first, origin.second)
    }

    fun canAttackLikeThat(piece: Piece, origin: Coords, destination: Coords): Boolean {
        return destination in piece.getAttackMoves(origin.first, origin.second)
    }

    fun isOnBoard(destination: Coords): Boolean {
        return board.isWithinBounds(destination.first, destination.second)
    }

    fun isPathCollisionFree(origin: Coords, destination: Coords): Boolean {
        return collisionDetector.detect(origin, destination)
    }

    fun isSpotFree(coords: Coords): Boolean {
        return board.isFree(coords.first, coords.second)
    }

    fun isFriendlyFire(destination: Coords, piece: Piece): Boolean {
        return board.isOccupiedBy(destination.first, destination.second, piece.side)
    }

}
