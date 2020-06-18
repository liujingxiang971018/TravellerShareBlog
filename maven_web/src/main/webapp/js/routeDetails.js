function appendItem(route_json){
    for(var key in route_json){
        if(key==="user"){
            $("#user_nickname").text(route_json[key][0]);
            $("#head_photo_img").prop("src",route_json[key][1]);
        }
        if(key==="route"){
            $(".set_photo").css("background","url("+route_json[key][2]+")");
            $("#route_title").text(route_json[key][0]);
            $("#route_createTime").text(route_json[key][1]);
            $("#start_time").text(route_json[key][3]);
            $("#spend_time").text(route_json[key][4]);
            $("#people_number").text(route_json[key][5]);
            $("#route_cost").text(route_json[key][6]);
            $("#item_like_text").text(route_json[key][7]);
            $("#item_dislike_text").text(route_json[key][9]);
            $("#item_collect_text").text(route_json[key][11]);

            var iconLikeAvatar;
            var iconDislikeAvatar;
            var iconCollectAvatar;
            if(route_json[key][8]===true){
                iconLikeAvatar="../images/icon_like_selected.jpg";
            }else{
                iconLikeAvatar="../images/icon_like_default.jpg";
            }
            if(route_json[key][10]==true){
                iconDislikeAvatar="../images/icon_dislike_selected.jpg";
            }else{
                iconDislikeAvatar="../images/icon_dislike_default.jpg";
            }
            if(route_json[key][12]==true){
                iconCollectAvatar="../images/icon_collect_selected.jpg";
            }else{
                iconCollectAvatar="../images/icon_collect_default.jpg";
            }
            var addIcon=
                "<div class='good'>\n" +
                "     <!--条目点赞数-->\n" +
                "     <img src='"+iconLikeAvatar+"' id='itemLike_"+route_json[key][14]+"' class='default'><span id='itemLike_text-"+route_json[key][14]+"'>"+route_json[key][7]+"</span>\n" +
                "</div>\n" +
                "<div class=\"bad\">\n" +
                "     <!--条目点踩数-->\n" +
                "     <img src='"+iconDislikeAvatar+"' id='itemDislike_"+route_json[key][14]+"'  class='default'><span id='itemDislike_text-"+route_json[key][14]+"'>"+route_json[key][9]+"</span>\n" +
                "</div>\n" +
                "<div class='collection'>\n" +
                "     <!--条目收藏数-->\n" +
                "     <img src='"+iconCollectAvatar+"' id='itemCollect_"+route_json[key][14]+"'  class='default'><span id='itemCollect_text-"+route_json[key][14]+"'>"+route_json[key][11]+"</span>\n" +
                "</div>";
            $(".icon").append(addIcon);
            //遍历日期
            for(var daykey in route_json[key][13]){
                //给目录添加日期
                var addMenuDate=
                    "<li class='routeMenu_date' id='routeMenu_date"+daykey+"'>\n" +
                    "   <a href='#route_date-"+daykey+"' style='font-size: 19px'>Day<strong class='MenuDay_value'>"+daykey+"</strong></a>" +
                    "   <ul id='routeMenu_city"+daykey+"'>\n" +
                    "   </ul>\n"+
                    "</li>";
                $(".tour_nav").append(addMenuDate);

                //遍历城市
                var cityStr;
                var cityCount=0;

                //遍历景点
                var sceneryCount=0;
                var sceneryStr;
                for(var citykey in route_json[key][13][daykey][0]){
                    //给目录添加城市
                    var addMenuCity=
                        "<li id='routeMenu_city"+daykey+"-"+citykey+"'>\n" +
                        "   <a href='#route_city-"+daykey+"-"+citykey+"' style='font-size: 18px'>"+route_json[key][13][daykey][0][citykey][1]+"</a>" +
                        "   <ul id='routeMenu_scenery"+daykey+"-"+citykey+"'>\n" +
                        "   </ul>\n"+
                        "</li>";
                    $("#routeMenu_city"+daykey+"").append(addMenuCity);

                    cityCount+=1;
                    var city_id=route_json[key][13][daykey][0][citykey][0];
                    var city_name=route_json[key][13][daykey][0][citykey][1];
                    if(cityCount==1){
                        cityStr=city_name;
                    }else{
                        cityStr+="->"+city_name;
                    }

                    for(var scenerykey in route_json[key][13][daykey][0][citykey][2]){
                        //给目录添加城市
                        var addMenuScenery=
                            "<li id='routeMenu_scenery"+daykey+"-"+citykey+"-"+scenerykey+"'>\n" +
                            "   <a href='#route_scenery-"+daykey+"-"+citykey+"-"+scenerykey+"' style='font-size: 16px'>"+route_json[key][13][daykey][0][citykey][2][scenerykey][1]+"</a>" +
                            "</li>";
                        $("#routeMenu_scenery"+daykey+"-"+citykey+"").append(addMenuScenery);


                        sceneryCount+=1;
                        var scenery_id=route_json[key][13][daykey][0][citykey][2][scenerykey][0];
                        var scenery_name=route_json[key][13][daykey][0][citykey][2][scenerykey][1];
                        if(sceneryCount==1){
                            var scenery_a="<a id='scenery_"+scenery_id+"' href='javascript:void(0)'>"+scenery_name+"</a>";
                            sceneryStr=scenery_a;
                        }else{
                            var scenery_a="-><a id='scenery_"+scenery_id+"' href='javascript:void(0)'>"+scenery_name+"</a>";
                            sceneryStr+=scenery_a;
                        }
                    }
                }

                //页面填充信息
                var addItem=
                    "<div class='day_item'>\n" +
                    "     <div class='day'>\n" +
                    "          <div class='border'>\n" +
                    "               <p class='left-day' id='route_date-"+daykey+"'>D<strong id='"+daykey+"'>"+daykey+"</strong></p>\n" +
                    "          </div>\n" +
                    "     </div>\n" +
                    "     <div class='day_info'>\n" +
                    "          <div class='day_content'>\n" +
                    "               <h3 id='route_city-"+daykey+"-"+citykey+"'>"+cityStr+"</h3><span id='"+city_id+"' hidden>"+city_id+"</span>\n" +
                    "               <h5 id='route_scenery-"+daykey+"-"+citykey+"-"+scenerykey+"'>"+sceneryStr+"</h5><span id='"+scenery_id+"' hidden>"+scenery_id+"</span>\n" +
                    "          </div>\n" +
                    "     </div>\n" +
                    "</div>";
                $(".tour_plan").append(addItem);

                //目录填充信息

            }

        }
    }
}


$(function () {
    //先请求用户信息
    $.ajax({
        url:"/getUserInfoServlet",
        type:"GET",
        data:{},
        success:function (data) {
            $("#nickName").html(data.nickname+"<span class=\"caret\"></span>");
        }
    });

    $.ajax({
        url:"/showRouteDetailsServlet",
        type:"POST",
        date:{},
        success:function (route_json) {
            appendItem(route_json);
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
});