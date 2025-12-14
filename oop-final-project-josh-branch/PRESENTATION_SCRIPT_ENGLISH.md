# SSC STUDENT WELFARE SYSTEM - PRESENTATION SCRIPT (ENGLISH)

## COMPREHENSIVE PRESENTATION GUIDE
**Group Members:** Joshua Romero (Lead), Zyrus Liao, Lalaine Anne Virtucio

---

## PART 1: INTRODUCTION & WELCOME
### [JOSHUA ROMERO - Lead Speaker]

Good morning/afternoon, everyone. My name is Joshua Romero, and I'm presenting our OOP Final Project today.

We are a group of three developers who worked together to create a meaningful system that addresses real-world problems in our academic community. This project represents the culmination of our learning in Object-Oriented Programming, and we are excited to share our development journey with you.

Our team consists of:
- **Joshua Romero** - Project lead and main core system developer
- **Zyrus Liao** - System architecture and user interface design specialist
- **Lalaine Anne Virtucio** - Database management and data persistence expert

Before we dive into the technical details, we would like to introduce our system and explain how it aligns with the United Nations' Sustainable Development Goals.

---

## PART 2: SYSTEM TITLE & SDG ALIGNMENT
### [JOSHUA ROMERO]

**SYSTEM TITLE: SSC Student Welfare System**

Our system is specifically designed for the **Student Satisfaction Committee (SSC)**, with the goal of providing a structured and efficient platform for students to submit their concerns, feedback, and grievances.

### **Sustainable Development Goals (SDGs) Alignment:**

Our system is aligned with three important UN Sustainable Development Goals:

#### **1. SDG 4: QUALITY EDUCATION**
- The system ensures transparent communication between students and the administration
- Provides an accessible platform for all students to voice their concerns
- Promotes continuous improvement of educational services based on student feedback
- **Relevance:** Quality education extends beyond academics to encompass the overall student experience and welfare

#### **2. SDG 10: REDUCED INEQUALITIES**
- All students have equal access to the system regardless of their background
- No discrimination in filing concerns - all students are treated equally
- Transparent process where the status of concerns is visible to all
- **Relevance:** The system ensures that every student has a voice and a mechanism to raise issues

#### **3. SDG 17: PARTNERSHIPS FOR THE GOALS**
- Creates partnerships between students and the administration
- Facilitates collaboration for resolving student concerns
- Promotes mutual understanding through structured communication
- **Relevance:** Collaborative approaches are essential for achieving all sustainable development goals

Now, let's see how the system works in practice.

---

## PART 3: SYSTEM DEMONSTRATION
### [JOSHUA ROMERO]

*[Turn on the application and show the main menu]*

Here is our SSC Student Welfare System main interface.

### **Key Features Overview:**

#### **A. USER REGISTRATION & AUTHENTICATION**

1. **Student Registration**
   - New students can register using their SR Code
   - System automatically validates against campus registry
   - Passwords are hashed for security
   - Accounts remain pending until admin validation

2. **Student Login**
   - SR Code and password authentication
   - System verifies account status
   - Dashboard access only granted to validated accounts

3. **Admin Login**
   - Separate login for administrative users
   - Full access to system management features

#### **B. STUDENT FUNCTIONALITIES**

1. **File Concerns**
   - Students can submit grievances through six categories:
     * Academic (grades, courses, teachers)
     * Harassment (bullying, discrimination)
     * Facilities (classroom resources, maintenance)
     * Financial Aid (scholarships, fee-related)
     * Health & Wellness (mental health, counseling)
     * Others (miscellaneous concerns)
   - Automatic grievance ID generation for tracking
   - Real-time status tracking

2. **View My Concerns**
   - Students can view all filed concerns
   - Pagination system for better organization (5 per page)
   - Detailed information including status and feedback
   - Can view admin responses anytime

3. **Change Password**
   - Secure password change mechanism
   - Current password verification required
   - Confirmation validation to prevent typos

#### **C. ADMIN FUNCTIONALITIES**

1. **View All Concerns**
   - Comprehensive list of all filed grievances
   - Paginated view for easy navigation
   - Status and category filtering capability
   - Full details of each concern

2. **Update Concern Status**
   - Change status progression: Submitted → Under Review → Resolved
   - Add feedback and response to student
   - Track resolution timeline
   - Provide closure for student concerns

3. **Validate Registered Students**
   - Review pending registrations
   - Accept or reject new student accounts
   - Ensure only legitimate users access the system

4. **Reset Student Password**
   - Emergency password reset capability
   - Temporary password generation
   - Admin assistance for locked accounts

5. **Reload SR Registry**
   - Update campus student database
   - Ensure latest enrollment data
   - Synchronize with student list for validation

*[Demonstrate one complete student workflow: Register → Login → File Concern → View Concern]*

*[Show admin workflow: View concerns → Update status with feedback]*

This is the high-level overview of our system. Now, let's dive deeper into the technical implementation and how we applied OOP principles.

---

## PART 4: OOP INTEGRATION DISCUSSION
### [Each Member Discusses Their Part]

---

## PART 4A: ENCAPSULATION & ARCHITECTURE
### [ZYRUS LIAO - System Architecture & Encapsulation Expert]

Good morning, everyone. I'm Zyrus Liao, and I led the architectural design of our system.

### **What is Encapsulation?**

Encapsulation is one of the core principles of object-oriented programming. It's the practice of bundling data and methods within a class and using private fields to protect that data. The core idea is: **"Hide internal details, expose only what's necessary."**

Think of it like a car:
- The engine internals are **private** - customers shouldn't need to access them
- The steering wheel and pedals are **public** - this is all the driver needs
- There is no direct access to the engine - it's protected through safe interfaces

### **How We Applied Encapsulation in Our System:**

#### **1. User Class (Base Class for Student & Admin)**

```java
public abstract class User {
    // Private fields - PROTECTED DATA
    private String userName;      // ← ENCAPSULATED
    private String password;      // ← ENCAPSULATED (never exposed directly)
    private String name;          // ← ENCAPSULATED
    
    // Public getters - CONTROLLED ACCESS
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    
    // Public setter with validation - CONTROLLED MODIFICATION
    public void setPassword(String newPassword) {
        this.password = newPassword;  // Could add validation here
    }
}
```

