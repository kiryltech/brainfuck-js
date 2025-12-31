package tech.kiryl.brainfuck

enum class BrainfuckCommands(
    val cmd: Char,
    val jsCmd: String
) {
    NEXT('>', "ptr++;"),
    PREV('<', "ptr--;"),
    INC('+', "data[ptr]++;"),
    DEC('-', "data[ptr]--;"),
    JMP_START('[', "while(data[ptr]) {"),
    JMP_END(']', "}"),
    IN(',', "data[ptr] = read();"),
    OUT('.', "print(data[ptr]);"),
    ;

    companion object {
        val byCmd = entries.associateBy { it.cmd }
    }
}