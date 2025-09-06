# 👤 User & Group Management Cheat Sheet (Red Hat Linux)

This cheat sheet covers essential **user and group management commands** in Red Hat Linux, with short descriptions of what each does and what happens internally.

---

## 🔹 User Creation & Defaults

* `sudo useradd [username]`
  ➝ Creates a new user account. Updates `/etc/passwd` (stores basic user info), `/etc/shadow` (stores secure password hashes), `/etc/group` (stores group membership), and `/etc/skel` (template files for new home directories). A home directory is created from `/etc/skel` if defaults allow if defaults allow.

* `ls -a /etc/skel`
  ➝ Shows default files (like `.bashrc`, `.profile`) that are copied into a new user’s home directory.

* `useradd --defaults` or `useradd -D`
  ➝ Shows or sets default values for new user creation (e.g., default shell, home dir location).

* `cat /etc/login.defs`
  ➝ Displays system-wide defaults for user account creation (UID/GID ranges, password aging policies).

* `sudo passwd [username]`
  ➝ Set or change a user’s password. Updates `/etc/shadow`.

---

## 🔹 User Deletion

* `sudo userdel [username]`
  ➝ Deletes a user account (but leaves home directory and files).

* `sudo userdel --remove [username]` or `sudo userdel -r [username]`
  ➝ Deletes user account **and** removes the user’s home directory and mail spool.

---

## 🔹 Custom User Settings

* `sudo useradd --shell /bin/othershell --home-dir /home/otherdirectory [username]`
  ➝ Creates user with custom shell and home directory.

* Short form: `sudo useradd -s /bin/othershell -d /home/otherdirectory [username]`

* `sudo useradd --uid [uid-value] [username]`
  ➝ Creates user with manually assigned UID.

* **Account details location:** `/etc/passwd` (stores username, UID, GID, shell, home directory).

---

## 🔹 User Identification

* `id`
  ➝ Shows current user’s UID, primary GID, and group memberships.

* `whoami`
  ➝ Prints the currently logged-in username.

---

## 🔹 System Accounts

* `sudo useradd --system [username]`
  ➝ Creates a **system account** (UID typically < 1000). Commonly used for services/daemons like `apache`, `mysql`.

### UID Ranges (Red Hat Default)

* **0** → `root` (superuser)
* **1–999** → System users and groups
* **1000+** → Regular (human) users

**Significance:** UID determines permissions, ownership of files, and how services/users are separated.

---

## 🔹 Modifying Users

* `sudo usermod --home /home/some-directory --move-home [username]`
  ➝ Moves user to a new home directory.

* Short form: `sudo usermod -d /home/some-directory -m [username]`

* `sudo usermod --login [new-username] [old-username]`
  ➝ Changes username.

* Short form: `sudo usermod -l [new-username] [old-username]`

* `sudo usermod --shell /bin/othershell [username]`
  ➝ Changes user’s default shell.
  Short form: `sudo usermod -s /bin/othershell [username]`

* `sudo usermod --lock [username]` or `sudo usermod -L [username]`
  ➝ Locks user account (password disabled). **SSH keys may still work.**

* `sudo usermod --unlock [username]` or `sudo usermod -U [username]`
  ➝ Unlocks a previously locked account.

* `sudo usermod --expiredate [YYYY-MM-DD] [username]` or `sudo usermod -e [YYYY-MM-DD] [username]`
  ➝ Sets account expiration date.

* `sudo usermod --expiredate "" [username]` or `sudo usermod -e "" [username]`
  ➝ Removes expiration date.

---

## 🔹 Password Aging (Using `chage`)

* `sudo chage --lastday 0 [username]` or `sudo chage -d 0 [username]`
  ➝ Expires password immediately (forces reset at next login).

* `sudo chage --lastday -1 [username]` or `sudo chage -d -1 [username]`
  ➝ Marks password as never used (unexpire).

* `sudo chage --maxdays [day-count] [username]` or `sudo chage -M [day-count] [username]`
  ➝ Sets maximum days before password must be changed.

* `sudo chage --maxdays -1 [username]` or `sudo chage -M -1 [username]`
  ➝ Password never expires.

* `sudo chage --list [username]`
  ➝ Lists password aging info (last change, expiry).

⚠️ **Note:**

* **Account expiration** → user cannot log in after date.
* **Password expiration** → user must change password after date.

---

# 👥 Local Groups & Group Membership (Red Hat Linux)

This document explains how to manage **local groups and group membership** in Red Hat Linux, with practical scenarios, command usage, and examples.

