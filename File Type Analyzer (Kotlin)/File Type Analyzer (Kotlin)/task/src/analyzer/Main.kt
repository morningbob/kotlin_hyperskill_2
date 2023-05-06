package analyzer

import java.io.File
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
/*
    val pattern = args[0]
    //println("pattern $pattern")

    val text = args[1]
    //println("text $text")
    val type = args[2]

    val array = buildPrefixFunction(pattern)
    println()
    for (each in array) {
        print("$each, ")
    }

    println(searchKMP(text, pattern, type))

 */

    val algorithm = args[0]
    val filename = args[1]
    val pattern = args[2]
    val type = args[3]

    val file = File(filename)
    val text = file.readText()

    when (algorithm) {
        "--naive" -> println(searchNaive(text, pattern, type))
        "--KMP" -> println(searchKMP(text, pattern, type))
    }

}

private fun searchKMP(text: String, pattern: String,
                      type: String) : String {
    // build prefix function
    // compare
    var output = ""
    val start = System.nanoTime()
    val prefix = buildPrefixFunction(pattern)
    val result = KMP(pattern, text, prefix)
    val end = System.nanoTime()
    if (result) {
        //println("result is $result")
        output += "$type\n"
    } else {
        output += "Unknown file type\n"
    }
    val time = formatTime(start, end)
    output += "It took ${time} seconds"

    return output
}

private fun formatTime(start: Long, end: Long) : String {
    val elapsedTime = end - start
    //val formattedTime = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS)
    //val formattedTime = TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS) / 1000.0
    val formattedTime = elapsedTime.toDouble() / 1E9
    return formattedTime.toString()
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

private fun KMP(pattern: String, text: String, prefixFunction: Array<Int>) : Boolean {
    var currentIndexText = 0

    while (currentIndexText < text.length) {
        var currentIndexPattern = 0
        var movingIndexText = currentIndexText
        //println("outter while")
        //println("current index text $currentIndexText")
        while (currentIndexPattern < pattern.length && movingIndexText < text.length) {
            //println("inner while")
            //println("comparing ${text[movingIndexText]} ${pattern[currentIndexPattern]}")
            //println("currentIndex $currentIndexPattern movingIndex $movingIndexText")
            //println("moving index $movingIndexText current index pattern $currentIndexPattern")
            if (text[movingIndexText] == pattern[currentIndexPattern]) {
                if (currentIndexPattern == pattern.length - 1) {
                    return true
                }
                currentIndexPattern += 1
                movingIndexText += 1

            } else {
                //println("prefix function at $currentIndexPattern value ${prefixFunction[currentIndexPattern]}")
                //println("current index pattern $currentIndexPattern")
                var step = 0
                if (currentIndexPattern > 0) {
                    step = currentIndexPattern - prefixFunction[currentIndexPattern - 1]
                }
                if (step > 0) {
                    currentIndexText += step - 1
                }
                break
            }

        }
        currentIndexText++
    }
    return false
}
private fun searchNaive(text: String,
                        pattern: String,
                          type: String) : String {

    var output = ""
    val start = System.nanoTime()

    val result = naive(text, pattern)
    val end = System.nanoTime()

    if (result) {
        output += "$type\n"
    } else {
        output += "Unknown file type\n"
    }
    val time = formatTime(start, end)
    output += "It took ${time} seconds"

    return output
}

private fun naive(text: String,
                  pattern: String,) : Boolean {
    for (currentIndexText in 0..text.length - 1) {
        var currentIndexPattern = 0
        var movingIndex = currentIndexText
        while (true) {
            if (pattern[currentIndexPattern] == text[movingIndex]) {
                movingIndex++
                currentIndexPattern++
                if (currentIndexPattern == pattern.length - 1) {
                    return true
                }
            } else {
                break
            }
        }
    }
    return false
}






