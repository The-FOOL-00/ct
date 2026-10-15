# Full-Stack Web App & Lab Exam Viva Preparation Master Guide

This repository contains all the code, templates, and backend/frontend components required for the Web Application Development (WAD) Lab Examination.

---

## Table of Contents
1. [Workspace Directory & File Breakdown](#1-workspace-directory--file-breakdown)
2. [How to Run Each Module Step-by-Step](#2-how-to-run-each-module-step-by-step)
   - [A. Apache Tomcat Servlets (HelloServlet & DatabaseServlet)](#a-apache-tomcat-servlets-helloservlet--databaseservlet)
   - [B. Spring Boot + MongoDB Backend (ProductManager)](#b-spring-boot--mongodb-backend-productmanager)
   - [C. Vue 3 + Vite Frontend (Modular, Simplified, Monolithic)](#c-vue-3--vite-frontend)
3. [Architecture & Core Concepts for Viva](#3-architecture--core-concepts-for-viva)
4. [Deep Dive: Routing (Vue Router)](#4-deep-dive-routing-vue-router)
5. [Deep Dive: Pinia (State Management)](#5-deep-dive-pinia-state-management)
6. [Deep Dive: Bino Whack-a-Mole Game in Pure JavaScript](#6-deep-dive-bino-whack-a-mole-game-in-pure-javascript)
7. [Comprehensive Viva Questions & Answers](#7-comprehensive-viva-questions--answers)
   - [HTML Viva Prep](#html-viva-prep)
   - [CSS Viva Prep](#css-viva-prep)
   - [JavaScript Viva Prep](#javascript-viva-prep)
   - [Java Servlets & JDBC Viva Prep](#java-servlets--jdbc-viva-prep)
   - [Spring Boot & MongoDB Viva Prep](#spring-boot--mongodb-viva-prep)
   - [Vue 3 & Frontend Architecture Viva Prep](#vue-3--frontend-architecture-viva-prep)

---

## 1. Workspace Directory & File Breakdown

| File / Folder | Type / Stack | Purpose & Contents |
| :--- | :--- | :--- |
| **`HelloServlet/`** | Java Servlet + HTML | Basic HTTP GET Servlet demo. Contains `index.html` (input form), `WEB-INF/web.xml` (servlet mapping), and `WEB-INF/classes/HelloServlet.java` (reads `username` parameter and renders HTML response). |
| **`DatabaseServlet/`** | Java Servlet + JDBC | Database-connected Servlet demo. Queries MariaDB/MySQL database (`company`), reads `Employees` table, and outputs records in a styled HTML table. Includes `mariadb-java-client-3.5.10.jar` in `WEB-INF/lib/`. |
| **`productmanager/`** | Spring Boot 3/4 + MongoDB | Production-ready REST API backend for Product Inventory. Contains entity (`Product`), repository (`ProductRepository`), business service (`ProductService`), and REST controller (`ProductController`) with full CRUD (`GET`, `POST`, `PUT`, `DELETE`). |
| **`src/`** | Vue 3 + Vite | Modular frontend client for Product Manager: `App.vue` (state & API communication), `ProductForm.vue` (input form for Add/Update), `ProductList.vue` (table/cards with Edit & Delete actions), and `main.js`. |
| **`vue1/App.vue`** | Vue 3 SFC | Compact 2-component version of the Product Manager frontend designed for rapid writing in exams. |
| **`combined_app.vue`** | Vue 3 SFC | Single-file monolithic Vue 3 app for Person Management. Demonstrates full CRUD plus "Find by MongoDB ID" in one self-contained component without needing child components. |
| **`studentmanager/`** | Spring Boot + MongoDB + Vue 3 | Multi-entity management system (Students and Courses). Contains models (`Student.java`, `Courses.java`), repositories (`StudentRepository.java`, `CourseRepository.java`), services (`StudentService.java`, `CourseService.java`, `ManagementService.java`), controllers (`ManagementController.java`, `ManagementController1.java`), and a complete Vue 3 frontend (`app.vue`) managing both collections simultaneously. |
| **`servlet.txt`** | Reference Cheatsheet | Quick-reference notes for Tomcat paths, compilation command (`javac -cp ...`), MySQL table creation SQL, and an alternative `EmployeeServlet` updating employee experience. |
| **`vue.txt`** | Reference Cheatsheet | Complete code template for a Vue 3 + Spring Boot + MongoDB Library Management System (`BookForm.vue`, `BookList.vue`, `BookItem.vue`, `bookService.js`). |
| **`vue2.txt`** | Reference Cheatsheet | Alternative Product Manager reference containing both modular and monolithic Vue 3 templates with inline CSS. |
| **`whack_a_mole.html`** | Vanilla HTML + CSS + JS | Complete, runnable Whack-a-Mole game demonstrating DOM events, random timer intervals (`setTimeout`/`setInterval`), and anti-cheat validation (`e.isTrusted`). |

---

## 2. How to Run Each Module Step-by-Step

### A. Apache Tomcat Servlets (HelloServlet & DatabaseServlet)

#### Prerequisites
- Java JDK 17+ installed and `JAVA_HOME` configured.
- Apache Tomcat 9.x or 10.x installed (e.g., at `C:\apache-tomcat-9.0.x`).
- MariaDB or MySQL running on port 3306.

#### Step 1: Set up MariaDB/MySQL Database
Open terminal or MySQL client:
```sql
mysql -u root -p
```
Run the following SQL script:
```sql
CREATE DATABASE IF NOT EXISTS company;
USE company;

CREATE TABLE IF NOT EXISTS Employees (
    id INT PRIMARY KEY,
    age INT,
    first_name VARCHAR(50),
    last_name VARCHAR(50)
);

INSERT INTO Employees (id, age, first_name, last_name) VALUES
    (101, 22, 'Zara', 'Ali'),
    (102, 28, 'Rahul', 'Kumar'),
    (103, 47, 'John', 'Doe');

SELECT * FROM Employees;
```

#### Step 2: Deploy to Tomcat `webapps` Directory
Copy `HelloServlet` and `DatabaseServlet` folders into your Tomcat `webapps` directory:
```
C:\apache-tomcat-9.0.x\webapps\
├── HelloServlet\
│   ├── index.html
│   └── WEB-INF\
│       ├── web.xml
│       └── classes\
│           └── HelloServlet.java (and HelloServlet.class)
└── DatabaseServlet\
    ├── index.html
    └── WEB-INF\
        ├── web.xml
        ├── classes\
        │   └── DatabaseServlet.java (and DatabaseServlet.class)
        └── lib\
            └── mariadb-java-client-3.5.10.jar
```

#### Step 3: Compile the Servlets
Open PowerShell or Command Prompt inside the `classes` folder of each servlet:

For **HelloServlet**:
```powershell
cd C:\apache-tomcat-9.0.x\webapps\HelloServlet\WEB-INF\classes
javac --release 17 -cp "C:\apache-tomcat-9.0.x\lib\servlet-api.jar" HelloServlet.java
```

For **DatabaseServlet**:
```powershell
cd C:\apache-tomcat-9.0.x\webapps\DatabaseServlet\WEB-INF\classes
javac --release 17 -cp "C:\apache-tomcat-9.0.x\lib\servlet-api.jar;..\lib\mariadb-java-client-3.5.10.jar" DatabaseServlet.java
```

#### Step 4: Start Tomcat & Open in Browser
1. Run `C:\apache-tomcat-9.0.x\bin\startup.bat`.
2. Open your browser:
   - Hello Servlet: `http://localhost:8080/HelloServlet/`
   - Database Servlet: `http://localhost:8080/DatabaseServlet/`

---

### B. Spring Boot + MongoDB Backend (ProductManager)

#### Prerequisites
- MongoDB Community Server running locally on `mongodb://localhost:27017`.
- Java 17+.

#### Step 1: Start MongoDB
Start the MongoDB daemon if not already running:
```powershell
mongod --dbpath "C:\data\db"
```
Or ensure the `MongoDB` Windows Service is running (`services.msc`).

#### Step 2: Configure `application.properties`
Location: `productmanager/src/main/resources/application.properties`
```properties
spring.application.name=productmanager
spring.mongodb.uri=mongodb://localhost:27017/wad_lab
server.port=8080
```

#### Step 3: Run the Application
Navigate to the `productmanager` folder:
```powershell
cd c:\ct\ct\productmanager
.\mvnw clean spring-boot:run
```
The server will start on `http://localhost:8080`.

#### Step 4: Test Backend Endpoints
- **Get all products**: `GET http://localhost:8080/api/products`
- **Create product**: `POST http://localhost:8080/api/products`
  ```json
  {
    "name": "Wireless Mouse",
    "category": "Electronics",
    "price": 799.00,
    "stock": 45
  }
  ```
- **Update product**: `PUT http://localhost:8080/api/products/{id}`
- **Delete product**: `DELETE http://localhost:8080/api/products/{id}`

---

### C. Vue 3 + Vite Frontend

#### Step 1: Install Dependencies
Navigate to the project root:
```powershell
cd c:\ct\ct
npm install
```

#### Step 2: Start Vite Dev Server
```powershell
npm run dev
```
Open the URL shown in terminal (typically `http://localhost:5173`).

#### Switching Between App Versions
In `src/main.js`:
- To run the **Standard Modular App**: `import App from './App.vue'`
- To run the **Simplified App**: `import App from '../vue1/App.vue'`
- To run the **Monolithic Person App**: `import App from '../combined_app.vue'`

---

## 3. Architecture & Core Concepts for Viva

### End-to-End Request/Response Lifecycle
```
[ User Browser / Vue 3 Component ]
             │
             ▼ (fetch JSON over HTTP)
[ CORS Filter / WebMvcConfig ]
             │
             ▼
[ @RestController: ProductController ] ──> Maps HTTP Verbs (@GetMapping, @PostMapping)
             │
             ▼
[ @Service: ProductService ]           ──> Business logic, validation, ID handling
             │
             ▼
[ MongoRepository: ProductRepository ] ──> Spring Data abstract queries
             │
             ▼
[ MongoDB Database (wad_lab) ]        ──> JSON BSON document storage
```

### Spring Boot Layering Explained
1. **Model / Entity (`Product.java`)**: Represents data structure mapped to MongoDB collection using `@Document(collection = "products")` and `@Id`.
2. **Repository (`ProductRepository.java`)**: Extends `MongoRepository<Product, String>` providing free out-of-the-box CRUD methods (`findAll`, `findById`, `save`, `deleteById`, `existsById`).
3. **Service Layer (`ProductService.java`)**: Decouples business logic from HTTP transport. Handles optional unpacking, field mutations, and conditional operations.
4. **Controller Layer (`ProductController.java`)**: Exposes REST endpoints, validates inputs, controls HTTP response statuses (`200 OK`, `204 No Content`, `404 Not Found`), and enables CORS via `@CrossOrigin`.

### Vue 3 Composition API Flow
1. **`<script setup>`**: Compiler sugar that makes reactive variables and functions directly available to the template without `return {}`.
2. **`ref()`**: Wraps primitive or object values in a reactive Proxy object accessible via `.value` in JavaScript and auto-unwrapped in `<template>`.
3. **`defineProps()`**: Declares data passed from Parent to Child.
4. **`defineEmits()`**: Declares events fired from Child to Parent.
5. **`onMounted()`**: Lifecycle hook triggered after initial DOM mounting, used for fetching initial data from backend.

---

## 4. Deep Dive: Routing (Vue Router)

### What is Client-Side Routing?
In a traditional Multi-Page Application (MPA), clicking a link causes the browser to request a brand new HTML page from the server, causing a blank screen flash.
In a Single-Page Application (SPA) using Vue Router:
- The browser downloads one single HTML page (`index.html`).
- Vue Router intercepts navigation events using the **HTML5 History API** (`pushState` / `replaceState`).
- The URL changes without reloading the page, and Vue swaps out components inside `<router-view>`.

### Setting up Vue Router
Install:
```bash
npm install vue-router@4
```

Create `src/router/index.js`:
```javascript
import { createRouter, createWebHistory } from 'vue-router'
import ProductList from '../components/ProductList.vue'
import ProductForm from '../components/ProductForm.vue'
import ProductDetail from '../components/ProductDetail.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: ProductList
  },
  {
    path: '/add',
    name: 'AddProduct',
    component: ProductForm
  },
  {
    path: '/product/:id',
    name: 'ProductDetail',
    component: ProductDetail,
    props: true // Passes route.params as props to the component
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
```

In `src/main.js`:
```javascript
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(router)
app.mount('#app')
```

### Essential Router Directives & Methods

1. **`<router-link to="/path">`**: Replaces standard `<a href="...">` to prevent full-page refreshes.
   ```html
   <router-link to="/add">Add New Product</router-link>
   <router-link :to="{ name: 'ProductDetail', params: { id: product.id } }">View</router-link>
   ```

2. **`<router-view />`**: Outlet where the matched component is dynamically rendered.

3. **Programmatic Navigation (`useRouter`)**:
   ```javascript
   import { useRouter } from 'vue-router'
   const router = useRouter()

   function goToHome() {
     router.push('/')
     // or router.replace('/login')
     // or router.go(-1)
   }
   ```

4. **Reading Route Parameters (`useRoute`)**:
   ```javascript
   import { useRoute } from 'vue-router'
   const route = useRoute()
   console.log(route.params.id)     // /product/123 -> "123"
   console.log(route.query.search)  // /product?search=phone -> "phone"
   ```

5. **Navigation Guards (Authentication / Authorization)**:
   ```javascript
   router.beforeEach((to, from, next) => {
     const isAuthenticated = !!localStorage.getItem('token')
     if (to.meta.requiresAuth && !isAuthenticated) {
       next('/login')
     } else {
       next()
     }
   })
   ```

---

## 5. Deep Dive: Pinia (State Management)

### Why Pinia?
In large applications, passing state down through multiple layers of components (**Prop Drilling**) and emitting events back up becomes unmaintainable. Pinia provides a **centralized reactive store** accessible anywhere in the component tree.

### Pinia vs Vuex
- **No Mutations**: Directly mutate state without boilerplate commits.
- **Full TypeScript & Composition API Support**.
- **Modular by default**: Multiple stores (`useProductStore`, `useUserStore`) instead of one giant nested tree.

### Complete Working Pinia Example

#### Step 1: Install & Register
```bash
npm install pinia
```
In `src/main.js`:
```javascript
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'

const app = createApp(App)
app.use(createPinia())
app.mount('#app')
```

#### Step 2: Define Store (`src/stores/productStore.js`)
```javascript
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useProductStore = defineStore('products', () => {
  // 1. STATE
  const products = ref([])
  const loading = ref(false)
  const error = ref(null)

  // 2. GETTERS (Computed values)
  const totalProducts = computed(() => products.value.length)
  const inStockProducts = computed(() => products.value.filter(p => p.stock > 0))

  // 3. ACTIONS (Methods + Async API calls)
  async function fetchProducts() {
    loading.value = true
    try {
      const res = await fetch('http://localhost:8080/api/products')
      products.value = await res.json()
    } catch (err) {
      error.value = 'Failed to load products'
    } finally {
      loading.value = false
    }
  }

  async function addProduct(newProduct) {
    const res = await fetch('http://localhost:8080/api/products', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newProduct)
    })
    const saved = await res.json()
    products.value.push(saved)
  }

  return { products, loading, error, totalProducts, inStockProducts, fetchProducts, addProduct }
})
```

#### Step 3: Using Store in a Component
```html
<script setup>
import { onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { useProductStore } from '../stores/productStore'

const store = useProductStore()
// Use storeToRefs to destructure reactive state/getters without losing reactivity
const { products, loading, totalProducts } = storeToRefs(store)
const { fetchProducts } = store

onMounted(() => {
  fetchProducts()
})
</script>

<template>
  <div>
    <h2>Total Products: {{ totalProducts }}</h2>
    <p v-if="loading">Loading...</p>
    <ul>
      <li v-for="p in products" :key="p.id">{{ p.name }} - ₹{{ p.price }}</li>
    </ul>
  </div>
</template>
```

---

## 6. Deep Dive: Bino Whack-a-Mole Game in Pure JavaScript

This is a classic web programming examination question testing DOM manipulation, JavaScript timers (`setInterval`, `setTimeout`), math functions, and event handling.

### Complete Self-Contained Source Code (`whack_a_mole.html`)

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Bino Whack-a-Mole Game</title>
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      text-align: center;
      background: #f4f7f6;
      margin: 0;
      padding: 20px;
    }
    h1 {
      color: #2c3e50;
      margin-bottom: 5px;
    }
    .score-board {
      font-size: 24px;
      margin-bottom: 15px;
      font-weight: bold;
      color: #34495e;
    }
    .controls {
      margin-bottom: 20px;
    }
    button {
      padding: 10px 20px;
      font-size: 16px;
      background: #27ae60;
      color: white;
      border: none;
      border-radius: 6px;
      cursor: pointer;
      font-weight: 600;
      transition: background 0.2s;
    }
    button:hover {
      background: #219150;
    }
    /* 3x3 Game Board Grid */
    .game-grid {
      width: 480px;
      height: 480px;
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      grid-gap: 20px;
      margin: 0 auto;
    }
    .hole {
      width: 140px;
      height: 140px;
      background: #8b5a2b;
      border-radius: 50%;
      position: relative;
      overflow: hidden;
      box-shadow: inset 0 10px 0 0 #5c3a1e;
    }
    /* The Mole */
    .mole {
      width: 100px;
      height: 100px;
      background: #d35400;
      border-radius: 50% 50% 20% 20%;
      position: absolute;
      top: 100%; /* Hidden underneath the hole */
      left: 20px;
      transition: top 0.2s ease-in-out;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 32px;
      user-select: none;
    }
    /* Mole popped up state */
    .hole.up .mole {
      top: 20px;
    }
  </style>
</head>
<body>

  <h1>Bino Whack-a-Mole!</h1>
  <div class="score-board">
    Score: <span id="score">0</span> | Time Left: <span id="time-left">30</span>s
  </div>

  <div class="controls">
    <button id="start-btn" onclick="startGame()">Start Game</button>
  </div>

  <div class="game-grid">
    <div class="hole hole1"><div class="mole" onclick="whack(event)">🐹</div></div>
    <div class="hole hole2"><div class="mole" onclick="whack(event)">🐹</div></div>
    <div class="hole hole3"><div class="mole" onclick="whack(event)">🐹</div></div>
    <div class="hole hole4"><div class="mole" onclick="whack(event)">🐹</div></div>
    <div class="hole hole5"><div class="mole" onclick="whack(event)">🐹</div></div>
    <div class="hole hole6"><div class="mole" onclick="whack(event)">🐹</div></div>
    <div class="hole hole7"><div class="mole" onclick="whack(event)">🐹</div></div>
    <div class="hole hole8"><div class="mole" onclick="whack(event)">🐹</div></div>
    <div class="hole hole9"><div class="mole" onclick="whack(event)">🐹</div></div>
  </div>

  <script>
    const holes = document.querySelectorAll('.hole');
    const scoreBoard = document.getElementById('score');
    const timeLeftBoard = document.getElementById('time-left');
    const startBtn = document.getElementById('start-btn');

    let lastHole;
    let timeUp = false;
    let score = 0;
    let countdown = 30;
    let timerId;

    // Helper: Generate random time for mole appearance (in milliseconds)
    function randomTime(min, max) {
      return Math.round(Math.random() * (max - min) + min);
    }

    // Helper: Select a random hole without picking the same one consecutively
    function randomHole(holes) {
      const idx = Math.floor(Math.random() * holes.length);
      const hole = holes[idx];
      if (hole === lastHole) {
        return randomHole(holes);
      }
      lastHole = hole;
      return hole;
    }

    // Mole popping logic
    function peep() {
      const time = randomTime(600, 1200); // Popup duration
      const hole = randomHole(holes);
      hole.classList.add('up');

      setTimeout(() => {
        hole.classList.remove('up');
        if (!timeUp) peep(); // Recursively trigger next mole until game is over
      }, time);
    }

    // Start game initialization
    function startGame() {
      score = 0;
      countdown = 30;
      timeUp = false;
      scoreBoard.textContent = 0;
      timeLeftBoard.textContent = countdown;
      startBtn.disabled = true;

      peep();

      // Countdown timer loop
      timerId = setInterval(() => {
        countdown--;
        timeLeftBoard.textContent = countdown;
        if (countdown <= 0) {
          clearInterval(timerId);
          timeUp = true;
          startBtn.disabled = false;
          alert('Game Over! Your final score is: ' + score);
        }
      }, 1000);
    }

    // Click/Whack handler
    function whack(e) {
      // Security check: ensure click was genuine user event, not an automated script
      if (!e.isTrusted) return;

      score++;
      // Hide the mole immediately once whacked
      e.target.parentElement.classList.remove('up');
      scoreBoard.textContent = score;
    }
  </script>
</body>
</html>
```

### Whack-a-Mole Game Viva Concepts
1. **How is the Mole hidden and shown?**
   - The `.hole` has `overflow: hidden; position: relative`.
   - The `.mole` is styled with `position: absolute; top: 100%`, which places it outside the visible boundary of the hole.
   - When the class `.up` is toggled on `.hole`, `.hole.up .mole` changes `top: 20px`, sliding the mole into view.
2. **Why is `e.isTrusted` checked?**
   - In JavaScript, automated bots or cheating scripts can call `mole.click()`. `e.isTrusted` evaluates to `true` **only** if the event was generated by genuine user physical interaction.
3. **What is the difference between `setTimeout` and `setInterval` in this game?**
   - `setTimeout` triggers a function **once** after a designated delay (used for random mole visibility duration).
   - `setInterval` triggers a function **repeatedly** at fixed intervals (used for the 1-second game countdown clock).
4. **How do you avoid memory leaks with timers?**
   - Store timer IDs in variables and call `clearInterval(timerId)` / `clearTimeout(timerId)` when the game finishes or the component unmounts.

---

## 7. Comprehensive Viva Questions & Answers

---

### HTML Viva Prep

#### Q1: What does `<!DOCTYPE html>` mean?
**Answer**: It is a document type declaration that tells the browser which HTML version to use. In HTML5, `<!DOCTYPE html>` enables **standards mode** in all modern browsers and prevents **quirks mode** rendering.

#### Q2: What are Semantic HTML5 tags and why are they important?
**Answer**: Semantic tags clearly describe their meaning to both the browser and the developer (e.g., `<header>`, `<nav>`, `<main>`, `<section>`, `<article>`, `<footer>`, `<aside>`).
Benefits:
1. **Accessibility**: Screen readers can navigate page landmarks.
2. **SEO**: Search engine crawlers understand content structure and weight.
3. **Maintainability**: Cleaner code compared to endless generic `<div>` tags ("div soup").

#### Q3: Difference between Block-level and Inline elements?
**Answer**:
- **Block-level elements** (e.g., `<div>`, `<p>`, `<h1>`-`<h6>`, `<form>`, `<ul>`, `<header>`): Always start on a new line and stretch to fill the full available width of their container. They respect `width`, `height`, `margin`, and `padding`.
- **Inline elements** (e.g., `<span>`, `<a>`, `<strong>`, `<em>`, `<img>`): Do not start on a new line; they occupy only the width necessary for their content. Top and bottom margins/paddings do not affect vertical layout.

#### Q4: What is the difference between `localStorage`, `sessionStorage`, and `Cookies`?
| Feature | `localStorage` | `sessionStorage` | Cookies |
| :--- | :--- | :--- | :--- |
| **Capacity** | ~5-10 MB | ~5 MB | ~4 KB |
| **Expiration** | Persistent until deleted | Cleared on tab/window close | Configurable (`Expires`/`Max-Age`) |
| **Sent to Server** | No (Client-side only) | No (Client-side only) | Yes (Sent with every HTTP request) |
| **Accessibility** | Any window/tab of same origin | Same tab only | Any window/tab, supports `HttpOnly` |

#### Q5: What are `async` and `defer` attributes in `<script>` tags?
**Answer**:
- **Standard `<script>`**: HTML parsing halts while the script is downloaded and executed immediately.
- **`<script async>`**: The script downloads in parallel with HTML parsing and executes the instant it finishes downloading, pausing HTML parsing. Script execution order is not guaranteed.
- **`<script defer>`**: The script downloads in parallel with HTML parsing, but **defers execution** until HTML parsing is completely finished, preserving script execution order.

---

### CSS Viva Prep

#### Q1: Explain the CSS Box Model.
**Answer**: Every element in CSS is represented as a rectangular box consisting of 4 concentric layers:
1. **Content**: Where text and images appear.
2. **Padding**: Transparent space clearing the area around content (inside the border).
3. **Border**: Surrounds the padding and content.
4. **Margin**: Transparent space outside the border separating the element from surrounding elements.

#### Q2: What is the difference between `box-sizing: content-box` and `box-sizing: border-box`?
**Answer**:
- **`content-box` (Default)**: The specified `width` and `height` apply only to the content. Adding padding or borders increases the total rendered size (`Total Width = width + padding + border`).
- **`border-box` (Recommended)**: The specified `width` and `height` include content, padding, and border. Adding padding shrinks the content area instead of expanding the element box, making responsive layouts predictable.

#### Q3: Explain CSS Specificity and how it is calculated.
**Answer**: Specificity determines which CSS rule applies when multiple rules match the same element. It follows a 4-tier hierarchy:
1. **Inline styles** (e.g., `style="..."`): Weight 1,0,0,0
2. **IDs** (e.g., `#header`): Weight 0,1,0,0
3. **Classes, Attributes, and Pseudo-classes** (e.g., `.btn`, `[type="text"]`, `:hover`): Weight 0,0,1,0
4. **Elements and Pseudo-elements** (e.g., `div`, `p`, `::before`): Weight 0,0,0,1
*(Note: `!important` overrides normal specificity entirely).*

#### Q4: Compare Flexbox and CSS Grid.
**Answer**:
- **Flexbox**: **One-dimensional** layout system (deals with either a single row or a single column at a time). Best for components, navbars, and aligning items inside containers.
- **CSS Grid**: **Two-dimensional** layout system (handles rows and columns simultaneously). Best for overall page structure, dashboards, and complex grid matrices.

#### Q5: Explain CSS `position` values: `static`, `relative`, `absolute`, `fixed`, and `sticky`.
**Answer**:
- **`static` (default)**: Follows normal document flow; `top`, `right`, `bottom`, `left`, and `z-index` have no effect.
- **`relative`**: Positioned relative to its normal position in the document flow without disturbing surrounding elements.
- **`absolute`**: Removed from document flow and positioned relative to its nearest non-static ancestor.
- **`fixed`**: Removed from document flow and positioned relative to the browser viewport; stays visible during scrolling.
- **`sticky`**: Hybrid of `relative` and `fixed`. Behaves relatively until a specified scroll threshold is reached, then sticks in place.

---

### JavaScript Viva Prep

#### Q1: What is the Event Loop and how does JS handle asynchronous operations?
**Answer**: JavaScript is **single-threaded** with a single call stack. The browser runtime provides Web APIs (timers, fetch, DOM events).
Workflow:
1. Synchronous code executes immediately on the **Call Stack**.
2. Asynchronous tasks (e.g., `setTimeout`, `fetch`) are offloaded to **Web APIs**.
3. When complete, their callbacks are queued in:
   - **Microtask Queue**: Promises, `async/await`, `queueMicrotask` (high priority).
   - **Macrotask Queue**: `setTimeout`, `setInterval`, I/O events.
4. When the Call Stack is empty, the **Event Loop** drains the entire Microtask Queue first, then picks the oldest callback from the Macrotask Queue.

#### Q2: Compare `var`, `let`, and `const`.
| Feature | `var` | `let` | `const` |
| :--- | :--- | :--- | :--- |
| **Scope** | Function scope | Block scope `{}` | Block scope `{}` |
| **Hoisting** | Hoisted and initialized to `undefined` | Hoisted into Temporal Dead Zone (TDZ) | Hoisted into TDZ |
| **Re-declaration** | Allowed | Not allowed | Not allowed |
| **Re-assignment** | Allowed | Allowed | Not allowed |

#### Q3: What is a Closure in JavaScript?
**Answer**: A closure is a function that retains access to its lexical scope (variables of its outer enclosing function) even after the outer function has finished executing and returned.
```javascript
function makeCounter() {
  let count = 0;
  return function() {
    return ++count;
  };
}
const counter = makeCounter();
console.log(counter()); // 1
console.log(counter()); // 2
```
*Use cases: Data privacy/encapsulation, currying, factory functions.*

#### Q4: What is the difference between `==` and `===`?
**Answer**:
- `==` (**Abstract Equality**): Compares values after performing automatic type coercion (e.g., `'5' == 5` is `true`, `null == undefined` is `true`).
- `===` (**Strict Equality**): Compares both value and data type without coercion (e.g., `'5' === 5` is `false`). Always prefer `===`.

#### Q5: Explain Event Bubbling vs Event Capturing.
**Answer**: When an event occurs on an element inside nested containers:
1. **Capturing phase (Trickling)**: Event travels from the window down through ancestors to the target element.
2. **Target phase**: The event triggers on the target element.
3. **Bubbling phase (Default)**: The event bubbles up from the target element through all its parent ancestors to the window.
- `e.stopPropagation()` stops the event from propagating further up or down the DOM tree.
- `e.preventDefault()` suppresses default browser behavior (e.g., submitting a form or following a hyperlink).

---

### Java Servlets & JDBC Viva Prep

#### Q1: What is a Servlet and what is its Lifecycle?
**Answer**: A Servlet is a server-side Java class that runs inside a Web Container (e.g., Apache Tomcat) to handle incoming HTTP requests and generate dynamic responses.
Lifecycle:
1. **Loading & Instantiation**: Servlet class is loaded into memory and an instance is created.
2. **Initialization (`init()`)**: Executed once when the servlet is first instantiated to initialize resources (e.g., database connection pools).
3. **Request Handling (`service()`)**: Invoked for every incoming request. The `service()` method determines the HTTP verb and delegates to `doGet()`, `doPost()`, `doPut()`, or `doDelete()`.
4. **Destruction (`destroy()`)**: Executed once when the container is shutting down to release resources.
5. **Garbage Collection**: Unloads from JVM memory.

#### Q2: What is the role of `web.xml` (Deployment Descriptor)?
**Answer**: `web.xml` configures how the web container maps incoming URL requests to specific servlet classes:
```xml
<servlet>
    <servlet-name>HelloServlet</servlet-name>
    <servlet-class>HelloServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>HelloServlet</servlet-name>
    <url-pattern>/hello</url-pattern>
</servlet-mapping>
```
*Note: In Servlet 3.0+, XML configuration can be replaced by the `@WebServlet("/hello")` annotation.*

#### Q3: Difference between `HttpServletRequest` and `HttpServletResponse`?
**Answer**:
- **`HttpServletRequest`**: Encapsulates incoming client request data: parameters (`getParameter("username")`), headers, cookies, HTTP method, and session object.
- **`HttpServletResponse`**: Formats and transmits server response back to client: sets MIME content type (`setContentType("text/html")`), status codes (`200`, `404`), headers, and provides character stream output (`response.getWriter()`).

#### Q4: What is the difference between `Statement` and `PreparedStatement` in JDBC?
**Answer**:
- **`Statement`**: Compiles and executes SQL queries at runtime. Slower for repeated executions and vulnerable to **SQL Injection** attacks if concatenated with user input.
- **`PreparedStatement`**: Pre-compiles SQL template with parameter placeholders (`?`). Parameters are parameterized safely, preventing SQL Injection, and compiled plans are cached in the database for better performance.

#### Q5: What are the essential steps to connect to a Database using JDBC?
**Answer**:
1. Load JDBC driver class: `Class.forName("org.mariadb.jdbc.Driver");`
2. Establish connection: `Connection con = DriverManager.getConnection(url, user, pass);`
3. Create Statement object: `Statement stmt = con.createStatement();`
4. Execute SQL query: `ResultSet rs = stmt.executeQuery("SELECT * FROM Employees");`
5. Process ResultSet: `while (rs.next()) { ... }`
6. Close resources in reverse order or use **try-with-resources** to avoid memory leaks: `rs.close()`, `stmt.close()`, `con.close()`.

---

### Spring Boot & MongoDB Viva Prep

#### Q1: What is Spring Boot and how does it differ from traditional Spring?
**Answer**: Spring Boot is an opinionated extension of the Spring framework designed to simplify application setup:
1. **Auto-configuration**: Automatically configures beans based on classpath JARs.
2. **Starter Dependencies (`spring-boot-starter-*`)**: Bundles common dependencies into one coordinate.
3. **Embedded Server**: Includes embedded Tomcat/Jetty, avoiding manual WAR deployment.
4. **No XML configuration required**.

#### Q2: What does `@SpringBootApplication` do?
**Answer**: It is a composite annotation combining three essential annotations:
1. **`@Configuration`**: Tags the class as a source of bean definitions for application context.
2. **`@EnableAutoConfiguration`**: Instructs Spring Boot to automatically configure Spring application based on dependencies.
3. **`@ComponentScan`**: Enables auto-scanning of components, services, and repositories in the package where the main class resides.

#### Q3: Difference between `@Controller` and `@RestController`?
**Answer**:
- **`@Controller`**: Standard Spring MVC controller used for rendering web views (JSP, Thymeleaf). Handlers return view names as Strings.
- **`@RestController`**: Convenience annotation combining `@Controller` and `@ResponseBody`. Method return objects are automatically serialized directly into JSON/XML HTTP responses.

#### Q4: Explain the differences between `@PathVariable`, `@RequestParam`, and `@RequestBody`.
**Answer**:
- **`@PathVariable`**: Extracts values directly from the URI path segment:
  `GET /api/products/{id}` -> `@GetMapping("/{id}") public Product get(@PathVariable String id)`
- **`@RequestParam`**: Extracts query parameters or form fields:
  `GET /api/products?category=tech` -> `public List<Product> get(@RequestParam String category)`
- **`@RequestBody`**: Deserializes incoming JSON/XML request payload into a Java POJO:
  `POST /api/products` -> `public Product add(@RequestBody Product product)`

#### Q5: What is CORS and how is it resolved in Spring Boot?
**Answer**: **Cross-Origin Resource Sharing (CORS)** is a browser security mechanism that blocks web pages running at one origin (e.g., `http://localhost:5173`) from making requests to an API at a different origin (e.g., `http://localhost:8080`).
Resolution in Spring Boot:
- Controller level: `@CrossOrigin(origins = "http://localhost:5173")`
- Global configuration: Implementing `WebMvcConfigurer` and adding `addCorsMappings()`.

#### Q6: How does Spring Data MongoDB interact with MongoDB?
**Answer**:
- Maps Java classes annotated with `@Document` to MongoDB collections.
- Translates Java property annotated with `@Id` to MongoDB `_id` field.
- Provides `MongoRepository<Entity, IDType>` interface containing pre-built CRUD methods without writing queries manually.

---

### Vue 3 & Frontend Architecture Viva Prep

#### Q1: What are the main differences between Vue 2 and Vue 3?
**Answer**:
1. **Composition API (`<script setup>`)**: Replaces Options API (`data`, `methods`, `computed`) for cleaner code organization and reuse.
2. **Reactivity Engine**: Uses modern ES6 **`Proxy`** instead of Vue 2's `Object.defineProperty`, enabling native detection of property additions/deletions and array index updates.
3. **Performance**: Smaller bundle size, faster virtual DOM diffing with compiler optimizations.
4. **Multiple Root Nodes (Fragments)**: Templates no longer require a single wrapping parent `<div>`.

#### Q2: What is the difference between `ref()` and `reactive()` in Vue 3?
**Answer**:
- **`ref()`**: Works on both primitive types (String, Number, Boolean) and Objects. Returns a reactive reference. In JS code, values must be accessed/modified with `.value`. In `<template>`, it auto-unwraps without `.value`.
- **`reactive()`**: Works **only** with Objects/Arrays. Returns a reactive Proxy directly. Does not require `.value`.
*(Note: Destructuring a `reactive()` object breaks its reactivity unless wrapped in `toRefs()`!)*

#### Q3: Difference between `v-if` and `v-show`?
**Answer**:
- **`v-if`**: Conditional rendering. If false, the element is **completely removed from the DOM**. Has higher toggle costs. Best for conditions that rarely change.
- **`v-show`**: Conditional display. The element is **always rendered in the DOM**, and toggled using the CSS `display: none;` property. Has higher initial render cost but lower toggle cost. Best for elements toggled frequently.

#### Q4: Why is `:key` mandatory in `v-for`?
**Answer**: The `:key` attribute provides a unique identifier for each DOM node in a list. When the list changes, Vue's Virtual DOM diffing algorithm uses keys to identify which elements were added, removed, or moved rather than re-rendering the entire list from scratch, guaranteeing correct state preservation and maximum performance.

#### Q5: How do components communicate in Vue 3?
**Answer**:
1. **Parent to Child**: Passed via **Props** (`defineProps({ product: Object })`).
2. **Child to Parent**: Communicated via **Custom Events** (`defineEmits(['save'])` and `emit('save', data)`).
3. **Deeply Nested Components**: Provide / Inject API.
4. **Global Cross-Component State**: State management library (**Pinia**).
