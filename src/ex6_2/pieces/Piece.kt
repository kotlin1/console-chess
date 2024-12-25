package list_5.ex6_2.pieces

import list_5.ex6_2.utils.Side

abstract class Piece {
    abstract val symbol: String
    abstract val side: Side
    protected var moveCounter: Int = 0

    abstract fun getMoves(x: Int, y: Int): List<Pair<Int, Int>>

    open fun getAttackMoves(x: Int, y: Int): List<Pair<Int, Int>> {
        return getMoves(x, y)
    }

    fun incMoveCounter() {
        this.moveCounter++
    }

    override fun toString(): String {
        return this.symbol.toString()
    }
}
