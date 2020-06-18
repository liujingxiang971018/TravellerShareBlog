$(function () {

});

function route_date_label_click(day_count){
    var flag=$("#route_date-"+day_count+"").attr("name");
    if(flag=="unhidden"){
        //关闭，隐藏
        for(var count=1;count<=city_count[day_count];count++){
            $("#route_city-"+day_count+"-"+count+"").attr("hidden",true);
        }
        $("#route_date_icon-"+day_count+"").attr("class","glyphicon glyphicon-triangle-right");
        $("#route_date-"+day_count+"").attr("name","hidden");
    }else{
        //带开，显示
        for(var count=1;count<=city_count[day_count];count++){
            $("#route_city-"+day_count+"-"+count+"").attr("hidden",false);
        }
        $("#route_date_icon-"+day_count+"").attr("class","glyphicon glyphicon-triangle-bottom");
        $("#route_date-"+day_count+"").attr("name","unhidden");
    }
}

function route_city_icon_click(day_count,city_count){
    var flag=$("#route_city-"+day_count+"-"+city_count+"").attr("name");
    if(flag=="unhidden"){
        //关闭，隐藏
        for(var count=1;count<=scenery_count[city_count];count++){
            $("#route_scenery-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",true);
        }
        $("#route_city_icon-"+day_count+"-"+city_count+"").attr("class","glyphicon glyphicon-triangle-right");
        $("#route_city-"+day_count+"-"+city_count+"").attr("name","hidden");
    }else{
        //带开，显示
        for(var count=1;count<=scenery_count[city_count];count++){
            $("#route_scenery-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",false);
        }
        $("#route_city_icon-"+day_count+"-"+city_count+"").attr("class","glyphicon glyphicon-triangle-bottom");
        $("#route_city-"+day_count+"-"+city_count+"").attr("name","unhidden");
    }
}

function route_scenery_icon_click(day_count,city_count,scenery_count){
    var flag=$("#route_scenery-"+day_count+"-"+city_count+"-"+scenery_count+"").attr("name");
    if(flag=="unhidden"){
        //关闭，隐藏
        for(var count=1;count<=scenery_count;count++){
            $("#route_scenery_photo_input-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",true);
            $("#route_scenery_score_label-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",true);
            $("#route_scenery_location_label-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",true);
            $("#route_scenery_traffic_label-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",true);
            $("#route_scenery_tel_label-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",true);
            $("#route_scenery_detail_label-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",true);
        }
        $("#route_scenery_icon-"+day_count+"-"+city_count+"-"+scenery_count+"").attr("class","glyphicon glyphicon-triangle-right");
        $("#route_scenery-"+day_count+"-"+city_count+"-"+scenery_count+"").attr("name","hidden");
    }else{
        //带开，显示
        for(var count=1;count<=scenery_count;count++){
            $("#route_scenery_photo_input-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",false);
            $("#route_scenery_score_label-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",false);
            $("#route_scenery_location_label-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",false);
            $("#route_scenery_traffic_label-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",false);
            $("#route_scenery_tel_label-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",false);
            $("#route_scenery_detail_label-"+day_count+"-"+city_count+"-"+count+"").attr("hidden",false);
        }
        $("#route_scenery_icon-"+day_count+"-"+city_count+"-"+scenery_count+"").attr("class","glyphicon glyphicon-triangle-bottom");
        $("#route_scenery-"+day_count+"-"+city_count+"-"+scenery_count+"").attr("name","unhidden");
    }
}

function route_date_label_dblclick(day_count){
    var b = confirm("是否删除目录：Day"+day_count);
    if(b){
        //确定删除
        //删除正文
        $("#route_date-"+day_count).remove();
        //删除目录
        $("#routeMenu_date"+day_count).remove();
        //修改day_count
        var route_date_list = document.getElementsByClassName("route_date");
        for(var i=0;i<route_date_list.length;i++){
            var day = route_date_list[i].getElementsByClassName("day_value");
            var value = $(day[0]).text();
            $(day[0]).text(value-1);
        }
        var routeMenu_data_list=document.getElementsByClassName("routeMenu_date");
        for(var m=0;m<routeMenu_data_list.length;m++){
            var MenuDay = routeMenu_data_list[m].getElementsByClassName("MenuDay_value");
            var MenuValue = $(MenuDay[0]).text();
            $(MenuDay[0]).text(MenuValue-1);

        }
    }
}

