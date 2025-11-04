# Linux Command Fundamentals Cheat Sheet

## 1. Access a shell prompt and issue commands

* **Open a shell:** `Ctrl+Alt+T` or switch to TTY with `Ctrl+Alt+F2`.
* **Basic syntax:** `command [options] [arguments]`
* **Example:** `ls -l /home`
* **Tab completion:**

  * Press `Tab` to auto-complete commands, file, or directory names.
  * Press `Tab` twice to list all possible suggestions.
* **Navigation:**

  ```bash
  cd /path/to/directory     # change directory
  cd ..                     # go up one directory
  cd ~                      # go to home directory
  cd -                      # go to previous directory
  cd                        # go to home directory (shortcut)
  pwd                        # print working directory
  ```

---

## 2. Log in and switch users

* **Login at TTY:** enter username & password.
* **Switch user:**

  ```bash
  su - username   # switch to another user with environment
  sudo -i         # become root with environment
  whoami          # check current user
  ```

---

## 3. Read and use system documentation

* **man pages:**

  ```bash
  man ls
  man 5 passwd    # section 5 of man (file formats)
  ```
* **info pages:** `info coreutils`
* **Documentation in /usr/share/doc:**

  ```bash
  ls /usr/share/doc | less
  less /usr/share/doc/bash/README
  ```
* **apropos:** search man pages by keyword

  ```bash
  apropos network
  apropos passwd
  ```

---

## 4. Create, delete, copy, move files/directories

```bash
# Files
 touch file.txt                # create empty file
 stat file.txt                 # view file info, size, permissions, links
 rm file.txt                   # delete
 cp file.txt /tmp/             # copy
 mv file.txt /var/tmp/         # move/rename

# Directories
 mkdir dir1                    # create
 rmdir dir1                    # remove empty dir
 rm -r dir1                    # remove recursively
 cp -r dir1 dir2               # copy directory
 mv dir1 dir2                  # move directory
```

---

## 5. Viewing File Contents

```bash
cat file.txt      # display file content
tac file.txt      # display file content in reverse
more file.txt     # view content page by page
less file.txt     # view content page by page with scrolling
```

---

## 6. Hard and Soft Links

* **Hard link:** points directly to the inode. Cannot cross filesystems. Multiple names for same file.

  ```bash
  ln file.txt hardlink.txt
  stat file.txt hardlink.txt   # same inode number
  ```
* **Soft (symbolic) link:** points to pathname. Can cross filesystems. Shows as link.

  ```bash
  ln -s file.txt softlink.txt
  ls -l softlink.txt           # shows -> target
  ```

---

## 7. File Permissions (ugo/rwx)

* **View permissions:** `ls -l`
* **Change permissions:**

  ```bash
  chmod u+x file.sh        # add execute for user
  chmod g-w file.sh        # remove write for group
  chmod o=r file.sh        # set read-only for others
  chmod u=rwx,g=rx,o= file.sh  # explicit permissions
  chmod 755 file.sh        # octal representation (u=rwx,g=rx,o=rx)
  chmod a+r file.sh        # add read for all
  chmod a-w file.sh        # remove write for all
  ```
* **Tips:**

  * `u` = user(owner), `g` = group, `o` = others, `a` = all
  * `+` add permission, `-` remove permission, `=` set exact permission

---

## 8. Special Permissions

* **SUID:** run as file owner

  ```bash
  chmod u+s file
  ```
* **SGID:** run as group or set group ownership on dirs

  ```bash
  chmod g+s dir
  ```
* **Sticky bit:** only owner can delete inside dir

  ```bash
  chmod +t /shared/dir
  ```

---

## 9. Searching for Files

* **find:**

  ```bash
  find /home -name "*.txt"                 # search by name
  find /home -iname "*.TXT"               # case-insensitive
  find /home -size +1M                     # files >1MB
  find /home -mmin -60                     # modified in last 60 mins
  find /home -cmin +30                     # status changed more than 30 mins ago
  find /home -name "*.txt" -and -size -1M # and operator
  find /home -name "*.txt" -or -name "*.log" # or operator
  find /home -not -name "*.txt"           # negate condition
  find /home \! -name "*.txt"            # alternative negation
  find /home -perm 644                     # permission match
  ```
* **locate:** `locate file.txt`
* **which/whereis:** `which ls`, `whereis bash`

---

# Exercise: Practice All Topics

1. Open a shell (TTY or terminal).
2. Navigate directories using `cd`, `cd -`, `pwd`, `ls`.
3. Log in as your user. Switch to root with `su -` or `sudo -i`.
4. Use **man**, **info**, **apropos** to explore commands like `touch`, `stat`, `mkdir`, `chmod`, `cat`, `tac`, `more`, `less`.
5. Use **Tab completion** to auto-complete file and directory names.
6. Create a directory `practice/` and inside it:

   * Create `file1.txt` using `touch`.
   * Check file info with `stat file1.txt`.
   * View contents using `cat`, `tac`, `more`, `less`.
   * Copy it to `file2.txt`, move `file2.txt` to `/tmp/`, delete it.
7. Create a hard link (`ln file1.txt hard1`) and a soft link (`ln -s file1.txt soft1`). Verify using `stat` and `ls -l`.
8. Set permissions using `chmod`:

   * Use symbolic (`u+x`) and octal (`644`, `755`) modes.
   * Experiment with `+`, `-`, `=` operators for u, g, o, a.
9. Apply special permissions:

   * `chmod u+s file1.txt`, `chmod g+s practice/`, `chmod +t practice/`
10. Search files using `find` with name, wildcard, `-size`, `-mmin`, `-cmin`, `-iname`, `-perm`, logical operators (`-and`, `-or`, `-not`, `!`).
11. Use `which bash` and `locate passwd`.

**Tips:** Take notes of inode numbers for links, observe permission changes with `ls -l`, and experiment with find operators to filter results effectively.

---

## Useful man pages

* `man 1 bash`
* `man 1 su`
* `man 1 sudo`
* `man 1 ls`
* `man 1 chmod`
* `man 1 ln`
* `man 1 find`
* `man 1 stat`
* `man 1 touch`
* `man 1 mkdir`
* `man 1 cat`
* `man 1 tac`
* `man 1 more`
* `man 1 less`
* `man 1 apropos`
* `man 5 passwd`
