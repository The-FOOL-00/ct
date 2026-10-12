<script setup>
import { ref, onMounted } from 'vue'
import ProductForm from './components/ProductForm.vue'
import ProductList from './components/ProductList.vue'

const API = 'http://localhost:8080/api/products'

const products = ref([])
const editingProduct = ref(null)
const formKey = ref(0)
const message = ref('')
const error = ref('')

// READ: Get all products
async function loadProducts() {
  try {
    const response = await fetch(API)

    if (!response.ok) {
      throw new Error('Failed to load products')
    }

    products.value = await response.json()
    error.value = ''
  } catch {
    error.value = 'Cannot connect to Spring Boot.'
  }
}

// CREATE and UPDATE
async function saveProduct(product) {
  const editing = editingProduct.value !== null

  const url = editing
    ? `${API}/${editingProduct.value.id}`
    : API

  try {
    const response = await fetch(url, {
      method: editing ? 'PUT' : 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(product)
    })

    if (!response.ok) {
      throw new Error('Failed to save product')
    }

    editingProduct.value = null
    formKey.value++

    await loadProducts()

    message.value = editing
      ? 'Product updated successfully!'
      : 'Product added successfully!'
  } catch {
    error.value = 'Could not save product.'
  }
}

// Select a product for editing
function editProduct(product) {
  editingProduct.value = { ...product }
  formKey.value++
  message.value = ''
  error.value = ''
}

// DELETE
async function deleteProduct(product) {
  if (!confirm('Delete ' + product.name + '?')) {
    return
  }

  try {
    const response = await fetch(`${API}/${product.id}`, {
      method: 'DELETE'
    })

    if (!response.ok) {
      throw new Error('Failed to delete product')
    }

    products.value = products.value.filter(
      p => p.id !== product.id
    )

    if (editingProduct.value?.id === product.id) {
      editingProduct.value = null
      formKey.value++
    }

    message.value = 'Product deleted successfully!'
    error.value = ''
  } catch {
    error.value = 'Could not delete product.'
  }
}

// Cancel editing
function cancelEdit() {
  editingProduct.value = null
  formKey.value++
  message.value = ''
  error.value = ''
}

// Load products when the page opens
onMounted(loadProducts)
</script>

<template>
  <header>
    <h1>ProductStudio</h1>
    <p>Product Inventory Management</p>
  </header>

  <main>
    <p v-if="message" class="success">{{ message }}</p>
    <p v-if="error" class="error">{{ error }}</p>

    <div class="layout">
      <!-- Form component -->
      <ProductForm
        :key="formKey"
        :product="editingProduct"
        @save="saveProduct"
        @cancel="cancelEdit"
      />

      <!-- Product list component -->
      <ProductList
        :products="products"
        @edit="editProduct"
        @delete="deleteProduct"
      />
    </div>
  </main>
</template>