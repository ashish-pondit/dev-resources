![](./images/queue.jpg)

# Message Queue
> A message queue is a form of asynchronous service-to-service communication used in serverless and microservices architectures. Messages are stored on the queue until they are processed and deleted. Each message is processed only once, by a single consumer. [[1]](#references)

## Contents
1. [What is message queue?]()
2. [Why we need message queue?]()
3. [How a message queue works?]()
4. [Types of message queue]()
5. [Real world example of message queue]()


## What is a message queues?
A Message Queue is a form of communication and data transfer mechanism used in computer science and system design. It functions as a temporary storage and routing system for messages exchanged between different components, applications, or systems within a larger software architecture.

Example:

A real life exmple would be **food panda app**. We order food in food panda and get it by the delivey man. but how the restaurent knows, how the delivery driver know? Here a lot of parties are involved just like a distributed system. One party is the restaurent, another party is the delivery man and of course the person who place the order. Restaurent, the delivery person are working independently but they are being communicating through the food panda app. 


Likewise in large system different component work independently but communicate with each other through **message queue**.

Another nice example is how Starbucks works [[2]](#references).


## Why do we need message queue?
Now we know that message queue is for communicating between different components but why do we need message queue? Why not communicate with each other directly?

Actually there are couple of problems:
1. Each component will have to have some mechanism to communicate
2. Component will have to wait for response
3. It is not guaranteed that the receiver component have received the message
4. Communication is an extra burden on the module/component.
5. If system is very large then communication between will get messier.

So message queue solves these problems and do much more.

### Benefits of Message Queues [[2]](#references)
1. Asynchronous Processing
2. Decoupling
3. Fan-out
4. Rate Limiting
5. Message Persistence
6. Batch Processing
7. Message Ordering

## How a message queue works?
![Message Queue Image](./images/message%20queue.png)




## Example of Message Queue
1. RabbitMQ
2. Apache Kafka
3. ActiveMQ
4. Amazon Simple Queue Service
5. IBM MQ

## Message Queue Protocol
1. IBM WebSphere MQ (proprietary)
2. Apache kafka (Open source)
3. AMQP (Open source)

## Temporary resources
1. [System Design - GeeksforGeeks](https://www.geeksforgeeks.org/message-queues-system-design/)
2. [How Message Queues Work in Distributed Systems - freeCodeCamp](https://www.freecodecamp.org/news/message-queues-in-distributed-systesms/)
3. [Messsage Broker vs Message Queue](https://www.svix.com/resources/faq/message-broker-vs-message-queue/)
4. [What is RabbitMQ - IBM Technology Youtube](https://www.youtube.com/watch?v=7rkeORD4jSw)
5. [RabbitMQ Tutorial - Message Queues and Distributed Systems - Amigoscode](https://www.youtube.com/watch?v=nFxjaVmFj5E&pp=ygUabWVzc2FnZSBicm9rZXIgYW1pZ29zIGNvZGU%3D)
6. 



## References
1. [What is a Message Queue? - AWS](https://aws.amazon.com/message-queue/)
2. [Message Queue - BYTEBYTEGO](https://blog.bytebytego.com/p/why-do-we-need-a-message-queue)