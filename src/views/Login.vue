<template>
  <div id="app">
    <el-empty :image="require('@/assets/logo2.png')" :image-size="500" description="  "></el-empty>

    <div class="container">
      <el-form label-width="70px" :inline="true" class="login-container" :model="form" :rules="rules" @submit.native.prevent >
        <h3 class="login-title">用户登录</h3>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入账号" style="width: 198px;"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" style="width: 198px;" show-password></el-input>
        </el-form-item>
        <el-form-item prop="validcode">
          <div class="validcode-container">
            <span class="validcode-label">验证码</span>
            <el-input v-model="form.validcodeInput" placeholder="" style="width: 90px;" ></el-input>
            <div class="validcode-wrapper">
              <valid-code v-model="form.validcode" style="width: 100px; height: 50px; background-color: blanchedalmond;"></valid-code>
            </div>
          </div>
        </el-form-item>

        <el-form-item class="actions">
          <el-link type="info" @click="register">注册账号</el-link>
          <el-button native-type="submit" type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
 

<script>
import axios from 'axios';
import ValidCode from '@/components/ValidCode.vue';
import Cookies from 'js-cookie';

export default {
  components: {
    ValidCode
  },
  data() {
    return {
      form: {
        username: '',
        password: '',
        validcode: '', // Holds the generated verification code
        validcodeInput: '' // Holds the user's input for the verification code
      },
      rules: {
        username: [{ required: true, trigger: 'blur', message: '请输入用户名' }],
        password: [{ required: true, trigger: 'blur', message: '请输入密码' }],
        validcodeInput: [{ required: true, trigger: 'blur', message: '请输入验证码' }],
      }
    }
  },
  methods: {
    register() {
      this.$router.push('/Register');
    },
    login() {
  const inputCode = this.form.validcodeInput.toLowerCase();
  const generatedCode = this.form.validcode.toLowerCase();

  if (generatedCode !== inputCode) {
    alert('验证码错误');
    return;
  }

  axios.post('http://localhost:8085/login', {
    username: this.form.username,
    password: this.form.password,
    validcode: this.form.validcodeInput,
  }, {
    withCredentials: true // 确保跨域请求携带 Cookie
  })
  .then(response => {
    if (response.status === 200) {
      alert('登录成功');
      this.$router.push('/home/Homepage');
    }
  })
  .catch(error => {
    if (error.response) {
      alert('登录失败：' + error.response.data);
    } else {
      alert('登录失败：' + error.message);
    }
    console.error(error);
  });
}

  }
}
</script>


<style lang="less" scoped>
#app {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.container {
  position: relative;
  margin-left: 100px;
}

.login-container {
  width: 350px;
  border: 1px solid #000000;
  padding: 35px 35px 15px 35px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 0 25px #cac6c6;
  box-sizing: border-box;
  text-align: center;
  max-height: 450px;
}

.login-title {
  text-align: center;
  margin-bottom: 40px;
}

.actions {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  position: relative;
}
.actions .el-link {
  position: relative;
  right: 100px;
}

.actions .el-button {
  display: block;
}

.validcode-container {
  display: flex;
  align-items: center;
  justify-content: flex-start; /* Align items to the start */
}

.validcode-label {
  margin-right: 10px;
}

.validcode-wrapper {
  margin-left: 20px; /* Adjust this value to move the image further to the right */
}
</style>

