package msz.myapplication;

/**  
* @Title: Constant.java
* @Package msz.myapplication
* @Description:
* @Author: msz
* @Mail: 2431098657@qq.com
* @Date: 2017/3/25 19:15
*/ 
public class Constant {
    public final static String APP_NAME = "qiushibaike";
    // 最新
//    public final static String URL_LATEST = "http://m2.qiushibaike.com/article/list/latest?page=%d";
    public final static String URL_LATEST = "http://m2.qiushibaike.com/article/list/suggest?page=%d";

    // 图片
    public final static String URL_PIC= "http://m2.qiushibaike.com/article/list/pic?page=%d";

    // 视频
    public final static String URL_VIDEO = "http://m2.qiushibaike.com/article/list/video?page=%d";

    // 文本
    public final static String URL_TEXT = "http://m2.qiushibaike.com/article/list/text?page=%d";

    //头像获取(+ id掉后4位 + "/" + id + "/thumb/" + icon图片名.jpg)
    //userIcon======http://pic.qiushibaike.com/system/avtnew/1499/14997026/thumb/20140404194843.jpg
    public final static String URL_USER_ICON="http://pic.qiushibaike.com/system/avtnew/%s/%s/thumb/%s";

    //内容图片获取(+图片名所有数字去掉后4位+"/"+图片名从数字开始数全部+"/"+"/"+small或者medium+"/"+图片名)
    //====图片Url=http://pic.qiushibaike.com/system/pictures/7128/71288069/medium/app71288069.jpg
    public final static String URL_IMAGE= "http://pic.qiushibaike.com/system/pictures/%s/%s/medium/%s";
}
