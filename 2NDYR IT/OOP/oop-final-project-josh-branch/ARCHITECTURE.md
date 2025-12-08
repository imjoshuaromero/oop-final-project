# SSC Student Welfare System - Architecture Documentation

## Overview
This document explains the industry-level architecture using **Interface-Based Design** and **Dependency Injection** patterns.

## Architecture Pattern: Layered Architecture with Dependency Injection

### 1. **Interface Layer** (Abstraction Layer)
Defines contracts for all operations without implementation details.

#### Files:
- `IStudentOperations.java` - Student-related operations contract
- `IAdminOperations.java` - Admin-related operations contract
- `IAuthenticationService.java` - Authentication operations contract
- `IDataPersistence.java` - Data storage operations contract

**Purpose**: 
- Loose coupling between components
- Easy to test (can mock implementations)
- Easy to change implementations without affecting other code

### 2. **Implementation Layer** (Business Logic Layer)
Implements the interfaces with actual business logic.

#### Files:
- `StudentOperationsImpl.java` - Implements `IStudentOperations`
  - Student registration
  - Student validation
  - Filing concerns
  - Viewing student concerns
  
- `AdminOperationsImpl.java` - Implements `IAdminOperations`
  - View all concerns
  - Update concern status
  - Delete concerns
  
- `AuthenticationServiceImpl.java` - Implements `IAuthenticationService`
  - Student authentication
  - Admin authentication
  - Password management
  
- `DataPersistenceImpl.java` - Implements `IDataPersistence`
  - Load/save students
  - Load/save grievances
  - JSON parsing

**Purpose**:
- Encapsulates business logic
- Separates "what to do" from "how to do it"
- Makes code more maintainable

### 3. **Presentation Layer** (UI Layer)

#### Files:
- `Main.java` - Application entry point with dependency injection
- `MenuHandler.java` - Handles all menu navigation using injected services
- `Utility.java` - UI utilities (menus, titles, prompts)
- `UIHelper.java` - User feedback messages

**Purpose**:
- Handles user interaction
- Calls business logic through interfaces
- No business logic in UI code

### 4. **Entity Layer** (Data Models)

#### Files:
- `User.java` - Abstract base class for users
- `Student.java` - Student entity
- `Admin.java` - Admin entity
- `Grievance.java` - Concern/complaint entity
- `AppData.java` - Container for application data

**Purpose**:
- Represents data structures
- Contains only data and simple getters/setters
- No business logic

### 5. **Utility Layer**

#### Files:
- `PasswordUtils.java` - Password hashing utilities
- `StudentValidator.java` - SR code and email validation
- `DataManager.java` - Legacy data manager (still used for static methods)
- `AuthService.java` - Legacy auth service (still used for static methods)
- `StudentService.java` - Legacy student service (replaced by StudentOperationsImpl)

## Dependency Injection Flow

```
Main.java
  ↓
  Creates implementations:
  - new StudentOperationsImpl()
  - new AdminOperationsImpl()
  - new AuthenticationServiceImpl()
  - new DataPersistenceImpl()
  ↓
  Injects into MenuHandler constructor
  ↓
MenuHandler uses interfaces:
  - IStudentOperations
  - IAdminOperations  
  - IAuthenticationService
  - IDataPersistence
```

### Key Benefits:

1. **Loose Coupling**: MenuHandler doesn't know about concrete implementations
2. **Easy Testing**: Can inject mock implementations for testing
3. **Flexibility**: Can swap implementations without changing MenuHandler
4. **Clean Code**: Each class has single responsibility

## How to Extend

### Adding a New Feature:

1. **Define interface method**:
   ```java
   // In IStudentOperations.java
   void exportConcerns(Student student, String filePath);
   ```

2. **Implement method**:
   ```java
   // In StudentOperationsImpl.java
   @Override
   public void exportConcerns(Student student, String filePath) {
       // Implementation here
   }
   ```

3. **Use in MenuHandler**:
   ```java
   // MenuHandler already has studentOps injected
   case 5:
       studentOps.exportConcerns(student, "export.txt");
       break;
   ```

No need to modify Main.java or other classes!

## Code Organization Improvements

### Before (Tightly Coupled):
```java
// MenuHandler calling concrete classes directly
StudentService.registerStudent(students);  // Static call
admin.viewAllConcerns(grievances);         // Business logic in entity
```

### After (Interface-Based):
```java
// MenuHandler using injected dependencies
studentOps.registerStudent(students);      // Interface call
adminOps.viewAllConcerns(grievances);      // Business logic in service
```

## Student-Friendly Explanation

Think of it like a restaurant:

- **Interfaces** = Menu (lists what's available)
- **Implementations** = Kitchen (makes the food)
- **MenuHandler** = Waiter (takes orders, doesn't cook)
- **Main.java** = Restaurant Manager (hires staff, sets everything up)

The waiter (MenuHandler) only knows the menu (interfaces), not how to cook. The kitchen (implementations) does the actual cooking. This way, you can change chefs (implementations) without retraining waiters!

## File Structure

```
src/
├── Interfaces (Contracts)
│   ├── IStudentOperations.java
│   ├── IAdminOperations.java
│   ├── IAuthenticationService.java
│   └── IDataPersistence.java
│
├── Implementations (Business Logic)
│   ├── StudentOperationsImpl.java
│   ├── AdminOperationsImpl.java
│   ├── AuthenticationServiceImpl.java
│   └── DataPersistenceImpl.java
│
├── Presentation (UI)
│   ├── Main.java
│   ├── MenuHandler.java
│   ├── Utility.java
│   └── UIHelper.java
│
├── Entities (Data Models)
│   ├── User.java
│   ├── Student.java
│   ├── Admin.java
│   ├── Grievance.java
│   └── AppData.java
│
└── Utilities
    ├── PasswordUtils.java
    ├── StudentValidator.java
    ├── DataManager.java (legacy)
    ├── AuthService.java (legacy)
    └── StudentService.java (legacy)
```

## Compilation & Running

```bash
# Compile
cd src
javac -d ..\bin *.java

# Run
cd ..\bin
java Main
```

## Design Patterns Used

1. **Dependency Injection** - Main.java injects dependencies
2. **Interface Segregation** - Separate interfaces for different responsibilities
3. **Single Responsibility** - Each class has one job
4. **Separation of Concerns** - UI, business logic, and data access are separated

---
*This architecture follows industry best practices while remaining student-friendly and easy to understand.*
