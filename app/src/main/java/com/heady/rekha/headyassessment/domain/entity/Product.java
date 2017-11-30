
package com.heady.rekha.headyassessment.domain.entity;

import java.util.List;
import com.squareup.moshi.Json;

public class Product {

    @Json(name = "id")
    public Integer id;
    @Json(name = "name")
    public String name;
    @Json(name = "date_added")
    public String dateAdded;
    @Json(name = "variants")
    public List<Variant> variants = null;
    @Json(name = "tax")
    public Tax tax;

}
