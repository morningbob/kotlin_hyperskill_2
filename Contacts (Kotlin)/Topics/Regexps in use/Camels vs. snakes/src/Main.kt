


fun getCamelStyleString(inputString: String): String {
    // put your code here
    //val list = inputString.split("-")
    //val needle = ""
    val result = inputString.replace("_[a-zA-Z]".toRegex()) {
        //needle = it.value
        it.value.toUpperCase().substring(1)
    }
    val new = result[0].toUpperCase() + result.substring(1)
    return new
}
