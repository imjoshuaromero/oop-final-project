# Title Style Guide

## Available Title Styles

Your system now has **5 different title styles** to choose from:

### 1. **printTitle()** - Modern Box Style (Current Default)
```
╔═══════════════════════════════════════════╗
║           SSC STUDENT WELFARE SYSTEM      ║
╚═══════════════════════════════════════════╝
```
- **Best for:** Sub-menus, dashboards, form headers
- **Style:** Clean, modern, professional with Unicode box-drawing characters
- **Usage:** `Utility.printTitle("ADMIN DASHBOARD");`

### 2. **printBanner()** - Star Banner (Main Menu)
```
****************************************************
*****                                          *****
         SSC STUDENT WELFARE SYSTEM
*****                                          *****
****************************************************
```
- **Best for:** Main welcome screen, important announcements
- **Style:** Bold, eye-catching, classic
- **Usage:** `Utility.printBanner("SSC STUDENT WELFARE SYSTEM");`
- **Currently used:** Main menu

### 3. **printFancyTitle()** - Gradient Block Style
```
▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
▓▓▓▓▓         MY CONCERNS         ▓▓▓▓▓
▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓
░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
```
- **Best for:** Special sections, reports, highlighted content
- **Style:** Modern, gradient effect with shading blocks
- **Usage:** `Utility.printFancyTitle("VALIDATION REPORT");`

### 4. **printSimpleTitle()** - Minimal Underline
```
  STUDENT MENU  
────────────────
```
- **Best for:** Quick sections, minimalist design, compact spaces
- **Style:** Clean, simple, lightweight
- **Usage:** `Utility.printSimpleTitle("STUDENT MENU");`

### 5. **printWaveTitle()** - Decorative Wave
```
~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~
》》》  UPDATE CONCERN  《《《
~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~
```
- **Best for:** Form pages, action screens, dynamic content
- **Style:** Friendly, decorative, with wave and arrow elements
- **Usage:** `Utility.printWaveTitle("FILE CONCERN");`

---

## Customization Tips

### Current Implementation:
- **Main Menu:** Uses `printBanner()` for bold welcome screen
- **Sub-menus:** Uses `printTitle()` for clean, professional look

### Mix & Match Suggestions:

1. **Professional Theme** (Current):
   - Main menu: `printBanner()`
   - Dashboards: `printTitle()`
   - Forms: `printTitle()`

2. **Modern Theme**:
   - Main menu: `printFancyTitle()`
   - Dashboards: `printTitle()`
   - Forms: `printWaveTitle()`

3. **Minimalist Theme**:
   - All screens: `printSimpleTitle()`

4. **Playful Theme**:
   - Main menu: `printBanner()`
   - All other: `printWaveTitle()`

---

## How to Change

Simply replace the method name in your code:

**Before:**
```java
Utility.printTitle("ADMIN DASHBOARD");
```

**After (using fancy style):**
```java
Utility.printFancyTitle("ADMIN DASHBOARD");
```

All title methods automatically:
- ✅ Center the text
- ✅ Convert to uppercase
- ✅ Add proper spacing
- ✅ Adjust to console width
