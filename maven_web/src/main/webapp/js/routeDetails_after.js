function likeItemClick(route_id,typeName) {
    //获取当前图片和数值
    var likeId = document.getElementById(typeName+"_"+route_id);
    var state = $(likeId).attr("src").split("_")[2];
    var likeTextId=document.getElementById(typeName+"_text-"+route_id);
    var value = parseInt($(likeTextId).html());
    if(state =="default.jpg"){
        //更换图片
        $(likeId).attr("src","../images/icon_like_selected.jpg");
        //将值加一
        $(likeTextId).text(value+1);

        //向后台保存数据
        $.ajax({
            url:"/addLikeRouteServlet",
            type:"POST",
            data:{"like_type":"add","route_id":route_id},
            success:function (data) {
                //若已点击点踩，则取消点踩
                cancelDislikeItem(route_id,"itemDislike");
            }
        })
    }else{
        //更换图片
        $(likeId).attr("src","../images/icon_like_default.jpg");
        //将值减一
        $(likeTextId).text(value-1);

        //向后台保存数据
        $.ajax({
            url:"/addLikeRouteServlet",
            type:"POST",
            data:{"like_type":"del","route_id":route_id},
            success:function (data) {
                cancelLikeItem(route_id,typeName);
            }
        })
    }
}

function dislikeItemClick(route_id,typeName){
    //获取当前图片和数值
    var dislikeId = document.getElementById(typeName+"_"+route_id);
    var state = $(dislikeId).attr("src").split("_")[2];
    var dislikeTextId=document.getElementById(typeName+"_text-"+route_id);
    var value = parseInt($(dislikeTextId).html());
    if(state =="default.jpg"){
        $(dislikeId).attr("name","selected");
        //更换图片
        $(dislikeId).attr("src","../images/icon_dislike_selected.jpg");
        //将值加一
        $(dislikeTextId).text(value+1);

        //向后台保存数据
        $.ajax({
            url:"/addDislikeRouteServlet",
            type:"POST",
            data:{"like_type":"add","route_id":route_id},
            success:function (data) {
                //若已点击点赞，则取消点赞
                cancelLikeItem(route_id,"itemLike");
            }
        })
    }else{
        //更换图片
        $(dislikeId).attr("src","../images/icon_dislike_default.jpg");
        //将值减一
        $(dislikeTextId).text(value-1);

        //向后台保存数据
        $.ajax({
            url:"/addDislikeRouteServlet",
            type:"POST",
            data:{"like_type":"del","route_id":route_id},
            success:function (data) {
                cancelDislikeItem(route_id,typeName);
            }
        })
    }
}
function collectItemClick(route_id,typeName){
    //获取当前图片和数值
    var collectId = document.getElementById(typeName+"_"+route_id);
    var state = $(collectId).attr("src").split("_")[2];
    var collectTextId=document.getElementById(typeName+"_text-"+route_id);
    var value = parseInt($(collectTextId).html());
    if(state =="default.jpg"){
        //更换图片
        $(collectId).attr("src","../images/icon_collect_selected.jpg");
        //将值加一
        $(collectTextId).text(value+1);

        //向后台保存数据
        $.ajax({
            url:"/addCollectRouteServlet",
            type:"POST",
            data:{"like_type":"add","route_id":route_id},
            success:function (data) {

            }
        })
    }else{
        //更换图片
        $(collectId).attr("src","../images/icon_collect_default.jpg");
        //将值减一
        $(collectTextId).text(value-1);

        //向后台保存数据
        $.ajax({
            url:"/addCollectRouteServlet",
            type:"POST",
            data:{"like_type":"del","route_id":route_id},
            success:function (data) {

            }
        })
    }
}

function cancelLikeItem(route_id,typeName){
    //获取当前图片和数值
    var likeId = document.getElementById(typeName+"_"+route_id);
    var state = $(likeId).attr("src").split("_")[2];
    var likeTextId=document.getElementById(typeName+"_text-"+route_id);
    var value = parseInt($(likeTextId).html());
    if(state =="selected.jpg"){
        //更换图片
        $(likeId).attr("src","../images/icon_like_default.jpg");
        //将值加一
        $(likeTextId).text(value-1);

        //向后台保存数据
        $.ajax({
            url:"/addLikeRouteServlet",
            type:"POST",
            data:{"like_type":"del","route_id":route_id},
            success:function (data) {

            }
        })
    }
}
function cancelDislikeItem(route_id,typeName){
    //获取当前图片和数值
    var dislikeId = document.getElementById(typeName+"_"+route_id);
    var state = $(dislikeId).attr("src").split("_")[2];
    var dislikeTextId=document.getElementById(typeName+"_text-"+route_id);
    var value = parseInt($(dislikeTextId).html());
    if(state =="selected.jpg"){
        $(dislikeId).attr("name","default");
        //更换图片
        $(dislikeId).attr("src","../images/icon_like_default.jpg");
        //将值加一
        $(dislikeTextId).text(value-1);

        //向后台保存数据
        $.ajax({
            url:"/addDislikeRouteServlet",
            type:"POST",
            data:{"like_type":"del","route_id":route_id},
            success:function (data) {

            }
        })
    }
}
function sceneryClick(scenery_id){
    $.ajax({
       url:"/getSceneryDetailsServlet",
        type:"POST",
        data:{"scenery_id":scenery_id},
        success:function (data) {
            location.href="../routes/sceneryDetails.html";
        }
    });
}

$(function () {
    //监听页面点击事件，获取点击元素的id
    $(document).click(function (e) {
        var id=$(e.target).attr("id");
        var split = id.split("_");
        //点击推荐栏中的条目所在的div，跳转到条目的详情页
        if(split[0]=="scenery"){
            sceneryClick(split[1]);
        }
        if(split[0]=="itemLike"){
            likeItemClick(split[1],split[0]);
        }
        if(split[0]=="itemDislike"){
            dislikeItemClick(split[1],split[0]);
        }
        if(split[0]=="itemCollect"){
            collectItemClick(split[1],split[0]);
        }
    });
});