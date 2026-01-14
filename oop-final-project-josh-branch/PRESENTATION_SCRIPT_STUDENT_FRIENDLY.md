# SSC Student Welfare System - Student-Friendly Presentation Script

**Group Members:** Joshua Romero (Lead), Zyrus Liao, Lalaine Anne Virtucio

---

## 1. Introduction

**Joshua:**
Hi everyone! We’re group [insert group number/name if needed], and today we’re excited to show you our project for OOP. I’m Joshua, the team lead. With me are Zyrus and Lalaine. We worked together to build a system that helps students like us share concerns and get help from the school.

---

## 2. What is Our System?

**Joshua:**
Our project is called the **SSC Student Grievance System**. It’s a computer program that lets students send their problems, suggestions, or feedback to the school’s Supreme Student Council (SSC). The goal is to make it easier for students to be heard and for the school to respond quickly.

We also made sure our project supports some of the United Nations’ Sustainable Development Goals (SDGs):
- **Quality Education:** Everyone gets a chance to speak up and help improve the school.
- **Reduced Inequalities:** All students, no matter who you are, can use the system.
- **Partnerships for the Goals:** It helps students and school staff work together.

---

## 3. Demo: How Does It Work?

**Joshua:**
Let’s show you how the system works!

1. **Student Registration:** - ZY
   - If you’re a new student, you can register using your SR Code (student number).
   - You’ll enter your name, email, and password. Don’t worry, your password is safe because it’s hidden using a special code (hashing).
   - After registering, an admin needs to approve your account before you can log in.

2. **Student Login:** - LALAINE
   - Once approved, you can log in using your SR Code and password.
   - You’ll see your own dashboard with options.

3. **Filing a Concern:** - ZY
   - You can file a concern by choosing a category (like Academic, Harassment, Facilities, etc.).
   - You’ll write a title and description of your problem.
   - The system gives your concern a unique ID so you can track it.

4. **Viewing Your Concerns:** - LALA
   - You can see all the concerns you’ve filed, their status (like Submitted, Under Review, or Resolved), and any feedback from the admin.
   - If you have a lot of concerns, they’re shown 5 at a time so it’s not overwhelming.

5. **Changing Your Password:** - LALA
   - You can change your password anytime for security.

6. **Admin Side:** - JOSH
   - Admins can see all concerns from all students, approve new student accounts, and update the status of concerns.
   - They can also reset passwords if someone gets locked out.

---

## 4. OOP Concepts in Our System (Explained Simply)

**Joshua (Lead):**
We’ll each explain how we used the four OOP principles in our own parts of the system, with quick code examples from our project.

### a. Encapsulation — Lalaine
- Simple idea: keep private data safe; expose only what’s needed through getters/setters.
- Where we used it: user credentials, student validation flags, grievance status.
- Code sample (from `User.java` and `Student.java`):
```java
// User.java (fields are private; accessed via methods)
private String userName;
private String passwordHash; // never store plaintext
private String name;

public String getUserName() { return userName; }
public String getName() { return name; }
// password changes are controlled
public void setPasswordHash(String newHash) { this.passwordHash = newHash; }

// Student.java (account state is encapsulated)
private boolean validated;
public boolean isValidated() { return validated; }
public void setValidated(boolean validated) { this.validated = validated; }
```
- Why: prevents accidental changes (e.g., students can’t flip their own `validated` flag), and keeps sensitive data (passwords) protected.
- Data layer also respects encapsulation (from `DataPersistenceImpl.java`):
```java
// We write only hashed passwords to storage
writer.print("\"passwordHash\":\"" + escapeJson(student.getPassword()) + "\"");
```

### b. Inheritance — Zyrus
- Simple idea: share common behavior in a base class; specialize in child classes.
- Where we used it: `User` (base) → `Student` and `Admin` (children).
- Code sample (from `User.java`, `Student.java`, `Admin.java`):
```java
// User.java (common attributes and behavior)
public abstract class User {
   private String userName;
   private String name;
   public String getName() { return name; }
}

// Student.java extends User and adds SR-specific data
public class Student extends User {
   private String srCode;
   public String getSrCode() { return srCode; }
}

// Admin.java extends User (inherits shared behavior)
public class Admin extends User { /* admin-specific actions elsewhere */ }
```
- Why: reduces duplication (we don’t rewrite name/username handling in every class) and makes maintenance easier—change once in `User`, benefit everywhere.

