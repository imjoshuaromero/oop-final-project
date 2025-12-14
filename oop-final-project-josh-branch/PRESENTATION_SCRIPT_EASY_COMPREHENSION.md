# SSC Student Welfare System - Easy-to-Comprehend Presentation Script

**Group Members:** Joshua Romero (Lead), Zyrus Liao, Lalaine Anne Virtucio

---

## 1. Introduction

**Joshua (Lead):**
Good day, everyone! I’m Joshua Romero, the lead for our group. With me are Zyrus Liao and Lalaine Anne Virtucio. Today, we’re excited to present our OOP final project, which we built together to help students and the school connect better.

**Zyrus:**
Hello, I’m Zyrus. I focused on the system’s structure and user interface.

**Lalaine:**
Hi, I’m Lalaine. I worked on data management and making sure everything is secure and saved properly.

---

## 2. System Title and SDG Brief

**Joshua:**
Our project is called the **SSC Student Welfare System**. It’s a program that lets students send their concerns, suggestions, or problems to the school’s Student Satisfaction Committee (SSC) in an organized way.

We also made sure our project supports some of the United Nations’ Sustainable Development Goals (SDGs):
- **Quality Education:** The system helps improve the school by letting students share feedback.
- **Reduced Inequalities:** Every student can use the system, no matter their background.
- **Partnerships for the Goals:** It helps students and school staff work together to solve issues.

---

## 3. System Demo: How It Works

**Joshua:**
Let’s walk through the main features of our system:

1. **Student Registration:**
   - New students can register using their SR Code, name, email, and password.
   - Passwords are kept safe using hashing (so they’re not stored as plain text).
   - After registering, an admin needs to approve the account before the student can log in.

2. **Student Login:**
   - Once approved, students can log in with their SR Code and password.
   - They’ll see a dashboard with options.

3. **Filing a Concern:**
   - Students can file a concern by choosing a category (like Academic, Harassment, Facilities, etc.), then entering a title and description.
   - Each concern gets a unique ID for tracking.

4. **Viewing Concerns:**
   - Students can view all their concerns, see their status (Submitted, Under Review, Resolved), and read admin feedback.
   - Concerns are shown 5 at a time for easy viewing.

5. **Changing Password:**
   - Students can change their password anytime for security.

6. **Admin Side:**
   - Admins can see all concerns, approve or reject student registrations, update concern statuses, and reset passwords if needed.

---

## 4. OOP Concepts in Our System

**Zyrus:**
We used the four main OOP concepts to make our system organized, secure, and easy to maintain. Here’s how:

### a. Encapsulation
- We keep important data (like passwords) private inside classes. Only special methods can access or change them. This keeps data safe and prevents mistakes.

### b. Inheritance
- We have a main User class. Both Student and Admin classes inherit from User, so they get all the basic features, but each can have their own extra features too. This avoids repeating code.

### c. Polymorphism
- We use the same method names for different types of users, but they work differently depending on whether it’s a student or admin. For example, `displayInfo()` shows different details for each.

### d. Abstraction
- We use interfaces and abstract classes to hide complex details. Other parts of the code just use simple commands, making the system easier to use and update.

---

## 5. Member Contributions and Code Walkthrough

**Joshua:**
I worked on the main logic, like registration, login, and concern filing. I also made sure the menus and screens are user-friendly.
- (Show in IDE: `ApplicationLauncher.java` for starting the system, `MenuHandler.java` for the main menu, `StudentOperationsImpl.java` for registration and concern logic.)

**Zyrus:**
I designed the system’s structure and user interface. I made the code for drawing borders and menus, and set up the interfaces for student actions.
- (Show in IDE: `Utility.java` for UI, `IStudentOperations.java` for student actions.)

**Lalaine:**
I handled saving and loading data, making sure everything is secure and correct. I also worked on password security and data validation.
- (Show in IDE: `DataPersistenceImpl.java` for saving/loading, `PasswordUtils.java` for password hashing.)

---

## 6. What We Learned

**Joshua:**
I learned that planning and organizing your code makes everything easier, especially when adding new features or fixing bugs.

**Zyrus:**
I realized that a clear and organized user interface helps users a lot, and using interfaces in code makes it easier to test and change things.

**Lalaine:**
I learned that keeping data safe and secure is very important. Even small mistakes can cause big problems, so you have to be careful with how you save and check data.

**All:**
- OOP helps make better, more organized projects.
- Good code is like a good group project: everyone knows their job, and everything fits together.
- Testing and planning save time in the long run.

---

## 7. Closing

Thank you for listening to our presentation! We hope you saw how our system can help students and the school work together. If you have questions, we’re happy to answer them!

You can also check our code on GitHub: https://github.com/imjoshuaromero/oop-final-project (branch: josh-edits)

---

**End of Script**

*(This script is clear, organized, and covers all the important details for your presentation.)*
