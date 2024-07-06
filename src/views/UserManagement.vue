<template>
  <div>
    <h1 style="text-align: center;">普通用户管理</h1>
    <el-input
      v-model="searchQuery"
      placeholder="搜索用户(输入用户名)"
      @input="fetchUsers"
      style="margin-bottom: 20px;"
    ></el-input>
    <el-table :data="users" style="width: 100%">
      <el-table-column prop="userid" label="用户id" width="180"></el-table-column>
      <el-table-column prop="username" label="用户名" width="180"></el-table-column>
      <el-table-column prop="email" label="邮箱" width="180"></el-table-column>
      <el-table-column prop="phone" label="手机号" width="180"></el-table-column>
      <el-table-column prop="address" label="联系地址"></el-table-column>
      <el-table-column prop="active" label="激活状态" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.active ? '已激活' : '未激活' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button @click="resetPassword(scope.row.userid)" size="mini">重置密码</el-button>
          <el-button @click="deleteUser(scope.row.userid)" size="mini" type="danger">删除</el-button>
          <el-button @click="toggleActive(scope.row)" size="mini" :type="scope.row.active ? 'danger' : 'success'">
            {{ scope.row.active ? '禁用' : '启用' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      users: [],
      searchQuery: ""
    };
  },
  created() {
    this.fetchUsers();
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get('http://localhost:8085/api/users/getall', {
          params: { search: this.searchQuery }
        });
        this.users = response.data;
      } catch (error) {
        console.error('获取用户失败:', error);
      }
    },
    async deleteUser(userId) {
      try {
        await axios.delete(`http://localhost:8085/api/users/${userId}`);
        this.$message.error('删除成功');
        this.fetchUsers();
      } catch (error) {
        console.error('删除用户失败:', error);
      }
    },
    async resetPassword(userId) {
      try {
        await axios.put(`http://localhost:8085/api/users/${userId}/reset-password`);
        this.$message.success('密码重置成功');
      } catch (error) {
        console.error('重置密码失败:', error);
        this.$message.error('密码重置失败');
      }
    },
    async toggleActive(user) {
      const newActiveStatus = !user.active;
      try {
        const response = await axios.put(`http://localhost:8085/api/users/${user.userid}/toggle-active`, {
          isactive: newActiveStatus
        });
        if (response.data === 1) {
          user.active = newActiveStatus; // 更新前端用户状态
          this.$message.success('状态更新成功');
        } else {
          this.$message.error('状态更新失败');
        }
      } catch (error) {
        this.$message.error('更新激活状态时出错');
      }
    }
  }
}
</script>

<style scoped>
.el-input {
  margin-bottom: 20px;
}
</style>
