<%--
  Created by IntelliJ IDEA.
  User: 28624
  Date: 2022/1/8
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--		分页条开始--%>
<div id="page_nav">
    <c:if test="${requestScope.page.pageNo>1}">
        <a href="${requestScope.page.url}&pageNo=1">首页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo-1}">上一页</a>
    </c:if>

    <%--			页码输出的开始--%>
    <c:choose>
        <c:when test="${requestScope.page.pageTotal<=5}">
            <%--情况1：如果总页码小于等于5的情况，页码范围是：1——总页码--%>
            <c:set var="begin" value="1"/>
            <c:set var="end" value="${requestScope.page.pageTotal}"/>
        </c:when>
        <%--			1页            1--%>
        <%--			2页            1，2--%>
        <%--			3页            1，2，3--%>
        <%--			4页            1，2，3，4--%>
        <%--			5页            1，2，3，4，5--%>

        <%--			情况2：总页码大于5的情况。假设一共10页--%>
        <c:when test="${requestScope.page.pageTotal>5}">
            <c:choose>
                <c:when test="${requestScope.page.pageNo<=3}">
                    <%--小情况1：当前页码为前面三个：1，2，3的情况，页码范围是：1——5。--%>
                    <c:set var="begin" value="1"/>
                    <c:set var="end" value="5"/>
                    <%--			【1】，2，3，4，5--%>
                    <%--			1，【2】，3，4，5--%>
                    <%--			1，2，【3】，4，5--%>
                </c:when>
                <%--小情况2：当前页码为最后三个：8，9，10的情况，页码范围是：总页码减4——总页码。--%>
                <c:when test="${requestScope.page.pageNo>requestScope.page.pageTotal-3}">
                    <c:set var="begin" value="${requestScope.page.pageTotal-4}"/>
                    <c:set var="end" value="${requestScope.page.pageTotal}"/>
                    <%--			6，7，【8】，9，10--%>
                    <%--			6，7，8，【9】，10--%>
                    <%--			6，7，8，9，【10】--%>
                </c:when>
                <%--小情况3：4，5，6，7，页码是：当前页码减2——当前页码加2.--%>
                <c:otherwise>
                    <c:set var="begin" value="${requestScope.page.pageNo-2}"/>
                    <c:set var="end" value="${requestScope.page.pageNo+2}"/>
                    <%--			2，3，【4】，5，6--%>
                    <%--			3，4，【5】，6，7--%>
                    <%--			4，5，【6】，7，8--%>
                    <%--			5，6，【7】，8，9--%>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>

    <c:forEach begin="${begin}" end="${end}" var="i">
        <c:if test="${i==requestScope.page.pageNo}">
            【${i}】
        </c:if>
        <c:if test="${i!=requestScope.page.pageNo}">
            <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
        </c:if>

    </c:forEach>

    <%--			页码输出的结束--%>

    <c:if test="${requestScope.page.pageNo<requestScope.page.pageTotal}">
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo+1}">下一页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageTotal}">末页</a>
    </c:if>

    共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录
    到第<input value="${param.pageNo}" name="pn" id="pn_input"/>页
    <input id="searchPageBtn" type="button" value="确定">
    <script type="text/javascript">
        $(function (){
            $("#searchPageBtn").click(function (){
                var pageNo = $("#pn_input").val();
                //javaScript语言提供了一个location地址栏对象
                //他有一个属性叫href，他可以获取浏览器地址栏中的地址
                //href属性可读，可写
                if (pageNo>0&&pageNo<=${requestScope.page.pageTotal})
                {
                    location.href = "${pageScope.bathPath}${requestScope.page.url}&pageNo="+pageNo;
                }
                else{
                    alert("不存在该页码")
                }
            })
        })
    </script>
</div>
<%--		分页条结束--%>