**Why this matters:**
- Passwords are **never exposed** - only getters, and only hashed values
- If we need to add validation (e.g., password requirements), we update it in ONE place
- Prevents accidental or malicious direct modification

#### **2. Student Class (Extends User)**

```java
public class Student extends User {
    private String srCode;        // ← ENCAPSULATED (SR Code)
    private String email;         // ← ENCAPSULATED
    private boolean validated;    // ← ENCAPSULATED (account status)
    
    // Getter for SR Code - accessible but not modifiable
    public String getSrCode() { return srCode; }
    
    // Getter for validation status
    public boolean isValidated() { return validated; }
    
    // Setter only for admin - controls who can validate
    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}
```

**Real-world benefit:**
- Students cannot change their own SR Code (immutable)
- Validation status is controlled exclusively by admin
- Email is protected - not visible to unauthorized users

#### **3. Grievance Class (Data Protection)**

```java
public class Grievance {
    private static int nextId = 1;     // ← ENCAPSULATED counter
    private int grievanceId;           // ← ENCAPSULATED
    private String status;             // ← ENCAPSULATED
    private String feedback;           // ← ENCAPSULATED
    
    // Getters allow reading but not modification
    public int getId() { return grievanceId; }
    public String getStatus() { return status; }
    
    // Setters control HOW data changes
    public void setStatus(String newStatus) {
        // Could add validation: only certain status values allowed
        this.status = newStatus;
    }
}
```

**Benefits:**
- Grievance ID is auto-generated and protected - no one can manually change it
- Status is controlled - only valid statuses are allowed
- Feedback is protected from unauthorized modification

### **Why Encapsulation is Critical:**

1. **Security:** Passwords and sensitive information are protected
2. **Maintainability:** Change implementation details without breaking external code
3. **Validation:** Control how data is modified and validated
4. **Debugging:** Know exactly where sensitive fields are modified

**Real example from our system:**
- If admin tries to set an invalid status, the system remains unchanged if validation is in place
- If password requirements change, we update ONE location
- If someone tries to hack the SR Code, it's impossible because it's private and immutable

This is the power of encapsulation - **security, control, and flexibility for future changes.**

---

## PART 4B: INHERITANCE & CODE REUSE
### [LALAINE ANNE VIRTUCIO - Inheritance & Database Structure Expert]

Hello everyone, I'm Lalaine Anne Virtucio. I handled the database design and data persistence, and I'd like to explain how we applied **Inheritance** in our system.

### **What is Inheritance?**

Inheritance is the concept where one class **acquires properties and methods from a parent class**. Instead of repeating code, we share common functionality. 

Think of it like a family hierarchy:
- Grandparents have general traits (height, eye color, etc.)
- Parents inherit those traits and add their own
- Children inherit from parents
- **We reuse and specialize** - no duplication

### **How We Applied Inheritance in Our System:**

#### **1. User → Student / Admin Hierarchy**

```
┌─────────────────┐
│      User       │  (Abstract Parent)
│   (ABSTRACT)    │
├─────────────────┤
│ - userName      │
│ - password      │
│ - name          │
│                 │
│ + getPassword() │
│ + getName()     │
└────────┬────────┘
         │
    ┌────┴────┐
    │          │
┌───▼───────┐  ┌──────────────┐
│  Student  │  │     Admin    │
├───────────┤  ├──────────────┤
│-srCode    │  │(no extras)   │
│-email     │  │              │
│-validated │  │              │
└───────────┘  └──────────────┘
```

**Code structure:**

```java
// PARENT CLASS - Common attributes
public abstract class User {
    private String userName;
    private String password;
    private String name;
    
    // Shared methods
    public String getName() { return name; }
    public void setPassword(String pass) { this.password = pass; }
}

// CHILD CLASS - Student-specific
public class Student extends User {
    private String srCode;      // UNIQUE to Student
    private String email;       // UNIQUE to Student
    private boolean validated;  // UNIQUE to Student
    
    public Student(String srCode, String name, String email, String password) {
        super(srCode, password, name);  // ← Call parent constructor
        this.srCode = srCode;
        this.email = email;
    }
}

// CHILD CLASS - Admin (minimal extensions)
public class Admin extends User {
    // Inherits everything from User
    // No need to rewrite password, name, etc.
}
```

### **Benefits of Inheritance:**

#### **1. Code Reuse**
- If the User class has 100 lines of common methods
- Student and Admin don't need to repeat those 100 lines
- Instead, `extends User` gives them all that functionality

#### **2. Single Point of Maintenance**
- If we need to change password validation
- Change it in the User class - automatically affects Student and Admin

**Example:** If we want to require capital letters in passwords:
```java
// User.java - ONE PLACE TO UPDATE
public void setPassword(String newPassword) {
    if (!newPassword.matches(".*[A-Z].*")) {
        throw new IllegalArgumentException("Password must have uppercase");
    }
    this.password = newPassword;
}

// AUTOMATICALLY APPLIED to both Student and Admin!
```

#### **3. Polymorphism Ready**
- We can treat both Student and Admin as User types
- Code becomes more flexible and can handle both types

### **Real-World Database Implication:**

In our data persistence layer:
```java
// One method handles BOTH Student and Admin
public void saveUsers(ArrayList<User> users) {  // ← Accepts both!
    for (User user : users) {  // ← Could be Student or Admin
        saveUser(user);
    }
}
```

Without inheritance, we'd need separate methods:
```java
// WITHOUT INHERITANCE - CODE DUPLICATION
public void saveStudents(ArrayList<Student> students) { ... }
public void saveAdmins(ArrayList<Admin> admins) { ... }
// And they'd have nearly identical code!
```

### **How Inheritance Saved Us Development Time:**

- **Without inheritance:** ~300 lines of duplicated code
- **With inheritance:** ~100 lines shared + ~50 lines unique per class
- **Savings:** ~150 lines of code = faster development, fewer bugs

**Real statistics from our project:**
- User class: ~80 lines
- Student class: ~60 lines (extends User)
- Admin class: ~30 lines (extends User)
- **Total: 170 lines** ← Without inheritance, could be 300+ lines

This is the practical power of inheritance - **code reuse, maintainability, and reduced duplication.**

---

## PART 4C: POLYMORPHISM & FLEXIBLE DESIGN
### [JOSHUA ROMERO - Business Logic & Polymorphism]

