<template>
  <div>
    <h2>用户所属组织</h2>
    <el-table :data="organizationsWithOrder" style="width: 100%">
      <el-table-column prop="order" label="序号" width="60"></el-table-column>
      <el-table-column prop="organizationname" label="组织名称" width="180"></el-table-column>
      <el-table-column prop="isNotifier" label="是否有权限发送通知" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.isNotifier ? '是' : '否' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" @click="leaveOrganization(scope.row)">退出组织</el-button>
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
      organizations: [],
    };
  },
  created() {
    this.fetchUserOrganizations();
  },
  methods: {
    async fetchUserOrganizations() {
      try {
        const response = await axios.get('http://localhost:8085/getliving', {
          withCredentials: true // 确保跨域请求携带 Cookie
        });
        this.organizations = response.data;
      } catch (error) {
        console.error('获取用户组织失败:', error);
      }
    },
    async leaveOrganization(organization) {
      try {
        const response = await axios.delete('http://localhost:8085/leaveOrganization', {
          params: {
            organizationid: organization.organizationid
          },
          withCredentials: true // 确保跨域请求携带 Cookie
        });
        if (response.data === 1) {
          this.$message.success('退出组织成功');
          // 重新获取组织列表
          this.fetchUserOrganizations();
        } else {
          console.error('退出组织失败:', response.data.message);
        }
      } catch (error) {
        console.error('退出组织失败:', error);
      }
    },
  },
  computed: {
    organizationsWithOrder() {
      return this.organizations.map((org, index) => ({
        ...org,
        order: index + 1,
      }));
    },
  },
};
</script>

<style scoped>
h2 {
  text-align: center;
  margin-bottom: 20px;
}
</style>
