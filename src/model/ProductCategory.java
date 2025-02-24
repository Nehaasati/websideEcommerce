package model;

public class ProductCategory {
    private int id;   //junction Table PK
    private int productId;
    private int categoryId;

    public ProductCategory() {}

    public ProductCategory(int productId, int categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "ProductCategory {" +
                "id=" + id +
                ", productId=" + productId +
                ", categoryId=" + categoryId +
                '}';
    }

}