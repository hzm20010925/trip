import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login'
import Home from '../components/Home'
import Search from '../components/Search'
import Insert from '../components/Insert'
import welcome from '../components/welcome'
import Updata from '../components/Updata'
import CityDetail from '../components/CityDetail'
import Return from '../components/spotdata'
import Register from "../components/Register";
import SpotSearch from "../components/SpotSearch";
import userSearch from "../components/userSearch";
import infoChange from "../components/infoChange";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/home',
    name: 'home',
    component: Home,
    redirect:'/welcome',
    children:[
      {
        path: '/search',
        name: 'search',
        component: Search
      },
      {
        path: '/usersearch',
        name: 'usersearch',
        component: userSearch
      },
      {
        path: '/spotsearch',
        name: 'spotsearch',
        component: SpotSearch
      },
      {
        path: '/welcome',
        name: 'welcome',
        component: welcome
      },
      {
        path: '/insert',
        name: 'insert',
        component: Insert
      },
      {
        path: '/updata',
        name: 'updata',
        component: Updata
      },
      {
        path: '/citydetail',
        name: 'citydeatil',
        component: CityDetail
      },
      {
        path: '/return',
        name: 'return',
        component: Return
      },
      {
        path: '/infochange',
        name: 'infochange',
        component: infoChange
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
