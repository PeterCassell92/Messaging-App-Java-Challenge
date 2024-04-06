# Basic Messaging App: Java Console UI

## Overview
This project delivers a simple yet effective messaging application built around a console user interface (UI) in Java. Inspired by a challenge introduced approximately 5 hours into [this tutorial video](https://www.youtube.com/watch?v=fis26HvvDII&t=17814s), the task was to develop a functional console UI for managing contacts and messages. The application mimics real-world messaging apps but operates entirely through the console, offering a retro feel combined with the robustness of modern Java programming.

## Features
The messaging app includes the following primary features, accessible through a clean and intuitive main menu:

### Main Menu Options
1. **Manage Contacts** - Allows the user to perform various operations related to contact management.
2. **Messages** - User can view, send, and manage their messages.
3. **Quit** - Exits the application.

### Manage Contacts
Within the 'Manage Contacts' section, users can:
1. **Show All Contacts** - Displays a list of all saved contacts.
2. **Add New Contact** - Enables the addition of new contacts to the user's contact list.
3. **Search for Contact** - Allows searching for a specific contact by name.
4. **Delete a Contact** - Removes a chosen contact from the list.

## Enhancements
Building on the initial specifications, several enhancements have been made to improve user experience and application maintainability:
- **Conversation Grouping**: Messages are organized by conversation, mirroring the structure seen in popular messaging applications.
- **Object-Oriented Design**: The application leverages classes to manage menu options and services, ensuring a clear separation of concerns and enhancing code readability.
- **Service-Oriented Architecture**: Interactions with the underlying data storage are abstracted through service classes. While the current implementation uses in-memory storage, this architecture allows for easy transition to persistent storage solutions such as SQL databases.

## Getting Started
To run this application, ensure you have Java installed on your machine. Clone this repository, navigate to the project directory, and compile the Java files. You can start the application by running the main class.

```bash
# Clone the repository
git clone <repository-url>

# Navigate to the project directory
cd basic-messaging-app

# Compile the Java files
javac *.java

# Run the application
java Main
