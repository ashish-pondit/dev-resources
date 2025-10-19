# Linux File Permissions and Umask Cheat Sheet

This cheat sheet covers essential Linux file permissions (UGO/RWX) and umask, providing examples, explanations, and practical usage.

---

## 1. UGO/RWX Permissions

### Basics

* **U (user)**: Owner of the file
* **G (group)**: Users in the same group as the file
* **O (others)**: Everyone else
* **R (read)**: View file contents / list directory
* **W (write)**: Modify file / add or remove files in a directory
* **X (execute)**: Run file / access directory

### How They Work

* Each file or directory has **3 sets of permissions**: user, group, others
* Permissions control **what each class of users can do**
* Displayed using `ls -l`:

```
-rwxr-xr-- 1 user group file.txt
```

* Breakdown:

  * `u=rwx` → user can read, write, execute
  * `g=r-x` → group can read and execute
  * `o=r--` → others can only read

### Octal Value vs R W X

| Permission | Symbol | Octal Value |
| ---------- | ------ | ----------- |
| Read       | r      | 4           |
| Write      | w      | 2           |
| Execute    | x      | 1           |
| No Perm    | -      | 0           |

### Common Examples

| Command               | Result                | Meaning                                |
| --------------------- | --------------------- | -------------------------------------- |
| `chmod 644 file.txt`  | `rw-r--r--`           | Owner read/write, group/others read    |
| `chmod 755 dir/`      | `rwxr-xr-x`           | Owner full access, others read/execute |
| `chmod u+x script.sh` | Adds execute for user | Script becomes executable by owner     |

### Add/Remove Permissions for User, Group, Others

```
chmod u+x file.txt  # add execute for user
chmod g-w file.txt  # remove write for group
chmod o+r file.txt  # add read for others
chmod a-x file.txt  # remove execute for all
```

### Change Permission Using RWX vs Octal Value

| Operation                                                | RWX              | Octal |
| -------------------------------------------------------- | ---------------- | ----- |
| User read/write/execute, group read/execute, others read | `u=rwx,g=rx,o=r` | 754   |
| User read/write, group read, others none                 | `u=rw,g=r,o=`    | 640   |
| All read/write                                           | `a=rw`           | 666   |

---

## 2. Umask

### What is Umask

* `umask` = **user file-creation mask**
* Defines **which permission bits should be turned off** when creating a file or directory
* Acts as a **filter applied to default permissions**

### Use Case of Umask

* Automatically restrict permissions on newly created files/directories
* Improve security by preventing group/others from writing sensitive files

### Default Permissions and Umask Calculation

| Type      | Default Mode    |
| --------- | --------------- |
| File      | 666 (rw-rw-rw-) |
| Directory | 777 (rwxrwxrwx) |

**Effective Permission** = Default Mode − Umask (bitwise)

Example (`umask 027`):

```
File: 666 - 027 = 640 (rw-r-----)
Dir : 777 - 027 = 750 (rwxr-x---)
```

### How to Set Umask Value

* Temporary (current shell):

```bash
umask 027
```

* Permanent (user-level): Add to `~/.bashrc` or `~/.bash_profile`:

```bash
umask 027
```

* Permanent (system-wide): Edit `/etc/login.defs`:

```
UMASK 027
```

### Set Umask Using Octal vs Symbolic (`-S`)

* Numeric (Octal): `umask 027`
* Symbolic: `umask -S u=rwx,g=rx,o=`
* Both achieve the same permission effect

### Mapping Octal Value to Symbolic (`-S`)

| Octal Umask | Symbolic (`umask -S`) | Meaning                       |
| ----------- | --------------------- | ----------------------------- |
| 000         | u=rwx,g=rwx,o=rwx     | All allowed                   |
| 022         | u=rwx,g=rx,o=rx       | Remove write for group/others |
| 027         | u=rwx,g=rx,o=         | Restrict others fully         |
| 077         | u=rwx,g=,o=           | Only user access              |

Example:

```bash
umask 022      # numeric
umask -S       # outputs u=rwx,g=rx,o=rx
umask -S u=rwx,g=rx,o=  # sets symbolic equivalent of 027
```

---

### Useful Man Pages

* `man chmod`
* `man umask`
* `man chown`
* `man login.defs`
* `man bash` (search for "umask")
