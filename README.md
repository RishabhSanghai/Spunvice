# Spunvice: The Unified App Marketplace

**Spunvice** is a unified, cross-platform application marketplace designed to streamline the app discovery and download experience. This project, created for the 21CSC203P Advanced Programming Practice course, reimagines the app store by amalgamating iOS and Android marketplaces into a single, seamless hub for both users and developers.

## The Problem
In the current mobile ecosystem, users are forced to navigate fragmented app stores (like the Apple App Store for iOS and the Google Play Store for Android). Developers, in turn, must manage and promote their applications on multiple platforms, dividing their user base.

## Our Solution
[cite_start]Spunvice eliminates this fragmentation[cite: 12]. It provides a single, elegant storefront where users can find any application. [cite_start]The system's core intelligence lies in its ability to detect the user's operating system (OS) upon a download request[cite: 11]. It then automatically delivers the correct, platform-specific build (e.g., `.ipa` for iOS or `.apk` for Android), creating a simple, frictionless experience.

## Key Features

* **Unified Marketplace:** A single, comprehensive catalog of both iOS and Android applications.
* [cite_start]**Intelligent OS Detection:** Automatically detects the user's OS to provide the correct download file[cite: 11].
* [cite_start]**Seamless User Experience:** Removes the confusion and fragmentation of using separate stores[cite: 12].
* **Developer-Friendly:** Allows developers to host and manage their applications from one centralized platform.

## Technology Stack

* **Backend:** Java (using technologies like Servlets, JDBC)
* **Frontend:** Java (using Maven, SpringBoot)
* **Database:** Supabase
* **APIs:** Java-based REST APIs
* **Deployment:** Apache Tomcat

## How It Works

1.  **Browse:** A user (on any device) browses the unified `spunvice.com` webstore.
2.  **Select:** The user finds an app and clicks "Download".
3.  **Detect:** The server receives the request and analyzes the User-Agent string to identify the user's OS (iOS, Android, Windows, etc.).
4.  **Deliver:** The system serves the appropriate file (e.g., `AppName.apk`) if the user is on Android, or `AppName.ipa` if on iOS.

## Future Roadmap

* [ ] Implement developer upload and management portals.
* [ ] Add user authentication and app review systems.
* [ ] Develop a native client application for desktop and mobile.

## Author

* **Rishabh Sanghai** - [github.com/RishabhSanghai](https://github.com/RishabhSanghai)