Now let me discuss **Polymorphism** - one of the most powerful features of OOP.

### **What is Polymorphism?**

Polymorphism literally means **"many forms."** It's when the same method has different behavior depending on the object type.

Real-world analogy:
- `displayInfo()` on a Student shows SR Code and grades
- `displayInfo()` on an Admin shows admin privileges and access level
- Same method name, DIFFERENT behavior based on who calls it

### **How We Applied Polymorphism in Our System:**

#### **1. Polymorphic displayInfo() Method**

```java
// Parent class defines method
public abstract class User {
    public abstract void displayInfo();  // ← Abstract, no implementation
}

// Student implements THEIR version
public class Student extends User {
    @Override
    public void displayInfo() {
        System.out.println("SR-Code: " + srCode);
        System.out.println("Name: " + getName());
        System.out.println("Email: " + email);
        System.out.println("Status: " + (validated ? "Validated" : "Pending"));
    }
}

// Admin implements THEIR version
public class Admin extends User {
    @Override
    public void displayInfo() {
        System.out.println("Admin: " + getName());
        System.out.println("Username: " + getUserName());
        System.out.println("Role: Administrator");
        System.out.println("Access: Full System");
    }
}

// Usage - SAME CODE, DIFFERENT BEHAVIOR
public void displayUserInfo(User user) {  // ← Accepts any User type
    user.displayInfo();  // ← Automatically calls correct version!
}

// Call with Student
Student student = new Student(...);
displayUserInfo(student);  // → Shows student info format

// Call with Admin
Admin admin = new Admin(...);
displayUserInfo(admin);  // → Shows admin info format
```

**The magic:** Same method name, completely different outputs based on object type!

#### **2. Polymorphic Service Interfaces**

The best example of polymorphism is our service interfaces:

```java
// Interface defines contract
public interface IStudentOperations {
    void registerStudent(ArrayList<Student> students);
    void fileConcern(Student student, ArrayList<Grievance> grievances);
    void viewStudentConcerns(Student student, ArrayList<Grievance> grievances);
}

// Implementation provides actual behavior
public class StudentOperationsImpl implements IStudentOperations {
    @Override
    public void registerStudent(ArrayList<Student> students) {
        // Actual registration logic
    }
    
    @Override
    public void fileConcern(Student student, ArrayList<Grievance> grievances) {
        // Actual concern filing logic
    }
}

// Usage through interface
public class MenuHandler {
    private IStudentOperations studentOps;  // ← Reference to interface
    
    public MenuHandler(IStudentOperations ops) {
        this.studentOps = ops;  // ← Could be StudentOperationsImpl or ANY impl
    }
    
    public void handleStudentMenu() {
        studentOps.registerStudent(students);  // ← Actual behavior injected
    }
}
```

**Why this matters:**
- MenuHandler doesn't need to know WHO implements IStudentOperations
- We can change the implementation without modifying MenuHandler
- We can create mock implementations for testing

#### **3. Practical Polymorphism in Our Pagination**

```java
// Same pagination logic, different data
public void viewConcerns(ArrayList<Grievance> concerns, int pageSize) {
    int totalPages = (int) Math.ceil((double) concerns.size() / pageSize);
    
    for (int page = 0; page < totalPages; page++) {
        // Display page of concerns
    }
}

// Called with all concerns (Admin)
ArrayList<Grievance> allConcerns = grievances;
viewConcerns(allConcerns, 5);  // Shows all, paginated

// Called with student's concerns (Student)
ArrayList<Grievance> myConcerns = filterByStudent(grievances, student);
viewConcerns(myConcerns, 5);  // Shows only student's, same pagination logic
```

### **Benefits of Polymorphism in Our System:**

1. **Flexibility:** Service implementation can change anytime
2. **Testing:** We can create fake services for testing
3. **Extensibility:** We can add new implementations without touching existing code
4. **Loose Coupling:** Components don't have tight dependencies on specific implementations

**Real scenario:** If tomorrow we need to integrate an external API for student registry:
```java
// Create new implementation
public class ExternalAPIStudentValidator implements IStudentValidator {
    @Override
    public boolean isCampusStudent(String srCode) {
        // Call external API
    }
}

// Use it - NO other code changes needed!
StudentValidator validator = new ExternalAPIStudentValidator();
```

This is the beauty of polymorphism - **flexibility and extensibility without code modification.**

---

## PART 4D: ABSTRACTION & INTERFACE-BASED DESIGN
### [ZYRUS LIAO - Abstraction & Clean Architecture]

Finally, let me explain **Abstraction** - which is actually integrated throughout our system.

### **What is Abstraction?**

Abstraction is about **"hiding complexity, showing only what's necessary."** It's like using a TV remote:
- You press "power" without knowing how electricity flows
- You don't need to understand circuit boards
- The interface is simple, complexity is hidden inside

### **How We Applied Abstraction in Our System:**

#### **1. Abstract User Class**

```java
public abstract class User {
    // Abstract methods - CONTRACT, not implementation
    public abstract void displayInfo();
    public abstract String getRole();
    
    // Concrete methods - Implementation details hidden
    public void changePassword(String oldPass, String newPass) {
        if (validatePassword(oldPass)) {
            this.password = hashPassword(newPass);
        }
    }
    
    // Private helper - ABSTRACTED AWAY
    private boolean validatePassword(String pass) {
        // Complex validation logic
    }
    
    private String hashPassword(String pass) {
        // SHA-256 hashing complexity hidden
    }
}
```

**What's abstracted:**
- Password hashing algorithm - callers don't need to know it's SHA-256
- Validation logic - internal implementation hidden
- Only public interface shows: `changePassword(oldPass, newPass)`

#### **2. Service Interfaces - Abstraction Layer**

```
┌────────────────────────────────────┐
│        MenuHandler                 │  ← Presentation Layer
│   (uses interfaces, not impl)      │
└────────────┬───────────────────────┘
             │
    ┌────────┴────────┐
    │                 │
┌───▼──────────────┐ ┌──────────────────┐
│ IStudentOps      │ │ IAdminOps        │  ← Interface Layer
│ (Contract)       │ │ (Contract)       │
└───────────────────┴──────────────────┘
    │                 │
┌───▼──────────┐ ┌───▼─────────────┐
│ StudentOpsImpl│ │ AdminOpsImpl     │  ← Implementation Layer
│ (Details)    │ │ (Details)       │
└──────────────┴─────────────────────┘
```

