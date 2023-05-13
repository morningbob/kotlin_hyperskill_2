package contacts

import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.pow
import kotlin.system.exitProcess




fun main() {

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    var contacts = listOf<BaseContact>()

    //var action : String? = null
    var shouldContinue = true

    while (shouldContinue) {
        val action = showMenu()
        if (action.toLowerCase() == "exit") {
            shouldContinue = false
            break
        }
        contacts = parseAction(action, contacts)
        //val pair = parseAction(action, contacts)
        //contacts = pair.first
        //shouldContinue = pair.second

    }
}

class Person : BaseContact() {
    override var isPerson = true

    private var surname = ""
    private var birthday = ""
    private var gender = ""

    fun setSurname(lastname: String) {
        if (lastname != "") {
            surname = lastname
        }
    }
    fun getSurname() : String {
        return surname
    }

    fun getBirthday() : String {
        return birthday
    }
    fun setBirthday(day: String) {
        birthday = day
    }

    fun getGender() : String {
        return gender
    }
    fun setGender(type: String) {
        gender = type
    }

    override fun propertiesAvailable(): List<String> {
        return listOf(
            "name", "surname", "birthday", "gender", "number"
        )
    }

    override fun getProperty(property: String): String {
        return when (property) {
            "name" -> getName()
            "surname" -> getSurname()
            "birthday" -> getBirthday()
            "gender" -> getGender()
            "number" -> getNumber()
            else -> ""
        }
    }

    override fun setProperty(property: String, newValue: String) {
        when (property) {
            "name" -> setName(newValue)
            "surname" -> setSurname(newValue)
            "birthday" -> setBirthday(newValue)
            "gender" -> setGender(newValue)
            "number" -> setNumber(newValue)
        }
    }

}

class Organization : BaseContact() {
    override var isPerson = false
    private var address = ""

    fun setAddress(add: String) {
        if (add != "") {
            address = add
        }
    }
    fun getAddress() : String {
        return address
    }

    override fun propertiesAvailable(): List<String> {
        return listOf(
            "name", "surname", "birthday", "gender", "number"
        )
    }

    override fun getProperty(property: String): String {
        return when (property) {
            "name" -> getName()
            "address" -> getAddress()
            "number" -> getNumber()
            else -> ""
        }
    }

    override fun setProperty(property: String, newValue: String) {
        when (property) {
            "name" -> setName(newValue)
            "address" -> setAddress(newValue)
            "number" -> setNumber(newValue)
        }
    }
}

open class BaseContact {
    var name: String = ""
    var number: String = ""
    open var isPerson: Boolean = false
    var created: String = ""
    var edited: String = ""

    @JvmName("setTheName")
    fun setName(firstname: String) {
        if (firstname != "") {
            name = firstname
        }
    }
    @JvmName("getTheName")
    fun getName() : String {
        return name
    }
    @JvmName("setTheNumber")
    fun setNumber(num: String) {
        if (checkPhone(num)) {
            number = num
        } else {
            number = ""
        }
    }
    @JvmName("getTheNumber")
    fun getNumber() : String {
        return number
    }

    open fun propertiesAvailable() : List<String> {
        return listOf()
    }

    open fun setProperty(property: String, newValue: String) {

    }

    open fun getProperty(property: String) : String {
        return ""
    }
}

private fun showMenu() : String {
    println()
    println("[menu] Enter action (add, list, search, count, exit):")
    return readln()
}

private fun parseAction(action: String, contacts: List<BaseContact>,
                        ) : List<BaseContact> {

    when (action.toLowerCase()) {
        Action.ADD.name.toLowerCase() -> {
            //println("matched add")
            return addRecord(contacts)
        }
        Action.SEARCH.name.toLowerCase() -> {
            searchRecord(contacts)
        }

        Action.COUNT.name.toLowerCase() -> {
            println("The Phone Book has ${contacts.size} records.")

        }
        Action.LIST.name.toLowerCase() -> {
            if (contacts.isEmpty()) {
                println("No records to list!")
            } else {
                listRecord(contacts)
            }
        }
        Action.EXIT.name.toLowerCase() -> {
            //Pair(contacts, false)
            System.exit(1)
            //exitProcess(1)
        }
        else -> 0
    }

    return contacts
}

