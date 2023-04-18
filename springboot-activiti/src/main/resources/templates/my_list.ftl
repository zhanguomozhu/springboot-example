<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>activiti工作流-任务</title>

    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <script src="/webjars/jquery/jquery.min.js "></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div style="width: 60%;height: 500px;background-color: antiquewhite;margin: 10% auto;">
    <div class="alert alert-primary" style="text-align: center;" role="alert">
        任务列表
    </div>
    <button type="button" data-toggle="modal" data-target="#modal_add" class="btn btn-secondary" id="add">新增</button>
    <div id="content" class="alert alert-primary" style="text-align: center;" role="alert">
        <div class="title" style="width: 100%;height: 30px;text-align: center;line-height: 30px;justify-content: center;display: flex;">
            <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">需求名称</div>
            <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">需求内容</div>
            <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">状态</div>
            <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">操作</div>
            <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">操作</div>
            <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">操作</div>
        </div>
    </div>

</div>

<div class="modal fade" id="modal_add" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">需求新增界面</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div id="c1" class="modal-body">
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">需求名称</span>
                    </div>
                    <input type="text" class="form-control"  id="name" placeholder="需求名称" aria-label="Username" aria-describedby="basic-addon1">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">具体内容</span>
                    </div>
                    <input type="text" class="form-control" id="xqcontent" placeholder="具体内容" aria-label="Username" aria-describedby="basic-addon1">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" onclick="save()" class="btn btn-primary" data-dismiss="modal">保存</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="model_assignee" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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

<div class="modal fade" id="modal_process" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">流程界面</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div id="c1" class="modal-body">
                <!--  -->
                <div id="content2" class="alert alert-primary" style="text-align: center;" role="alert">
                    <div class="title" style="width: 100%;height: 30px;text-align: center;line-height: 30px;justify-content: center;display: flex;">
                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">流程名称</div>
                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">审批人</div>
                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">处理信息</div>
                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">处理时间</div>
                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">状态</div>
                    </div>
                </div>
                <img class="pic" src="" style="width: 100%;height: 300px;background-color: aqua;">
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
        //获取列表
        getList();
    });


    //提交审核
    function shenhe(){
        console.log("流程id:",localStorage.getItem("taskId"));
        console.log("下一审核人：", $("#liuchengoptions").val())
        $.ajax({
            url:"http://localhost:8080/activiti/taskSubmit",
            data:{
                "taskId":localStorage.getItem("taskId"),
                "suggestion":"通过",//可以新增一个输入框用户自己输入
                "type":1,//第一步默认是通过
                "assignee":$("#liuchengoptions").val()//选择的审核人
            },
            type:"post",
            dataType: "json",
            success: function(data) {
                console.log(data);
                //获取列表
                getList();
            }
        });
    }

    //新增任务
    function save(){
        var name = $("#name").val();
        var content = $("#xqcontent").val();
        $.ajax({
            url:"http://localhost:8080/activiti/saveDemand",
            data:JSON.stringify({
                "name":name,
                "content":content,
                "type":1,
                "createuser":localStorage.getItem("id")
            }),
            type:"post",
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function(data) {
                console.log("返回值：",data)
                if(data.code == 200){
                    //获取列表
                    getList();
                }else{
                    console.log("操作失败");
                }
            }
        });
    }


    //获取我的需求列表
    function getList(){
        console.log("获取列表");
        $.ajax({
            url:"http://localhost:8080/activiti/getDemandList",
            data:null,
            type:"post",
            dataType: "json",
            success: function(res) {
                console.log("返回值："+res);
                let data = res.data
                let tableStr = `<div class="title" style="width: 100%;height: 30px;text-align: center;line-height: 30px;justify-content: center;display: flex;">
                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">流程名称</div>
                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">审批人</div>
                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">处理信息</div>
                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">处理时间</div>
                        <div style="background-color: red;color: aliceblue;height: 30px;width: 30%;">状态</div>
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
										  <div data-toggle="modal" data-target="#modal_start"  onclick="start('`+data[i].taskid+`')" style="background-color: gray;color: aliceblue;height: 30px;width: 30%;">发起流程</div>
										  <div data-toggle="modal" data-target="#model_assignee" onclick="getAssigneeList('`+data[i].taskid+`')" style="background-color: gray;color: aliceblue;height: 30px;width: 30%;">提交审核</div>
										  <div data-toggle="modal" data-target="#modal_process" onclick="getProcessList('`+data[i].taskid+`')" style="background-color: gray;color: aliceblue;height: 30px;width: 30%;">查看流程</div>
						</div>
						`
                }
                $("#content").html(tableStr)
            }
        });
    }

    //发起流程
    function start(taskId){
        //获取id
        console.log("流程id:",taskId);
        localStorage.setItem("taskId",taskId)
        $.ajax({
            url:"http://localhost:8080/activiti/start",
            data:{
                "uid":localStorage.getItem("id"),
                "taskId":taskId
            },
            type:"post",
            dataType: "json",
            success: function(res) {
                console.log("返回值："+res);
                if(res.code == 200){
                    //审批动作
                    taskSubmit(taskId);
                }else{
                    console.log("操作失败");
                }
            }
        });
    }

    /**
     * 审批
     */
    function taskSubmit(taskId){
        $.ajax({
            url:"http://localhost:8080/activiti/taskSubmit",
            data:{
                "type":"1",
                "taskId":taskId
            },
            type:"post",
            dataType: "json",
            success: function(res) {
                console.log("返回值："+res);
                if(res.code == 200){
                    //审批动作
                    console.log("操作成功");
                    //获取列表
                    getList();
                }else{
                    console.log("操作失败");
                }
            }
        });
    }

    /**
     * 获取审核人
     * @param taskId
     */
    function getAssigneeList(taskId){
        console.log("流程id:",taskId);
        //获取id
        localStorage.setItem("taskId",taskId)
        $.ajax({
            url:"http://localhost:8080/activiti/getAssigneeList",
            data:{
                "type":1,
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

    /**
     * 查看流程
     * @param taskId
     */
    function getProcessList(taskId){
        console.log("流程id:",taskId);
        //获取id
        localStorage.setItem("taskId",taskId)
        $.ajax({
            url:"http://localhost:8080/activiti/getProcessList",
            data:{
                "taskId":taskId
            },
            type:"post",
            dataType: "json",
            success: function(res) {
                console.log("返回值："+res);
                if(res.code == 200){
                    $("#content2").html("");
                    let data = res.data
                    for(var i=0;i<data.length;i++){
                        var str = `<div class="title" style="width: 100%;height: 30px;text-align: center;line-height: 30px;justify-content: center;display: flex;">
                                              <div style="background-color: gray;color: aliceblue;height: 30px;width: 30%;">`+data[i].flowName+`</div>
                                              <div style="background-color: gray;color: aliceblue;height: 30px;width: 30%;">`+data[i].assignee+`</div>
                                              <div style="background-color: gray;color: aliceblue;height: 30px;width: 30%;">`+data[i].flowMsg+`</div>
                                              <div style="background-color: gray;color: aliceblue;height: 30px;width: 30%;">`+data[i].endTime+`</div>
                                              <div style="background-color: gray;color: aliceblue;height: 30px;width: 30%;">`+data[i].flowState+`</div>
                            </div>`;
                        $("#content2").append(str);
                    }
                }else{
                    console.log("操作失败");
                }
            }
        });

        //获取流程图
        $(".pic").attr("src",'http://localhost:8080/activiti/getProcessPic?taskId='+taskId)
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