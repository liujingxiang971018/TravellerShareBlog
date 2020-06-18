package servlet;

import domain.route;
import service.impl.routeServiceImpl;
import service.routeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
* 根据输入框提供的搜索条件，进行搜索,每页展示10条数据，将结果存放在session中
* 排序类别：按照发布时间降序排序（createTime）、收藏数量降序排序（collectCount）、路线花费时间升序（spendTime）、路线成本升序（routeCost）；排序默认按照时间降序排序
* */

public class searchRoutesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");

        String type = request.getParameter("type");//city或者scenery
        System.out.println("type="+type);
        String inputText = request.getParameter("input_text");

        String sortType=request.getParameter("sortType");
        if(sortType==null){
            sortType="createTime";//默认按照创建时间降序排序
        }
        int currentPage = Integer.parseInt(request.getParameter("Page"));

        int totalNumber=0;

        //调用service
        routeService routeService=new routeServiceImpl();
        List<route> routeList=null;

        if("city".equals(type)){
            //说明指定搜索类型，并为city
            routeList=routeService.searchRouteItemsByCity(inputText,currentPage,sortType);
            totalNumber=routeList.size();
        }else if("scenery".equals(type)){
            //说明指定搜索类型，并为scenery
            routeList=routeService.searchRouteItemsByScenery(inputText,currentPage,sortType);
            totalNumber=routeList.size();

        }else{
            //说明是不包含类型的，城市和地点都会搜索
            routeList=routeService.searchRouteItemsByAll(inputText,currentPage,sortType);
            totalNumber=routeList.size();
        }

        List<route> filterRouteList=new ArrayList<>();
        if(totalNumber!=0){
            //取对应页码的5个
            int i=(currentPage-1)*5;
            if(i+5>routeList.size()){
                //如果取最后一页
                for(int j=i;j<routeList.size();j++){
                    filterRouteList.add(routeList.get(j));
                }
            }else{
                for(int j=i;j<i+5;j++){
                    filterRouteList.add(routeList.get(j));
                }
            }
        }
        System.out.println(filterRouteList);

        //将routeList存入session中,等待获取
        HttpSession session=request.getSession();
        session.setAttribute("searchRouteList",filterRouteList);
        session.setAttribute("inputText",inputText);
        session.setAttribute("currentPage",currentPage);
        session.setAttribute("type",type);
        session.setAttribute("totalNumber",totalNumber);
        session.setAttribute("sortType",sortType);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
