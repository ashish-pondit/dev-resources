# 🔐 SSH Key-Based Authentication and Configuration Guide

This document covers SSH key-based authentication, configuration files, and common commands for managing secure connections in Linux.

---

## 1. SSH Configuration Files

### **1.1 Server-Side: `/etc/ssh/sshd_config`**

This file controls the **SSH server daemon (sshd)** behavior.

Key options:

* **`Port 22`** → Port number where sshd listens (default 22).
* **`AddressFamily any`** → Defines IP protocol family:

  * `any` → both IPv4 and IPv6
  * `inet` → IPv4 only
  * `inet6` → IPv6 only
* **`ListenAddress 0.0.0.0`** → Bind sshd to specific IP addresses (default: all).
* **`PermitRootLogin no`** → Controls root login:

  * `no` (recommended)
  * `yes`
  * `prohibit-password` (allow root only with keys)
* **`PasswordAuthentication no`** → Disable password login, enforce key-based authentication.
* **`X11Forwarding no`** → Controls GUI forwarding over SSH.
* **Per-User Password Authentication**:

  ```
  PasswordAuthentication no   # Disable globally
  Match User myuser           # Exception for specific user
      PasswordAuthentication yes
  ```

After changes:

```bash
sudo systemctl reload sshd   # Reload config without killing sessions
sudo systemctl restart sshd  # Restart service completely
```

---

### **1.2 Client-Side: `/etc/ssh/ssh_config`**

This file defines **defaults for SSH clients**.

Common options:

* **`Host *`** → Settings apply to all hosts unless overridden.
* **`Port`** → Default SSH port.
* **`User`** → Default username for connections.
* **`IdentityFile ~/.ssh/id_rsa`** → Path to private key.

Also supports per-host configs:

```ini
Host myserver
    HostName 192.168.1.10
    User devuser
    Port 2222
    IdentityFile ~/.ssh/id_ed25519
```

> 📖 Use `man sshd_config` and `man ssh_config` for full option reference.

---

## 2. SSH Keys and Authentication

### **2.1 What is `.ssh/`?**

Located in the user’s home directory (`~/.ssh/`), it stores authentication files:

* `id_rsa`, `id_ed25519` → Private keys
* `id_rsa.pub`, `id_ed25519.pub` → Public keys
* `authorized_keys` → List of allowed public keys (server-side)
* `known_hosts` → Stores server fingerprints to prevent MITM attacks
* `config` → User-specific SSH config (overrides `/etc/ssh/ssh_config`)

### **2.2 Generating SSH Keys**

```bash
ssh-keygen -t ed25519 -C "myemail@example.com"   # Generate a new key pair
```

* `-t` → Key type (`rsa`, `ed25519`, `ecdsa`)
* `-C` → Comment for identification
* Keys saved in `~/.ssh/`

### **2.3 Copying Keys to Server**

```bash
ssh-copy-id user@server   # Copy public key to server’s authorized_keys
```

If `ssh-copy-id` is unavailable:

```bash
cat ~/.ssh/id_ed25519.pub | ssh user@server "mkdir -p ~/.ssh && chmod 700 ~/.ssh && cat >> ~/.ssh/authorized_keys && chmod 600 ~/.ssh/authorized_keys"
```

### **2.4 Permissions (very important)**

SSH enforces strict permissions:

* `~/.ssh/` → `700`
* `~/.ssh/authorized_keys` → `600`
* `~/.ssh/config` → `600`

```bash
chmod 700 ~/.ssh
chmod 600 ~/.ssh/authorized_keys ~/.ssh/config
```

---

## 3. Managing Known Hosts and Fingerprints

When connecting to a server, SSH stores its fingerprint in `~/.ssh/known_hosts`.

To remove old/changed keys:

```bash
ssh-keygen -R hostname   # Remove by hostname
ssh-keygen -R 192.168.1.10   # Remove by IP
```

---

## 4. Additional Configurations

### **4.1 `/etc/ssh/ssh_config.d/`**

* Modern distributions allow dropping custom configs into `/etc/ssh/ssh_config.d/`.
* Each file is a small snippet with host-specific or global configuration.
* Example: `/etc/ssh/ssh_config.d/new-conf` can contain different host entries:

  ```ini
  Host github.com
      User git
      IdentityFile ~/.ssh/id_ed25519

  Host internal-server
      HostName 10.0.0.5
      User admin
      Port 2222
      IdentityFile ~/.ssh/id_rsa
  ```
