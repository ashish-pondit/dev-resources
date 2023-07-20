# GIT

## How to write Git commit message [(Original Article)](https://cbea.ms/git-commit/)

### Purpose of using git:

- Version control
- Colaboration

> "Every line of code should be appear to be written by a single person, no matter the number of contributors" <br>
                    by Mark Otto (Creator of Bootstrap)

### Why good commit message matters

- Increase readability
- Best way to communicate
- Easy to maintain
- Easy to review

### Standard rules to follow for a Good commit message
1. Keep your commit atomic [(Original Article)](https://www.freshconsulting.com/insights/blog/atomic-commits/)

    An “atomic” change revolves around one task or one fix.

    **Approach**
    - Commit each fix or task as a separate change
    - Only commit when a block of work is complete
    - Commit each layout change separately
    - Joint commit for layout file, code behind file, and additional resources

    <br><br>
    **Benefits**

    - Easy to roll back without affecting other changes
    - Easy to make other changes on the fly
    - Easy to merge features to other branches


1. Separate subject from body with a blank line

    ```
    Change agency package filter by name 

    Change package filter by destination to package name
    Fix custom missing css selector
    ```
2. Limit the subject line to 50 characters
3. Capitalize the subject line
4. Do not end the subject line with a period
5. Use the imperative mood in the subject line <br>
    A properly formed Git commit subject line should always be able to complete the following sentence:<br>
    If applied, this commit will **your subject line here**

    For example:

    - If applied, this commit will **refactor subsystem X for readability**
    - If applied, this commit will **update getting started documentation**
    - If applied, this commit will **remove deprecated methods**
    - If applied, this commit will **release version 1.0.0**
    - If applied, this commit will **merge pull request #123 from user/branch**
    
6. Wrap the body at 72 characters
7. User the body to explain what and why vs how

Example repositories:
1. 