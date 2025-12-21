// Portfolio Data - Easy Manual Updates
// Simply add, edit, or remove items from this array to update your portfolio

const portfolioData = [
    {
        id: 1,
        title: 'Brand Identity Design',
        category: 'print',
        description: 'Complete brand identity package',
        image: 'https://images.unsplash.com/photo-1561070791-2526d30994b5?w=800&h=600&fit=crop',
        dateAdded: '2025-01-01'
    },
    {
        id: 2,
        title: 'Social Media Graphics',
        category: 'pubmats',
        description: 'Engaging social media content',
        image: 'https://images.unsplash.com/photo-1611162617474-5b21e879e113?w=800&h=1000&fit=crop',
        dateAdded: '2025-01-02'
    },
    {
        id: 3,
        title: 'Magazine Layout',
        category: 'print',
        description: 'Editorial design for lifestyle magazine',
        image: 'https://images.unsplash.com/photo-1586281380349-632531db7ed4?w=800&h=1200&fit=crop',
        dateAdded: '2025-01-03'
    },
    {
        id: 4,
        title: 'Logo Design',
        category: 'apparel',
        description: 'Modern minimalist logo',
        image: 'https://images.unsplash.com/photo-1626785774573-4b799315345d?w=800&h=800&fit=crop',
        dateAdded: '2025-01-04'
    },
    {
        id: 5,
        title: 'Web Design',
        category: 'pubmats',
        description: 'Modern website interface',
        image: 'https://images.unsplash.com/photo-1547658719-da2b51169166?w=800&h=600&fit=crop',
        dateAdded: '2025-01-05'
    },
    {
        id: 6,
        title: 'Business Cards',
        category: 'print',
        description: 'Professional business card design',
        image: 'https://images.unsplash.com/photo-1589939705384-5185137a7f0f?w=800&h=1000&fit=crop',
        dateAdded: '2025-01-06'
    },
    {
        id: 7,  // Increment the ID
        title: 'Esports MVP',
        category: 'apparel',  // Choose: 'apparel', 'pubmats', 'print', or 'others'
        description: 'Esports team branding and merchandise design',
        image: 'designs/esports-mvp.png',  // Path to your image
        dateAdded: '2025-01-07'  // Today's date
    }
];

// HOW TO ADD NEW DESIGNS:
// 1. Add your image file to the assets folder (e.g., 'assets/my-new-design.jpg')
// 2. Copy this template and add it to the array above:
/*
    {
        id: 8,  // Increment the ID
        title: 'Your Design Title',
        category: 'apparel',  // Choose: 'apparel', 'pubmats', 'print', or 'others'
        description: 'Brief description of your design',
        image: 'assets/my-new-design.jpg',  // Path to your image
        dateAdded: '2025-12-21'  // Today's date
    },
*/

// HOW TO UPDATE:
// - Just edit the title, description, or image path of any item above
// - Save the file and refresh your browser (or deploy to Netlify)

// HOW TO DELETE:
// - Simply remove the entire object {} from the array
// - Make sure to keep proper comma separation between items
