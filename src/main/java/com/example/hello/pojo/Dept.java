package com.example.hello.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Dept {
    private Integer id;
    private String name;
    private LocalDateTime updateTime;
}
