package com.example.agenda.listners;

import com.example.agenda.database.Product;
import com.example.agenda.pojo.CustomerProductModel;

public interface OnCustomerProductItemClickListner {
    public void onItemClick(CustomerProductModel product, boolean state);

    public void ontTextNumOfPiecesChange(Product product,long num_of_pieces);
}