function route_city_label_dblclick(day_count,city_count){
    //获取城市
    var cityName=$("#route_city_input-"+day_count+"-"+city_count+"").val();
    var b = confirm("是否删除城市："+cityName);
    if(b){
        //确定删除
        //删除正文
        $("#route_city-"+day_count+"-"+city_count+"").remove();
        //删除目录
        $("#routeMenu_city"+day_count+"-"+city_count+"").remove();
    }
}
function route_scenery_label_dblclick(day_count,city_count,scenery_count){
    //获取城市
    var sceneryName=$("#route_scenery_input-"+day_count+"-"+city_count+"-"+scenery_count+"").val();
    var b = confirm("是否删除景点："+sceneryName);
    if(b){
        //确定删除
        //删除正文
        $("#route_scenery-"+day_count+"-"+city_count+"-"+scenery_count+"").remove();
        //删除目录
        $("#routeMenu_scenery"+day_count+"-"+city_count+"-"+scenery_count+"").remove();
    }
}

$(function () {
    $(document).click(function (e) {
        var id = $(e.target).attr("id");
        var split = id.split("-");

        if(id=="route_detail_label" || id=="route_detail_label_icon"){
            var flag= $(".route_detail_input").attr("hidden");
            if(flag){
                $("#route_detail_label_icon").attr("class","glyphicon glyphicon-triangle-bottom");
                $(".route_detail_input").attr("hidden",false);
            }else{
                $("#route_detail_label_icon").attr("class","glyphicon glyphicon-triangle-right");
                $(".route_detail_input").attr("hidden",true);
            }
        }
        if(split[0]=="route_scenery_photo"){
            document.getElementById(id).addEventListener("change",function () {
                var file=document.getElementById(id);

                var fileTypes = [".jpg", ".png",".jpeg",".bmp"];
                var filePath = file.value;
                //当括号里面的值为0、空字符、false 、null 、undefined的时候就相当于false
                if(filePath){
                    var isNext = false;
                    var fileEnd = filePath.substring(filePath.indexOf("."));
                    for (var i = 0; i < fileTypes.length; i++) {
                        if (fileTypes[i] == fileEnd) {
                            isNext = true;
                            break;
                        }
                    }
                    if (!isNext){
                        alert('不接受此文件类型');
                        file.value = "";
                        return false;
                    }
                }else {
                    return false;
                }


                var fileItem = $("#"+id)[0].files[0];
                var formData = new FormData();
                formData.append('file', fileItem);
                formData.append('type', '1');
                console.log(formData);
                $.ajax ({
                    url : "/upLoadSceneryAvatarServlet",
                    type : "POST",
                    data : formData,
                    async : true,
                    cache : false,
                    contentType : false,//需设置为false,因为是FormData对象，且已经声明了属性enctype="multipart/form-data"
                    processData : false,//需设置为false,因为data值是FormData对象，不需要对数据做处理
                    dataType : 'json',
                    success : function (data) {
                        // alert("上传成功");
                        $("#"+id).prop("name",data.scenery_avatar);

                    },
                    error : function(data) {
                        // alert("上传失败");
                        $("#"+id).prop("name",data.scenery_avatar);
                    }
                });
            });
        }

        if (split[0] == "route_date_label" || split[0] == "route_date_icon") {
            route_date_label_click(split[1]);
        }
        if(split[0]=="route_city_icon"){
            route_city_icon_click(split[1],split[2]);
        }
        if(split[0]=="route_scenery_icon"){
            route_scenery_icon_click(split[1],split[2],split[3]);
        }
    });
    //双击删除
    $(document).dblclick(function (e) {
        var id = $(e.target).attr("id");
        var split = id.split("-");
        if (split[0] == "route_date_label" || split[0] == "route_date_icon") {
            route_date_label_dblclick(split[1]);
        }
        if(split[0]=="route_city_icon"){
            route_city_label_dblclick(split[1],split[2]);
        }
        if(split[0]=="route_scenery_icon"){
            route_scenery_label_dblclick(split[1],split[2],split[3]);
        }
    });

    //点击提交，向后台保存数据
    $("#publish_view").click(function () {
        //获取路线信息
        //获取标题
        var routeName= $("#input_title").val();
        //获取路线id
        var routeId=$("#route_id").text();
        //获取路线头像
        var routeAvatar=$("#photo").prop("name");
        //获取路线的创建时间
        var routeCreateTime = $("#route_createTime").text();
        //获取路线标签
        var routeLabel_people=$("#classify_people").val();
        var routeLabel_scenery=$("#classify_scenery").val();
        var routeLabel=routeLabel_people+","+routeLabel_scenery;
        //获取出发时间
        var routeStartTime=$("#start_time").val();
        //获取花费时间
        var routeSpendTime=$("#spend_time").val();
        //获取出发人数
        var routePersonNumber=$("#person_number").val();
        //获取路线成本
        var routeCost=$("#route_cost").val();
        //获取路线详情
        var routeDetail=$("#route_detail_input").val();
        
        //组成路线信息表
        $.ajax({
            url:"/updateRouteInfoServlet",
            type:"POST",
            data:{"route_id":routeId,"route_name":routeName,"route_avatar":routeAvatar,"route_createTime":routeCreateTime,"route_label":routeLabel,"route_detail":routeDetail,"start_time":routeStartTime,"spend_time":routeSpendTime,"person_number":routePersonNumber,"route_cost":routeCost},
            success:function (data) {
                if(data.Success==true){
                    //获取城市信息表
                    //先获取日期集合
                    var dateList = document.getElementsByClassName("route_date");
                    for(var i=1;i<=dateList.length;i++){
                        //获取城市集合
                        var cityList=dateList[i-1].getElementsByClassName("route_city");
                        for (var j=1;j<=cityList.length;j++){
                            //获取城市id
                            var cityId=cityList[j-1].getElementsByClassName("route_city_id")[0].innerText;
                            //获取城市名称
                            var cityName=cityList[j-1].getElementsByClassName("route_city_name")[0].value;

                            //组成城市信息表
                            $.ajax({
                                url:"/updateCityInfoServlet",
                                type:"POST",
                                data:{"city_id":cityId,"city_name":cityName,"route_id":routeId,"start_time":i,"start_count":j},
                                success:function (data) {

                                }
                            });
                            //景点集合
                            var sceneryList=cityList[j-1].getElementsByClassName("route_scenery");
                            for(var k=1;k<=sceneryList.length;k++){
                                //获取景点id
                                var sceneryId=sceneryList[k-1].getElementsByClassName("route_scenery_id")[0].innerText;
                                //获取景点名称
                                var sceneryName=sceneryList[k-1].getElementsByClassName("route_scenery_name")[0].value;
                                //获取景点图片
                                var sceneryAvatar=sceneryList[k-1].getElementsByClassName("route_scenery_photo")[0].getAttribute("name");
                                //获取分数
                                var sceneryScore=sceneryList[k-1].getElementsByClassName("scenery_score")[0].getAttribute("name");
                                //获取景点详细信息
                                var sceneryDetail=sceneryList[k-1].getElementsByClassName("scenery_detail_input")[0].value;
                                //获取景点位置
                                var sceneryLocation=sceneryList[k-1].getElementsByClassName("scenery_location_input")[0].value;
                                //获取景点交通
                                var sceneryTraffic=sceneryList[k-1].getElementsByClassName("scenery_traffic_input")[0].value;
                                //获取景点联系方式
                                var sceneryTelephone=sceneryList[k-1].getElementsByClassName("scenery_tel_input")[0].value;
                                //组成景点信息表
                                $.ajax({
                                    url:"/updateSceneryInfoServlet",
                                    type:"POST",
                                    data:{"scenery_id":sceneryId,"scenery_name":sceneryName,"city_id":cityId,"start_count":k,"scenery_score":sceneryScore,"scenery_avatar":sceneryAvatar,"scenery_detail":sceneryDetail,"scenery_location":sceneryLocation,"scenery_traffic":sceneryTraffic,"scenery_telephone":sceneryTelephone},
                                    success:function (data) {

                                    }
                                });
                            }

                        }
                    }
                }
                alert("保存成功");
            }
        });

    })
});