# 📝 Managing Containers on Red Hat (Docker & Podman)

## 🔹 1. Container Management on Red Hat

On **Red Hat Enterprise Linux (RHEL)**, the default container engine is **Podman**, not Docker. However, Docker commands can still be used because:

* **Podman is daemonless** (doesn’t need a background service).
* Podman provides a **Docker-compatible CLI** (the `docker` command can map to Podman).
* If you install Docker, internally it may redirect to Podman through a compatibility package.

### Key Configurations

* **Set default registry**:

  ```bash
  sudo vi /etc/containers/registries.conf
  ```

  Add:

  ```
  unqualified-search-registries = ["docker.io"]
  ```

* **Disable Docker usage (force Podman)**:

  ```bash
  sudo touch /etc/containers/nodocker
  ```

---

## 🔹 2. Working with Images

* **Search for an image**:

  ```bash
  docker search nginx
  ```

* **Pull an image**:

  ```bash
  docker pull docker.io/library/nginx
  ```

* **List local images**:

  ```bash
  docker images
  ```

* **Remove an image**:

  ```bash
  docker rmi nginx
  docker rmi <imageID>
  docker rmi --force nginx
  ```

---

## 🔹 3. Running Containers

* **Run a container from image**:

  ```bash
  docker run nginx
  docker run <imageID>
  ```

* **Run in detached mode**:

  ```bash
  docker run -d nginx
  ```

* **Run with port mapping & custom name**:

  ```bash
  docker run -d -p 8080:80 --name webserver nginx
  ```

---

## 🔹 4. Managing Containers

* **List running containers**:

  ```bash
  docker ps
  docker container list
  ```

* **List all containers (including stopped)**:

  ```bash
  docker ps --all
  ```

* **Stop a container**:

  ```bash
  docker stop <containerID or name>
  ```

* **Start a container**:

  ```bash
  docker start <containerID or name>
  ```

* **Remove a container**:

  ```bash
  docker rm <containerID or name>
  ```

---

## 🔹 5. Common Docker Commands Cheat Sheet

* **Check system info**:

  ```bash
  docker info
  ```
* **View logs of a container**:

  ```bash
  docker logs <containerID or name>
  ```
* **Execute command inside container**:

  ```bash
  docker exec -it <containerID or name> /bin/bash
  ```
* **Inspect container details**:

  ```bash
  docker inspect <containerID or name>
  ```
* **Show resource usage**:

  ```bash
  docker stats
  ```

---

## 🔹 6. Summary

* RHEL prefers **Podman** over Docker.
* Installing Docker often means you’re using **Podman behind the scenes**.
* Managing containers involves:

  * **Images** (search, pull, list, delete)
  * **Containers** (run, stop, start, remove)
  * **Networking** (port mapping)
  * **Logs, exec, inspect** for debugging.

---

## 🔹 Suggested Next Topics

* Podman vs Docker (deep dive into architecture).
* Container networking and storage in RHEL.
* Building custom images with **Dockerfile/Containerfile**.
* Managing container lifecycle with **systemd**.
* Introduction to **Kubernetes/OpenShift**.

---

## 📖 Useful Man Pages

* `man podman`
* `man docker`
* `man containers-registries.conf`
