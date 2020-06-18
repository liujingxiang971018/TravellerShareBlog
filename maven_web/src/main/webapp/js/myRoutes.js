function appendMyItems(popular_json){
    $("#raiders_items").empty();
    $(".pager").empty();
    var route_count=popular_json["totalNumber"];
    var sortType=popular_json["sortType"];
    var currentPage=0;
    for(var key in popular_json){
        if(key!=="inputText"&&key!=="sortType"&&key!=="totalNumber")
        {
            currentPage=popular_json[key][12];

            var iconLikeAvatar;
            var iconDislikeAvatar;
            var iconCollectAvatar;
            if(popular_json[key][6]===true){
                iconLikeAvatar="../images/icon_like_selected.jpg";
            }else{
                iconLikeAvatar="../images/icon_like_default.jpg";
            }
            if(popular_json[key][8]==true){
                iconDislikeAvatar="../images/icon_dislike_selected.jpg";
            }else{
                iconDislikeAvatar="../images/icon_dislike_default.jpg";
            }
            if(popular_json[key][10]==true){
                iconCollectAvatar="../images/icon_collect_selected.jpg";
            }else{
                iconCollectAvatar="../images/icon_collect_default.jpg";
            }
            var addItem=
                "<div class='raiders_item'>\n"+
                "<input type='radio' name='item' routeId="+popular_json[key][0]+" class='radio'>\n"+
                "    <span class='route_id' id="+popular_json[key][0]+" hidden>"+popular_json[key][0]+"</span>\n"+
                "    <!--条目图片-->\n"+
                "    <img class='item_image' src="+popular_json[key][1]+" id='item_image-"+popular_json[key][0]+"'>\n"+
                "    <!--条目标题-->\n"+
                "    <label class='item_label' id='item_label-"+popular_json[key][0]+"'>"+popular_json[key][3]+"</label>\n"+
                "    <!--条目内容-->\n"+
                "    <p class='item_detail' id='item_detail-"+popular_json[key][0]+"'>"+popular_json[key][4]+"</p>\n"+
                "    <!--条目类型-->\n"+
                "    <img class='item_type' src='../images/icon_type.jpg' id='item_type-"+popular_json[key][0]+"'><span class='item_type_text' id='item_type_text-"+popular_json[key][0]+"'>"+popular_json[key][2]+"</span>\n"+
                "    <!--条目创建者-->\n"+
                "    <img class='item_creater' src='../images/icon_creater.jpg' id='item_creater-"+popular_json[key][0]+"'><span class='item_creater_text' id='item_creater_text-"+popular_json[key][0]+"'>"+popular_json[key][5]+"</span>\n"+
                "    <!--条目点赞数-->\n"+
                "    <img src="+iconLikeAvatar+" id='item_like-"+popular_json[key][0]+"' class='item_like' name=''><span class='item_like_text' id='item_like_text-"+popular_json[key][0]+"'>"+popular_json[key][7]+"</span>\n"+
                "    <!--条目点踩数-->\n"+
                "    <img src="+iconDislikeAvatar+" id='item_dislike-"+popular_json[key][0]+"' class='item_dislike' name=''><span class='item_dislike_text' id='item_dislike_text-"+popular_json[key][0]+"'>"+popular_json[key][9]+"</span>\n"+
                "    <!--条目收藏数-->\n"+
                "    <img src="+iconCollectAvatar+" id='item_collect-"+popular_json[key][0]+"' class='item_collect' name=''><span class='item_collect_text' id='item_collect_text-"+popular_json[key][0]+"'>"+popular_json[key][11]+"</span>\n"+
                "</div>\n";
            $("#raiders_items").append(addItem);
        }

    }
    //给header添加信息
    $(".search_text").html(popular_json["inputText"]);
    $("#"+sortType+"").attr("name","click");

    if(route_count==0){
        $(".route_count").text(0);
    }else{
        $(".route_count").html(route_count);
    }


    //生成分页数
    var frontPage=
        " <li class='disabled'>\n" +
        "     <a href='javascript:void(0)' aria-label='Previous' id='page-front'>\n" +
        "       <span aria-hidden='true'>&laquo;</span>\n" +
        "     </a>\n" +
        "</li>";
    $(".pager").append(frontPage);


    var page_count=(route_count%5==0)?route_count/5:route_count/5+1;
    //向分页栏添加页码
    var addPage=null;
    for(var i=1;i<=page_count;i++){
        if(i==currentPage){
            addPage=
                "<li class='active'><a href='javascript:void(0)' id='page-"+i+"'>"+i+"</a></li>\n";
        }else{
            addPage=
                "<li><a href='javascript:void(0)' id='page-"+i+"'>"+i+"</a></li>\n";
        }
        $(".pager").append(addPage);
    }

    var nextPage=
        "<li>\n" +
        "    <a href='javascript:void(0)' aria-label='Next' id='page-next'>\n" +
        "        <span aria-hidden='true'>&raquo;</span>\n" +
        "    </a>\n" +
        "</li>";
    $(".pager").append(nextPage);
}

