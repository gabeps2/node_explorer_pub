# Node Explorer

**Node Explorer** is a native Android application built using Kotlin and Jetpack Compose. It displays a list of the 100 most well-connected nodes on the Bitcoin Lightning Network using public data from the [Mempool.space API](https://mempool.space).

The app presents key information about each node, such as alias, public key, number of channels, capacity in Bitcoin, location (when available), and relevant timestamps. It supports loading states, error handling, and a manual refresh mechanism.


## Features

- Fetches and displays the top 100 most connected Lightning nodes
- Presents detailed information for each node:
    - **Public Key** – Unique identifier for the node
    - **Alias** – User-defined name of the node
    - **Number of Channels** – Total channels the node has
    - **Capacity** – Total Bitcoin held in the node's channels, displayed in BTC
    - **First Seen** – Timestamp of when the node was first made public (formatted)
    - **Last Updated** – Most recent data update timestamp (formatted)
    - **Location** – City and country, localized to pt-BR when available
- Pull-to-refresh to reload the node list manually
- Error and loading state indicators for a smooth user experience
- Mark nodes as favorites and highlight them for quick access
- Local caching with Room Database to store and retrieve favorite nodes offline
- Built using a modern Android architecture (MVVM + Jetpack Compose)
- Core logic is unit tested

## Technologies

- Kotlin
- Jetpack Compose
- Retrofit + Gson (Networking & JSON parsing)
- Room
- Koin
- Kotlin Coroutines
- ViewModel (Jetpack)

## Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- Android SDK 24 or higher
- JDK 17+

### How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/gabeps2/node_explorer_pub.git
   cd node-explorer
   ```

2. Open the project in Android Studio.

3. Connect an Android device or start an emulator.

4. Click **Run** or use the command:
   ```bash
   ./gradlew installDebug
   ```

### Running Tests

To run unit tests:
```bash
./gradlew testDebug
```

To run Jacoco code coverage:
```bash
./gradlew JacocoDebugCodeCoverage
```

Coverage report file location:
```bash
./app/build/reports/jacoco/JacocoDebugCodeCoverage/html/index.html
```

## Planned Improvements

- **Integrate Timber for logging** – Replace default logging with Timber for better control, filtering, and structured log output during development and debugging.
- **Implement a Global Theme** – Create a centralized design system with consistent color palettes and typography to ensure a cohesive look and feel across the app.
- **Add Internationalization (i18n) support** – Enable the app to support multiple languages beyond pt-BR and en, preparing it for global use and better accessibility.
- **Expand Test Coverage to 50%** – Increase confidence in code reliability by adding more unit and instrumentation tests, particularly around UI and ViewModel logic.
- **Introduce Dark Mode** – Add support for a dark theme that respects the system UI mode to improve UX in low-light environments.
- **Create a Splash Screen** – Design and implement a branded splash screen that displays while the app initializes, enhancing the perceived performance and first-time user experience.

## Design References

The UI design for this app was based on a Figma community layout by **Renata Pôrto**.  
Figma: [Design System](https://www.figma.com/community/file/1129468881607079432)

## App References

Developed by **Gabriel Silva** – 2025
- GitHub: [github.com/gabeps2](https://github.com/gabeps2)
- LinkedIn: [linkedin.com/in/gabrielps2](https://www.linkedin.com/in/gabrielps2/)
- Email: gabriel.psilva226@gmail.com
