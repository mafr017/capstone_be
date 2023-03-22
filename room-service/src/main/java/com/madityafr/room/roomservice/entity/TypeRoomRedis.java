package com.madityafr.room.roomservice.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("TypeRoom")
@Data
public class TypeRoomRedis {
    private Integer id;
    private String name;
    private String description;

    public TypeRoomRedis(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
