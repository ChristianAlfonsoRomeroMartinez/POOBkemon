# Trainer Game

## Overview
Trainer Game is a strategic battle game where players can choose between different types of trainers, each with unique strategies and abilities. The game utilizes the Factory design pattern to create trainers and follows the Model-View-Controller (MVC) architecture to separate concerns and maintain clean code.

## Project Structure
```
TrainerGame
├── src
│   ├── controller
│   │   └── GameController.java
│   ├── model
│   │   ├── Trainer.java
│   │   ├── DefensiveTrainer.java
│   │   ├── AttackingTrainer.java
│   │   ├── TrainerFactory.java
│   │   └── GameLogic.java
│   └── view
│       ├── GameView.java
│       └── TrainerSelectionView.java
├── test
│   └── TrainerGameTests.java
└── README.md
```

## Features
- **Trainer Selection**: Players can choose between defensive and attacking trainers.
- **Battle Mechanics**: Each trainer has unique moves and strategies that affect the outcome of battles.
- **Game Flow Management**: The `GameController` class manages the game state and user interactions.

## Getting Started
1. Clone the repository:
   ```
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```
   cd TrainerGame
   ```
3. Compile the project:
   ```
   javac -d bin src/controller/*.java src/model/*.java src/view/*.java
   ```
4. Run the game:
   ```
   java -cp bin controller.GameController
   ```

## Design Patterns
- **Factory Pattern**: The `TrainerFactory` class is used to create instances of trainers based on user selection, promoting loose coupling and enhancing code maintainability.
- **MVC Architecture**: The project is structured into three main components:
  - **Model**: Contains the game logic and data structures.
  - **View**: Responsible for the user interface and displaying game information.
  - **Controller**: Manages the flow of the game and user input.

## Testing
Unit tests are provided in the `test` directory to ensure the functionality of the core components. Use a testing framework like JUnit to run the tests.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any suggestions or improvements.