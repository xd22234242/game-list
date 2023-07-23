import { createRouter, createWebHashHistory } from 'vue-router'
import GameListView from '../views/GameListView.vue'
import FiveChessView from '../views/FiveChessView.vue'
import Login from "../views/LoginView.vue"
import register from "../views/RegisterView.vue"

const routes = [
  {
    path: '/',
    name: 'gamelistview',
    component: GameListView
  },
  {
    path: '/FiveChessView',
    name: 'FiveChessView', 
    component: FiveChessView
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: register
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