### c. Polymorphism — Joshua
- Simple idea: same method name, different behavior depending on the object.
- Where we used it: different display/handling for `Student` vs `Admin`; operations wired via interfaces and called uniformly.
- Code sample (using interfaces and overrides):
```java
// IStudentOperations.java (contract)
void fileConcern(Student student, ArrayList<Grievance> grievances);

// StudentOperationsImpl.java (implementation)
@Override
public void fileConcern(Student student, ArrayList<Grievance> grievances) {
   // ... logic to collect inputs and add a Grievance
}

// MenuHandler.java (calls through the interface, polymorphically)
studentOps.fileConcern(currentStudent, grievances);
```
- Another example: grievance display adapts to data (from `Grievance.java`):
```java
public void display() {
   // Shows fields formatted for the console UI; same call, output varies by object state
}
```
- Why: we write code once against a contract and swap implementations without changing callers (great for testing and future extensions).

### d. Abstraction — Joshua
- Simple idea: hide complex details behind simple interfaces.
- Where we used it: `IDataPersistence`, `IStudentOperations`, `IAdminOperations` separate “what” from “how”.
- Code sample (from `DataPersistenceImpl.java` implementing `IDataPersistence`):
```java
@Override
public AppData loadApplicationData() {
   ArrayList<Student> students = loadStudents();
   ArrayList<Grievance> grievances = loadGrievances();
   return new AppData(students, grievances);
}
```
- The UI layer never cares if data comes from files or a future database; it just calls the interface.
- Why: easier to replace implementations (e.g., move from JSON files to SQL) without touching menus or business logic.

---

## 5. What Did Each Member Do?

**Joshua Romero (Lead – Core Logic & Student UX)**
- Files I owned/led:
   - `Main.java` (or `ApplicationLauncher` entry) – boots the app and wires services.
   - `MenuHandler.java` – routes main, student, and admin menus.
   - `StudentOperationsImpl.java` – register/login/file concern/view concerns with pagination.
   - `Grievance.java` – display formatting and readable printout for concerns.
   - `UIHelper.java` – consistent message boxes and success/error prompts.
- What I implemented and why:
   - Registration flow: SR-Code validation → duplicate check → password hashing → save.
      - Code: in `StudentOperationsImpl.registerStudent(...)` we validate, then create `new Student(sr, name, email, PasswordUtils.hash(pass))` and persist.
   - File a concern: collect category/title/description, auto-generate ID, append to list, save.
      - Code: `fileConcern(student, grievances)` builds a `Grievance` and writes to storage.
   - Pagination for better readability: show 5 concerns per page.
      - Code: `int pageSize = 5; int totalPages = (int)Math.ceil((double)list.size()/pageSize);` then loop pages.
   - Consistent UI: all screens use `Utility.printTitle(...)`, `Utility.printMenu(...)`, and centered prompts.
      - Code: `Utility.promptCenteredInt("Enter choice:")` in `MenuHandler` for a unified UX.

**Zyrus Liao (Architecture, Interfaces, Console UI)**
- Files I owned/led:
   - `Utility.java` – borders, centering, menus, clear screen, pagination helpers.
   - `User.java` (base), `Student.java`, `Admin.java` – inheritance structure with encapsulated fields.
   - Interfaces: `IStudentOperations`, `IAdminOperations`, `IAuthenticationService`, `IDataPersistence` – contracts for loose coupling.
   - `AdminOperationsImpl.java` – admin flows (view/update concerns, validate students).
