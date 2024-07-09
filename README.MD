# Full Stack Note-taking Application

## Table of Contents

1. [Introduction](#introduction)
2. [Technologies Used](#technologies-used)
3. [App Setup](#setup-instructions)
4. [App Run](#running-the-application)
5. [Test App](#testing-application)


## Introduction 
This repository contains the implementation of a full-stack web application for managing notes. The application is structured as a Single Page Application (SPA), with separate frontend and backend components.

## Technologies Used

- **Frontend:**
  - Vue.js 3 with TypeScript
  - Vite for development
  - Axios for API communication
  - Tailwind CSS for styling

- **Backend:**
  - Spring Boot 3.3.1 (Java)
  - PostgreSQL 16.3 as Database
  - Docker for containerization
  - OpenAPI for API documentation

## Setup Instructions

### Prerequisites

- Docker

Ensure Docker is installed on your local machine. You can install Docker on your OS following the [official guide](https://docs.docker.com/engine/install/).

1. Clone the repository:
```bash
  git clone https://github.com/ensolvers-github-challenges/CayllahuaGutierrez-7b94af.git
  cd CayllahuaGutierrez-7b94af
```
## Running the Application

2. Run the Following Command in terminal:
```bash
  docker-compose up --build
```

## Testing Application

1. The Web App will start in port 3000, insert this url in the web browser `http://localhost:3000` or click here:
[App Link](http:localhost:3000)

2. If you want to watch all the endpoints in swagger, go to  `http://localhost:8080/swagger-ui/index.html#/` or click here:
[API Doc](http://localhost:8080/swagger-ui/index.html#/)


