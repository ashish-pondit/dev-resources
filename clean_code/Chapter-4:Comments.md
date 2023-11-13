# Chapter 4: Comments

> "Don't comment bad code -- rewrite it"
                                -- Brian W. Kernighan and P. J. Plaugher


## Overview

1. Comments are always failures. The purpose of comments is to compensate for our failure to express ourself in code.
2. They are not "pure good". Indeed, comments are, at best, a necessary evil.
3. Think twice before writing a comment, try to express with code
4. Comments are bad because they lie, not always not intentionally. Programmers can't realistically maintain them.

Example:
```
MockRequest request; private final String HTTP_DATE_REGEXP =
  "[SMTWF][a-z]{2}\\,\\s[0-9]{2}\\s[JFMASOND][a-z]{2}\\s"+ 
  "[0-9]{4}\\s[0-9]{2}\\:[0-9]{2}\\:[0-9]{2}\\sGMT"; 
private Response response; 
private FitNesseContext context; 
private FileResponder responder; 
private Locale saveLocale;
// Example: "Tue, 02 Apr 2003 22:18:49 GMT"
```
5. Inaccurate comments are far worse than no comments at all.
6. Truth can found in one place: **The Code**

## Contents
<!-- todo: rewrite this after this readme completes -->


### Comments Do Not Make Up for Bad Code

1. Movtivations for writing comments is bad code.
2. Ranther than spend your time in comments, you better clean the code.


### Explain Yourself in Code

1. It takes only few seconds of thought to explain most of yor intent in code.

Example:

```
// Check to see if the employee is eligible for full benefits
if ((employee.flags & HOURLY_FLAG) && 
    (employee.age > 65))
```

```
if (employee.isEligibleForFullBenefits())
```

### Good Comments

1. Some comments are necessary or beneficial.
2. **However, the only truly good comment is the comment you found a way not to write.**

#### Legal Comments

1. Corporate coding standards force us to write certain comments for legal reasons.<br> Ex. **Copyright**, **authorship** etc.
2. These statements are necessary and reasonable to put into a comment at the **start** of each source file.
<br>Example:

```
// Copyright (C) 2003,2004,2005 by Object Mentor, Inc. All rights reserved.
// Released under the terms of the GNU General Public License version 2 or later.
```

3. Comments like this should not be contracts or legal tomes.
4. Where possible refer to a standard licnse or external documents.

#### Informative Comments

- It is sometimes useful to provide basic information with a comment.

Example:
```
// Returns an instance of the Responder being tested. 
protected abstract Responder responderInstance();
```
> This is useful but it is better to use the name of the function to convey the information. <br> The function could be nameed like `responderBeingTested`.

<br>

A Better Example:
```
// format matched kk:mm:ss EEE, MMM dd, yyyy 
Pattern timeMatcher = Pattern.compile( 
    "\\d*:\\d*:\\d* \\w*, \\w* \\d*, \\d*");
```


### Explanation of Intent
1. Sometimes a comment not only provides information about implementation but also the intent **behind** a decision.
Example:
```
public int compareTo(Object o) {
    if(o instanceof WikiPagePath) {
        WikiPagePath p = (WikiPagePath) o;
        String compressedName = StringUtil.join(names, ""); 
        String compressedArgumentName = StringUtil.join(p.names, ""); 
        return compressedName.compareTo(compressedArgumentName);
    } return 1; // we are greater because we are the right type. 
}
```

### Clarification

- Sometimes it is just helpful to translate the meaning of some obscure argument or return

Example:
```
public void testCompareTo() throws Exception 
{
    WikiPagePath a = PathParser.parse("PageA"); 
    WikiPagePath ab = PathParser.parse("PageA.PageB"); 
    WikiPagePath b = PathParser.parse("PageB"); 
    WikiPagePath aa = PathParser.parse("PageA.PageA"); 
    WikiPagePath bb = PathParser.parse("PageB.PageB"); 
    WikiPagePath ba = PathParser.parse("PageB.PageA");

    assertTrue(a.compareTo(a) == 0);    // a == a 
    assertTrue(a.compareTo(b) != 0);    // a != b 
    assertTrue(ab.compareTo(ab) == 0);  // ab == ab a
    ssertTrue(a.compareTo(b) == -1);    // a < b 
    assertTrue(aa.compareTo(ab) == -1); // aa < ab 
    assertTrue(ba.compareTo(bb) == -1); // ba < bb 
    assertTrue(b.compareTo(a) == 1);    // b > a 
    assertTrue(ab.compareTo(aa) == 1);  // ab > aa 
    assertTrue(bb.compareTo(ba) == 1);  // bb > ba
}
```

