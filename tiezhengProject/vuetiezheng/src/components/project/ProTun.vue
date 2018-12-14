<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="通过员工名搜索员工,记得回车哦..."
            clearable
            @change="keywordsChange"
            style="width: 300px;margin: 0px;padding: 0px;"
            size="mini"
            :disabled="advanceSearchViewVisible"
            @keyup.enter.native="searchEmp"
            prefix-icon="el-icon-search"
            v-model="keywords">
          </el-input>
          <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search" @click="searchEmp">搜索
          </el-button>
          <el-button slot="reference" type="primary" size="mini" style="margin-left: 5px"
                     @click="showAdvanceSearchView"><i
            class="fa fa-lg" v-bind:class="[advanceSearchViewVisible ? faangledoubleup:faangledoubledown]"
            style="margin-right: 5px"></i>高级搜索
          </el-button>
        </div>
        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddTunnelView">
            添加隧道
          </el-button>
        </div>
      </el-header>
      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          <transition name="slide-fade">
            <div
              style="margin-bottom: 10px;border: 1px;border-radius: 5px;border-style: solid;padding: 5px 0px 5px 0px;box-sizing:border-box;border-color: #20a0ff"
              v-show="advanceSearchViewVisible">
              <el-row>
                <el-col :span="5">
                  政治面貌:
                  <el-select v-model="emp.politicId" style="width: 130px" size="mini" placeholder="政治面貌">
                    <el-option
                      v-for="item in politics"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="4">
                  民族:
                  <el-select v-model="emp.nationId" style="width: 130px" size="mini" placeholder="请选择民族">
                    <el-option
                      v-for="item in nations"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="4">
                  职位:
                  <el-select v-model="emp.posId" style="width: 130px" size="mini" placeholder="请选择职位">
                    <el-option
                      v-for="item in positions"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="4">
                  职称:
                  <el-select v-model="emp.jobLevelId" style="width: 130px" size="mini" placeholder="请选择职称">
                    <el-option
                      v-for="item in joblevels"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="7">
                  聘用形式:
                  <el-radio-group v-model="emp.engageForm">
                    <el-radio label="劳动合同">劳动合同</el-radio>
                    <el-radio style="margin-left: 15px" label="劳务合同">劳务合同</el-radio>
                  </el-radio-group>
                </el-col>
              </el-row>
              <el-row style="margin-top: 10px">
                <el-col :span="5">
                  所属部门:
                  <el-popover
                    v-model="showOrHidePop2"
                    placement="right"
                    title="请选择部门"
                    trigger="manual">
                    <el-tree :data="deps" :default-expand-all="true" :props="defaultProps" :expand-on-click-node="false"
                             @node-click="handleNodeClick2"></el-tree>
                    <div slot="reference"
                         style="width: 130px;height: 26px;display: inline-flex;font-size:13px;border: 1px;border-radius: 5px;border-style: solid;padding-left: 13px;box-sizing:border-box;border-color: #dcdfe6;cursor: pointer;align-items: center"
                         @click="showDepTree2" v-bind:style="{color: depTextColor}">{{emp.departmentName}}
                    </div>
                  </el-popover>
                </el-col>
                <el-col :span="10">
                  入职日期:
                  <el-date-picker
                    v-model="beginDateScope"
                    unlink-panels
                    size="mini"
                    type="daterange"
                    value-format="yyyy-MM-dd"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期">
                  </el-date-picker>
                </el-col>
                <el-col :span="5" :offset="4">
                  <el-button size="mini" @click="cancelSearch">取消</el-button>
                  <el-button icon="el-icon-search" type="primary" size="mini" @click="searchEmp">搜索</el-button>
                </el-col>
              </el-row>
            </div>
          </transition>
          <el-table
            :data="tunnels"
            v-loading="tableLoading"
            border
            stripe
            @selection-change="handleSelectionChange"
            size="mini"
            style="width: 100%">
            <el-table-column
              type="selection"
              align="left"
              width="30">
            </el-table-column>
            <el-table-column
              prop="id"
              align="left"
              fixed
              label="序号"
              width="50">
            </el-table-column>
            <el-table-column
              prop="name"
              width="250"
              align="left"
              label="隧道名称">
            </el-table-column>
            <el-table-column
              prop="address"
              label="隧道地址"
              width="250">
            </el-table-column>
           <el-table-column
              prop="km"
              label="隧道里程"
              width="250">
            </el-table-column>
            <el-table-column
              prop="geology"
              label="地质类型" 
              width="250">
            </el-table-column>
            <el-table-column
              prop="geology"
              label="支护方式" 
              width="250">
            </el-table-column>
            <el-table-column
              prop="supportType"
              label="地质类型" 
              width="250">
            </el-table-column>
            <el-table-column
              prop="supportType"
              label="参数" 
              width="250">
            </el-table-column>
            <el-table-column
              prop="excavationWay"
              label="开挖方式">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="250">
              <template slot-scope="scope">
                <el-button @click="showEditTunnelView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
                <el-button style="padding: 3px 4px 3px 4px;margin: 2px" type="primary" @click="detail(scope.row)"
                           size="mini">详情
                </el-button>
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="deleteTunnel(scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <el-button type="danger" size="mini" v-if="tunnels.length>0" :disabled="multipleSelection.length==0"
                       @click="deleteManyTunnels">批量删除
            </el-button>
            <el-pagination
              background
              :page-size="10"
              :current-page="currentPage"
              @current-change="currentChange"
              layout="prev, pager, next"
              :total="totalCount">
            </el-pagination>
          </div>
        </div>
      </el-main>
    </el-container>
    <el-form :model="tunnel" ref="addTunnelForm" style="margin: 0px;padding: 0px;">
      <div style="text-align: left">
        <el-dialog
          :title="dialogTitle"
          style="padding: 0px;"
          :close-on-click-modal="false"
          :visible.sync="dialogVisible"
          width="40%">
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="标段:" prop="bidId">
                  <el-select v-model="tunnel.bidId" style="width: 150px" size="mini" placeholder="请选择标段">
                    <el-option
                      v-for="item in bids"
                      :key="item.id"
                      :label="item.bidName"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
           <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="隧道名称:" prop="name">
                  <el-input prefix-icon="el-icon-edit" v-model="tunnel.name" size="mini" style="width: 150px"
                            placeholder="隧道名称"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
           <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="隧道地址:" prop="address">
                  <el-input prefix-icon="el-icon-edit" v-model="tunnel.address" size="mini" style="width: 150px"
                            placeholder="隧道地址"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
           <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="隧道里程:" prop="km">
                  <el-input prefix-icon="el-icon-edit" v-model="tunnel.km" size="mini" style="width: 150px"
                            placeholder="隧道里程"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
           <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="地址类型:" prop="geology">
                  <el-input prefix-icon="el-icon-edit" v-model="tunnel.geology" size="mini" style="width: 150px"
                            placeholder="地址类型"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
           <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="支护方式:" prop="supportType">
                  <el-input prefix-icon="el-icon-edit" v-model="tunnel.supportType" size="mini" style="width: 150px"
                            placeholder="支护方式"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
           <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="参数:" prop="parameter">
                  <el-input prefix-icon="el-icon-edit" v-model="tunnel.parameter" size="mini" style="width: 150px"
                            placeholder="参数"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
           <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="开挖方式:" prop="excavationWay">
                  <el-input prefix-icon="el-icon-edit" v-model="tunnel.excavationWay" size="mini" style="width: 150px"
                            placeholder="开挖方式"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
    <el-button size="mini" @click="cancelEidt">取 消</el-button>
    <el-button size="mini" type="primary" @click="addTunnel('addTunnelForm')">确 定</el-button>
  </span>
        </el-dialog>
      </div>
    </el-form>
    <el-dialog
  title="隧道详情"
  :visible.sync="centerDialogVisible"
  width="30%"
  center>
  <div>
    <el-card class="box-card" :visible.sync="dialogVisible">
  <div slot="header" class="clearfix">
    <span>项目信息</span>
  </div>
  <el-form ref="form" :model="companys" label-width="80px">
  <el-form-item v-for="item in companys"
                      :key="item.id"
                      :value="item.id">
    建设单位：<el-input v-model="item.name" disabled="false"></el-input>
  </el-form-item>
