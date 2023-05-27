 ## Android Repo - PL Matches
This repository contains an Android application for displaying Premier League (PL) matches using the MVVM architecture, modularity, Hilt for dependency injection, and unit tests with Retrofit.

 ## Features
View the list of Premier League matches.
Filter matches by Date.
Mark matches as favorites.
View favorite matches.
Modular project structure for better maintainability and scalability.
Dependency injection using Hilt for clean and efficient code.
Unit tests with Retrofit for API integration testing.
 ## Architecture
The application follows the MVVM (Model-View-ViewModel) architectural pattern with Intents Consept From MVI((Model-View-Intent), which promotes separation of concerns and enables easier testing and maintenance. The key components of the architecture are:

- Model: Represents the data and business logic. It includes entities, repositories, and data sources.
- View: Responsible for displaying the UI to the user. It includes activities, fragments, and layouts.
- ViewModel: Acts as a bridge between the Model and View. It holds the data and exposes it to the View through observable properties. It also handles user interactions and triggers appropriate actions in the Model.
Project Structure
The project is structured using a modular approach to enhance modularity and maintainability. The modules in the project are:

## app: Contains the main application module.
- domain: Contains the core business logic and use cases.
- data: Handles data retrieval and manipulation. Includes remote and local data sources.
- presentation: Implements the UI components and ViewModels.
- test: Contains unit tests for various modules.
Dependency Injection with Hilt
Hilt is used for dependency injection in the application. It simplifies the process of managing dependencies and provides a clean and efficient way to inject dependencies into classes. By using Hilt, the code becomes more modular, testable, and scalable.

## Unit Testing
The project includes unit tests to ensure the correctness and reliability of the application. These tests are located in the test module and cover various aspects of the application, including ViewModel, UseCase, and API integration testing with Retrofit. The tests are written using JUnit and Mockito frameworks.

## Retrofit for API Integration
Retrofit is used for making network requests and handling API integration in the application. It provides a type-safe and efficient way to communicate with the server by converting API calls into Java/Kotlin method calls. Retrofit integrates seamlessly with other libraries such as OkHttp and Gson to handle network communication and JSON parsing.

## Prerequisites
To build and run the application, you need the following:

- Android Studio
- Android SDK with API 
- Gradle 

## Getting Started
- Clone the repository: git clone https://github.com/mohamedOsama2929/PL-Matches
- Open the project in Android Studio.
- Build the project.
- Run the application on an emulator or physical device.
## Contributing
Contributions are welcome! If you find any issues or want to enhance the application, feel free to create a pull request or submit an issue.

## Acknowledgements
We would like to express our gratitude to the following libraries and frameworks used in this project:

Retrofit: https://square.github.io/retrofit/
Hilt: https://dagger.dev/hilt/
JUnit: https://junit.org/junit5/
Mockito: https://site.mockito.org/
Contact
For any questions or inquiries, please contact mohamedosama2929@gmail.com.

Happy coding!
