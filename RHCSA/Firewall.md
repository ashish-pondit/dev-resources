# Linux Networking, Firewall & Time Management Cheat Sheet

## 🔹 Network Interfaces

* **List network interfaces:**

  ```bash
  ip link show
  ip l
  ```

* **Show IP addresses:**

  ```bash
  ip address show
  ip a
  ```

* **Show routing table:**

  ```bash
  ip route show
  ip r
  ```

* **Check DNS settings:**

  ```bash
  cat /etc/resolv.conf
  ```

* **/etc/hosts vs /etc/hostname:**

  * `/etc/hosts` → Maps hostnames to IPs (local DNS resolution)
  * `/etc/hostname` → Defines the system hostname

* **Set static hostname:**

  ```bash
  sudo hostnamectl set-hostname myserver
  ```

* **Add a temporary IP address:**

  ```bash
  sudo ip a add 10.0.0.50/24 dev eth1
  ```

* **Do it with nmtui:**

  1. Run `sudo nmtui`
  2. Edit connection → select your interface (`enp0s3`, `ens33`, etc.)
  3. Set IPv4 CONFIGURATION → Manual
  4. Add IP, Gateway, DNS
  5. Save and activate connection

---

## 🔹 Firewall (firewalld)

### Default Zone

```bash
firewall-cmd --get-default-zone
firewall-cmd --set-default-zone=public
```

### View Rules

```bash
sudo firewall-cmd --list-all
sudo firewall-cmd --info-service=cockpit
```

### Open / Close Ports & Services

```bash
sudo firewall-cmd --add-service=http           # allow HTTP temporarily
sudo firewall-cmd --add-port=80/tcp            # allow TCP port 80 temporarily
sudo firewall-cmd --remove-service=http        # remove HTTP temporarily
sudo firewall-cmd --remove-port=80/tcp         # remove TCP port 80 temporarily
```

### Zones & Sources

```bash
sudo firewall-cmd --add-source=10.11.12.0/24 --zone=trusted    # trusted source (temporary)
sudo firewall-cmd --remove-source=10.11.12.0/24 --zone=trusted # remove source
sudo firewall-cmd --get-active-zones                           # see active zones
```

### Make Changes Permanent

```bash
sudo firewall-cmd --runtime-to-permanent         # save runtime rules permanently
sudo firewall-cmd --add-port=80/tcp --permanent  # permanent open port
sudo firewall-cmd --reload                       # apply permanent rules
```

---

## 🔹 Static IP Routing

### View Routes

```bash
ip route show
ip r
netstat -rn
```

### Add Temporary Routes

```bash
sudo ip route add 192.168.20.0/24 via 192.168.10.1 dev eth0   # network route
sudo ip route add 192.168.20.50 via 192.168.10.1 dev eth0     # host route
```

### Delete Routes

```bash
sudo ip route del 192.168.20.0/24
sudo ip route del 192.168.20.50
```

### Default Gateway

```bash
sudo ip route add default via 192.168.10.1 dev eth0
sudo ip route del default
```
These routing are not permanent. It is only active for the current session.

## Permanent Routes
### Permanent Routes Cheat Sheet

**1. Check current active connections**

```bash
sudo nmcli connection show
```

* Lists all network connections managed by NetworkManager.

**2. Add a permanent route to a connection**

```bash
sudo nmcli connection modify [nic] +ipv4.routes "192.168.0.0/24 10.0.0.100"
```

* `[nic]` is a placeholder for your **network interface name** (e.g., `eth0`, `enp3s0`).
* Example:

```bash
sudo nmcli connection modify eth0 +ipv4.routes "192.168.0.0/24 10.0.0.100"
```

* Meaning: to reach network `192.168.0.0/24`, route traffic via `10.0.0.100`.

**3. Apply changes immediately (no reboot needed)**

```bash
sudo nmcli device reapply [nic]
```

* Example:

```bash
sudo nmcli device reapply eth0
```

**4. Verify the route**

```bash
ip route show
```

**5. Remove a permanent route**

```bash
sudo nmcli connection modify [nic] -ipv4.routes "192.168.0.0/24 10.0.0.100"
```

* Example:

```bash
sudo nmcli connection modify eth0 -ipv4.routes "192.168.0.0/24 10.0.0.100"
```

---

**Notes:**

* `[nic]` is generic and should be replaced with your actual network interface.
* Use `+` to add a route and `-` to remove.
* Changes are persistent and stored in NetworkManager configuration.

---

**Related topics for deeper understanding:**

1. How Linux routing table works
2. Difference between static and dynamic routing
3. ip route vs route command
4. Network namespaces and routing isolation
5. Advanced route options: metrics, default routes, policy routing



---

## 🔹 Time & NTP Management

### Chrony Service

```bash
sudo systemctl status chronyd.service   # check if Chrony (NTP sync) is running
```

### View Time Info

```bash
timedatectl
```

* Shows local time, UTC, timezone, RTC, NTP status

### Set Timezone

```bash
sudo timedatectl set-timezone Asia/Dhaka
```

### List Timezones

```bash
timedatectl list-timezones
```

### Enable/Disable NTP Sync

```bash
sudo systemctl set-ntp true   # enable auto time sync
sudo systemctl set-ntp false  # disable auto time sync
```

---

✅ This document covers **interfaces, firewall, routing, and time management** with compact commands and notes for quick reference.