* If you **do not write a `Host` line**, then any directives written will apply **globally to all hosts** (similar to using `Host *`).
* This allows administrators to separate configurations by service or environment (e.g., corporate servers vs. GitHub).
* Useful for modular configuration without editing the main file.

### **4.2 Management & Priority**

* Files inside `/etc/ssh/ssh_config.d/` are read in **lexicographic order** (sorted by filename).
* Later files can **override** options defined earlier.
* If there are conflicts, the **last matching directive wins**.
* Typically, distro defaults ship with `/etc/ssh/ssh_config.d/50-redhat.conf` or similar. You can add `99-custom.conf` to ensure your rules load last.
* These configs are merged with `/etc/ssh/ssh_config` and user-specific `~/.ssh/config`.
* Precedence (lowest → highest): `/etc/ssh/ssh_config` → `/etc/ssh/ssh_config.d/*.conf` → `~/.ssh/config`.


# 📝 SELinux Context & Label Management Cheat Sheet

## 🔹 Check SELinux Status & Mode

* `getenforce` → Show current SELinux mode (`Enforcing`, `Permissive`, `Disabled`).
* `sestatus` → Display detailed SELinux status.

---

## 🔹 Viewing Labels (Security Contexts)

* `ls -Z <file>` → Show SELinux label of a file.

  ```bash
  ls -Z /etc/passwd
  -rw-r--r--. root root system_u:object_r:passwd_file_t:s0 /etc/passwd
  ```

* `ps axZ` → Show SELinux labels for running processes.

  ```bash
  ps axZ | grep sshd
  system_u:system_r:sshd_t:s0   1234 ? Ss  0:00 /usr/sbin/sshd
  ```

* `id -Z` → Show current user’s security context.

---

## 🔹 Managing File Contexts

* `semanage fcontext -l` → List all SELinux file context rules.
* `semanage fcontext -a -t <type> <path>` → Add a new context mapping.

  ```bash
  semanage fcontext -a -t httpd_sys_content_t "/web(/.*)?"
  ```
* `restorecon -Rv <path>` → Apply default context to files.

  ```bash
  restorecon -Rv /web
  ```
* `chcon -t <type> <file>` → Change file context (non-persistent).

  ```bash
  chcon -t httpd_sys_content_t /var/www/html/index.html
  ```
* `chcon -u <user> -r <role> -t <type> <file>` → Change full context.

---

## 🔹 Managing User ↔ SELinux User Mapping

* `semanage login -l` → List login mappings.
* `semanage login -a -s <selinux_user> <linux_user>` → Map Linux user to SELinux user.

  ```bash
  semanage login -a -s staff_u bob
  ```
* `semanage login -d <linux_user>` → Delete mapping.

---

## 🔹 Managing SELinux Users & Roles

* `semanage user -l` → List SELinux users and roles.
* `semanage user -a -R <roles> -r <range> <selinux_user>` → Add SELinux user.

  ```bash
  semanage user -a -R "staff_r sysadm_r" -r s0-s0:c0.c1023 staff_u
  ```

---

## 🔹 Troubleshooting & Logs

* `ausearch -m avc -ts recent` → Search audit logs for recent AVC (Access Vector Cache) denials.
* `audit2why < /var/log/audit/audit.log` → Explain why an access was denied.
* `audit2allow -w -a` → Summarize denials into human-readable reasons.
* `audit2allow -a -M mypol` → Generate a custom policy module from denials.
* `semodule -i mypol.pp` → Install generated policy module.

---

## 🔹 Booleans (Toggling SELinux Features)

* `getsebool -a` → List all SELinux booleans.
* `getsebool <boolean>` → Show status of a boolean.
* `setsebool <boolean> on|off` → Temporarily set a boolean.
* `setsebool -P <boolean> on|off` → Persistently set a boolean.

  ```bash
  setsebool -P httpd_can_network_connect on
  ```

---

# ✅ Quick Notes

* **`chcon`** → Temporary label change.
* **`restorecon`** → Restore default label from policy.
* **`semanage fcontext`** → Persistent label assignment.
* **`audit2allow`** → Convert denials into policy rules.

SELinux labels = `user:role:type:level` → Most important field is **type** (process domain ↔ file type enforcement).

