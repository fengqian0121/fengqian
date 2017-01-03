function isRPirce(s){
    p =/^[1-9](\d+(\.\d{1,2})?)?$/;
    p1=/^[0-9](\.\d{1,2})?$/;
    return p.test(s) || p1.test(s);
}

function isRNumber(s) {
	z = /^[1-9]\d*$/;
	return z.test(s);
}
function isNumber(s) {
	z = /^[0-9]\d*$/;
	return z.test(s);
}

// 判断手机号是否11位数字
function is11Number(s) {
	z = /^[0-9]{11}$/;
	return z.test(s);
}

// 判断手机号前3位是否正确
function isPhone3(s) {
	ss = ["130","131","132","133","134","135","136","137","138","139",
	     "150","151","152","153","155","156","157","158","159",
	     "180","181","182","183","184","185","186","187","188","189",
	     "170","176","177","178"];
	lz = s.substr(0,3);
	qq = false;
	for (i=0;i<ss.length;i++) {
		if (lz == ss[i]) qq = true;
	}
	return qq;
}

// 判断18位身份证号
function isCard(s) {
	z = /^\d{17}(\d|x|X)$/;
	return z.test(s);
}

// 判断邮箱
function isEmail(s) {
	z = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	return z.test(s);
}

// 判断输入6-12位，由数字和字母组成
function is612Pass(s) {
	z = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/;
	return z.test(s);
}

//判断输入6-20位，由数字和字母组成
function is620Pass(s) {
	z = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
	return z.test(s);
}

// 判断输入4-16位字母用户名
function is416UserCode(s) {
	z = /^[a-zA-Z]{4,16}$/;
	return z.test(s);
}

// 判断是否中文
function isChinese(s) {
	z = /^[\u4e00-\u9fa5]*$/;
	return z.test(s);
}

// 判断是否英文
function isCodeWord(s) {
	z = /^[a-zA-Z]*$/;
	return z.test(s);
}

//判断是否以固定字符串开头
function isStartWithWord(str,word){
	var res=str.indexOf(word);
	if(res==0){
		return true;
	}else {
		return false;
	}
}

//判断日期是否规范yyyy-mm-dd
function isDateRight(str){
	var a = /^(\d{4})-(\d{2})-(\d{2})$/;
	return a.test(str);
}
