<template>
    <div class="background">
        <div class="leftBackground">
            <img src="../assets/img/L-P.jpg">
        </div>
        <div class="chessesBackground" id="div_center" @click="putChess($event)">
            <div class="buttons">
                <table style="border-spacing:0px 30px;">
                    <tr><button id="btn1" class="buttonStyle" @click="beginMatch()">开始匹配</button></tr>
                    <tr><button id="btn2" class="buttonStyle" @click="endMatch()" style="disabled:true;">取消匹配</button></tr>
                </table>
            </div>
        </div>
        <div class="rightBackground">
            <img src="../assets/img/R-P.jpg">
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            chessesType: 0,
            map: Array(19*19).fill(0),
            userNum: 0,  //接入游戏的人数，为双数时游戏开始
            isPut: false,    //判断是否轮到下棋的时刻, 0代表false,1代表true 
        };
    },
    mounted() {

    },
    methods: {
        insertChess(x, y, chessesType) {
            var xPosition = x * 35 + 10;
            var yPosition = y * 35 + 10;

            var suffix = Date.now();
            var htmlValue=`
            <img style=
            "position:absolute; 
            z-index: 999;
            top: ` + yPosition + `px;
            left: ` + xPosition + `px;" 
            id='` + suffix + `'/>
            `
            document.getElementById('div_center').innerHTML += htmlValue;

            document.getElementById(suffix).src = chessesType == 1 
            ? require("../assets/img/black.png") : require("../assets/img/white.png");
        },
        putChess(e) {
            if (this.userNum % 2 != 0 || this.userNum == 0) {
                document.getElementById("div_center").style.cursor="not-allowed";
                console.log("游戏还未开始");
                return ;
            }

            if (!this.isPut) {
                console.log("不到你下棋的时候");
                return ;
            }

            document.getElementById("div_center").style.cursor="default";

            var div = document.getElementById('div_center');
            var xMouse = e.clientX;
            var yMouse = e.clientY;
            var xOffset = div.offsetLeft;
            var yOffset = div.offsetTop;

            /*
                map[x + y * 19]
                xPosition = x * 35 + 10;
                yPosition = y * 35 + 10;
                x：横坐标
                y：纵坐标
            */
            var x = Math.round((xMouse - xOffset) / 35) - 1;
            var y = Math.round((yMouse - yOffset) / 35) - 1;
            x = x > 0 ? x : 0;
            y = y > 0 ? y : 0;

            if (this.map[x + y * 19] != 0) {
                console.log("此位置已经有棋子");
                return ;
            }

            this.map[x + y * 19] = this.chessesType;

            this.insertChess(x, y, this.chessesType);

            var params = new FormData();
            params.append("map", this.map);
            params.append("x", x);
            params.append("y", y);
            params.append("chessesType", this.chessesType);

            this.isPut = false;

            axios.post("http://localhost:9001/post/putChess", params)
            .then(res => {
                if (res.data.win) {
                    alert("获胜者是---" + (res.data.isWinPlayer == 1 ? "黑方" : "白方"));
                    return ;
                }

                this.isPut = true;
                this.map[res.data.x + res.data.y * 19] = (this.chessesType == 1 ? 2 : 1);
                this.insertChess(res.data.x, res.data.y, (this.chessesType == 1 ? 2 : 1));
            }) 
        },
        beginMatch() {
            axios
                .get("http://localhost:9001/get/redis/getUserNum")
                .then(res => {
                    this.userNum=res.data;
                    console.log(this.userNum);
                    
                    if (this.userNum % 2 == 0 && this.userNum != 0) {
                        document.getElementById("btn1").style.display="none";
                        document.getElementById("btn2").style.display="none";
                        document.getElementById("div_center").style.cursor="not-allowed";
                        this.chessesType=2;
                        axios.get("http://localhost:9001/get/getPut?chessesType=" + this.chessesType)
                        .then(res => {
                            this.isPut = true;
                            this.map[res.data.x + res.data.y * 19] = (this.chessesType == 1 ? 2 : 1);
                            this.insertChess(res.data.x, res.data.y, (this.chessesType == 1 ? 2 : 1));
                        });
                    } else {
                        this.chessesType=1;
                        document.getElementById("btn1").disabled=true;
                        document.getElementById("btn2").disabled=false;
                        document.getElementById("btn1").style.backgroundColor="grey";
                        document.getElementById("btn2").style.backgroundColor="rgb(243, 177, 101)";
                        axios.get("http://localhost:9001/get/gameIsBegin")
                            .then((res) => {
                                if (res.data) { //游戏开始
                                    this.isPut = true;
                                    this.userNum += 1;  //另一位玩家进入游戏后，游戏人数+1
                                    document.getElementById("btn1").style.display="none";
                                    document.getElementById("btn2").style.display="none";
                                    document.getElementById("div_center").style.cursor="not-allowed";
                                } else {    //取消匹配

                                }
                            });
                    }
                });
        },
        endMatch() {
            axios
                .get("http://localhost:9001/get/redis/deleteUserNum")
                .then(res => {
                    console.log(res.data);
                    this.userNum = res.data;
                    this.chessesType = 0;
                    document.getElementById("btn1").disabled=false;
                    document.getElementById("btn2").disabled=true;
                    document.getElementById("btn1").style.backgroundColor="rgb(243, 177, 101)";
                    document.getElementById("btn2").style.backgroundColor="grey";
                });
        }
    }
}

</script>


<style scoped>
    .background{
        display: flex;
        background: #d9d8dd;
        z-index: 1;
    }
    .chessesBackground {
        position:relative;
        z-index: 2;
        background-image: url('../assets/img/19-19.jpg');
        margin: auto;
        width: 683px;
        height: 683px;
        overflow: hidden;
    }
    .leftBackground {
        height: 100%;
        overflow: hidden;
    }
    .rightBackground {
        height: 100%;
        overflow: hidden;
    }
    .buttonStyle {
        display: inline-block;
        padding: 15px 25px;
        font-size: 24px;
        cursor: pointer;
        text-align: center;   
        text-decoration: none;
        outline: none;
        color: #fff;
        background-color: rgb(243, 177, 101);
        border: none;
        border-radius: 15px;
        box-shadow: 0 9px #999;
    }

    .buttonStyle:hover {background-color: #3e8e41}

    .buttonStyle:active {
        background-color: #3e8e41;
        box-shadow: 0 5px #666;
        transform: translateY(4px);
    }

    .buttons {
        margin: 0 auto;
        /* div 宽度匹配内容 */
        width: fit-content; 
        margin-top: 200px;
    }

    #btn2 {
        background-color:gray;
    }
</style>