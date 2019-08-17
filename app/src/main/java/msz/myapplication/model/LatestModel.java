package msz.myapplication.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import msz.myapplication.entity.LatestEntity;

/**
 * @Title: LatestModel
 * @Package msz.myapplication.model
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/27 13:19
 */
public class LatestModel {
    LatestEntity mLatestEntity=new LatestEntity();

    public LatestEntity praseJsonToLatestEntity(String jsonStirng){
        Gson gson=new Gson();
        mLatestEntity=gson.fromJson(jsonStirng,new TypeToken<LatestEntity>(){}.getType());
        return mLatestEntity;
    }
}
