package com.prisma.challengeppi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaseEntity
{

    @JsonIgnore
    public List<Method> getGetters() {
        Method[] arrGettersVector = getClass().getMethods();
        return Arrays.stream(arrGettersVector).filter(method -> method.getName().startsWith("get")).collect(Collectors.toList());
    }

}
