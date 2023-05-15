package cryptography


import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    while (true) {
        println("Task (hide, show, exit): ")
        val input = readln()
        parseCommand(input)
    }
}

private fun parseCommand(command: String) {
    when (command.toLowerCase()) {
        Command.HIDE.name.toLowerCase() -> {
            //println("Hiding message in image.")
            val filenames = getFilenames()
            //val inputFile =
            val inputImage = retrieveImage(filenames[0])
            hideMessage(inputImage)
        }
        Command.SHOW.name.toLowerCase() -> {
            println("Obtaining message from image.")
        }
        Command.EXIT.name.toLowerCase() -> {
            println("Bye!")
            System.exit(0)
        }
        else -> {
            println("Wrong task: $command")
        }
    }
}

private fun getFilenames() : List<String> {
    println("Input image file: ")
    val inputFileName = readln()
    println("Output image file: ")
    val outputFileName = readln()
    println("Input Image: $inputFileName")
    println("Output Image: $outputFileName")
    val filenames = listOf<String>(inputFileName, outputFileName)
    return filenames
}

private fun hideMessage(originalImage: BufferedImage) : BufferedImage {
    // loops for getting the pixel
    // turn the least significant bit to 1
    // save the pixel back to the image
    // return the image
    for (row in 0..originalImage.height - 1) {
        for (column in 0..originalImage.width - 1) {
            val pixel = originalImage.getRGB(column, row)
            var color = Color(pixel)
            var red = color.red
            var green = color.green
            var blue = color.blue
            red = modifyBit(red)
            green = modifyBit(green)
            blue = modifyBit(blue)
            //val modifiedColor = Color(r = red, g = green, b = blue)
            val newColor = intArrayOf(red, green, blue)
            originalImage.setRGB(column, row, 1, 1, newColor, 0, 0)
            break
        }
        break
    }
    return originalImage
}

private fun modifyBit(num: Int) : Int {
    val binaryForm = Integer.toBinaryString(num)
    //println("binaryForm $binaryForm")
    val changedBinary = binaryForm.substring(0..binaryForm.length - 2) + '1'
    //println("changedBinary $changedBinary")
    //println("new integer ${changedBinary.toInt(2)}")

    return changedBinary.toInt(2)
}

//private fun

private fun retrieveImage(filename: String) : BufferedImage {
    val file = File(filename)
    return ImageIO.read(file)
}
enum class Command {
    HIDE,
    SHOW,
//    TASK,
    EXIT
}

