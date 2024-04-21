# System Design FAQ

## Contents


### [Web server vs. Application server](https://aws.amazon.com/compare/the-difference-between-web-server-and-application-server)
Web servers and application servers are the technologies that allow the exchange of data and services over the internet. The client-server architecture is the underlying mechanism of the internet. When you visit a website or application, your browser (as the client) requests data from a remote server and displays the response. A **web server** is a software component that delivers **static data** like images, files, and text in response to client requests. An **application server** adds **business logic** to compute the web server's response. Both terms are used synonymously, and the most popular server software solutions today are hybrid web application servers.

### [Forward vs Reverse Proxy](https://blog.hubspot.com/website/reverse-proxy)

Forward proxies and reverse proxies are similar in function, but they’re not quite the same. The key difference is this: A **forward proxy** generally works on behalf of the client making requests, while the **reverse proxy** generally works on behalf of the server receiving requests (hence the “reverse” in the name)
