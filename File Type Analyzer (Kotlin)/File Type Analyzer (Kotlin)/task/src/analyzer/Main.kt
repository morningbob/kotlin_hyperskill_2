package analyzer

import java.io.File

fun main(args: Array<String>) {
    val pattern = args[0]
    val text = args[1]
    val array = buildPrefixFunction(pattern)
    println()
    for (each in array) {
        print("$each, ")
    }

    KMP(pattern, text, array)

/*
    val algorithm = args[0]
    val filename = args[1]
    val pattern = args[2]
    val type = args[3]

    val file = File(filename)
    val lines = file.readLines()

    when (algorithm) {
        "--navie" -> println(searchNaive(lines, pattern, type))
        "--KMP" -> println()
    }
    //println()
  */

}

private fun searchKMP(lines: List<String>, pattern: String,
                      type: String) {
    // build prefix function
    // compare


}

private fun buildPrefixFunction(pattern: String) : Array<Int> {
    val p = Array<Int>(pattern.length) { 0 }
    p[0] = 0

    for (i in 1..pattern.length - 1) {
        val s = pattern.substring(0, i + 1)

        var j = p[i - 1]
        while (j > 0 && s[i] != s[j]) {
            j = p[j - 1]
        }
        if (s[i] == s[j]) {
            j++
        }
        p[i] = j
    }

    return p
}

private fun KMP(pattern: String, text: String, prefixFunction: Array<Int>) {
    var currentIndexText = 0
    //var match = false

    for (i in 0..text.length - 1) {
        // compare pattern and text
        var currentIndexPattern = 0
        var movingIndexText = currentIndexText
        while (currentIndexPattern <= pattern.length) {
            println("comparing ${text[currentIndexText]} ${pattern[currentIndexPattern]}")
            println("moving index $movingIndexText current index pattern $currentIndexPattern")
            if (text[movingIndexText] == pattern[currentIndexPattern]) {
                // a match
                println("match")
                if (currentIndexPattern == pattern.length - 1) {
                    println("complete match")
                    break
                }
                // move 1 step

                //match = true
            } else {
                println("prefix function at $currentIndexPattern value ${prefixFunction[currentIndexPattern]}")
                println("current index pattern $currentIndexPattern")
                val step = currentIndexPattern - prefixFunction[currentIndexPattern]
                currentIndexText += step
                break
            }
            println("move index one step")
            currentIndexPattern++
            movingIndexText++
        }
        // lookup prefix function for the pattern index
        currentIndexText++
        //prefixFunction[currentIndexPattern]
    }

}


private fun searchNaive(lines: List<String>,
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






