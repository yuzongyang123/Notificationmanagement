<template>
  <div>
    <h1 style="text-align: center;">组织成员管理</h1>
    <el-table :data="members" style="width: 100%">
      <el-table-column prop="userid" label="用户ID" width="180"></el-table-column>
      <el-table-column prop="username" label="用户名" width="180"></el-table-column>
      <el-table-column prop="isNotifier" label="是否为通知发布者" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.isNotifier ? '是' : '否' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button @click="removeMember(scope.row.userid)" size="mini" type="danger">移除</el-button>
          <el-button @click="openPermissionDialog(scope.row)" size="mini" type="primary">权限分配</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" title="添加成员">
      <el-input v-model="newMemberName" placeholder="输入用户名"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addMember">添加</el-button>
      </span>
    </el-dialog>

    <el-dialog :visible.sync="permissionDialogVisible" title="权限更改">
      <el-form :model="currentUser">
        <el-form-item label="当前用户名">
          <el-input v-model="currentUser.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="是否为通知发布者">
          <el-select v-model="currentUser.isNotifier">
            <el-option label="是" :value="true"></el-option>
            <el-option label="否" :value="false"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="permissionDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveUser">保存</el-button>
      </span>
    </el-dialog>

    <el-button type="primary" @click="dialogVisible = true">添加成员</el-button>
  </div>
</template>
<script>
import axios from 'axios';

export default {
  data() {
    return {
      members: [],
      dialogVisible: false,
      permissionDialogVisible: false,
      newMemberName: '',
      currentUser: {
        userid: '',
        username: '',
        isNotifier: false
      }
    };
  },
  created() {
    this.fetchMembers();
  },
  methods: {
    async fetchMembers() {
      const organizationId = this.$route.params.organizationid;
      try {
        const response = await axios.get(`http://localhost:8085/showmembers/${organizationId}`);
        this.members = response.data;
      } catch (error) {
        console.error('获取成员失败:', error);
      }
    },
    async addMember() {
      const organizationId = this.$route.params.organizationid;
      const memberExists = this.members.some(member => member.username === this.newMemberName);
      if (memberExists) {
        this.$message.error('用户已在组织中');
        this.dialogVisible = false;
        return;
      }

      try {
        const response = await axios.post(`http://localhost:8085/addmember/${organizationId}`, { name: this.newMemberName });
        this.dialogVisible = false;
        this.newMemberName = '';
        if (response.data === 1) {
          this.$message.success('添加成功');
        } else {
          this.$message.error('添加失败');
        }
        this.fetchMembers();
      } catch (error) {
        this.$message.error('用户已存在组织');
        this.dialogVisible = false;
      }
    },
    async removeMember(memberId) {
      const organizationId = this.$route.params.organizationid;
      try {
        await axios.delete(`http://localhost:8085/deletemember/${organizationId}/${memberId}`);
        this.fetchMembers();
      } catch (error) {
        console.error('移除成员失败:', error);
      }
    },
    openPermissionDialog(member) {
      this.currentUser.userid = member.userid;
      this.currentUser.username = member.username;
      this.currentUser.isNotifier = member.isNotifier;
      this.permissionDialogVisible = true;
    },
    async saveUser() {
      const organizationId = this.$route.params.organizationid;
      try {
        const response = await axios.put(`http://localhost:8085/updateMemberRole/${organizationId}`, {
          userid: this.currentUser.userid,
          isNotifier: this.currentUser.isNotifier
        });
        if (response.data === 1) {
          this.$message.success('权限更新成功');
          this.permissionDialogVisible = false;
          this.fetchMembers();
        } else {
          this.$message.error('权限更新失败');
        }
      } catch (error) {
        this.$message.error('更新权限时出错');
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
