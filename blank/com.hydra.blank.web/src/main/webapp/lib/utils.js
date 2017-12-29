//复制到剪切板js代码
function copyToClipBoard(s) {
	//alert(s);
	if (window.clipboardData) {
		window.clipboardData.setData("Text", s);
	} else if (navigator.userAgent.indexOf("Opera") != -1) {
		window.location = s;
	} else if (window.netscape) {
		try {
			netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
		} catch (e) {
			alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
		}
		var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
		if (!clip)
			return;
		var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
		if (!trans)
			return;
		trans.addDataFlavor('text/unicode');
		var str = new Object();
		var len = new Object();
		var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
		var copytext = s;
		str.data = copytext;
		trans.setTransferData("text/unicode", str, copytext.length * 2);
		var clipid = Components.interfaces.nsIClipboard;
		if (!clip)
			return false;
		clip.setData(trans, null, clipid.kGlobalClipboard);
	} else {
		alert("该浏览器暂不支持复制功能。。。");
	}
}

//#id对应值为空时，自动定位光标
function autoFocus(id, cal){
	var sid = '#' + id;
	var txt = $(sid).val();
	if(txt || txt != 0){
		if(typeof cal === 'function'){
			cal();
		}
	}else{
		$(sid).focus();
	}
}
