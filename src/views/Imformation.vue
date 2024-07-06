<template>
  <div id="app">
    <el-descriptions title="用户信息" :border="true" column="1">
      <el-descriptions-item label="用户名">{{ userInfo.username }}</el-descriptions-item>
      <el-descriptions-item label="手机号">{{ userInfo.phone }}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{ userInfo.email }}</el-descriptions-item>
      <el-descriptions-item label="联系地址">{{ userInfo.address }}</el-descriptions-item>
    </el-descriptions>
    <el-button type="primary" @click="openEditDialog">编辑</el-button>

    <el-dialog title="编辑个人信息" :visible.sync="dialogVisible">
      <el-form :model="editUserInfo" label-width="80px">
        <el-form-item label="手机号">
          <el-input v-model="editUserInfo.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editUserInfo.email"></el-input>
        </el-form-item>
        <el-form-item label="联系地址">
          <el-input v-model="editUserInfo.address"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEdit">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      dialogVisible: false,
      userInfo: {
        username: '',
        phone: '',
        email: '',
        address: '',
      },
      editUserInfo: {
        username: '',
        phone: '',
        email: '',
        address: '',
      }
    };
  },
  created() {
    this.fetchUserInfo();
  },
  methods: {
    async fetchUserInfo() {
    try {
      const response = await axios.get('http://localhost:8085/api/users/selectuser', {
        withCredentials: true
      });

      if (response.data) {
        // 使用 Vue.set 确保数据响应式更新
        this.$set(this, 'userInfo', response.data);

      } else {
        console.error('API response is empty or malformed');
      }
    } catch (error) {
      console.error("Failed to fetch user data:", error);
    }
  },
    openEditDialog() {
      this.editUserInfo = { ...this.userInfo };
      this.dialogVisible = true;
    },
    async saveEdit() {
      try {
        const response = await axios.post('http://localhost:8085/api/users/updateuser', this.editUserInfo, {
          withCredentials: true
        });
        console.log('Save response data:', response.data);
        if (response.data) {
          this.userInfo = response.data;
          this.dialogVisible = false;
          this.$message.success('保存成功');

        } else {
          console.error('Save API response is empty or malformed');
          this.$message.error('保存失败');

        }
      } catch (error) {
        console.error("Failed to save user data:", error);
      }
    }
  }
};
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