**Code example:**

```java
// ABSTRACTION - Interface hides implementation
public interface IStudentOperations {
    void registerStudent(ArrayList<Student> students);
    void fileConcern(Student student, ArrayList<Grievance> grievances);
}

// MenuHandler doesn't know HOW registration works
public class MenuHandler {
    private IStudentOperations studentOps;
    
    public void runStudentMenu() {
        studentOps.registerStudent(students);  // ← Just call method
        // Don't care if it uses database, files, or API
    }
}

// Implementation - all complexity hidden here
public class StudentOperationsImpl implements IStudentOperations {
    @Override
    public void registerStudent(ArrayList<Student> students) {
        // 50 lines of validation logic
        // Email validation
        // SR Code verification
        // Password hashing
        // File I/O operations
        // All HIDDEN from MenuHandler
    }
}
```

#### **3. Data Persistence Abstraction**

```java
// MenuHandler thinks it's simple
public interface IDataPersistence {
    AppData loadApplicationData();
    void saveStudents(ArrayList<Student> students);
    void saveGrievances(ArrayList<Grievance> grievances);
}

// But implementation is complex
public class DataPersistenceImpl implements IDataPersistence {
    @Override
    public void saveStudents(ArrayList<Student> students) {
        // Complex JSON formatting
        // File I/O handling
        // Error handling
        // Character encoding
        // All abstracted away
    }
}
```

**Real scenario:** If tomorrow we need to use a database instead of files:
```java
// Create new implementation
public class DatabasePersistence implements IDataPersistence {
    @Override
    public void saveStudents(ArrayList<Student> students) {
        // SQL queries, connection pooling, transactions
        // All abstracted from MenuHandler
    }
}

// MenuHandler has no idea the implementation changed!
```

### **Why Abstraction is Critical:**

1. **Simplicity:** Callers use simple interface, complexity hidden
2. **Maintainability:** Change implementation without affecting callers
3. **Testing:** Easy to create mock implementations for testing
4. **Professional:** Clear separation of concerns

This is the complete picture - **abstraction enables flexibility and professional code.**

---

## PART 5: MEMBER CONTRIBUTIONS & CODE WALKTHROUGH
### [Each member presents their specific contributions]

---

## PART 5A: JOSHUA ROMERO - PROJECT LEAD & CORE LOGIC
### [JOSHUA ROMERO - Code Demonstration]

Thank you for the architecture overview. Now, let me show you the core business logic we implemented.

### **My Contributions:**

1. **Main Application Entry Point & System Initialization**
2. **Authentication & User Management Logic**
3. **Student Operations & Concern Management**
4. **User Interface Design & Console Interface**
5. **Overall System Integration**

### **Code Walkthrough:**

#### **A. ApplicationLauncher.java - System Initialization**

*[Open IDE and navigate to ApplicationLauncher.java]*

```java
public class ApplicationLauncher {
    public static void start() {
        // 1. CREATE SERVICE IMPLEMENTATIONS
        IDataPersistence dataService = new DataPersistenceImpl();
        IStudentOperations studentOps = new StudentOperationsImpl();
        IAdminOperations adminOps = new AdminOperationsImpl();
        IAuthenticationService authService = new AuthenticationServiceImpl();
        
        // 2. LOAD APPLICATION DATA
        AppData appData = dataService.loadApplicationData();
        
        // 3. CREATE MENU HANDLER WITH DEPENDENCY INJECTION
        MenuHandler menuHandler = new MenuHandler(
            studentOps, adminOps, authService, dataService
        );
        
        // 4. START APPLICATION
        menuHandler.runMainMenu(appData.getStudents(), appData.getGrievances());
    }
}
```

**Explanation:**
- Lines 1-4: We instantiate all services
- Line 7: Loading data from JSON files - this is the startup point
- Lines 10-13: Dependency injection - passing services to MenuHandler
- Line 16: Start the application menu

**Why this design?**
- Centralized initialization - everything in one place
- Dependency injection - loose coupling between components
- Easy to modify - if a service changes, we update it in one location

#### **B. MenuHandler.java - Main Application Logic**

*[Navigate to MenuHandler.java]*

```java
public class MenuHandler {
    private IStudentOperations studentOps;
    private IAdminOperations adminOps;
    private IAuthenticationService authService;
    private IDataPersistence dataService;
    
    // Constructor - DEPENDENCY INJECTION
    public MenuHandler(IStudentOperations studentOps, ...) {
        this.studentOps = studentOps;
        // ... initialize others
    }
    
    public void runMainMenu(ArrayList<Student> students, ArrayList<Grievance> grievances) {
        int choice;
        do {
            Utility.clearScreen();
            Utility.printTitle("SSC STUDENT WELFARE SYSTEM");
            Utility.printMenu(new String[]{
                "[1] Student Login",
                "[2] Register Student",
                "[3] Admin Login",
                "[4] Exit"
            });
            choice = Utility.promptCenteredInt("Enter choice:");
            
            switch(choice) {
                case 1: studentLogin(students); break;
                case 2: studentOps.registerStudent(students); break;
                case 3: adminLogin(students, grievances); break;
                case 4: Utility.overlaySpinner("Exiting", 1000); break;
            }
        } while(choice != 4);
    }
}
```

**Key points:**
- Fields are interfaces, not concrete classes - this provides abstraction
- Constructor receives dependencies - injected, not created locally
- Menu loop is simple - delegation to service implementations

#### **C. StudentOperationsImpl.java - Student Business Logic**

*[Navigate to StudentOperationsImpl.java]*

```java
public class StudentOperationsImpl implements IStudentOperations {
    
    @Override
    public void registerStudent(ArrayList<Student> students) {
        String srCode;
        
        // VALIDATION LOOP
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
        
        // COLLECT USER INPUT
        String name = Utility.promptCenteredString("Enter Full Name: ");
        String email = Utility.promptCenteredString("Enter Email: ");
        String password = Utility.promptCenteredString("Enter Password: ");
        
        // CREATE & PERSIST
        Student newStudent = new Student(srCode, name, email, 
                                        PasswordUtils.hash(password));
        students.add(newStudent);
        DataManager.saveStudents(students);
        
        UIHelper.showSuccess("Registration successful!");
    }
    
    // HELPER METHOD
    private boolean isDuplicateStudent(ArrayList<Student> students, String srCode) {
        for (Student s : students) {
            if (s.getSrCode().equals(srCode)) return true;
        }
        return false;
    }
}
```

