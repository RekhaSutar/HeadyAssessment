
package com.heady.rekha.headyassessment.domain.entity;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Variant implements Serializable {

    @Json(name = "id")
    public Integer id;
    @Json(name = "color")
    public String color;
    @Json(name = "size")
    public String size;
    @Json(name = "price")
    public Integer price;

}
