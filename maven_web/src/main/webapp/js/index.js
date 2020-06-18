function Popular_btn(){
    $("#Popular_btn").trigger('click');
}

function appendItems(popular_json){
    $("#raiders_items").empty();
    for(var key in popular_json){
        var iconLikeAvatar;
        var iconDislikeAvatar;
        var iconCollectAvatar;
        if(popular_json[key][6]===true){
            iconLikeAvatar="./images/icon_like_selected.jpg";
        }else{
            iconLikeAvatar="./images/icon_like_default.jpg";
        }
        if(popular_json[key][8]==true){
            iconDislikeAvatar="./images/icon_dislike_selected.jpg";
        }else{
            iconDislikeAvatar="./images/icon_dislike_default.jpg";
        }
        if(popular_json[key][10]==true){
            iconCollectAvatar="./images/icon_collect_selected.jpg";
        }else{
            iconCollectAvatar="./images/icon_collect_default.jpg";
        }
        var addItem=
            "<div class='raiders_item'>\n"+
            "    <span class='route_id' id="+popular_json[key][0]+" hidden>"+popular_json[key][0]+"</span>\n"+
            "    <!--条目图片-->\n"+
            "    <img class='item_image' src="+popular_json[key][1]+" id='item_image-"+popular_json[key][0]+"'>\n"+
            "    <!--条目标题-->\n"+
            "    <label class='item_label' id='item_label-"+popular_json[key][0]+"'>"+popular_json[key][3]+"</label>\n"+
            "    <!--条目内容-->\n"+
            "    <p class='item_detail' id='item_detail-"+popular_json[key][0]+"'>"+popular_json[key][4]+"</p>\n"+
            "    <!--条目类型-->\n"+
            "    <img class='item_type' src='./images/icon_type.jpg' id='item_type-"+popular_json[key][0]+"'><span class='item_type_text' id='item_type_text-"+popular_json[key][0]+"'>"+popular_json[key][2]+"</span>\n"+
            "    <!--条目创建者-->\n"+
            "    <img class='item_creater' src='./images/icon_creater.jpg' id='item_creater-"+popular_json[key][0]+"'><span class='item_creater_text' id='item_creater_text-"+popular_json[key][0]+"'>"+popular_json[key][5]+"</span>\n"+
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

$(function () {
    //当页面加载完，获得昵称和居住城市
    $.ajax({
        url:"/getUserInfoServlet",
        type:"GET",
        data:{},
        success:function (data) {
            $("#nickName").html(data.nickname+"<span class=\"caret\"></span>");
            /*local_city=data.city;
            email=data.email;*/
        }
    });


    //轮播图效果
    //修改图片src属性
    var img_filename=["./images/Carousel_1.png","./images/Carousel_2.png","./images/Carousel_3.png","./images/Carousel_4.png","./images/Carousel_5.png"]
    var i=0;
    function fun() {
        var img=document.getElementById("img");
        img.src=img_filename[i];
        i++;
        if(i>4){
            i=0;
        }
    }
    var id=setInterval(fun,5000);


    //页面加载完成，发起ajax请求，请求推荐的五个
    $.ajax({
        url:"/getRecommendItemsServlet",
        type:"POST",
        data:{},
        success:function (recommend_json) {
            for(var key in recommend_json){
                var route_id=recommend_json[key][0];
                var route_imgSrc=recommend_json[key][1];
                var route_label=recommend_json[key][2];
                var route_name=recommend_json[key][3];
                var addItem="<div class='recommend_item' id='recommend_item-"+route_id+"'>\n"+
                    "    <span class='recommendRoute_id' id='recommendRoute_id-"+route_id+"' hidden>"+route_id+"</span>\n"+
                    "    <img class='recommend_image' src="+route_imgSrc+" id='recommend_image-"+route_id+"' alt='推荐路线'>\n"+
                    "    <p class='recommend_label' id='recommend_label-"+route_id+"'>"+route_label+"</p>\n"+
                    "    <p class='recommend_name' id='recommend_name-"+route_id+"'>"+route_name+"</p>\n"+
                    "</div>\n";
                $("#items").append(addItem);
            }
        }
    });

    //页面加载完成，自动触发获取点击Popular_btn按钮事件，请求大众喜欢的25个，分5页展示
    window.onload=Popular_btn;

    //页面跳转
    //点击搜一搜发起搜索，每页最多展示10条
    $("#search_btn").click(function () {
        var text=$("#input_CityMsg").val();
        $.ajax({
            url:"/searchRoutesServlet",
            type:"POST",
            data:{"input_text":text,"Page":1},
            success:function (data) {
                //实现页面跳转到搜索结果展示页面
                location.href="./routes/searchResult.html";
            }
        })
    });
    //点击按类别搜一搜发起搜索，每页最多展示10条
    $("#search_btn_2").click(function () {
        //获取到单选框的值
        var radio=document.getElementsByName("search");
        var search_type=null;   //  selectvalue为radio中选中的值
        for(var i=0;i<radio.length;i++) {
            if (radio[i].checked == true) {
                search_type = radio[i].value;
                break;
            }
        }
        var text=$("#input_CityMsg_2").val();
        $.ajax({
            url:"/searchRoutesServlet",
            type:"POST",
            data:{"type":search_type,"input_text":text,"Page":1},
            success:function (data) {
                //实现页面跳转到搜索结果展示页面
                location.href="./routes/searchResult.html";
            }
        })
    });

    //点击当地游，获取搜索结果，分页展示，每页展示10条
    $("#local_travel").click(function () {
        $.ajax({
            url:"/searchRoutesByButtonServlet",
            type:"POST",
            data:{"type":"Local","Page":1},
            success:function (search_LocalResults) {
                //实现页面跳转到路线结果展示页面
                location.href="../routes/classifyResult.html";
            }
        })
    });
    //点击省内游，获取搜索结果，分页展示，每页展示10条
    $("#province_travel").click(function () {
        $.ajax({
            url:"/searchRoutesByButtonServlet",
            type:"POST",
            data:{"type":"Province","Page":1},
            success:function (search_ProvinceResults) {
                //实现页面跳转到路线结果展示页面
                location.href="../routes/classifyResult.html";
            }
        })
    });
    //点击国内游，获取搜索结果，分页展示，每页展示10条
    $("#nation_travel").click(function () {
        $.ajax({
            url:"/searchRoutesByButtonServlet",
            type:"POST",
            data:{"type":"Nation","Page":1},
            success:function (search_NationResults) {
                //实现页面跳转到路线结果展示页面
                location.href="../routes/classifyResult.html";
            }
        })
    });
    //点击国际游，获取搜索结果，分页展示，每页展示10条
    $("#world_travel").click(function () {
        $.ajax({
            url:"/searchRoutesByButtonServlet",
            type:"POST",
            data:{"type":"World","Page":1},
            success:function (search_WorldResults) {
                //实现页面跳转到路线结果展示页面
                location.href="../routes/classifyResult.html";
            }
        })
    });

    //点击大众喜欢，筛选出5页，每页5条
    $("#Popular_btn").click(function () {
        //修改按钮的颜色
        $("#Popular_btn").css("background","#ec971f");
        $("#lastestPublish_btn").css("background","#fff");
        //自定义选中属性
        $("#Popular_btn").attr("clicked","clicked");
        $("#lastestPublish_btn").attr("clicked","unclicked");

        //获取当前class="active"的页
        var page=$(".pagination li");
        for(var i=0;i<page.length;i++) {
            if ($(page[i]).hasClass("active")) {
                $(page[i]).prop("class", "");
                $(page[1]).prop("class", "active");
                $(page[0]).prop("class", "disabled");
                $(page[6]).prop("class", "");
            }
        }

        $.ajax({
            url:"/getPopularItemsServlet",
            type:"POST",
            data:{"currentPage":1},
            success:function (popular_json) {
                appendItems(popular_json);
            }
        });
    });

    //点击最新发表，筛选出5页，每页5条
    $("#lastestPublish_btn").click(function () {
        //修改按钮的颜色
        $("#lastestPublish_btn").css("background","#ec971f");
        $("#Popular_btn").css("background","#fff");
        //自定义选中属性
        $("#lastestPublish_btn").attr("clicked","clicked");
        $("#Popular_btn").attr("clicked","unclicked");

        //获取当前class="active"的页
        var page=$(".pagination li");
        for(var i=0;i<page.length;i++) {
            if ($(page[i]).hasClass("active")) {
                $(page[i]).prop("class", "");
                $(page[1]).prop("class", "active");
                $(page[0]).prop("class", "disabled");
                $(page[6]).prop("class", "");
            }
        }

        $.ajax({
            url:"/getLastestItemsServlet",
            type:"POST",
            data:{"currentPage":1},
            success:function (lastestItem_json) {
                appendItems(lastestItem_json);
            }
        })
    });

    //点击写游记按钮，跳转到指定页面
    $("#writeRaiders_btn").click(function () {
        location.href="../routes/writeRoute.html";
    });

    //点击分页栏，重新请求
    $("#currentPage_front").click(function () {
        //获取当前class="active"的页
        var page=$(".pagination li");
        for(var i=0;i<page.length;i++){
            if($(page[i]).prop("class")=="active"){
                if(i==2){
                    $(page[0]).prop("class","disabled");
                }
                $(page[i]).prop("class","");
                $(page[i-1]).prop("class","active");
                $(page[6]).prop("class","");

                //获取当前类型
                var type=$("#lastestPublish_btn").attr("clicked");
                if(type=="clicked"){
                    //发起ajax请求，请求最新发表的
                    $.ajax({
                        url:"/getLastestItemsServlet",
                        type:"POST",
                        data:{"currentPage":i-1},
                        success:function(lastestItem_json) {
                            appendItems(lastestItem_json);
                        }
                    });
                }else{
                    //发起ajax请求,请求大众喜欢的
                    $.ajax({
                        url:"/getPopularItemsServlet",
                        type:"POST",
                        data:{"currentPage":i-1},
                        success:function(popular_json) {
                            appendItems(popular_json);
                        }
                    });
                }
                break;
            }
        }
    });
    $("#currentPage_1").click(function () {
        //获取当前class="active"的页
        var page=$(".pagination li");
        for(var i=0;i<page.length;i++){
            if($(page[i]).hasClass("active")){
                $(page[i]).prop("class","");
                $(page[1]).prop("class","active");
                $(page[0]).prop("class","disabled");
                $(page[6]).prop("class","");

                //获取当前类型
                var type=$("#lastestPublish_btn").attr("clicked");
                if(type=="clicked"){
                    //发起ajax请求，请求最新发表的
                    $.ajax({
                        url:"/getLastestItemsServlet",
                        type:"POST",
                        data:{"currentPage":1},
                        success:function(lastestItem_json) {
                            appendItems(lastestItem_json);
                        }
                    });
                }else{
                    //发起ajax请求,请求大众喜欢的
                    $.ajax({
                        url:"/getPopularItemsServlet",
                        type:"POST",
                        data:{"currentPage":1},
                        success:function(popular_json) {
                            appendItems(popular_json);
                        }
                    });
                }
                break;
            }
        }
    });
    $("#currentPage_2").click(function () {
        //获取当前class="active"的页
        var page=$(".pagination li");
        for(var i=0;i<page.length;i++){
            if($(page[i]).prop("class")=="active"){
                $(page[i]).prop("class","");
                $(page[2]).prop("class","active");
                $(page[0]).prop("class","");
                $(page[6]).prop("class","");

                //获取当前类型
                var type=$("#lastestPublish_btn").attr("clicked");
                if(type=="clicked"){
                    //发起ajax请求，请求最新发表的
                    $.ajax({
                        url:"/getLastestItemsServlet",
                        type:"POST",
                        data:{"currentPage":2},
                        success:function(lastestItem_json) {
                            appendItems(lastestItem_json);
                        }
                    });
                }else{
                    //发起ajax请求,请求大众喜欢的
                    $.ajax({
                        url:"/getPopularItemsServlet",
                        type:"POST",
                        data:{"currentPage":2},
                        success:function(popular_json) {
                            appendItems(popular_json);
                        }
                    });
                }
                break;
            }
        }
    });
    $("#currentPage_3").click(function () {
        //获取当前class="active"的页
        var page=$(".pagination li");
        for(var i=0;i<page.length;i++){
            if($(page[i]).prop("class")=="active"){
                $(page[i]).prop("class","");
                $(page[3]).prop("class","active");
                $(page[0]).prop("class","");
                $(page[6]).prop("class","");

                //获取当前类型
                var type=$("#lastestPublish_btn").attr("clicked");
                if(type=="clicked"){
                    //发起ajax请求，请求最新发表的
                    $.ajax({
                        url:"/getLastestItemsServlet",
                        type:"POST",
                        data:{"currentPage":3},
                        success:function(lastestItem_json) {
                            appendItems(lastestItem_json);
                        }
                    });
                }else{
                    //发起ajax请求,请求大众喜欢的
                    $.ajax({
                        url:"/getPopularItemsServlet",
                        type:"POST",
                        data:{"currentPage":3},
                        success:function(popular_json) {
                            appendItems(popular_json);
                        }
                    });
                }
                break;
            }
        }
    });
    $("#currentPage_4").click(function () {
        //获取当前class="active"的页
        var page=$(".pagination li");
        for(var i=0;i<page.length;i++){
            if($(page[i]).prop("class")=="active"){
                $(page[i]).prop("class","");
                $(page[4]).prop("class","active");
                $(page[0]).prop("class","");
                $(page[6]).prop("class","");

                //获取当前类型
                var type=$("#lastestPublish_btn").attr("clicked");
                if(type=="clicked"){
                    //发起ajax请求，请求最新发表的
                    $.ajax({
                        url:"/getLastestItemsServlet",
                        type:"POST",
                        data:{"currentPage":4},
                        success:function(lastestItem_json) {
                            appendItems(lastestItem_json);
                        }
                    });
                }else{
                    //发起ajax请求,请求大众喜欢的
                    $.ajax({
                        url:"/getPopularItemsServlet",
                        type:"POST",
                        data:{"currentPage":4},
                        success:function(popular_json) {
                            appendItems(popular_json);
                        }
                    });
                }
                break;
            }
        }
    });
    $("#currentPage_5").click(function () {
        //获取当前class="active"的页
        var page=$(".pagination li");
        for(var i=0;i<page.length;i++){
            if($(page[i]).prop("class")=="active"){
                $(page[i]).prop("class","");
                $(page[5]).prop("class","active");
                $(page[6]).prop("class","disabled");
                $(page[0]).prop("class","");

                //获取当前类型
                var type=$("#lastestPublish_btn").attr("clicked");
                if(type=="clicked"){
                    //发起ajax请求，请求最新发表的
                    $.ajax({
                        url:"/getLastestItemsServlet",
                        type:"POST",
                        data:{"currentPage":5},
                        success:function(lastestItem_json) {
                            appendItems(lastestItem_json);
                        }
                    });
                }else{
                    //发起ajax请求,请求大众喜欢的
                    $.ajax({
                        url:"/getPopularItemsServlet",
                        type:"POST",
                        data:{"currentPage":5},
                        success:function(popular_json) {
                            appendItems(popular_json);
                        }
                    });
                }
                break;
            }
        }
    });
    $("#currentPage_next").click(function () {
        //获取当前class="active"的页
        var page=$(".pagination li");
        for(var i=0;i<page.length;i++){
            if($(page[i]).prop("class")=="active"){
                if(i==4){
                    $(page[6]).prop("class","disabled");
                }
                $(page[i]).prop("class","");
                $(page[i+1]).prop("class","active");
                $(page[0]).prop("class","");

                //获取当前类型
                var type=$("#lastestPublish_btn").attr("clicked");
                if(type=="clicked"){
                    //发起ajax请求，请求最新发表的
                    $.ajax({
                        url:"/getLastestItemsServlet",
                        type:"POST",
                        data:{"currentPage":i+1},
                        success:function(lastestItem_json) {
                            appendItems(lastestItem_json);
                        }
                    });
                }else{
                    //发起ajax请求,请求大众喜欢的
                    $.ajax({
                        url:"/getPopularItemsServlet",
                        type:"POST",
                        data:{"currentPage":i+1},
                        success:function(popular_json) {
                            appendItems(popular_json);
                        }
                    });
                }
                break;
            }
        }
    });
});