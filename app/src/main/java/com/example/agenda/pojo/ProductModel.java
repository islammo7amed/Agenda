package com.example.agenda.pojo;

import java.io.Serializable;

public class ProductModel implements Serializable {
    long productId;
    long number_of_pieces;

    public ProductModel(long productId, long number_of_pieces) {
        this.productId = productId;
        this.number_of_pieces = number_of_pieces;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getNumber_of_pieces() {
        return number_of_pieces;
    }

    public void setNumber_of_pieces(long number_of_pieces) {
        this.number_of_pieces = number_of_pieces;
    }
}
