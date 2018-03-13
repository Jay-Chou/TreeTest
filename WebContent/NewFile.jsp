<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'map_baidu.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		body {
			font-size: 12px;
		}
		#container {
			position: relative;
			width: 1300px;
			margin: 30px auto 0px;
		}
		#header {
			position: relative;
			width: 100%;
			height: 30px;
		}
		#header input[type="text"]{
			width: 300px;
		}
		#mainbody {
			position: relative;
			with: 100%;
			height: 450px;
		}
		#sidebar {
			float: left;
			width: 20%;
			height:100%;
			border: solid 1px blue;
			overflow: auto;
		}
		#sidebar ul{
  		list-style-type: none;
  		
  		}
  		#sidebar ul li{
  		width:100px;
  		height: 30px;
  		font-size: 24px;
  		}
  		#sidebar ul li:hover{
  		cursor: pointer;
  		background-color: blue;
  		}
		#mapview{
			float: right;
			width: 78%;
			height:100%;
			border: solid 1px blue;
		}
	</style>
  <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>  
  <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=9Lx5vB2Nq1bpsWWBR0HuuAHu"></script>
  <script type="text/javascript">
    var map;
    
  	var local;  	
  	var driving;
  	var myDis;  	
  	function init(){  		
  		map = new BMap.Map("mapview");//参数为显示map的容器  		
  		var point = new BMap.Point(106.54,29.59);  // 创建点坐标  
  		map.centerAndZoom(point, 15);  		
  		map.addControl(new BMap.NavigationControl());    
  		map.addControl(new BMap.ScaleControl());    
  		map.addControl(new BMap.OverviewMapControl());    
  		map.addControl(new BMap.MapTypeControl());
  		myDis = new BMapLib.DistanceTool(map);  	   
  		local = new BMap.LocalSearch(map,{renderOptions:{map:map,panel:"sidebar1"},pageCapacity:4});  		
  		driving = new BMap.DrivingRoute(map,{renderOptions:{map:map,panel:"sidebar",autoViewport:true}});
  		
  	}
  	function searchByAddress() {
		var address = document.getElementById("address");
		if(!address.value){
			address.focus();
			return;
		}
		map.clearOverlays();
		local.setSearchCompleteCallback(function (searchResult){
			var poi = searchResult.getPoi(0);
			alert(poi.point.lng + "," + poi.point.lat);
			map.centerAndZoom(poi.point, 15);});
		local.search(address);
		
	}   		
  	function myDisopen(){
  		myDis.open();
  	}  	
  </script>
  </head>
  
  <body onload="init()">
    <h2 align="center">百度地图</h2>
    <hr/>
    <div id="container">
    	   	
    		<div id="mapview"></div>  		
    	
    </div>
  </body>
</html>
