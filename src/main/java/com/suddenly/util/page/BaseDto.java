package com.suddenly.util.page;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

public class BaseDto implements Serializable {
    @JsonSerialize(
            using = ToStringSerializer.class
    )
    private Long id;

    public BaseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
