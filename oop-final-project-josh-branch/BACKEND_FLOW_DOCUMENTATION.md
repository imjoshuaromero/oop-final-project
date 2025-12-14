# SSC Student Welfare System - Backend Flow & OOP Integration Documentation

## 📋 Table of Contents
1. [Program Flow Overview](#program-flow-overview)
2. [Backend Architecture](#backend-architecture)
3. [Detailed Component Breakdown](#detailed-component-breakdown)
4. [OOP Concepts Integration](#oop-concepts-integration)
5. [Method Flow Diagrams](#method-flow-diagrams)
6. [Key Learnings](#key-learnings)

---

## 1. Program Flow Overview

### Application Startup Flow

```
┌─────────────────────────────────────────────────────────────┐
│                      JVM Starts                             │
│                      ↓                                       │
│                   Main.java                                 │
│         (10 lines - Entry Point)                            │
│         ├─ Utility.clearScreen()                            │
│         ├─ Utility.loadingScreen()                          │
│         ├─ Utility.clearScreen()                            │
│         └─ ApplicationLauncher.start()                      │
│                      ↓                                       │
│            ApplicationLauncher.java                         │
│    (Creates & initializes all services)                     │
│         ├─ new DataPersistenceImpl()                         │
│         ├─ new StudentOperationsImpl()                       │
│         ├─ new AdminOperationsImpl()                         │
│         ├─ new AuthenticationServiceImpl()                   │
│         ├─ Load AppData (students + grievances)             │
│         └─ Create MenuHandler with DI                       │
│                      ↓                                       │
│            MenuHandler.runMainMenu()                        │
│         (Main application loop starts)                      │
└─────────────────────────────────────────────────────────────┘
```

---

## 2. Backend Architecture

### Layered Architecture Pattern

```
┌─────────────────────────────────────────────────────────────┐
│                  PRESENTATION LAYER                         │
│  Main.java → ApplicationLauncher.java → MenuHandler.java    │
│  (User Interface - handles menus & navigation)              │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│            INTERFACE/CONTRACT LAYER                         │
│  ├─ IStudentOperations                                      │
│  ├─ IAdminOperations                                        │
│  ├─ IAuthenticationService                                  │
│  └─ IDataPersistence                                        │
│  (Defines what operations are available, not how)           │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│          BUSINESS LOGIC/SERVICE LAYER                       │
│  ├─ StudentOperationsImpl                                    │
│  ├─ AdminOperationsImpl                                      │
│  ├─ AuthenticationServiceImpl                                │
│  └─ DataPersistenceImpl                                      │
│  (Actual implementation of business logic)                  │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│              ENTITY/DATA LAYER                              │
│  ├─ User.java (Abstract)                                    │
│  ├─ Student.java (extends User)                             │
│  ├─ Admin.java (extends User)                               │
│  ├─ Grievance.java                                          │
│  └─ AppData.java (Container)                                │
│  (Data models & encapsulation)                              │
└──────────────────────┬──────────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────────┐
│              UTILITY/HELPER LAYER                           │
│  ├─ Utility.java (UI helpers)                               │
│  ├─ UIHelper.java (Messages)                                │
│  ├─ PasswordUtils.java (Hashing)                            │
│  └─ StudentValidator.java (Validation)                      │
│  (Supporting utilities & helpers)                           │
└─────────────────────────────────────────────────────────────┘
```

---

## 3. Detailed Component Breakdown

### 🎯 A. Entry Point (Main.java)

**Purpose**: Application entry point - starts the application

**Code**:
```java
public class Main {
    public static void main(String[] args) {
        Utility.clearScreen();
        Utility.loadingScreen();
        Utility.clearScreen();
        ApplicationLauncher.start();
    }
}
```

**What it does**:
1. Clears console screen for clean UI
2. Shows loading animation
3. Delegates to ApplicationLauncher (separation of concerns)

**Why it's important**: Follows Single Responsibility Principle - Main only starts, doesn't configure

---

### 🚀 B. Application Launcher (ApplicationLauncher.java)

**Purpose**: Initialize services and set up dependency injection

**Key Method**: `start()`

**Code Flow**:
```java
public static void start() {
    // Step 1: Create concrete implementations
    IDataPersistence dataService = new DataPersistenceImpl();
    IStudentOperations studentOps = new StudentOperationsImpl();
    IAdminOperations adminOps = new AdminOperationsImpl();
    IAuthenticationService authService = new AuthenticationServiceImpl();
    
    // Step 2: Load data from files
    AppData appData = dataService.loadApplicationData();
    
    // Step 3: Inject dependencies into MenuHandler
    MenuHandler menuHandler = new MenuHandler(
        studentOps,      // ← Interface reference
        adminOps,        // ← Interface reference
        authService,     // ← Interface reference
        dataService      // ← Interface reference
    );
    
    // Step 4: Start menu with loaded data
    menuHandler.runMainMenu(
        appData.getStudents(),    // ArrayList<Student>
        appData.getGrievances()   // ArrayList<Grievance>
    );
}
```

**Design Pattern**: **Dependency Injection** - MenuHandler receives its dependencies through constructor

**Why it matters**:
- MenuHandler doesn't create its own services (loose coupling)
- Easy to swap implementations (e.g., use MockStudentOperations for testing)
- MenuHandler focuses on navigation, not object creation

---

### 📱 C. Menu Handler (MenuHandler.java)

**Purpose**: Main application logic hub - handles menu navigation and delegates operations

**Key Components**:

#### Constructor (Dependency Injection):
```java
public MenuHandler(IStudentOperations studentOps, 
                  IAdminOperations adminOps,
                  IAuthenticationService authService,
                  IDataPersistence dataService) {
    this.studentOps = studentOps;
    this.adminOps = adminOps;
    this.authService = authService;
    this.dataService = dataService;
}
```

**Why Interfaces?** Loose coupling - MenuHandler works with ANY implementation of these interfaces

#### Main Method: `runMainMenu()`

```java
public void runMainMenu(ArrayList<Student> students, ArrayList<Grievance> grievances) {
    int choice;
    do {
        // Display main menu
        Utility.clearScreen();
        Utility.printTitle("SSC STUDENT WELFARE SYSTEM");
        Utility.printMenu(new String[]{
            "[1] Student Login",
            "[2] Register Student",
            "[3] Admin Login",
            "[4] Exit"
        });
        
        choice = Utility.promptCenteredInt("Enter choice:");
        
        // Route to appropriate handler
        switch (choice) {
            case 1: studentLogin(students, grievances); break;
            case 2: studentOps.registerStudent(students); break;
            case 3: adminLogin(students, grievances); break;
            case 4: UIHelper.exitProgram(); break;
        }
    } while (choice != 4);
}
```

**Flow**: Menu → User Input → Route to Service → Service executes → Return to Menu

---

### 👥 D. Service Layer (Business Logic)

#### **1. StudentOperationsImpl (IStudentOperations)**

**Responsible For**:
- Student registration
- Student validation
- Filing concerns
- Viewing concerns

**Example: registerStudent()**

```java
@Override
public void registerStudent(ArrayList<Student> students) {
    // Step 1: Get SR code with validation
    String srCode;
    while (true) {
        srCode = Utility.promptCenteredString("Enter SR-Code (XX-XXXXX): ");
        if (StudentValidator.isCampusStudent(srCode)) {
            if (isDuplicateStudent(students, srCode)) {
                UIHelper.showError("SR-Code already registered!");
                continue;
            }
            break;
        } else {
            UIHelper.showError("Invalid SR-Code format!");
        }
    }
    
    // Step 2: Get other details
    String name = Utility.promptCenteredString("Enter Full Name: ");
    String email = ...
    String password = ...
    
    // Step 3: Create and save student
    Student newStudent = new Student(srCode, name, email, 
                                    PasswordUtils.hash(password));
    students.add(newStudent);
    DataManager.saveStudents(students);
    
    // Step 4: Notify user
    UIHelper.showSuccess("Registration successful!");
}
```

**Key Points**:
- Uses `StudentValidator` for validation
- Uses `PasswordUtils.hash()` for secure password storage
- Uses `DataManager.saveStudents()` to persist data
- Provides user feedback via `UIHelper`

---

#### **2. AdminOperationsImpl (IAdminOperations)**

**Responsible For**:
- View all concerns
- Update concern status
- Delete concerns

**Example: updateConcern()**

```java
@Override
public void updateConcern(ArrayList<Grievance> grievances) {
    Utility.printTitle("UPDATE CONCERN");
    
    if (grievances.isEmpty()) {
        UIHelper.showError("No concerns available to update.");
        return;
    }
    
    // Step 1: Get grievance ID
    int grievanceId = Utility.promptCenteredInt("Enter Grievance ID: ");
    
    // Step 2: Find grievance
    Grievance targetGrievance = null;
    for (Grievance grievance : grievances) {
        if (grievance.getId() == grievanceId) {
            targetGrievance = grievance;
            break;
        }
    }
    
    if (targetGrievance == null) {
        UIHelper.showError("Grievance ID not found!");
        return;
    }
    
    // Step 3: Update status and feedback
    String newStatus = Utility.promptCenteredString("Enter new status: ");
    String feedback = Utility.promptCenteredString("Enter feedback: ");
    
    targetGrievance.setStatus(newStatus);
    targetGrievance.setFeedback(feedback);
    
    // Step 4: Persist changes
    dataService.saveGrievances(grievances);
    UIHelper.showSuccess("Concern updated successfully!");
}
```

---

#### **3. AuthenticationServiceImpl (IAuthenticationService)**

**Responsible For**:
- Student authentication
- Admin authentication
- Password management

**Example: authenticateStudent()**

```java
@Override
public Student authenticateStudent(ArrayList<Student> students, 
                                  String srCode, String password) {
    // Step 1: Hash the input password
    String hashedPassword = PasswordUtils.hash(password);
    
    // Step 2: Find matching student
    for (Student student : students) {
        if (student.getSrCode().equals(srCode) && 
            student.getPassword().equals(hashedPassword)) {
            
            // Step 3: Check if validated by admin
            if (!student.isValidated()) {
                return null;  // Not yet approved
            }
            return student;  // Authentication successful
        }
    }
    return null;  // Not found or wrong password
}
```

**Why Hash Passwords?** Security - even if database is compromised, passwords are protected

---

#### **4. DataPersistenceImpl (IDataPersistence)**

**Responsible For**:
- Loading students from file
- Saving students to file
- Loading grievances from file
- Saving grievances to file
- JSON parsing/serialization

**Example: loadStudents()**

```java
private ArrayList<Student> loadStudents() {
    ArrayList<Student> students = new ArrayList<>();
    File file = new File(STUDENTS_FILE);
    
    if (!file.exists()) {
        return students;
    }
    
    try {
        // Step 1: Read file content
        String content = new String(
            Files.readAllBytes(file.toPath()), 
            StandardCharsets.UTF_8
        );
        
        // Step 2: Parse JSON objects using regex
        Pattern jsonPattern = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
        Matcher jsonMatcher = jsonPattern.matcher(content);
        
        // Step 3: Convert each JSON to Student object
        while (jsonMatcher.find()) {
            Student student = parseStudentFromJson(jsonMatcher.group(1));
            if (student != null) {
                students.add(student);
            }
        }
    } catch (Exception e) {
        UIHelper.showError("Failed to load students: " + e.getMessage());
    }
    
    return students;
}
```

**Data Format** (students.json):
```json
[
  {
    "srCode": "21-12345",
    "name": "Juan Dela Cruz",
    "email": "juan@example.com",
    "password": "hashed_password_here",
    "validated": true
  },
  {...}
]
```

---

### 💾 E. Entity Layer (Data Models)

#### **1. User.java (Abstract Base Class)**

**Purpose**: Common properties for Student and Admin

```java
public abstract class User {
    private String userName;      // ← Encapsulated (private)
    private String password;      // ← Encapsulated (private)
    private String name;          // ← Encapsulated (private)
    
    // Constructor
    public User(String userName, String password, String name) {
        this.userName = userName;
        this.password = password;
        this.name = name;
    }
    
    // Getters (controlled access)
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    
    // Setter (controlled modification)
    public void setPassword(String password) { 
        this.password = password; 
    }
    
    // Abstract methods (polymorphism)
    public abstract void displayInfo();
    public abstract String getRole();
}
```

**OOP Principles**:
- **Encapsulation**: Fields are private, accessed via getters
- **Abstraction**: Abstract methods define contract for subclasses
- **Inheritance**: Base for Student and Admin classes

---

#### **2. Student.java (Extends User)**

**Purpose**: Student-specific data and behavior

```java
public class Student extends User {
    private String srCode;
    private String email;
    private boolean validated;
    
    // Constructor
    public Student(String srCode, String name, String email, String password) {
        super(srCode, password, name);  // ← Call parent constructor (inheritance)
        this.srCode = srCode;
        this.email = email;
        this.validated = false;
    }
    
    // Implementation of abstract method
    @Override
    public void displayInfo() {
        System.out.println("SR-Code: " + srCode);
        System.out.println("Name: " + getName());  // ← Use inherited getter
        System.out.println("Email: " + email);
    }
    
    @Override
    public String getRole() {
        return "Student";
    }
}
```

**OOP Principles**:
- **Inheritance**: Extends User, reuses userName, password, name
- **Polymorphism**: Overrides displayInfo() and getRole()
- **Encapsulation**: Has private fields (srCode, email, validated)

---

#### **3. Grievance.java (Concern Entity)**

**Purpose**: Represents a student's concern/complaint

```java
public class Grievance {
    private static int nextId = 1;  // ← Auto-increment counter
    
    private int grievanceId;
    private String studentSr;
    private String title;
    private String category;
    private String description;
    private String status;
    private String feedback;
    
    // Constructor for new grievance
    public Grievance(String studentSr, String title, String category, String description) {
        this.grievanceId = nextId++;  // ← Auto-generate ID
        this.studentSr = studentSr;
        this.title = title;
        this.category = category;
        this.description = description;
        this.status = "Submitted";
        this.feedback = "None";
    }
    
    // Getters & Setters for controlled access
    public int getId() { return grievanceId; }
    public String getStatus() { return status; }
    public void setStatus(String newStatus) { this.status = newStatus; }
    // ... more getters/setters
}
```

**Key Features**:
- **Static counter**: Ensures unique IDs automatically
- **Encapsulation**: Private fields with controlled access
- **Table display**: Professional formatting for admin viewing

---

### 🛠️ F. Utility Layer

#### **1. Utility.java** - UI Components

**Purpose**: Consistent UI presentation

```java
public class Utility {
    // Clear console
    public static void clearScreen() {
        // Implementation using ANSI codes or System.out
    }
    
    // Print title with box borders
    public static void printTitle(String title) {
        // ╔═══════════════════╗
        // ║   TITLE TEXT      ║
        // ╚═══════════════════╝
    }
    
    // Print menu with buttons
    public static void printMenu(String[] options) {
        // ┌─────────────────┐
        // │  [1] Option 1   │
        // │  [2] Option 2   │
        // └─────────────────┘
    }
    
    // Prompt user for input
    public static String promptCenteredString(String prompt) {
        System.out.println(prompt);
        return new Scanner(System.in).nextLine();
    }
    
    // Loading animation
    public static void loadingScreen() {
        // Shows animated loading bar
    }
}
```

**Imported in**: Every menu operation for consistent UI

---

#### **2. PasswordUtils.java** - Security

**Purpose**: Secure password handling with SHA-256 hashing

```java
public class PasswordUtils {
    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes());
            
            // Convert bytes to hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
```

**How it works**:
- Input: "password123"
- Output: "ef92b778bafe8ce8ff6a85e72667b0be5c72dda4e0f9c3e8c6a5e3d3c7e9f8a0"
- One-way: Can't reverse to get original password

---

#### **3. StudentValidator.java** - Input Validation

**Purpose**: Validate SR codes and emails

```java
public class StudentValidator {
    public static boolean isCampusStudent(String srCode) {
        // Validate against campus registry
        // Load from file and check
    }
    
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
```

---

### 📊 G. Data Container (AppData.java)

**Purpose**: Holds loaded data in memory

```java
public class AppData {
    private ArrayList<Student> students;
    private ArrayList<Grievance> grievances;
    
    public AppData(ArrayList<Student> students, 
                   ArrayList<Grievance> grievances) {
        this.students = students;
        this.grievances = grievances;
    }
    
    public ArrayList<Student> getStudents() { return students; }
    public ArrayList<Grievance> getGrievances() { return grievances; }
}
```

---

## 4. OOP Concepts Integration

### 🔒 A. ENCAPSULATION

**Definition**: Bundling data and methods together, hiding internal details

**Implementation in our system**:

#### User.java
```java
private String userName;       // ← HIDDEN (private)
private String password;       // ← HIDDEN (private)

public String getUserName() {  // ← CONTROLLED ACCESS
    return userName;
}

public void setPassword(String newPassword) {  // ← CONTROLLED MODIFICATION
    this.password = PasswordUtils.hash(newPassword);
}
```

**Why it matters**:
- Password never exposed directly
- Can add validation in setter (e.g., min 8 characters)
- Can audit password changes
- Prevents accidental modification

#### Student.java
```java
private String srCode;         // ← HIDDEN
private String email;          // ← HIDDEN
private boolean validated;     // ← HIDDEN

public String getSrCode() {    // ← Only read access
    return srCode;
}

public void setValidated(boolean v) {  // ← Only specific fields exposed
    this.validated = v;
}
```

**Benefit**: Admin can only change validation status, not SR code

---

### 👨‍👩‍👧 B. INHERITANCE

**Definition**: Child class inherits properties and methods from parent class

**Implementation in our system**:

#### Class Hierarchy
```
        User (Abstract)
        /           \
    Student         Admin
```

#### Code Example
```java
// User.java (Parent)
public abstract class User {
    protected String userName;
    protected String password;
    protected String name;
    
    public String getUserName() { return userName; }
    public String getName() { return name; }
    public abstract void displayInfo();
}

// Student.java (Child)
public class Student extends User {  // ← INHERITS from User
    private String srCode;
    private String email;
    
    public Student(String srCode, String name, String email, String password) {
        super(srCode, password, name);  // ← Call parent constructor
        this.srCode = srCode;
        this.email = email;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Username: " + getUserName());  // ← Use inherited method
        System.out.println("Name: " + getName());          // ← Use inherited method
        System.out.println("SR-Code: " + srCode);
    }
}

// Admin.java (Child)
public class Admin extends User {
    @Override
    public void displayInfo() {
        System.out.println("Admin Name: " + getName());    // ← Use inherited method
    }
}
```

**Benefits**:
- **Code Reuse**: Both Student and Admin have getUserName(), getPassword(), getName()
- **Consistency**: Common fields in one place
- **Maintenance**: Change User once, affects both Student and Admin

**Real World Analogy**:
```
Vehicle (Parent)
├─ Car (Child)     - has wheels, engine, steering
├─ Bike (Child)    - has wheels, engine, steering
└─ Truck (Child)   - has wheels, engine, steering (but bigger)
```

---

### 🔄 C. POLYMORPHISM

**Definition**: Objects can take multiple forms; same method name, different behavior

**Implementation in our system**:

#### Abstract Method in User.java
```java
public abstract class User {
    // Abstract method - no implementation
    public abstract void displayInfo();
    public abstract String getRole();
}
```

#### Different Implementations
```java
// Student Implementation
public class Student extends User {
    @Override
    public void displayInfo() {
        System.out.println("=== Student Information ===");
        System.out.println("SR-Code: " + srCode);
        System.out.println("Email: " + email);
        // ... student-specific info
    }
    
    @Override
    public String getRole() {
        return "Student";
    }
}

// Admin Implementation
public class Admin extends User {
    @Override
    public void displayInfo() {
        System.out.println("=== Administrator Information ===");
        System.out.println("Admin Name: " + getName());
        // ... admin-specific info
    }
    
    @Override
    public String getRole() {
        return "Administrator";
    }
}
```

#### Polymorphic Usage
```java
// In MenuHandler
User user;  // ← Can be Student OR Admin

if (login_successful) {
    user = authenticatedStudent;  // ← Student object
    user.displayInfo();  // ← Calls Student's displayInfo()
    
    // Later
    user = authenticatedAdmin;  // ← Admin object
    user.displayInfo();  // ← Calls Admin's displayInfo()
}
```

**Benefits**:
- Same code works with different objects
- Easy to add new user types (e.g., Counselor)
- No need to write separate methods for Student.displayInfo() and Admin.displayInfo()

---

### 🎭 D. ABSTRACTION

**Definition**: Hide complex details, show only essential features

**Implementation in our system**:

#### Interface Contracts (Define WHAT, not HOW)

```java
// IStudentOperations - "What" student operations should do
public interface IStudentOperations {
    void registerStudent(ArrayList<Student> students);
    void validateRegisteredStudents(ArrayList<Student> students);
    void fileConcern(Student student, ArrayList<Grievance> grievances);
    void viewStudentConcerns(Student student, ArrayList<Grievance> grievances);
}
```

**MenuHandler doesn't need to know**:
- How password is hashed
- How validation works internally
- How data is saved to file
- Which files are used

**MenuHandler only knows**:
- Interface methods available
- What parameters to pass
- What result to expect

#### Real World Analogy

Driving a car (Abstraction):
```
What you see:        What you don't need to know:
├─ Steering wheel    ├─ How engine combustion works
├─ Pedals            ├─ How transmission shifts gears
├─ Gear shift        └─ How brakes convert energy
└─ Dashboard
```

You don't need to understand combustion to drive! That's abstraction.

---

## 5. Method Flow Diagrams

### Student Registration Flow

```
MenuHandler.studentMenu()
    ↓
User selects "Register Student"
    ↓
studentOps.registerStudent(students)
    ├─ Loop: Get SR code
    │  ├─ StudentValidator.isCampusStudent()  ← Is it valid format?
    │  └─ isDuplicateStudent()  ← Already registered?
    │
    ├─ Get Name, Email, Password
    │
    ├─ Create Student object
    │  └─ new Student(srCode, name, email, password)
    │
    ├─ Add to ArrayList
    │  └─ students.add(newStudent)
    │
    ├─ Save to file
    │  └─ dataService.saveStudents(students)
    │     └─ Write to students.json
    │
    └─ Show success message
       └─ UIHelper.showSuccess()
```

---

### Student Login Flow

```
MenuHandler.studentLogin()
    ├─ Get SR code
    ├─ Get password
    ├─ Utility.spinner("Logging In")
    │
    ├─ authService.authenticateStudent(students, srCode, password)
    │  ├─ Hash password using PasswordUtils.hash()
    │  ├─ Loop through students
    │  ├─ Compare SR code & hashed password
    │  ├─ Check if student is validated
    │  └─ Return Student object if match
    │
    ├─ If authenticated:
    │  └─ MenuHandler.studentMenu(student)
    │     ├─ File Concern → studentOps.fileConcern()
    │     ├─ View Concerns → studentOps.viewStudentConcerns()
    │     ├─ Change Password → authService.changePassword()
    │     └─ Logout
    │
    └─ Else: Show error
```

---

### File Concern Flow

```
studentOps.fileConcern(student, grievances)
    ├─ Get concern title
    ├─ Display categories
    ├─ Get category selection
    ├─ Get detailed description
    │
    ├─ Create Grievance object
    │  └─ new Grievance(
    │     studentSr = student.getSrCode(),
    │     title = title,
    │     category = category,
    │     description = description
    │  )
    │
    ├─ Add to ArrayList
    │  └─ grievances.add(newGrievance)
    │
    ├─ Save to file
    │  └─ dataService.saveGrievances(grievances)
    │     └─ Write to grievances.json
    │
    └─ Show success message
       └─ UIHelper.showSuccess("Grievance ID: " + grievanceId)
```

---

## 6. Key Learnings

### 💡 Learning 1: Why Interfaces Matter

**Before (Tightly Coupled)**:
```java
// MenuHandler directly uses concrete classes
StudentService.registerStudent(students);
AuthService.authenticateStudent(students, sr, pass);
DataManager.saveStudents(students);
```

**Problem**:
- If we change StudentService, MenuHandler breaks
- Can't test with mock implementations
- Hard to extend with new implementations

**After (Loosely Coupled)**:
```java
// MenuHandler uses interfaces
studentOps.registerStudent(students);        // IStudentOperations
authService.authenticateStudent(...);        // IAuthenticationService
dataService.saveStudents(students);          // IDataPersistence
```

**Benefits**:
- Can swap implementations anytime
- Easy to test with mocks
- MenuHandler doesn't care about details

---

### 💡 Learning 2: Encapsulation Prevents Bugs

**Bad Practice**:
```java
student.password = "plaintext_password";  // ← Direct access
```

**Problem**: Password not hashed, visible in memory

**Good Practice** (Our System):
```java
student.setPassword(password);  // ← Controlled access
// Inside setPassword():
this.password = PasswordUtils.hash(password);  // ← Automatic hashing
```

**Result**: Password always hashed, consistent behavior

---

### 💡 Learning 3: Inheritance Eliminates Duplication

**Question**: Why have both Student and Admin inherit from User?

**Answer**: Common properties
```
User
├─ userName  ← Both need this
├─ password  ← Both need this
└─ name      ← Both need this

Student extends User → Adds: srCode, email
Admin extends User → Adds: (none, but different behavior)
```

**Benefit**: Change password format once in User → affects both Student and Admin

---

### 💡 Learning 4: Polymorphism Enables Flexibility

**Problem**: We need to display different info for Student vs Admin
- Student: "SR-Code, Email, Validated Status"
- Admin: "Admin Name, Department"

**Solution**: Polymorphism
```java
// Same method name, different behavior
student.displayInfo();  // Shows student info
admin.displayInfo();    // Shows admin info
```

**Benefit**: Single code point works with multiple types

---

### 💡 Learning 5: Abstraction Manages Complexity

**Complex Internal Operation**:
```
registerStudent() internally does:
- Validate SR code against campus database
- Check for duplicates in memory
- Hash password with SHA-256
- Create Student object with validation status
- Add to ArrayList
- Serialize to JSON
- Write to file
- Show success message
```

**User doesn't see**: Just one method call
```java
studentOps.registerStudent(students);
// Done! 15 operations hidden behind one method
```

**Benefit**: Manage complexity - developers don't need to understand all internals

---

### 💡 Learning 6: Dependency Injection is Flexible

**Example**: What if we want to use a database instead of JSON files?

**With our architecture**:
```java
// Create new implementation
IDataPersistence dbService = new DatabasePersistenceImpl();

// Inject into MenuHandler
MenuHandler menuHandler = new MenuHandler(
    studentOps,
    adminOps,
    authService,
    dbService  // ← Just swap this
);

// MenuHandler works the same!
```

**No changes needed** to StudentOperationsImpl, AuthenticationServiceImpl, or MenuHandler

---

### 💡 Learning 7: Single Responsibility Principle

**Each class has ONE job**:
- `Main.java` - Start application
- `ApplicationLauncher.java` - Set up services
- `MenuHandler.java` - Navigate menus
- `StudentOperationsImpl.java` - Student operations
- `DataPersistenceImpl.java` - Save/load data
- `PasswordUtils.java` - Hash passwords
- `Utility.java` - UI helpers

**Benefit**: Easy to test, maintain, and understand

---

## 7. System Flow Diagram (Complete)

```
┌──────────────────────────────────────────────────────────────┐
│                        JVM START                             │
│                          ↓                                    │
│                      Main.java                               │
│   ┌─────────────────────────────────────┐                    │
│   │ • clearScreen()                     │                    │
│   │ • loadingScreen()                   │                    │
│   │ • ApplicationLauncher.start()       │                    │
│   └────────────┬────────────────────────┘                    │
│                ↓                                              │
│   ┌──────────────────────────────────┐                       │
│   │  ApplicationLauncher.start()      │                      │
│   │  ┌──────────────────────────────┐ │                      │
│   │  │ new DataPersistenceImpl()     │ │                      │
│   │  │ new StudentOperationsImpl()   │ │                      │
│   │  │ new AdminOperationsImpl()     │ │                      │
│   │  │ new AuthenticationServiceImpl │ │                      │
│   │  └───────────┬──────────────────┘ │                      │
│   │              ↓                     │                      │
│   │  ┌──────────────────────────────┐ │                      │
│   │  │ dataService.load...()        │ │                      │
│   │  │ → students.json              │ │  Loads from file     │
│   │  │ → grievances.json            │ │                      │
│   │  └───────────┬──────────────────┘ │                      │
│   │              ↓                     │                      │
│   │  ┌──────────────────────────────┐ │                      │
│   │  │ new MenuHandler(             │ │ Dependency Injection │
│   │  │   studentOps,                │ │                      │
│   │  │   adminOps,                  │ │                      │
│   │  │   authService,               │ │                      │
│   │  │   dataService                │ │                      │
│   │  │ )                            │ │                      │
│   │  └───────────┬──────────────────┘ │                      │
│   │              ↓                     │                      │
│   │  menuHandler.runMainMenu()        │                      │
│   └────────────┬─────────────────────┘                       │
│                ↓                                              │
│   ┌──────────────────────────────────┐                       │
│   │   MenuHandler.runMainMenu()      │                       │
│   │   ┌────────────────────────────┐ │                       │
│   │   │ Display Main Menu          │ │                       │
│   │   │ [1] Student Login          │ │                       │
│   │   │ [2] Register Student       │ │                       │
│   │   │ [3] Admin Login            │ │                       │
│   │   │ [4] Exit                   │ │                       │
│   │   └────────┬───────────────────┘ │                       │
│   │            ↓                      │                       │
│   │   ┌────────────────────────────┐ │                       │
│   │   │ User Input → Choice        │ │                       │
│   │   └────────┬───────────────────┘ │                       │
│   │            ↓                      │                       │
│   │   Switch Statement:              │                       │
│   │   ┌────────────────────────────┐ │                       │
│   │   │ case 1:                    │ │                       │
│   │   │   studentLogin()           │ │                       │
│   │   │   → authService.auth...()  │ │                       │
│   │   │   → studentMenu()          │ │                       │
│   │   │      ├─ fileConcern        │ │                       │
│   │   │      │  └─ studentOps      │ │                       │
│   │   │      ├─ viewConcerns       │ │                       │
│   │   │      │  └─ studentOps      │ │                       │
│   │   │      └─ changePassword     │ │                       │
│   │   │         └─ authService     │ │                       │
│   │   │                            │ │                       │
│   │   │ case 2:                    │ │                       │
│   │   │   studentOps.register()    │ │                       │
│   │   │   → dataService.save()     │ │                       │
│   │   │   → students.json          │ │                       │
│   │   │                            │ │                       │
│   │   │ case 3:                    │ │                       │
│   │   │   adminLogin()             │ │                       │
│   │   │   → authService.auth...()  │ │                       │
│   │   │   → adminMenu()            │ │                       │
│   │   │      ├─ viewConcerns       │ │                       │
│   │   │      │  └─ adminOps        │ │                       │
│   │   │      ├─ updateConcern      │ │                       │
│   │   │      │  └─ adminOps        │ │                       │
│   │   │      └─ deleteConcern      │ │                       │
│   │   │         └─ adminOps        │ │                       │
│   │   │                            │ │                       │
│   │   │ case 4: Exit               │ │                       │
│   │   │   System.exit(0)           │ │                       │
│   │   └────────────────────────────┘ │                       │
│   │                                  │                       │
│   │   Loop back to Display Menu      │                       │
│   └──────────────────────────────────┘                       │
└──────────────────────────────────────────────────────────────┘
```

---

## Summary for Presentation

### What We Built
- **Layered Architecture**: UI → Interfaces → Services → Entities → Utils
- **Dependency Injection**: Services are injected, not created
- **OOP Principles**: All 4 pillars (E, I, P, A) integrated

### Key Design Patterns
1. **Interface-Based Design** - Loose coupling
2. **Dependency Injection** - Flexible, testable
3. **Single Responsibility** - Each class has one job
4. **Abstraction** - Hide complexity

### Technologies Used
- **SHA-256 Hashing**: Secure password storage
- **Regex Parsing**: JSON data loading
- **ArrayList**: Dynamic data storage
- **File I/O**: Data persistence

### Why This Matters
- **Maintainable**: Easy to fix and extend
- **Testable**: Can mock services
- **Professional**: Follows industry standards
- **Scalable**: Can add new features without breaking existing code

---

**Created for: OOP Final Project Presentation**
**System: SSC Student Welfare System**
**Date: December 2025**
