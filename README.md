Messenger Chat Application

![Messenger Chat Application](https://raw.githubusercontent.com/bot-alert/messenger/main/ui/ss.png)

A real-time chat application built with Spring Boot and WebSocket.




## Overview

Messenger is a real-time chat application that allows users to interact with each other through chat rooms. It is built using Spring Boot and WebSocket technology, providing a seamless chat experience.


## Features

- Create and join chat rooms.
- Real-time chat with other users.
- User authentication and registration.
- Responsive and user-friendly UI.

---

## REST API

The Messenger chat application exposes a RESTful API for managing chat users and chat rooms.

### Endpoints

- `GET /api/v1/chat-user`: Get a list of chat users.
- `PUT /api/v1/chat-user/{id}`: Update a chat user's information.
- `POST /api/v1/chat-user`: Create a new chat user.
- `POST /api/v1/chatroom/generate`: Generate a chat room.
- `GET /api/v1/chatroom/{id}/user`: Get users in a specific chat room.

## WebSocket

Messenger also utilizes WebSocket for real-time communication.

### WebSocket Endpoints

- Register a user:
  - WebSocket Endpoint: `/app/register`
  - Subscribe Destination: `/user/queue/register`

- Send a chat message:
  - WebSocket Endpoint: `/app/chat`
  - Subscribe Destination: `/user/queue/chat`

---

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 21 or higher.
- Maven for building the project.
- Your preferred IDE (e.g., IntelliJ IDEA or Eclipse).

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/botalert/messenger.git
   cd messenger-chat-app
   mvn clean install
   mvn spring-boot:run

## Usage

1. Open your web browser and navigate to chat.html in UI folder (first register room and user using rest endpoints).

2. Create or join chat rooms.

3. Start chatting in real-time with other users.

## Contributing

Contributions are welcome! Feel free to open issues and pull requests.

1. Fork the repository.
2. Create your feature branch: `git checkout -b feature/your-feature`.
3. Commit your changes: `git commit -m 'Add some feature'`.
4. Push to the branch: `git push origin feature/your-feature`.
5. Open a pull request.
