package list_5.ex6_2.logic

import list_5.ex6_2.board.Board
import list_5.ex6_2.pieces.Piece
import list_5.ex6_2.utils.*


class ActionManager(private val board: Board) {
    private val actionValidator = ActionValidator(board)

    fun move(origin: Coords, destination: Coords): String {
        val piece = board.getPiece(origin.first, origin.second) ?: return "There is no piece!".red()

        return when {
            !actionValidator.isOnBoard(destination) -> "The destination is over the board!".red()
            actionValidator.isSpotFree(destination) -> tryMove(piece, origin, destination)
            else -> tryAttack(piece, origin, destination)
        }
    }

    private fun tryMove(piece: Piece, origin: Coords, destination: Coords): String {
        return when {
            !actionValidator.canMoveLikeThat(piece, origin, destination) -> "The ${piece.symbol} cannot move like that!".red()
            actionValidator.isPathCollisionFree(origin, destination) -> "Collision detected!".red()
            else -> movePiece(origin, destination, piece).blue()
        }
    }

    private fun tryAttack(piece: Piece, origin: Coords, destination: Coords): String {
        return when {
            !actionValidator.canAttackLikeThat(piece, origin, destination) -> "The ${piece.symbol} cannot attack like that!".red()
            actionValidator.isPathCollisionFree(origin, destination) -> "Collision detected!".red()
            actionValidator.isFriendlyFire(destination, piece) -> "Friendly fire is not allowed!".red()
            else -> attack(origin, destination, piece).purple()
        }
    }

    fun getAvailableMoves(origin: Coords): List<Pair<Int, Int>> {
        val x = origin.first
        val y = origin.second
        val piece = board.getPiece(x, y) ?: return listOf()
        val moves = piece.getMoves(x, y)
        return moves.filter { dest ->
            actionValidator.isOnBoard(dest) &&
                    (actionValidator.isSpotFree(dest) &&
                            !actionValidator.isFriendlyFire(dest, piece)) &&
                    !actionValidator.isPathCollisionFree(origin, dest)
        }
    }

    fun getAvailableAttacks(origin: Coords): List<Pair<Int, Int>> {
        val x = origin.first
        val y = origin.second
        val piece = board.getPiece(x, y) ?: return listOf()
        val allAttackMoves = piece.getAttackMoves(x, y)
        val validAttackMoves = allAttackMoves.filter { destination ->
            actionValidator.isOnBoard(destination) &&
                    !actionValidator.isSpotFree(destination) &&
                    !actionValidator.isFriendlyFire(destination, piece) &&
                    !actionValidator.isPathCollisionFree(origin, destination)
        }
        return validAttackMoves
    }

    fun markAvailableMoves(origin: Coords) {
        val allMoves = getAvailableMoves(origin)
        val attackMoves = getAvailableAttacks(origin)

        if (allMoves.isNotEmpty() || attackMoves.isNotEmpty()) {
            board.setColor(origin.first, origin.second, Color.CYAN)
            allMoves.forEach { dest -> board.setColor(dest.first, dest.second, Color.GREEN) }
            attackMoves.forEach { dest -> board.setColor(dest.first, dest.second, Color.PURPLE) }
        }
    }

    private fun movePiece(origin: Coords, destination: Coords, piece: Piece): String {
        val (ox, oy) = origin
        val (dx, dy) = destination
        board.swapSpots(ox, oy, dx, dy)
        piece.incMoveCounter()
        return "$piece [$origin -> $destination]"
    }

    private fun attack(origin: Coords, destination: Coords, piece: Piece): String {
        val captured = board.moveToCaptured(destination.first, destination.second)
        board.swapSpots(origin.first, origin.second, destination.first, destination.second)
        return "${piece.symbol} captures $captured on $destination"
    }
}
