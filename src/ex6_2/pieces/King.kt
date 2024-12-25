package list_5.ex6_2.pieces

import list_5.ex6_2.utils.Side


class King(override val side: Side) : Piece() {
    override val symbol: String
        get() = if (side == Side.WHITE) "K" else "k"

    override fun getMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        return listOf(
            Pair(x - 1, y - 1),
            Pair(x, y - 1),
            Pair(x + 1, y - 1),
            Pair(x + 1, y),
            Pair(x + 1, y + 1),
            Pair(x, y + 1),
            Pair(x - 1, y + 1),
            Pair(x - 1, y)
        )
    }

}
