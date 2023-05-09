fun main() {
    val isAscending: Boolean = readLine()!! == "ascending"
    val list: MutableList<Int> = readLine()!!.split(' ').map { it.toInt() }.toMutableList()

    val comparator: (Int, Int) -> Int = if (isAscending) ::compareAscending else ::compareDescending
        // put your code here
        //var compareFunction = if (isAscending) ::compareAscending else ::compareDescending

    sort(list, comparator)
    list.forEach { e -> print("$e ") }
}

fun compareAscending(num1: Int, num2: Int) : Int {
    return if (num1 > num2) num2 else num1
}

fun compareDescending(num1: Int, num2: Int) : Int {
    return if (num1 < num2) num2 else num1
}

fun sort(array: MutableList<Int>, comparator: (Int, Int) -> Int) {
    for (i in 0 until array.size - 1) {
        for (j in 0 until array.size - i - 1) {
            if (comparator(array[j], array[j + 1]) == array[j + 1]) {
                val temp = array[j]
                array[j] = array[j + 1]
                array[j + 1] = temp
            }
        }
    }
}