<template>
  <el-dialog title='详情' :visible.sync="visible" :close-on-click-modal="false" :before-close="close"  width="40%">
    <el-form :model="service">
      <el-form-item label="唤醒机名称" :label-width="formLabelWidth">
        {{service.name}}
      </el-form-item>
      <el-form-item label="IP" :label-width="formLabelWidth">
        {{service.ipAddress}}
      </el-form-item>
      <el-form-item label="mac地址" :label-width="formLabelWidth">
        {{service.macAddress}}
      </el-form-item>
      <el-form-item label="创建时间" :label-width="formLabelWidth">
        {{service.createTime}}
      </el-form-item>
      <el-form-item label="描述" :label-width="formLabelWidth">
        {{service.description}}
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getOne } from '@/api/machine'

export default {
  name: 'machineDialog',
  props: {
    visible: {
      type: Boolean
    },
    uuid: {
      type: String,
      default: undefined
    }
  },
  created() {
    getOne(this.uuid).then(response => {
      this.service = response.data
    })
  },
  data() {
    return {
      formLabelWidth: '150px',
      popWindowTitle: '',
      service: {
        userId: this.$store.state.user.userId
      }
    }
  },
  methods: {
    close() {
      this.$emit('closePopWindow')
    },
    copy(data) {
      //创建一个input元素
      let input = document.createElement('input') 
      //给input的内容复制
      input.value = data   
      // 在body里面插入这个元素
      document.body.appendChild(input)   
      // 选中input里面内容
      input.select()  
      //执行document里面的复制方法
      document.execCommand("Copy") 
      // 复制之后移除这个元素
      document.body.removeChild(input)
   }
  }
}
</script>

<style scoped>

</style>
