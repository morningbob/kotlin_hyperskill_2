fun main() {
    // write your code here
    val n = readln().toInt()
    val list = mutableListOf<Int>()
    for (i in 0..n-1) {
        list.add(readln().toInt())
    }
    val m = readln().toInt()
    println(if (m in list) "YES" else "NO")
}