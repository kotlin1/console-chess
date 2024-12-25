package list_5.ex6_2.utils

enum class GameActions(val short: Char, val desc: String) {
    H('h', "Show Actions"),
    Q('q', desc = "Quite Game"),
    R('r', "Restart Game"),
    N('n', "Randomize Pieces"),
    M('m', "Move Pieces mode");
}