- What I implemented and why:
   - Clean architecture via interfaces so `MenuHandler` depends on contracts, not concrete classes.
      - Code: `class MenuHandler { private IStudentOperations studentOps; ... }` then call `studentOps.registerStudent(...)`.
   - Console UI that’s aligned and readable using box-drawing characters.
      - Code: in `Utility.printMenu(...)`: `int contentWidth = boxWidth - 2;` then draw `┌─┐`, `│ │`, `└─┘` with centered text for perfect borders.
   - Inheritance for reuse: common getters in `User`, specialized data in `Student`/`Admin`.
      - Code: `public abstract class User { private String name; public String getName(){return name;} }` then `class Student extends User { private String srCode; }`.
   - Admin pagination and status updates for large lists.
      - Code: in `AdminOperationsImpl.viewAllConcerns(...)`, compute `totalPages` and prompt “Press Enter for next page...”.

**Lalaine Anne Virtucio (Data Persistence, Validation & Security)**
- Files I owned/led:
   - `DataPersistenceImpl.java` – load/save `students.json` and `grievances.json` with proper JSON formatting and escaping.
   - `PasswordUtils.java` – SHA-256 hashing for secure password storage.
   - `StudentValidator.java` – validates SR codes against a registry and marks accounts accordingly.
   - Data files: `students.json`, `grievances.json` – storage format and field naming (`sr`, `passwordHash`, etc.).
- What I implemented and why:
   - Correct field mapping and safe defaults when reading JSON.
      - Code: parse `sr` and `passwordHash`; set `student.setValidated(true)` if missing flag to avoid locking valid users.
   - Safe writes using try-with-resources and escaped output.
      - Code: in `saveStudents(...)`: `writer.print("\"passwordHash\":\"" + escapeJson(student.getPassword()) + "\"");` and wrap the array with `[` ... `]`.
   - Grievance persistence with all properties saved and readable.
      - Code: write `grievanceId`, `studentSr`, `title`, `category`, `description`, `status`, `feedback` in `saveGrievances(...)`.
   - One-way password security.
      - Code: `PasswordUtils.hash(String input)` uses `MessageDigest.getInstance("SHA-256")` and hex encoding; no plaintext passwords stored.

---

## 6. Let’s Point to the Code!

**Joshua:**
- Here’s the part where we start the system and load all the data. (Show `ApplicationLauncher.java`)
- This is the menu where you choose to log in, register, or exit. (Show `MenuHandler.java`)
- Here’s how we check if your SR Code is valid and if you’re already registered. (Show `StudentOperationsImpl.java`)

**Zyrus:**
- Here’s how we draw the nice borders and menus. (Show `Utility.java`)
- This is the interface that says what students can do. (Show `IStudentOperations.java`)

**Lalaine:**
- Here’s how we save your data to a file and make sure it’s safe. (Show `DataPersistenceImpl.java`)
- This is the code that hides your password using hashing. (Show `PasswordUtils.java`)

---

## 7. What Did We Learn?

**Joshua Romero (Lead):**
I learned that planning and organizing your code makes everything easier. Before we started, I thought coding was just writing lines to make things work. But after structuring registration flows, menu routing, and pagination logic, I realized that good organization is half the battle.

Specifically, I saw that:
- When I separated concerns (registration logic in `StudentOperationsImpl`, menus in `MenuHandler`, UI in `UIHelper`), adding new features became straightforward. For example, adding pagination only required changes in one place, not scattered everywhere.
- OOP principles like polymorphism meant I could call `studentOps.registerStudent(students)` without caring how it's implemented, making the code flexible. If we needed to change registration logic later, `MenuHandler` stays untouched.
- Input validation at every step (SR Code check, duplicate check, password hashing) prevented bugs and data corruption. Even small oversights can cause big problems.
- Thinking about the user experience (centered menus, consistent borders, clear prompts) matters as much as the backend logic. Users are more likely to use a system that's easy to navigate.

Key takeaway: Good architecture is an investment that pays off. What took time upfront saves hours later when debugging or adding features.

**Zyrus Liao (Architecture & UI):**
I realized that making a clean user interface and a solid architecture are deeply connected. When I started designing the system structure, I thought interfaces were just nice-to-have abstractions. But after implementing `IStudentOperations`, `IAdminOperations`, and `IDataPersistence`, I saw how powerful they are.

