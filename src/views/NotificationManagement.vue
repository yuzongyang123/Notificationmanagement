<template>
  <div>
    <h1>通知管理界面</h1>

    <!-- 发送通知的 Dialog -->
    <el-dialog :visible.sync="notificationDialogVisible" title="发送通知">
      <el-form :model="notificationForm" label-width="100px">
        <el-form-item label="选择组织">
          <el-select v-model="selectedOrganization" placeholder="请选择组织" @change="handleOrganizationChange">
            <el-option 
              v-for="org in notifierOrganizations" 
              :key="org.organizationsid" 
              :label="org.organizationname" 
              :value="org.organizationsid" 
            />
          </el-select>
        </el-form-item>
        <el-form-item label="通知标题" prop="title">
          <el-input v-model="notificationForm.title" placeholder="请输入通知标题" maxlength="50"></el-input>
          <span v-if="notificationForm.title.length == 50" style="color: red;">标题字数最多为50</span>
        </el-form-item>
        <el-form-item label="通知内容">
          <el-input type="textarea" v-model="notificationForm.content" placeholder="请输入通知内容"></el-input>
        </el-form-item>
        <el-form-item label="图片">
          <input type="file" ref="imageInput" @change="handleImageChange" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="notificationDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="sendNotification">发送</el-button>
      </span>
    </el-dialog>

    <!-- 发送通知按钮 -->
    <el-button type="primary" @click="openNotificationDialog">发送通知</el-button>

    <!-- 通知列表 -->
    <el-table :data="notifications" style="width: 100%">
      <el-table-column prop="organizationname" label="组织名称" width="180"></el-table-column>
      <el-table-column prop="username" label="发布者" width="180"></el-table-column>
      <el-table-column prop="title" label="标题" width="180"></el-table-column>
      <el-table-column prop="createdAt" label="创建时间" width="200">
        <template slot-scope="scope">
          <span>{{ formatDate(scope.row.createdAt) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button @click="viewNotification(scope.row)" size="mini" type="primary">查看</el-button>
          <el-button :disabled="!scope.row.nofiter" @click="removeNotification(scope.row)" type="danger" size="mini">删除通知</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 查看通知的 Dialog -->
    <el-dialog :visible.sync="viewDialogVisible" title="查看通知">
      <p><strong>标题：</strong>{{ currentNotification.title }}</p>
      <el-collapse>
        <el-collapse-item title="内容" name="1">
          <div>{{ currentNotification.content }}</div>
        </el-collapse-item>
      </el-collapse>
      <p><strong>创建时间：</strong>{{ formatDate(currentNotification.createdAt) }}</p>
      <img v-if="currentNotification.imageUrl" :src="currentNotification.imageUrl" alt="通知图片" style="max-width: 100%;">
      <span slot="footer" class="dialog-footer">
        <el-button @click="viewDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';

export default {
  data() {
    return {
      notificationDialogVisible: false,
      viewDialogVisible: false,
      notifierOrganizations: [],
      selectedOrganization: null,
      notificationForm: {
        title: '',
        content: '',
        imageUrl: '',
      },
      notifications: [],
      currentNotification: {},
      selectedImage: null,
    };
  },
  created() {
    this.fetchNotifierOrganizations();
    this.fetchNotifications();
  },
  methods: {
    async fetchNotifierOrganizations() {
      try {
        const response = await axios.get('http://localhost:8085/Notifications/getNotifierOrganizations', {
          withCredentials: true
        });
        this.notifierOrganizations = response.data;
      } catch (error) {
        console.error('获取通知发布者的组织列表失败:', error);
      }
    },
    async fetchNotifications() {
      try {
        const response = await axios.get('http://localhost:8085/Notifications/getNotifications', {
          withCredentials: true
        });
        this.notifications = response.data;
        // 输出每个通知的创建时间以供调试
        this.notifications.forEach(notification => {
          console.log(notification.createdAt); // 调试输出每个通知的创建时间
        });
      } catch (error) {
        console.error('获取通知列表失败:', error);
      }
    },
    openNotificationDialog() {
      this.notificationDialogVisible = true;
      // 重置表单和文件选择
      this.selectedOrganization = null;
      this.notificationForm.title = '';
      this.notificationForm.content = '';
      this.notificationForm.imageUrl = '';
      this.selectedImage = null;
      if (this.$refs.imageInput) {
        this.$refs.imageInput.value = '';
      }
    },
    handleImageChange(event) {
      this.selectedImage = event.target.files[0];
    },
    handleOrganizationChange(value) {
      this.selectedOrganization = value;
    },
    async sendNotification() {
      try {
        const organizationId = this.selectedOrganization;
        const { title, content } = this.notificationForm;

        const formData = new FormData();
        formData.append('organizationId', organizationId);
        formData.append('title', title);
        formData.append('content', content);

        if (this.selectedImage) {
          formData.append('image', this.selectedImage);
        }

        const response = await axios.post('http://localhost:8085/Notifications/sendNotification', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          },
          withCredentials: true
        });

        if (response.data.status === 200) {
          this.$message.success('通知发送成功');
          this.notificationDialogVisible = false;
          this.notificationForm.title = '';
          this.notificationForm.content = '';
          this.notificationForm.imageUrl = '';
          this.selectedImage = null;
          this.selectedOrganization = null;
          this.fetchNotifications();
        } else if (response.data.status === 409) {
          this.$message.error('该组织已存在同样标题的通知，请更改标题');
        }
      } catch (error) {
        if (error.response && error.response.status === 409) {
        this.$message.error('该组织已存在同样标题的通知，请更改标题');
    }   else {
        console.error('发送通知失败:', error);
        this.$message.error('发送通知失败');
    }
      }
    },
    viewNotification(notification) {
      this.currentNotification = notification;
      this.viewDialogVisible = true;
    },
    formatDate(date) {
      return moment(date).format('YYYY-MM-DD HH:mm:ss');
    },
    async removeNotification(notification) {
      try {
        const url = `http://localhost:8085/Notifications/deleteNotification`;
        const response = await axios.delete(url, {
          params: {
            createdBy: notification.createdBy,
            title: notification.title,
            organizationId: notification.organizationID
          },
          withCredentials: true
        });
        if (response.data.status === 200) {
          this.$message.success('通知删除成功');
          this.fetchNotifications();
        } else {
          this.$message.error(response.data.message);
        }
      } catch (error) {
        console.error('删除通知失败:', error);
        this.$message.error('删除通知失败');
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
