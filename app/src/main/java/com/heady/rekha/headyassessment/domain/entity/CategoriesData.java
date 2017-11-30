
package com.heady.rekha.headyassessment.domain.entity;

import java.util.List;
import com.squareup.moshi.Json;

public class CategoriesData {

    @Json(name = "categories")
    public List<Category> categories = null;
    @Json(name = "rankings")
    public List<Ranking> rankings = null;

}
