package com.sarah.anotaai.domain.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    private String id;

    private String title;

    private String ownerId;

    private Integer price;

    private String description;

    private String category;

    public Product(ProductDTO data){
        this.title = data.title();
        this.description = data.description();
        this.ownerId = data.ownerId();
        this.price = data.price();
        this.category = data.categoryId();
    }
    public void update(ProductDTO data) {
        if (data.title() != null) this.title = data.title();
        if (data.description() != null) this.description = data.description();
        if (data.price() != null) this.price = data.price();
        if (data.categoryId() != null) this.category = data.categoryId();
    }
    @Override

    public String toString(){
        JSONObject json = new JSONObject();
        json.put("title",this.title);
        json.put("description",this.description);
        json.put("ownerId",this.ownerId);
        json.put("price",this.price);
        json.put("categoryId", this.category);
        json.put("type", "product");

        return json.toString();
        }
    }

