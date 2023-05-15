package processor

fun main() {
    // need to calculate how many lines to read
    // the first line, with 2 digits, the first digit, the number of line
    //var matrices = mutableListOf<Array<IntArray>>()

    //var quit = false
    while (true) {
        displayMenu()
        val choice = readln().first()
        if (choice == '0') {
            //quit = true
            //println()
            break
        } else {
            parseChoice(choice)
        }
    }
}

private fun parseChoice(choice: Char) {
    when (choice) {
        '1' -> {
            val output = processAddition()
            if (output != "") {
                println("The result is:\n ${output}")
            } else {
                println("The operation cannot be performed.")
            }
        }
        '2' -> {
            val output = processMultiplyByScalar()
            if (output != "") {
                println("The result is:\n $output")
            } else {
                println("The operation cannot be performed.")
            }
        }
        '3' -> {val output = processMultiplyMatrix()
            if (output != "") {
                println("The result is:\n $output")
            } else {
                println("The operation cannot be performed.")
            }
        }
        '0' -> {
            System.exit(0)
        }
    }
}

private fun printMatrix(matrix: Array<DoubleArray>) : String {
    var output = "\n"

    for (row in matrix) {
        for (element in row) {
            output += "$element "
        }
        output += "\n"
    }
    return output
}

private fun getMatrixInputs(name: String) : Array<DoubleArray> {
    println("Enter size of $name matrix: > ")
    val dimension = readln().split(" ")

    val totalRow = dimension[0].toInt()
    val totalColumn = dimension[1].toInt()

    val matrix : Array<DoubleArray> = Array(totalRow) { DoubleArray(totalColumn) }

    println("Enter $name matrix:")
    for (i in 0..totalRow - 1) {
        val input = readln().split(" ")
        val nums = input.map { each ->
            each.toDouble()
        }
        for (j in 0..totalColumn - 1) {
            matrix[i][j] = nums[j]
        }
    }

    return matrix
}

private fun processAddition() : String {
    val matrixOne = getMatrixInputs("first")
    val matrixTwo = getMatrixInputs("second")

    return addition(matrixOne, matrixTwo)
}

private fun processMultiplyByScalar() : String {
    val matrixOne = getMatrixInputs("")
    //val matrixTwo = getMatrixInputs("second")
    print("Enter constant: > ")
    val constant = readln().toDouble()

    return multiByScalar(matrixOne, constant)
}

private fun processMultiplyMatrix() : String {

    val matrixOne = getMatrixInputs("first")
    val matrixTwo = getMatrixInputs("second")

    return multiplyMatrix(matrixOne, matrixTwo)
}

private fun addition(matrixOne: Array<DoubleArray>, matrixTwo: Array<DoubleArray>) : String {
    if (matrixOne.size != matrixTwo.size ||
        matrixOne[0].size != matrixTwo[0].size) {
        return ""
    }

    val matrix : Array<DoubleArray> = Array(matrixOne.size) { DoubleArray(matrixOne[0].size) }

    for (row in 0..matrixOne.size - 1)  {
        for (column in 0..matrixOne[row].size - 1) {
            matrix[row][column] = matrixOne[row][column] + matrixTwo[row][column]
        }
    }

    return printMatrix(matrix)
}

private fun multiByScalar(matrix: Array<DoubleArray>, scalar: Double) : String {

    val resultMatrix : Array<DoubleArray> = Array(matrix.size) { DoubleArray(matrix[0].size) }

    for (row in 0..matrix.size - 1)  {
        for (column in 0..matrix[row].size - 1) {
            resultMatrix[row][column] = matrix[row][column] * scalar
        }
    }

    return printMatrix(resultMatrix)
}

private fun multiplyMatrix(matrixOne: Array<DoubleArray>, matrixTwo: Array<DoubleArray>) : String {

    if (matrixOne.size != matrixTwo[0].size) {
        return ""
    }

    val resultMatrix : Array<DoubleArray> = Array(matrixOne.size) { DoubleArray(matrixTwo[0].size) }

    val rowsIndexRange = matrixOne.size - 1

    for (i in 0..resultMatrix.size - 1) {
        for (j in 0..resultMatrix[0].size - 1) {
            resultMatrix[i][j] = dotProduct(matrixOne[i], getColumnVector(j, rowsIndexRange, matrixTwo))
        }
    }

    return printMatrix(resultMatrix)
}

private fun getColumnVector(columnIndex: Int, rowsIndexRange: Int, matrix: Array<DoubleArray>) : DoubleArray {
    val column : DoubleArray = DoubleArray(matrix.size)

    //println(printMatrix(matrix))
    for (i in matrix.indices) {
        column[i] = matrix[i][columnIndex]
    }

    return column
}

private fun dotProduct(row: DoubleArray, column: DoubleArray) : Double {
    var product = 0.0

    for (i in 0..row.size - 1) {
        product += row[i] * column[i]
    }

    return product
}

private fun displayMenu() {
    println("1. Add matrices")
    println("2. Multiply matrix by a constant")
    println("3. Multiply matrices")
    println("0. Exit")
    println("Your choice: > ")
}


