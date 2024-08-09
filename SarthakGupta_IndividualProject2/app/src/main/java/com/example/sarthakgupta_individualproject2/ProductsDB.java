package com.example.sarthakgupta_individualproject2;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (
        tableName = "ProductDetails"
)

public class ProductsDB implements Serializable
{
    @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name="productImg")
    String productImg;

    @ColumnInfo(name="productName")
    String productName;

    @ColumnInfo(name="productPrice")
    Double productPrice;

    @ColumnInfo(name="productShortDesc")
    String productShortDesc;

    @ColumnInfo(name="productLongDesc")
    String productLongDesc;

    public ProductsDB(
            String productImg,
            String productName,
            Double productPrice,
            String productShortDesc,
            String productLongDesc)
    {
        this.productImg = productImg;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productShortDesc = productShortDesc;
        this.productLongDesc = productLongDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductShortDesc() {
        return productShortDesc;
    }

    public void setProductShortDesc(String productShortDesc) {
        this.productShortDesc = productShortDesc;
    }

    public String getProductLongDesc() {
        return productLongDesc;
    }

    public void setProductLongDesc(String productLongDesc) {
        this.productLongDesc = productLongDesc;
    }
}