**Workflow:**
1. **Validate SR Code** - confirm student is in campus registry
2. **Check Duplicates** - ensure no duplicate registrations
3. **Collect Data** - name, email, password
4. **Hash Password** - security first!
5. **Save to File** - persistence
6. **Show Feedback** - user confirmation

*[Point to specific lines in IDE]*

"Notice here - the password is hashed immediately using SHA-256 before saving. This ensures that even if someone accesses the file, there are no plaintext passwords. This is a critical security measure in any real system."

### **My Key Learnings:**

1. **Separation of Concerns** - Each class has a specific responsibility
2. **Dependency Injection** - Enabled loose coupling and easier testing
3. **Service Layer Pattern** - Business logic separated from user interface
4. **Data Validation** - Input validation at every step
5. **Error Handling** - User-friendly error messages

"The most important thing I learned was the power of good architecture. Without using interfaces and dependency injection, the system would be much harder to maintain and extend."

---

## PART 5B: ZYRUS LIAO - ARCHITECTURE & UI DESIGN
### [ZYRUS LIAO - Architecture & Interface Design]

Hello, I'm Zyrus. In this section, I'll show you our architectural design and user interface implementation.

### **My Contributions:**

1. **System Architecture Design (5-Layer Pattern)**
2. **Interface-Based Design & Dependency Injection**
3. **User Interface Implementation**
4. **Console UI Components (Borders, Menus, Pagination)**
5. **System Stability & Code Organization**

### **Code Walkthrough:**

#### **A. 5-Layer Architecture Overview**

*[Show architecture diagram in IDE or documentation]*

```
LAYER 1: PRESENTATION LAYER
├── Main.java
├── MenuHandler.java
└── Console UI (Utility, UIHelper)

LAYER 2: INTERFACE LAYER
├── IStudentOperations
├── IAdminOperations
├── IAuthenticationService
└── IDataPersistence

LAYER 3: SERVICE LAYER (Business Logic)
├── StudentOperationsImpl
├── AdminOperationsImpl
├── AuthenticationServiceImpl
└── DataPersistenceImpl

LAYER 4: ENTITY LAYER (Data Models)
├── User (abstract)
├── Student (extends User)
├── Admin (extends User)
├── Grievance
└── AppData

LAYER 5: UTILITY LAYER
├── Utility
├── UIHelper
├── PasswordUtils
└── StudentValidator
```

**Explanation of each layer:**

"Layer 1 is our user-facing code - what users see and interact with.
Layer 2 defines contracts - interfaces that specify what needs to be done.
Layer 3 is the actual implementation - how we do what the interfaces require.
Layer 4 contains data structures - the entities that represent real-world objects.
Layer 5 provides helper utilities - reusable components used throughout.

The important thing is the separation. If we need to change database storage, we only modify Layer 3. Layers 1 and 2 remain untouched."

#### **B. Interface Design - IStudentOperations**

*[Navigate to IStudentOperations.java in IDE]*

```java
public interface IStudentOperations {
    void registerStudent(ArrayList<Student> students);
    void validateRegisteredStudents(ArrayList<Student> students);
    void fileConcern(Student student, ArrayList<Grievance> grievances);
    void viewStudentConcerns(Student student, ArrayList<Grievance> grievances);
}
```

"Notice that the interface has no implementation details. It's a contract - 'this is what needs to be done.' The actual implementation is in StudentOperationsImpl.

Why is this important? Because we can create mock implementations for testing:

```java
// For testing, we can create a fake implementation
public class MockStudentOperations implements IStudentOperations {
    @Override
    public void registerStudent(ArrayList<Student> students) {
        // Test implementation - no real file I/O
    }
}

// And use it in tests without changing MenuHandler
menuHandler = new MenuHandler(new MockStudentOperations(), ...);
```

This is the power of interfaces - flexibility and testability."

#### **C. UI Components - Utility.java**

*[Navigate to Utility.java]*

```java
public class Utility {
    // BORDER DRAWING
    public static void printTitle(String title) {
        int consoleWidth = getConsoleWidth();
        int innerWidth = Math.max(title.length() + 4, 60);
        
        // Top border
        printCentered("╔" + "═".repeat(innerWidth) + "╗");
        
        // Title
        int leftPad = (innerWidth - title.length()) / 2;
        printCentered("║" + " ".repeat(leftPad) + title + "║");
        
        // Bottom border
        printCentered("╚" + "═".repeat(innerWidth) + "╝");
    }
    
    // MENU DISPLAY
    public static void printMenu(String[] options) {
        int boxWidth = 38;
        int contentWidth = boxWidth - 2;
        
        for (String opt : options) {
            if (opt.trim().isEmpty()) {
                System.out.println();
            } else {
                // Create centered menu items with borders
                System.out.println("┌" + "─".repeat(contentWidth) + "┐");
                System.out.println("│" + centerText(opt, contentWidth) + "│");
                System.out.println("└" + "─".repeat(contentWidth) + "┘");
            }
        }
    }
}
```

"These UI components are reusable. All screens use the same printTitle and printMenu methods. This follows the DRY principle - Don't Repeat Yourself.

Notice the box-drawing characters (╔ ═ ║ ╚ etc) - these are Unicode characters that create professional-looking borders in the console. The alignment is calculated precisely for perfect lining."

*[Point to specific alignment calculations in IDE]*

"Here - contentWidth minus title length, divided by 2 for centering. If we create a new screen, the same logic applies - reusable, maintainable, and consistent UI."

#### **D. Pagination in AdminOperationsImpl.java**

*[Navigate to AdminOperationsImpl.java]*

```java
public void viewAllConcerns(ArrayList<Grievance> grievances) {
    int pageSize = 5;  // 5 concerns per page
    int totalPages = (int) Math.ceil((double) grievances.size() / pageSize);
    
    for (int page = 0; page < totalPages; page++) {
        Utility.clearScreen();
        Utility.printTitle("ALL CONCERNS");
        Utility.printCentered("Page " + (page + 1) + " of " + totalPages);
        Utility.printCentered("");
        
        int start = page * pageSize;
        int end = Math.min(start + pageSize, grievances.size());
        
        for (int i = start; i < end; i++) {
            grievances.get(i).display();
            System.out.println();
        }
        
        if (page < totalPages - 1) {
            Utility.waitForEnter("Press Enter for next page...");
        } else {
            Utility.waitForEnter("Press Enter to continue...");
        }
    }
}
```

