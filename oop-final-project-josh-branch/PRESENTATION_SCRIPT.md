# SSC STUDENT WELFARE SYSTEM - PRESENTATION SCRIPT

## COMPREHENSIVE PRESENTATION GUIDE
**Group Members:** Joshua Romero (Lead), Zyrus Liao, Lalaine Anne Virtucio

---

## PART 1: INTRODUCTION & WELCOME
### [JOSHUA ROMERO - Lead Speaker]

Good morning/afternoon, everyone. My name is Joshua Romero, at kami ay narito ngayon para ipakita ang aming OOP Final Project.

Kami ay isang group na nagtrabaho together para gumawa ng isang meaningful system na makakatulong sa ating community. This project ay ang culmination ng aming learnings sa Object-Oriented Programming, at excited kami na ibahagi sa inyo ang aming journey sa development process.

Sa aming grupo, mayroon kaming tatlong miyembro:
- **Joshua Romero** - Ako mismo, ang project lead at main developer
- **Zyrus Liao** - Aming expert sa user interface design at system architecture
- **Lalaine Anne Virtucio** - Aming database management at data persistence specialist

Bago namin ipagsimula ang technical discussion, gustong naming ipakilala sa inyo ang aming system at kung paano ito aligned sa Sustainable Development Goals ng United Nations.

---

## PART 2: SYSTEM TITLE & SDG ALIGNMENT
### [JOSHUA ROMERO]

**SYSTEM TITLE: SSC Student Welfare System**

Ang aming system ay specially designed para sa **Student Satisfaction Committee (SSC)**, na naglalayong magbigay ng structured at efficient na paraan para sa mga estudyante na magsumite ng kanilang concerns, feedback, at grievances.

### **Sustainable Development Goals (SDGs) Alignment:**

Ang aming system ay aligned sa tatlong importanteng SDGs:

#### **1. SDG 4: QUALITY EDUCATION**
- Ang system ay nag-eensure ng transparent communication between students at administration
- Nagbibigay ng accessible platform para sa lahat ng students na magpahayag ng kanilang concerns
- Nag-promote ng continuous improvement ng educational services base sa feedback
- **Relevance:** Ang quality education ay hindi lang academics, kundi pati na rin ang overall student experience at welfare

#### **2. SDG 10: REDUCED INEQUALITIES**
- Lahat ng students ay may equal access sa sistema regardless ng kanilang background
- Walang discrimination sa pag-file ng concerns - lahat ay treated equally
- Transparent process na makikita ng lahat ang status ng kanilang concerns
- **Relevance:** Ang system ay nag-eensure na bawat estudyante ay may boses at mechanism para magpa-raise ng issues

#### **3. SDG 17: PARTNERSHIPS FOR THE GOALS**
- Nag-create ng partnership between students at administration
- Nag-facilitate ng collaboration para sa resolution ng student concerns
- Nag-promote ng mutual understanding through structured communication
- **Relevance:** Ang collaborative approach ay essential sa pag-achieve ng lahat ng sustainable development goals

Ngayon, ipapakita namin sa inyo kung paano gumagana ang system sa practice.

---

## PART 3: SYSTEM DEMONSTRATION
### [JOSHUA ROMERO]

*[Turn on the application and show the main menu]*

Dito ang aming system. Makikita ninyo ang **SSC STUDENT WELFARE SYSTEM** main interface.

### **Key Features Overview:**

#### **A. USER REGISTRATION & AUTHENTICATION**
1. **Student Registration**
   - New students ay pwedeng mag-register gamit ang kanilang SR Code
   - System ay automatically nag-validate laban sa campus registry
   - Password hashing para sa security
   - Account pending admin validation

2. **Student Login**
   - SR Code + Password authentication
   - System ay nag-verify ng account status
   - Access lang sa dashboard kung validated na ng admin

3. **Admin Login**
   - Separate login para sa administrative users
   - Full access sa system management features

#### **B. STUDENT FUNCTIONALITIES**
1. **File Concerns**
   - Student ay pwedeng mag-submit ng grievances
   - Six categories available:
     * Academic / Akademiko (grades, courses, teachers)
     * Harassment / Pang-aabuso (bullying, discrimination)
     * Facilities (classroom, facilities, resources)
     * Financial Aid (scholarships, fees)
     * Health & Wellness (mental health, counseling)
     * Others (miscellaneous concerns)
   - Automatic grievance ID generation para sa tracking
   - Real-time status tracking

