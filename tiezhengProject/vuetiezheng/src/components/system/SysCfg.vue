<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input placeholder="通过员工名搜索员工,记得回车哦..." clearable @change="keywordsChange" style="width: 300px;margin: 0px;padding: 0px;" size="mini" :disabled="advanceSearchViewVisible" @keyup.enter.native="searchEmp" prefix-icon="el-icon-search" v-model="keywords">
          </el-input>
          <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search" @click="searchEmp">搜索
          </el-button>
        </div>
        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-button type="primary" size="mini" icon="el-icon-plus" @click="showAddMenuView">
            添加菜单
          </el-button>
        </div>
      </el-header>
      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          <transition name="slide-fade">
            <div style="margin-bottom: 10px;border: 1px;border-radius: 5px;border-style: solid;padding: 5px 0px 5px 0px;box-sizing:border-box;border-color: #20a0ff" v-show="advanceSearchViewVisible">
              <el-row>
                <el-col :span="5">
                  政治面貌:
                  <el-select v-model="emp.politicId" style="width: 130px" size="mini" placeholder="政治面貌">
                    <el-option v-for="item in politics" :key="item.id" :label="item.name" :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="4">
                  民族:
                  <el-select v-model="emp.nationId" style="width: 130px" size="mini" placeholder="请选择民族">
                    <el-option v-for="item in nations" :key="item.id" :label="item.name" :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="4">
                  职位:
                  <el-select v-model="emp.posId" style="width: 130px" size="mini" placeholder="请选择职位">
                    <el-option v-for="item in positions" :key="item.id" :label="item.name" :value="item.id">
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="4">
                  职称:
                  <el-select v-model="emp.jobLevelId" style="width: 130px" size="mini" placeholder="请选择职称">
                    <el-option v-for="item in joblevels" :key="item.id" :label="item.name" :value="item.id">
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
                  <el-popover v-model="showOrHidePop2" placement="right" title="请选择部门" trigger="manual">
                    <el-tree :data="deps" :default-expand-all="true" :props="defaultProps" :expand-on-click-node="false" @node-click="handleNodeClick2"></el-tree>
                    <div slot="reference" style="width: 130px;height: 26px;display: inline-flex;font-size:13px;border: 1px;border-radius: 5px;border-style: solid;padding-left: 13px;box-sizing:border-box;border-color: #dcdfe6;cursor: pointer;align-items: center" @click="showDepTree2" v-bind:style="{color: depTextColor}">{{emp.departmentName}}
                    </div>
                  </el-popover>
                </el-col>
                <el-col :span="10">
                  入职日期:
                  <el-date-picker v-model="beginDateScope" unlink-panels size="mini" type="daterange" value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
                  </el-date-picker>
                </el-col>
                <el-col :span="5" :offset="4">
                  <el-button size="mini" @click="cancelSearch">取消</el-button>
                  <el-button icon="el-icon-search" type="primary" size="mini" @click="searchEmp">搜索</el-button>
                </el-col>
              </el-row>
            </div>
          </transition>
          <el-table :data="menus" v-loading="tableLoading" border stripe @selection-change="handleSelectionChange" size="mini" style="width: 100%">
            <el-table-column type="selection" align="middle" width="30">
            </el-table-column>
            <el-table-column prop="name" align="middle" fixed label="菜单名称" width="150">
            </el-table-column>
            <el-table-column prop="component" width="150" align="middle" label="菜单代码">
            </el-table-column>
            <el-table-column prop="url" label="url地址" align="middle" width="150">
            </el-table-column>
            <el-table-column prop="path" width="150" align="middle" label="path地址">
            </el-table-column>
            <el-table-column prop="iconCls" width="150" align="middle" label="图标">
            </el-table-column>
            <el-table-column width="150" align="middle" prop="orderNo" label="排序号">
            </el-table-column>
            <el-table-column prop="type" align="middle" width="150" label="类型">
            </el-table-column>
            <el-table-column prop="description" align="middle" label="描述">
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="195">
              <template slot-scope="scope">
                <el-button @click="showEditMenuView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini">编辑
                </el-button>
                <el-button style="padding: 3px 4px 3px 4px;margin: 2px" type="primary" size="mini">查看高级资料
                </el-button>
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini" @click="deleteMenu(scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <el-button type="danger" size="mini" v-if="menus.length>0" :disabled="multipleSelection.length==0" @click="deleteManyMenus">批量删除
            </el-button>
            <el-pagination background :page-size="10" :current-page="currentPage" @current-change="currentChange" layout="prev, pager, next" :total="totalCount">
            </el-pagination>
          </div>
        </div>
      </el-main>
    </el-container>
    <el-form :model="menu" ref="addMenuForm" style="margin: 0px;padding: 0px;">
      <div style="text-align: left">
        <el-dialog :title="dialogTitle" style="padding: 0px;" :close-on-click-modal="false" :visible.sync="dialogVisible" width="40%">
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="父级菜单:" prop="name">
                  <el-select v-model="menu.parentId" style="width: 200px" size="mini" placeholder="父级菜单">
                    <el-option v-for="item in parentMenus" :key="item.id" :label="item.name" :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="菜单名称:" prop="name">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.name" size="mini" style="width: 150px" placeholder="菜单名称"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="菜单代码:" prop="component">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.component" size="mini" style="width: 150px" placeholder="菜单代码"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="url地址:" prop="url">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.url" size="mini" style="width: 150px" placeholder="url地址"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="path地址:" prop="path">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.path" size="mini" style="width: 150px" placeholder="path地址"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="图标:" prop="iconCls">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.iconCls" size="mini" style="width: 150px" placeholder="图标"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="排序号:" prop="orderNo">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.orderNo" size="mini" style="width: 150px" placeholder="排序号"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="类型:" prop="type">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.type" size="mini" style="width: 150px" placeholder="类型"></el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <div>
                <el-form-item label="描述:" prop="description">
                  <el-input prefix-icon="el-icon-edit" v-model="menu.description" size="mini" style="width: 150px" placeholder="描述"></el-input>
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
      menus: [],
      emps: [],
      parentMenus: [],
      keywords: "",
      fileUploadBtnText: "导入数据",
      beginDateScope: "",
      faangledoubleup: "fa-angle-double-up",
      faangledoubledown: "fa-angle-double-down",
      dialogTitle: "",
      multipleSelection: [],
      depTextColor: "#c0c4cc",
      nations: [],
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
        name: "",
        gender: "",
        birthday: "",
        idCard: "",
        wedlock: "",
        nationId: "",
        nativePlace: "",
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
        id: "",
        name: "",
        component: "",
        url: "",
        path: "",
        iconCls: "",
        orderNo: "",
        type: "",
        parentId: "",
        description: "",
        pMenu: []
      },
      pMenu: {
        id: "",
        name: "",
        pId: ""
      }
    };
  },
  mounted: function() {
    this.initData();
    this.loadMenus();
    // this.loadEmps();
  },
  methods: {
    fileUploadSuccess(response, file, fileList) {
      if (response) {
        this.$message({ type: response.status, message: response.msg });
      }
      this.loadEmps();
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
      this.loadEmps();
    },
    showAdvanceSearchView() {
      this.advanceSearchViewVisible = !this.advanceSearchViewVisible;
      this.keywords = "";
      if (!this.advanceSearchViewVisible) {
        this.emptyEmpData();
        this.beginDateScope = "";
        this.loadEmps();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    deleteManyMenus() {
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
    deleteMenu(row) {
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
      this.deleteRequest("/menu/deleteMenu/" + ids).then(resp => {
        _this.tableLoading = false;
        if (resp && resp.status == 200) {
          var data = resp.data;
          _this.loadMenus();
        }
      });
    },
    keywordsChange(val) {
      if (val == "") {
        this.loadEmps();
      }
    },
    searchEmp() {
      this.loadEmps();
    },
    currentChange(currentChange) {
      this.currentPage = currentChange;
      this.loadMenus();
    },
    loadEmps() {
      var _this = this;
      this.tableLoading = true;
      this.getRequest(
        "/employee/basic/emp?page=" +
          this.currentPage +
          "&size=10&keywords=" +
          this.keywords +
          "&politicId=" +
          this.emp.politicId +
          "&nationId=" +
          this.emp.nationId +
          "&posId=" +
          this.emp.posId +
          "&jobLevelId=" +
          this.emp.jobLevelId +
          "&engageForm=" +
          this.emp.engageForm +
          "&departmentId=" +
          this.emp.departmentId +
          "&beginDateScope=" +
          this.beginDateScope
      ).then(resp => {
        this.tableLoading = false;
        if (resp && resp.status == 200) {
          var data = resp.data;
          _this.emps = data.emps;
          _this.totalCount = data.count;
          //            _this.emptyEmpData();
        }
      });
    },
    loadMenus() {
      var _this = this;
      this.tableLoading = true;
      this.getRequest(
        "/menu/getMenus?page=" + this.currentPage + "&size=10"
      ).then(resp => {
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
            //更新
            this.tableLoading = true;
            this.putRequest("/menu/updateMenu", this.menu).then(resp => {
              _this.tableLoading = false;
              if (resp && resp.status == 200) {
                var data = resp.data;
                _this.dialogVisible = false;
                _this.emptyMenuData();
                _this.loadMenus();
              }
            });
          } else {
            //添加
            this.tableLoading = true;
            this.postRequest("/menu/addMenu", this.menu).then(resp => {
              _this.tableLoading = false;
              if (resp && resp.status == 200) {
                var data = resp.data;
                _;
                _this.dialogVisible = false;
                _this.emptyMenuData();
                _this.loadMenus();
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
      this.emptyEmpData();
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
      this.getRequest("/menu/getParentMenu/").then(resp => {
        if (resp && resp.status == 200) {
          console.log("initData >>>>");
          var data = resp.data;
          _this.parentMenus = data.pMenus;
          // _this.politics = data.politics;
          // _this.deps = data.deps;
          // _this.positions = data.positions;
          // _this.joblevels = data.joblevels;
          // _this.emp.workID = data.workID;
        }
      });
    },
    showEditMenuView(row) {
      this.dialogTitle = "编辑菜单";
      this.menu = row;
      this.dialogVisible = true;
    },
    showAddMenuView() {
      this.dialogTitle = "添加菜单";
      this.dialogVisible = true;
    },
    emptyEmpData() {
      this.emp = {
        name: "",
        gender: "",
        birthday: "",
        idCard: "",
        wedlock: "",
        nationId: "",
        nativePlace: "",
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
      };
    },
    emptyMenuData() {
      this.menu = {
        id: "",
        name: "",
        component: "",
        url: "",
        path: "",
        iconCls: "",
        orderNo: "",
        type: "",
        description: ""
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
