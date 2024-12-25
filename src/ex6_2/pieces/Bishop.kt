package list_5.ex6_2.pieces

import list_5.ex6_2.utils.Side

class Bishop(override val side: Side) : Piece() {
    override val symbol: String
        get() = if (side == Side.WHITE) "B" else "b"

    override fun getMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        val availableMoves: MutableList<Pair<Int, Int>> = mutableListOf()
        for (it in 1..7) {
            availableMoves.add(Pair(x + it, y + it))
            availableMoves.add(Pair(x + it, y - it))
            availableMoves.add(Pair(x - it, y - it))
            availableMoves.add(Pair(x - it, y + it))
        }
        return availableMoves
    }
}
