
package com.heady.rekha.headyassessment.domain.entity;

import java.util.List;
import com.squareup.moshi.Json;

public class Ranking {

    @Json(name = "ranking")
    public String ranking;
    @Json(name = "products")
    public List<Product_> products = null;

}
