# RestAssured Requester

A wrapper framework for RestAssured that simplifies the management, creation, and editing of DTOs. and much more....
## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Tests](#tests)
- [Contributing](#contributing)
- [License](#license)

## Introduction

This repository provides a wrapper framework for RestAssured, aiming to streamline the process of managing, creating, and editing Data Transfer Objects (DTOs) when interacting with RESTful APIs. The framework is designed to enhance readability, maintainability, and ease of use.

## Features

- **Endpoint Management:** Simplified creation and management of API endpoints.
- **JSON Utilities:** Utility classes for handling JSON content, including validation and manipulation.
- **DTO Handling:** Effortless handling of Data Transfer Objects (DTOs) for API interactions.
- **Schema Validation:** Support for schema-based validation of API responses.

## Getting Started

To get started, clone the repository to your local machine:

```bash
git clone https://github.com/GBursali/ra-requestor.git
cd ra-requestor
```
Make sure you have Java and Maven installed. You can build the project using:
```bash
mvn clean install
```

## Usage
### Creating a Base
Before defining the endpoints, we need a Base Class. This class handles our approach to the other endpoints.
#### Base with the Base URI
```java
EndpointBase base = EndpointBase.builder("https://api.example.com");
```
#### Base for JSON DTO's
Simply chain the ```withJsonBasePath(Path)``` function to the Base class.
```java
EndpointBase base = EndpointBase.builder("https://api.example.com")
    .withJsonBasePath(Path.of("src", "test", "resources"));
```
### Creating an Endpoint
After you prepared your Base, you can create an ```Endpoint``` instance using
```java
Endpoint endpoint = base.makeWithJson("sample_endpoint.json");
```
This will pull the settings from the JSON file. This JSON file should be under the ```Path``` where you defined via ```withJsonBasePath()``` 

If you don't want to use json files, you can define a simple endpoint with:
```java
Endpoint endpoint = base.makeWithPath("/api/path");
```
This path is automatically added to the Base's builder parameter. If you need a Full-url path, you can create another Base.
### Sending a Request
After you have built your Endpoint, sending a request is simple:
```java
Response response = endpoint.send();
```

## Tests
The repository includes unit tests demonstrating the usage of the RestAssured wrapper framework. You can run the tests using:
```bash
mvn test
```
These tests serve as examples and provide insights into how the framework can be utilized for API testing and interaction.

## Contributing
Contributions are welcome! Feel free to open issues, suggest improvements, or submit pull requests.

- Fork the repository.
- Create a new branch: git checkout -b feature-name
- Make your changes and commit them: git commit -m 'Add feature'
- Push to the branch: git push origin feature-name
- Open a pull request.

## License
This project is licensed under the [Apache 2.0 License].


[Apache 2.0 License]:./LICENSE