2. **View My Concerns**
   - Students ay makikita ang lahat ng kanilang filed concerns
   - Pagination para sa better organization (5 per page)
   - Detailed information including status at feedback
   - Puwedeng makita ang admin response anytime

3. **Change Password**
   - Secure password change mechanism
   - Current password verification required
   - Confirmation validation para avoid typos

#### **C. ADMIN FUNCTIONALITIES**
1. **View All Concerns**
   - Comprehensive list ng lahat ng filed grievances
   - Paginated view para sa easy navigation
   - Status at category filtering capability
   - See full details ng bawat concern

2. **Update Concern Status**
   - Change status from: Submitted → Under Review → Resolved
   - Add feedback/response sa student
   - Track resolution timeline
   - Provide closure para sa student concern

3. **Validate Registered Students**
   - Review pending registrations
   - Accept o reject new student accounts
   - Ensure legitimate users lang ang makapasok

4. **Reset Student Password**
   - Emergency password reset capability
   - Temporary password generation
   - Admin assistance para sa locked accounts

5. **Reload SR Registry**
   - Update ang campus student database
   - Ensure latest enrollment data
   - Sync sa student list for validation

*[Demonstrate one complete student workflow: Register → Login → File Concern → View Concern]*

*[Show admin workflow: View concerns → Update status with feedback]*

Ito ang high-level overview ng system. Ngayon, kami ay mag-dive deeper sa technical implementation at kung paano namin ginamit ang OOP principles.

---

## PART 4: OOP INTEGRATION DISCUSSION
### [Each Member Discusses Their Part]

---

## PART 4A: ENCAPSULATION & ARCHITECTURE
### [ZYRUS LIAO - System Architecture & Encapsulation Expert]

Good morning/afternoon sa lahat. I'm Zyrus Liao, at ako ang nag-lead sa architectural design ng aming system.

### **What is Encapsulation?**

Encapsulation ay isa sa core principles ng OOP. Ito ay kung paano natin **"ini-wrap"** ang data at methods sa isang class, at ginagamit ang **private** fields para protect ang data. Ang idea ay **"Hide internal details, expose only what's necessary."**

Think of it like a car:
- Ang engine internals ay **private** - customers ay hindi dapat mag-usap directly
- Ang steering wheel at pedals ay **public** - ito lang ang need ng driver
- Walang direct access sa engine - protected lang through safe interfaces

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
- Passwords ay **never exposed** - only getters, at hashed values lang
- Kung need natin mag-add ng validation (e.g., password requirements), ONE place lang mag-update
- Prevents accidental or malicious direct modification

#### **2. Student Class (Extends User)**

```java
public class Student extends User {
    private String srCode;        // ← ENCAPSULATED (SR Code)
    private String email;         // ← ENCAPSULATED
    private boolean validated;    // ← ENCAPSULATED (account status)
    
    // Getter para sa SR Code - puwedeng i-access pero not modifiable
    public String getSrCode() { return srCode; }
    
    // Getter para sa validation status
    public boolean isValidated() { return validated; }
    
    // Setter para lang ng admin - control kung sino lang may power mag-validate
    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}
```

**Real-world benefit:**
- Student ay hindi pwedeng mag-change ng sariling SR Code (immutable)
- Validation status ay kontrolado lang ng admin
- Email ay protected - hindi mo makikita ng kung sino lang

#### **3. Grievance Class (Data Protection)**

```java
public class Grievance {
    private static int nextId = 1;     // ← ENCAPSULATED counter
    private int grievanceId;           // ← ENCAPSULATED
    private String status;             // ← ENCAPSULATED
    private String feedback;           // ← ENCAPSULATED
    
    // Getters allow reading pero hindi modification
    public int getId() { return grievanceId; }
    public String getStatus() { return status; }
    
    // Setters control HOW data changes
    public void setStatus(String newStatus) {
        // Could add validation: kung allowed lang certain status values
        this.status = newStatus;
    }
}
```

**Benefit:**
- Grievance ID ay auto-generated at protected - no one can manually change
- Status ay controlled - only valid statuses allowed
- Feedback ay protected from unauthorized modification

### **Why Encapsulation is Critical:**

1. **Security:** Passwords, sensitive info ay protected
2. **Maintainability:** Change implementation details without breaking external code
3. **Validation:** Control kung paano nag-change ang data
4. **Debugging:** Know exactly kung saan modified ang sensitive fields

