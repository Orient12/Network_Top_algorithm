<%--
  Created by IntelliJ IDEA.
  User: zhp13
  Date: 2018/9/28
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script src="d3.js"></script>
<script>
        var dataC=<%=request.getAttribute("circle")%>;
        var dataL=<%=request.getAttribute("line")%>;
        var circles=eval(dataC);
        var lines=eval(dataL);
    var drag=d3.drag().on("drag",dragmove).on("start",dragss).on("end",drage);
    function dragmove(d){
        d3.select(this).attr("cx",d.cx=d3.event.x)
            .attr("cy",d.cy=d3.event.y);
    }
    function dragss(d){
        d3.select(this).attr("fill","black");
    }
    function drage(d) {
        d3.select(this).attr("fill","yellow");
    }
    var svg=d3.select("body")
        .append("svg")
        .attr("height",30000)
        .attr("width",40000);
        var lin=svg.selectAll("line")
            .data(lines)
            .enter()
            .append("line")
            .attr("x1",function (d) {
                return circles[d.source].cx;
            })
            .attr("y1",function (d) {
                return circles[d.source].cy;
            })
            .attr("x2",function (d) {
                return circles[d.target].cx;
            })
            .attr("y2",function (d) {
                return circles[d.target].cy;
            })
            .attr("style","stroke:black;strock-with:4");
    var cir=svg.selectAll("circle")
        .data(circles)
        .enter()
        .append("circle")
        .attr("cx",function (d) {
            return d.cx;
        }).attr("cy",function (d) {
         return d.cy;
        }).attr("r",function (d) {
         return 20;
        }).attr("fill","blue")
        .attr("stroke","white")
        .attr("stroke-width",1)
        .call(drag);

</script>
</body>
</html>
