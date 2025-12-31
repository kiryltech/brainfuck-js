# Brainfuck to JavaScript Compiler

A Brainfuck compiler written in Kotlin that targets JavaScript (Node.js).

**Key Feature:** This compiler generates **Source Maps** (v3 with VLQ encoding). This allows you to debug your original Brainfuck source code directly in Chrome DevTools or VS Code, stepping through the BF commands line-by-line while executing the generated JavaScript.

## 🚀 Features

*   **Compiles to Node.js compatible JavaScript**: Generates standalone JS files.
*   **Source Maps Support**: Maps generated JS instructions back to the original Brainfuck source positions.
*   **Standard Compliant**:
    *   **30,000 cell** memory tape.
    *   **Synchronous Input/Output** support (`,` and `.`).
*   **Modern Stack**: Built with Kotlin 1.9.

## 🛠 Prerequisites

*   Java JDK 8+
*   Maven
*   Node.js (to run the output)

## 📦 Building

```bash
mvn clean package
```

## 💻 Usage

### 1. Compile a Brainfuck program

Run the compiler using Maven, providing the path to your source file:

```bash
mvn exec:java -Dexec.args=src/test/resources/brainfuck/helloworld.bf
```

This creates a `.js` file in the `target/` directory (e.g., `target/helloworld.js`).

### 2. Run the generated code

```bash
node target/helloworld.js
```

### 3. Input Example (Cat/Echo)

The compiler supports input streaming using the `,` command.

```bash
# Compile the included cat.bf
mvn exec:java -Dexec.args=src/test/resources/brainfuck/cat.bf

# Pipe input into the generated script
echo "Hello Brainfuck!" | node target/cat.js
```

## 🐞 Debugging with Source Maps

To debug your Brainfuck code visually:

1.  **Run with Inspector:**
    Start Node.js with the `--inspect-brk` flag to pause at the beginning.
    ```bash
    node --inspect-brk target/helloworld.js
    ```

2.  **Open Chrome DevTools:**
    *   Open Chrome and go to `chrome://inspect`.
    *   Click **"inspect"** under "Remote Target".

3.  **Step Through Code:**
    *   Navigate to the **Sources** tab.
    *   Locate your `.bf` file in the file tree (loaded via the source map).
    *   You can now step through your Brainfuck code command by command!

## 🧪 Running Tests

```bash
mvn test
```