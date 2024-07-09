import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api/v1'

const http = axios.create({
  baseURL: API_BASE_URL,
  withCredentials: false,
  headers: { 'Content-type': 'application/json' }
})

export default http