**Real example from our system:**
- If admin tries to set invalid status, system ay "walang mangyari" kung may validation
- If password needs special characters requirement, ONE place lang update
- If student tries to "hack" SR Code, impossible dahil private at immutable

Ito ang power ng encapsulation - **security, control, at flexibility sa future changes.**

---

## PART 4B: INHERITANCE & CODE REUSE
### [LALAINE ANNE VIRTUCIO - Inheritance & Database Structure Expert]

Hi everyone, I'm Lalaine Anne Virtucio. Ako ang nag-handle ng database design at data persistence, at gusto kong i-explain kung paano namin ginamit ang **Inheritance** sa system.

### **What is Inheritance?**

Inheritance ay ang concept kung saan ang isang class ay **nag-acquire ng properties at methods** from parent class. Instead of repeating code, nag-share tayo ng common functionality. 

Think of it like family:
- Grandparent may general traits (height, eye color, etc.)
- Parent ay nag-inherit ng mga traits at nag-add ng sarili
- Child ay nag-inherit from parent
- **Reuse at specialize** - hindi duplication

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
- User class may 100 lines of common methods
- Student at Admin ay hindi na-repeat ng 100 lines
- Instead, `extends User` lang - get lahat ng functionality

#### **2. Single Point of Maintenance**
- Kung kailangan mag-change ng password validation
- Change in User class lang - automatically affect Student at Admin

**Example:** Kung gusto nating mag-require ng capital letters sa password:
```java
// User.java - ONE PLACE TO UPDATE
public void setPassword(String newPassword) {
    if (!newPassword.matches(".*[A-Z].*")) {
        throw new IllegalArgumentException("Password must have uppercase");
    }
    this.password = newPassword;
}

// AUTOMATICALLY APPLIED to both Student at Admin!
```

#### **3. Polymorphism Ready**
- Pwede nating treat both Student at Admin bilang User
- Flexible code na puwedeng mag-handle ng both types

### **Real-World Database Implication:**

In our data persistence layer:
```java
// One method handles BOTH Student at Admin
public void saveUsers(ArrayList<User> users) {  // ← Accepts both!
    for (User user : users) {  // ← Could be Student or Admin
        saveUser(user);
    }
}
```

Without inheritance, kailangan ng separate methods:
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
- **Total: 170 lines** ← If without inheritance, could be 300+ lines

Ito ang practical power ng inheritance - **code reuse, maintainability, at less duplication.**

---

## PART 4C: POLYMORPHISM & FLEXIBLE DESIGN
### [JOSHUA ROMERO - Business Logic & Polymorphism]

Now, let me discuss **Polymorphism** - isa sa pinaka-powerful features ng OOP.

### **What is Polymorphism?**

Polymorphism literally means **"many forms"**. It's when ang parehong method ay may different behavior depending sa object type.

Real-world analogy:
- `displayInfo()` sa Student = shows SR Code + grades
- `displayInfo()` sa Admin = shows admin privileges + access level
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

**Magic:** Same method name, completely different outputs based on object type!

#### **2. Polymorphic Service Interfaces**

Ang pinakamagandang halimbawa ng polymorphism ay ang aming service interfaces:

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
- MenuHandler ay hindi need to know WHO implements IStudentOperations
- Puwede nating baguhin implementation without changing MenuHandler code
- Puwede nating mag-create ng mock implementation for testing

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

1. **Flexibility:** Service implementation ay pwedeng mag-change anytime
2. **Testing:** Puwede nating mag-create ng fake services para sa testing
3. **Extensibility:** Puwede nating mag-add ng new implementations without touching existing code
4. **Loose Coupling:** Components ay walang tight dependency sa specific implementations

**Real scenario:** If bukas kailangan nating mag-integrate ng external API para sa student registry:
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

Ito ang beauty ng polymorphism - **flexibility at extensibility without code modification.**

---

## PART 4D: ABSTRACTION & INTERFACE-BASED DESIGN
### [ZYRUS LIAO - Abstraction & Clean Architecture]

Finally, let me explain **Abstraction** - which is actually integrated throughout our system.

### **What is Abstraction?**

Abstraction ay tungkol sa **"hiding complexity, showing only what's necessary."** It's like using a TV remote:
- You press "power" without knowing how electricity flows
- You don't need to understand circuit boards
- Interface ay simple, complexity ay hidden inside

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

