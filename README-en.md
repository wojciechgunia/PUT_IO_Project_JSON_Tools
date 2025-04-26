[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/wojciechgunia/PUT_IO_Project_JSON_Tools/blob/main/README-en.md)

# JSON Tools - Backend

### GUI Angular: [JSON Tools FE](https://github.com/wojciechgunia/PUT_IO_Project_JSON_Tools_FE)

### Authors of the completed project
<img src="https://skillicons.dev/icons?i=github" height="25" alt="github logo"/> [Wojciech Gunia](https://github.com/wojciechgunia)<br>
<img src="https://skillicons.dev/icons?i=github" height="25" alt="github logo"/> [Remigiusz Janicki](https://github.com/TheRemekk)<br>
<img src="https://skillicons.dev/icons?i=github" height="25" alt="github logo"/> [Filip Kempa](https://github.com/Pilif102)<br>
<img src="https://skillicons.dev/icons?i=github" height="25" alt="github logo"/> [Paweł Kołaciński](https://github.com/KolacinskiP)

## Table of contents

1. [Description](#l1)
2. [Installation](#l2)
3. [Available features](#l3)
4. [Project structure](#l4)

<a id="l1"></a>
## Description

Application that formats and filters data structures stored in JSON format. It also compares these structures with each other. 
JSON Tools allows you to minify unminified JSON representations and perform the reverse operation, representing the full structure with spaces and new lines. 

The project uses a REST API, a programming interface based on REST architecture that enables communication between clients and servers via the HTTP protocol.

The project is written in Java using [Spring Boot](https://github.com/spring-projects/spring-boot) version 3.1.X.

<a id="l2"></a>
## Installation

To run this project locally, follow these steps:

```bash
  cd <path_in_which_you_want_to_host_the_project>
  git clone https://github.com/wojciechgunia/PUT_IO_Project_JSON_Tools
  ``` 

I recommend using the IntelliJ environment to interact with the application.  
The port on which the application runs can be changed in the .properties file.
For example, for a server running locally on port 8080, the base URL would look like this:

```bash
http://localhost:8080/api/v1/jsontools/..
```

The program is a web application with a graphical interface created in the Angular CLI environment. It can be found in the [JSON Tools FE](https://github.com/wojciechgunia/PUT_IO_Project_JSON_Tools_FE) repository.

Use HTTP request tools such as Postman, cURL, or any other tool that supports the REST API to access and test the available endpoints.

<a id="l3"></a>
## Available features

Endpoints that you can use to transform the JSON structure:
* load-json - Loads the structure as text.
  * POST method
  * Accepts content in text format. Followed by a parameter that will be the name of the entered structure.
* get-json - Retrieves the structure in JSON form.
  * GET method
  * Accepts the name of the structure as a parameter
* get-original - Retrieves the structure in text form.
  * GET method
  * Takes the name of the structure as a parameter.
* get-minimalize - Displays the minified JSON representation.
  * GET method
  * Accepts the name of the structure as a parameter
* get-full - Displays an expanded JSON representation.
  * GET method
  * Accepts the name of the structure as the first parameter.
* get-filtered - Shows only the requested keys and values.
  * GET method
  * Accepts the name of the structure as the first parameter.
  * As the second parameter, accepts the keys to be left with the values.
* get-without - Removes the specified keys and values.
  * GET method
  * Accepts the name of the structure as the first parameter.
  * As the second parameter, accepts the keys to be removed.
* get-differences - Compares two previously loaded structures.
  * GET method
  * Names of the previously entered structures

<a id="l4"></a>
## Project structure

The following classes are found in the project, along with their functionalities:
* JsonToolsController: Used to handle endpoints created using the REST API.
* JsonToolsService: A class that implements functions called by endpoints to separate application logic from the controller.
* Response: A class that displays information to the user, such as error codes, execution dates and times, and server messages.
* Code: An ENUM class that stores messages sent to the user when an endpoint is called by Response.
