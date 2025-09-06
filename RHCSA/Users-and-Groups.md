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

## 🔹 Group Management

* `sudo groupdel [group-name]`
  ➝ Deletes a group from the system.
