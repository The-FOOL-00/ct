<script setup>
import { ref, onMounted } from 'vue'

const API = 'http://localhost:8080/api/persons'

const persons = ref([])
const name = ref('')
const selectedId = ref(null)
const searchId = ref('')
const foundPerson = ref(null)
const message = ref('')
const error = ref('')

// READ ALL: Get all persons
async function getPersons() {
  try {
    const res = await fetch(API)
    if (!res.ok) throw new Error()

    persons.value = await res.json()
    error.value = ''
  } catch {
    error.value = 'Failed to fetch persons!'
  }
}

// READ ONE: Get a person by MongoDB ID
async function getPersonById() {
  try {
    const res = await fetch(`${API}/${searchId.value}`)
    if (!res.ok) throw new Error()

    foundPerson.value = await res.json()
    error.value = ''
  } catch {
    foundPerson.value = null
    error.value = 'Person not found!'
  }
}

// CREATE: Add a new person
async function createPerson() {
  if (!name.value.trim()) {
    error.value = 'Enter a name!'
    return
  }

  try {
    const res = await fetch(API, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name: name.value.trim() })
    })

    if (!res.ok) throw new Error()

    message.value = 'Person added!'
    resetForm()
    await getPersons()
  } catch {
    error.value = 'Failed to add person!'
  }
}

// UPDATE: Update the selected person
async function updatePerson() {
  if (!name.value.trim()) {
    error.value = 'Enter a name!'
    return
  }

  try {
    const res = await fetch(`${API}/${selectedId.value}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name: name.value.trim() })
    })

    if (!res.ok) throw new Error()

    message.value = 'Person updated!'
    resetForm()
    await getPersons()
  } catch {
    error.value = 'Failed to update person!'
  }
}

// EDIT: Load the selected person's name into the input
function editPerson(person) {
  name.value = person.name
  selectedId.value = person.id
  message.value = 'Editing person...'
  error.value = ''
}

// DELETE: Delete a person by ID
async function deletePerson(person) {
  if (!confirm('Delete ' + person.name + '?')) return

  try {
    const res = await fetch(`${API}/${person.id}`, {
      method: 'DELETE'
    })

    if (!res.ok) throw new Error()

    message.value = 'Person deleted!'
    error.value = ''

    await getPersons()

    if (selectedId.value === person.id) {
      resetForm()
    }
  } catch {
    error.value = 'Failed to delete person!'
  }
}

// CLEAR: Reset the form
function resetForm() {
  name.value = ''
  selectedId.value = null
}

// Run GET ALL when the page opens
onMounted(getPersons)
</script>

<template>
  <header>
    <h1>Person Manager</h1>
    <p>Simple Person Management System</p>
  </header>

  <main>
    <p v-if="message">{{ message }}</p>
    <p v-if="error">{{ error }}</p>

    <h2>{{ selectedId === null ? 'Add Person' : 'Update Person' }}</h2>

    <form @submit.prevent="selectedId === null ? createPerson() : updatePerson()">
      <label for="name">Name:</label>
      <input
        id="name"
        v-model="name"
        placeholder="Enter person's name"
        required
      />

      <button v-if="selectedId === null" type="submit">
        Add Person
      </button>

      <button v-else type="submit">
        Update Person
      </button>

      <button type="button" @click="resetForm">
        Clear
      </button>
    </form>

    <hr />

    <h2>Person List ({{ persons.length }})</h2>

    <p v-if="persons.length === 0">No persons found.</p>

    <ul>
      <li v-for="person in persons" :key="person.id">
        <strong>{{ person.name }}</strong>
        — ID: {{ person.id }}

        <button @click="editPerson(person)">Edit</button>
        <button @click="deletePerson(person)">Delete</button>
      </li>
    </ul>

    <hr />

    <h2>Find Person by ID</h2>

    <form @submit.prevent="getPersonById">
      <label for="searchId">MongoDB ID:</label>
      <input
        id="searchId"
        v-model="searchId"
        placeholder="Enter MongoDB ID"
        required
      />

      <button type="submit">Find Person</button>
    </form>

    <p v-if="foundPerson">
      Name: <strong>{{ foundPerson.name }}</strong>
    </p>

    <p v-if="foundPerson">
      ID: {{ foundPerson.id }}
    </p>
  </main>
</template>