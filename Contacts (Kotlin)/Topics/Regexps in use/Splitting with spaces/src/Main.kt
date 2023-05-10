fun main() {

    val string = readLine()!!
    val n = readLine()!!.toInt()

    //val resultList = string.split("\\s".toRegex())

    val result = string.split("\\s+".toRegex(), n)

    for (each in result) {
        println(each)
    }


/*
    //val regex = Regex("")
    var num = 0
    if (n == 0) {
        num = resultList.size
    } else {
        num = n
    }
    
    //if (num <= resultList.size) {
        for (i in 0..num-2) {
            //resultlist[i]
            if (!"\\s".toRegex().matches(resultList[i])) {
                println(resultList[i])
            }
            //if (resultList[i] != " ") {
            //    println(resultList[i])
            //}
        }
        //println(resultList[num-1].value)
        for (i in num-1..resultList.size-1) {
            print("${resultList[i]} ")
        }
    //}
*/
}
