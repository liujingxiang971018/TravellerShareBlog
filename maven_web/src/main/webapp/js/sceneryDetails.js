var comment_count;
var scenery_id;
function appendScenery(scenery){
    for(var key in scenery){
        if(key==="scenery"){
            scenery_id=scenery[key][0];
            $("#scenery_id").text(scenery_id);
            $("#scenery_name").text(scenery[key][1]);
            $("#scenery_photo").prop("src",scenery[key][3]);
            var star_count=scenery[key][2];
            for(var i=1;i<=star_count;i++){
                $("#star"+i).text("★");
            }
            $("#scenery_details").text(scenery[key][4]);
            $("#scenery_location").text(scenery[key][5]);
            $("#scenery_traffic").text(scenery[key][6]);
            $("#scenery_telephone").text(scenery[key][7]);
        }
        if(key==="comment"){
            for(var commentkey in scenery[key][0]){
                comment_count=parseInt(commentkey);

                //获取评论给信息
                var addComment=
                    "<div class='personal_comment' id='"+commentkey+"'>\n" +
                    "     <div class='header'>\n" +
                    "          <img src='"+scenery[key][0][commentkey][2]+"' class='head_picture1'>\n" +
                    "          <p class='user_nickname'>"+scenery[key][0][commentkey][1]+"</p>\n" +
                    "     </div>\n" +
                    "     <div class='comments_info'>\n" +
                    "          <p class='comments_date'>"+scenery[key][0][commentkey][3]+"</p>\n" +
                    "          <p class='comment'>"+scenery[key][0][commentkey][4]+"</p>\n" +
                    "     </div>\n" +
                    "     <div class='delete'>\n" +
                    "          <a id='delete-"+commentkey+"' href='javascript:void(0)'><img src='../images/delete.jpg' class='delete_icon'></a>\n" +
                    "     </div>\n" +
                    "</div>";

                $(".personal_comments").append(addComment);
                var flag=scenery[key][5];
                if(flag==false){
                    $("#delete-"+commentkey).prop("style","pointer-events: none;");
                }
                
                //添加事件
                $("#delete-"+commentkey).click(function () {
                    $.ajax({
                        url:"/deleteCommentServlet",
                        type:"POST",
                        data:{"scenery_date":scenery[key][0][commentkey][3]},
                        success:function (data) {
                            $("#"+commentkey+"").empty();
                            comment_count-=1;
                            $("#comments_num").text(comment_count);
                        }
                    })
                })
            }

            //得到评论条数
            $("#comments_num").text(comment_count);

            /*//生成分页数
            var frontPage=
                " <li class='disabled'>\n" +
                "     <a href='javascript:void(0)' aria-label='Previous' id='front'>\n" +
                "       <span aria-hidden='true'>&laquo;</span>\n" +
                "     </a>\n" +
                "</li>";
            $(".pager").append(frontPage);


            var page_count=(comment_count%5==0)?comment_count/5:comment_count/5+1;
            //向分页栏添加页码
            var addPage=null;
            for(var i=1;i<=page_count;i++){
                addPage="<li><a href='javascript:void(0)' id='page-"+i+"'>"+i+"</a></li>\n";

                $(".pager").append(addPage);

                //点击页码发起搜索，每页最多展示5条
                $("#page-"+i+"").click(function () {
                    //设置页码背景
                    var pages=$(".pager li");
                    for(var k=0;k<pages.length;k++){
                        if($(pages[k]).prop("class")=="active"){
                            $(pages[k]).prop("class","");
                        }
                    }
                    $("#page-"+i+"").prop("class","active");
                    if(i==1){
                        $(pages[0]).prop("class","disabled");
                        $(pages[pages.length-1]).prop("class","");
                    }
                    if(i==pages.length-1){
                        $(pages[pages.length-1]).prop("class","disabled");
                        $(pages[0]).prop("class","");
                    }

                    //获取排序方式
                    var sortTypeList=$(".sortType");
                    for(var j=0;j<sortTypeList.length;j++){
                        var value=$(sortTypeList[j]).attr("name");
                        if(value=="click"){
                            sortType=$(sortTypeList[j]).attr("id");
                        }
                    }
                    $.ajax({
                        url:"/showSceneryInfoServlet",
                        type:"POST",
                        data:{},
                        success:function (route_json) {
                            //实现页面跳转到搜索结果展示页面
                            // location.href="../routes/myRoutes.html";
                            appendScenery(route_json);
                        }
                    })
                });
            }

            var nextPage=
                "<li>\n" +
                "    <a href='javascript:void(0)' aria-label='Next' id='next'>\n" +
                "        <span aria-hidden='true'>&raquo;</span>\n" +
                "    </a>\n" +
                "</li>";
            $(".pager").append(nextPage);*/
        }
    }
}


function appendComment(data){
    //获取原有值
    var commentHtml=$(".personal_comments").html();
    $(".personal_comments").empty();
    comment_count+=1;
    for(var key in data){

        //填充内容
        var addItem=
            "<div class='personal_comment' id='"+comment_count+"'>\n" +
            "     <div class='header'>\n" +
            "          <img src='"+data[key][0]+"' class='head_picture1'>\n" +
            "          <p class='user_nickname'>"+data[key][1]+"</p>\n" +
            "     </div>\n" +
            "     <div class='comments_info'>\n" +
            "          <p class='comments_date'>"+data[key][2]+"</p>\n" +
            "          <p class='comment'>"+data[key][3]+"</p>\n" +
            "     </div>\n" +
            "     <div class='delete'>\n" +
            "          <a id='delete-"+comment_count+"' href='javascript:void(0)'><img src='../images/delete.jpg' class='delete_icon'></a>\n" +
            "     </div>\n" +
            "</div>";
        $(".personal_comments").append(addItem);

        var flag=data[key][5];
        if(flag==false){
            $("#delete-"+comment_count).prop("style","pointer-events: none;");
        }

        //添加事件
        $("#delete-"+comment_count+"").click(function () {
            $.ajax({
                url:"/deleteCommentServlet",
                type:"POST",
                data:{"scenery_date":data[key][2]},
                success:function (data) {
                    $("#"+comment_count+"").empty();
                    comment_count-=1;
                    $("#comments_num").text(comment_count);
                }
            })
        })

        $(".personal_comments").append(commentHtml);
        document.getElementById("write_comment").value="";
    }
    $("#comments_num").text(comment_count);

}

$(function () {
   //获取用户信息
    $.ajax({
        url:"/getUserInfoServlet",
        type:"GET",
        data:{},
        success:function (data) {
            $("#nickName").html(data.nickname+"<span class=\"caret\"></span>");
        }
    });

    //获取景点和评论信息
    $.ajax({
        url:"/showSceneryInfoServlet",
        type:"POST",
        data:{},
        success:function (scenery) {
            appendScenery(scenery);
        }
    });

    //点击搜一搜发起搜索，每页最多展示5条
    $("#search_btn").click(function () {
        var text=$("#input_CityMsg").val();
        $.ajax({
            url:"/searchRoutesServlet",
            type:"POST",
            data:{"input_text":text,"Page":1 },
            success:function (data) {
                //实现页面跳转到搜索结果展示页面
                location.href="../routes/searchResult.html";
            }
        })
    });

    //点击发布
    $("#publish").click(function () {
        var comment_detail=$("#write_comment").val();
        var scenery_id=$("#scenery_id").text();
        $.ajax({
            url:"/updateSceneryCommentServlet",
            type:"POST",
            data:{"scenery_id":scenery_id,"comment_detail":comment_detail},
            success:function (data) {
                appendComment(data);
            }
        })
    })

});