# Cheat Sheet

## GREP
```bash
grep [options] "pattern" file
```
Examples:

```bash
grep "root" /etc/passwd                # simple search
grep -i "root" /etc/passwd             # case-insensitive
grep -v "root" /etc/passwd             # invert match
grep -r "Listen" /etc/httpd/           # recursive search
grep -n "root" /etc/passwd             # show line numbers
grep -c "root" /etc/passwd             # count matches
grep -E "root|admin" /etc/passwd       # extended regex
```

**Using with pipes:**

```bash
cat /etc/passwd | grep "root"
cat /var/log/messages | grep -i "error"
cat file1 file2 | grep "pattern"
cat /var/log/messages | grep "error" | grep -i "ssh"
```

## Find
```bash
find [path] [options] [expression]
```

Examples:

```bash
find / -name "passwd"                   # search by name
find /var -type f -size +10M            # files >10MB
find /home -type d -name "backup"       # search directories
find /tmp -mtime -1                      # modified in last 24h
find /etc -perm 644                     # files with specific permissions
find /var -type f -exec ls -l {} \;     # execute command on found files
```

## SED
```bash
sed [options] 'command' file
```

Examples:
```bash
sed 's/root/admin/' /etc/passwd          # substitute first match in line
sed 's/root/admin/g' /etc/passwd         # substitute all matches in line
sed -i 's/root/admin/g' /etc/passwd      # edit file in-place
sed -n '1,5p' /etc/passwd                # print lines 1-5
sed '/root/d' /etc/passwd                # delete lines matching pattern
sed -n '/root/p' /etc/passwd             # print only matching lines
```

## AWK
```bash
awk 'pattern {action}' file
```

Examples:
```bash
awk '{print $1}' /etc/passwd             # print first column
awk -F: '{print $1,$3}' /etc/passwd      # custom field separator
awk '/root/ {print $0}' /etc/passwd      # print lines matching pattern
awk 'NR==3 {print $0}' /etc/passwd       # print specific line
awk '{sum+=$3} END {print sum}' file     # sum values in 3rd column
```

**Using with pipes:**
```bash
cat /etc/passwd | awk -F: '{print $1}' | grep "root"
```

## TAR
```bash
tar [options] -f archive.tar files
```

Examples:
```bash
tar -cvf backup.tar /etc                 # create archive
tar -xvf backup.tar                       # extract archive
tar -tvf backup.tar                       # list contents
tar -czvf backup.tar.gz /etc              # create compressed archive
tar -xzvf backup.tar.gz                   # extract compressed archive
tar --exclude='*.log' -czvf backup.tar.gz /var/log   # exclude files
```

## CAT
```bash
cat [options] file
```

Examples:
```bash
cat /etc/passwd                           # display file
cat file1 file2 > merged.txt              # merge files
cat -n file                               # number all lines
cat -b file                               # number non-empty lines only
```
**Using with pipes:**
```bash
cat /etc/passwd | grep "root"
cat file1 file2 | grep "pattern"
```

## LESS
```bash
less file
```

Examples:
```bash
less /etc/passwd
less +G /var/log/messages                 # go to end
less +/pattern /var/log/messages          # search for pattern
```

## MORE
```bash
more file
```

Examples:
```bash
more /etc/passwd
more +5 file                              # start at line 5
```

Pipes & Filters (Practical Examples)
```bash
cat /var/log/messages | grep "error" | less        # view filtered logs
cat /etc/passwd | awk -F: '{print $1,$3}' | grep "root"
grep -r "Listen" /etc/httpd/ | less               # recursive search with pager
```