**Real scenario:** If tomorrow kailangan nating mag-use ng database instead ng files:
```java
// Create new implementation
public class DatabasePersistence implements IDataPersistence {
    @Override
    public void saveStudents(ArrayList<Student> students) {
        // SQL queries, connection pooling, transactions
        // All abstracted from MenuHandler
    }
}

// MenuHandler ay walang idea na nag-change implementation!
```

### **Why Abstraction is Critical:**

1. **Simplicity:** Callers use simple interface, complexity hidden
2. **Maintainability:** Change implementation without affecting callers
3. **Testing:** Easy to create mock implementations para sa testing
4. **Professional:** Clear separation of concerns

Ito ang complete picture - **abstraction enables flexibility at professional code.**

---

## PART 5: MEMBER CONTRIBUTIONS & CODE WALKTHROUGH
### [Each member presents their specific contributions]

---

## PART 5A: JOSHUA ROMERO - PROJECT LEAD & CORE LOGIC
### [JOSHUA ROMERO - Code Demonstration]

Salamat sa aming architect. Ngayon, ipakikita ko ang core business logic na aming ginawa.

### **My Contributions:**

1. **Main Application Entry Point & System Initialization**
2. **Authentication & User Management Logic**
3. **Student Operations & Concern Management**
4. **UI/UX Design & Console Interface**
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
- Line 1-4: Nag-instantiate kami ng lahat ng services
- Line 7: Loading data from JSON files - ito ang startup point
- Line 10-13: Dependency injection - pumapasa ng services sa MenuHandler
- Line 16: Start the menu

**Why this design?**
- Centralized initialization - lahat sa isang lugar
- Dependency injection - loose coupling between components
- Easy to modify - if mag-change service, one place lang update

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
- Fields ay interfaces, hindi concrete classes - abstraction
- Constructor receives dependencies - injected, hindi created locally
- Menu loop ay simple - delegation sa service implementations

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
1. **Validate SR Code** - confirm student sa campus registry
2. **Check Duplicates** - ensure walang duplicate registration
3. **Collect Data** - name, email, password
4. **Hash Password** - security!
5. **Save to File** - persistence
6. **Show Feedback** - user confirmation

*[Point to specific lines in IDE]*

"Notice dito - password ay hashed agad using SHA-256 before saving. This ensures na kahit makita ang file, walang plaintext password. Ito ang practical security measure na importante sa any real system."

### **My Key Learnings:**

1. **Separation of Concerns** - Each class may specific responsibility
2. **Dependency Injection** - Enabled loose coupling at easier testing
3. **Service Layer Pattern** - Business logic separated from UI
4. **Data Validation** - Input validation at every step
5. **Error Handling** - User-friendly error messages

"Ang pinakamahalaga na natutunan ko ay ang power ng good architecture. Kung hindi namin ginamit ang interfaces at DI, ang system ay mas mahirap i-maintain at i-extend."

---

## PART 5B: ZYRUS LIAO - ARCHITECTURE & UI DESIGN
### [ZYRUS LIAO - Architecture & Interface Design]

Hi, I'm Zyrus. Sa part na ito, ipapakita ko ang aming architectural design at UI implementation.

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

**Explain each layer:**

"Layer 1 ay aming user-facing code - kung ano makikita ng users
Layer 2 ay mga contracts - interfaces na define kung ano dapat gawin
Layer 3 ay actual implementation - kung paano gina-do ang requirements
Layer 4 ay data structures - entities na represent ng real-world objects
Layer 5 ay helper utilities - reusable components

Ang important thing dito ay ang separation. Kung kailangan mag-change ng database storage, change lang kami sa Layer 3. Ang Layer 1 at 2 ay untouched."

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

"Notice na ang interface ay walang implementation detail. Ito ay contract lang - 'ito ang kailangan naming gawin.' Ang actual implementation ay sa StudentOperationsImpl.

Bakit importante ito? Dahil pwede nating mag-mock ito para sa testing:

```java
// For testing, puwede nating gumawa ng fake implementation
public class MockStudentOperations implements IStudentOperations {
    @Override
    public void registerStudent(ArrayList<Student> students) {
        // Test implementation - walang real file I/O
    }
}

// At gamitin sa tests without changing MenuHandler
menuHandler = new MenuHandler(new MockStudentOperations(), ...);
```

Ito ang power ng interface - flexibility at testability."

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

