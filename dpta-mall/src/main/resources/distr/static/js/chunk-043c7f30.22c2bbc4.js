(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-043c7f30"],{9406:function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"dashboard-container"},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:6}},[a("el-card",[a("div",[t._v("已核销可用提现收入")]),a("span",[t._v(t._s(t.statis.verified))])])],1),a("el-col",{attrs:{span:6}},[a("el-card",[a("div",[t._v("未核销收入")]),a("span",[t._v(t._s(t.statis.verify))])])],1),a("el-col",{attrs:{span:6}},[a("el-card",[a("div",[t._v("总收入金额")]),a("span",[t._v(t._s(t.statis.amount))])])],1),a("el-col",{attrs:{span:6}},[a("el-card",[a("div",[t._v(" 今日 "),a("li",[t._v("总收入")]),a("span",[t._v(t._s(t.statis.today_verified))]),a("li",[t._v("已核销收入")]),a("span",[t._v(t._s(t.statis.today_verify))]),a("li",[t._v("未核销收入")]),a("span",[t._v(t._s(t.statis.today_amount))])])])],1)],1),a("el-row",[a("el-col",{attrs:{span:18}},[a("el-card",{attrs:{id:"top"}},[a("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[a("span",[t._v("商品收入排行榜")])]),a("el-table",{attrs:{data:t.topList}},[a("el-table-column",{attrs:{label:"收入",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.amount)+" ")]}}])}),a("el-table-column",{attrs:{label:"商品id",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.commId)+" ")]}}])}),a("el-table-column",{attrs:{label:"商品名",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.commName)+" ")]}}])}),a("el-table-column",{attrs:{label:"最高价格",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.maxPrice)+" ")]}}])}),a("el-table-column",{attrs:{label:"最低价格",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.minPrice)+" ")]}}])}),a("el-table-column",{attrs:{label:"价格平均值",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.avgPrice)+" ")]}}])}),a("el-table-column",{attrs:{label:"数量",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.count)+" ")]}}])})],1)],1)],1)],1)],1)},s=[],l=a("5530"),r=a("2f62"),o=a("b775");function i(t){return Object(o["a"])({url:"/distr/api/statis/all",method:"get",data:t})}function c(t){return Object(o["a"])({url:"/distr/api/statis/top",method:"get",data:t})}var u={components:{},name:"Dashboard",computed:Object(l["a"])({},Object(r["b"])(["name"])),data:function(){return{statis:{amount:0,today_amount:0,today_verified:0,today_verify:0,verified:0,verify:0},topList:null,formData:{suppName:"",field101:void 0},rules:{suppName:[{required:!0,message:"请输入分销商名",trigger:"blur"}],field101:[{required:!0,message:"请选择下拉选择",trigger:"change"}]},field101Options:[{label:"选项一",value:1},{label:"选项二",value:2}]}},created:function(){this.fetchData()},methods:{fetchData:function(){var t=this;i({}).then((function(e){console.log(e),t.statis=e.data})),c({}).then((function(e){t.topList=e.data}))}}},d=u,f=(a("b4c5"),a("2877")),_=Object(f["a"])(d,n,s,!1,null,"32c9e188",null);e["default"]=_.exports},aa97:function(t,e,a){},b4c5:function(t,e,a){"use strict";a("aa97")}}]);