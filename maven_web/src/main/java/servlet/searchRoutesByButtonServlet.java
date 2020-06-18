package servlet;

import domain.route;
import redis.clients.jedis.Jedis;
import service.impl.routeServiceImpl;
import service.routeService;
import util.JedisPoolUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
* 通过点击分类路线按钮，筛选路线
* */
public class searchRoutesByButtonServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");//取值：Local,Province,Nation,World
        int currentPage = Integer.parseInt(request.getParameter("Page"));
        String sortType=request.getParameter("sortType");
        if(sortType==null){
            sortType="createTime";//默认按照创建时间降序排序
        }
        //获取用户当前城市
        HttpSession httpSession=request.getSession();
        String email = (String) httpSession.getAttribute("email");

        Jedis jedis= JedisPoolUtils.getJedis();
        String city = jedis.hget(email,"city");
        jedis.close();


        routeService service=new routeServiceImpl();
        List<route> routeList=service.searchRoutesByClassify(city,type,currentPage,sortType);
        int totalNumber=routeList.size();
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



        //将routeList存入session中,等待获取
        HttpSession session=request.getSession();
        session.setAttribute("searchRouteList",filterRouteList);
        session.setAttribute("inputText",type);
        session.setAttribute("currentPage",currentPage);
        session.setAttribute("type",type);
        session.setAttribute("totalNumber",totalNumber);
        session.setAttribute("sortType",sortType);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
