fun main() {
    val text = readLine()!!
    val regexColors = "#[0-9a-fA-F]{6}\\b".toRegex()

    // write your code here
    val matches = regexColors.findAll(text)
    for (each in matches) {
        println(each.value)
    }
}
