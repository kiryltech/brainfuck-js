## Brainfuck to JavaScript compiler

### Usage

* Compiling a Brainfuck file:
    ```sh
    $ mvn clean package exec:java -Dexec.args=src/test/resources/brainfuck/helloworld.bf
    ```
* Running compiled code:
    ```sh
    $ node --inspect-brk target/helloworld.js
    ```
