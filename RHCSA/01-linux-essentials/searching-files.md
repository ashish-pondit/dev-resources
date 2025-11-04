## Searching Files with `find`

The `find` command in Linux is used to search for files and directories in a directory hierarchy based on various conditions like name, size, ownership, permissions, and time.

---

### **Syntax**

```
find [path] [search_option] [action]
```

* **path** – where to start searching (e.g., `/`, `/home`, `.`)
* **search option** – criteria (e.g., `-name`, `-user`, `-size`)
* **action** – what to do with matched files (e.g., `-print`, `-delete`)

---

### **Common Examples**

#### **1. Find file by name (case sensitive & insensitive)**

```
find /path -name filename.txt       # Case-sensitive search
find /path -iname filename.txt      # Case-insensitive search
```

#### **2. Find file by size**

```
find /path -size 10M                # Files exactly 10MB
find /path -size +10M               # Files greater than 10MB
find /path -size -10M               # Files less than 10MB
```

#### **3. Find file by ownership**

```
find /path -user username           # Files owned by user
find /path ! -user username         # Files NOT owned by user
```

#### **4. Find file by group ownership**

```
find /path -group groupname         # Files owned by group
find /path ! -group groupname       # Files NOT owned by group
```

#### **5. Mix ownership and group search**

```
find /path -user username -group groupname
```

#### **6. Find by maxdepth and type**

```
find /path -maxdepth 2 -type f      # Only files within 2 levels
find /path -maxdepth 1 -type d      # Only directories in top level
```

#### **7. Find by modification time**

```
find /path -mtime -7                # Modified in last 7 days
find /path -mtime +30               # Modified more than 30 days ago
find /path -mmin -60                # Modified within last 60 minutes
```

#### **8. Find file by permission**

```
find /path -perm 644                # Files with exact 644 permissions
find /path -perm -u+x               # Files user can execute
find /path -perm /222               # Files writable by anyone
find /path ! -perm 644              # Files NOT having 644 permissions
```

---

### **Useful Actions**

```
-print          # Display results (default)
-delete         # Delete found files
-exec cmd {} \; # Execute a command on found files
```

Example:

```
find /path -name "*.log" -exec rm {} \;
```

---

### **Related Topics to Learn Next:**

* `locate` vs `find`
* Using `xargs` with `find`
* File timestamps: atime, mtime, ctime
* `grep` inside `find`

---

### **Useful Man Pages:**

```
man find
man xargs
```
