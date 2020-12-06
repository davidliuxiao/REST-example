package com.roche.product.server.dtos.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductDto {
    private Long id;


    @ApiModelProperty(
            value = "product name",
            name = "name",
            dataType = "String",
            example = "product1")
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String name;

    @ApiModelProperty(
            value = "product price",
            name = "price",
            dataType = "BigDecimal",
            example = "20.0")
    @JsonProperty(required = true)
    @NotNull
    @Positive
    private BigDecimal price;

    @ApiModelProperty(
            value = "product creation date",
            name = "date",
            dataType = "LocalDate",
            example = "2020-11-11")
    @JsonProperty(required = true)
    @NotNull
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @SuppressWarnings("synthetic-access")
    public ProductDto() {
    }

    public ProductDto(Long id, @NotEmpty @NotBlank String name, @NotNull @Positive BigDecimal price, @NotNull @PastOrPresent LocalDate date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}

