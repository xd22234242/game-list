<template>
  <div class="login" clearfix>
    <div class="login-wrap">
      <el-row type="flex" justify="center">
        <el-form ref="loginForm" :model="user">
          <h3 style="text-align: center">登录</h3>
          <hr>
          <el-form-item prop="username" label="username">
            <el-input v-model="user.username" placeholder="请输入用户名" prefix-icon></el-input>
          </el-form-item>
          <el-form-item id="password" prop="password" label="password:">
            <el-input v-model="user.password" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <div style="text-align:center;">
            <router-link to="/">找回密码</router-link>
            <router-link to="/register">注册账号</router-link> 
          </div>
          <el-form-item>
            <el-button type="primary" icon="el-icon-upload" @click="doLogin()">登 录</el-button>
          </el-form-item>
        </el-form>
      </el-row>
    </div>
    <router-view :key="$route.fullPath" />
  </div>
</template>
 
<script>
import axios from "axios";
export default {
  name: "loginView",
  data() {
    return {
      user: {
        username: "",
        password: ""
      }  
    };
  },
  created() {},
  methods: {
    doLogin() {
      if (!this.user.username) {
        this.$message.error("请输入用户名！");
        return;
      } else if (!this.user.password) {
        this.$message.error("请输入密码！");
        return;
      } else {
        var params = new FormData();

        params.append("username", this.user.username);
        params.append("password", this.user.password);

        //校验用户名和密码是否正确;
        axios
          .post("http://localhost:9001/post/login", params)
          .then(res => {
            if (res.data) {
              this.$router.push({ path: "/", query: {
                id: res.data.id,
                username: res.data.username
              }});
            } else {
              alert("您输入的用户名或密码错误！");
            }
          });
      }
    }
  }
};
</script>
 
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.login {
  width: 100%;
  background: url("../assets/img/loginBackground.jpg") no-repeat;
  background-size: cover;
  overflow: hidden;
  background-size: 100% 100%;
  position: fixed;
}
.login-wrap {
  /* background: url("../assets/img/loginBackground.jpg") no-repeat; */
  background-size: cover;
  width: 400px;
  height: 300px;
  margin: 215px auto;
  overflow: hidden;
  padding-top: 10px;
  line-height: 40px;
}
#password {
  margin-bottom: 5px;
}
h3 {
  color: #0babeab8;
  font-size: 24px;
}
hr {
  background-color: #444;
  margin: 20px auto;
}
a {
  text-decoration: none;
  color: #aaa;
  font-size: 15px;
}
a:hover {
  color: coral;
}
.el-button {
  width: 60%;
  margin: auto;
}
</style>