</el-form>
</el-card>
<el-card class="box-card" :visible.sync="dialogVisible">
  <div slot="header" class="clearfix">
    <span>隧道信息</span>
  </div>
  <div v-for="o in 4" :key="o" class="text item">
    {{'列表内容 ' + o }}
  </div>
</el-card>
<el-card class="box-card" :visible.sync="dialogVisible">
  <div slot="header" class="clearfix">
    <span>参见单位</span>
  </div>
  <div v-for="o in 4" :key="o" class="text item">
    {{'列表内容 ' + o }}
  </div>
</el-card>
  </div>
  <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
  </span>
  
</el-dialog>
    
  </div>
</template>
<script>
  export default {
    data() {
      return {
        centerDialogVisible: false,
        tunnels: [],//隧道列表
        bids:[],
        companys:[],
        keywords: '',
        fileUploadBtnText: '导入数据',
        beginDateScope: '',
        faangledoubleup: 'fa-angle-double-up',
        faangledoubledown: 'fa-angle-double-down',
        dialogTitle: '',
        multipleSelection: [],
        depTextColor: '#c0c4cc',
        nations: [],
        politics: [],
        positions: [],
        joblevels: [],
        totalCount: -1,
        currentPage: 1,
        degrees: [{id: 4, name: '大专'}, {id: 5, name: '本科'}, {id: 6, name: '硕士'}, {id: 7, name: '博士'}, {
          id: 3,
          name: '高中'
        }, {id: 2, name: '初中'}, {id: 1, name: '小学'}, {id: 8, name: '其他'}],
        deps: [],
        defaultProps: {
          label: 'name',
          isLeaf: 'leaf',
          children: 'children'
        },
        dialogVisible: false,
        tableLoading: false,
        advanceSearchViewVisible: false,
        showOrHidePop: false,
        showOrHidePop2: false,
        tunnel:{
          id:'',
          bidId:'',
          name:'',
          address:'',
          km:'',
          geology:'',
          supportType:'',
          parameter:'',
          excavationWay:''
        },
        emp: {
          name: '',
          gender: '',
          birthday: '',
          idCard: '',
          wedlock: '',
          nationId: '',
          nativePlace: '',
          politicId: '',
          email: '',
          phone: '',
          address: '',
          departmentId: '',
          departmentName: '所属部门...',
          jobLevelId: '',
          posId: '',
          engageForm: '',
          tiptopDegree: '',
          specialty: '',
          school: '',
          beginDate: '',
          workState: '',
          workID: '',
          contractTerm: '',
          conversionTime: '',
          notWorkDate: '',
          beginContract: '',
          endContract: '',
          workAge: ''
        },
         form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        }
      };
    },
    mounted: function () {
      this.initData();
      this.loadTunnels();
    },
    methods: {
      fileUploadSuccess(response, file, fileList){
        if (response) {
          this.$message({type: response.status, message: response.msg});
        }
        this.loadEmps();
        this.fileUploadBtnText = '导入数据';
      },
      fileUploadError(err, file, fileList){
        this.$message({type: 'error', message: "导入失败!"});
        this.fileUploadBtnText = '导入数据';
      },
      beforeFileUpload(file){
        this.fileUploadBtnText = '正在导入';
      },
      exportEmps(){
        window.open("/employee/basic/exportEmp", "_parent");
      },
      cancelSearch(){
        this.advanceSearchViewVisible = false;
        this.emptyEmpData();
        this.beginDateScope = '';
        this.loadEmps();
      },
      showAdvanceSearchView(){
        this.advanceSearchViewVisible = !this.advanceSearchViewVisible;
        this.keywords = '';
        if (!this.advanceSearchViewVisible) {
          this.emptyEmpData();
          this.beginDateScope = '';
          this.loadEmps();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      deleteManyTunnels(){
        this.$confirm('此操作将删除[' + this.multipleSelection.length + ']条数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var ids = '';
          for (var i = 0; i < this.multipleSelection.length; i++) {
            ids += this.multipleSelection[i].id + ",";
          }
          this.doDelete(ids);
        }).catch(() => {
        });
      },
      deleteTunnel(row){
        this.$confirm('此操作将永久删除[' + row.name + '], 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.doDelete(row.id);
        }).catch(() => {
        });
      },
      doDelete(ids){
        this.tableLoading = true;
        var _this = this;
        this.deleteRequest("/tunnel/deleteTunnel/" + ids).then(resp=> {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _
            _this.loadTunnels();
          }
        })
      },
      keywordsChange(val){
        if (val == '') {
          this.loadTunnelsy();
        }
      },
      searchEmp(){
        this.loadEmps();
      },
      currentChange(currentChange){
        this.currentPage = currentChange;
        this.loadTunnels();
      },
      loadTunnels(){
        var _this = this;
        this.tableLoading = true;
        this.getRequest("/tunnel/getTunnelsByPage?page=" + this.currentPage + "&size=10" ).then(resp=> {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.tunnels = data.tunnels;
            _this.totalCount = data.count;
           _this.emptyTunnelData();
          }
        })
      },
      addTunnel(formName){
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.tunnel.id) {
              //更新
              this.tableLoading = true;
              this.putRequest("/tunnel/updateTunnel", this.tunnel).then(resp=> {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyTunnelData();
                  _this.loadTunnels();
                }
              })
            } else {
              //添加
              this.tableLoading = true;
              this.postRequest("/tunnel/addTunnel", this.tunnel).then(resp=> {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyTunnelData();
                  _this.loadTunnels();
                }
              })
            }
          } else {
            return false;
          }
        });
      },
      cancelEidt(){
        this.dialogVisible = false;
        this.emptyTunnelData();
      },
      showDepTree(){
        this.showOrHidePop = !this.showOrHidePop;
      },
      showDepTree2(){
        this.showOrHidePop2 = !this.showOrHidePop2;
      },
      handleNodeClick(data) {
        this.emp.departmentName = data.name;
        this.emp.departmentId = data.id;
        this.showOrHidePop = false;
        this.depTextColor = '#606266';
      },
      handleNodeClick2(data) {
        this.emp.departmentName = data.name;
        this.emp.departmentId = data.id;
        this.showOrHidePop2 = false;
        this.depTextColor = '#606266';
      },
      initData(){
        var _this = this;
        this.getRequest("/bid/getBasicData").then(resp=> {
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.bids = data.bids;
            _this.companys = data.companys;
          }
        })
      },
      showEditTunnelView(row){
        this.dialogTitle = "编辑隧道";
        this.tunnel = row;
        this.tunnel.bidId = row.bidId;
        this.tunnel.name = row.name;
        this.tunnel.address = row.address;
        this.tunnel.km = row.km;
        this.tunnel.geology = row.geology;
        this.tunnel.supportType = row.supportType;
        this.tunnel.parameter = row.parameter;
        this.tunnel.excavationWay = row.excavationWay;
        this.dialogVisible = true;
      },
      showAddTunnelView(){
        this.dialogTitle = "添加隧道";
        this.dialogVisible = true;
        // var _this = this;
        // this.getRequest("/employee/basic/maxWorkID").then(resp=> {
        //   if (resp && resp.status == 200) {
        //     _this.emp.workID = resp.data;
        //   }
        // })
      },
      emptyTunnelData(){
        this.tunnel = {
          id: '',
          bidId: '',
          name: '',
          address: '',
          km: '',
          geology: '',
          supportType: '',
          parameter: '',
          excavationWay: ''
        }
      },
      detail(row){
        console.log("id >>> "+row.id);
        this.centerDialogVisible = true;

      }
    }
  };
</script>
<style>
  .el-dialog__body {
    padding-top: 0px;
    padding-bottom: 0px;
  }

  .slide-fade-enter-active {
    transition: all .8s ease;
  }

  .slide-fade-leave-active {
    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }

  .slide-fade-enter, .slide-fade-leave-to {
    transform: translateX(10px);
    opacity: 0;
  }
   .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
  }
</style>
