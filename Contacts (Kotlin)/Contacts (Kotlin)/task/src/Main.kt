package contacts

import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun main() {
    var contacts = listOf<BaseContact>()

    var action : String? = null
    while (true) {
        action = showMenu()
        if (action.toLowerCase() == "exit") {
            break
        }
        contacts = parseAction(action, contacts)

        println()
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
}

private fun showMenu() : String {
    println("Enter Action (add, remove, edit, count, info, exit):")
    return readln()

}

private fun parseAction(action: String, contacts: List<BaseContact>,
                        ) : List<BaseContact>{
    //var output = ""

    when (action.toLowerCase()) {
        Action.ADD.name.toLowerCase() -> {
            //println("matched add")
            return addRecord(contacts)
        }
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
        Action.COUNT.name.toLowerCase() -> {
            println("The Phone Book has ${contacts.size} records.")

        }
        Action.INFO.name.toLowerCase() -> {
            if (contacts.isEmpty()) {
                println("No records to list!")
            } else {
                listRecord(contacts)
            }
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
    INFO,
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

    val time = Instant.now().truncatedTo(ChronoUnit.MINUTES).toString()//.substring(0, )
    //val timeString = time.substring(0, time.length-4)
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

private fun <BaseContact> recordsToChoose(entities: List<BaseContact>) {
    for (i in 1..entities.size) {
        val contact = entities[i-1] as contacts.BaseContact
        if (contact.isPerson) {
            contact as Person
            println("$i. ${contact.getName()} " +
                    "${contact.getSurname()}")
        } else {
            contact as Organization
            println("$i. ${contact.getName()}")
        }
        //println("$i. ${(entities[i - 1] as contacts.BaseContact).getName()} " +
        //        "${(entities[i-1] as contacts.BaseContact).get}")
    }
}

private fun <BaseContact> listRecord(entities: List<BaseContact>) {

    recordsToChoose(entities)

    println("Select a record: ")
    val index = readln().toInt() - 1

    listDetails(entities as List<contacts.BaseContact>, index)

}

private fun listDetails(contacts: List<BaseContact>, index: Int) {

    val contact = contacts[index]
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

}

private fun editRecord(contacts: List<BaseContact>) : List<BaseContact> {
    recordsToChoose(contacts)
    println("Select a record: ")
    val selectedRecord = readln().toInt() - 1

    println("Select a field (name, surname, number): ")
    val selectedFieldName = readln()
    println("Enter $selectedFieldName: ")
    val selectedFieldData = readln()

    val updatedList = updateField(contacts, selectedRecord, selectedFieldName, selectedFieldData)

    println("The record updated!")
    return updatedList
}

private fun updateField(contacts: List<BaseContact>,
                        index: Int, fieldName: String, fieldData: String) :
        List<BaseContact> {

    val contact = contacts[index]
    if (contact.isPerson) {
        contact as Person
    } else {
        contact as Organization
    }

    when (fieldName) {
        "name" -> {
            contact.setName(fieldData)
        }
        "surname" -> {
            (contact as Person).setSurname(fieldData)
        }
        "number" -> {
            contact.setNumber(fieldData)
        }
        "birthday" -> {
            (contact as Person).setBirthday(fieldData)
        }
        "gender" -> {
            (contact as Person).setGender(fieldData)
        }
        "address" -> {
            (contact as Organization).setAddress(fieldData)
        }
    }
    val time = Instant.now().truncatedTo(ChronoUnit.MINUTES).toString()//.substring(0, )
    val timeString = time.substring(0, time.length-4)
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

private fun removeRecord(contacts: List<BaseContact>): List<BaseContact> {
    listRecord(contacts)
    println("Select a record: ")
    val selectedRecord = readln().toInt() - 1
    val list = mutableListOf<BaseContact>()
    //list.toMutableList().removeAt(selectedRecord)
    for (i in 0..contacts.size-1) {
        if (i != selectedRecord) {
            list.add(contacts[i])
        }
    }
    println("The record removed!")
    return list
}