function sortFunction(sortTypeName) {
    //设置被点击
    var type=$(".sortType");
    for(var i=0;i<type.length;i++){
        if($(type[i]).attr("name")=="click"){
            $(type[i]).attr("name","");
        }
    }
    $("#"+sortTypeName+"").attr("name","click");
    //发起请求
    $.ajax({
        url:"/getMyRoutesServlet",
        type:"POST",
        data:{"sortType":sortTypeName,"Page":1},
        success:function (route_json) {
            //实现页面跳转到搜索结果展示页面
            // location.href="../routes/myRoutes.html";
            appendMyItems(route_json);
        }
    });
}

$(function () {
    $.ajax({
        url:"/getUserInfoServlet",
        type:"GET",
        data:{},
        success:function (data) {
            $("#nickName").html(data.nickname+"<span class=\"caret\"></span>");
        }
    });

    //加载查找结果
    $.ajax({
        url: "/getMyRoutesServlet",
        type: "POST",
        data: {"Page":1,"sortType":"createTime"},
        success: function (route_json) {
            appendMyItems(route_json);
        }
    });


    //点击搜一搜发起搜索，每页最多展示5条
    $("#search_btn").click(function () {
        var text=$("#input_CityMsg").val();
        $.ajax({
            url:"/searchRoutesServlet",
            type:"POST",
            data:{"input_text":text,"Page":1},
            success:function (data) {
                //实现页面跳转到搜索结果展示页面
                location.href="../routes/searchResult.html";
            }
        })
    });

    //点击写游记，跳转到写游记页面
    $("#insertItem").click(function () {
        location.href="../routes/writeRoute.html";
    });
    //点击修改，获取选中的条目，得到路线id，到写页面
    $("#updateItem").click(function () {
        var radioList = $(".radio");
        for(var i=0;i<radioList.length;i++){
            if($(radioList[i]).prop("checked")==true){
                var routeId=$(radioList[i]).attr("routeId");
                $.ajax({
                    url:"/getRouteInfoServlet",
                    type:"POST",
                    data:{"route_id":routeId},
                    success:function (data) {
                        location.href="../routes/writeRoute.html"
                    }
                })
            }
        }
    });
    //点击删除，获取选中的条目，得到路线id，直接删除，重新加载本页面
    $("#deleteItem").click(function () {
        var radioList = $(".radio");
        for(var i=0;i<radioList.length;i++){
            if($(radioList[i]).prop("checked")==true){
                var routeId=$(radioList[i]).attr("routeId");
                $.ajax({
                    url:"/deleteRouteServlet",
                    type:"POST",
                    data:{"route_id":routeId},
                    success:function (data) {
                        location.href="../routes/myRoutes.html"
                    }
                })
            }
        }
    });

    //排序方式点击，请求
    $("#createTime").click(function () {
        sortFunction("createTime");
    });
    $("#collectCount").click(function () {
        sortFunction("collectCount");
    });
    $("#spendTime").click(function () {
        sortFunction("spendTime");
    });
    $("#routeCost").click(function () {
        sortFunction("routeCost");
    });
});
