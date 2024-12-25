package list_5.ex6_2.utils

enum class Color(val code: Int) {
    NONE(0), RED(31), GREEN(32), YELLOW(33), BLUE(34), PURPLE(35), CYAN(36)
}

fun String.color(color: Color): String {
    if (color == Color.NONE) return this
    return "\u001B[1;${color.code}m$this\u001B[0m"
}

fun String.red() = this.color(Color.RED)
fun String.green() = this.color(Color.GREEN)
fun String.yellow() = this.color(Color.YELLOW)
fun String.blue() = this.color(Color.BLUE)
fun String.purple() = this.color(Color.PURPLE)
fun String.cyan() = this.color(Color.CYAN)
