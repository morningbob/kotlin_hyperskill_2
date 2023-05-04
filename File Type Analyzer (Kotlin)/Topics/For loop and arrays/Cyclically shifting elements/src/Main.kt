fun main() {
    // write your code here
    val n = readln().toInt()
    val list = mutableListOf<Int>()
    for (i in 0..n-1) {
        list.add(readln().toInt())
    }
    print("${list[n-1]} ")
    for (i in 0..n-2) {
        print("${list[i]} ")
    }
}