<template>
  <div>
    <h1 style="text-align: center;">用户权限管理</h1>
    <el-table :data="users" style="width: 100%">
      <el-table-column prop="userid" label="用户id" width="180"></el-table-column>
      <el-table-column prop="username" label="用户名" width="180"></el-table-column>
      <el-table-column prop="rolename" label="角色" width="180"></el-table-column>
      <el-table-column label="操作" width="250">
        <template slot-scope="scope">
          <el-button @click="openDialog(scope.row)" size="mini">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" title="权限更改">
      <el-form :model="currentUser">
        <el-form-item label="用户名">
          <el-input v-model="currentUser.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="currentUser.rolename">
            <el-option v-if="isadmin" label="超级管理员" value="超级管理员"></el-option>
            <el-option label="组织管理员" value="组织管理员"></el-option>
            <el-option label="普通用户" value="普通用户"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveUser">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import axios from 'axios';

export default {
  data() {
    return {
      users: [],
      dialogVisible: false,
      currentUser: {
        userid: null,
        username: '',
        rolename: '',
        originalRole: '',
      },
      isadmin: false,
    };
  },
  created() {
    this.getUsers();
    this.getCurrentUserRole();
  },
  methods: {
    async getUsers() {
      try {
        const response = await axios.get('http://localhost:8085/getUserPermissions', {
          withCredentials: true // 确保跨域请求携带 Cookie
        });
        this.users = response.data.permissions; // 获取用户权限
        this.isadmin=response.data.isadmin;
      } catch (error) {
        console.error('获取用户失败:', error);
      }
    },
    openDialog(user) {
      this.currentUser = { ...user };
      this.dialogVisible = true;
    },
    async saveUser() {
      try {
        await axios.put(`http://localhost:8085/updateuser/${this.currentUser.userid}`, this.currentUser);
        this.dialogVisible = false;
        this.getUsers();
        this.$message.success('用户更新成功');

      } catch (error) {
        console.error('用户更新失败:', error);
        this.$message.error('用户更新失败');
      }
    },
  },
};
</script>
<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
