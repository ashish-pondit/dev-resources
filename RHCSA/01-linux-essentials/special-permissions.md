# Special Permissions in Linux: setuid, setgid, and Sticky Bit

---

## 1. Overview

Beyond the basic `ugo/rwx` permission system, Linux includes three *special permission bits* that provide more control over file and directory behavior:

* **setuid (Set User ID)**
* **setgid (Set Group ID)**
* **sticky bit**

These bits extend normal permission functionality for executables and directories.

---

## 2. setuid (Set User ID)

### Purpose

When the **setuid** bit is set on an executable binary, it allows users to execute the file **with the permissions of the file owner**, not their own.

### Internal Working

Each process in Linux has:

* **RUID** (Real User ID): who started the process
* **EUID** (Effective User ID): whose permissions the process uses

When you execute a setuid binary:

```
RUID = user running the program
EUID = file owner
```

The kernel checks and sets the EUID during `execve()` system call.

### Example

```bash
ls -l /usr/bin/passwd
-rwsr-xr-x 1 root root 54256 Oct 18  /usr/bin/passwd
```

Here, `s` replaces `x` in the owner’s execute bit, showing **setuid** is active.
When a normal user runs it, the program runs with **root privileges**, allowing safe password changes.

### Commands

```bash
chmod u+s file        # enable setuid
chmod u-s file        # disable setuid
chmod 4755 file       # equivalent octal form
```

### ASCII Diagram: setuid in Action

```
Before execution:
+---------------------------+----------------------------+
| Attribute                 | Value                      |
+---------------------------+----------------------------+
| RUID (Real UID)           | user (e.g., bob)           |
| EUID (Effective UID)      | user (bob)                 |
+---------------------------+----------------------------+

After executing setuid binary (owned by root):
+---------------------------+----------------------------+
| Attribute                 | Value                      |
+---------------------------+----------------------------+
| RUID (Real UID)           | user (bob)                 |
| EUID (Effective UID)      | root                       |
+---------------------------+----------------------------+
```

### Notes

* Works only on **binary executables**.
* **Ignored on scripts** for security (prevents privilege escalation).

### **Use Case:**

When a normal user changes their password using the `passwd` command, the system must update `/etc/shadow`, which is only writable by `root`.

If `passwd` didn’t have elevated permissions, normal users couldn’t modify their passwords.

#### **How it Works:**

* The `passwd` binary is owned by `root` and has the **setuid** bit set.
* When executed by any user, the kernel sets the **EUID to root**.
* The program then safely updates `/etc/shadow` on behalf of the user.

```bash
$ ls -l /usr/bin/passwd
-rwsr-xr-x 1 root root 54256 Oct 18 /usr/bin/passwd
```

Similarly, `/bin/su` uses **setuid** to temporarily grant root privileges when switching users.

#### **Explanation:**

```
RUID = bob         → identifies the real user running su
EUID = root        → allows execution of privileged operations
```

The program includes internal security checks (like password validation) before performing actions as root.


---

## 3. setgid (Set Group ID)

### Purpose

* On **executables**: process runs with the **group permissions of the file’s group**.
* On **directories**: files and subdirectories created inside inherit the directory’s **group ownership**.

### Example (directory)

```bash
mkdir /shared
chown root:devs /shared
chmod 2775 /shared
```

Now any file created inside `/shared` will belong to the group `devs`.

### Commands

```bash
chmod g+s file_or_dir   # enable setgid
chmod g-s file_or_dir   # disable setgid
chmod 2755 file_or_dir  # octal form
```

### ASCII Diagram: setgid in Directory

```
/shared (group = devs, g+s)
│
├── user1 creates → file1 (group = devs)
├── user2 creates → file2 (group = devs)
└── user3 creates → subdir/ (inherits g+s and group = devs)
```

### Notes

* Works for both binaries (execution privilege) and directories (group inheritance).
* Ignored on shell scripts.

### **Use Case:**

