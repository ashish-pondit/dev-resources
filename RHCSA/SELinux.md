# 🛡️ SELinux Basics and Key Commands

## 1. Introduction to SELinux

**SELinux (Security-Enhanced Linux)** is a security framework built into the Linux kernel, based on **Mandatory Access Control (MAC)**. Unlike traditional **Discretionary Access Control (DAC)**, where file owners decide access, SELinux enforces system-wide policies that even `root` must follow.

SELinux uses:

* **Policies** → Define what subjects (users/processes) can do to objects (files, sockets, ports, etc.).
* **Labels (Security Contexts)** → Every process and file has a label describing its security attributes.
* **LSM (Linux Security Module) hooks** → Kernel extension points where SELinux checks access.

Modes of operation:

* **Enforcing** → SELinux actively blocks actions that violate policy.
* **Permissive** → SELinux only logs policy violations but does not block.
* **Disabled** → SELinux is completely turned off.

Check mode with:

```bash
getenforce
```

Example outputs:

* `Enforcing` → SELinux is enforcing.
* `Permissive` → Logging only.
* `Disabled` → Inactive.

---

## 2. SELinux Labels (Security Contexts)

Every file, process, and user session in SELinux is assigned a **label**, also known as a **security context**.

### Purpose of Labels:

* Allow SELinux to apply rules beyond normal Unix permissions.
* Ensure compromised processes are contained (least privilege).
* Distinguish between different types of objects (web content vs. system configs).

A label is usually in the format:

```
user:role:type:level
```

Example (for `/usr/sbin/sshd`):

```bash
ls -Z /usr/sbin/sshd
-rwxr-xr-x. root root system_u:object_r:sshd_exec_t:s0 /usr/sbin/sshd
```

Explanation:

* `system_u` → SELinux user.
* `object_r` → Role assigned to objects.
* `sshd_exec_t` → Type (here, SSH daemon executable).
* `s0` → Sensitivity level.

Processes also have labels:

```bash
ps axZ | grep sshd
system_u:system_r:sshd_t:s0   1023 ? Ss   0:00 /usr/sbin/sshd
```

Here, the running sshd process has type `sshd_t` (a domain).

---

## 3. SELinux Components (User, Role, Type, Level)

SELinux enforces access control using **four key components**:

### 🔹 User

* SELinux **user** is not the same as a Linux user.

* Mapping: normal Linux user (`john`) is mapped to an SELinux user (`user_u`).

* View mapping:

  ```bash
  sudo semanage login -l
  ```

  Example output:

  ```
  Login Name           SELinux User         MLS/MCS Range        Service
  __default__          unconfined_u         s0-s0:*              *
  root                 unconfined_u         s0-s0:*              *
  ```

  This means all normal logins map to `unconfined_u` by default.

* See your SELinux context:

  ```bash
  id -Z
  unconfined_u:unconfined_r:unconfined_t:s0-s0
  ```

### 🔹 Role

* Defines what types a user can assume.
* Example: `system_r` is for system processes.
* Roles are more restrictive than users.

### 🔹 Type (Most Important)

* Also called **Domain** for processes.
* Enforces access rules: A process of type `httpd_t` can only access files of type `httpd_sys_content_t`.
* This is called **Type Enforcement (TE)**, the core of SELinux.

### 🔹 Level

* Defines **MLS/MCS (Multi-Level / Multi-Category Security)**.
* Example: `s0`, `s1`, or ranges `s0-s15:c0.c1023`.
* Used in environments requiring strict data classification (military, government).

---

## 4. ASCII Diagram: SELinux Security Contexts

```
+-------------------+      +-------------------+      +-------------------+
|  SELinux User     | ---> |      Role         | ---> |      Type         |
|  (unconfined_u)   |      |  (system_r)       |      | (httpd_t)         |
+-------------------+      +-------------------+      +-------------------+
                                                         |
                                                         v
                                                  +---------------+
                                                  |   Level (s0)  |
                                                  +---------------+
```

* A Linux user (`john`) logs in → mapped to `unconfined_u`.
* That SELinux user has roles (`unconfined_r`, `system_r`).
* Roles determine what types (domains) processes can run in.
* Types control access to objects (files, sockets, ports).
* Levels define sensitivity categories.

---

## 5. SELinux Policy Configuration

Policies define **what is allowed or denied**. Main policy types:

* **Targeted Policy** → Default on most distros, confines specific services (e.g., `httpd`, `sshd`) but leaves users unconfined.
* **Strict Policy** → Everything confined, even users.
* **MLS Policy** → Adds sensitivity levels.

Tools:

* `sestatus` → Show status of SELinux.
* `semanage user -l` → List SELinux users and their roles.
* `audit2allow` → Generate policy rules from denied logs.

---

## 6. Common SELinux Commands

### 🔹 Check mode

```bash
getenforce
```

Shows if SELinux is `Enforcing`, `Permissive`, or `Disabled`.

### 🔹 List labels on files

```bash
ls -Z /etc/passwd
-rw-r--r--. root root system_u:object_r:passwd_file_t:s0 /etc/passwd
```

Displays the security context of a file.

### 🔹 List running processes with labels

```bash
ps axZ | head -3
LABEL                              PID TTY      STAT   TIME COMMAND
system_u:system_r:kernel_t:s0        1 ?        S      0:03 /sbin/init
system_u:system_r:sshd_t:s0       1023 ?        Ss     0:00 /usr/sbin/sshd
```

### 🔹 See current user’s security context

```bash
id -Z
unconfined_u:unconfined_r:unconfined_t:s0-s0
```

### 🔹 View login-to-SELinux user mapping

```bash
sudo semanage login -l
```

### 🔹 View SELinux users and roles

```bash
sudo semanage user -l
```

---

## 7. How SELinux User Mapping Works

* When you log in, PAM + SELinux map your Linux account to an SELinux user.
* Example:

  * Linux user `alice` logs in.
  * `semanage login -l` shows `__default__` → `unconfined_u`.
  * Therefore, `alice` gets mapped to `unconfined_u` with default role `unconfined_r`.
* This mapping decides which domains (process types) her sessions can run in.

---

## 8. Why Labels Matter

* Labels connect **subjects (processes)** and **objects (files, sockets, ports)**.
* SELinux rules are written in terms of labels, not usernames or PIDs.
* Example rule: `httpd_t` (process) → can read `httpd_sys_content_t` (web content files).
* Purpose: Even if Apache is hacked, it cannot read `/etc/shadow` (labeled `shadow_t`).

---

# ✅ Summary

* SELinux enforces **Mandatory Access Control** using labels (security contexts).
* Security context = `user:role:type:level`.
* **Type enforcement** is the core concept (process types/domains vs. file types).
* Users are mapped to SELinux users at login.
* Modes: Enforcing, Permissive, Disabled.
* Key commands: `ls -Z`, `ps axZ`, `id -Z`, `getenforce`, `semanage`.

SELinux ensures even `root` cannot bypass security policy, providing strong isolation and containment.
