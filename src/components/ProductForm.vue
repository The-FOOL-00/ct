<script setup>
import { ref } from 'vue'

const props = defineProps({
  product: Object
})

const emit = defineEmits(['save', 'cancel'])

function emptyForm() {
  return {
    name: '',
    category: 'Electronics',
    price: '',
    stock: ''
  }
}

const form = ref(
  props.product ? { ...props.product } : emptyForm()
)

function submitForm() {
  emit('save', {
    ...form.value,
    name: form.value.name.trim(),
    price: Number(form.value.price),
    stock: Number(form.value.stock)
  })
}
</script>

<template>
  <section class="panel">
    <h2>{{ product ? 'Edit Product' : 'Add Product' }}</h2>

    <form @submit.prevent="submitForm">
      <label>Name</label>
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

      <button class="primary" type="submit">
        {{ product ? 'Update Product' : 'Add Product' }}
      </button>

      <button v-if="product" type="button"
              class="secondary" @click="emit('cancel')">
        Cancel
      </button>
    </form>
  </section>
</template>