In a development team, members need a shared directory where all files are accessible by the group. Without `setgid`, new files would inherit the **creator’s group**, leading to permission issues.

#### **Setup:**

```bash
sudo groupadd devteam
sudo mkdir /projects
sudo chown root:devteam /projects
sudo chmod 2775 /projects   # setgid ensures group inheritance
```

#### **How it Works:**

* The **setgid** bit (`g+s`) ensures every file/subdirectory inside `/projects` inherits the `devteam` group.
* Users can collaborate without manually changing group ownership.

**Example:**

```
/projects (drwxrwsr-x)
│
├── alice creates → code1.c (group = devteam)
└── bob creates → docs/ (inherits group = devteam)
```

Even if Alice’s primary group is `engineers`, any file she creates under `/projects` belongs to `devteam`.

---

## 4. Sticky Bit

### Purpose

Used mainly on **directories** to restrict file deletion. It prevents users from deleting or renaming files they don’t own inside a world-writable directory.

### Example

```bash
ls -ld /tmp
drwxrwxrwt 17 root root 4096 Oct 18 /tmp
```

The `t` in place of others’ execute bit indicates the sticky bit.

### Commands

```bash
chmod +t directory   # set sticky bit
chmod -t directory   # remove sticky bit
chmod 1777 directory # octal form (common for /tmp)
```

### ASCII Diagram: Sticky Bit Behavior

```
/tmp (drwxrwxrwt)
│
├── alice creates file_a
├── bob creates file_b
└── Only alice can delete file_a; bob cannot delete others’ files
```

### Notes

* Only the **file owner**, **directory owner**, or **root** can delete or rename files.
* On regular files, sticky bit is ignored.

### **Use Case:**

`/tmp` is a world-writable directory used by all users for temporary files. Without the sticky bit, any user could delete another user’s files.

#### **Setup:**

```bash
ls -ld /tmp
drwxrwxrwt 17 root root 4096 Oct 18 /tmp
```

The `t` at the end indicates the sticky bit.

#### **How it Works:**

* The sticky bit restricts deletion or renaming of files inside `/tmp`.
* A user can only delete files **they own**, even though the directory is writable by everyone.

**Example:**

```
/tmp (drwxrwxrwt)
│
├── alice creates → file_a
├── bob creates → file_b
└── Only alice can delete file_a; bob cannot remove it.
```

Without the sticky bit, malicious users could delete others’ temp files.

---

## 5. Octal Representation of Special Bits

| Special Bit | Symbolic | Octal | Description                       |
| ----------- | -------- | ----- | --------------------------------- |
| setuid      | u+s      | 4     | Run as file owner                 |
| setgid      | g+s      | 2     | Run as file group / inherit group |
| sticky      | +t       | 1     | Protect files from deletion       |

### Combined Values

| Combination     | Octal | Meaning                              |
| --------------- | ----- | ------------------------------------ |
| setuid + setgid | 6xxx  | Run with owner and group permissions |
| setuid + sticky | 5xxx  | Rare use case                        |
| setgid + sticky | 3xxx  | Group + sticky directory             |
| all three       | 7xxx  | Full combination                     |

---

## 6. Summary Table

| File Type         | setuid                     | setgid                     | sticky                  |
| ----------------- | -------------------------- | -------------------------- | ----------------------- |
| Binary Executable | Runs with file owner’s UID | Runs with file group’s GID | Ignored                 |
| Directory         | Ignored                    | New files inherit group    | Restricts file deletion |
| Shell Script      | Ignored                    | Ignored                    | Ignored                 |

---

## 7. Quick Reference

* **setuid → binaries** (EUID changes)
* **setgid → binaries + directories** (EGID changes or group inheritance)
* **sticky → directories** (restrict delete)

---

## 8. Useful man pages

* `man chmod`
* `man 2 execve`
* `man 7 credentials`
* `man 7 inode`

---

**In short:**

> setuid = run as owner
> setgid = run as group
> sticky = protect files in directory
