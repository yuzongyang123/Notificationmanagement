<template>
  <div>
    <h1 style="text-align: center;">组织管理</h1>
    <el-button type="primary"  @click="createOrganization">创建组织</el-button>
    <el-table :data="organizationsWithOrder" style="width: 100%">
      <el-table-column prop="order" label="序号" width="180"></el-table-column>
      <el-table-column prop="organizationname" label="组织名称" width="180"></el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button @click="editOrganization(scope.row.organizationid)" size="mini">编辑</el-button>
          <el-button @click="manageMembers(scope.row.organizationid)" size="mini">管理成员</el-button>
          <el-button @click="deleteOrganization(scope.row.organizationid)" size="mini" type="danger">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" title="组织信息">
      <el-form :model="currentOrganization">
        <el-form-item label="组织名称">
          <el-input v-model="currentOrganization.organizationname"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveOrganization">保存</el-button>
      </span>
    </el-dialog>

    <!-- 弹出框 -->
    <el-dialog :visible.sync="nameExistsDialogVisible" title="提示">
      <span>组织名称已经存在，请重新输入组织名称。</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="nameExistsDialogVisible = false">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';
import { ElMessage } from 'element-ui';

export default {
  data() {
    return {
      organizations: [],
      dialogVisible: false,
      currentOrganization: {
        organizationid: null,
        organizationname: '',
      },
      nameExistsDialogVisible: false, // 新增的弹出框状态
    };
  },
  created() {
    this.fetchOrganizations();
  },
  computed: {
    organizationsWithOrder() {
      return this.organizations.map((org, index) => ({
        ...org,
        order: index + 1,
      }));
    },
  },
  methods: {
    async fetchOrganizations() {
      try {
        const response = await axios.get('http://localhost:8085/getorganization', {
          withCredentials: true // 确保跨域请求携带 Cookie
        });
        this.organizations = response.data;
      } catch (error) {
        console.error('获取组织失败:', error);
      }
    },
    createOrganization() {
      this.currentOrganization = { organizationid: null, organizationname: '' };
      this.dialogVisible = true;
    },
    editOrganization(organizationid) {
      const organization = this.organizations.find(org => org.organizationid === organizationid);
      if (organization) {
        this.currentOrganization = { ...organization };
        this.dialogVisible = true;
      }
    },
    async saveOrganization() {
      try {
        // 检查组织名称是否已经存在
        const existingOrganization = this.organizations.find(org => org.organizationname === this.currentOrganization.organizationname);
        if (existingOrganization && !this.currentOrganization.organizationid) {
          // 组织名称已存在且当前操作为创建新组织，弹出提示框
          this.nameExistsDialogVisible = true;
          return;
        }

        let response;
        if (this.currentOrganization.organizationid) {
          response = await axios.put(`http://localhost:8085/updatename/${this.currentOrganization.organizationid}`, this.currentOrganization, {
            withCredentials: true // 确保跨域请求携带 Cookie
          });
          this.$message.success('保存成功');

        } else {
          response = await axios.post('http://localhost:8085/createOrganization', this.currentOrganization, {
            withCredentials: true // 确保跨域请求携带 Cookie
          });
          this.$message.success('创建成功');
        }

        this.dialogVisible = false;
        this.fetchOrganizations();
      } catch (error) {
        console.error('保存组织失败:', error);
        this.$message.error('保存失败');
      }
    },
    async deleteOrganization(organizationid) {
      try {
        await axios.delete(`http://localhost:8085/deleteorganization/${organizationid}`, {
          withCredentials: true // 确保跨域请求携带 Cookie
        });
        this.fetchOrganizations();
        this.$message.success('删除成功');

      } catch (error) {
        console.error('删除组织失败:', error);
        this.$message.error('删除失败');

      }
    },
    manageMembers(organizationid) {
      this.$router.push({ name: 'Organizationmember', params: { organizationid } });
    },
  },
};
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
