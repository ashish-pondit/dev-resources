# Exception Handling in Java
![](./images/exception_handling.jpeg)

# Contents
1. [Introduction]()
2. [Types of Exceptions]()
3. [Exception Handling Mechanisms]()
4. [Common Exception Classes]()
5. [Best Practices for Exception Handling]()
6. [Custom Exceptions]()
7. [Conclusion]()
8. [Bonus]()

# Introduction
> an event, which occurs during the execution of a program, that disrupts the normal flow of the program's instructions. 

    When an error occurs within a method, the method creates an object and hands it off to the runtime system.

### Why do we need exception handling?
- To avoid program / system crash

    ```java
    public int stringToNumber(String numberString){
        int number;
        try{
            number = Integer.parseInt(numberString);
        } catch (NumberFormatException e){
            System.out.println("caught number format exception");
            number = 0;
        }
        return number;
    }
    ```

# Types of Exceptions
## Checked Exceptions
- Definition
- Examples
## Unchecked Exceptions
- Definition
- Examples

# Exception Handling Mechanisms
## try-catch block:

**Basic Syntax**
```java
try {
    // code that may cause exception
} catch (Throwable ex){
    // Action to be preformed when exception arises
}
```

- The code that may cause exception goes inside this block goes inside `try` block
- The action that is preformed when the exception arises goes inside `catch` block
- There can be multiple `catch` blocks for each `try` block
    ```java
    try {
        // code that may cause exception
    } catch (ExceptionType ex){ // first catch block
        
    }catch (AnotherExceptionType ex){ // second catch block
        
    }
    ```
- The `catch` syntax takes an argument which denotes the type of exception and since there can be multiple types of exception hence multiple `catch` block allowed

    ```java
    try{
        // some code
    } catch (IndexOutOfBoundsException e){
        System.out.println("Caught index out of bounds exception");
    } catch (IOException e){
        System.out.println("Caught IOException");
    } 
    ```
- After Java 7 a catch block can handle multiple exceptions
    ```java
    try{
        // some code
    } catch (IndexOutOfBoundsException | IOException e){
        System.out.println("Caught an exception");
    }
    ```
 

## Finally block:
- No matter if an exception occurs or not this block will always be executed
```java
    try {
        // code that may cause exception
    } catch (Throwable ex){
        // Action to be preformed when exception arises
    } finally {
        // this block will always be executed
    }
```

- Purpose and usage
- Code execution in finally block

## Throwing exceptions
- Re-throwing exceptions

# Common Exception Classes:

- java.lang.Exception: Base class for most exceptions
- ArithmeticException: Division by zero, etc.
- NullPointerException: Accessing null objects
- ArrayIndexOutOfBoundsException: Accessing invalid array indexes
- FileNotFoundException: File not found
- IOException: Input/output errors (checked)

# Best Practices for Exception Handling:

- Try to handle specific exceptions instead of generic ones.
- Use finally block for resource cleanup (closing files, connections, etc.).
- Don't abuse exception handling for normal program flow.
- Consider using try-with-resources statement (Java 7+) for automatic resource management.



# References
- [What is an exception? (Oracle docs)](https://docs.oracle.com/javase/tutorial/essential/exceptions/definition.html#:~:text=Definition%3A%20An%20exception%20is%20an,off%20to%20the%20runtime%20system.)


