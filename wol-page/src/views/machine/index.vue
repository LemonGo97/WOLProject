<template>
  <div class="app-container">
    <div class="jumbotron">
      <h1><svg-icon icon-class="rocketmq" />&nbsp;&nbsp;唤醒机管理</h1>
      <p>服务管理的简介服务管理的简介服务管理的简介服务管理的简介服务管理的简介服务管理的简介服务管理的简介服务管理的简介服务管理的简介服务管理的简介</p>
    </div>
    <div class="filter-container" style="margin-top: 10px">
      <el-button icon="el-icon-refresh" @click="refreshContent()">刷新</el-button>
      <el-button icon="el-icon-circle-plus-outline" type="primary" @click="handleEdit({})">添加唤醒机</el-button>
    </div>
    <el-table
      :data="tableData"
      border
      style="width: 100%;margin-top: 10px">
      <el-table-column
        align="center"
        prop="name"
        label="唤醒机名称">
      </el-table-column>
      <el-table-column
        align="center"
        prop="ipAddress"
        label="IP"
        width="160">
      </el-table-column>
      <el-table-column
        align="center"
        prop="macAddress"
        label="mac地址"
        width="160">
      </el-table-column>
      <el-table-column
        align="center"
        prop="createTime"
        label="创建时间"
        width="160"
      >
      </el-table-column>
      <el-table-column
        align="center"
        prop="updateTime"
        label="更新时间"
        width="160"
      >
      </el-table-column>
      <el-table-column
        align="center"
        prop="description"
        label="描述"
        :show-overflow-tooltip="true"
      >
      </el-table-column>
      <el-table-column
        align="center"
        label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            icon="el-icon-search"
            type="info"
            @click="handleDetail(scope.row)">详情
          </el-button>
          <el-button
            size="mini"
            icon="el-icon-search"
            type="success"
            @click="handleWake(scope.row)">唤醒
          </el-button>
          <el-button
            size="mini"
            icon="el-icon-edit"
            type="primary"
            @click="handleEdit(scope.row)">修改
          </el-button>
          <el-popconfirm
            style="margin-left: 10px"
            title="确定删除此服务器？"
            @onConfirm="handleDelete(scope.row)"
          >
            <el-button
              size="mini"
              type="danger"
              icon="el-icon-delete"
              slot="reference">
              删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="margin-top: 10px"
      layout="total, sizes, prev, pager, next, jumper"
      :current-page="currentPage"
      :page-sizes="[20, 50, 100, 200, 300, 400]"
      :page-size="pageSize"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange">
    </el-pagination>
    <machine-dialog v-if="Dioslog.popWindowVisible" :uuid="uuid" :visible.sync="Dioslog.popWindowVisible" @closePopWindow="closePopWindow"></machine-dialog>
    <machine-detail v-if="Dioslog.DetailVisible" :uuid="uuid" :visible.sync="Dioslog.DetailVisible" @closePopWindow="closePopWindow"></machine-detail>
  </div>
</template>

<script>
import { getList, remove } from '@/api/machine'
import MachineDialog from '@/views/machine/dialog/machineDialog'
import MachineDetail from '@/views/machine/dialog/machineDetail'
export default {
  components: { MachineDialog, MachineDetail },
  inject: ['reload'],
  data() {
    return {
      tableData: [],
      total: 0,
      Dioslog: {
        popWindowVisible: false,
        DetailVisible: false
      },
      currentPage: 1,
      pageSize: 20,
      uuid: undefined
    }
  },
  created() {
    this.refreshContent()
  },
  methods: {
    refreshContent() {
      this.fetchData()
    },
    fetchData() {
      this.listLoading = true
      getList({ userId: 1, size: this.pageSize, current: this.currentPage }).then(response => {
        this.tableData = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleEdit(row) {
      if(row.id) {
        this.uuid = row.id
      }
      this.Dioslog.popWindowVisible = true
    },
    handleDetail(row) {
      console.log(row)
      this.uuid = row.id
      this.Dioslog.DetailVisible = true
    },
    handleWake(row) {
      alert(JSON.stringify(row.id))
    },
    handleDelete(row) {
      remove(row.id).then(response => {
        this.refreshContent()
      })
    },
    handleSizeChange(val) {
      this.pageSize = val
      console.log(`每页 ${this.pageSize} 条`)
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      console.log(`当前页: ${this.currentPage}`)
      this.fetchData()
    },
    closePopWindow() {
      this.uuid = ''
      for(let key in this.Dioslog) {
        this.Dioslog[key] = false
      }
      this.refreshContent()
    }
  }
}
</script>

<style scoped>
.line {
  text-align: center;
}

.jumbotron {
  -webkit-text-size-adjust: 100%;
  -webkit-tap-highlight-color: rgba(0,0,0,0);
  font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
  font-size: 14px;
  line-height: 1.42857143;
  box-sizing: border-box;
  color: #FFFFFF;
  padding-top: 30px;
  padding-bottom: 30px;
  border-radius: 6px;
  padding-right: 60px;
  padding-left: 60px;
  margin-bottom: 0;
  background-color: #455A64;
}

.jumbotron > h1 {
  font-size: 4em;
}
</style>

<style>
  .el-message-box {
    white-space: pre-line;
    width: 800px;
  }
</style>

