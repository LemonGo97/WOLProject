<template>
  <el-dialog :title='(uuid?"修改":"增加") + "服务"' :visible.sync="visible" :close-on-click-modal="false" :before-close="close">
    点击确定后服务器会自动生成 clientId 和 clientKey，请注意保存并配置到您的客户端中
    <el-form :model="service">
      <el-form-item label="服务名称" :label-width="formLabelWidth">
        <el-input v-model="service.name" autocomplete="off" style="width: 100%"></el-input>
      </el-form-item>
      <el-form-item label="描述" :label-width="formLabelWidth">
      <el-input v-model="service.description" type="textarea" autocomplete="off" style="width: 100%"></el-input>
    </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="submitPopWindow">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getOne, save, modify } from '@/api/service'

export default {
  name: 'ServiceDialog',
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
    if (this.uuid) {
      getOne(this.uuid).then(response => {
        this.service = response.data
      })
    }
  },
  data() {
    return {
      formLabelWidth: '100px',
      popWindowTitle: '',
      service: {}
    }
  },
  methods: {
    close() {
      this.$emit('closePopWindow')
    },
    submitPopWindow() {
      if (this.uuid) {
        modify(this.service).then(response => {
          this.close()
        })
      } else {
        save(this.service).then(response => {
          this.close()
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
