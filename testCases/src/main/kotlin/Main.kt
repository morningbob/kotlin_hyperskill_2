fun main(args: Array<String>) {
    //println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    //println("Program arguments: ${args.joinToString()}")
    val string = "A_modern_programming_language_that_makes_developers_happier"
    println(getCamelStyleString(string))
}

fun getCamelStyleString(inputString: String): String {
    // put your code here
    //val list = inputString.split("-")
    //val needle = ""
    val result = inputString.replace("_[a-zA-Z]".toRegex()) {
        //needle = it.value
        it.value.toUpperCase().substring(1)
    }
    return result
}