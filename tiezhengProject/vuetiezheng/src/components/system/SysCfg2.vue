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
          <el-upload
            :show-file-list="false"
            accept="application/vnd.ms-excel"
            action="/employee/basic/importEmp"
            :on-success="fileUploadSuccess"
            :on-error="fileUploadError" :disabled="fileUploadBtnText=='正在导入'"
            :before-upload="beforeFileUpload" style="display: inline">
            <el-button size="mini" type="success" :loading="fileUploadBtnText=='正在导入'"><i class="fa fa-lg fa-level-up"
                                                                                          style="margin-right: 5px"></i>{{fileUploadBtnText}}
            </el-button>
          </el-upload>
          <el-button type="success" size="mini" @click="exportEmps"><i class="fa fa-lg fa-level-down"
                                                                       style="margin-right: 5px"></i>导出数据
          </el-button>
          <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddMenuView">
            添加菜单
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
            :data="menus"
            v-loading="tableLoading"
            border
            stripe
            @selection-change="handleSelectionChange"
            size="mini"
            style="width: 100%">
            <el-table-column
              type="selection"
              align="middle"
              width="30">
            </el-table-column>
            <el-table-column
              prop="name"
              align="middle"
              fixed
              label="菜单名称"
              width="150">
            </el-table-column>
            <el-table-column
              prop="component"
              width="150"
              align="middle"
              label="菜单代码">
            </el-table-column>
            <el-table-column
              prop="description"
              label="菜单描述"
              width="150">
            </el-table-column>
             <el-table-column
              prop="url"
              width="150"
              align="left"
              label="路由地址">
            </el-table-column>
            <el-table-column
              prop="path"
              width="150"
              align="left"
              label="路径">
            </el-table-column>
            <el-table-column
              width="150"
              prop="iconCls"
              label="图标">
            </el-table-column>
            <el-table-column
              prop="orderNo"
              width="80"
              label="排序号">
            </el-table-column>
            <el-table-column
              prop="type"
              label="类型">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="195">
              <template slot-scope="scope">
                <el-button @click="showEditMenuView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
                <!-- <el-button style="padding: 3px 4px 3px 4px;margin: 2px" type="primary"
                           size="mini">查看高级资料
                </el-button> -->
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="deleteEmp(scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <el-button type="danger" size="mini" v-if="menus.length>0" :disabled="multipleSelection.length==0"
                       @click="deleteManyEmps">批量删除
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
    <el-form :model="menu"  ref="addMenuForm" style="margin: 0px;padding: 0px;">
      <div style="text-align: left">
        <el-dialog
          :title="dialogTitle"
          style="padding: 0px;"
          :close-on-click-modal="false"
          :visible.sync="dialogVisible"
          width="40%">
          <el-row>
           <el-col :span="10">
                  父节点:
                  <el-select v-model="emp.politicId" style="width: 130px" size="mini" placeholder="政治面貌">
                    <el-option
                      v-for="item in politics"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="菜单名" prop="name">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.name" size="mini" style="width: 200px"
                            placeholder="菜单名"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="菜单代码" prop="component">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.component" size="mini" style="width: 200px"
                            placeholder="菜单代码"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="菜单描述" prop="description">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.description" size="mini" style="width: 200px"
                            placeholder="菜单描述"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="路由地址" prop="url">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.url" size="mini" style="width: 200px"
                            placeholder="路由地址"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="图标" prop="iconCls">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.iconCls" size="mini" style="width: 200px"
                            placeholder="图标"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="序号" prop="orderNo">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.orderNo" size="mini" style="width: 200px"
                            placeholder="序号"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="类型" prop="type">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.type" size="mini" style="width: 200px"
                            placeholder="类型"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <span slot="footer" class="dialog-footer">
            <el-button size="mini" @click="cancelEidt">取 消</el-button>
            <el-button size="mini" type="primary" @click="addMenu('addMenuForm')">确 定</el-button>
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
      // emps: [],
      menus:[],
      keywords: "",
      fileUploadBtnText: "导入数据",
      beginDateScope: "",
      faangledoubleup: "fa-angle-double-up",
      faangledoubledown: "fa-angle-double-down",
      dialogTitle: "",
      multipleSelection: [],
      depTextColor: "#c0c4cc",
      nations: [],
      politics: [],
      positions: [],  
      joblevels: [],
      totalCount: -1,
      currentPage: 1,
      degrees: [
        { id: 4, name: "大专" },
        { id: 5, name: "本科" },
        { id: 6, name: "硕士" },
        { id: 7, name: "博士" },
        {
          id: 3,
          name: "高中"
        },
        { id: 2, name: "初中" },
        { id: 1, name: "小学" },
        { id: 8, name: "其他" }
      ],
      deps: [],
      defaultProps: {
        label: "name",
        isLeaf: "leaf",
        children: "children"
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
        politicId: "",
        email: "",
        phone: "",
        address: "",
        departmentId: "",
        departmentName: "所属部门...",
        jobLevelId: "",
        posId: "",
        engageForm: "",
        tiptopDegree: "",
        specialty: "",
        school: "",
        beginDate: "",
        workState: "",
        workID: "",
        contractTerm: "",
        conversionTime: "",
        notWorkDate: "",
        beginContract: "",
        endContract: "",
        workAge: ""
      },
      menu: {
        parentId: "",
        name: "",
        component:"Home",
        description: "",
        url: "",
        path:"/home",
        iconCls: "",
        orderNo: "",
        type: ""
      },
      rules: {
        name: [{ required: true, message: "必填:姓名", trigger: "blur" }],
        gender: [{ required: true, message: "必填:性别", trigger: "blur" }],
        birthday: [
          { required: true, message: "必填:出生日期", trigger: "blur" }
        ],
        idCard: [
          {
            required: true,
            message: "必填:身份证号码",
            trigger: "blur"
          },
          {
            pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
            message: "身份证号码格式不正确",
            trigger: "blur"
          }
        ],
        wedlock: [
          { required: true, message: "必填:婚姻状况", trigger: "blur" }
        ],
        nationId: [{ required: true, message: "必填:民族", trigger: "change" }],
        nativePlace: [
          { required: true, message: "必填:籍贯", trigger: "blur" }
        ],
        politicId: [
          { required: true, message: "必填:政治面貌", trigger: "blur" }
        ],
        email: [
          { required: true, message: "必填:电子邮箱", trigger: "blur" },
          {
            type: "email",
            message: "邮箱格式不正确",
            trigger: "blur"
          }
        ],
        phone: [{ required: true, message: "必填:电话号码", trigger: "blur" }],
        address: [
          { required: true, message: "必填:联系地址", trigger: "blur" }
        ],
        departmentId: [
          { required: true, message: "必填:部门", trigger: "change" }
        ],
        jobLevelId: [
          { required: true, message: "必填:职称", trigger: "change" }
        ],
        posId: [{ required: true, message: "必填:职位", trigger: "change" }],
        engageForm: [
          { required: true, message: "必填:聘用形式", trigger: "blur" }
        ],
        tiptopDegree: [
          { required: true, message: "必填:最高学历", trigger: "change" }
        ],
        specialty: [{ required: true, message: "必填:专业", trigger: "blur" }],
        workID: [{ required: true, message: "必填:工号", trigger: "blur" }],
        school: [{ required: true, message: "必填:毕业院校", trigger: "blur" }],
        beginDate: [
          { required: true, message: "必填:入职日期", trigger: "blur" }
        ],
        conversionTime: [
          { required: true, message: "必填:转正日期", trigger: "blur" }
        ],
        beginContract: [
          { required: true, message: "必填:合同起始日期", trigger: "blur" }
        ],
        endContract: [
          { required: true, message: "必填:合同终止日期", trigger: "blur" }
        ],
        workAge: [{ required: true, message: "必填:工龄", trigger: "blur" }]
      }
    };
  },
  mounted: function() {
    this.initData();
    // this.loadEmps();
    this.loadMenuList();
  },
  methods: {
    fileUploadSuccess(response, file, fileList) {
      if (response) {
        this.$message({ type: response.status, message: response.msg });
      }
      // this.loadEmps();
      this.loadMenuList();
      consolt.log();
      this.fileUploadBtnText = "导入数据";
    },
    fileUploadError(err, file, fileList) {
      this.$message({ type: "error", message: "导入失败!" });
      this.fileUploadBtnText = "导入数据";
    },
    beforeFileUpload(file) {
      this.fileUploadBtnText = "正在导入";
    },
    exportEmps() {
      window.open("/employee/basic/exportEmp", "_parent");
    },
    cancelSearch() {
      this.advanceSearchViewVisible = false;
      this.emptyEmpData();
      this.beginDateScope = "";
      // this.loadEmps();
    },
    showAdvanceSearchView() {
      this.advanceSearchViewVisible = !this.advanceSearchViewVisible;
      this.keywords = "";
      if (!this.advanceSearchViewVisible) {
        this.emptyEmpData();
        this.beginDateScope = "";
        // this.loadEmps();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    deleteManyEmps() {
      this.$confirm(
        "此操作将删除[" + this.multipleSelection.length + "]条数据, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          var ids = "";
          for (var i = 0; i < this.multipleSelection.length; i++) {
            ids += this.multipleSelection[i].id + ",";
          }
          this.doDelete(ids);
        })
        .catch(() => {});
    },
    deleteEmp(row) {
      this.$confirm("此操作将永久删除[" + row.name + "], 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.doDelete(row.id);
        })
        .catch(() => {});
    },
    doDelete(ids) {
      this.tableLoading = true;
      var _this = this;
      this.deleteRequest("/employee/basic/emp/" + ids).then(resp => {
        _this.tableLoading = false;
        if (resp && resp.status == 200) {
          var data = resp.data;
          _;
          // _this.loadEmps();
        }
      });
    },
    keywordsChange(val) {
      if (val == "") {
        // this.loadEmps();
      }
    },
    searchEmp() {
      // this.loadEmps();
    },
    currentChange(currentChange) {
      this.currentPage = currentChange;
      // this.loadEmps();
    },
    loadMenuList() {
      var _this = this;
      this.tableLoading = true;
      this.getRequest(
        "/menu/getAllMenu").then(resp => {
        this.tableLoading = false;
        if (resp && resp.status == 200) {
          var data = resp.data;
          _this.menus = data.menus;
          _this.totalCount = data.count;
          //            _this.emptyEmpData();
        }
      });
    },
    addMenu(formName) {
      var _this = this;
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.menu.id) {
            console.log("<<<编辑>>>");
            //更新
            this.tableLoading = true;
            this.putRequest("/menu/updateMenu", this.menu).then(resp => {
              _this.tableLoading = false;
              if (resp && resp.status == 200) {
                var data = resp.data;
                _this.dialogVisible = false;
                _this.emptyMenuData();
                // _this.loadEmps();
              }
            });
          } else {
            //添加
            console.log("addMenu >>> 添加");
            this.tableLoading = true;
            this.postRequest("/menu/addMenu", this.menu).then(resp => {
              _this.tableLoading = false;
              if (resp && resp.status == 200) {
                var data = resp.data;
                _this.dialogVisible = false;
                _this.emptyMenuData();
                // _this.loadEmps();
              }
            });
          }
        } else {
          return false;
        }
      });
    },
    cancelEidt() {
      this.dialogVisible = false;
      // this.emptyEmpData();
    },
    showDepTree() {
      this.showOrHidePop = !this.showOrHidePop;
    },
    showDepTree2() {
      this.showOrHidePop2 = !this.showOrHidePop2;
    },
    handleNodeClick(data) {
      this.emp.departmentName = data.name;
      this.emp.departmentId = data.id;
      this.showOrHidePop = false;
      this.depTextColor = "#606266";
    },
    handleNodeClick2(data) {
      this.emp.departmentName = data.name;
      this.emp.departmentId = data.id;
      this.showOrHidePop2 = false;
      this.depTextColor = "#606266";
    },
    initData() {
      var _this = this;
      this.getRequest("/employee/basic/basicdata").then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          _this.nations = data.nations;
          _this.politics = data.politics;
          _this.deps = data.deps;
          _this.positions = data.positions;
          _this.joblevels = data.joblevels;
          _this.emp.workID = data.workID;
        }
      });
    },
    loadParentMenus(){
        var _this = this;
        this.getRequest("/system/basic/deps").then(resp=> {
          if (resp && resp.status == 200) {
            _this.allDeps = resp.data;
          }
        });
    },
    showAddMenuView(row) {
      //  this.loadAllDeps();
       this.dialogVisible = true;
      this.dialogTitle = "添加菜单";
    },
    showEditMenuView(row) {
      //  this.loadAllDeps();
       this.dialogVisible = true;
      this.dialogTitle = "编辑菜单";
      this.menu = row;
      this.menu.parentId = row.parentId;
      this.menu.name = row.name;
      this.menu.component = row.component;
      this.menu.description = row.description;
      this.menu.orderNo = row.orderNo;
       this.menu.type = row.type;
      this.dialogVisible = true;
    },
    emptyMenuData() {
      this.menu = {
        parentId: "",
        name: "",
        component: "",
        description: "",
        url: "",
        path: "",
        iconCls: "",
        orderNo: "",
        type: ""
      };
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
  transition: all 0.8s ease;
}

.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter,
.slide-fade-leave-to {
  transform: translateX(10px);
  opacity: 0;
}
</style>
