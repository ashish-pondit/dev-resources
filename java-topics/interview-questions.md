# Java Interview Questions

## Questions

### 1. Why would it be more secure to store sensitive data in a character array rather than in a String?

Answer: 
- Strings are **immutable** and stored in string pool
- So if a string is created, it stays in the pool memory until being garbage collected
- Even if you are done processing the string value, it remains in the memory for sometime
- Anyone having access a memory dump can potentially extract the sensitive data
- But if you use mutable objects like a character array you can clear it afterwards



### 2. How can you swap the values of two numeric variables without using any other variables?

```
a = a + b
b = a - b
a = a - b

Using XOR

a = a xor b
b = b xor a
a = a xor b
```

### 3. What variance is imposed on generic type parameters? How much control does Java give you over this?


### 4. What does it mean for a collection to be “backed by” another? Give an example of when this property is useful.