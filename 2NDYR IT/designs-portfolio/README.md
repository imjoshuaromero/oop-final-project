# Graphic Design Portfolio Website

A modern, minimalistic portfolio website for graphic designers and layout artists featuring a black and purple theme with interactive Pinterest-style layout.

## 🎨 Features

- **Modern Design**: Black and purple color scheme with gradients and glass morphism effects
- **Responsive**: Fully responsive design that works on all devices
- **Pinterest-Style Layout**: Interactive masonry grid layout for portfolio items
- **Easy Management**: Simple file-based content management
- **Filter System**: Filter designs by category (Branding, Digital, Print)
- **Smooth Animations**: Fade-in effects, hover transitions, and smooth scrolling
- **Image Modal**: Click on any design to view it in full screen

## 🚀 Getting Started

### Prerequisites

- A modern web browser (Chrome, Firefox, Safari, Edge)
- Basic understanding of HTML, CSS, and JavaScript (for customization)

### Installation

1. Simply open the `index.html` file in your web browser
2. No build process or dependencies required!

### Quick Start

1. **View the Portfolio**: Open `index.html` in your browser
2. **Add Designs**: Edit `js/portfolio-data.js` to add your work
3. **Upload Images**: Place images in the `assets/` folder

## 📁 File Structure

```
designs-portfolio/
│
├── index.html                    # Main HTML file
├── README.md                     # This file
│
├── css/
│   └── styles.css               # Custom CSS with animations
│
├── js/
│   ├── script.js                # Main JavaScript
│   ├── portfolio-data.js        # Portfolio items (EDIT THIS!)
│   └── cms-integration.js       # Optional CMS setup
│
├── assets/
│   ├── mypic.jpg               # Your profile photo
│   └── [your designs]          # Add your design images here
│
└── docs/
    └── PORTFOLIO-MANAGEMENT.md  # Complete management guide
```

## 🎯 Technology Stack

- **HTML5**: Semantic markup
- **CSS3**: Custom styles with animations
- **Tailwind CSS**: Utility-first CSS framework (via CDN)
- **DaisyUI**: Component library for Tailwind (via CDN)
- **JavaScript**: Vanilla JS for interactivity
- **LocalStorage**: For data persistence
- **Font Awesome**: Icons

## ⚙️ Customization

### Change Admin Password

Open `js/script.js` and modify line 4:
```javascript
const ADMIN_PASSWORD = 'your-new-password'; // Change this
```

### Modify Colors

The color scheme can be changed in `css/styles.css`:
```css
:root {
    --primary-purple: #9333ea;
    --secondary-purple: #7c3aed;
    --accent-purple: #a855f7;
}
```

### Update Personal Information

Edit the following sections in `index.html`:
- Hero section: Update name and tagline
- About section: Add your bio and social media links
- Footer: Add your copyright information

### Add Categories

To add new categories:
1. Add a new filter button in the portfolio section of `index.html`
2. Add the category option in the admin upload form select dropdown

## 📱 Sections

1. **Home**: Hero section with call-to-action buttons
2. **About**: Information about you as a designer
3. **Portfolio**: Masonry grid with filtering options
4. **Contact**: Contact form for inquiries
5. **Admin**: Secure login and upload system

## 🎨 Design Features

- **Glass Morphism**: Transparent blurred backgrounds
- **Gradients**: Purple gradient buttons and text
- **Rounded Corners**: Smooth, modern border radius
- **Hover Effects**: Interactive animations on all elements
- **Responsive Grid**: Adapts from 1-4 columns based on screen size
- **Custom Scrollbar**: Purple-themed scrollbar

## 📊 Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## 🔒 Security Note

This is a client-side only application. For production use with real authentication:
- Implement server-side authentication
- Use a proper database instead of localStorage
- Add HTTPS encryption
- Implement proper user management

## 🚀 Deployment

### GitHub Pages
1. Create a GitHub repository
2. Push all files to the repository
3. Go to Settings > Pages
4. Select main branch and save
5. Your site will be live at `https://yourusername.github.io/repository-name`

### Netlify
1. Drag and drop your folder to Netlify
2. Your site will be live instantly

### Vercel
1. Import your GitHub repository
2. Deploy with one click

## 📝 Tips

- Replace sample images with your actual designs
- Update social media links with your profiles
- Test on multiple devices for responsiveness
- Optimize images before uploading (recommended max 2MB each)
- Consider adding analytics to track visitors

## 🆘 Support

For issues or questions, refer to the documentation:
- [Tailwind CSS](https://tailwindcss.com/docs)
- [DaisyUI](https://daisyui.com/)
- [Font Awesome](https://fontawesome.com/)

## 📄 License

Free to use for personal and commercial projects.

## 🎉 Credits

Created with modern web technologies:
- Tailwind CSS for styling
- DaisyUI for components
- Font Awesome for icons
- Unsplash for sample images

---

**Happy designing! 🎨**
