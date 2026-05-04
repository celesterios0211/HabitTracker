
## Overview

The Habit Tracker App is a Java Swing GUI application that allows users to create, manage, and track habits. Users can categorize habits, assign frequencies (daily, weekly, hourly), mark them as completed, and monitor their progress over time.

This project demonstrates key Object-Oriented Programming (OOP) concepts in a real-world application.

---

## Features

* Add new habits
* Edit existing habits
* Delete habits
* Categorize habits (School, Health, Work, etc.)
* Set habit frequency (Daily, Weekly, Hourly)
* Mark habits as completed
* Track streaks and completion counts
* View habits in a table format
* Progress bar showing completion progress
* Last completed date tracking
* Save and load habits from a file (`habits.txt`)

---

## User Stories

* As a user, I should be able to create a new habit so that I can track my daily routines.
* As a user, I should be able to mark a habit as completed so that I can maintain a streak.
* As a user, I should be able to view my progress so that I can stay motivated.
* As a user, I should be able to categorize habits so that I can organize them easily.
* As a user, I should be able to edit a habit if I want to update it.
* As a user, I should be able to delete a habit that I no longer need.
* As a user, I should be able to save my habits so that my data persists after closing the app.

---

## Technologies Used

* Java
* Java Swing (GUI)
* File I/O (for saving/loading data)

---

## OOP Concepts Demonstrated

### Classes & Objects

The program uses multiple classes such as:

* `Habit` (abstract base class)
* `DailyHabit`, `WeeklyHabit`, `HourlyHabit`
* `HabitTrackerGUI`

Each habit is an object created from these classes.

---

### Encapsulation

The `Habit` class keeps its attributes (`name`, `category`, `streak`, etc.) private and provides public getter/setter methods to control access.

---

### Inheritance

`DailyHabit`, `WeeklyHabit`, and `HourlyHabit` inherit from the abstract `Habit` class, allowing shared behavior while customizing frequency.

---

### Polymorphism

All habit types are stored in a single:

```java
ArrayList<Habit>
```

This allows the program to treat different habit types uniformly while still using their specific implementations.

---

### Abstraction

The `Habit` class is abstract and defines the method:

```java
public abstract String getFrequency();
```

Each subclass implements this method differently.

---

## Project Structure

```
HabitTrackerApp/
│
├── src/
│   ├── Main.java
│   ├── Habit.java
│   ├── DailyHabit.java
│   ├── WeeklyHabit.java
│   ├── HourlyHabit.java
│   └── HabitTrackerGUI.java
│
├── habits.txt
├── README.md
└── .gitignore
```

---

## How to Run

### 1. Compile the program

```bash
cd src
javac *.java
```

### 2. Run the application

```bash
java Main
```

---

## Data Storage

* Habits are saved in a file called `habits.txt`
* The file is automatically created after saving habits
* Data is loaded when the app starts

---

