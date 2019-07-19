<%--
Created by IntelliJ IDEA.
User: Administrator
Date: 2019-07-15
Time: 10:08
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

    <script type="text/javascript">

        $(function () {
            //创建
            $("#createBtn").click(fun1);
            //删除
            $("#deleteBtn").click(del);
            //修改
            $("#editBtn").click(selectOne);
            //更新
            $("#updateBtn").click(update);
            //条件查询
            $("#searchBtn").click(search);
            //添加
            $("#save-Activity").click(fun2);
            //日历插件
            $(".time").datetimepicker({
                minView: "month",
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });
            //页面加载完毕分页查询
            pageList(1, 5);
            //全选按钮
            $("#check-all").click(checkAll);
            //每一个按钮单击事件
            $("#search-show").on("click", $(":checked"), function () {
                var chedNum = $(":checkbox:gt(0):checked").length;
                var chNum = $(":checkbox:gt(0)").length;
                $("#check-all")[0].checked = (chNum === chedNum);
            });

        });

        //创建市场活动
        function fun1() {
            $("#create-name").val("");
            $("#create-startDate").val("");
            $("#create-endDate").val("");
            $("#create-cost").val("");
            $("#create-description").val("");
            $.ajax({
                url: "workbench/activity/allOwner.do",
                type: "get",
                dataType: "json",
                success: function (data) {
                    var html = "";
                    $.each(data, function (i, item) {
                        html += "<option value='" + item.id + "'>" + item.name + "</option>";
                    });
                    $("#create-Owner").html(html);
                    $("#create-Owner").val("${sessionScope.user.id}");
                    $("#createActivityModal").modal("show");
                }
            });
        }

        //保存市场活动
        function fun2() {
            var SystemTime = getSystemTime();
            $.ajax({
                url: "workbench/activity/add.do",
                type: "post",
                data: $("#addActivityForm").serialize() + "&createTime=" + SystemTime,
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        $("#createActivityModal").modal("hide");
                        pageList(1, 5);
                    }
                }
            });
        }

        //yyyy-MM-dd hh:mm:ss 19位
        function getSystemTime() {
            var time = "";
            var date = new Date();
            time += date.getFullYear() + "-";
            time += (date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1) + "-";
            time += (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + " ";
            time += (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ":";
            time += (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) + ":";
            time += date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
            return time;
        }

        //分页查询
        function search() {
            //将搜索框中的信息保存到隐藏域中
            $("#hidden-name").val($.trim($("#search-name").val()));
            $("#hidden-owner").val($.trim($("#search-owner").val()));
            $("#hidden-startDate").val($.trim($("#search-startDate").val()));
            $("#hidden-endDate").val($.trim($("#search-endDate").val()));
            pageList(1, 5);
        }

        //条件查询，并分页
        function pageList(pageNo, pageSize) {
            var name = $.trim($("#hidden-name").val());
            var owner = $.trim($("#hidden-owner").val());
            var startDate = $.trim($("#hidden-startDate").val());
            var endDate = $.trim($("#hidden-endDate").val());

            $.ajax({
                url: "workbench/activity/search.do",
                type: "get",
                dataType: "json",
                data: {
                    'name': name,
                    'owner': owner,
                    'startDate': startDate,
                    'endDate': endDate,
                    'pageNo': pageNo,
                    'pageSize': pageSize
                },
                success: function (data) {
                    var html = "";
                    $.each(data.dataList, function (i, item) {
                        html += "<tr class='active'>";
                        html += "<td><input type='checkbox' value='" + item.id + "'></td>";
                        html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+item.id+'\';">'+item.name+'</a></td>';
                        html += "<td>" + item.owner + "</td>";
                        html += "<td>" + item.startDate + "</td>";
                        html += "<td>" + item.endDate + "</td>";
                        html += "</tr>";
                    });
                    $("#search-show").html(html);


                    var totalPages = data.pageCount % pageSize === 0 ? data.pageCount / pageSize : data.pageCount / pageSize + 1;
                    //分页插件
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.pageCount, // 总记录条数

                        visiblePageLinks: 3, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        //该回调函数的触发时机：当点击分页查询的时候触发
                        onChangePage: function (event, data) {
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });
                }
            });
        }

        //全选按钮
        function checkAll() {
            $(":checkbox:gt(0)").prop("checked", this.checked);
        }

        //删除市场活动
        function del() {
            var $ched = $(":checkbox:gt(0):checked");
            if ($ched.length === 0) {
                alert("请先选中您想修改的记录");
                return false;
            }
            if (!confirm("您确定要删除选中记录吗？")) {
                return false;
            }
            var ids = "";
            $.each($ched, function (i, item) {
                ids += "id=" + $(item).val() + "&";
            });
            ids = ids.substr(0, ids.length - 1);
            $.ajax({
                url: "workbench/activity/delete.do",
                type: "post",
                data: ids,
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        pageList(1, 5);
                    }
                }
            });
        }

        //查询单个市场活动
        function selectOne() {
            var $ched = $(":checkbox:gt(0):checked");
            if ($ched.length === 0) {
                alert("请先选中您想修改的记录");
                return false;
            } else if ($ched.length > 1) {
                alert("对不起，只能选择其中一条语句进行修改，请重新选择");
                return false;
            }
            $.ajax({
                url: "workbench/activity/selectOne.do",
                type: "post",
                data: {'id': $(":checkbox:gt(0):checked").val()},
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        pageList(1, 5);
                        var html = "";
                        $.each(data.userList, function (i, item) {
                            html += "<option value='" + item.id + "'>" + item.name + "</option>";
                        });
                        $("#edit-owner").html(html);
                        var activity = data.activity;
                        $("#edit-id").val(activity.id);
                        $("#edit-owner").val(activity.owner);
                        $("#edit-name").val(activity.name);
                        $("#edit-startDate").val(activity.startDate);
                        $("#edit-endDate").val(activity.endDate);
                        $("#edit-cost").val(activity.cost);
                        $("#edit-description").val(activity.description);
                        $("#editActivityModal").modal("show");
                    }
                }
            });
        }

        //修改市场活动
        function update() {
            var html = "";
            var id = $.trim($("#edit-id").val());
            var owner = $.trim($("#edit-owner").val());
            var name = $.trim($("#edit-name").val());
            var startDate = $.trim($("#edit-startDate").val());
            var endDate = $.trim($("#edit-endDate").val());
            var cost = $.trim($("#edit-cost").val());
            var description = $.trim($("#edit-description").val());
            $.ajax({
                url: "workbench/activity/update.do",
                type: "post",
                data: {
                    'id': id,
                    'owner': owner,
                    'name': name,
                    'startDate': startDate,
                    'endDate': endDate,
                    'cost': cost,
                    'description': description,
                    'editTime': getSystemTime()
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        $("#editActivityModal").modal("hide");
                        pageList(1, 5);
                    }
                }
            });
        }
    </script>
