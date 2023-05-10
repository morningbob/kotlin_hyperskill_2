fun findSerialNumberForGame(listGames: List<String>) {
    val gameMap = mutableMapOf<String, List<Long>>()
    val regex = Regex("[a-zA-Z]+")
    for (each in listGames) {
        val parts = each.split("@")
        gameMap.put(parts[0], listOf(parts[1].toLong(), parts[2].toLong()))
    }
    val game = readln()
    val numbers = gameMap[game]!!
    println("Code for Xbox - ${numbers[0]}, for PC - ${numbers[1]}")
}
