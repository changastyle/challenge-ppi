package com.prisma.challengeppi.dto;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BaseDTO
{
    @JsonIgnore
    public boolean verbose = false;

    public Object toEntity(Class<?> clazz) {
        Object response = null;
        try {
            Constructor<?> ctor = clazz.getConstructor();

            response = ctor.newInstance();

            Class<?> dtoClass = this.getClass();
            Method[] dtoMethods = dtoClass.getMethods();
            Method[] entityMethods = clazz.getMethods();

            List<Method> arrGettersDTO = Arrays.stream(dtoMethods).filter(method -> method.getName().startsWith("get")).collect(Collectors.toList());
            List<Method> arrGettersEntity = Arrays.stream(entityMethods)
                    .filter(method -> method.getName().startsWith("get")).collect(Collectors.toList());
            List<Method> arrSettersEntity = Arrays.stream(entityMethods)
                    .filter(method -> method.getName().startsWith("set")).collect(Collectors.toList());

            if (verbose) {
                System.out.println("MIS CAMPOS (" + arrGettersDTO.size() + "):");
                System.out.println("CAMPOS ENTITY (" + arrGettersEntity.size() + "):");
            }
            for (Method getterEntityLoop : arrGettersEntity) {
                for (Method getterDTOLoop : arrGettersDTO) {
                    String field = getterDTOLoop.getName();
                    String entityField = getterEntityLoop.getName();

                    String rawField = field.substring(3);
                    String rawEntityField = entityField.substring(3);

                    if (rawEntityField.equalsIgnoreCase(rawField)) {
                        Object valor = getterDTOLoop.invoke(this, null);
                        Method setterEntity = getMethodByName("set" + rawField, arrSettersEntity);
                        if (setterEntity != null) setterEntity.invoke(response, valor);

                        if (verbose) {
                            System.out.println(
                                    "TENGO ESTE VALOR PARA : " + field + " - (" + rawField + ") -> " + valor);
                        }
                    }
                }
            }

            if (verbose) {
                System.out.println("RTA FINAL:" + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public Method getMethodByName(String setterName, List<Method> arrSetters) {
        return arrSetters.stream().filter(method -> method.getName().equalsIgnoreCase(setterName)).findFirst().orElse(null);
    }

    public Object toDTO(Object entity) {
        Object response = null;

        try {
            Class<?> clazz = entity.getClass();
            Class<?> dtoClass = this.getClass();
            Method[] dtoMethods = dtoClass.getMethods();
            Method[] entityMethods = clazz.getMethods();

            List<Method> arrGettersDTO = Arrays.stream(dtoMethods).filter(method -> method.getName().startsWith("get")).collect(Collectors.toList());
            List<Method> arrSettersDTO = Arrays.stream(dtoMethods).filter(method -> method.getName().startsWith("set")).collect(Collectors.toList());
            List<Method> arrGettersEntity = Arrays.stream(entityMethods)
                    .filter(method -> method.getName().startsWith("get")).collect(Collectors.toList());
            List<Method> arrSettersEntity = Arrays.stream(entityMethods)
                    .filter(method -> method.getName().startsWith("set")).collect(Collectors.toList());

            for (Method setterDTOLoop : arrSettersDTO) {
                // System.out.println("SETTER: " + setterDTOLoop.getName() );

                for (Method getterEntityLoop : arrGettersEntity) {
                    String miCampo = setterDTOLoop.getName();
                    String campoEntity = getterEntityLoop.getName();

                    String rawMiCampo = miCampo.substring(3);
                    String rawCampoEntity = campoEntity.substring(3);

                    if (rawCampoEntity.equalsIgnoreCase(rawMiCampo)) {
                        Object valor = getterEntityLoop.invoke(entity, null);

                        if (verbose) {
                            System.out.println(
                                    "TENGO ESTE VALOR PARA : " + miCampo + " - (" + rawMiCampo + ") -> " + valor);
                        }

                        setterDTOLoop.invoke(this, valor);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response = this;

        return response;
    }
}