---

## 🔹 Why Groups Are Necessary

* Groups are used to **organize users** and manage **permissions collectively**.
* Instead of assigning file or directory permissions to individual users, admins assign them to a group, and all members inherit access.

### Scenario Example:

* Some people like John, Mike, and Jake need access to a file or directory.
* Instead of granting permissions to each user individually, create a group `devs`, assign the folder’s permissions to that group, and add all developers to it.

---

## 🔹 Primary vs Secondary Groups

* **Primary Group**:

  * Every user has one **primary** group (set in `/etc/passwd`).
  * Files created by the user are owned by this group by default.
  * Example: If user `alice` has primary group `alice`, any file she creates belongs to group `alice`.

* **Secondary (Supplementary) Groups**:

  * A user can belong to multiple **secondary** groups.
  * Secondary groups grant additional permissions/access.
  * Example: `alice` can be in the `devs` and `qa` groups to access shared project directories.

---

## 🔹 Group Management Commands

* `sudo useradd [username]`
  ➝ Creates a new user (also creates a group with the same name by default in Red Hat).

* `sudo groupadd [groupname]`
  ➝ Creates a new group.

* `sudo gpasswd --add [username] [groupname]` or `sudo gpasswd -a [username] [groupname]`
  ➝ Adds a user to a group (as a secondary group).

* `groups [username]`
  ➝ Shows groups that the user belongs to.

* `sudo gpasswd --delete [username] [groupname]` or `sudo gpasswd -d [username] [groupname]`
  ➝ Removes a user from a group.

* `sudo usermod -g [groupname] [username]`
  ➝ Changes the **primary group** of a user.
  *(Note: `-g` is correct; `-gid` is not valid syntax.)*

* `sudo groupmod --new-name [newgroupname] [oldgroupname]` or `sudo groupmod -n [newgroupname] [oldgroupname]`
  ➝ Renames an existing group.

* `sudo groupdel [groupname]`
  ➝ Deletes a group.
  ⚠️ Cannot delete a group if it is still set as a user’s **primary group**.

---

## ✅ Summary

* Groups simplify **permission management**.
* **Primary group**: default ownership of user’s files.
* **Secondary groups**: grant additional access.
* Use `groupadd`, `gpasswd`, `usermod`, `groupmod`, and `groupdel` for managing groups.

---




# 🔑 Managing Access to Root Account (Red Hat Linux)

This document explains how to manage access to the **root account**, including using `sudo`, switching users, locking/unlocking root, and related commands.

---

## 🔹 What is `sudo`?

* `sudo` (superuser do) allows permitted users to run commands as **root** (or another user, with `-u`).
* When you run a command with `sudo`, the system:

  1. Checks if your user is in the `sudoers` configuration (`/etc/sudoers`).
  2. Prompts for your **own password** (not root’s) if required.
  3. Executes the command with **root privileges**.
* This is safer than logging in directly as root because it limits exposure and allows auditing.

---

## 🔹 Becoming Root via `sudo`

* `sudo --login` or `sudo -i`
  ➝ Opens a login shell as root (simulates full root login).

  * Use `logout` (or `exit`) to return to normal user.
    ⚠️ Requires the user to already have **sudo privileges**.

---

## 🔹 Switching to Root with `su`

* `su -` or `su -l` or `su --login`
  ➝ Switches to the root account if you know the **root password**.

  * Provides a full login environment for root.

### Note:

* On some systems, the **root account is locked** by default.

  * In this case, you **cannot use `su -`** (because it requires the root password).
  * But you can still use `sudo -i` if your account has sudo privileges.

---

## 🔹 Managing Root Account Password

* `sudo passwd root`
  ➝ Assigns a password to the root account (if it never had one). Enables password-based root login.

* `sudo passwd --unlock root` or `sudo passwd -u root`
  ➝ Unlocks the root account.

* `sudo passwd --lock root` or `sudo passwd -l root`
  ➝ Locks the root account (disables password-based login).

---

## 🔹 Remote Access to Root

* If SSH is set up and root has a password, the root account can be accessed via SSH (if `PermitRootLogin` is enabled in `/etc/ssh/sshd_config`).
* Best practice: **disable direct root SSH login** and use `sudo` instead.

---

## ✅ Summary

* Use `sudo` for safer, auditable root privilege management.
* `sudo -i` → root shell using your own credentials.
* `su -` → root shell requiring root’s password.
* Root account can be locked/unlocked or assigned a password with `passwd`.
* Direct root SSH login is possible but discouraged.

---

