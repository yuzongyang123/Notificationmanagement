<template>
  <div id="app">
    
    
    <!-- 路由匹配到的组件将渲染在这里 -->
 <router-view></router-view>
 
  </div>
</template>

<script>


export default {
  name: 'App',
  
}
</script>

<style lang="less" >
html, body {  //消除浏览器默认外边距
    margin: 0;
    padding: 0;
}


</style>


