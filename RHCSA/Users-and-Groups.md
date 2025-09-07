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

# 🔐 Pluggable Authentication Modules (PAM) in Linux

This document explains PAM in depth: what it is, where its files are located, how it works, common modules, and how control flags combine results.

---

## 🔹 What is PAM?

* **PAM (Pluggable Authentication Modules)** is a framework that provides a flexible, centralized way to manage authentication and authorization.
* Instead of embedding authentication logic directly into programs (`login`, `sshd`, `sudo`, etc.), these programs delegate to PAM.
* PAM reads configuration files and runs a **stack of modules** that decide whether the authentication succeeds.

---

## 🔹 Where Are PAM Files Located?

* Configuration files live in:

  ```
  /etc/pam.d/
  ```
* Each service has its own file:

  * `/etc/pam.d/sshd` → rules for SSH logins.
  * `/etc/pam.d/sudo` → rules for sudo.
  * `/etc/pam.d/login` → rules for local logins.
* A common file `/etc/pam.d/system-auth` is often included by others for shared rules.

Example (`/etc/pam.d/login`):

```
auth    required    pam_securetty.so
auth    requisite   pam_nologin.so
auth    include     system-auth
account required    pam_unix.so
password include    system-auth
session required    pam_unix.so
```

---

## 🔹 PAM Configuration Line Structure

Each PAM rule has this format:

```
<module-type>   <control-flag>   <module-path>   [options]
```

* **Module Types**:

  * `auth` → Verifies user identity (passwords, keys).
  * `account` → Checks if the account is allowed (expiry, access restrictions).
  * `password` → Handles password updates.
  * `session` → Sets up/tears down user sessions (limits, logging).

* **Control Flags**:

  * `required` → Must succeed. Failure is remembered, but remaining modules still run.
  * `requisite` → Must succeed. On failure, authentication stops immediately.
  * `sufficient` → If successful, authentication succeeds immediately (ignores rest of stack for this type).
  * `optional` → Success/failure ignored unless it’s the only module for that type.
  * `include` → Includes rules from another PAM config file.

---

## 🔹 How PAM Works (Step by Step)

1. A service (e.g., `sshd`, `sudo`, `login`) requests authentication.
2. PAM reads the corresponding config file from `/etc/pam.d/`.
3. Rules are processed **in order**:

   * Module type determines purpose (auth/account/password/session).
   * Control flag decides how success/failure affects the outcome.
   * Module runs the check (password, token, Kerberos, LDAP, etc.).
4. PAM combines results according to control flags.
5. Final decision: **grant or deny access**.
6. If successful, `session` modules apply resource limits, logging, or audit rules.

---

## 🔹 Example Walkthrough

Config file snippet:

```
auth    sufficient   pam_unix.so
auth    required     pam_ldap.so
```

* If password matches local `/etc/shadow` via `pam_unix.so` → success immediately, LDAP check skipped.
* If local check fails, PAM still calls `pam_ldap.so`. Because it is `required`, failure there denies login.

---

## 🔹 Common PAM Modules

Here are widely used modules and their purposes:

* **pam\_unix.so** → Traditional UNIX authentication using `/etc/passwd` and `/etc/shadow`.
* **pam\_rootok.so** → Grants access if the user is `root` (UID 0).
* **pam\_securetty.so** → Restricts root login to secure terminals listed in `/etc/securetty`.
* **pam\_nologin.so** → Denies login if `/etc/nologin` file exists.
* **pam\_tally2.so** / **pam\_faillock.so** → Tracks failed login attempts, used to lock accounts after too many failures.
* **pam\_limits.so** → Enforces limits from `/etc/security/limits.conf` (CPU, memory, processes).
* **pam\_env.so** → Sets environment variables from `/etc/security/pam_env.conf`.
* **pam\_wheel.so** → Restricts `su` command usage to members of the `wheel` group.
* **pam\_sss.so** → Integrates with SSSD for LDAP/Active Directory authentication.
* **pam\_krb5.so** → Handles Kerberos authentication.
* **pam\_systemd.so** → Starts a user systemd session.