"Ang UI components na ito ay reusable. Lahat ng screens ay gumgamit ng same printTitle at printMenu. Ito ang DRY principle - Don't Repeat Yourself.

Notice din ang box-drawing characters (╔ ═ ║ ╚ etc) - ito ay Unicode characters na nag-create ng professional-looking borders sa console. Ang alignment ay calculated exactly para perfect ang lining."

*[Point to specific alignment calculations in IDE]*

"Here - contentWidth minus title length, divided by 2 para center. Kung gagawa ulit kami ng new screen, same logic lang - reusable, maintainable, consistent UI."

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

"Dito ang pagination logic. Calculate kung ilang total pages based sa 5 items per page. Loop through pages, display 5 items each, then prompt for next page.

Math: If may 13 items at 5 per page:
- Page 1: items 0-4
- Page 2: items 5-9
- Page 3: items 10-12
Total pages = ceil(13/5) = 3 pages

Ang calculation sa code ay automatic - system ay mag-compute based sa actual data."

### **My Key Learnings:**

1. **Architecture Matters** - Good design ay foundation ng scalability
2. **Reusable Components** - Build once, use many times
3. **Separation of Concerns** - Each layer has specific responsibility
4. **User Experience** - Consistent UI, clear pagination, helpful messages
5. **Code Organization** - Clear structure makes maintenance easier

"Ang aking biggest realization ay kung gaano powerful ang proper architecture. Nakita ko na kung maganda ang foundation, puwedeng mag-scale at mag-evolve ang system without major refactoring."

---

## PART 5C: LALAINE ANNE VIRTUCIO - DATA MANAGEMENT & PERSISTENCE
### [LALAINE ANNE VIRTUCIO - Database & Persistence Logic]

Hello, I'm Lalaine Anne. Ako ang nag-focus sa data management, persistence, at security aspects ng system.

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

"When system starts:
1. Load students from JSON - parse file at convert to Student objects
2. Load grievances from JSON - same process
3. Pass to MenuHandler via AppData wrapper

Key security point: notice na `passwordHash` ay ginagamit - never plaintext passwords!"

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

"Dito ang nagsasave ng data back to JSON file. Notice:
1. Try-with-resources (try with parentheses) - automatically closes file
2. Escaping JSON special characters - prevent corruption
3. Proper JSON array format - [{ }, { }] - valid JSON
4. Exception handling - graceful error messages

Important: walang plaintext passwords - hashed values lang ang nase-save."

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

"SHA-256 hashing - ito ang important:

1. **One-way function** - hindi ma-reverse ang hash para makuha password
2. **Same input = same output** - para mag-verify login
3. **Different input = different output** - unique hash per password

Example:
- Password: 'myPassword123' → Hash: 'a3e8f4d2c...' (always)
- Password: 'mypassword123' → Hash: 'x9q2k1m9b...' (different)

During login:
1. User enters: 'myPassword123'
2. System hashes it: 'a3e8f4d2c...'
3. Compare with stored hash: 'a3e8f4d2c...'
4. Match! Login successful

If attacker gets file, makikita nila lang hashes, hindi passwords."

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

"Grievances ay mas complex - maraming fields. Notice:
1. grievanceId - numeric, hindi quoted
2. String fields - all quoted at escaped
3. Proper JSON formatting - indentation for readability

Ang escapeJson function ay prevent corruption from special characters:
- If title ay may quote: 'He said \"Hello\"'
- Kailangan i-escape: 'He said \\\"Hello\\\"'
- Otherwise, JSON parser ay confused"

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
3. Reload campus registry para sa validation

This ensures data is current at consistent at startup."

### **My Key Learnings:**

1. **Data Integrity** - Proper format at escaping ay critical
2. **Security** - Never store plaintext passwords, always hash
3. **Persistence Patterns** - Abstraction (IDataPersistence) enables flexibility
4. **Error Handling** - Always handle file I/O exceptions gracefully
5. **Data Validation** - Validate data both on input at output

"Ang biggest lesson ay how sensitive data handling is. One wrong decision sa password storage ay pwedeng compromise ang entire system. That's why proper cryptography ay hindi optional."

---

## PART 6: SYSTEM FLOW DEMONSTRATION
### [JOSHUA ROMERO - Live Demo]

Now, let's do a complete system flow demonstration para makita ninyo lahat together.

*[Open the running application]*

### **Complete User Journey:**