"This is the pagination logic. We calculate how many total pages are needed based on 5 items per page. Then we loop through pages, displaying 5 items each, and prompt for the next page.

Example: If we have 13 items and 5 per page:
- Page 1: items 0-4
- Page 2: items 5-9
- Page 3: items 10-12
Total pages = ceil(13/5) = 3 pages

The calculation in code is automatic - the system computes based on actual data."

### **My Key Learnings:**

1. **Architecture Matters** - Good design is the foundation of scalability
2. **Reusable Components** - Build once, use many times
3. **Separation of Concerns** - Each layer has a specific responsibility
4. **User Experience** - Consistent UI, clear pagination, helpful messages
5. **Code Organization** - Clear structure makes maintenance easier

"My biggest realization was how powerful proper architecture is. I saw that with a good foundation, we can scale and evolve the system without major refactoring."

---

## PART 5C: LALAINE ANNE VIRTUCIO - DATA MANAGEMENT & PERSISTENCE
### [LALAINE ANNE VIRTUCIO - Database & Persistence Logic]

Hello, I'm Lalaine Anne. I focused on data management, persistence, and security aspects of the system.

### **My Contributions:**

1. **Data Persistence Implementation (File-based JSON)**
2. **Database Structure Design**
3. **Data Security (Password Hashing)**
4. **Data Validation & Integrity**
5. **Error Handling & Recovery**

### **Code Walkthrough:**

#### **A. DataPersistenceImpl.java - Core Data Operations**

*[Navigate to DataPersistenceImpl.java]*

```java
public class DataPersistenceImpl implements IDataPersistence {
    private static final String STUDENTS_FILE = "students.json";
    private static final String GRIEVANCES_FILE = "grievances.json";
    
    @Override
    public AppData loadApplicationData() {
        ArrayList<Student> students = loadStudents();
        ArrayList<Grievance> grievances = loadGrievances();
        StudentValidator.reloadRegistry();
        return new AppData(students, grievances);
    }
    
    private ArrayList<Student> loadStudents() {
        ArrayList<Student> students = new ArrayList<>();
        File file = new File(STUDENTS_FILE);
        
        if (!file.exists()) {
            return students;  // Return empty if file doesn't exist
        }
        
        try {
            String content = new String(
                Files.readAllBytes(file.toPath()),
                StandardCharsets.UTF_8
            );
            
            // REGEX PATTERN MATCHING
            Pattern jsonPattern = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
            Matcher jsonMatcher = jsonPattern.matcher(content);
            
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
    
    private Student parseStudentFromJson(String jsonContent) {
        try {
            // Extract fields from JSON
            String password = extractJsonField(jsonContent, "passwordHash");
            String name = extractJsonField(jsonContent, "name");
            String srCode = extractJsonField(jsonContent, "sr");
            String email = extractJsonField(jsonContent, "email");
            
            Student student = new Student(srCode, name, email, password);
            student.setValidated(true);  // Default to validated for existing students
            
            return student;
        } catch (Exception e) {
            return null;
        }
    }
}
```

**Explain the flow:**

"When the system starts:
1. Load students from JSON - parse the file and convert to Student objects
2. Load grievances from JSON - same process
3. Pass to MenuHandler via AppData wrapper

Critical security point: Notice that `passwordHash` is used - never plaintext passwords!"

#### **B. Saving Data - Write Operations**

*[Navigate to saveStudents method]*

```java
@Override
public void saveStudents(ArrayList<Student> students) {
    try (PrintWriter writer = new PrintWriter(STUDENTS_FILE, StandardCharsets.UTF_8)) {
        writer.println("[");
        
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            writer.print("{\"sr\":\"" + escapeJson(student.getSrCode()) + "\",");
            writer.print("\"name\":\"" + escapeJson(student.getName()) + "\",");
            writer.print("\"email\":\"" + escapeJson(student.getEmail()) + "\",");
            writer.print("\"passwordHash\":\"" + escapeJson(student.getPassword()) + "\"");
            
            if (i < students.size() - 1) {
                writer.println("},");
            } else {
                writer.println("}");
            }
        }
        
        writer.println("]");
    } catch (Exception e) {
        UIHelper.showError("Failed to save students: " + e.getMessage());
    }
}
```

"Here's where we save data back to the JSON file. Notice:
1. Try-with-resources (try with parentheses) - automatically closes the file
2. Escaping JSON special characters - prevents corruption
3. Proper JSON array format - [{ }, { }] - valid JSON
4. Exception handling - graceful error messages

Important: there are no plaintext passwords - only hashed values are saved."

#### **C. PasswordUtils - Cryptographic Hashing**

*[Navigate to PasswordUtils.java]*

```java
public class PasswordUtils {
    public static String hash(String input) {
        if (input == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            
            // Convert bytes to hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
```

"SHA-256 hashing - here's why it's important:

1. **One-way function** - the hash cannot be reversed to get the password
2. **Same input = same output** - for verifying login
3. **Different input = different output** - unique hash per password

Example:
- Password: 'myPassword123' → Hash: 'a3e8f4d2c...' (always the same)
- Password: 'mypassword123' → Hash: 'x9q2k1m9b...' (different)

During login:
1. User enters: 'myPassword123'
2. System hashes it: 'a3e8f4d2c...'
3. Compare with stored hash: 'a3e8f4d2c...'
4. Match! Login successful

If an attacker gets the file, they only see hashes, not the actual passwords."

#### **D. Grievance Persistence - Complex Data**

*[Navigate to Grievance save method]*

