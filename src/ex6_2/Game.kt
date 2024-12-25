package list_5.ex6_2

import list_5.ex6_2.board.Board
import list_5.ex6_2.logic.ActionManager
import list_5.ex6_2.pieces.*
import list_5.ex6_2.utils.*

class Game {
    private var board = Board()
    private var actionManager = ActionManager(board)
    private var running = false
    private val log: MutableList<String> = mutableListOf()

    fun start() {
        running = true
        setStandardGame()
        showBoard()
        showActions()
        startEngine()
    }

    private fun resetGame() {
        newGame()
        setStandardGame()
        showBoard()
        showActions()
    }

    private fun newGame() {
        board = Board()
        actionManager = ActionManager(board)
    }

    private fun newRandomGame() {
        newGame()
        setRandomGame()
        showBoard()
        showActions()
    }

    private fun setRandomGame() {

        val allCoords = ArrayDeque<Pair<Int, Int>>()


        for (x in 0 until 8)
            for (y in 0 until 8)
                allCoords.add(Pair(x, y))

        allCoords.shuffle()

        repeat(8) {
            val (x, y) = allCoords.removeLast()
            val (x1, y1) = allCoords.removeLast()
            board.setPiece(x, y, Pawn(Side.WHITE))
            board.setPiece(x1, y1, Pawn(Side.BLACK))
        }

        repeat(2) {
            val (x, y) = allCoords.removeLast()
            val (x1, y1) = allCoords.removeLast()
            board.setPiece(x, y, Knight(Side.WHITE))
            board.setPiece(x1, y1, Knight(Side.BLACK))
        }

        repeat(2) {
            val (x, y) = allCoords.removeLast()
            val (x1, y1) = allCoords.removeLast()
            board.setPiece(x, y, Rook(Side.WHITE))
            board.setPiece(x1, y1, Rook(Side.BLACK))
        }

        repeat(2) {
            val (x, y) = allCoords.removeLast()
            val (x1, y1) = allCoords.removeLast()
            board.setPiece(x, y, Bishop(Side.WHITE))
            board.setPiece(x1, y1, Bishop(Side.BLACK))
        }

        val (x, y) = allCoords.removeLast()
        val (x1, y1) = allCoords.removeLast()
        board.setPiece(x, y, Queen(Side.WHITE))
        board.setPiece(x1, y1, Queen(Side.BLACK))

        val (x2, y2) = allCoords.removeLast()
        val (x3, y3) = allCoords.removeLast()
        board.setPiece(x2, y2, King(Side.WHITE))
        board.setPiece(x3, y3, King(Side.BLACK))
    }

    private fun setStandardGame() {
        repeat(8) {
            board.setPiece(it, 6, Pawn(Side.WHITE))
            board.setPiece(it, 1, Pawn(Side.BLACK))
        }
        board.setPiece(3, 7, Queen(Side.WHITE))
        board.setPiece(4, 7, King(Side.WHITE))
        board.setPiece(1, 7, Knight(Side.WHITE))
        board.setPiece(6, 7, Knight(Side.WHITE))
        board.setPiece(2, 7, Bishop(Side.WHITE))
        board.setPiece(5, 7, Bishop(Side.WHITE))
        board.setPiece(0, 7, Rook(Side.WHITE))
        board.setPiece(7, 7, Rook(Side.WHITE))

        board.setPiece(4, 0, Queen(Side.BLACK))
        board.setPiece(3, 0, King(Side.BLACK))
        board.setPiece(1, 0, Knight(Side.BLACK))
        board.setPiece(6, 0, Knight(Side.BLACK))
        board.setPiece(2, 0, Bishop(Side.BLACK))
        board.setPiece(5, 0, Bishop(Side.BLACK))
        board.setPiece(0, 0, Rook(Side.BLACK))
        board.setPiece(7, 0, Rook(Side.BLACK))
    }

    private fun showBoard() {
        println()
        println(board.getBoard())
    }

    private fun startEngine() {
        while (running) {
            val action = readAction()
            executeAction(action)
        }
    }

    private fun movingMode() {
        println("q - to exit move mode.")
        while (true) {
            try {
                board.resetColors()
                println()
                showLog()
                showBoard()
                var selectedCoords: Coords
                do {
                    print("Select piece [xy]: ")
                    selectedCoords = getCoords()
                    val isSpotEmpty = board.isFree(selectedCoords.first, selectedCoords.second)
                    if (isSpotEmpty) println("There is no piece!".yellow())
                } while (isSpotEmpty)
                actionManager.markAvailableMoves(selectedCoords)
                showBoard()
                print("Destination [xy]: ")
                val destination = getCoords()
                val moveInfo = actionManager.move(selectedCoords, destination)
                addMsgToLog(moveInfo)
//                println("\n$moveInfo")
            } catch (e: Exception) {
                showActions()
                break;
            }
        }
    }

    private fun addMsgToLog(msg: String) {
        log.add(msg)
    }

    private fun showLog() {
        println("#".blue().repeat(20))
        log.forEach { println(it) }
        println("#".blue().repeat(20))
    }

    private fun getCoords(): Coords {
        val (x, y) = readln().map { it.digitToInt() }
        return Pair(x, y)
    }

    private fun decodeCoords(coords: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val (fx, fy, tx, ty) = coords.replace(" ", "").map { it.digitToInt() }

        return Pair(Pair(fx, fy), Pair(tx, ty))
    }

    private fun showActions() {
        println("Actions:")
        GameActions.entries.forEach { action ->
            println("${action.short} - ${action.desc}")
        }
    }

    private fun readAction(): Char {
        return readln().toCharArray()[0]
    }

    private fun executeAction(action: Char) {
        when (action) {
            GameActions.H.short -> showActions()
            GameActions.Q.short -> running = false
            GameActions.R.short -> resetGame()
            GameActions.N.short -> newRandomGame()
            GameActions.M.short -> movingMode()
            else -> println("Wrong action!")
        }
    }

}
