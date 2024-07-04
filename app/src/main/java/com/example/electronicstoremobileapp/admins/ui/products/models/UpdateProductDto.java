package com.example.electronicstoremobileapp.admins.ui.products.models;

import java.io.File;
import java.util.Date;

public class UpdateProductDto {
    public String ProductName;
    public String Description;
    public double DefaultPrice;
    public String CategoryId;
    public String Manufacturer;
    public int StorageAmount;
    public boolean IsOnSale;
    public Date SaleEndDate;
    public File ImageFile;

}
