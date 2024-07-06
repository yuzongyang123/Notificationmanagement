<template>
    <el-table :data="notifications" style="width: 100%">
      <el-table-column prop="title" label="标题" width="180"></el-table-column>
      <el-table-column prop="content" label="内容"></el-table-column>
      <el-table-column prop="createdAt" label="创建时间" width="180"></el-table-column>
      <el-table-column label="操作" width="120">
        <template slot-scope="scope">
          <el-button @click="$emit('edit', scope.row)" type="primary" size="small">编辑</el-button>
          <el-button @click="$emit('delete', scope.row.id)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </template>
  
  <script>
  export default {
    name: 'NotificationList',
    props: {
      notifications: {
        type: Array,
        required: true
      }
    }
  };
  </script>
  