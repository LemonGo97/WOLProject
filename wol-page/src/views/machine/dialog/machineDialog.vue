<template>
  <el-dialog :title='(uuid?"修改":"增加") + "唤醒机"' :visible.sync="visible" :close-on-click-modal="false" :before-close="close"  width="40%">
    <!-- 点击确定后服务器会自动生成 clientId 和 clientKey，请注意保存并配置到您的客户端中 -->
    <el-form :model="service">
      <el-form-item label="唤醒机名称" :label-width="formLabelWidth">
        <el-input v-model="service.name" autocomplete="off" style="width: 100%"></el-input>
      </el-form-item>
      <el-form-item label="IP" :label-width="formLabelWidth">
        <IPAddress ref="ip" v-model="service.ipAddress" :value="service.ipAddress"></IPAddress>
        <!-- <el-input v-model="service.ipAddress" autocomplete="off" style="width: 100%"></el-input> -->
      </el-form-item>
      <el-form-item label="mac地址" :label-width="formLabelWidth">
        <MacAddress v-model="service.macAddress" :value="service.macAddress"></MacAddress>
        <!-- <el-input v-model="service.macAddress" autocomplete="off" style="width: 100%"></el-input> -->
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
import { getOne, save, modify } from '@/api/machine'

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
      service: {
        userId: this.$store.state.user.userId
      }
    }
  },
  methods: {
    close() {
      this.$emit('closePopWindow')
    },
    submitPopWindow() {
      this.service.ipAddress = this.service.ipAddress.substr(0,this.service.ipAddress.length-1)
      this.service.macAddress = this.service.macAddress.substr(0,this.service.macAddress.length-1)
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