enum class Action {
    ADD,
    REMOVE,
    EDIT,
    COUNT,
    LIST,
    SEARCH,
    EXIT
}

private fun addRecord(contacts: List<BaseContact>) : List<BaseContact> {
    println("Enter the type (person, organization): ")
    val type = readln()
    var listContacts = listOf<BaseContact>()

    when (type) {
        "person" -> {
            listContacts = addPerson(contacts)
        }
        "organization" -> {
            listContacts = addOrganization(contacts)
        }
    }

    return listContacts
}

private fun addPerson(contacts: List<BaseContact>) : List<BaseContact> {
    println("Enter the name of the person: ")
    val name = readln()
    println("Enter the surname of the person: ")
    val surname = readln()
    println("Enter the birth date: ")
    val birthday = readln()
    if (birthday == "") {
        println("Bad birth date!")
    }
    println("Enter the gender (M, F): ")
    val gender = readln()
    if (gender != "M" && gender != "F") {
        println("Bad gender!")
    }
    println("Enter the number: ")
    val phone = readln()

    println("A record added!")
    //println("A Phone Book with a single record created!")
    val person = Person()
    person.setName(name)
    person.setSurname(surname)
    person.setNumber(phone)
    person.setBirthday(birthday)
    person.setGender(gender)

    person.created = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString()
    person.edited = person.created
    val list = contacts.toMutableList()
    list.add(person)

    return list
}

private fun addOrganization(contacts: List<BaseContact>) : List<BaseContact> {
    println("Enter the organization name: ")
    val name = readln()
    println("Enter the address: ")
    val address = readln()
    println("Enter the number: ")
    val number = readln()

    val org = Organization()
    org.setName(name)
    org.setAddress(address)
    org.setNumber(number)

    val time = Instant.now().truncatedTo(ChronoUnit.MINUTES).toString()//.substring(0, )
    val timeString = time.substring(0, time.length-4)
    org.created =
        LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString()
    org.edited = org.created
    println("A record added!")
    //println("A Phone Book with a single record created!")
    val list = contacts.toMutableList()
    //val list =
    list.add(org)

    return list
}

private fun checkPhone(phone: String) : Boolean {
    val parts = phone.split("\\s|-".toRegex())

    val firstPart = "\\+?\\(?[0-9a-zA-Z]+\\)?".toRegex()
    val firstParenthesisTest = "\\+?\\([0-9a-zA-Z]+\\)".toRegex()
    val parenthesisTest = "\\([0-9a-zA-Z]+\\)".toRegex()
    val digitPart = "[0-9a-zA-Z][0-9a-zA-Z]+".toRegex()

    if (parts.isEmpty()) {
        return false
    }
    if (parts.size == 1) {
        return parts[0].matches(firstPart)
    } else if (parts.size == 2) {
        return !(!parts[0].matches(firstPart) || parts[0].matches(firstParenthesisTest) && parts[1].matches(parenthesisTest) ||
                (!parts[1].matches(digitPart) && !parts[1].matches(parenthesisTest)))
    } else {
        return !(!parts[0].matches(firstPart) || parts[0].matches(firstParenthesisTest) && parts[1].matches(parenthesisTest) ||
                !parts[2].matches(digitPart) || (!parts[1].matches(digitPart) && !parts[1].matches(parenthesisTest)))
    }
}

private fun searchRecord(contacts: List<BaseContact>) {
    println("Enter search query: ")
    val query = readln()
    val result = RabinKarp(query, contacts)
    println("Found ${result.size} results.")
    val indexMap = recordsToChoose(contacts, result)

    println("[search] Enter action ([number], back, again): ")
    val searchAction = readln()
    parseSearchAction(contacts, searchAction)
}

