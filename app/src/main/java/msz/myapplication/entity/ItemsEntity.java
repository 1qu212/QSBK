package msz.myapplication.entity;

import java.util.List;

/**
 * @Title: ItemsEntity
 * @Package msz.myapplication.entity
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/27 13:07
 */
public class ItemsEntity {
    private Object image;
    private int published_at;
    private UserEntity user;
    private String content;
    private int comments_count;
    private String high_loc;
    private ImageSizeEntity image_size;
    private String high_url;
    private String pic_url;
    private List<Integer> pic_size;

    public String getHigh_url() {
        return high_url;
    }

    public void setHigh_url(String high_url) {
        this.high_url = high_url;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public List<Integer> getPic_size() {
        return pic_size;
    }

    public void setPic_size(List<Integer> pic_size) {
        this.pic_size = pic_size;
    }

    public ImageSizeEntity getImage_size() {
        return image_size;
    }

    public void setImage_size(ImageSizeEntity image_size) {
        this.image_size = image_size;
    }

    public String getHigh_loc() {
        return high_loc;
    }

    public void setHigh_loc(String high_loc) {
        this.high_loc = high_loc;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public int getPublished_at() {
        return published_at;
    }

    public void setPublished_at(int published_at) {
        this.published_at = published_at;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }
}
