package list_5.ex6_2.pieces

import list_5.ex6_2.utils.Side


class Rook(override val side: Side) : Piece() {
    override val symbol: String
        get() = if (side == Side.WHITE) "R" else "r"

    override fun getMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        val availableMoves: MutableList<Pair<Int, Int>> = mutableListOf()
        for (it in 1..7) {
            availableMoves.add(Pair(x + it, y))
            availableMoves.add(Pair(x - it, y))
            availableMoves.add(Pair(x, y + it))
            availableMoves.add(Pair(x, y - it))
        }
        return availableMoves
    }
}
