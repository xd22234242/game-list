<template>
    <div class="register">
        <el-row :span="12" justify="center">
            <el-form ref="/login" :model="user">
                <h3>注    册</h3>
                <hr>
                <el-form-item label="用户名">
                    <el-input v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item label="密  码">
                    <el-input v-model="user.password"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="doRegister()">注  册</el-button>
                </el-form-item>
            </el-form>
        </el-row>
    </div>
</template>

<script>

import axios from "axios"

export default {
    data() {
        return {
            user: {
                username: "",
                password: ""
            }
        }
    },
    methods: {
        doRegister() {
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
                axios
                    .post("http://localhost:9001/post/register", params)
                    .then(res => {
                        if (res.data) {
                            this.$router.push({ path: "/", query: {
                                id: res.data.id,
                                username: res.data.username
                            }});
                        } else {
                            alert("用户名已存在");
                        }
                    });
            }
        }
    }
}
</script>