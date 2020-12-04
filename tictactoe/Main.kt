package tictactoe

import kotlin.reflect.jvm.internal.impl.util.Check

fun main() {
//    print("Enter cells: ")
//    val input = readLine()!!
//    var board = initalizeBoard(input)

    var board = emptyBoard()
    display(board)
    var result:String
    var player = 'X'

    do {

        makeMove(board, player)
        display(board)

        result = analyze(board)
        player = if (player == 'X') 'O' else 'X'
    } while (result == "Game not finished")

    println(result)
}

fun emptyBoard(): Array<CharArray> {
    return Array(3) { CharArray(3) { '_' } }
}

fun initalizeBoard(input: String): Array<CharArray> {
    var board = emptyBoard()

    for (row in 0..2) {
        for (col in 0..2) {
//            println("DEBUG row: $row col: $col strpos: ${row *3 + col} char: ${input[row * 3 + col]}")
            board[row][col] = input[row * 3 + col]
        }
    }

    return board
}

fun display(board: Array<CharArray>) {
    println("---------")

    for (rowCount in 0..2) {
        val row = board[rowCount]
        println("| ${row[0]} ${row[1]} ${row[2]} |")
    }
    println("---------")

}

data class Coordinate(val row: Int, val column: Int)

fun makeMove(board: Array<CharArray>, player: Char = 'X') {
    val (row, column) = getMove(board)
//    println("DEBUG you selected $row, $column")
    board[row][column] = player
}

fun getMove(board: Array<CharArray>): Coordinate {

    var row = -1
    var column = -1

    do {
        print("Enter the coordinates: ")
        val input = readLine()!!.split(" ")

        if (input.size != 2) {
            println("You need to enter 2 numbers!")
        } else if (input[0][0].isDigit() && input[0][0].isDigit()) {
            // use 0 as the first coordinate
            row = input[0].toInt()-1
            column = input[1].toInt()-1

            if (!(row in 0..2 && column in 0..2)) {
                println("Coordinates should be from 1 to 3!")
                row = -1
            } else if (board[row][column] != '_') {
                println("This cell is occupied! Choose another one!")
                row = -1
            }
        } else {
            println("You should enter numbers!")
            row = -1
        }
    } while (row == -1)

    return Coordinate(row, column)
}

fun analyze(board: Array<CharArray>): String {

    val xCount = markerCount(board, 'X')
    val oCount = markerCount(board, 'O')
    val emptyCount = markerCount(board, '_')
//    println("DEBUG Xcount: $xCount Ocount $oCount Empty count: $emptyCount")
    if (Math.abs(xCount - oCount) > 1) return "Impossible"

    // check for winner, row, column, diagonals
    val xWin = playerWin(board, 'X')
    val oWin = playerWin(board, 'O')
    if (xWin && oWin) return "Impossible"
    if (xWin) return "X wins"
    if (oWin) return "O wins"

    if (emptyCount > 0) return "Game not finished" else return "Draw"
}

// check for winner, row, column, diagonals
fun playerWin(board: Array<CharArray>, player: Char): Boolean {
    return rowWin(board, player, 0)
            || rowWin(board, player, 1)
            || rowWin(board, player, 2)
            || colWin(board, player, 0)
            || colWin(board, player, 1)
            || colWin(board, player, 2)
            || diagonalWin(board, player)
}

fun rowWin(board: Array<CharArray>, player: Char, row: Int): Boolean {
    return (board[row][0] == player && board[row][1] == player && board[row][2] == player)
}

fun colWin(board: Array<CharArray>, player: Char, col: Int): Boolean {
    return (board[0][col] == player && board[1][col] == player && board[2][col] == player)
}

fun diagonalWin(board: Array<CharArray>, player: Char): Boolean {
    return (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            || (board[0][2] == player && board[1][1] == player && board[2][0] == player)
}

fun markerCount(board: Array<CharArray>, player: Char): Int {
    var count = 0;
    for (row in board) {
        for (spot in row) {
            if (spot == player) count++
        }
    }

    return count;
}