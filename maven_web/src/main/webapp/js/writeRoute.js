var day_count=0;
var city_count=[];
var scenery_count=[];

//包括目录和内容
function fillPage(route_json){
    for(var key in route_json){
        //填充路线的基本信息
        $("#route_id").text(route_json[key][0]);
        $("#input_title").val(route_json[key][1]);
        $("#route_createTime").text(route_json[key][2]);

        $("#photo").prop("name",route_json[key][3]);
        $(".content-top").css("background","url("+route_json[key][3]+")");

        var route_lables=route_json[key][4];
        var split = route_lables.split(",");
        $("#classify_people").val(split[0]);
        $("#classify_scenery").val(split[1]);

        $("#start_time").val(route_json[key][6]);
        $("#spend_time").val(route_json[key][7]);
        $("#person_number").val(route_json[key][8]);
        $("#route_cost").val(route_json[key][9]);

        $("#note").prop("hidden",true);
        var addDetail=
            "<div class='route_detail' id='route_detail'>\n" +
            "     <h4 class='route_detail_label' id='route_detail_label'><span class='glyphicon glyphicon-triangle-bottom' id='route_detail_label_icon'></span>&nbsp;攻略简介</h4>\n" +
            "     <textarea class='route_detail_input' id='route_detail_input' maxlength=\"200\" placeholder=\"添加攻略的简介......\">"+route_json[key][5]+"</textarea>\n" +
            "</div>";
        $(".route_content").append(addDetail);
        $(".route_content").append(
            "<script src='../js/writeRoute_after.js'></script>"
        );
        //给菜单栏添加信息
        var addMenuDetail=
            "<ul id='menu'>\n" +
            "    <li class='routeMenu_detail'>\n" +
            "       <a href='#route_detail'>路线介绍</a>" +
            "       <ul class='routeMenu' id='routeMenu_date'>\n" +
            "       </ul>\n"+
            "   </li>\n" +
            "</ul>";
        $(".content-bottom-left").append(addMenuDetail);



        for(var daykey in route_json[key][10]){
            var addDate=
                "<div class='route_date' id='route_date-"+daykey+"' name='unhidden'>\n" +
                "     <h5 id='route_date_label-"+daykey+"'>&nbsp;<span id='route_date_icon-"+daykey+"' class='glyphicon glyphicon-triangle-bottom'></span>&nbsp;Day<strong class='day_value'>"+daykey+"</strong></h5>\n" +
                "</div>";
            $(".route_content").append(addDate);
            //给菜单栏添加信息
            var addMenuDate=
                "<li class='routeMenu_date' id='routeMenu_date"+daykey+"'>\n" +
                "   <a href='#route_date-"+daykey+"'>Day<strong class='MenuDay_value'>"+daykey+"</strong></a>" +
                "   <ul id='routeMenu_city"+daykey+"'>\n" +
                "   </ul>\n"+
                "</li>";
            $("#routeMenu_date").append(addMenuDate);

            day_count+=1;

            //{"route_info":["1632408202@qq.com202069141925","一场说走就走的旅行","2020年6月9日 14:19","../route_avatar/1632408202@qq.com20200609142005.jpg","吃货,文化","我要对你做，春天对樱花树做的事情。 这是我第三次来 日本 ，第二次来 日本 看樱花。 在这个春天里，我终于又来到了 日本 ，走进这里的青青之森与春天的樱花林。 一个人，十五天，从北至南，又从南至北，环形 日本 ，去寻找这里的春天。 我要对你做，春天对樱花树做的事情。","2020-06-09",1,2,200.0,{"1":[{"1":["1632408202@qq.com202069141925-1-1","成都",{"1":["1632408202@qq.com202069141925-1-1-1","都江堰","../scenery_avatar/1632408202@qq.com20200609142147.jpg",4.0,"暂时没有介绍","都江堰市","高铁","123456789"]}]}]}]}
            for(var citykey in route_json[key][10][daykey][0]){
                var addCity=
                    "<div class='route_city' id='route_city-"+daykey+"-"+citykey+"' name='unhidden'>\n" +
                    "     <span class='route_city_id' id='route_city_id-"+daykey+"-"+citykey+"' hidden>"+route_json[key][10][daykey][0][citykey][0]+"</span>\n" +
                    "     <h5 id='route_city_label-"+daykey+"-"+citykey+"'>&nbsp;&nbsp;<span id='route_city_icon-"+daykey+"-"+citykey+"' class='glyphicon glyphicon-triangle-bottom'></span>&nbsp;<strong><input type='text' id='route_city_input-"+daykey+"-"+citykey+"' class='route_city_name' maxlength='20' placeholder='添加城市名称'  value='"+route_json[key][10][daykey][0][citykey][1]+"'></strong></h5>\n" +
                    "</div>";
                $("#route_date-"+daykey+"").append(addCity);

                var addMenuCity=
                    "<li id='routeMenu_city"+daykey+"-"+citykey+"'>\n" +
                    "   <a href='#route_city-"+daykey+"-"+citykey+"'>"+route_json[key][10][daykey][0][citykey][1]+"</a>" +
                    "   <ul id='routeMenu_scenery"+daykey+"-"+citykey+"'>\n" +
                    "   </ul>\n"+
                    "</li>";
                $("#routeMenu_city"+daykey+"").append(addMenuCity);

                city_count[day_count]+=1;

                //添加景点
                for(var scenerykey in route_json[key][10][daykey][0][citykey][2]){
                    var addScenery=
                        "<div class='route_scenery'  id='route_scenery-"+daykey+"-"+citykey+"-"+scenerykey+"' name='unhidden'>\n" +
                        "     <span class='route_scenery_id' id='route_scenery_id-"+daykey+"-"+citykey+"-"+scenerykey+"' hidden>"+route_json[key][10][daykey][0][citykey][2][scenerykey][0]+"</span>\n" +
                        "     <h5>&nbsp;&nbsp;&nbsp;<span id='route_scenery_icon-"+daykey+"-"+citykey+"-"+scenerykey+"' class='glyphicon glyphicon-triangle-bottom'></span>&nbsp;<strong><input type='text' id='route_scenery_input-"+daykey+"-"+citykey+"-"+scenerykey+"' class='route_scenery_name' maxlength='20' placeholder='添加景点名称' value='"+route_json[key][10][daykey][0][citykey][2][scenerykey][1]+"'></strong></h5>\n" +
                        "</div>\n";
                    $("#route_city-"+daykey+"-"+citykey+"").append(addScenery);

                    var addMenuScenery=
                        "<li id='routeMenu_scenery"+daykey+"-"+citykey+"-"+scenerykey+"'>\n" +
                        "   <a href='#route_scenery-"+daykey+"-"+citykey+"-"+scenerykey+"'>"+route_json[key][10][daykey][0][citykey][2][scenerykey][1]+"</a>" +
                        "</li>";
                    $("#routeMenu_scenery"+daykey+"-"+citykey+"").append(addMenuScenery);

                    var addText=
                        "<div class='photo_input' id='route_scenery_photo_input-"+daykey+"-"+citykey+"-"+scenerykey+"'>\n" +
                        "     <input type='file' class='route_scenery_photo' id='route_scenery_photo-"+daykey+"-"+citykey+"-"+scenerykey+"' name='"+route_json[key][10][daykey][0][citykey][2][scenerykey][2]+"' accept='image/jpg,image/jpeg,image/png,image/bmp'>\n" +
                        "</div>\n"+
                        "<div  id='route_scenery_score_label-"+daykey+"-"+citykey+"-"+scenerykey+"'>\n" +
                        "      <div class='scenery_score_label'><span>&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
                        "      <label>景点评分：</label></div>" +
                        "      <div class='scenery_score' id='route_scenery_score-"+daykey+"-"+citykey+"-"+scenerykey+"' name='"+route_json[key][10][daykey][0][citykey][2][scenerykey][3]+"'><span class='star'>☆</span><span class='star'>☆</span><span class='star'>☆</span><span class='star'>☆</span><span class='star'>☆</span></div>" +
                        "</div>\n" +
                        "<div class='scenery_location' id='route_scenery_location_label-"+daykey+"-"+citykey+"-"+scenerykey+"'>\n" +
                        "     <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
                        "     <label>景点位置：</label>\n" +
                        "     <input type='text' class='scenery_location_input' id='route_scenery_location-"+daykey+"-"+citykey+"-"+scenerykey+"' placeholder=\"添加景点的位置...\" value='"+route_json[key][10][daykey][0][citykey][2][scenerykey][5]+"'>\n" +
                        "</div>\n" +
                        "<div class='scenery_traffic' id='route_scenery_traffic_label-"+daykey+"-"+citykey+"-"+scenerykey+"'>\n" +
                        "     <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
                        "     <label>交通工具：</label>\n" +
                        "     <input type='text' class='scenery_traffic_input' id='route_scenery_traffic-"+daykey+"-"+citykey+"-"+scenerykey+"' placeholder=\"添加景点的交通工具...\" value='"+route_json[key][10][daykey][0][citykey][2][scenerykey][6]+"'>\n" +
                        "</div>\n" +
                        "<div class='scenery_tel' id='route_scenery_tel_label-"+daykey+"-"+citykey+"-"+scenerykey+"'>\n" +
                        "     <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
                        "     <label>联系方式：</label>\n" +
                        "     <input type='text' class='scenery_tel_input' id='route_scenery_tel-"+daykey+"-"+citykey+"-"+scenerykey+"' placeholder=\"添加景点的联系方式...\" value='"+route_json[key][10][daykey][0][citykey][2][scenerykey][7]+"'>\n" +
                        "</div>\n"+
                        "<div class='scenery_detail' id='route_scenery_detail_label-"+daykey+"-"+citykey+"-"+scenerykey+"'>\n" +
                        "     <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
                        "     <label>景点介绍：</label>" +
                        "     <textarea class='scenery_detail_input' id='route_scenery_detail-"+daykey+"-"+citykey+"-"+scenerykey+"' placeholder=\"添加景点的简介......\">"+route_json[key][10][daykey][0][citykey][2][scenerykey][4]+"</textarea>\n" +
                        "</div>\n";

                    $("#route_scenery-"+daykey+"-"+citykey+"-"+scenerykey+"").append(addText);
                }
               scenery_count[city_count[day_count]]=parseInt(scenerykey);
            }
            city_count[day_count]=parseInt(citykey);
        }
        day_count=parseInt(daykey);

    }
}