```java
public void saveGrievances(ArrayList<Grievance> grievances) {
    try (PrintWriter writer = new PrintWriter(GRIEVANCES_FILE, StandardCharsets.UTF_8)) {
        writer.println("[");
        
        for (int i = 0; i < grievances.size(); i++) {
            Grievance g = grievances.get(i);
            writer.println("  {");
            writer.println("    \"grievanceId\": " + g.getId() + ",");
            writer.println("    \"studentSr\": \"" + escapeJson(g.getStudentSr()) + "\",");
            writer.println("    \"title\": \"" + escapeJson(g.getTitle()) + "\",");
            writer.println("    \"category\": \"" + escapeJson(g.getCategory()) + "\",");
            writer.println("    \"description\": \"" + escapeJson(g.getDescription()) + "\",");
            writer.println("    \"status\": \"" + escapeJson(g.getStatus()) + "\",");
            writer.println("    \"feedback\": \"" + escapeJson(g.getFeedback()) + "\"");
            
            if (i < grievances.size() - 1) {
                writer.println("  },");
            } else {
                writer.println("  }");
            }
        }
        
        writer.println("]");
    } catch (Exception e) {
        UIHelper.showError("Failed to save grievances: " + e.getMessage());
    }
}
```

"Grievances are more complex - they have many fields. Notice:
1. grievanceId - numeric, not quoted
2. String fields - all quoted and escaped
3. Proper JSON formatting - indentation for readability

The escapeJson function prevents corruption from special characters:
- If title contains a quote: 'He said \"Hello\"'
- We need to escape it: 'He said \\\"Hello\\\"'
- Otherwise, the JSON parser gets confused"

#### **E. Validation & Startup**

*[Show StudentValidator integration]*

```java
@Override
public AppData loadApplicationData() {
    ArrayList<Student> students = loadStudents();
    ArrayList<Grievance> loadGrievances();
    StudentValidator.reloadRegistry();  // ← Reload from registry
    return new AppData(students, grievances);
}
```

"At startup:
1. Load all students from students.json
2. Load all grievances from grievances.json
3. Reload campus registry for validation

This ensures data is current and consistent at startup."

### **My Key Learnings:**

1. **Data Integrity** - Proper format and escaping are critical
2. **Security** - Never store plaintext passwords, always hash
3. **Persistence Patterns** - Abstraction (IDataPersistence) enables flexibility
4. **Error Handling** - Always handle file I/O exceptions gracefully
5. **Data Validation** - Validate data both on input and output

"The biggest lesson was how sensitive data handling is. One wrong decision in password storage can compromise the entire system. That's why proper cryptography is not optional."

---

## PART 6: SYSTEM FLOW DEMONSTRATION
### [JOSHUA ROMERO - Live Demo]

Now, let's do a complete system flow demonstration so you can see everything working together.

*[Open the running application]*

### **Complete User Journey:**

#### **STEP 1: System Startup**
- Show Main.java entry
- Show loading animation
- Display main menu

#### **STEP 2: Student Registration**
1. Select "Register Student"
2. Enter SR Code (e.g., "24-31688")
3. Enter name, email, password
4. Show validation success
5. Show data persisted to students.json

#### **STEP 3: Admin Validation**
1. Login as admin (username: admin, password: admin)
2. Select "Validate Registered Students"
3. Approve the new student
4. Show student status changed in file

#### **STEP 4: Student Login**
1. Login with the registered student
2. Show student menu options
3. Display welcome message with student name

#### **STEP 5: File Concern**
1. Select "File Concern"
2. Choose category (e.g., Academic)
3. Enter title and description
4. Show auto-generated Grievance ID
5. Show concern saved to grievances.json

#### **STEP 6: View Concerns**
1. Student views "My Concerns"
2. Show pagination if multiple concerns
3. Display details of each concern
4. Show empty feedback (before admin responds)

#### **STEP 7: Admin Actions**
1. Admin login
2. Select "View All Concerns"
3. Show paginated list
4. Select concern to update
5. Change status to "Under Review"
6. Add feedback
7. Show concern updated in file

#### **STEP 8: Student Checks Response**
1. Student login again
2. View my concerns
3. Show updated status
4. Show admin feedback

*[Close application]*

"This is the complete cycle - from registration to resolution. All data is persisted, secured with hashing, and organized using OOP principles."

---

## PART 7: LEARNING OUTCOMES & REFLECTIONS
### [All Members - Closing]

Now, let's reflect on our learning journey while building this system.

### [JOSHUA ROMERO - First Reflection]

**My Key Learnings:**

1. **The Power of Architecture**
   - Before this project, I thought architecture was just theory. But implementing it myself, I realized how critical it is for maintenance and scalability. With good foundations, future changes are smooth.

2. **Importance of Clean Code**
   - Naming conventions, organization, separation of concerns - these aren't just best practices, they're necessities for team collaboration and long-term maintenance.

3. **Security Cannot Be an Afterthought**
   - Password handling, data validation - they must be core to system design, not added later. One mistake can compromise the entire application.

4. **Testing & Validation**
   - Validation at every step - input validation, duplicate checking, SR Code verification. This prevents bugs and ensures data integrity. Small validations have big impact.

5. **User Experience Matters**
   - Clear error messages, pagination, organized menus - they make the difference between a confusing app and a user-friendly one. Code quality isn't just performance, it's also UX.

**Real-World Application:**
"In industry, companies invest heavily in proper architecture because of the ROI. A well-architected system allows faster feature additions, easier bug fixes, and better long-term maintainability. This is the value of OOP."

### [ZYRUS LIAO - Architectural Reflection]

**My Key Learnings:**

1. **Interface-Based Design is a Game Changer**
   - Working with interfaces, I realized how powerful this flexibility is. You can change the implementation anytime without touching other code. This is crucial for professional development.

2. **Separation of Concerns Prevents Chaos**
   - If everything was in one class, debugging would be a nightmare. Separating presentation, business logic, and data makes code readable and maintainable.

3. **Reusable Components Save Time**
   - Build once, use many times. Our UI utilities are reused throughout the system. This is the DRY principle in action - Don't Repeat Yourself.

4. **Architecture is About Future Needs**
   - We designed the system thinking: 'What if we need to change the database? What if we need to add new features?' Good architecture anticipates these needs.

5. **Code Organization = Team Success**
   - With proper folders, naming, and structure, any team member can understand the codebase quickly. This is the foundation of collaboration.

**Industry Insight:**
"Large companies like Google and Microsoft have architectural patterns that are strictly followed. Not because of rules, but because it saves millions in maintenance and prevents costly mistakes. Our project, though small, taught us why these patterns matter."

### [LALAINE ANNE VIRTUCIO - Data & Security Reflection]

**My Key Learnings:**

