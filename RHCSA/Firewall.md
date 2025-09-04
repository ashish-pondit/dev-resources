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

### Permanent Routes

* **RHEL / CentOS / Fedora:**

  * Add routes to `/etc/sysconfig/network-scripts/route-<interface>`

  ```
  192.168.20.0/24 via 192.168.10.1 dev eth0
  default via 192.168.10.1 dev eth0
  ```

  * Restart:

  ```bash
  sudo systemctl restart network
  ```

* **Ubuntu / Debian (Netplan):**

  ```yaml
  network:
    version: 2
    ethernets:
      eth0:
        addresses: [192.168.10.100/24]
        gateway4: 192.168.10.1
        routes:
          - to: 192.168.20.0/24
            via: 192.168.10.1
  ```

  ```bash
  sudo netplan apply
  ```

### Debug

```bash
ip route show
ping 192.168.20.1
traceroute 192.168.20.1
```

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
