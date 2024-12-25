package list_5.ex6_2.board

import list_5.ex6_2.pieces.Piece
import list_5.ex6_2.utils.Color
import list_5.ex6_2.utils.color


class Spot(private var piece: Piece? = null) {
    private var color: Color = Color.NONE

    fun draw(): String {
        //        •
        val symbol = piece?.symbol ?: if (color == Color.NONE) " " else "*"
        return symbol.color(color)
    }

    fun setColor(color: Color) {
        this.color = color
    }

    fun resetColor() {
        this.color = Color.NONE
    }

    fun setPiece(newPiece: Piece): Piece? {
        val oldPiece = this.piece
        this.piece = newPiece
        return oldPiece
    }

    fun getPiece(): Piece? {
        return this.piece
    }

    fun removePiece(): Piece? {
        val oldPiece = this.piece
        this.piece = null
        return oldPiece
    }

    fun isFree(): Boolean {
        return piece == null
    }
}
