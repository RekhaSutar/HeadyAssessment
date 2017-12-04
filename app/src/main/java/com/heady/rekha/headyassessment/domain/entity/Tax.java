
package com.heady.rekha.headyassessment.domain.entity;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Tax implements Serializable {

    @Json(name = "name")
    public String name;
    @Json(name = "value")
    public Double value;

}
