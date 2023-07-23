<template>
    <div>        
        <div style="display: flex">
            <div>
                <img src="../assets/img/OIP-C.jpg" />
            </div>
            <div v-if="user.username == undefined">
                <router-link to="/login">登  录 | </router-link>
                <router-link to="/register">注  册</router-link>
            </div>
            <div v-else>
                <p>{{ user.username }}</p>
            </div>
        </div>
        <hr>
        <div>
            <carousel-3d v-if="slides.length"
                :autoplay="true"
                :autoplayTimeout="3000"
                :display="5"
                :width="480"
                :height="400"
                :animationSpeed="1000">
                <slide v-for="(slide, i) in slides" :key="i" :index="i" class="slideClass">
                    <img :src="slide.src" style="height: 234px"/>
                    <div class="gameDescription">
                        <a href="javascript:void(0);" @click="jump(slide.ipAddr)">{{ slide.description }}</a>
                    </div>
                </slide>
            </carousel-3d>
        </div>

    </div>
</template>

<script>
import { useRoute } from 'vue-router';

export default {
    setup() {
        var user = {
            id: "",
            username: "",
        };

        const router = useRoute();

        user.id = router.query.id;
        user.username = router.query.username;

        console.log(user.id + ":" + user.username);

        return {
            user
        }
    },
    data() {
        return {
            slides: [
                {
                    src: require('../assets/img/chessPicture.jpg'), 
                    description: "五子棋", 
                    ipAddr: "/FiveChessView"
                },
                {
                    src: require('../assets/img/src=http _i0.hdslb.com_bfs_article_668f48bad06d272bfbbc11cc305834319ae63d5d.jpg&refer=http _i0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg.jpg'),
                    description: "一张图片",
                    ipAddr: "/"
                },
                {
                    src: require('../assets/img/src=http _i0.hdslb.com_bfs_article_668f48bad06d272bfbbc11cc305834319ae63d5d.jpg&refer=http _i0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg.jpg'),
                    description: "一张图片",
                    ipAddr: "/"                
                }
            ],
        };
    }, 
    methods: {
        jump(ipAddr) {
            if (!this.user.id) {
                alert("请先登录！");
                this.$router.push({path: '/login'});
                return ;
            }
            this.$router.push({path: ipAddr, query: {id: this.user.id, username: this.user.username}});
        }
    }
};
</script>

<style scope>
    .gameDescription{
        text-align: center;
        margin-top: 40px;
    }
    .slideClass{
        background-color: white;
        border: none;
    }
</style>