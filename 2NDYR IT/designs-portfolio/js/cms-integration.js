// Headless CMS Integration - Sanity.io Example
// Uncomment and configure this file if you want to use a headless CMS

/*
========================================
SETUP INSTRUCTIONS FOR SANITY.IO:
========================================

1. Create a Sanity account at https://www.sanity.io/
2. Create a new project
3. Install Sanity CLI: npm install -g @sanity/cli
4. Initialize Sanity: sanity init
5. Create a schema for portfolio items (see schema example below)
6. Get your project ID and dataset name
7. Replace YOUR_PROJECT_ID and YOUR_DATASET below
8. Uncomment the loadPortfolioFromSanity() function
9. In script.js, replace loadPortfolioItems() with loadPortfolioFromSanity()

========================================
SANITY SCHEMA EXAMPLE (portfolio.js):
========================================

export default {
  name: 'portfolio',
  title: 'Portfolio Item',
  type: 'document',
  fields: [
    {
      name: 'title',
      title: 'Title',
      type: 'string',
      validation: Rule => Rule.required()
    },
    {
      name: 'category',
      title: 'Category',
      type: 'string',
      options: {
        list: [
          {title: 'Branding', value: 'branding'},
          {title: 'Digital', value: 'digital'},
          {title: 'Print', value: 'print'}
        ]
      },
      validation: Rule => Rule.required()
    },
    {
      name: 'description',
      title: 'Description',
      type: 'text'
    },
    {
      name: 'image',
      title: 'Image',
      type: 'image',
      options: {
        hotspot: true
      },
      validation: Rule => Rule.required()
    },
    {
      name: 'dateAdded',
      title: 'Date Added',
      type: 'datetime',
      initialValue: () => new Date().toISOString()
    }
  ]
}

========================================
JAVASCRIPT INTEGRATION:
========================================
*/

// Sanity Configuration
const SANITY_CONFIG = {
    projectId: 'YOUR_PROJECT_ID', // Replace with your Sanity project ID
    dataset: 'production', // or 'development'
    apiVersion: '2024-01-01',
    useCdn: true // Use CDN for faster loading
};

// Function to load portfolio from Sanity
async function loadPortfolioFromSanity() {
    try {
        const query = `*[_type == "portfolio"] | order(dateAdded desc) {
            "id": _id,
            title,
            category,
            description,
            "image": image.asset->url,
            dateAdded
        }`;
        
        const url = `https://${SANITY_CONFIG.projectId}.api.sanity.io/v${SANITY_CONFIG.apiVersion}/data/query/${SANITY_CONFIG.dataset}?query=${encodeURIComponent(query)}`;
        
        const response = await fetch(url);
        const data = await response.json();
        
        if (data.result) {
            portfolioItems = data.result;
            renderPortfolio('all');
        }
    } catch (error) {
        console.error('Error loading portfolio from Sanity:', error);
        // Fallback to local data if Sanity fails
        loadPortfolioItems();
    }
}

/*
========================================
OTHER CMS OPTIONS:
========================================

1. CONTENTFUL
   - Website: https://www.contentful.com/
   - Free tier: 25,000 API calls/month
   - Great UI and documentation
   - Similar setup to Sanity

2. STRAPI
   - Website: https://strapi.io/
   - Self-hosted (free)
   - More control, more setup
   - Good for custom needs

3. GITHUB AS CMS
   - Store images in GitHub repo
   - Use GitHub API to fetch data
   - Completely free
   - Version controlled

========================================
BENEFITS OF USING A HEADLESS CMS:
========================================

✅ Easy content updates without coding
✅ Upload images through a dashboard
✅ No need to redeploy for content changes
✅ Better for non-technical users
✅ Automatic image optimization
✅ Version history and rollbacks
✅ Collaboration features

========================================
*/

// Export functions if using modules
if (typeof module !== 'undefined' && module.exports) {
    module.exports = { loadPortfolioFromSanity };
}