$(function () {
    //当页面加载完，获得昵称
    $.ajax({
        url:"/getUserInfoServlet",
        type:"GET",
        data:{},
        success:function (data) {
            $("#nickName").html(data.nickname+"<span class=\"caret\"></span>");
            /*local_city=data.city;*/
            var email=data.email;
            //获取时间
            var myDate = new Date;
            var year = myDate.getFullYear(); //获取当前年
            var mon = myDate.getMonth() + 1; //获取当前月
            var date = myDate.getDate(); //获取当前日
            var h = myDate.getHours();//获取当前小时数(0-23)
            var m = myDate.getMinutes();//获取当前分钟数(0-59)
            var s = myDate.getSeconds();//获取当前秒
            $("#route_id").html(email+year+mon+date+h+m+s);
            $("#route_createTime").html(year+"年"+mon+"月"+date+"日"+" "+h+":"+m);

            //当页面加载完，获取路线的基本信息
            $.ajax({
                url:"/showRouteInfoServlet",
                type:"POST",
                data:{},
                success:function (route_json) {
                    //自动补充页面
                    fillPage(route_json);
                }
            });
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

    //获取路线标题输入框的文本长度
    var input_title = document.getElementById("input_title");
    input_title.oninput=function () {
        var length = input_title.value.length;
        $("#input_count").html(45-length);
    };

    //点击添加简介，向内容区添加信息,同时向目录添加信息
    $("#add_detail").click(function () {
        $("#note").prop("hidden",true);
        var addDetail=
            "<div class='route_detail' id='route_detail'>\n" +
            "     <h4 class='route_detail_label' id='route_detail_label'><span class='glyphicon glyphicon-triangle-bottom' id='route_detail_label_icon'></span>&nbsp;攻略简介</h4>\n" +
            "     <textarea class='route_detail_input' id='route_detail_input' maxlength=\"200\" placeholder=\"添加攻略的简介......\"></textarea>\n" +
            "</div>";
        $(".route_content").append(addDetail);
        $(".route_content").append(
            "<script src='../js/writeRoute_after.js'></script>"
        );
        //给菜单栏添加信息
        var addMenuDetail=
            "<ul id='menu'>\n" +
            "    <li class='routeMenu_detail'>\n" +
            "       <a href='#route_detail'>路线介绍</a>" +
            "       <ul class='routeMenu' id='routeMenu_date'>\n" +
            "       </ul>\n"+
            "   </li>\n" +
            "</ul>";
        $(".content-bottom-left").append(addMenuDetail);

    });
    $("#add_date").click(function () {
        day_count+=1;
        city_count[day_count]=0;
        var addDate=
            "<div class='route_date' id='route_date-"+day_count+"' name='unhidden'>\n" +
            "     <h5 id='route_date_label-"+day_count+"'>&nbsp;<span id='route_date_icon-"+day_count+"' class='glyphicon glyphicon-triangle-bottom'></span>&nbsp;Day<strong class='day_value'>"+day_count+"</strong></h5>\n" +
            "</div>";
        $(".route_content").append(addDate);
        //给菜单栏添加信息
        var addMenuDate=
            "<li class='routeMenu_date' id='routeMenu_date"+day_count+"'>\n" +
            "   <a href='#route_date-"+day_count+"'>Day<strong class='MenuDay_value'>"+day_count+"</strong></a>" +
            "   <ul id='routeMenu_city"+day_count+"'>\n" +
            "   </ul>\n"+
            "</li>";
        $("#routeMenu_date").append(addMenuDate);

    });

    $("#add_city").click(function () {
        city_count[day_count]+=1;
        scenery_count[city_count[day_count]]=0;
        //获取路线的id
        var route_id=$("#route_id").text();
        var addCity=
            "<div class='route_city' id='route_city-"+day_count+"-"+city_count[day_count]+"' name='unhidden'>\n" +
            "     <span class='route_city_id' id='route_city_id-"+day_count+"-"+city_count[day_count]+"' hidden>"+route_id+"-"+day_count+"-"+city_count[day_count]+"</span>\n" +
            "     <h5 id='route_city_label-"+day_count+"-"+city_count[day_count]+"'>&nbsp;&nbsp;<span id='route_city_icon-"+day_count+"-"+city_count[day_count]+"' class='glyphicon glyphicon-triangle-bottom'></span>&nbsp;<strong><input type='text' id='route_city_input-"+day_count+"-"+city_count[day_count]+"' class='route_city_name' maxlength='20' placeholder='添加城市名称'></strong></h5>\n" +
            "</div>";
        $("#route_date-"+day_count+"").append(addCity);

        var flag=true;
        //给菜单栏添加信息,按enter
        $("#route_city_input-"+day_count+"-"+city_count[day_count]+"").keydown(function (e) {
            if(flag){
                if(e.keyCode==13){
                    var cityName = $("#route_city_input-"+day_count+"-"+city_count[day_count]+"").val();
                    var addMenuCity=
                        "<li id='routeMenu_city"+day_count+"-"+city_count[day_count]+"'>\n" +
                        "   <a href='#route_city-"+day_count+"-"+city_count[day_count]+"'>"+cityName+"</a>" +
                        "   <ul id='routeMenu_scenery"+day_count+"-"+city_count[day_count]+"'>\n" +
                        "   </ul>\n"+
                        "</li>";
                    $("#routeMenu_city"+day_count+"").append(addMenuCity);
                    flag=false;
                }
            }
        });

        $("#add_scenery").click(function () {
            if(flag){
                var cityName = $("#route_city_input-"+day_count+"-"+city_count[day_count]+"").val();
                var addMenuCity=
                    "<li id='routeMenu_city"+day_count+"-"+city_count[day_count]+"'>\n" +
                    "   <a href='#route_city-"+day_count+"-"+city_count[day_count]+"'>"+cityName+"</a>" +
                    "   <ul id='routeMenu_scenery"+day_count+"-"+city_count[day_count]+"'>\n" +
                    "   </ul>\n"+
                    "</li>";
                $("#routeMenu_city"+day_count+"").append(addMenuCity);
                flag=false;
            }
        })

    });

    $("#add_scenery").click(function () {

        scenery_count[city_count[day_count]]+=1;
        var route_id=$("#route_id").text();
        var addScenery=
            "<div class='route_scenery'  id='route_scenery-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"' name='unhidden'>\n" +
            "     <span class='route_scenery_id' id='route_scenery_id-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"' hidden>"+route_id+"-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"</span>\n" +
            "     <h5>&nbsp;&nbsp;&nbsp;<span id='route_scenery_icon-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"' class='glyphicon glyphicon-triangle-bottom'></span>&nbsp;<strong><input type='text' id='route_scenery_input-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"' class='route_scenery_name' maxlength='20' placeholder='添加景点名称'></strong></h5>\n" +
            "</div>\n";
        $("#route_city-"+day_count+"-"+city_count[day_count]+"").append(addScenery);

        var flag=true;
        $("#route_scenery_input-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"").keydown(function (e) {
            if(flag){
                if(e.keyCode==13){
                    //获取值
                    var sceneryName=$("#route_scenery_input-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"").val();
                    //给菜单栏添加信息
                    var addMenuScenery=
                        "<li id='routeMenu_scenery"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"'>\n" +
                        "   <a href='#route_scenery-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"'>"+sceneryName+"</a>" +
                        "</li>";
                    $("#routeMenu_scenery"+day_count+"-"+city_count[day_count]+"").append(addMenuScenery);
                    flag=false;
                }
            }
        });

        $("#add_text").click(function () {
            if(flag){
                //获取值
                var sceneryName=$("#route_scenery_input-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"").val();
                //给菜单栏添加信息
                var addMenuScenery=
                    "<li id='routeMenu_scenery"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"'>\n" +
                    "   <a href='#route_scenery-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"'>"+sceneryName+"</a>" +
                    "</li>";
                $("#routeMenu_scenery"+day_count+"-"+city_count[day_count]+"").append(addMenuScenery);
                flag=false;
            }
        })

    });

    $("#add_text").click(function () {

        var addText=
            "<div class='photo_input' id='route_scenery_photo_input-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"'>\n" +
            "     <input type='file' class='route_scenery_photo' id='route_scenery_photo-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"' name='' accept='image/jpg,image/jpeg,image/png,image/bmp'>\n" +
            "</div>\n"+
            "<div  id='route_scenery_score_label-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"'>\n" +
            "      <div class='scenery_score_label'><span>&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
            "      <label>景点评分：</label></div>" +
            "      <div class='scenery_score' id='route_scenery_score-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"' name=''><span class='star'>☆</span><span class='star'>☆</span><span class='star'>☆</span><span class='star'>☆</span><span class='star'>☆</span></div>" +
            "</div>\n" +
            "<div class='scenery_location' id='route_scenery_location_label-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"'>\n" +
            "     <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
            "     <label>景点位置：</label>\n" +
            "     <input type='text' class='scenery_location_input' id='route_scenery_location-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"' placeholder=\"添加景点的位置...\">\n" +
            "</div>\n" +
            "<div class='scenery_traffic' id='route_scenery_traffic_label-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"'>\n" +
            "     <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
            "     <label>交通工具：</label>\n" +
            "     <input type='text' class='scenery_traffic_input' id='route_scenery_traffic-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"' placeholder=\"添加景点的交通工具...\">\n" +
            "</div>\n" +
            "<div class='scenery_tel' id='route_scenery_tel_label-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"'>\n" +
            "     <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
            "     <label>联系方式：</label>\n" +
            "     <input type='text' class='scenery_tel_input' id='route_scenery_tel-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"' placeholder=\"添加景点的联系方式...\">\n" +
            "</div>\n"+
            "<div class='scenery_detail' id='route_scenery_detail_label-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"'>\n" +
            "     <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>" +
            "     <label>景点介绍：</label>" +
            "     <textarea class='scenery_detail_input' id='route_scenery_detail-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"' placeholder=\"添加景点的简介......\"></textarea>\n" +
            "</div>\n";

        $("#route_scenery-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"").append(addText);


        // 为每一个span加一个属性，标记数字
        $("#route_scenery_score-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+" .star").each(function (k, v) {
            $(this).attr("score", k);
        });
        // 当前current，表示当前点击选中的五角星
        var current;
        $("#route_scenery_score-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+" .star").mouseover(function (e) {
            if (current) return;
            // 如果current里面没有对象就是undefined，就是false
            // 如果有了对象，就是true
            $(this).text("★").prevAll().text("★").end().nextAll().text("☆");
        }).mouseout(function (e) {
            if (current) return;
            $(this).text("☆").siblings().text("☆");
        }).click(function (e) {
            if (current) return;
            current = this;

            // alert($(current).attr("score"));
            $("#route_scenery_score-"+day_count+"-"+city_count[day_count]+"-"+scenery_count[city_count[day_count]]+"").attr("name",parseInt($(current).attr("score"))+1);
        });
    });
});