</head>
<body>
<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-startDate"/>
<input type="hidden" id="hidden-endDate"/>
<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">
                <form id="addActivityForm" class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="create-Owner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-Owner" name="owner">
                            </select>
                        </div>
                        <label for="create-name" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" name="name" class="form-control" id="create-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" name="startDate" class="form-control time" id="create-startDate"
                                   readonly>
                        </div>
                        <label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" name="endDate" class="form-control time" id="create-endDate" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" name="cost" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-description" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" name="description" rows="3"
                                      id="create-description"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="save-Activity">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">
                    <input type="hidden" id="edit-id" value="">

                    <div class="form-group">
                        <label for="edit-owner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-owner">
                            </select>
                        </div>
                        <label for="edit-name" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-startDate">
                        </div>
                        <label for="edit-endDate" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-endDate">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-description" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updateBtn">更新</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="search-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="search-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="search-startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="search-endDate">
                    </div>
                </div>

                <button type="button" class="btn btn-default" id="searchBtn">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" id="createBtn" class="btn btn-primary">
                    <span class="glyphicon glyphicon-plus"></span>创建
                </button>
                <button type="button" id="editBtn" class="btn btn-default"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" id="deleteBtn" class="btn btn-danger"><span
                        class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table id="ActivityShow" class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="check-all"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="search-show">

                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">
            <div id="activityPage">

            </div>
        </div>
    </div>
</div>
</body>
</html>