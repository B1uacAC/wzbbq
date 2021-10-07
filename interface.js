/*
(后端加载):
具备Java运行环境且在cmd窗口输入java有反馈提示
运行start2.0.bat启动服务器

(前端加载):
将这端代码插入jsa代码前端
导入jquery
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
第一次执行，参数为空Get();      返回一个二维数组,结构如下
data = [
    [name1 , message1],
    [name2 , message2],
    [name3 , message3],
]
初始列表的加载在func()函数后端完成

(发表):
再次调用Get(name , message)，并传入参数
后台接收成功会提示并自动刷新页面
后台接收失败无反应
数据会保存在data.txt中
*/
var i95028 = 0;
function Get(name , message){ 
    $.post("http://localhost:8010/",{name:name,message:message});
    var frame95028 = document.createElement('script');
    frame95028.src = 'http://localhost:8010?name=95028&message=27793&callback=func';
    frame95028.id = 'id95028';
    $('body').append(frame95028);
}
function func(data){
    if (i95028++ != 0) {
        alert("posted !")
        history.go(0);
    }
    console.log("get arrey succeeded !");
    for (let iiiiiiiii = 0; iiiiiiiii < data.length; iiiiiiiii++) {
        for (let jjjjjjjj = 0; jjjjjjjj < data[iiiiiiiii].length; jjjjjjjj++) {
            data[iiiiiiiii][jjjjjjjj] = decodeURIComponent(data[iiiiiiiii][jjjjjjjj]);  
        }
    }
    console.log(data);
    //初始列表的加载在这完成

}