
package com.heady.rekha.headyassessment.domain.entity;

import com.squareup.moshi.Json;

public class Variant {

    @Json(name = "id")
    public Integer id;
    @Json(name = "color")
    public String color;
    @Json(name = "size")
    public Object size;
    @Json(name = "price")
    public Integer price;

}
