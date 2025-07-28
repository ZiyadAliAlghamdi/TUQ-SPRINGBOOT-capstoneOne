const { default: fetch } = require('node-fetch');

const BASE_URL = 'http://localhost:8080/api/v1';

const categories = [
    {"id": "1", "name": "Electronics"},
    {"id": "2", "name": "Books"},
    {"id": "3", "name": "Clothing"},
    {"id": "4", "name": "HomeGoods"},
    {"id": "5", "name": "Sports"},
    {"id": "6", "name": "Food"},
    {"id": "7", "name": "Beauty"},
    {"id": "8", "name": "Toys"},
    {"id": "9", "name": "Automotive"},
    {"id": "10", "name": "Garden"}
];

const merchants = [
    {"id": "101", "name": "TechMart"},
    {"id": "102", "name": "Bookworm"},
    {"id": "103", "name": "FashionHub"},
    {"id": "104", "name": "HomeDecor"},
    {"id": "105", "name": "SportingGoods"},
    {"id": "106", "name": "GourmetFoods"},
    {"id": "107", "name": "BeautyEssentials"},
    {"id": "108", "name": "ToyLand"},
    {"id": "109", "name": "AutoParts"},
    {"id": "110", "name": "GreenThumb"}
];

const products = [
    {"id": "201", "name": "Laptop", "price": 1200.00, "categoryId": "1", "discountCode": "null", "discountPercentage": 0, "numberOfBuyers": 50},
    {"id": "202", "name": "Novel", "price": 15.50, "categoryId": "2", "discountCode": "SUMMER20", "discountPercentage": 20, "numberOfBuyers": 120},
    {"id": "203", "name": "T-Shirt", "price": 25.00, "categoryId": "3", "discountCode": "null", "discountPercentage": 0, "numberOfBuyers": 200},
    {"id": "204", "name": "Coffee Maker", "price": 75.99, "categoryId": "4", "discountCode": "SAVE10", "discountPercentage": 10, "numberOfBuyers": 80},
    {"id": "205", "name": "Basketball", "price": 30.00, "categoryId": "5", "discountCode": "null", "discountPercentage": 0, "numberOfBuyers": 90},
    {"id": "206", "name": "Organic Apples", "price": 5.99, "categoryId": "6", "discountCode": "FRESH", "discountPercentage": 5, "numberOfBuyers": 300},
    {"id": "207", "name": "Face Cream", "price": 45.00, "categoryId": "7", "discountCode": "BEAUTY15", "discountPercentage": 15, "numberOfBuyers": 150},
    {"id": "208", "name": "Building Blocks", "price": 20.00, "categoryId": "8", "discountCode": "null", "discountPercentage": 0, "numberOfBuyers": 180},
    {"id": "209", "name": "Car Wax", "price": 18.75, "categoryId": "9", "discountCode": "CARCARE", "discountPercentage": 10, "numberOfBuyers": 70},
    {"id": "210", "name": "Garden Hose", "price": 35.00, "categoryId": "10", "discountCode": "null", "discountPercentage": 0, "numberOfBuyers": 60}
];

const merchantStocks = [
    {"id": "301", "productID": "201", "merchantID": "101", "stock": 50},
    {"id": "302", "productID": "202", "merchantID": "102", "stock": 100},
    {"id": "303", "productID": "203", "merchantID": "103", "stock": 200},
    {"id": "304", "productID": "204", "merchantID": "104", "stock": 75},
    {"id": "305", "productID": "205", "merchantID": "105", "stock": 120},
    {"id": "306", "productID": "206", "merchantID": "106", "stock": 300},
    {"id": "307", "productID": "207", "merchantID": "107", "stock": 150},
    {"id": "308", "productID": "208", "merchantID": "108", "stock": 180},
    {"id": "309", "productID": "209", "merchantID": "109", "stock": 90},
    {"id": "310", "productID": "210", "merchantID": "110", "stock": 60}
];

const users = [
    {"id": "401", "username": "userOne", "password": "Password123!", "email": "user1@example.com", "role": "Customer", "balance": 1000.00, "loyaltyPoint": 10},
    {"id": "402", "username": "adminUser", "password": "AdminPass!1", "email": "admin@example.com", "role": "Admin", "balance": 5000.00, "loyaltyPoint": 50},
    {"id": "403", "username": "customerTwo", "password": "MyPass456$", "email": "user2@example.com", "role": "Customer", "balance": 750.50, "loyaltyPoint": 5},
    {"id": "404", "username": "testUser", "password": "TestUser789#", "email": "test@example.com", "role": "Customer", "balance": 200.00, "loyaltyPoint": 2},
    {"id": "405", "username": "anotherAdmin", "password": "SecureAdmin!2", "email": "admin2@example.com", "role": "Admin", "balance": 10000.00, "loyaltyPoint": 100},
    {"id": "406", "username": "shoppingFan", "password": "ShopTillYouDrop!1", "email": "shop@example.com", "role": "Customer", "balance": 300.00, "loyaltyPoint": 3},
    {"id": "407", "username": "techGeek", "password": "TechyPass!23", "email": "tech@example.com", "role": "Customer", "balance": 1500.00, "loyaltyPoint": 15},
    {"id": "408", "username": "bookLover", "password": "Bookworm!45", "email": "books@example.com", "role": "Customer", "balance": 50.00, "loyaltyPoint": 0},
    {"id": "409", "username": "fashionista", "password": "Fashion!678", "email": "fashion@example.com", "role": "Customer", "balance": 800.00, "loyaltyPoint": 8},
    {"id": "410", "username": "homeMaker", "password": "HomeSweetHome!9", "email": "home@example.com", "role": "Customer", "balance": 400.00, "loyaltyPoint": 4}
];

async function postData(endpoint, dataArray) {
    console.log(`--- Inserting data for ${endpoint} ---`);
    for (const data of dataArray) {
        try {
            const response = await fetch(`${BASE_URL}/${endpoint}/post`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            const result = await response.json();
            if (response.ok) {
                console.log(`Successfully added ${endpoint} with ID ${data.id || data.username}:`, result);
            } else {
                console.error(`Failed to add ${endpoint} with ID ${data.id || data.username}:`, result);
            }
        } catch (error) {
            console.error(`Error adding ${endpoint} with ID ${data.id || data.username}:`, error.message);
        }
    }
    console.log(`--- Finished inserting data for ${endpoint} ---\n`);
}

async function insertAllData() {
    // Order matters due to foreign key relationships
    await postData('category', categories);
    await postData('merchant', merchants);
    await postData('product', products); // Products depend on Categories
    await postData('merchantStock', merchantStocks); // MerchantStock depends on Products and Merchants
    await postData('user', users);
    console.log("All data insertion attempts completed.");
}

insertAllData();