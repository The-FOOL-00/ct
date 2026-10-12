<script setup>
defineProps({
  products: Array
})

const emit = defineEmits(['edit', 'delete'])
</script>

<template>
  <section class="panel">
    <h2>Product List</h2>

    <p v-if="products.length === 0">
      No products available.
    </p>

    <article
      v-for="p in products"
      :key="p.id"
      class="product"
    >
      <div>
        <h3>{{ p.name }}</h3>
        <p>{{ p.category }} | Stock: {{ p.stock }}</p>
        <strong>₹{{ p.price }}</strong>
      </div>

      <div class="actions">
        <button
          class="edit"
          @click="emit('edit', p)"
        >
          Edit
        </button>

        <button
          class="delete"
          @click="emit('delete', p)"
        >
          Delete
        </button>
      </div>
    </article>
  </section>
</template>