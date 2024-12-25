package list_5.ex6_2.pieces

import list_5.ex6_2.utils.Side

class Knight(override val side: Side) : Piece() {
    override val symbol: String
        get() = if (side == Side.WHITE) "N" else "n"

    override fun getMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        return listOf(
            Pair(x - 1, y - 2), // UP LEFT
            Pair(x + 1, y - 2), // UP RIGHT
            Pair(x + 2, y - 1), // RIGHT UP
            Pair(x + 2, y + 1), // RIGHT DOWN
            Pair(x + 1, y + 2), // DOWN RIGHT
            Pair(x - 1, y + 2), // DOWN LEFT
            Pair(x - 2, y + 1), // LEFT DOWN
            Pair(x - 2, y - 1), // LEFT UP
        )
    }
}
