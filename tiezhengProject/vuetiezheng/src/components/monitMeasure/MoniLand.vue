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
            @keyup.enter.native="searchLand"
            prefix-icon="el-icon-search"
            v-model="keywords">
          </el-input>
          <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search" @click="searchLand">搜索
          </el-button>
          <el-button slot="reference" type="primary" size="mini" style="margin-left: 5px"
                     @click="showAdvanceSearchView"><i
            class="fa fa-lg" v-bind:class="[advanceSearchViewVisible ? faangledoubleup:faangledoubledown]"
            style="margin-right: 5px"></i>高级搜索
          </el-button>
        </div>
        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddLandView">
            添加地表
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
                  <el-button icon="el-icon-search" type="primary" size="mini" @click="searchLand">搜索</el-button>
                </el-col>
              </el-row>
            </div>
          </transition>
          <el-table
            :data="lands"
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
              prop="tunnelId"
              align="left"
              fixed
              label="隧道id"
              width="50">
            </el-table-column>
            <el-table-column
              prop="stationId"
              align="left"
              fixed
              label="测点id"
              width="90">
            </el-table-column>
            <el-table-column
              prop="observeDate"
              width="85"
              align="left"
              label="观测日期">
            </el-table-column>
            <el-table-column
              prop="measureInterval"
              label="量测间隔"
              width="50">
            </el-table-column>
            <el-table-column
              prop="observeVal"
              width="85"
              align="left"
              label="观测值">
            </el-table-column>
            <el-table-column
              prop="sinkDiffVal"
              width="150"
              align="left"
              label="下沉差值">
            </el-table-column>
            <el-table-column
              prop="sinkSpeed"
              width="70"
              label="下沉速率">
            </el-table-column>
            <el-table-column
              width="50"
              prop="addUpDisplacement"
              label="累计位移">
            </el-table-column>
            <el-table-column
              prop="maxDisplacement"
              width="80"
              label="最大位移">
            </el-table-column>
            <el-table-column
              prop="measureMan"
              width="80"
              label="测量人">
            </el-table-column>
            <el-table-column
              prop="warningState"
              width="180"
              align="left"
              label="预警状态">
            </el-table-column>
            <el-table-column
              prop="distanceFace"
              width="100"
              label="据开挖面距离">
            </el-table-column>
            <el-table-column
              prop="description"
              width="220"
              align="left"
              label="描述">
            </el-table-column>
            <el-table-column
              prop="createDate"
              align="left"
              width="100"
              label="创建日期">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="195">
              <template slot-scope="scope">
                <el-button @click="showEditLandView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
                <el-button style="padding: 3px 4px 3px 4px;margin: 2px" type="primary"
                           size="mini">查看高级资料
                </el-button>
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="deleteLand(scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <el-button type="danger" size="mini" v-if="lands.length>0" :disabled="multipleSelection.length==0"
                       @click="deleteManyLands">批量删除
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
    <el-form :model="land" ref="addLandForm" style="margin: 0px;padding: 0px;">
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
                <el-form-item label="隧道id:" prop="tunnelId">
                  <el-input prefix-icon="el-icon-edit" v-model="land.tunnelId" size="mini" style="width: 150px"
                            placeholder="隧道id"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="测点id:" prop="stationId">
                  <el-input prefix-icon="el-icon-edit" v-model="land.stationId" size="mini" style="width: 150px"
                            placeholder="测点id"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="观测日期:" prop="observeDate">
                  <el-input prefix-icon="el-icon-edit" v-model="land.observeDate" size="mini" style="width: 150px"
                            placeholder="观测日期"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="量测间隔:" prop="measureInterval">
                  <el-input prefix-icon="el-icon-edit" v-model="land.measureInterval" size="mini" style="width: 150px"
                            placeholder="量测间隔"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="观测值:" prop="observeVal">
                  <el-input prefix-icon="el-icon-edit" v-model="land.observeVal" size="mini" style="width: 150px"
                            placeholder="观测值"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="下沉差值:" prop="sinkDiffVal">
                  <el-input prefix-icon="el-icon-edit" v-model="land.sinkDiffVal" size="mini" style="width: 150px"
                            placeholder="下沉差值"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="下沉速率:" prop="sinkSpeed">
                  <el-input prefix-icon="el-icon-edit" v-model="land.sinkSpeed" size="mini" style="width: 150px"
                            placeholder="下沉速率"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="累计位移:" prop="addUpDisplacement">
                  <el-input prefix-icon="el-icon-edit" v-model="land.addUpDisplacement" size="mini" style="width: 150px"
                            placeholder="累计位移"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="最大位移:" prop="maxDisplacement">
                  <el-input prefix-icon="el-icon-edit" v-model="land.maxDisplacement" size="mini" style="width: 150px"
                            placeholder="最大位移"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="测量人:" prop="measureMan">
                  <el-input prefix-icon="el-icon-edit" v-model="land.measureMan" size="mini" style="width: 150px"
                            placeholder="测量人"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="预警状态:" prop="warningState">
                  <el-input prefix-icon="el-icon-edit" v-model="land.warningState" size="mini" style="width: 150px"
                            placeholder="预警状态"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
           <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="据开挖面距离:" prop="distanceFace">
                  <el-input prefix-icon="el-icon-edit" v-model="land.distanceFace" size="mini" style="width: 150px"
                            placeholder="据开挖面距离"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
           <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="描述:" prop="description">
                  <el-input prefix-icon="el-icon-edit" v-model="land.description" size="mini" style="width: 150px"
                            placeholder="描述"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
           <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="创建时间:" prop="createDate">
                  <el-input prefix-icon="el-icon-edit" v-model="land.createDate" size="mini" style="width: 150px"
                            placeholder="创建时间"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
    <el-button size="mini" @click="cancelEidt">取 消</el-button>
    <el-button size="mini" type="primary" @click="addLand('addLandForm')">确 定</el-button>
  </span>
        </el-dialog>
      </div>
    </el-form>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        emps: [],
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
       lands:[],
       land:{
         tunnelId:"",
         stationId:"",
         observeDate:"",
         measureInterval:"",
         observeVal:"",
         sinkDiffVal:"",
         sinkSpeed:"",
         addUpDisplacement:"",
         addUpDisplacement:"",
         measureMan:"",
         warningState:"",
         distanceFace:"",
         distanceFace:"",
         createDate:"",
         updateDate:"",
       }
      };
    },
    mounted: function () {
      // this.initData();
      this.loadLands();
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
      deleteManyLands(){
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
      deleteLand(row){
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
        this.deleteRequest("/land/land/" + ids).then(resp=> {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _
            _this.loadLands();
          }
        })
      },
      keywordsChange(val){
        if (val == '') {
          this.loadEmps();
        }
      },
      searchLand(){
        this.loadLands();
      },
      currentChange(currentChange){
        this.currentPage = currentChange;
        this.loadLands();
      },
      loadLands(){
        var _this = this;
        this.tableLoading = true;
        this.getRequest("/land/land?page=" + this.currentPage + "&size=10").then(resp=> {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.lands = data.lands;
            _this.totalCount = data.count;
//            _this.emptyEmpData();
          }
        })
      },
      addLand(formName){
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.land.id) {
              //更新
              this.tableLoading = true;
              this.putRequest("/land/land", this.land).then(resp=> {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyLandData();
                  _this.loadLands();
                }
              })
            } else {
              //添加
              this.tableLoading = true;
              this.postRequest("/land/land", this.land).then(resp=> {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyLandData();
                  _this.loadLands();
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
        this.emptyLandData();
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
        this.getRequest("/employee/basic/basicdata").then(resp=> {
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.nations = data.nations;
            _this.politics = data.politics;
            _this.deps = data.deps;
            _this.positions = data.positions;
            _this.joblevels = data.joblevels;
            _this.emp.workID = data.workID;
          }
        })
      },
      showEditLandView(row){
        console.log(row)
        this.dialogTitle = "编辑员工";
        this.land = row;
        this.land.tunnelId = row.tunnelId;
        this.land.stationId = row.stationId;
        this.land.observeDate = row.observeDate;
        this.land.measureInterval = row.measureInterval;
        this.land.observeVal = row.observeVal;
        this.land.sinkDiffVal = row.sinkDiffVal;
        this.land.sinkSpeed = row.sinkSpeed;
        this.land.addUpDisplacement = row.addUpDisplacement;
        this.land.maxDisplacement = row.maxDisplacement;
        this.land.measureMan = row.measureMan;
        this.land.warningState = row.warningState;
        this.land.distanceFace = row.distanceFace;
        this.land.description = row.description;
        this.land.createDate = row.createDate;
        this.land.updateDate = row.updateDate;
        this.dialogVisible = true;
      },
      showAddLandView(){
        this.dialogTitle = "添加地表";
        this.dialogVisible = true;
      },
      emptyLandData(){
        this.land = {
          tunnelId:"",
         stationId:"",
         observeDate:"",
         measureInterval:"",
         observeVal:"",
         sinkDiffVal:"",
         sinkSpeed:"",
         addUpDisplacement:"",
         addUpDisplacement:"",
         measureMan:"",
         warningState:"",
         distanceFace:"",
         distanceFace:"",
         createDate:"",
         updateDate:"",
        }
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
</style>
