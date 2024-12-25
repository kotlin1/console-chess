package list_5.ex6_2.logic

import list_5.ex6_2.utils.Coords
import list_5.ex6_2.board.Board
import list_5.ex6_2.pieces.Knight
import kotlin.math.abs

class CollisionDetector(private val board: Board) {

    enum class MoveDirection() {
        V, H, D, X;
    }

    enum class Heading() {
        U, D, L, R,
        UL, UR, DL, DR,
        X;
    }

    data class Trajectory(val heading: Heading, val from: Coords, val to: Coords) {
        val steps: Int = when (heading) {
            Heading.L, Heading.R -> Math.abs(from.first - to.first)
            else -> abs(from.second - to.second)
        }
    }

    fun detect(from: Pair<Int, Int>, to: Pair<Int, Int>): Boolean {
        if (isKnight(from.first, from.second))
            return false
        val trajectory: Trajectory = getTrajectory(from, to)
        return !isPathFree(trajectory)
    }

    private fun isKnight(x: Int, y: Int): Boolean {
        return board.getPiece(x, y) is Knight
    }

    private fun isPathFree(trajectory: Trajectory): Boolean {
        var x = trajectory.from.first
        var y = trajectory.from.second
        repeat(trajectory.steps - 1) {
            val isFree = when (trajectory.heading) {
                Heading.R -> board.isFree(++x, y)
                Heading.L -> board.isFree(--x, y)
                Heading.U -> board.isFree(x, --y)
                Heading.D -> board.isFree(x, ++y)
                Heading.UL -> board.isFree(--x, --y)
                Heading.UR -> board.isFree(++x, --y)
                Heading.DL -> board.isFree(--x, ++y)
                Heading.DR -> board.isFree(++x, ++y)
                else -> false
            }
            if (!isFree) return false
        }
        return true
    }

    private fun getTrajectory(from: Coords, to: Coords): Trajectory {
        val heading = when (detectDirection(from, to)) {
            MoveDirection.D -> detectDiagonalHeading(from, to)
            MoveDirection.H -> when {
                from.first > to.first -> Heading.L
                else -> Heading.R
            }

            MoveDirection.V -> when {
                from.second > to.second -> Heading.U
                else -> Heading.D
            }

            else -> Heading.X
        }
        return Trajectory(heading, from, to)
    }

    private fun detectDirection(from: Coords, to: Coords): MoveDirection {
        return when {
            from.first != to.first && from.second != to.second -> MoveDirection.D
            from.first != to.first -> MoveDirection.H
            from.second != to.second -> MoveDirection.V
            else -> MoveDirection.X
        }
    }

    private fun detectDiagonalHeading(from: Coords, to: Coords): Heading {
        return when {
            from.first > to.first -> when {
                from.second > to.second -> Heading.UL
                else -> Heading.DL
            }

            else -> when {
                from.second > to.second -> Heading.UR
                else -> Heading.DR
            }
        }
    }

}
