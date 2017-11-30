
package com.heady.rekha.headyassessment.domain.entity;

import java.util.List;
import com.squareup.moshi.Json;

public class Category {

    @Json(name = "id")
    public Integer id;
    @Json(name = "name")
    public String name;
    @Json(name = "products")
    public List<Product> products = null;
    @Json(name = "child_categories")
    public List<Integer> childCategories = null;

}
