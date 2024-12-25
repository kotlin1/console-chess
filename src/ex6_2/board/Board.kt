package list_5.ex6_2.board

import list_5.ex6_2.pieces.Piece
import list_5.ex6_2.utils.Color
import list_5.ex6_2.utils.Side
import list_5.ex6_2.utils.yellow


class Board {
    private val board: Array<Array<Spot>> = Array(8) { Array(8) { Spot() } }
    private val captured: Map<Side, MutableList<Piece>> =
        mutableMapOf(Side.WHITE to mutableListOf(), Side.BLACK to mutableListOf())

    fun getBoard(): String {
        val textBoard = StringBuilder()
        val horizontalLine = " ---"
        val verticalLine = "|"
        var rowNum = 0

        textBoard.append(captured[Side.WHITE].toString().yellow())
        textBoard.append('\n')

        board.forEach { row ->
            textBoard.append("   " + horizontalLine.repeat(row.size) + "\n")
            textBoard.append("y" + rowNum++ + " " + verticalLine)
            row.forEach { spot ->
                textBoard.append(" " + spot.draw() + " " + verticalLine)
            }
            textBoard.append("\n")
        }
        textBoard.append("   " + horizontalLine.repeat(board.size) + "\n   ")
        repeat(8) { textBoard.append(" x$it ") }

        textBoard.append('\n')
        textBoard.append(captured[Side.BLACK].toString().yellow())

        return textBoard.toString()
    }

    fun setColor(x: Int, y: Int, color: Color) {
        board[y][x].setColor(color)
    }

    fun resetColors() {
        board.forEach { row -> row.forEach { spot -> spot.resetColor() } }
    }

    fun replacePiece(x: Int, y: Int, piece: Piece): Piece? {
        return this.board[y][x].setPiece(piece)
    }

    fun getPiece(x: Int, y: Int): Piece? {
        return this.board[y][x].getPiece()
    }

    fun destroyPiece(x: Int, y: Int) {
        this.board[y][x].removePiece()
    }

    fun swapSpots(fromX: Int, fromY: Int, toX: Int, toY: Int) {
        val tmp = board[fromY][fromX]
        board[fromY][fromX] = board[toY][toX]
        board[toY][toX] = tmp
    }

    fun setPiece(x: Int, y: Int, piece: Piece) {
        this.board[y][x].setPiece(piece)
    }

    fun isFree(x: Int, y: Int): Boolean {
        return this.board[y][x].isFree()
    }

    fun isOccupiedBy(x: Int, y: Int, side: Side): Boolean {
        return side == getPiece(x, y)?.side
    }

    fun isWithinBounds(x: Int, y: Int): Boolean {
        return y in board.indices && x in board.first().indices
    }

    fun addCaptured(piece: Piece) {
        captured[piece.side]?.add(piece)
    }

    fun moveToCaptured(x: Int, y: Int): Piece? {
        val piece = board[y][x].getPiece()
        if (piece != null) addCaptured(piece)
        destroyPiece(x, y)
        return piece
    }
}