Specifically, I learned:
- Interfaces let different parts of the code talk to each other without knowing implementation details. `MenuHandler` calls `studentOps.fileConcern(...)` without knowing or caring if data gets saved to files, a database, or the cloud. This flexibility is huge.
- The 5-layer architecture (Presentation → Interfaces → Services → Entities → Utilities) made the code maintainable. Each layer has a clear job, and I can test and modify one layer without breaking others.
- Console UI details matter: aligned borders, centered text, consistent spacing. Users feel more confident with a polished interface, even in the console. Using `Utility.printMenu(...)` everywhere ensured every screen looked professional.
- Inheritance (User → Student/Admin) eliminated repetition. Instead of writing `getName()` twice, I write it once in `User` and both children inherit it. Changes in the base class automatically benefit all children.
- Testing becomes easier with good design. If I wanted to test menu flows, I could create a mock `IStudentOperations` that doesn't touch files, making tests fast and reliable.

Key takeaway: Good architecture enables growth. A system built on solid design patterns scales better and breaks less often as requirements change.

**Lalaine Anne Virtucio (Data & Security):**
I learned that data handling and security are not optional—they're foundational. Before this project, I thought security was something you "add on" at the end. But after working with password hashing, JSON persistence, and data validation, I realized security must be baked in from day one.

Specifically, I learned:
- Passwords must be hashed immediately. In `StudentOperationsImpl.registerStudent(...)`, we call `PasswordUtils.hash(pass)` before storing anything. If we ever accidentally logged a password or saved plaintext, that's a breach. One mistake here could compromise every student's account.
- JSON parsing requires care. When I initially parsed the wrong field names (`srCode` instead of `sr`, `password` instead of `passwordHash`), authentication broke. Field mapping matters, and escape sequences are critical to prevent corruption. Using `escapeJson(...)` prevents special characters from breaking the file format.
- Data validation prevents cascading problems. I validate data on input (check SR Code exists), on processing (check duplicates), and on output (ensure hashed passwords, not plaintext). Each layer of validation catches errors early.
- Try-with-resources in Java (`try (PrintWriter writer = ...)`) ensures files close properly even if an error occurs. Poor file handling can corrupt data or leave locks.
- Default values and backward compatibility matter. When the `validated` flag was missing from JSON, I set it to `true` by default instead of failing. This prevents locking valid users due to a missing field.

Real-world impact: In healthcare, banking, or e-commerce, one data breach costs millions and damages trust. This project taught me why companies invest so heavily in secure data handling.

Key takeaway: Security and data integrity are non-negotiable. Secure code is not harder to write—it just requires discipline and forethought.

**All Together:**
- **OOP isn't just for big companies.** Students building projects benefit enormously from inheritance, interfaces, and encapsulation. What started as class requirements became practical tools.
- **Good code is like a good group project.** Everyone knows their job (Joshua: logic, Zyrus: architecture, Lalaine: data), and everything fits together. Clear responsibilities prevent conflicts and confusion.
- **Testing and planning save time.** We spent upfront time designing menus, data structures, and interfaces. This prevented rewrites and debugging nightmares later.
- **Scalability starts with design.** Our system can handle more students, more concerns, more admins—not because of raw power, but because we didn't hard-code assumptions. The foundation supports growth.
- **Real-world skills matter.** We used patterns and practices from professional software. Dependency injection, interfaces, hashing, pagination—these are things you'll see in industry code.
- **Communication is as important as coding.** Our group succeeded because we clearly divided work and understood each other's code. Comments and consistent naming made collaboration smooth.

---

## 8. Final Words and Thank You

Thank you for listening to our presentation! We hope you saw how our system can help students and the school work together. If you have questions, we’re happy to answer them!

You can also check our code on GitHub: https://github.com/imjoshuaromero/oop-final-project (branch: josh-edits)

---

**End of Script**

*(This script is ready to read as-is for your presentation!)*
