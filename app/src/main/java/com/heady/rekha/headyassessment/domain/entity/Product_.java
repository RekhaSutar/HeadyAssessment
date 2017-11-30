
package com.heady.rekha.headyassessment.domain.entity;

import com.squareup.moshi.Json;

public class Product_ {

    @Json(name = "id")
    public Integer id;
    @Json(name = "view_count")
    public Integer viewCount;
    @Json(name = "order_count")
    public Integer orderCount;
    @Json(name = "shares")
    public Integer shares;

}
