<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>activiti工作流-待办列表</title>

    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <script src="/webjars/jquery/jquery.min.js "></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

<div style="width: 60%;height: 500px;background-color: antiquewhite;margin: 10% auto;">
    <div class="alert alert-primary" style="text-align: center;" role="alert">
        我的待办列表
    </div>
    <div id="content" class="alert alert-primary" style="text-align: center;" role="alert">
        <div class="title" style="width: 100%;height: 30px;text-align: center;line-height: 30px;justify-content: center;display: flex;">
            <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">需求名称</div>
            <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">需求内容</div>
            <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">状态</div>
            <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">操作</div>
            <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">操作</div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal_assignee" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">审核界面</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div id="c1" class="modal-body">
                <!-- 审核界面 -->
                <div class="alert alert-primary" id="liuchengname" role="alert">
                </div>
                <div class="form-group col-md-4">
                    <label for="inputState">下一步审批人</label>
                    <select id="liuchengoptions" class="form-control">
                        <option selected>Choose...</option>
                        <option>...</option>
                    </select>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" onclick="shenhe()" class="btn btn-primary" data-dismiss="modal">保存</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
</body>
<script>

    $(function(){
        getDaliyList();
    });


    localStorage.setItem("type","1");
    //提交审核
    function shenhe(){
        console.log("下一个审核人：", $("#liuchengoptions").val())
        $.ajax({
            url:"http://localhost:8080/activiti/taskSubmit",
            data:{
                "taskId":localStorage.getItem("taskId"),
                "suggestion":"发起流程",
                "type":localStorage.getItem("type"),//第一步默认是通过
                "assignee": $("#liuchengoptions").val()//选择的审核人
            },
            type:"post",
            dataType: "json",
            success: function(res) {
                console.log("返回值："+res);
                if(res.code == 200){
                    //审批动作
                    console.log("操作成功");
                    getDaliyList();
                }else{
                    console.log("操作失败");
                }
            }
        });
    }

    /**
     * 获取我的待办列表
     */
    function getDaliyList(){
        console.log("我的待办列表：", localStorage.getItem("id"));
        $.ajax({
            url:"http://localhost:8080/activiti/toDoTaskList",
            data:{
                "uid":localStorage.getItem("id")
            },
            type:"post",
            dataType: "json",
            success: function(res) {
                console.log("返回值："+res);
                if(res.code == 200){
                    let data = res.data
                    let tableStr = `<div class="title" style="width: 100%;height: 30px;text-align: center;line-height: 30px;justify-content: center;display: flex;">
                                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">需求名称</div>
                                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">需求内容</div>
                                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">状态</div>
                                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">操作</div>
                                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">操作</div>
                                    </div>`
                    for(var i=0;i<data.length;i++){
                        var status = "";
                        if(data[i].status=="-1"){
                            status = "未启动";
                        }else if(data[i].status=="0"){
                            status = "表单填写";
                        }else if(data[i].status=="1"){
                            status = "组长审批";
                        }else if(data[i].status=="2"){
                            status = "开发经理审批";
                        }else if(data[i].status=="3"){
                            status = "项目经理审批";
                        }else if(data[i].status=="4"){
                            status = "结束";
                        }
                        tableStr += `
                            <div class="title" style="width: 100%;height: 30px;text-align: center;line-height: 30px;justify-content: center;display: flex;">
                                              <div style="background-color: gray;color: aliceblue;height: 30px;width: 30%;">`+data[i].name+`</div>
                                              <div style="background-color: gray;color: aliceblue;height: 30px;width: 30%;">`+data[i].content+`</div>
                                              <div style="background-color: gray;color: aliceblue;height: 30px;width: 30%;">`+status+`</div>
                                              <div data-toggle="modal" data-target="#modal_assignee" onclick="getAssigneeList('`+data[i].taskId+`',1)" style="background-color: gray;color: aliceblue;height: 30px;width: 30%;background-color:blue">同意</div>
                                              <div data-toggle="modal" data-target="#modal_assignee" onclick="getAssigneeList('`+data[i].taskId+`',0)" style="background-color: gray;color: aliceblue;height: 30px;width: 30%;line-height: 30px;background-color:green">退回</div>
                            </div>
                            `
                    }
                    $("#content").html(tableStr)
                }else {
                    console.log("获取失败");
                }
            }
        });
    }

    /**
     * 获取审核人
     * @param taskId
     */
    function getAssigneeList(taskId, type){
        console.log("流程id:",taskId);
        //获取id
        localStorage.setItem("taskId",taskId)
        $.ajax({
            url:"http://localhost:8080/activiti/getAssigneeList",
            data:{
                "type":type,
                "taskId":taskId
            },
            type:"post",
            dataType: "json",
            success: function(res) {
                console.log("返回值："+res);
                if(res.code == 200){
                    let data = res.data
                    $("#liuchengname").html(data.flowInfo.flowId+data.flowInfo.flowName);
                    $("#liuchengoptions").html("");
                    for(var i=0;i<data.userInfo.length;i++){
                        var str = `<option value=`+data.userInfo[i].id+` class="xuanxiang" id=`+data.userInfo[i].id+`>`+data.userInfo[i].username+`</option>`;
                        $("#liuchengoptions").append(str);
                    }
                }else{
                    console.log("操作失败");
                }
            }
        });
    }

    $(document).on({
        click:function(){
            var options=$("#liuchengoptions option:selected");
            console.log("选择人："+options.val());
            //获取id
            var assignee = options.val();
            localStorage.setItem("assignee",assignee);
        }
    },'#liuchengoptions')
</script>
</html>