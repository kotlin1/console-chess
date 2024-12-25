package list_5.ex6_2.pieces

import list_5.ex6_2.utils.Side


class Pawn(override val side: Side) : Piece() {
    override val symbol
        get() = if (side == Side.WHITE) "P" else "p"

    override fun getMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        val direction = if (side == Side.WHITE) -1 else 1
        val moves = mutableListOf(Pair(x, y + direction))
        if (moveCounter == 0)
            moves.add(Pair(x, y + direction + direction))
        return moves
    }

    override fun getAttackMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        val direction = if (side == Side.WHITE) -1 else 1
        return listOf(
            Pair(x + direction, y + direction),
            Pair(x - direction, y + direction),
        )
    }
}
