package cn.edu.jnu.supershopper.data;

import java.io.Serializable;

public class BookItem implements Serializable {
    public BookItem(String title,  int resourceId) {
        this.title = title;
       // this.price = price;
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /*public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
*/
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    private String title;
    //private Double price;
    private int resourceId;
}