---

## 🔹 Why PAM Matters

* Provides **modularity**: admins can mix and match authentication methods.
* Provides **security**: can enforce password policies, 2FA, lockouts.
* Provides **consistency**: all applications use the same authentication stack.

---

## ✅ Summary

* PAM configs live in `/etc/pam.d/`, each file corresponds to a service.
* Rules are stacked and processed with control flags (`required`, `sufficient`, etc.).
* Modules perform the real work: checking passwords, limiting logins, enforcing policies.
* Misconfiguration can lock out all users, so always edit using `sudo visudo`-like precautions (`authconfig` or distribution tools may help).

---

## 🔹 Where to Find PAM Modules

* PAM configuration files: `/etc/pam.d/`
* PAM modules: usually stored in `/lib/security/` or `/lib64/security/`
* List installed modules:

  ```bash
  ls /lib64/security/ | grep pam_
  ```

---

## 🔹 Example PAM Configurations

### 1. Requiring Strong Passwords

* File: `/etc/pam.d/system-auth`
* Add password complexity with `pam_pwquality`:

  ```
  password   requisite    pam_pwquality.so retry=3 minlen=12 ucredit=-1 lcredit=-1 dcredit=-1 ocredit=-1
  ```

  ➝ Enforces minimum length and at least one uppercase, lowercase, digit, and special character.

### 2. Account Lockout After Failed Attempts

* File: `/etc/pam.d/system-auth`
* Use `pam_faillock`:

  ```
  auth    required    pam_faillock.so preauth silent deny=5 unlock_time=600
  auth    [success=1 default=bad] pam_unix.so
  auth    [default=die] pam_faillock.so authfail
  account required    pam_faillock.so
  ```

  ➝ Locks account for 10 minutes after 5 failed attempts.

### 3. Restricting Login Times

* File: `/etc/pam.d/sshd`
* Use `pam_time`:

  ```
  account required pam_time.so
  ```

  * Rules defined in `/etc/security/time.conf`, e.g.:

    ```
    login ; * ; alice ; !Wk0900-1700
    ```

    ➝ User `alice` cannot log in outside weekday 9 AM–5 PM.

### 4. Limiting Resources Per User

* File: `/etc/pam.d/common-session`
* Use `pam_limits`:

  ```
  session required pam_limits.so
  ```

  * Rules in `/etc/security/limits.conf`, e.g.:

    ```
    alice hard nofile 1000
    ```

    ➝ Limits user `alice` to 1000 open files.

### 5. Two-Factor Authentication (2FA)

* File: `/etc/pam.d/sshd`
* Use `pam_google_authenticator`:

  ```
  auth required pam_google_authenticator.so
  ```

  ➝ Requires OTP code along with password for SSH login.

---

## 🔹 Commonly Used PAM Modules

* **pam\_unix.so** → Standard authentication against `/etc/passwd` & `/etc/shadow`.
* **pam\_rootok.so** → Allows root to bypass authentication.
* **pam\_pwquality.so** → Enforces strong password policies.
* **pam\_faillock.so** → Locks accounts after failed login attempts.
* **pam\_limits.so** → Enforces resource limits.
* **pam\_time.so** → Restricts login times.
* **pam\_listfile.so** → Allows/denies login based on a file list.
* **pam\_google\_authenticator.so** → Adds OTP-based 2FA.

---

## 🔹 How to Get the List of PAM Modules

* Installed PAM modules:

  ```bash
  ls /lib64/security/ | grep pam_
  ```
* Configuration per service:

  ```bash
  ls /etc/pam.d/
  ```
* Check documentation:

  ```bash
  man pam_<module>
  ```

  Example: `man pam_faillock`

---

✅ This shows how PAM modules are used in real-world security hardening.

