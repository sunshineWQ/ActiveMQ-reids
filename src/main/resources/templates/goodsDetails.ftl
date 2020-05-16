<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
  <title>商品列表</title>
</head>

<body>

<p>${msg!}</p>
<table  border="1px"  align="center">
    <tr>
        <td>商品编号</td>
        <td>商品名称</td>
        <td>商品库存</td>
        <td>操作</td>
    </tr>

    ${goods!}
    <#if  goods ??>
     <tr>
         <td>${goods.gid!}</td>
         <td>${goods.gname!}</td>
         <td>${goods.stock!}</td>
         <td><a  href="/buy?uid=1055&gid=${goods.gid!}">购买</a></td>

     </tr>
    </#if>



</table>

</body>

</html>