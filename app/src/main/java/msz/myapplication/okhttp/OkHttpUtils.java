package msz.myapplication.okhttp;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;

public class OkHttpUtils {
    private static final String TAG = "OKHttpUtils";
    private static final OkHttpClient okHttpClient = new OkHttpClient();

    ///////////////////////////////////////////////////////////////////////////
    // GET方式同步请求网络
    ///////////////////////////////////////////////////////////////////////////

    /**
     * GET方式请求网络,获取Request请求对象
     *
     * @param urlString
     * @return Request
     */
    private static Request buildGetRequest(String urlString) {
        Request request = new Request.Builder()
                .url(urlString)
                .build();
        return request;
    }

    /**
     * 访问网络，获取Response响应对象
     *
     * @param urlString
     * @return Response
     * @throws IOException
     */
    private static Response buildResponse(String urlString) throws IOException {
        Request request = buildGetRequest(urlString);
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }

    /**
     * 获取ResponseBody对象
     *
     * @param urlString
     * @return ResponseBody
     * @throws IOException
     */
    private static ResponseBody buildResponseBody(String urlString) throws IOException {
        Response response = buildResponse(urlString);
        if (response.isSuccessful()) {
            return response.body();
        }
        return null;
    }

    /**
     * GET方式请求网络，返回字符串
     *
     * @param urlString
     * @return String
     * @throws IOException
     */
    public static String getStringFromURL(String urlString) throws IOException {
        ResponseBody responseBody = buildResponseBody(urlString);
        if (responseBody != null) {
            return responseBody.string();
        }
        return null;
    }

    /**
     * GET方式请求网络，返回字节数组
     *
     * @param urlString
     * @return byte[]
     * @throws IOException
     */
    public static byte[] getBytesFromURL(String urlString) throws IOException {
        ResponseBody responseBody = buildResponseBody(urlString);
        if (responseBody != null) {
            return responseBody.bytes();
        }
        return null;
    }

    /**
     * GET方式请求网络，返回输入流
     *
     * @param urlString
     * @return InputStream
     * @throws IOException
     */
    public static InputStream getStreamFromURL(String urlString) throws IOException {
        ResponseBody responseBody = buildResponseBody(urlString);
        if (responseBody != null) {
            return responseBody.byteStream();
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // GET异步访问网络
    ///////////////////////////////////////////////////////////////////////////

    /**
     * GET方式异步请求网络
     *
     * @param urlString
     * @param callback
     */

    public static void getDataAsync(String urlString, Callback callback) {
        //Log.d("OkHttpUtils", "---->>getDataAsync: " + Thread.currentThread().getId());
        Request request = buildGetRequest(urlString);
        okHttpClient.newCall(request).enqueue(callback);
    }

    ///////////////////////////////////////////////////////////////////////////
    // post同步网络访问
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param urlString
     * @param requestBody
     * @return
     */
    private static Request buildPostRequest(String urlString, RequestBody requestBody) {
        Request.Builder builder = new Request.Builder();
        builder.url(urlString).post(requestBody);
        return builder.build();
    }

    /**
     * @param urlString
     * @param requestBody
     * @return
     * @throws IOException
     */
    private static String postRequestBody(String urlString, RequestBody requestBody) throws IOException {
        Request request = buildPostRequest(urlString, requestBody);
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }
        return null;
    }

    /**
     * 作用：post网络请求发送键值对时，获取RequestBody对象
     *
     * @param map
     * @return
     */
    private static RequestBody buildRequestBody(Map<String, String> map) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    /**
     * 作用：post网络访问，提交键值对
     *
     * @param urlString
     * @param map
     * @return
     * @throws IOException
     */
    public static String postKeyValuePair(String urlString, Map<String, String> map) throws IOException {
        RequestBody requestBody = buildRequestBody(map);
        return postRequestBody(urlString, requestBody);
    }

    /**
     * 作用：post同步上传文件以及其它表单控件（也就是提交分块请求）
     *
     * @param urlString     网络地址
     * @param map           提交给服务器的表单信息键值对
     * @param files         提交的文件
     * @param formFieldName 每个需要提交的文件对应的文件input的name值
     * @return
     * @throws IOException
     */
    public static String postUploadFiles(String urlString, Map<String, String> map, File[] files, String[] formFieldName) throws IOException {
        RequestBody requestBody = buildRequestBody(map, files, formFieldName);
        return postRequestBody(urlString, requestBody);
    }

    /**
     * 作用：生成提交分块请求时的RequestBody对象
     *
     * @param map
     * @param files
     * @param formFieldName
     * @return
     */
    private static RequestBody buildRequestBody(Map<String, String> map, File[] files, String[] formFieldName) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        //第一部分提交：文件控件以外的其它input的数据
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data;name=\"" + entry.getKey() + "\""), RequestBody.create(null, entry.getValue()));
            }
        }
        //第二部分：上传文件控件的数据
        //往MultipartBuilder对象中添加file input控件的内容
        if (files != null && formFieldName != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                RequestBody requestBody = RequestBody.create(MediaType.parse(getMimeType(fileName)), file);
                //添加file input块的数据
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + formFieldName[i] + "\"; filename=\"" + fileName + "\""), requestBody);
            }
        }
        return builder.build();
    }

    /**
     * 获取文件MimeType
     *
     * @param filename
     * @return
     */
    private static String getMimeType(String filename) {
        FileNameMap filenameMap = URLConnection.getFileNameMap();
        String contentTypeFor = filenameMap.getContentTypeFor(filename);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;

    }

    ///////////////////////////////////////////////////////////////////////////
    // post异步网络请求
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 作用：post异步网络请求，提交RequestBody对象
     *
     * @param urlString
     * @param requestBody
     * @param callback
     */
    private static void postRequestBodyAsync(String urlString, RequestBody requestBody, Callback callback) {
        Request request = buildPostRequest(urlString, requestBody);
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 作用：post异步请求，提交键值对
     *
     * @param urlString
     * @param map
     * @param callback
     */
    public static void postKeyValuePairAsync(String urlString, Map<String, String> map, Callback callback) {
        RequestBody requestBody = buildRequestBody(map);
        postRequestBodyAsync(urlString, requestBody, callback);
    }

    /**
     * 作用：post异步上传文件，提交分块请求
     *
     * @param urlString     网络地址
     * @param map           提交给服务器的表单信息键值对
     * @param files         提交的文件
     * @param formFieldName 每个需要提交的文件对应的文件input的name值
     * @param callback      异步上传回调方法
     * @throws IOException
     */
    public static void postUploadFilesAsync(String urlString, Map<String, String> map, File[] files, String[] formFieldName, Callback callback) throws IOException {
        RequestBody requestBody = buildRequestBody(map, files, formFieldName);
        postRequestBodyAsync(urlString, requestBody, callback);
    }


}
