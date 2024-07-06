<template>
    <el-form :model="notification" label-width="80px">
      <el-form-item label="标题">
        <el-input v-model="notification.title"></el-input>
      </el-form-item>
      <el-form-item label="内容">
        <el-input type="textarea" v-model="notification.content"></el-input>
      </el-form-item>
      <el-form-item label="图片">
        <el-upload action="/api/upload" list-type="picture-card" :on-success="handleUploadSuccess" :file-list="fileList">
          <i class="el-icon-plus"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="附件">
        <el-upload action="/api/upload" :on-success="handleUploadSuccess" :file-list="fileList">
          <el-button type="primary">上传附件</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="$emit('save', notification)">保存</el-button>
      </el-form-item>
    </el-form>
  </template>
  
  <script>
  export default {
    name: 'NotificationEditor',
    props: {
      notification: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
        fileList: []
      };
    },
    methods: {
      handleUploadSuccess(response, file, fileList) {
        this.fileList = fileList;
        // 假设 API 返回上传文件的 URL
        this.notification.files = fileList.map(file => file.response.url);
      }
    }
  };
  </script>
  