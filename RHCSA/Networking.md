# Networking cheat sheet
### Short Definitions

**DNS (Domain Name System):** Translates human-friendly domain names (like `example.com`) into IP addresses that computers use to locate servers.

**Router:** A device that forwards data packets between different networks. It connects your local network to other networks (like the Internet).

**Gateway:** A device that acts as an entry/exit point for a network, usually connecting a local network to the Internet. In many home setups, the router is also the gateway.

---

### Step-by-Step Process When Accessing a Domain

1. **You type a URL in the browser**

   * Example: `http://example.com`

2. **DNS Resolution**

   * Browser checks local DNS cache.
   * If not found, request goes to your configured DNS server (usually provided by ISP or Google DNS).
   * DNS server resolves `example.com` to an IP address (e.g., `93.184.216.34`).

3. **Packet Preparation**

   * Your device prepares an IP packet destined for the resolved IP.
   * Packet encapsulates data (HTTP request) inside TCP, IP headers.

4. **Routing Through Gateway**

   * Packet is sent to your local gateway (usually your router).
   * Router examines the destination IP and forwards the packet towards the Internet.

5. **Traversal Across Routers**

   * Packet may pass through multiple routers across ISPs and backbone networks.
   * Each router examines the destination IP and forwards accordingly.

6. **Arrival at Destination Server**

   * Packet reaches the server hosting `example.com`.
   * Server processes HTTP request and sends a response back.

7. **Response Back to Your Device**

   * Response packets travel back through routers to your gateway.
   * Gateway forwards to your local device.

8. **Browser Receives Response**

   * Browser receives the HTTP response and renders the page.

---

### Diagram

```
+-----------+         +-----------+         +------------+         +-----------+
| Your      |         | Gateway/  |         | ISP Router |         | Web       |
| Device    | ------> | Router    | ----->  | Backbone   | ----->  | Server    |
| (Browser) |         | (Gateway) |         | Routers    |         | (example) |
+-----------+         +-----------+         +------------+         +-----------+
       |                     |                    |                      |
       | DNS Request          | Routing            | Routing              | Response
       |-------------------->|------------------->|--------------------->|
       | DNS Response         |                    |                      |
       |<--------------------|<------------------|<---------------------|
```

---

This shows how DNS, gateway, and routers interact to deliver web content.

**Related in-depth topics you could explore next:**

1. DNS internals (recursive vs iterative queries, caching, root servers)
2. TCP/IP packet structure and headers
3. Routing tables and how routers decide paths
4. NAT (Network Address Translation) at the gateway
5. HTTP request/response flow and TCP handshake

Which of these topics do you want to dive into first?




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


# 📝 nmcli Cheat Sheet

## 🔹 View Connections

* `nmcli connection show` → List all saved connections
* `nmcli connection show --active` → Show only active connections
* `nmcli device status` → Show network interfaces and status

---

## 🔹 Manage Connections

* `sudo nmcli connection up <connection-name>` → Bring connection up
* `sudo nmcli connection down <connection-name>` → Bring connection down
* `sudo nmcli connection delete <connection-name>` → Remove a saved connection

---

## 🔹 Autoconnect Settings

* `sudo nmcli connection modify <connection-name> autoconnect yes` → Enable autoconnect
* `sudo nmcli connection modify <connection-name> autoconnect no` → Disable autoconnect

---

## 🔹 Create Connections

* `sudo nmcli connection add type ethernet ifname eth0` → Create Ethernet connection
* `sudo nmcli connection add type wifi ifname wlan0 ssid <SSID>` → Create Wi-Fi connection

---

## 🔹 Wi-Fi Specific Commands

* `nmcli device wifi list` → Show available Wi-Fi networks
* `nmcli device wifi connect <SSID> password <password>` → Connect to Wi-Fi
* `nmcli device disconnect wlan0` → Disconnect Wi-Fi interface

---

## 🔹 Practice Exercise

👉 **Scenario:** You want your laptop to always connect automatically to your office Wi-Fi (`Office-WiFi`) and remove an old connection `Home-WiFi`. Then verify the active connections.

### Step 1 – Enable autoconnect for Office-WiFi

```bash
sudo nmcli connection modify Office-WiFi autoconnect yes
```

### Step 2 – Delete the old Home-WiFi connection

```bash
sudo nmcli connection delete Home-WiFi
```

### Step 3 – Verify active connections

```bash
nmcli connection show --active
```

✅ **Expected Output (example):**

```
NAME          UUID                                  TYPE      DEVICE
Office-WiFi   a1b2c3d4-1234-5678-9abc-d1234e5678f9  wifi      wlan0
```
