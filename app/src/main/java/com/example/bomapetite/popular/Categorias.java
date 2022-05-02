package com.example.bomapetite.popular;

public class Categorias {
    String title;
    int imgProduct;

    public Categorias(String title, int imgProduct) {
        this.title = title;
        this.imgProduct = imgProduct;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(int imgProduct) {
        this.imgProduct = imgProduct;
    }
}