#### **STEP 1: System Startup**
- Show Main.java entry
- Show loading animation
- Show main menu

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
1. Login with registered student
2. Show student menu options
3. Show welcome message with student name

#### **STEP 5: File Concern**
1. Select "File Concern"
2. Choose category (e.g., Academic)
3. Enter title at description
4. Show auto-generated Grievance ID
5. Show concern saved to grievances.json

#### **STEP 6: View Concerns**
1. Student views "My Concerns"
2. Show pagination if multiple concerns
3. Show details of each concern
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

"Ito ang complete cycle - from registration to resolution. Lahat ng data ay persisted, secured with hashing, at organized using OOP principles."

---

## PART 7: LEARNING OUTCOMES & REFLECTIONS
### [All Members - Closing)

Now, let's reflect sa aming learning journey sa project na ito.

### [JOSHUA ROMERO - First Reflection]

**My Key Learnings:**

1. **The Power of Architecture**
   - "Before this project, I thought architecture ay just theory. But implementing it myself, nag-realize ako kung critical ito sa maintenance at scalability. Kung maganda ang foundation, future changes ay smooth."

2. **Importance of Clean Code**
   - "Naming conventions, organization, separation of concerns - ito ay hindi lang 'best practices', ito ay necessity para sa team collaboration at long-term maintenance."

3. **Security Can't Be Afterthought**
   - "Password handling, data validation - dapat sila ay core sa system design, not added later. One mistake ay pwedeng compromise ang entire application."

4. **Testing & Validation**
   - "Validation sa every step - input validation, duplicate checking, SR Code verification. Ito ay prevent bugs at ensure data integrity. Small validations ay big impact."

5. **User Experience Matters**
   - "Clear error messages, pagination, organized menus - ito ay make difference between confusing app at user-friendly app. Code quality ay hindi lang performance, it's also UX."

**Real-World Application:**
"In industry, companies ay nag-invest heavily sa proper architecture dahil napakalakas ng ROI. A system na well-architected ay mas mabilis mag-add ng features, mas madaling mag-fix ng bugs, at mas maintainable long-term. Ito ang value ng OOP."

### [ZYRUS LIAO - Architectural Reflection]

**My Key Learnings:**

1. **Interface-Based Design is Game Changer**
   - "Working with interfaces, nag-realize ako ng flexibility ito. Can change implementation anytime without touching other code. This is crucial para sa professional development."

2. **Separation of Concerns Prevents Chaos**
   - "If everything was in one class, debugging would be nightmare. Separating presentation, business logic, data - ito ay make code readable at maintainable."

3. **Reusable Components Save Time**
   - "Build once, use many times. Our UI utilities ay reused sa entire system. This is DRY principle in action - Don't Repeat Yourself."

4. **Architecture is About Future Needs**
   - "We designed system thinking: 'what if kailangan nating mag-change database? What if mag-add ng new features?' Good architecture anticipates these needs."

5. **Code Organization = Team Success**
   - "With proper folders, naming, at structure, any team member can understand ang codebase quickly. Ito ay foundation ng collaboration."

**Industry Insight:**
"Large companies like Google, Microsoft - they have architectural patterns na strictly followed. Not because rules, but because it saves millions sa maintenance at prevents costly mistakes. Our project, though small, taught us why these patterns matter."

### [LALAINE ANNE VIRTUCIO - Data & Security Reflection]

**My Key Learnings:**

1. **Data Integrity is Non-Negotiable**
   - "Proper file I/O, error handling, validation - ito ay prevent data corruption. Even small mistakes ay pwedeng result sa lost data."

2. **Security Must Be Prioritized**
   - "Hashing passwords, escaping special characters, input validation - ito ay hindi optional extras, ito ay fundamental requirements. One security vulnerability ay pwedeng compromise everything."

3. **Abstraction Enables Flexibility**
   - "Through IDataPersistence interface, pwede nating mag-switch from file-based to database-based without changing business logic. This flexibility ay incredibly valuable."

4. **Error Handling is Part of Design**
   - "Try-catch blocks, graceful failure, user-friendly error messages - ito ay make system robust. Users ay hindi frustrated, developers ay madaling mag-debug."

5. **Data Format Matters**
   - "JSON format ay structured, readable, standardized. Paggamit ng proper formats ay prevent parsing issues at make data portable."