private fun parseSearchAction(contacts: List<BaseContact>, action: String, indexMap: Map<Int, Int> = mapOf()) {
    // check if it is a number first
    var choice = 0
    try {
        choice = action.toInt()
    } catch (e: NumberFormatException) {
        println("tested, not a number")
        when (action) {
            "back" -> {
                parseAction(showMenu(), contacts)
                return
            }
            "again" -> {
                searchRecord(contacts)
                return
            }
        }
    }
    if (choice != 0) {
        listDetails(contacts, choice - 1, indexMap)
    }

}
private fun RabinKarp(pattern: String, records: List<BaseContact>) : List<Int> {

    val factor = 3
    val modular = 11
    // we keep a list of index of the records which the search term is found
    val foundIndex = mutableListOf<Int>()
    val processedPattern = pattern.toLowerCase()

    for (index in 0..records.size - 1) {
        var textToBeCut = records[index].name.toLowerCase()
        //println("text to be cut ${records[index].name}")

        val patternHash = computeHash(pattern.toLowerCase(), factor, modular)
        //println("pattern hash $patternHash")

        var previousSubstring = records[index].name.substring(
            records[index].name.length - pattern.length, records[index].name.length
        ).toLowerCase()
        //println("first substring $previousSubstring")
        var previousHash = computeHash(previousSubstring, factor, modular)
        //println("first hash $previousHash")
        textToBeCut = textToBeCut.dropLast(1)
        //println("text before while loop $textToBeCut")

        while (true) {
            //println("$substring's hash $previousHash")
            if (patternHash == previousHash) {
                // compare characters
                for (i in 0..pattern.length - 1) {
                    if (pattern[i].toLowerCase() != previousSubstring[i]) {
                        //println("different when comparing actual char")
                        break
                    } else {
                        if (i == pattern.length - 1) {
                            // here when we found a match, we keep the index of the record
                            //println("found match, index $index")
                            foundIndex.add(index)
                            //return true
                        }
                    }
                }

            }
            // put the termination condition here because we have to run
            // the above one more time
            if ((textToBeCut.length - pattern.length < 0)) {
                //println("text to be cut is too short")
                break
            }
            val newSubstring = records[index].name.substring(
                textToBeCut.length - pattern.length, textToBeCut.length
            ).toLowerCase()
            //println("substring inside while loop $newSubstring")
            //println("substring dealing with $substring")
            //println("calculated hash ${computeHash(substring, factor, modular, letterMap)}")
            previousHash = computeNewHashFromOldHash(
                previousHash, previousSubstring, newSubstring,
                factor, modular
            )
            //println("previous hash inside while loop $previousHash")
            previousSubstring = newSubstring
            textToBeCut = textToBeCut.dropLast(1)
            //println("text before another while loop $textToBeCut")
        }
    }
    //println("finished comparing all hashes, no match")
    return foundIndex
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

    return progress.mod(modular) //% modular
}

private fun <BaseContact> recordsToChoose(entities: List<BaseContact>, recordsToList: List<Int> = emptyList())
    : Map<Int, Int> {

    var listRecords = mutableListOf<Int>()
    val indexMapRecords = mutableMapOf<Int, Int>()

    if (recordsToList.isEmpty()) {
        for (i in 0..entities.size - 1) {
            listRecords.add(i)
        }
    } else {
        listRecords = recordsToList.toMutableList()
    }
    var indexDisplay = 0
    for (indexRecord in listRecords) {
        indexDisplay++
        indexMapRecords.put(indexDisplay, indexRecord)
        val contact = entities[indexRecord] as contacts.BaseContact
        if (contact.isPerson) {
            contact as Person
            println("${indexDisplay}. ${contact.getName()} " +
                    "${contact.getSurname()}")
        } else {
            contact as Organization
            println("${indexDisplay}. ${contact.getName()}")
        }
    }
    return indexMapRecords
}

private fun listRecord(contacts: List<BaseContact>, recordsToList: List<Int> = emptyList()) {

    recordsToChoose(contacts, recordsToList)

    println("[list] Enter action ([number], back): ")
    //val index = readln().toInt() - 1
    val action = readln()
    parseListAction(contacts, action)
    //listDetails(entities as List<contacts.BaseContact>, index)

}

private fun parseListAction(contacts: List<BaseContact>, action: String) {
    var choice = 0
    try {
        choice = action.toInt()
    } catch (e: NumberFormatException) {
        println("tested, not a number")
        when (action) {
            "back" -> {
                parseAction(showMenu(), contacts)
                return
            }
        }
    }
    if (choice != 0) {
        listDetails(contacts, choice - 1)
    }
}

