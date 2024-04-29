<template >

  <div id="app" :style="note">

    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'App',
    data () {
      return {
        note: {
          backgroundImage: 'url(' + require('./assets/27.png') + ')',
          backgroundRepeat: 'no-repeat',
          // backgroundSize: '25px auto',
          // marginTop: '5px'
          backgroundSize:'1550px 750px',
          // marginLeft:'100px',
        }
      }
    }
  }

</script>


<style>



  nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
