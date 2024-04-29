import Vue from 'vue'
import echarts from 'echarts'
import App from './App.vue'
import router from './router'
import Element,{Message} from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/global.css'

Vue.use(Element)
// Vue.use(Message)
Vue.prototype.$message=Message
Vue.config.productionTip = false
Vue.prototype.$echarts = echarts

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
