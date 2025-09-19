# Kernel parameters — runtime (sysctl)

**Short summary:**
Kernel parameters (aka *sysctl* or *kernel tunables*) are runtime variables that control kernel behaviour (networking, memory management, filesystems, security, etc.). They live in **/proc/sys/** and are exposed/managed with the `sysctl` tool. You can change them **temporarily** (runtime only) or **persistently** (by writing configuration files read at boot).

---

## 1) What are kernel parameters and what are they used for?

* They are kernel-exposed configuration values (tunables) used to tweak kernel behaviour without recompiling the kernel.
* Common categories: `net.*` (networking), `vm.*` (virtual memory), `fs.*` (filesystem), `kernel.*` (core kernel settings), `fs.file-max`, `kernel.pid_max`, `vm.swappiness`, `net.ipv4.ip_forward`, `kernel.sem`, etc.
* Use-cases: enable packet forwarding, tune swapping behaviour, change maximum number of file handles, adjust TCP stack behaviour for performance, tighten security parameters (for example disable IP forwarding or enable rp\_filter), tuning for databases/web servers/container hosts.

---

## 2) Where these live on the system (mapping)

* All these parameters are provided by **procfs** under `/proc/sys`.
* Dotted sysctl names map to files by replacing `.` with `/`.

  * Example: `vm.swappiness` -> `/proc/sys/vm/swappiness`
  * Example: `net.ipv4.ip_forward` -> `/proc/sys/net/ipv4/ip_forward`
* You can read/write those files directly (with root) e.g. `cat /proc/sys/vm/swappiness` or `echo 10 | sudo tee /proc/sys/vm/swappiness` — but `sysctl` is safer and more convenient.

---

## 3) Useful `sysctl` commands (quick reference)

* Show **all** kernel parameters:

  ```bash
  sudo sysctl -a
  ```
* Filtered view (example: all `vm` parameters):

  ```bash
  sudo sysctl -a | grep '^vm\.'
  ```
* Read a single parameter (human friendly):

  ```bash
  sudo sysctl vm.swappiness
  # or just value only
  sudo sysctl -n vm.swappiness
  ```
* Change a parameter immediately (non-persistent):

  ```bash
  sudo sysctl -w vm.swappiness=10
  # output: vm.swappiness = 10
  ```
* Load settings from a file (single file):

  ```bash
  sudo sysctl -p /etc/sysctl.d/swap-less.conf
  ```
* Reload **all** sysctl configuration files (system-wide):

  ```bash
  sudo sysctl --system
  ```

  This reads files from the standard directories (see below) and applies them.
* Show kernel file mapping (raw):

  ```bash
  cat /proc/sys/vm/swappiness
  ```

---

## 4) Example output and explanation

**Example:**

```
$ sudo sysctl -a | grep '^vm\.'
vm.swappiness = 60
vm.dirty_ratio = 20
vm.dirty_background_ratio = 10
```

**What the parts mean:**

* `vm.swappiness` — the **name** (namespace `vm`, key `swappiness`).
* `=` — separator used by `sysctl` output.
* `60` — the **current value** used by the kernel right now.

**Multi-valued example:**

```
kernel.sem = 250 32000 100 128
```

* Some sysctl keys contain multiple space-separated numbers. For `kernel.sem` those four numbers represent: `SEMMSL SEMMNS SEMOPM SEMMNI` (see `man kernel-parameters` or `man sem` for meanings) — this is a semaphores configuration example.

**Mapping to /proc/sys:**

* `vm.swappiness = 60` corresponds to `/proc/sys/vm/swappiness` which contains `60`.
* Dots in the sysctl name become slashes in the path: `a.b.c` -> `/proc/sys/a/b/c`.

---

## 5) Non-persistent change (runtime only)

* `sudo sysctl -w KEY=VALUE` writes the value to the kernel immediately (updates `/proc/sys/...`) but **does not** persist across reboots.
* Equivalent low-level method (also runtime-only):

  ```bash
  echo VALUE | sudo tee /proc/sys/path/to/key
  ```
* If the kernel rejects the value (range/type), writing will fail and usually a message will appear.

**Good practice:** Before changing a value, read the current value and save it (so you can restore it):

```bash
OLD=$(sysctl -n vm.swappiness)
sudo sysctl -w vm.swappiness=10
# to restore later
sudo sysctl -w vm.swappiness=${OLD}
```

---

## 6) Making changes persistent

* **Location:** persistent sysctl settings are kept in `.conf` files under the *sysctl.d* directories (files **must** end in `.conf`).

  * Typical directories and their precedence (when `sysctl --system` or systemd-sysctl reads them):

    1. `/etc/sysctl.d/*.conf` (administrator, highest precedence)
    2. `/run/sysctl.d/*.conf`
    3. `/usr/local/lib/sysctl.d/*.conf`
    4. `/usr/lib/sysctl.d/*.conf`
    5. `/lib/sysctl.d/*.conf` (if present on your distro)
  * Files are sorted lexicographically by filename across those directories; the lexicographically later file (or a file in a higher-precedence directory) can override earlier entries.
  * Numeric prefixes are commonly used to control order, e.g. `10-network.conf`, `99-local.conf`.
* **Basic file format:** each non-comment line should be `key = value` or `key=value`. Lines starting with `#` are comments.

**Example:** create `/etc/sysctl.d/swap-less.conf`

```ini
# reduce swap aggressiveness for memory-heavy workloads
vm.swappiness = 15
```

* After creating and saving the file, it will be applied automatically at next boot by `systemd-sysctl` on systemd systems. However the current running kernel still has the old value until you load the file.

**Apply immediately from that file:**

```bash
sudo sysctl -p /etc/sysctl.d/swap-less.conf
# or to reload *all* sysctl configuration files
sudo sysctl --system
```

`sysctl -p` reads a single file and writes its settings to `/proc/sys`. `sysctl --system` reads all standard sysctl.d directories and applies them in precedence order.

**Legacy file:** `/etc/sysctl.conf` is a traditional single-file location. On modern systemd systems the `sysctl.d` hierarchy and `systemd-sysctl` are preferred; behaviour for `/etc/sysctl.conf` can be distro-specific (some tools still load it).

---

## 7) How systemd interacts with sysctl

* On systemd-based systems, `systemd-sysctl.service` runs early in boot and applies `.conf` files from the sysctl.d directories.
* `sudo systemctl status systemd-sysctl.service` and `journalctl -u systemd-sysctl` are useful for debugging boot-time application of sysctl settings.

---

## 8) Common pitfalls & debugging

* **File must end with `.conf`** or system tools will ignore it.
* **Lexicographic order & precedence:** if a value you set isn't taking effect, search other `/etc/sysctl.d` or `/usr/lib/sysctl.d` files for the same key — a later filename may override it.
* **Typo in key name** will be ignored. Check `/proc/sys` path to verify.
* **Multi-valued keys:** ensure you supply correct number of fields and correct ordering; some keys expect several numbers.
* **Kernel rejects out-of-range values** — check kernel logs and the error returned by `sysctl -w`.
* When in doubt, run: `sudo sysctl --system` and then inspect `/proc/sys/...` and `journalctl -xe` for messages.

---

## 9) Safety & best practice

* Always record the original value before changing so you can revert.
* For production systems, make persistent changes via configuration management (Ansible/Puppet/Chef) or by creating `/etc/sysctl.d/XX-name.conf` with a clear comment and version control.
* Avoid changing kernel values blindly — read `man 5 sysctl.d`, `man sysctl`, or specific kernel docs; test in a staging environment.

---

## 10) Quick cheatsheet (commands & equivalents)

* Read current: `sysctl vm.swappiness`  — `cat /proc/sys/vm/swappiness`
* Read just the value: `sysctl -n vm.swappiness`
* Set now (non-persistent): `sudo sysctl -w vm.swappiness=10`  — `echo 10 | sudo tee /proc/sys/vm/swappiness`
* Make persistent: create `/etc/sysctl.d/99-local.conf` with `vm.swappiness=15`
* Apply file immediately: `sudo sysctl -p /etc/sysctl.d/99-local.conf`
* Apply all config files: `sudo sysctl --system`

---

## 11) Advanced notes (short)

* **Kernel command-line vs sysctl:** kernel boot parameters (passed via bootloader / kernel command-line) are a separate mechanism (affect kernel at boot) and are not the same as sysctl runtime tunables. Some settings are only available or meaningful at boot.
* **Configuration management:** populate `/etc/sysctl.d/` via Ansible/puppet for reproducible systems.
* **Namespacing:** `net.*`, `vm.*`, `fs.*`, `kernel.*` — useful to filter and find keys with `sysctl -a | grep '^net\.'`.

---

## 12) Big exercise (hands-on)

> **Goal:** Learn how to check, change, persist and debug sysctl settings safely.

**Step 1 — Explore current values**

1. Run: `sudo sysctl -a | grep '^vm\.'` and pick `vm.swappiness`.
2. Save the current value: `OLD=$(sysctl -n vm.swappiness)`
3. Note the file mapping: `cat /proc/sys/vm/swappiness` (should match `OLD`).

**Step 2 — Change non-persistently**

1. Set value runtime-only: `sudo sysctl -w vm.swappiness=10`
2. Verify: `sysctl vm.swappiness` and `cat /proc/sys/vm/swappiness`.
3. Reboot the VM (optional) to verify it returns to original — but *don’t reboot yet* if you want to keep the change.

**Step 3 — Make it persistent**

1. Create `/etc/sysctl.d/99-custom-swappiness.conf` with contents:

   ```ini
   # lower swappiness for database workload
   vm.swappiness = 15
   ```
2. Apply immediately: `sudo sysctl -p /etc/sysctl.d/99-custom-swappiness.conf` (or `sudo sysctl --system`).
3. Verify: `sysctl vm.swappiness`
4. Reboot the machine and verify the value persists after boot: `sysctl vm.swappiness`.

**Step 4 — Find conflicts and order**

1. Search for other definitions: `sudo grep -R "vm.swappiness" /etc/sysctl.d /run/sysctl.d /usr/lib/sysctl.d || true`.
2. If another file exists with the same key, experiment with ordering by renaming files to `10-...` or `99-...` and re-run `sudo sysctl --system` to see which final value wins.

**Step 5 — Multi-valued parameter**

1. Show: `sysctl kernel.sem` (ex: `250 32000 100 128`).
2. Research what each number means (hint: semaphore limits — `man sem` or kernel docs).
3. Try to change carefully (take backup of original) and apply via a `/etc/sysctl.d/` file.

**Step 6 — Undo / restore**

1. If you saved `OLD` earlier, restore with: `sudo sysctl -w vm.swappiness=${OLD}`
2. Remove or edit the `/etc/sysctl.d/99-custom-swappiness.conf` if necessary and reload with `sudo sysctl --system`.

**Bonus challenge (networking):**

* Turn on IP forwarding runtime: `sudo sysctl -w net.ipv4.ip_forward=1`.
* Make persistent: add `net.ipv4.ip_forward = 1` to `/etc/sysctl.d/10-network.conf` and `sudo sysctl --system`.
* Test forwarding by creating two network namespaces and routing traffic between them.

---

## 13) Things you might want to study next (suggestions for deep dives)

* Kernel boot-time parameters vs sysctl tunables (how and when to use each).
* `systemd-sysctl` and how systemd applies sysctl configs at boot.
* In-depth: `vm.*` tunables — what `swappiness`, `dirty_ratio`, `dirty_background_ratio`, `oom_kill` do to performance.
* `net.*` tunables for TCP tuning (tcp\_tw\_reuse, tcp\_fin\_timeout, tcp\_max\_syn\_backlog, etc.).
* Filesystem tunables (`fs.*`), `fs.inotify.max_user_watches` tuning.
* `kernel.*` limits (e.g., `pid_max`, `core_pattern`), and `fs.file-max` and per-user limits (`ulimit`/`/etc/security/limits.conf`).
* How configuration management tools (Ansible/Puppet) manage sysctl settings reliably.

---

## 14) Useful man-pages

* `man sysctl` (or `man 8 sysctl`)
* `man 5 sysctl.d`
* `man systemd-sysctl.service`

---

If you want, I can now:

* produce a shorter printable cheat-sheet (one page), or
* walk you through the *big exercise* step-by-step with commands you can paste and run, or
* deep-dive into a specific tunable (for example: `vm.swappiness` or `net.ipv4.tcp_tw_reuse`) and explain the inner workings and performance implications.

Which would you like next?
