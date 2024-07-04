package com.example.electronicstoremobileapp.admins.ui.products.models;

import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThanOrEqual;
import static br.com.fluentvalidator.predicate.ComparablePredicate.lessThan;
import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.stringSizeGreaterThan;

import java.io.File;

import br.com.fluentvalidator.AbstractValidator;

public class CreateProductDto {

    public String ProductName;
    public String Description;
    public double DefaultPrice;
    public String CategoryId;
    public String Manufacturer;
    public int StorageAmount;
    public File ImageFile;

    public CreateProductDto() {
        DefaultPrice = 0;
        StorageAmount = 0;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getDefaultPrice() {
        return DefaultPrice;
    }

    public void setDefaultPrice(double defaultPrice) {
        DefaultPrice = defaultPrice;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public int getStorageAmount() {
        return StorageAmount;
    }

    public void setStorageAmount(int storageAmount) {
        StorageAmount = storageAmount;
    }

    public File getImageFile() {
        return ImageFile;
    }

    public void setImageFile(File imageFile) {
        ImageFile = imageFile;
    }

    public static class CreateProductDtoValidator extends AbstractValidator<CreateProductDto> {
        @Override
        public void rules() {
            ruleFor(CreateProductDto::getProductName)
                    .must(not(nullValue()))
                    .withMessage("product name is null ")
                    .withFieldName("ProductName")
                    .must(stringSizeGreaterThan(1))
                    .withMessage("product name must > 1 in length")
                    .withFieldName("ProductName");
            ruleFor(CreateProductDto::getDefaultPrice)
                    .must(not(nullValue()))
                    .withMessage("DefaultPrice not null")
                    .withFieldName("DefaultPrice")
                    .must(greaterThanOrEqual(0.0))
                    .withMessage("DefaultPrice must > 0")
                    .withFieldName("DefaultPrice")
                    .must(lessThan(100000000.0))
                    .withMessage("DefaultPrice must < 100 million")
                    .withFieldName("DefaultPrice");
            ruleFor(CreateProductDto::getCategoryId)
                    .must(not(nullValue()))
                    .withFieldName("CategoryId");
            ruleFor(CreateProductDto::getStorageAmount)
                    .must(not(nullValue()))
                    .withMessage("storage not null")
                    .withFieldName("getStorageAmount")
                    .must(greaterThanOrEqual(0))
                    .withMessage("storage must >= 0")
                    .withFieldName("getStorageAmount")
                    .must(lessThan(100000000))
                    .withMessage("storage must < 100 million")
                    .withFieldName("getStorageAmount");
//                .withMessage("product price must > 0")
            ruleFor(CreateProductDto::getManufacturer)
                    .must(not(nullValue()))
                    .withMessage("manufactuer must not null")
                    .withFieldName("Manufacturer")
                    .must(stringSizeGreaterThan(0))
                    .withMessage("manufactuer must be > 0")
                    .withFieldName("Manufacturer");
            ruleFor(CreateProductDto::getDescription)
                    .must(not(nullValue()))
                    .withMessage("Description must not null")
                    .withFieldName("Description")
                    .must(stringSizeGreaterThan(0))
                    .withMessage("Description must be > 0")
                    .withFieldName("Description");
            ruleFor(CreateProductDto::getImageFile)
                    .must(not(nullValue()))
                    .withMessage("Image must not null")
                    .withFieldName("ImageFile");
        }
    }
}
