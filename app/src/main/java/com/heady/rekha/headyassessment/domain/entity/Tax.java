
package com.heady.rekha.headyassessment.domain.entity;

import com.squareup.moshi.Json;

public class Tax {

    @Json(name = "name")
    public String name;
    @Json(name = "value")
    public Integer value;

}
