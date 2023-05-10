package contacts

fun main() {
    var contacts = listOf<Contact>()
    var action : String? = null
    while (true) {
        action = showMenu()
        if (action.toLowerCase() == "exit") {
            break
        }
        contacts = parseAction(action, contacts)
    }
}

class Contact {
    private var number = ""
    private var name = ""
    private var surname = ""

    fun setName(firstname: String) {
        if (firstname != "") {
            name = firstname
        }
    }
    fun getName() : String {
        return name
    }
    fun setSurname(lastname: String) {
        if (lastname != "") {
            surname = lastname
        }
    }
    fun getSurname() : String {
        return surname
    }

    fun setNumber(num: String) {
        if (checkPhone(num)) {
            number = num
        } else {
            number = ""
        }
    }
    fun getNumber() : String {
        return number
    }
}

private fun showMenu() : String {
    println("Enter Action (add, remove, edit, count, list, exit):")
    return readln()

}

private fun parseAction(action: String, contacts: List<Contact>) : List<Contact> {
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
            //if (contacts.isEmpty()) {
            println("The Phone Book has ${contacts.size} records.")
            //} else {
                // search for the record
            //}
        }
        Action.LIST.name.toLowerCase() -> {
            if (contacts.isEmpty()) {
                println("No records to list!")
            } else {
                listRecord(contacts)
            }
        }
        else -> 0
    }
    //output += Action.ADD.name.toLowerCase()
    return contacts
}

enum class Action {
    ADD,
    REMOVE,
    EDIT,
    COUNT,
    LIST,
    EXIT
}

private fun addRecord(contacts: List<Contact>) : List<Contact> {
    println("Enter the name of the person:")
    val name = readln()
    println("Enter the surname of the person:")
    val surname = readln()
    println("Enter the number:")
    val phone = readln()
    if (checkPhone(phone)) {
        //println("phone is valid")
    } else {
        //println("wrong format")
    }

    println("A record created!")
    println("A Phone Book with a single record created!")
    val contact = Contact()
    contact.setName(name)
    contact.setSurname(surname)
    contact.setNumber(phone)
    val list = contacts.toMutableList()
    list.add(contact)

    return list
}

private fun checkPhone(phone: String) : Boolean {
    val parts = phone.split("\\s|-".toRegex())
    //println("parts")
    //for (each in parts) {
    //    print("$each, ")
    //}
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

private fun listRecord(contacts: List<Contact>) {
    for (i in 1..contacts.size) {
        val phoneDisplay = if (contacts[i-1].getNumber() != "") contacts[i-1].getNumber() else "[no number]"
        println("$i. ${contacts[i-1].getName()} ${contacts[i-1].getSurname()}, ${phoneDisplay}")
    }
}

private fun editRecord(contacts: List<Contact>) : List<Contact> {
    listRecord(contacts)
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

private fun updateField(contacts: List<Contact>, index: Int, fieldName: String, fieldData: String) : List<Contact> {

    //val updatedList = emptyList<Contact>()
    when (fieldName) {
        "name" -> {
            contacts[index].setName(fieldData)
        }
        "surname" -> {
            contacts[index].setSurname(fieldData)
        }
        "number" -> {
            contacts[index].setNumber(fieldData)
        }
    }
    val updatedList = contacts
    return updatedList

}

private fun removeRecord(contacts: List<Contact>): List<Contact> {
    listRecord(contacts)
    println("Select a record: ")
    val selectedRecord = readln().toInt() - 1
    val list = mutableListOf<Contact>()
    //list.toMutableList().removeAt(selectedRecord)
    for (i in 0..contacts.size-1) {
        if (i != selectedRecord) {
            list.add(contacts[i])
        }
    }
    println("The record removed!")
    return list
}

