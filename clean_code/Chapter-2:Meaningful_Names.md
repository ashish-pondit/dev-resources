# Chapter 2: Meaningful Names
> Names are everywhere in software. We name our **variables**, **functions**, **arguments**, **classes** and **packages**. Because we do so much of it, we'd better do it well.


## Use Intention-Revealing Names

- Names should reveal intent

    **Wrong ❌**
    ```java
    int d; //elapsed time in days
    ```
    **Right ✅**
    ```java
    int elapsedTimeInDays;
    int daySinceCreation;
    int fileAgeInDays;
    ```
    See in the second example we did not use any comment because the variable itself reveals its intention.

    Another Example:

    **Wrong ❌**
    ```java
    public List<int[]> getThem() {
        List<int[]> list1 = new ArrayList<int[]>();
        for (int[] x : theList)
            if (x[0] == 4)
                list1.add(x);
            return list1;
    }
    ```
    The above code raises so many questions though it is a very simple code.
    Now again notice the same code with appropriate variable names.
    
    **Right ✅**
    ```java
    public List<int[]> getFlaggedCells() {
        List<int[]> flaggedCells = new ArrayList<int[]>();
        for (int[] cell: gameBoard)
            if (cell[STATUS_VALUE] == FLAGGED)
                flaggedCells.add(cell);
            return flaggedCells;
    }
    ```
    See the magic. Only by changing the variable names the code now seems to make more sense.


## Avoid Disinvormation
- We should avoid words whose entrenched meaning vary from our intended meaning.

    For example you should not refre to a grouping of accounts as `accountList` unless it's actually a `List`. 

    So `accountGroup`, `bunchOfAccounts` or just `accounts` would be better.
- Beware of using names which are vary in small ways.
 
    **Wrong ❌**
    ```java
    String XYZControllerForEfficientHandlingOfStrings;
    String XYZControllerForEfficientStorageOfStrings;
    ```
    If you can't spot the difference between two variables that is excatly my point. Name a variable in a way that it does not create confusion.

## Make Meaningful Distinctions
- Sometimes you can't use the same name to refer to two different things in the same scope, you might be tempted to change one name in anarbitrary way. Sometimes this is done by mispellingone name.
- It is not sufficient to add number series or noise words, even though the compiler is satisfied. If names must be different then they should also mean something different.

    **Wrong ❌**
    ```java
    public static void copyChars(char a1[], char a2[]) {
        for (int i = 0; i < a1.length; i++) {
            a2[i] = a1[i];
        }
    }
    ```
    **Right ✅**
    ```java
    public static void copyChars(char source[], char destination[]) {
        for (int i = 0; i < a1.length; i++) {
            destination[i] = source[i];
        }
    }
    ```
    Changing variables from `a1`, `a2` to `source` and `destination` makes the code more readable.
- Noise words are another meaningless distinction. Imagine that you have a `Product` class. If you have another called `ProductInfo` or `ProductData`, you have made the names different without making them mean anything different. `Info` and `Data` are indistinct noie words. 
- You might use `a` for all local variables and the `the` for all function arguments. 
- Distinguish names in such a way that the reader knows what the differences offer.

## Use Pronounceable Names
If you can't pronounce it, you can't discuss it without sounding like an idiot.

**Wrong ❌**
```java
class DtaRcrd102 {
    private Date genymdhms;
    private Date modymdhms;
    private final String pszqint = "102";
    /* ... */
};
```
**Right ✅**
```java
Class Customer {
    private Date generationTimestamp;
    private Date modificationTimestamp;
    private final String recordId = "102";
    /* ... */
}
```

## Use Searchable Names
Single-letter names and numeric constants have a particular problem in that they are not easy to locate across a body of text. One might easily grep for `Max_CLASSES_PER_STUDENT`, but the number `7` could be more troublesome.


**Wrong ❌**
```java
for (int j=0; j<34; j++) {
    s += (t[j]*4) / 5;
}
```
**Right ✅**
```java
int realDayPerIdealDay = 4;
const int WORK_DAYS_PER_WEEK = 5;
int sum = 0;
for (int j=0; j < NUMBER_OF_TASKS; j++) {
    int realTaskDays = taskEstimate[j] * realDaysPerIdealDay;
    int realTaskWeeks = (realdays / WORK_DAYS_PER_WEEK);
    sum += realTaskWeeks;
}

```

## Avoid Encodings
Encoding type or scope information into names simply adds an extraburden of deciphering.

## Member Prefixes
You don't need to prefix member variables `m_` anymore.Your classes and functions sould be small enough that you don't need them. Your IDE should support highlighting variables vs functions.

## Interfaces and Implementations
These are sometimes a special case for encodings. For example, say you are building an `ABSTRACT FACTORY` for the creation of shapes. This factory will be an interface and will be implemented by a concrete class. Should you name it `IShapeFactory` ? The author suggested to leave the interface unadorned. So author suggested to encode the implementation insted of the interface calling it `ShapeFactoryImp`.

## Avoid Mental Mapping
Readers shouldn't have to mentally translate your names into other names they already know. This problem arises from a choice to sue neither problem domain terms nor solution domain terms.

This is a problem with single-letter variable names. Certainly a loop counter names `i` `j` or `k` if its scope is very small. Single letter names for loop counters are traditional. However, in most other contexts a single-letter  name is a poor choice.

## Class Names
Classes and Objects should have noud or noun phrase names like `Customer`, `WikiPage`, `Account`, and `AddressParser`.


## Method Names
- Methods should have verb or verb phrase names like `postPayment`, `deletePage`, or `save`. Accessors, mutators, and predicates should be named for their value and prefixed with `get`, `set` and `is` according to javabean standard.

- When constructors are overloaded, use static factory methods with names that describe the arguments.
    **Wrong ❌**
    ```java
    Complex fulcrumPoint = new Complex(23.0);
    ```
    **Right ✅**
    ```java
    Complex fulcrumPoint = Complex.FromRealNumber(23.0);
    ```
    Consider enforcing their use by making the corresponding constructors private.

## Don't Be Cute
If names are too clever, they will be memorable only to people who share the author's sense of humor.


## Pick One Word per Concept
Pick one word for one abstract concept and stick with it. For instance, it's confusing to have `fetch`, `retrieve`, and `get` as equivaleent methods of different classes.

## Don't Pun
Avoid using the same word for two purposes. 
For example: an `add` method. As long as the parameter lists and return values of the various `add` methods are semantically equivalent, all is well.
However one might decide to use the word `add` for "consistency" when he or she is not in fact adding in the same sense. Let's say we have many classes were `add` will create a new value by adding or concatenating two existing values. Now let's say we are writing a newclass that has a method that puts its single parameter into a collection. Should we call this method `add`? We should name it `insert` or `append`.
