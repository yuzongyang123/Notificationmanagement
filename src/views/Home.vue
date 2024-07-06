<template>
  <div>
    <div class="head-controler">
      <el-menu
        :default-active="activeIndex2"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        style="display: flex; justify-content: space-between;">
        
        <el-menu-item index="1">首页</el-menu-item>
        <el-submenu index="2">
          <template slot="title">组织</template>
          <el-menu-item index="2-1" v-if="isOrganizaioner">组织管理</el-menu-item>
          <el-menu-item index="2-2">查看组织</el-menu-item>
        </el-submenu>
        <el-menu-item index="3" v-if="isAdmin">用户管理</el-menu-item>
        <el-menu-item index="4" v-if="isAdmin">权限管理</el-menu-item>
        <el-menu-item index="5">通知管理</el-menu-item>

        <el-dropdown @command="handleCommand">
          <div class="avatar-container">
            <el-avatar :src="require('@/assets/user.jpg')" style="margin-top: 10px;"></el-avatar>
            <span class="username">{{ role }}</span>
          </div>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="item1">个人信息</el-dropdown-item>
            <el-dropdown-item command="item2">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-menu>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      activeIndex: '1',
      activeIndex2: '1',
      isAdmin: false, // 用于存储用户是否为超级管理员
      role: "",
      isOrganizaioner: false,
    };
  },
  created() {
    this.fetchUserPermissions();
  },
  methods: {
    async fetchUserPermissions() {
      try {
        const response = await axios.get('http://localhost:8085/getpermission', {
          withCredentials: true // 确保跨域请求携带 Cookie
        });
        const data = response.data;
        this.isAdmin = data.admin;
        this.role = data.role;
        if (this.role === "组织管理者" || this.role === "超级管理员max" || this.role === "超级管理员") {
          this.isOrganizaioner = true;
        }
      } catch (error) {
        console.error('获取用户权限失败:', error);
      }
    },
    handleCommand(command) {
      switch (command) {
        case 'item1':
          this.$router.push({ name: 'imformation' }); // 确保路由路径正确
          break;
        case 'item2':
          this.$router.push('/login'); // 根据路由配置跳转到退出页面
          break;
        default:
          break;
      }
    },
    handleSelect(key, keyPath) {
      switch (key) {
        case '1':
          this.$router.push({ name: 'Homepage' });
          break;
        case '2-1':
          this.$router.push({ name: 'Organizationmanagement' });
          break;
        case '2-2':
          this.$router.push({ name: 'Organization' });
          break;
        case '3':
          this.$router.push({ name: 'userManagement' });
          break;
        case '4':
          this.$router.push({ name: 'Authoritymanagement' });
          break;
        case '5':
          this.$router.push({ name: 'NotificationManagement' });
          break;
        default:
          break;
      }
      console.log(key, keyPath);
    }
  }
}
</script>

<style lang="less" scoped>
.avatar-container {
  display: flex;
  align-items: center;
}

.username {
  margin-left: 5px;
  margin-top: 10px;
  cursor: pointer;
  color: #f0f0f0;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