private fun listDetails(contacts: List<BaseContact>, index: Int, indexMap: Map<Int, Int> = mapOf()) {
    var parsedIndex = 0
    // from search result, need to parse record index
    if (indexMap.isNotEmpty()) {
        parsedIndex = indexMap[index]!!
    } else {
        parsedIndex = index
    }

    val contact = contacts[parsedIndex]
    if (contact.isPerson) {
        val person = contact as Person
        println("Name: ${contact.getName()}")
        println("Surname: ${contact.getSurname()}")
        val birth = contact.getBirthday()
        print("Birth date: " )
        if (birth != "") {
            println(birth)
        } else {
            println("[no data]")
        }
        val gender = contact.getGender()
        print("Gender: ")
        if (gender != "") {
            println(gender)
        } else {
            println("[no data]")
        }
        println("Number: ${contact.getNumber()}")
        println("Time created: ${contact.created}")
        println("Time last edit: ${contact.edited}")
    } else {
        val org = contact as Organization
        println("Organization name: ${org.getName()}")
        println("Address: ${org.getAddress()}")
        println("Number: ${org.getNumber()}")
        println("Time created: ${org.created}")
        println("Time last edit: ${org.edited}")
    }

    println("")
    println("[record] Enter action (edit, delete, menu): ")
    val action = readln()
    parseRecordAction(contacts, action, parsedIndex)

}

private fun parseRecordAction(contacts: List<BaseContact>, action: String, index: Int) {
    when (action) {
        "menu" -> {
            parseAction(showMenu(), contacts)
            return
        }
        "edit" -> {
            editRecord(contacts, index)
            return
        }
        "delete" -> {
            removeRecord(contacts, index)
            return
        }
    }
}

private fun editRecord(contacts: List<BaseContact>, index: Int) : List<BaseContact> {
    //recordsToChoose(contacts)
    //println("Select a record: ")
    //val selectedRecord = readln().toInt() - 1
    var updatedList = listOf<BaseContact>()
    // test if it is a person or org , show different message
    val record = contacts[index]
    // only org has address
    if ("address" in record.propertiesAvailable()) {
        println("Select a field (name, address, number): ")
        //record as Organization

    } else {
        println("Select a field (name, surname, birth date, gender, number): ")
        //record as Person
        //val selectedFieldName = readln()
        //println("Enter $selectedFieldName: ")
        //val selectedFieldData = readln()
        //updatedList = updateField(contacts, index, selectedFieldName, selectedFieldData)
    }
    val selectedFieldName = readln()
    println("Enter $selectedFieldName: ")
    val selectedFieldData = readln()
    //updatedList = updateField(contacts, index, selectedFieldName, selectedFieldData)
    updatedList = updateField(contacts, index, selectedFieldName, selectedFieldData)

    println("Saved")
    // we need to display the updated record after saved
    listDetails(contacts, index)
    return updatedList
}

private fun updateField(contacts: List<BaseContact>, index: Int,
                        fieldName: String, fieldData: String) :
        List<BaseContact> {

    val contact = contacts[index]
    contact.setProperty(fieldName, fieldData)

    contact.edited = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString()

    val updatedList = mutableListOf<BaseContact>()
    for (each in contacts) {
        if (each.getName() != contact.getName()) {
            updatedList.add(each)
        } else {
            updatedList.add(contact)
        }
    }

    return updatedList

}

private fun removeRecord(contacts: List<BaseContact>, index: Int): List<BaseContact> {
    //listRecord(contacts)
    //println("Select a record: ")
    //val selectedRecord = readln().toInt() - 1
    val list = mutableListOf<BaseContact>()
    //list.toMutableList().removeAt(selectedRecord)
    for (i in 0..contacts.size-1) {
        if (i != index) {
            list.add(contacts[i])
        }
    }
    println("The record removed!")
    return list
}
/*
        Action.REMOVE.name.toLowerCase() -> {
            if (contacts.isEmpty()) {
                println("No records to remove!")
            } else {
                // search for the record
                return removeRecord(contacts)
            }
        }
        Action.EDIT.name.toLowerCase() -> {
            if (contacts.isEmpty()) {
                println("No records to edit!")
            } else {
                // search for the record
                return editRecord(contacts)
            }
        }

         */