1. **Data Integrity is Non-Negotiable**
   - Proper file I/O, error handling, validation - they prevent data corruption. Even small mistakes can result in lost data.

2. **Security Must Be Prioritized**
   - Hashing passwords, escaping special characters, input validation - these are not optional extras, they're fundamental requirements. One security vulnerability can compromise everything.

3. **Abstraction Enables Flexibility**
   - Through the IDataPersistence interface, we can switch from file-based to database-based storage without changing business logic. This flexibility is incredibly valuable.

4. **Error Handling is Part of Design**
   - Try-catch blocks, graceful failure, user-friendly error messages - they make the system robust. Users aren't frustrated, and developers can debug easily.

5. **Data Format Matters**
   - JSON format is structured, readable, and standardized. Using proper formats prevents parsing issues and makes data portable.

**Real-World Application:**
"In healthcare, banking, and government - data handling is critical. One mistake can be a legal liability, financial loss, or security breach. Our system, though educational, taught us real data handling practices essential in professional development."

### [ALL MEMBERS - Collective Insights]

**Common Themes We All Learned:**

1. **OOP is Not Just Theory**
   - Before: OOP was abstract concepts
   - After: OOP is practical tools that solve real problems
   - Encapsulation, Inheritance, Polymorphism, and Abstraction are not just letters - they're principles that guide design

2. **Code Quality Requires Discipline**
   - Comments, naming, organization, validation
   - It's not about being perfect, it's about being professional
   - Code is written once but read many times

3. **Collaboration Requires Clear Code**
   - Well-organized code is easier for teams
   - Clear interfaces are clear communication
   - Our project showed how documentation and code work together

4. **Scalability Requires Planning**
   - Starting with good architecture saves time later
   - Small decisions early have big impact later
   - What works for 100 users may not work for 100,000

5. **Learning is Continuous**
   - This project is just the beginning
   - There are more patterns, technologies, and approaches
   - But the foundation we built here is solid

### **Our Biggest "Aha" Moment:**

*[JOSHUA]*: "Midway through development, when we added a new feature without major refactoring - that's when it clicked. Good architecture has a visible ROI."

*[ZYRUS]*: "When I realized we could change the implementation without updating MenuHandler - this is the power of abstraction. It clicked why professional code structures this way."

*[LALAINE]*: "When we had to change the students.json format and everything adjusted automatically because of persistence abstraction - security and flexibility together."

### **What We'd Do Differently Next Time:**

1. Start with more unit tests from the beginning
2. Document more extensively as we code
3. Involve more security review from the design phase
4. Plan data structures more thoroughly upfront
5. Consider more edge cases in validation

### **Advice for Future Developers:**

1. **Don't Skip Architecture**
   - It's tempting to code first and design later
   - Investing in design upfront saves months later

2. **Learn from Patterns**
   - We used common patterns (DI, service layer, interfaces)
   - These patterns are battle-tested in industry

3. **Security First, Always**
   - It's easier to build secure from the start than patch later
   - One vulnerability could compromise everything

4. **Testing as You Go**
   - Not as an afterthought
   - Integration testing as you build

5. **Document Your Decisions**
   - Code comments help future you and your team
   - Explain WHY, not just WHAT

### **What This Project Means to Us:**

> "This project is not just a coding exercise. It's understanding that software development is discipline, art, and science combined. Technical skills are important, but the thinking - how to structure, design, and maintain - that's the real learning." - Joshua Romero

> "Seeing a complete system work from design to implementation is fulfilling. Knowing that the code is clean, secure, and maintainable is professional pride." - Zyrus Liao

> "Realizing that data handling is critical, security is non-negotiable, and careful design prevents problems - these lessons will guide our careers." - Lalaine Anne Virtucio

---

## PART 8: Q&A & CLOSING REMARKS

### **Thank You**

Thank you for your attention to our presentation. We hope we demonstrated:
- How OOP principles apply to real systems
- Why architecture and design matter
- How small projects teach big lessons
- That coding is collaborative, not solo

### **Contact & Next Steps**

If you have questions about our system, architecture, or implementation - we're happy to discuss further.

Our GitHub repository: https://github.com/imjoshuaromero/oop-final-project

Branch: josh-edits (contains our latest work)

### **Final Thoughts**

"Building the SSC Student Welfare System was a journey. From concept to implementation, we learned that good software is a combination of technical skill, careful design, and understanding of business needs.

The system has 25+ classes, 5-layer architecture, secure data handling, and clean interfaces.

But most importantly, it taught us that engineering is thinking - not just coding."

---

## APPENDIX: Technical Reference

### **Technology Stack**
- **Language:** Java 8+
- **Data Storage:** JSON files
- **Security:** SHA-256 password hashing
- **Architecture:** 5-Layer Layered Architecture with Dependency Injection
- **Design Patterns:** Service Layer, Dependency Injection, Interface Segregation

### **Key Files & Responsibilities**

| File | Lines | Responsibility |
|------|-------|-----------------|
| Main.java | 10 | Application entry point |
| ApplicationLauncher.java | 25 | Dependency injection setup |
| MenuHandler.java | 184 | Main application logic, routing |
| StudentOperationsImpl.java | 192 | Student business logic |
| AdminOperationsImpl.java | 134 | Admin operations |
| AuthenticationServiceImpl.java | 86 | Authentication & password management |
| DataPersistenceImpl.java | 196 | Data I/O with JSON files |
| Utility.java | 304 | UI utilities, console formatting |
| UIHelper.java | 73 | UI messages and dialogs |

### **System Statistics**
- **Total Lines of Code:** ~1,800+
- **Number of Classes:** 20+
- **Number of Interfaces:** 4
- **Students Handled:** 40+ campus registry
- **Concerns Tracked:** Unlimited (paginated)
- **Compilation:** 0 errors, 0 warnings
- **OOP Principles Used:** All 4 (Encapsulation, Inheritance, Polymorphism, Abstraction)

### **Complexity Highlights**
- **Encapsulation:** 8+ private fields with controlled access
- **Inheritance:** 2-level hierarchy (User → Student/Admin)
- **Polymorphism:** 3+ polymorphic methods (displayInfo, etc.)
- **Abstraction:** 4 major interface abstraction layers

---

**END OF PRESENTATION SCRIPT**

*[Total estimated presentation time: 45-60 minutes]*
*[Can be adjusted based on depth of Q&A and demo]*
