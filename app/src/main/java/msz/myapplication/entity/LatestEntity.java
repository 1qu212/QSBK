package msz.myapplication.entity;

import java.util.List;

/**
 * @Title: LatestEntity
 * @Package msz.myapplication.entity
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/27 13:00
 */
public class LatestEntity {
    private Long id;
    private List<ItemsEntity> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }
}
