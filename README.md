# Steam Library Manager

Steam Library Manager is a Java application designed to help users store, organize, and track their Steam games and related statistics. It provides a structured way to manage your game library, including metadata, playtime, achievements, and more.  

## Features

- Store basic game information: title, developer, publisher, price, and release date.  
- Track playtime and completion of achievements.  
- Keep record of review scores and the game engine used.  
- Easily expandable to include additional statistics or integration with Steam APIs.  

## Data Model

The core of the project is the `Game` class, which represents a single game in your library. Each game stores the following information:  

- **App ID**: Unique Steam identifier for the game.  
- **Title**: Name of the game.  
- **Developer & Publisher**: Information about who made and published the game.  
- **Price**: Current or purchase price of the game.  
- **Review Score**: Steam review score or rating.  
- **Release Date**: When the game was released.  
- **Playtime**: Total hours played.  
- **Achievements**: Number of achievements completed vs total achievements.  
- **Game Engine**: The engine used to develop the game.  

This structure allows users to quickly query, sort, and manage their library in a clean and organized way.  

## Example Usage

```java
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        Game myGame = new Game(
            123456,
            "My Awesome Game",
            "GameDev Studio",
            "GamePub Co.",
            29.99,
            87.5,
            Date.valueOf("2023-10-15"),
            42.3,
            15,
            50,
            "Unreal Engine 5"
        );

        System.out.println("Game title: " + myGame.getTitle());
        System.out.println("Achievements completed: " + myGame.getAchievementsCompleted());
    }
}
