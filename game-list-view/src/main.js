import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Carousel3d from 'vue3-carousel-3d';
import ElementPlus from '../node_modules/element-plus'

import 'element-plus/dist/index.css'

import "vue3-carousel-3d/dist/index.css"

createApp(App).use(router).use(Carousel3d).use(ElementPlus).mount('#app')

