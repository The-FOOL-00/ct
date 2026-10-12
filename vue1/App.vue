<script setup>
import { ref, onMounted } from 'vue'
import ProductForm from './components/ProductForm.vue'
import ProductList from './components/ProductList.vue'

const API = 'http://localhost:8080/api/products'

const products = ref([])
const selected = ref(null)
const formKey = ref(0)
const message = ref('')

// READ
async function load() {
  const res = await fetch(API)
  products.value = await res.json()
}

// CREATE and UPDATE
async function save(product) {
  const edit = selected.value !== null

  const res = await fetch(
    edit ? `${API}/${selected.value.id}` : API,
    {
      method: edit ? 'PUT' : 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(product)
    }
  )

  if (!res.ok) {
    message.value = 'Save failed!'
    return
  }

  selected.value = null
  formKey.value++
  message.value = edit ? 'Updated!' : 'Added!'

  await load()
}

// Select product for editing
function editProduct(product) {
  selected.value = { ...product }
  formKey.value++
}

// DELETE
async function deleteProduct(product) {
  if (!confirm('Delete ' + product.name + '?')) return

  const res = await fetch(`${API}/${product.id}`, {
    method: 'DELETE'
  })

  if (res.ok) {
    products.value = products.value.filter(
      p => p.id !== product.id
    )
    message.value = 'Deleted!'
  }
}

function cancel() {
  selected.value = null
  formKey.value++
}

onMounted(load)
</script>

<template>
  <header>
    <h1>Product Manager</h1>
    <p>Simple Inventory System</p>
  </header>

  <main>
    <p>{{ message }}</p>

    <div class="layout">
      <ProductForm
        :key="formKey"
        :product="selected"
        @save="save"
        @cancel="cancel"
      />

      <ProductList
        :products="products"
        @edit="editProduct"
        @delete="deleteProduct"
      />
    </div>
  </main>
</template>