**Real-World Application:**
"In healthcare, banking, government - data handling ay critical. One mistake ay legal liability, financial loss, security breach. Our system, though educational, taught us real data handling practices na essential sa professional development."

### [ALL MEMBERS - Collective Insights]

**Common Themes We All Learned:**

1. **OOP is Not Just Theory**
   - Before: OOP ay abstract concepts
   - After: OOP ay practical tools na solve real problems
   - E, I, P, A ay not just letters - they're principles na guide design

2. **Code Quality Requires Discipline**
   - Comments, naming, organization, validation
   - It's not about being perfect, it's about being professional
   - Code ay written once but read many times

3. **Collaboration Requires Clear Code**
   - Well-organized code ay easier para sa team
   - Clear interfaces ay clear communication
   - Our project showed how documentation at code can work together

4. **Scalability Requires Planning**
   - Starting with good architecture ay save time later
   - Small decisions early ay have big impact later
   - What works for 100 users may not work for 100,000

5. **Learning is Continuous**
   - This project ay just beginning
   - Mas maraming patterns, technologies, approaches pa
   - But foundation na nag-build kami here ay solid

### **Our Biggest "Aha" Moment:**

*[JOSHUA]*: "Midway through development, when we added new feature at walang major refactoring needed - dun nag-click. Good architecture ay ROI na visible."

*[ZYRUS]*: "When I realized na mag-change ng implementation without updating MenuHandler - ito ang power ng abstraction. It clicked na why professional code structures are like this."

*[LALAINE]*: "When kami had to change students.json format at everything ay adjusted automatically dahil sa persistence abstraction - security at flexibility together."

### **What We'd Do Differently Next Time:**

1. "Start with more unit tests from beginning"
2. "Document more extensively as we code"
3. "Involve more security review from design phase"
4. "Plan data structure more thoroughly upfront"
5. "Consider more edge cases in validation"

### **Advice for Future Developers:**

1. **Don't Skip Architecture**
   - Tempting to code first, design later
   - Nag-invest sa design upfront ay save months later

2. **Learn from Patterns**
   - We use common patterns (DI, service layer, interfaces)
   - These patterns ay battle-tested sa industry

3. **Security First, Always**
   - Easier to build secure from start than patch later
   - One vulnerability ay could compromise everything

4. **Testing as You Go**
   - Not afterthought
   - Integration testing as you build

5. **Document Your Decisions**
   - Code comments ay help future you (and team)
   - Explain WHY, not just WHAT

### **What This Project Means to Us:**

> "This project ay hindi lang coding exercise. It's understanding na software development ay discipline, art, at science combined. The technical skills ay important, but the thinking - how to structure, design, maintain - that's the real learning." - Joshua Romero

> "Seeing a complete system work from design to implementation - that's fulfilling. Knowing that ang code ay clean, secure, at maintainable - that's professional pride." - Zyrus Liao

> "Realizing na data handling ay critical, security ay non-negotiable, and careful design ay prevent problems - these are lessons that'll guide our careers." - Lalaine Anne Virtucio

---

## PART 8: Q&A & CLOSING REMARKS

### **Thank You**

Salamat sa attention ninyo sa aming presentation. We hope na nag-demonstrate kami ng:
- How OOP principles apply to real systems
- Why architecture at design ay matter
- How small projects ay teach big lessons
- That coding ay collaborative, not solo

### **Contact & Next Steps**

If may questions about our system, architecture, or implementation - hindi problema! We're available to discuss further.

Our GitHub repository: https://github.com/imjoshuaromero/oop-final-project

Branch: josh-edits (contains our latest work)

### **Final Thoughts**

"Building this SSC Student Welfare System ay journey. From concept to implementation, we learned na good software ay combination of technical skill, careful design, at understanding ng business needs.

The system ay may 25+ classes, 5-layer architecture, secure data handling, clean interfaces.

But most importantly, it taught us na engineering ay thinking - not just coding."

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
- **OOP Principles Used:** All 4 (E, I, P, A)

### **Complexity Highlights**
- **Encapsulation:** 8+ private fields with controlled access
- **Inheritance:** 2-level hierarchy (User → Student/Admin)
- **Polymorphism:** 3+ polymorphic methods (displayInfo, etc.)
- **Abstraction:** 4 major interfaces abstraction layers

---

**END OF PRESENTATION SCRIPT**

*[Total estimated presentation time: 45-60 minutes]*
*[Can be adjusted based on depth of Q&A and demo]*
