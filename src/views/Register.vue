<template>
    <div id="app">
      <el-empty :image="require('@/assets/logo2.png')" :image-size="500" description="  "></el-empty>
  
      <div class="container">
        <el-form  ref="form" :inline="true" class="login-container" :model="form" :rules="rules">
          <h3 class="login-title">用户注册</h3>
          <el-form-item  label-width="80px" label="用户名" prop="username">
            <el-input  v-model="form.username" placeholder="请输入账号"></el-input>
          </el-form-item>
          <el-form-item label-width="80px" label="密码" prop="password">
            <el-input v-model="form.password" placeholder="请输入密码" show-password></el-input>
          </el-form-item>
          <el-form-item  label-width="80px" label="确认密码" prop="password2" >
            <el-input v-model="form.password2" placeholder="请确认密码" show-password></el-input>
          </el-form-item>
          <el-form-item prop="validcode">
          <div class="validcode-container">
            <span class="validcode-label">验证码</span>
            <el-input v-model="form.validcodeInput" placeholder="" style="width: 90px;"></el-input>
            <div class="validcode-wrapper">
              <valid-code v-model="form.validcode" style="width: 100px; height: 50px; background-color: blanchedalmond;"></valid-code>
            </div>
          </div>
        </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="register">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import ValidCode from '@/components/ValidCode.vue';

  export default {
    components: {
    ValidCode
  },
    data() {
      return {
        form: {
          username: '',
          password: '',
          password2: '', // 存储确认密码
          validcode: '', // Holds the generated verification code
        validcodeInput: '' // Holds the user's input for the verification code

  
        },
        rules: {
          username: [{ required: true, trigger: 'blur', message: '请输入用户名' }],
          password: [{ required: true, trigger: 'blur', message: '请输入密码' }],
          password2: [
          { required: true, trigger: 'blur', message: '请确认密码' },
          { validator: this.validatePassword2, trigger: 'blur' },
          
        ],
        validcodeInput: [{ required: true, trigger: 'blur', message: '请输入验证码' }]

  
        }
      }
    },
    methods: {
        validatePassword2(rule, value, callback) {
      if (value !== this.form.password) {
        callback(new Error('两次输入的密码不一致'));
      } else {
        callback();
      }
    },
    
     
        register() {
          const inputCode = this.form.validcodeInput.toLowerCase();
    const generatedCode = this.form.validcode.toLowerCase();
    
    if (generatedCode !== inputCode) {
      alert('验证码错误');
      return;
    }
      this.$refs.form.validate(valid => {
        if (valid) {
          axios.post('http://localhost:8085/register', {
            username: this.form.username,
            password: this.form.password,
          })
          .then(response => {
            if (response.status === 200) {
              alert('注册成功');
              this.$router.push('/login');
            } else {
              alert('注册失败');
            }
          })
          .catch(error => {
            if (error.response && error.response.status === 400) {
                alert(error.response.data);
              } else {
                alert('注册失败：' + error.message);
              }
              console.error(error);
          });
        } else {
          console.log('表单验证失败');
          return false;
        }
      });
    }
  }
}
</script>
  
    
  
  <style lang="less" scoped>
  #app {
    height: 100vh; /* 设置页面高度为视口高度 */
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .container {
    position: relative; /* 设置相对定位 */
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
  }
  
  .el-input {
    width: 198px;
  }
  
  .login-title {
    text-align: center;
    margin-bottom: 40px;
  }
  
  .el-button {
    margin-left: 20px;
  }
  
  .el-link {
    margin-right: 100px;
    margin-top: 10px;
    font-size: 12px;
  }

  .el-form-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
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