### Warning of Consequences
- Sometimes it is useful to warn other programmers about certain consequences.


Example:
```
// Don't run unless you 
// have some time to kill.
public void _testWithReallyBigFile() 
{
    writeLinesToFile(10000000); 
    response.setBody(testFile); 
    response.readyToSend(this);
    String responseString = output.toString();
    assertSubString("Content-Length: 1000000000", responseString); 
    assertTrue(bytesSent > 1000000000);
}
```
> Nowadays, we'd turn off the test case by using `@Ignore("Takes too long to run")`. Back in the days before JUnit 4, putting an underscore in front of the method names was a common convention.


Example:
```
public static SimpleDateFormat makeStandardHttpDateFormat() 
{
    //SimpleDateFormat is not thread safe, 
    //so we need to create each instance independently. 
    SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z"); 
    df.setTimeZone(TimeZone.getTimeZone("GMT")); return df;
}
```

### TODO Comments

1. `TODOS` are job that the programmer thinks should be done.
2. Whatever else a `TODO` might be, it is not an escuse to leave bad code in the system.

Example:
```
//TODO-MdM these are not needed
// We expect this to go away when we do the checkout model 
protected VersionInfo makeVersion() throws Exception 
{
    return null; 
}
```

### Amplification

- A comment may be used to amplify the importance of something that may otherwise seem inconssequential.

Example:

```
String listItemContent = match.group(3).trim(); 
// the trim is real important. It removes the starting 
// spaces that could cause the item to be recognized 
// as another list.
new ListItemWidget(this, listItemContent, this.level + 1); 
return buildList(text.substring(match.end()));
```


### Javadocs in Public APIs
> There is nothing quite so helpful and satisfying as well-described public API. 

- If you are writing a public API, then you should certainly write good javadocs for it.
- Keep in mind that, Javadocs can be just as misleading as any other kind of comment.


### Bad Comments
> Most comments fall into this category (😂).


#### Mumbling

- If you decide to write a comment, then spend the time necessary to make sure it is the best comment you can write.

