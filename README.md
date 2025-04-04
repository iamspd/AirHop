# AirHop

AirHop is an Android flight search app built using modern development practices. The app leverages the MVVM architecture pattern, manual dependency injection (DI), Jetpack Compose for UI creation, and Room for local database management. It comes with thorough unit and instrumentation tests to maintain quality and reliability.

## Overview

AirHop enables users to search for flights quickly and easily. The app features a prepopulated **Airport** table (loaded from an asset) and an initially empty **Favorite** table. As users enter search queries, the app dynamically displays a list of available airports and generates a flight list based on the selected departure airport. Users can mark flights as favorites and manage these selections seamlessly.

## User Flow

1. **Home Screen:**  
   - The only screen in the app toggles between the flight list and favorites list.
2. **Searching for Flights:**  
   - The user types a departure airport name or code into the search bar.
   - The ViewModel debounces input and uses flatMapList to provide a filtered list of available airports in a LazyColumn.
3. **Generating Flight List:**  
   - Upon selecting a departure airport, the ViewModel maps the remaining airports to create a dynamic list of possible flight routes.
4. **Managing Favorites:**  
   - Users tap the star icon on a flight card to mark it as favorite.
   - In the favorites view, users can remove flights from their favorites list.
5. **Persisting Search Data:**  
   - The search query is stored using the Preference Data Store for a seamless user experience.

## Screenshots

<img src="https://github.com/user-attachments/assets/27ed4536-b462-47fb-b1d8-a3a008dc48a1" alt="home screen" style="width:20%; height:auto;"/>
<img src="https://github.com/user-attachments/assets/088aba37-7ee1-49d4-8f91-b34ee714ff25" alt="search bar component" style="width:20%; height:auto;"/>
<img src="https://github.com/user-attachments/assets/8f33203a-cdb1-44e7-886c-246be1faa72d" alt="flight list screen" style="width:20%; height:auto;"/>
<img src="https://github.com/user-attachments/assets/74d63076-914f-4d1f-9f52-09c6d0c4212b" alt="favorites list screen" style="width:20%; height:auto;"/>

## Architecture

The application follows a clear separation of concerns:

- **Model:**  
  - **Room Database:** Contains two tables: one for airports (prepopulated from an asset) and one for favorite flights.
- **ViewModel:**  
  - Manages UI state and business logic.
  - Implements live search using debounce and flatMapList to fetch available airports dynamically.
  - Generates a flight list by mapping the selected departure airport with other airports from the database.
- **View (Jetpack Compose):**  
  - A single-screen interface that swaps between the dynamically created flight list view and the favorite list view based on ViewModel flags and the search query.
- **Repository Layer:**  
  - Handles data operations for the Room database and interacts with the Preference Data Store.
 
## Testing

- **Instrumentation Tests:**  
  - Run tests on Room DAO operations for both the airport and favorite entities.
- **Unit Tests:**  
  - Use the JUnit Temporary Folder rule to test ViewModel logic and repository interactions (including the preference data store).

 ## Directory Structure
 ```AirHop/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/         # Application source code (MVVM, Jetpack Compose UI, Room database)
│   │   │   └── res/          # Resources for the application
│   │   ├── androidTest/      # Instrumentation tests (DAO operations & database testing)
│   │   └── test/             # Unit tests (Preference Data Store using TemporaryFolder rule)
  
