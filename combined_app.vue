
<script setup>
import { ref, onMounted } from 'vue'

const API = 'http://localhost:8080/api/products'

const products = ref([])
const message = ref('')

function emptyForm() {
  return {
    id: null,
    name: '',
    category: 'Electronics',
    price: '',
    stock: ''
  }
}

const form = ref(emptyForm())

// READ
async function getProducts() {
  const res = await fetch(API)

  if (!res.ok) throw new Error()

  products.value = await res.json()
}

// CREATE and UPDATE
async function saveProduct() {
  const editing = form.value.id !== null

  const product = {
    name: form.value.name,
    category: form.value.category,
    price: Number(form.value.price),
    stock: Number(form.value.stock)
  }

  try {
    const res = await fetch(
      editing ? `${API}/${form.value.id}` : API,
      {
        method: editing ? 'PUT' : 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(product)
      }
    )

    if (!res.ok) throw new Error()

    await getProducts()
    form.value = emptyForm()
    message.value = editing ? 'Product updated!' : 'Product added!'
  } catch {
    message.value = 'Operation failed!'
  }
}

// EDIT
function editProduct(product) {
  form.value = { ...product }
}

// DELETE
async function deleteProduct(product) {
  if (!confirm('Delete ' + product.name + '?')) return

  try {
    const res = await fetch(`${API}/${product.id}`, {
      method: 'DELETE'
    })

    if (!res.ok) throw new Error()

    await getProducts()
    message.value = 'Product deleted!'
  } catch {
    message.value = 'Delete failed!'
  }
}

// RESET FORM
function resetForm() {
  form.value = emptyForm()
}

onMounted(() => {
  getProducts().catch(() => {
    message.value = 'Cannot connect to Spring Boot!'
  })
})
</script>

<template>
  <header>
    <h1>Product Manager</h1>
    <p>Simple Inventory Management</p>
  </header>

  <main>
    <section class="panel">
      <h2>{{ form.id ? 'Edit Product' : 'Add Product' }}</h2>

      <form @submit.prevent="saveProduct">
        <label>Product Name</label>
        <input v-model="form.name" required />

        <label>Category</label>
        <select v-model="form.category">
          <option>Electronics</option>
          <option>Accessories</option>
          <option>Stationery</option>
          <option>Other</option>
        </select>

        <label>Price</label>
        <input type="number" min="0" step="0.01"
               v-model="form.price" required />

        <label>Stock</label>
        <input type="number" min="0" step="1"
               v-model="form.stock" required />

        <button type="submit">
          {{ form.id ? 'Update Product' : 'Add Product' }}
        </button>

        <button type="button" class="secondary" @click="resetForm">
          Clear
        </button>
      </form>
    </section>

    <section class="panel">
      <h2>Product List ({{ products.length }})</h2>

      <p v-if="products.length === 0">No products found.</p>

      <div v-for="p in products" :key="p.id" class="product">
        <div>
          <h3>{{ p.name }}</h3>
          <p>{{ p.category }} | Stock: {{ p.stock }}</p>
          <strong>₹{{ p.price }}</strong>
        </div>

        <div>
          <button class="edit" @click="editProduct(p)">Edit</button>
          <button class="delete" @click="deleteProduct(p)">Delete</button>
        </div>
      </div>
    </section>

    <p class="message">{{ message }}</p>
  </main>
</template>

<style>
* {
  box-sizing: border-box;
}

body {
  margin: 0;
  font-family: Arial, sans-serif;
  background: #f2f3fa;
  color: #292b45;
}

header {
  text-align: center;
  padding: 25px;
  color: white;
  background: linear-gradient(120deg, #292b50, #6b5bd2);
}

header h1 {
  margin-bottom: 8px;
}

main {
  max-width: 850px;
  margin: 30px auto;
  padding: 0 18px;
}

.panel {
  background: white;
  padding: 22px;
  margin-bottom: 22px;
  border-radius: 14px;
  box-shadow: 0 4px 15px #22224412;
}

label {
  display: block;
  margin: 12px 0 6px;
  font-weight: bold;
  font-size: 13px;
}

input, select {
  width: 100%;
  padding: 11px;
  border: 1px solid #d5d7e5;
  border-radius: 7px;
}

button {
  padding: 9px 13px;
  margin: 8px 4px 0 0;
  border: none;
  border-radius: 7px;
  background: #6254d6;
  color: white;
  cursor: pointer;
}

button:hover {
  opacity: 0.85;
}

.secondary, .edit {
  background: #eae7ff;
  color: #5145b5;
}

.delete {
  background: #ffedf0;
  color: #bb304a;
}

.product {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  padding: 14px 0;
  border-bottom: 1px solid #eee;
}

.product h3 {
  margin-bottom: 5px;
}

.product p {
  color: #777;
  font-size: 13px;
}

.message {
  text-align: center;
  color: #5145b5;
}

@media (max-width: 600px) {
  .product {
    align-items: flex-start;
    flex-direction: column;
  }
}
</style>
