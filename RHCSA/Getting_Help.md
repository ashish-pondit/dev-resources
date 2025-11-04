# Getting Help in Linux (RHCSA Cheat Sheet)

During the RHCSA exam, knowing **how to get help quickly** is extremely valuable. Here are the key tools and techniques to help you find command syntax, configuration details, and examples.

---

> **Note:** If `whatis` or `apropos` don’t show results, it may be because the manual database hasn’t been built. Run the following to update it:
>
    ```bash
    mandb
    ```
>
> This command rebuilds the database that `whatis` and `apropos` depend on so they work correctly.

## 1. `man` (Manual Pages)

* Displays detailed documentation for most commands.
* **Usage:**

  ```bash
  man ls
  ```

* **Tip:** Use `/` to search inside the man page and `n` to move to the next result.
* **Example:**

  ```bash
  man useradd
  ```

  → Shows all options for creating a new user.


### Understanding Man Page Sections

The manual information is split into **nine sections** for organization and clarity; different sections serve different purposes:

1. **General commands** (1) – User commands and programs
2. **System calls** (2) – Functions provided by the kernel
3. **Library functions** (3) – Standard C library functions
4. **Special files / devices** (4) – Device files in `/dev`
5. **File formats and conventions** (5) – Configuration and data file formats
6. **Games and screensavers** (6)
7. **Miscellaneous** (7) – Conventions, protocols, macro packages
8. **System administration commands** (8) – Admin commands, often requiring root
9. **Kernel routines** (9) – Kernel internal functions

**Example:** The `passwd` name exists both as a command and as a config file in `/etc/passwd`. Searching the man pages can show results for both. If you want the `passwd` command, you look in section 1; if you want the config file, you look in section 5.

```bash
man 1 passwd
```

To see a configuration file format (like `/etc/passwd`) in section 5:

```bash
man 5 passwd
```

---

## 2. `--help` Option

* Most Linux commands provide a short help summary with `--help`.
* **Usage:**

  ```bash
  ls --help
  ```

* **When to use:** Quick reference when you forget an option but don’t need full documentation.

---

## 3. `/usr/share/doc`

* Contains documentation files installed with packages.
* **Usage:**

  ```bash
  ls /usr/share/doc | grep ssh
  ```

  Then:

  ```bash
  less /usr/share/doc/openssh/README
  ```

* **Tip:** Use this when `man` pages are unavailable or incomplete.

---

## 4. `apropos`

* Searches the manual page descriptions for a keyword.
* **Usage:**

  ```bash
  apropos user
  ```

  → Lists all commands and topics related to “user”.

---

## 5. `whatis`

* Gives a one-line description of a command.
* **Usage:**

  ```bash
  whatis passwd
  ```

  → Outputs: `passwd (1) - change user password`

---

## 6. Tab Completion

* Saves time and avoids typos.
* **Usage:** Type part of a command or filename, then press `TAB`.

  ```bash
  sys<TAB>
  ```

  → Might auto-complete to `systemctl`.
* **Double TAB:** Lists all possible matches.

---

## 7. `info` Command

* Provides structured manuals with sections and examples.
* **Usage:**

  ```bash
  info coreutils 'ls invocation'
  ```

* Often more detailed than `man` pages.

---

## 8. Shell History & Reverse Search

* **View command history:**

  ```bash
  history
  ```

* **Search previous commands interactively:**
  Press `Ctrl + r`, then type part of a previous command.

---

## 9. Using Examples from `/usr/share/doc` or `man` Pages

* Many man pages contain practical examples at the end under the **EXAMPLES** section.

  ```bash
  man tar | less +/EXAMPLES
  ```

  → Jumps directly to example usage.

---

## 10. Helpful Exam Tips

* Always check the `man` page before assuming syntax.
* Use tab completion to avoid typing mistakes.
* Explore `/usr/share/doc` if something is unclear.
* Keep calm — every required command has documentation.
* During the exam, **you are allowed to use `man`, `--help`, and `/usr/share/doc`**.

---
