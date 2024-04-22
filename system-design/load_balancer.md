# Load Balancer

## Contents
1. What is Load Balancer?
2. Why do we need a load balancer?
3. Load balancing algorithms
4. How does a load balancer work?
5. Load balancer technology
6. Load balancer types
7. The OSI model and Load balancer
8. Load balancer in popular cloud provider

## What is a Load Balancer

A load balancer acts as the **traffic cop** sitting in front of your servers and routing client requests across all servers capable of fulfilling those requests in a manner that maximizes speed and capacity utilization and ensures that no one server is overworked, which could degrade performance. If a single server goes down, the load balancer **redirects traffic** to the remaining online servers. When a new server is added to the server group, the load balancer automatically starts to send requests to it. [[1]](#references)

## Why do we need load balancer?
Suppose we have a application running on a single server. Now there are some concerns like:
- What happens when the single server goes down?

    The application will be unavailble since the only server is down 
- What happens if network traffic get increased?
    
    If suddenly a lot of users try to access the application then the server will be overwhelmed and unable to serve new traffic
- Is the server secure safe? 

    If someone try DDOS attact on the site then it surely will go down unable to handle the huge traffic

So load balancer come to the rescue. It offers the following:
1. **Application availability** with high fault tolerance
2. Ensures **Application scalability** by redirecting traffic to multiple servers
3. Improve **Applicatioin Performance** by reducing the response time and latency
4. Increase **Application security** by monitoring traffic and block malicious content



## Resources
1. System Design Interview - An Insider's Guide by `Alex Xu`


## References
1. [What Is Load Balancing? - NGINX](https://www.nginx.com/resources/glossary/load-balancing/)