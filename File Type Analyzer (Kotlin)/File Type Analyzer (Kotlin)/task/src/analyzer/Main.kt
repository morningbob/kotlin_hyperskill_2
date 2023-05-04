package analyzer

import java.io.File

fun main(args: Array<String>) {
    //println("Hello World!")
    //val args = readln().split(" ")

    //val docDict = mutableMapOf<String, String>()
    //docDict.put("%PDF-", "PDF document")

    val filename = args[0]
    val pattern = args[1]
    val type = args[2]

    val file = File(filename)
    val lines = file.readLines()

    println(searchPattern(lines, pattern, type))

}



private fun searchPattern(lines: List<String>,
                          pattern: String,
                          type: String) : String? {
    for (line in lines) {
        if (line.contains(pattern)) {
            //return "${pattern.substring(1, 4)} document"
            return type
        } else {
            return "Unknown file type"
        }
    }
    return null
}






