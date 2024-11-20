# SC2002_Hospital Management System

This is our group assignment for SC2002 (Object Oriented Programming).

![HMS preview](https://github.com/user-attachments/assets/0c332592-f1df-4be2-ae18-2f2ed15da71b)

## Introduction
The Hospital Management System (HMS) is a Java console application developed using the object-oriented concepts. This project illustrates how different roles communicate efficient with each other, ensuring the functionality of the application.

# Setup Instructions

## Project Folder Structure

> Folder structure of our project

#### Top Level Directory Layout

```terminal
.                
├── Doc                     # Javadocs generated as html
├── src                     # Source files (all the codes)
└── README.md
```

#### Source files

```terminal
.
├── ...
├── src                    # Source files (all the codes)
│   ├── Controller         # Managers classes
│   ├── Enum               # Enumeration classes
│   ├── Helper             # Helper classes
│   ├── Main               # Main Driver file (HMS App)
│   ├── Model              # Model classes
│   └── Repository         # Repository
|   └── View               # View classes
└── ...
```

## Scripts

> How to run our project

1. In your command line change directory into SC2002_HMS

```terminal
C:\file_path_to_source_codes\SC2002
```

2. Reset our database first before using our interface, steps are as follows

```terminal
╔═════════════════════════════════════════════════════════════════╗
║ Hospital Management App View                                    ║
╚═════════════════════════════════════════════════════════════════╝
Who would you like to continue as?
(1) Staff
(2) Patient
(3) Reset database
(4) Quit Hospital Management App
```

`Press "3" then <ENTER>`

```terminal
╔═════════════════════════════════════════════════════════════════╗
║ Hospital Management App View > Database View                    ║
╚═════════════════════════════════════════════════════════════════╝
What would you like to do ?
(1) Initialize guests
(2) Initialize menu
(3) Reset database
(4) Exit Database View
```

`Press "3" then <ENTER>`

```terminal
╔═════════════════════════════════════════════════════════════════╗
║ Hospital Mangement App View > Repository View > Reset database  ║
╚═════════════════════════════════════════════════════════════════╝
Are you sure you want to reset the database? (yes/no)
```

`Type "yes" then <ENTER>`

```terminal
╔═════════════════════════════════════════════════════════════════╗
║ Hospital Mangement App View > Repository View > Reset database  ║
╚═════════════════════════════════════════════════════════════════╝
Are you sure you want to reset the database? (yes/no)
yes
Database cleared. Returning to the main menu...
```

`Database is cleared successfully if the above message is shown`

## UML Class Diagram
The UML diagram is generated using 

Alternatively, You can choose to refer to our 

# Contributors

- @JiachiK      
- @WeijunCheah
- @JasmineTye
- @DCXY1997
- @bryanlyc20
