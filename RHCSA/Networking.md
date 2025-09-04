# Networking cheat sheet

### 🔍 Interfaces & IPs
```bash
ip link show        # Show network interfaces
ip l                # Short form

ip address show     # Show IP addresses
ip a                # Short form
```

### 🛣 Routes & Gateway
```bash
ip route show       # Show routing table
ip r                # Short form
```
### 🌐 DNS & Hosts
```bash
cat /etc/resolv.conf   # Check current DNS resolvers
sudo nano /etc/hosts   # Edit static hostname-to-IP mappings
```
### ⚙️ NetworkManager
```bash
nmtui                         # Text UI to configure network
nmcli                         # Command-line network manager
sudo nmcli device reapply ens33   # Reapply config for interface
sudo systemctl status NetworkManager.service  # Check service status
```
### 📡 Connections (Ports & Sockets)

Using `ss` (modern replacement for netstat)
```bash
ss -tuln               # Show listening TCP/UDP sockets
ss -s                  # Summary of connections
sudo ss -ltunep        # Show listening ports + processes + users
```
Using `netstat` (older tool, may be missing)
```bash
netstat -tuln          # Show listening TCP/UDP sockets
netstat -anp           # Show all connections + processes
sudo netstat -plnt     # Show listening TCP ports + process names
```

### ⏱ Time Sync
```bash
systemctl status chronyd.service  # Check NTP time sync status
```
### 📂 /etc/hosts

- Purpose: Maps hostnames to IP addresses (local DNS override).

- Format:
```bash
IP_address   hostname   alias
```

Example:
```bash
127.0.0.1   localhost
192.168.1.10   db.local   db
```

- Use case:

    - Resolve names without using DNS.

    - Useful in small networks or for testing.

    - If /etc/hosts contains an entry, it overrides DNS queries.

👉 Think of it as a manual phonebook for hostname-to-IP mapping.


### 📂 /etc/hostname

- Purpose: Stores the system’s hostname (the name identifying the machine itself).

- Example:
```nginx
webserver01
```

- Use case:

    - Sets what your system calls itself (hostname command shows this).

    - Used in shell prompts, logs, and for other machines to recognize your system on the network.

    - Can be changed with:
    ```bash
    hostnamectl set-hostname newname
    ```
(this updates /etc/hostname).

👉 Think of it as the name tag your system wears.

### Others
- Assigns the IP address 10.0.0.50 with netmask /24 to the eth1 network interface.
    ```bash
    sudo ip a add 10.0.0.50/24 dev eth1
    ```

- Add a secondary IP (alias):
    ```bash
    sudo ip a add 10.0.0.51/24 dev eth1
    ```

- Remove an IP:
    ```bash
    sudo ip a del 10.0.0.50/24 dev eth1
    ```

- Check applied IPs:
    ```bash
    ip a show dev eth1
    ```