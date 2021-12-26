<template>
    <ul class="macAdress">
        <li v-for="(item,index) in macAddress" :key="index">
            <input ref="ipInput" v-model="item.value" type="text" @input="checkIpVal(item)" @keyup="turnIpPosition(item,index,$event)"/>
            <div>：</div>
        </li>
    </ul>
</template>

<script>
export default {
    props: {
        value: {
            type: String,
            default: ''
        }
    },
    data() {
        return {
            macAddress: [
                {
                    value: ''
                },{
                    value: ''
                },{
                    value: ''
                },{
                    value: ''
                },{
                    value: ''
                },{
                    value: ''
                }
            ]
        }
    },
    watch: {
        macAddress: { // 双向数据绑定的value
            handler: function (newVal, oldVal) {
                let str = ''
                for (const i in this.macAddress) {
                    str += this.macAddress[i].value + ':'
                }
                this.$emit('input', str)
            },
            deep: true
        }
    },
    created() {
        setTimeout(()=> {
            this.macAddress.map((item,index) => {
                item.value = this.value.split(":")[index]
                return item.value
            })
        },500)
    },
    methods: {
        // 检查ip输入为0-255
        checkIpVal(item) {
            // //确保每个值都处于0-255
            // let val = item.value;
            // // 处理非数字
            // val = val.toString().replace(/[^0-9]/g,'');
            // val = parseInt(val, 10);
            // if(isNaN(val)) {
            //     val = ''
            // } else {
            //     val = val < 0 ? 0 : val;
            //     val = val > 255 ? 255 : val;
            // }
            // item.value = val;
        },
        // 光标位置判断
        turnIpPosition(item, index, event) {
            let self = this;
            let e = event || window.event;
            if (e.keyCode === 37) { // 左箭头向左跳转，左一不做任何措施
                if(index !== 0 && e.currentTarget.selectionStart === 0) {
                    self.$refs.ipInput[index - 1].focus();
                }
            } else if (e.keyCode == 39) { // 右箭头向右跳转，右一不做任何措施
                if(index !== 3 && e.currentTarget.selectionStart === item.value.toString().length) {
                    self.$refs.ipInput[index + 1].focus();
                }
            } else if (e.keyCode === 8) { // 删除键把当前数据删除完毕后会跳转到前一个input，左一不做任何处理
                if(index !== 0 && item.value === '') {
                    self.$refs.ipInput[index - 1].focus();
                }
            } else if (e.keyCode === 13 || e.keyCode === 32 || e.keyCode === 190) {// 回车键、空格键、冒号均向右跳转，右一不做任何措施
                if(index !== 5) {
                    self.$refs.ipInput[index + 1].focus();
                }
            } else if (item.value.toString().length === 2) { // 满3位，光标自动向下一个文本框
                if(index !== 5) {
                    self.$refs.ipInput[index + 1].focus();
                }
            }
        }
    }
}
</script>
<style type="text/css" scoped>
    .macAdress{
        width: 100%;
        display: flex;
        border: 1px solid #dcdfe6;
        border-radius: 4px;
        height: 40px;
        padding-inline-start: 0px;
        margin: 0;
    }
    .macAdress li{
        flex: 1;
        /* position: relative; */
        height: 36px;
        margin: 0;
        display: flex;
    }
    .macAdress li input{
        flex: 1;
        width: 20px;
        border: none;
        height: 40px;
        text-align: center;
        background: transparent;
    }
    /*只需要3个div*/
    .macAdress li:last-child div{
        display: none;
    }
    /*取消掉默认的input focus状态*/
    .macAdress input:focus{
        outline: none;
    }
</style>
