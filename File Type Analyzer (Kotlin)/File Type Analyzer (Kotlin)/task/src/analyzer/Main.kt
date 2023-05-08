package analyzer

import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.math.pow

fun main(args: Array<String>) {
/*
    val (pattern, text) = args
    println(RabinKarp(pattern, text))


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
*/

    val (path, patternFile) = args
    val patternList = File(patternFile).readLines()
    val patterns = createPatternList(patternList)
    val sortedPatterns = rankPatterns(patterns)

    //val (path, pattern, type) = args
    val files = File(path).listFiles()
    var output = ""

    val mainThread = Thread() {
        for (file in files) {
            //println("starting ${file.name} process")
            val thread = Thread() {
                val text = file.readText()
                //output += "${file.name}: ${searchKMP(text, pattern)}"
                output += search(text, sortedPatterns, file.name)
                //println("waiting for ${file.name}")
            }

            thread.start()
            thread.join()
        }
    }
    mainThread.start()
    mainThread.join()
    println(output)

}

private fun createPatternList(patterns: List<String>) : List<Pattern> {
    val listPattern = mutableListOf<Pattern>()
    for (pattern in patterns) {
        val (priority, patternFormat, name) = pattern.split(";")
        //println("name $name, pattern $patternFormat, priority $priority")
        val newPattern = Pattern(
            pattern = patternFormat.substring(1, patternFormat.length - 1),
            name = name.substring(1, name.length - 1),
            priority = priority.toInt())
        listPattern.add(newPattern)
    }
    return listPattern

}

private fun rankPatterns(patterns: List<Pattern>) : List<Pattern> {
    return patterns.sortedByDescending { it.priority }
}

data class Pattern(val pattern: String, val name: String, val priority: Int)

private fun search(text: String, patterns: List<Pattern>, filename: String
                      ) : String {
    // build prefix function
    // compare
    var output = ""
    //val start = System.nanoTime()
    //var found = false
    for (pattern in patterns) {
        //while (true) {
            //val prefix = buildPrefixFunction(pattern.pattern)
            //println("KMP pattern ${pattern.pattern}")
            //val result = KMP(pattern.pattern, text, prefix)
            //val end = System.nanoTime()
        val result = RabinKarp(pattern.pattern, text)
        if (result) {
            return "$filename: ${pattern.name}\n"
            //println("result is $result")
            //output += "$type\n"
        } else {
            //output += "$filename not ${pattern.name} with pattern ${pattern.pattern}\n"
            //output += "$filename: Unknown file type\n"
        }
        //}
        //val time = formatTime(start, end)
        //output += "It took ${time} seconds"
    }
    // so we didn't find it
    output += "$filename: Unknown file type\n"

    return output
}

private fun RabinKarp(pattern: String, text: String) : Boolean {

    val factor = 3
    val modular = 11
    var textToBeCut = text

    //val letterMap = mutableMapOf<Char, Int>()
    //var i = 1
    //for (each in "ABCDEFGHIJKLMNOPQRSTUVWXYZ") {
    //    letterMap.put(each, i)
    //    i++
    //}
    //var different = false

    val patternHash = computeHash(pattern, factor, modular)

    // test if the text length > pattern length, if not, return false
    if (text.length < pattern.length) {
        return false
    }
    //var currentPosition = (text.length - pattern.length)
    //var endIndex = 1
    var previousSubstring = text.substring(
        text.length - pattern.length, text.length)
    println("first substring $previousSubstring")
    var previousHash = computeHash(previousSubstring, factor, modular)
    //println("first hash $previousHash")
    textToBeCut = textToBeCut.dropLast(1)
    println("text before while loop $textToBeCut")
    //while (currentPosition >= 0 && (text.length - pattern.length - endIndex >= 0)) {
    //    val substring = text.substring(
    //        text.length - pattern.length - endIndex, text.length - endIndex)
    while (true) {
        //println("$substring's hash $previousHash")
        if (patternHash == previousHash) {
            // compare characters
            for (i in 0..pattern.length - 1) {
                if (pattern[i] != previousSubstring[i]) {
                    //println("different when comparing actual char")
                    //return false
                    //different = true
                    break
                } else {
                    if (i == pattern.length - 1) {
                        return true
                    }
                }
            }
            //println("found a match")
            //return true
        }
        // put the termination condition here because we have to run
        // the above one more time
        if ((textToBeCut.length - pattern.length < 0)) {
            break
        }
        val newSubstring = text.substring(
            textToBeCut.length - pattern.length, textToBeCut.length)
        println("substring inside while loop $newSubstring")
        //println("substring dealing with $substring")
        //println("calculated hash ${computeHash(substring, factor, modular, letterMap)}")
        previousHash = computeNewHashFromOldHash(previousHash, previousSubstring, newSubstring,
            factor, modular)
        previousSubstring = newSubstring
        textToBeCut = textToBeCut.dropLast(1)
        println("text before another while loop $textToBeCut")
        //currentPosition -= pattern.length
        //endIndex += pattern.length
    }
    //println("finished comparing all hashes, no match")
    return false
}


private fun computeHash(substring: String, factor: Int, modular: Int) : Int {
    var sum = 0.0
    for (i in 0..substring.length - 1) {
        //sum += letterMap[substring[i]]!! * factor.toDouble().pow(i)
        sum += substring[i].code * factor.toDouble().pow(i)
        //println("char ${substring[i]} sum $sum")

    }
    val hash = sum.toInt().mod(modular) //% modular
    //println(hash)
    return hash
}

private fun computeNewHashFromOldHash(oldHash: Int, oldSubstring: String,
                                      newSubstring: String, factor: Int,
                                      modular: Int) : Int {
    val progress = (oldHash - (oldSubstring.last().code *
            factor.toDouble().pow(oldSubstring.length - 1).toInt())) *
            factor +
            newSubstring.first().code
    //println("old hash $oldHash - ${letterMap[oldSubstring.last()]!!} * " +
    //        "${factor.toDouble().pow(oldSubstring.length - 1)} * $factor " +
    //        "+ ${letterMap[newSubstring.first()]!!}")
    return progress.mod(modular) //% modular
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
/*
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
*/