Example:
![Mumbling example](/clean_code//images/mumbling-example.png)
> What does that comment in the `catch` block mean? Clearly it meant something to the
author, but the meaning does not come through all that well

> Any comment that forces you to look in another module for the meaning of that comment has failed to communicate to you and is not worth the bits it consumes


### Redundant Comments

Example:

![](/clean_code//images/example-4.1.png)

> It is rather like a gladhanding used-car salesman assuring you that you don't need to look under the hood 😜.

Another example taken from **Tomcat**
![](/clean_code//images/example-4.2-part-1.png)


### Misleading Comments

1. Sometimes with best intentions, a programmer makes a statement that  isn't precise enough to be accurate.
<br>Example: Check the  `example 4.1`
2. Subtle bit of misinformation is harder to than the body of the code. It would also be harder to debug.


### Mandated Comments
> It is just silly to have a rule that says every function/variable must have a javadoc.

> Required javadocs for every function lead to abominations such as below example.

Example:

![](/clean_code//images/example-4.3.png)


### Journal Comments
> Sometimes people add comment to the start of a module every time they edit. These comments accumulate as a kind of journal or log.

Example:

![](/clean_code//images/journal-comments-example.png)


### Noise Comments
> Some comments are nothing but noise

Example:

```
/**
 * Default constructor. 
 */
protected AnnualDateRule() {
}
```

```
/** The day of the month. */ 
    private int dayOfMonth;
```


```
/**
 * Returns the day of the month. *
 * @return the day of the month. 
 */
public int getDayOfMonth() { 
    return dayOfMonth;
} 
```


Example 4.4:

![](/clean_code//images/example-4.4.png)
>The first comment in Listing 4-4 seems appropriate.2 It explains why the `catch` block
is being ignored. <br>But the second comment is pure noise.

- Rather than venting in a worthless and noisy comment, the programmer should have
recognized that his frustration could be resolved by improving the structure of his code

Refactored Example:

![](/clean_code//images/example-4.5-part-1.png)
![](/clean_code//images/example-4.5-part-2.png)


### Scary Noise

Javadocs can also be noisy. If authors
aren’t paying attention when comments are written (or pasted), why should readers be expected to profit from them?

![](/clean_code//images/scary-noise-example.png)

### Don't Use a Comment When You Can Use a Function or a Variable

Example:
```
// does the module from the global list <mod> depend on the 
// subsystem we are part of?
if (smodule.getDependSubsystems().contains(subSysMod.getSubSystem()))
```

Refactored Code:

```
ArrayList moduleDependees = smodule.getDependSubsystems(); String ourSubSystem = subSysMod.getSubSystem(); 
if (moduleDependees.contains(ourSubSystem))
```

### Position Markers
> Sometimes programmers like to mark a particular position in a source file. For example, I recently found this in a program I was looking through: 

Example:

```
    // Actions //////////////////////////////////
```

- Think of it this way. A banner is startling and obvious if you don’t see banners very often. 
- So use them very sparingly, and only when the benefit is significan


### Closing Brace Comments
Example:

![](/clean_code//images/example-4.6-part-1.png)
![](/clean_code//images/example-4.6-part-2.png)

> If you find yourself wanting to mark your closing braces, try to shorten your functions instead

### Attributation and Bylines

Example:
```
/* Added by Limon Bhai */
```
The source code control system is a better place for this kind of information


### Commented-Out Code

- Few practices are as odious as commenting-out code. Don’t do this!

Example:
```
     InputStreamResponse response = new InputStreamResponse()
     response.setBody(formatter.getResultStream(), formatter.getByteCount());
//   InputStream resultsStream = formatter.getResultStream(); 
//   StreamReader reader = new StreamReader(resultsStream); 
//   response.setContent(reader.read(formatter.getByteCount()));
```

- Others who see that commented-out code won’t have the courage to delete it

Example: 
```
this.bytePos = writeBytes(pngIdBytes, 0); 
//hdrPos = bytePos; 
writeHeader(); 
writeResolution(); 
//dataPos = bytePos; 
if (writeImageData()) {
    writeEnd();
    this.pngBytes = resizeByteArray(this.pngBytes, this.maxPos); 
}
else {
    this.pngBytes = null; 
} 
return this.pngBytes;
```

- There was a time, back in the sixties, when commenting-out code might have been
useful. 
- But we’ve had good source code control systems for a very long time now

### HTML Comments

- HTML in source code comments is an abomination
Example:

![](/clean_code//images/example-html-in-comments.png)


### Nonlocal Information

- If you must write a comment, then make sure it describes the code it appears near. 
- Don’t offer systemwide information in the context of a local comment

```
/**
 * Port on which fitnesse would run. Defaults to <b>8082</b>. 
 *
 * @param fitnessePort 
 */
public void setFitnessePort(int fitnessePort) 
{
    this.fitnessePort = fitnessePort; 
}
```

### Too Much Information

- Don’t put interesting historical discussions or irrelevant descriptions of details into your comments

Example:

```
/*
    RFC 2045 - Multipurpose Internet Mail Extensions (MIME) Part One: 
    Format of Internet Message Bodies section 6.8. Base64 
    Content-Transfer-Encoding
    The encoding process represents 24-bit groups of input bits as output
    strings of 4 encoded characters. Proceeding from left to right, a 
    24-bit input group is formed by concatenating 3 8-bit input groups. 
    These 24 bits are then treated as 4 concatenated 6-bit groups, each 
    of which is translated into a single digit in the base64 alphabet. 
    When encoding a bit stream via the base64 encoding, the bit stream 
    must be presumed to be ordered with the most-significant-bit first. 
    That is, the first bit in the stream will be the high-order bit in 
    the first 8-bit byte, and the eighth bit will be the low-order bit in 
    the first 8-bit byte, and so on.
*/
```


### Inobvious Connection

- The connection between a comment and the code it describes should be obvious

Example:

```
/*
 * start with an array that is big enough to hold all the pixels 
 * (plus filter bytes), and an extra 200 bytes for header info 
 */
this.pngBytes = new byte[((this.width + 1) * this.height * 3) + 200];
```

### Function Headers

> Tip: Short functions don't need much description. User proper function name

### Javadocs in Nonpublic Code

> Generating javadoc pages for the classes and functions inside a system is not generally useful.