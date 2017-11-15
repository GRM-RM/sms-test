<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
</head>

<body>
<form id="form1" name="form1" method="post" action="register">
  <p>&nbsp;</p>
  <table width="600" border="1" align="center" cellpadding="1" cellspacing="0">
    <tr>
      <td align="right">token</td>
      <td><label for="token"></label>
      <input type="text" name="token" id="token" value="${token}" /></td>
    </tr>
    <tr>
      <td align="right">业务方名称</td>
      <td><label for="cname"></label>
      <input type="text" name="cname" id="cname" /></td>
    </tr>
    <tr>
      <td align="right">日总量</td>
      <td><label for="maxday"></label>
      <input type="text" name="maxday" id="maxday" /></td>
    </tr>
    <tr>
      <td align="right">手机日上限</td>
      <td><input type="text" name="maxphone" id="maxphone" /></td>
    </tr>
    <tr>
      <td align="right"><input type="submit" name="button" id="button" value="提交" /></td>
      <td>&nbsp;</td>
    </tr>
  </table>
  <p>&nbsp;</p>
</form>
</body>
</html>
