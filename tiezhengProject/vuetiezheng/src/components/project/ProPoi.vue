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
                     @click="showAddPointView">
            添加测点
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
            :data="points"
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
              prop="sectionId"
              align="left"
              fixed
              label="断面id"
              width="90">
            </el-table-column>
            <el-table-column
              prop="tunnelId"
              width="85"
              align="left"
              label="隧道id">
            </el-table-column>
            <el-table-column
              prop="measPointNoFormat"
              label="测点编号格式"
              width="50">
            </el-table-column>
            <el-table-column
              prop="measPointNum"
              width="85"
              align="left"
              label="测点数量">
            </el-table-column>
            <el-table-column
              prop="embLocation"
              width="150"
              align="left"
              label="埋设位置">
            </el-table-column>
            <el-table-column
              prop="embTime"
              width="70"
              label="埋设时间">
            </el-table-column>
            <el-table-column
              width="50"
              prop="equipment"
              label="设备名称">
            </el-table-column>
            <el-table-column
              prop="equiNo"
              width="80"
              label="设备编号">
            </el-table-column>
            <el-table-column
              prop="basedOnCriteria"
              width="80"
              label="依据标准">
            </el-table-column>
            <el-table-column
              prop="envConditions"
              width="180"
              align="left"
              label="环境条件">
            </el-table-column>
            <el-table-column
              prop="maxDisplacement"
              width="100"
              label="极限位移参考值">
            </el-table-column>
            <el-table-column
              prop="XMaxDisplacement"
              width="220"
              align="left"
              label="x方向极限位移参考值">
            </el-table-column>
            <el-table-column
              prop="YMaxDisplacement"
              align="left"
              width="100"
              label="y方向极限位移参考值">
            </el-table-column>
            <el-table-column
              width="100"
              align="left"
              prop="ZMaxDisplacement"
              label="z方向极限位移参考值">
            </el-table-column>
            <el-table-column
              prop="sinkSpeed"
              width="100"
              align="left"
              label="变形速率">
            </el-table-column>
            <el-table-column
              prop="modValue"
              width="100"
              label="修正值">
            </el-table-column>
            <el-table-column
              prop="baseDistance"
              width="85"
              align="left"
              label="据基点距离">
            </el-table-column>
            <el-table-column
              prop="detectionMan"
              width="85"
              align="left"
              label="检测人">
            </el-table-column>
            <el-table-column
              prop="setIdentity"
              width="95"
              align="left"
              label="沉降标识">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="195">
              <template slot-scope="scope">
                <el-button @click="showEditPointView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
                <el-button style="padding: 3px 4px 3px 4px;margin: 2px" type="primary"
                           size="mini">查看高级资料
                </el-button>
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="deletePoint(scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <el-button type="danger" size="mini" v-if="points.length>0" :disabled="multipleSelection.length==0"
                       @click="deleteManyPoints">批量删除
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
    <el-form :model="point" ref="addPointForm" style="margin: 0px;padding: 0px;">
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
                <el-form-item label="断面ID:" prop="sectionId">
                  <el-input prefix-icon="el-icon-edit" v-model="point.sectionId" size="mini" style="width: 150px"
                            placeholder="断面ID"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="隧道ID:" prop="tunnelId">
                  <el-input prefix-icon="el-icon-edit" v-model="point.tunnelId" size="mini" style="width: 150px"
                            placeholder="隧道ID"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="测点编号格式:" prop="measPointNoFormat">
                  <el-input prefix-icon="el-icon-edit" v-model="point.measPointNoFormat" size="mini" style="width: 150px"
                            placeholder="测点编号格式"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="测点数量:" prop="measPointNum">
                  <el-input prefix-icon="el-icon-edit" v-model="point.measPointNum" size="mini" style="width: 150px"
                            placeholder="测点数量"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="埋设位置:" prop="embLocation">
                  <el-input prefix-icon="el-icon-edit" v-model="point.embLocation" size="mini" style="width: 150px"
                            placeholder="埋设位置"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="埋设时间:" prop="embTime">
                  <el-input prefix-icon="el-icon-edit" v-model="point.embTime" size="mini" style="width: 150px"
                            placeholder="埋设时间"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="设备名称:" prop="equipment">
                  <el-input prefix-icon="el-icon-edit" v-model="point.equipment" size="mini" style="width: 150px"
                            placeholder="设备名称"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="设备编号:" prop="equiNo">
                  <el-input prefix-icon="el-icon-edit" v-model="point.equiNo" size="mini" style="width: 150px"
                            placeholder="设备编号"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="依据标准:" prop="basedOnCriteria">
                  <el-input prefix-icon="el-icon-edit" v-model="point.basedOnCriteria" size="mini" style="width: 150px"
                            placeholder="依据标准"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="环境条件:" prop="envConditions">
                  <el-input prefix-icon="el-icon-edit" v-model="point.envConditions" size="mini" style="width: 150px"
                            placeholder="环境条件"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="极限位移参考值:" prop="maxDisplacement">
                  <el-input prefix-icon="el-icon-edit" v-model="point.maxDisplacement" size="mini" style="width: 150px"
                            placeholder="极限位移参考值"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="x方向极限位移参考值:" prop="XMaxDisplacement">
                  <el-input prefix-icon="el-icon-edit" v-model="point.XMaxDisplacement" size="mini" style="width: 150px"
                            placeholder="x方向极限位移参考值"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="y方向极限位移参考值:" prop="YMaxDisplacement">
                  <el-input prefix-icon="el-icon-edit" v-model="point.YMaxDisplacement" size="mini" style="width: 150px"
                            placeholder="y方向极限位移参考值"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="z方向极限位移参考值:" prop="ZMaxDisplacement">
                  <el-input prefix-icon="el-icon-edit" v-model="point.ZMaxDisplacement" size="mini" style="width: 150px"
                            placeholder="z方向极限位移参考值"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="变形速率:" prop="sinkSpeed">
                  <el-input prefix-icon="el-icon-edit" v-model="point.sinkSpeed" size="mini" style="width: 150px"
                            placeholder="变形速率"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="修正值:" prop="modValue">
                  <el-input prefix-icon="el-icon-edit" v-model="point.modValue" size="mini" style="width: 150px"
                            placeholder="修正值"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="据基点距离:" prop="baseDistance">
                  <el-input prefix-icon="el-icon-edit" v-model="point.baseDistance" size="mini" style="width: 150px"
                            placeholder="据基点距离"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="检测人:" prop="detectionMan">
                  <el-input prefix-icon="el-icon-edit" v-model="point.detectionMan" size="mini" style="width: 150px"
                            placeholder="检测人"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="沉降标识:" prop="setIdentity">
                  <el-input prefix-icon="el-icon-edit" v-model="point.setIdentity" size="mini" style="width: 150px"
                            placeholder="沉降标识"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
    <el-button size="mini" @click="cancelEidt">取 消</el-button>
    <el-button size="mini" type="primary" @click="addPoint('addPointForm')">确 定</el-button>
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
        points:[],
        point:{
          id:"",
          sectionId:"",
          tunnelId:"",
          measPointNoFormat:"",
          measPointNum:"",
          embLocation:"",
          embTime:"",
          equipment:"",
          equiNo:"",
          basedOnCriteria:"",
          envConditions:"",
          maxDisplacement:"",
          XMaxDisplacement:"",
          YMaxDisplacement:"",
          ZMaxDisplacement:"",
          sinkSpeed:"",
          modValue:"",
          baseDistance:"",
          detectionMan:"",
          setIdentity:""
        }
      };
    },
    mounted: function () {
      // this.initData();
      this.loadPoints();
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
      deleteManyPoints(){
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
      deletePoint(row){
        this.$confirm('此操作将永久删除[' + row.embLocation + '], 是否继续?', '提示', {
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
        this.deleteRequest("/meaPoint/point/" + ids).then(resp=> {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _
            _this.loadPoints();
          }
        })
      },
      keywordsChange(val){
        if (val == '') {
          this.loadPoints();
        }
      },
      searchEmp(){
        this.loadPoints();
      },
      currentChange(currentChange){
        this.currentPage = currentChange;
        this.loadPoints();
      },
      loadPoints(){
        var _this = this;
        this.tableLoading = true;
        this.getRequest("/meaPoint/point?page=" + this.currentPage + "&size=10").then(resp=> {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.points = data.points;
            _this.totalCount = data.count;
//            _this.emptyEmpData();
          }
        })
      },
      addPoint(formName){
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.point.id) {
              //更新
              this.tableLoading = true;
              this.putRequest("/meaPoint/point", this.point).then(resp=> {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyPointData();
                  _this.loadPoints();
                }
              })
            } else {
              //添加
              this.tableLoading = true;
              this.postRequest("/meaPoint/point", this.point).then(resp=> {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyPointData();
                  _this.loadPoints();
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
        this.emptyPointData();
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
      showEditPointView(row){
        console.log(row)
        this.dialogTitle = "编辑员工";
        this.point = row;
        this.point.sectionId = row.sectionId;
        this.point.tunnelId = row.tunnelId;
        this.point.measPointNoFormat = row.measPointNoFormat;
        this.point.measPointNum = row.measPointNum;
        this.point.embLocation = row.embLocation;
        this.point.embTime = row.embTime;
        this.point.equipment = row.equipment;
        this.point.equiNo = row.equiNo;
        this.point.basedOnCriteria = row.basedOnCriteria;
        this.point.envConditions = row.envConditions;
        this.point.maxDisplacement = row.maxDisplacement;
        this.point.XMaxDisplacement = row.XMaxDisplacement;
        this.point.YMaxDisplacement = row.YMaxDisplacement;
        this.point.ZMaxDisplacement = row.ZMaxDisplacement;
        this.point.sinkSpeed = row.sinkSpeed;
        this.point.modValue = row.modValue;
        this.point.baseDistance = row.baseDistance;
        this.point.detectionMan = row.detectionMan;
        this.point.setIdentity = row.setIdentity;
        this.dialogVisible = true;
      },
      showAddPointView(){
        this.dialogTitle = "添加员工";
        this.dialogVisible = true;
      },
      emptyPointData(){
        this.point = {
           sectionId:"",
          tunnelId:"",
          measPointNoFormat:"",
          measPointNum:"",
          embLocation:"",
          embTime:"",
          equipment:"",
          equiNo:"",
          basedOnCriteria:"",
          envConditions:"",
          maxDisplacement:"",
          XMaxDisplacement:"",
          YMaxDisplacement:"",
          ZMaxDisplacement:"",
          sinkSpeed:"",
          modValue:"",
          baseDistance:"",
          detectionMan:"",
          setIdentity:"",
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
