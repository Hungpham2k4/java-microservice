package com.microservice.product_service.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
public class ProductUpdateForm {

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Length(max = 100, message = "Tên sản phẩm có tối đa 100 kí tự")
    private String name; // Tên sản phẩm

    @NotEmpty(message = "ID danh mục không được để trống")
    private List<Long> productCategories; // ID của danh mục

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Positive(message = "Giá sản phẩm phải lớn hơn 0")
    private Double price; // Giá sản phẩm

    @NotBlank(message = "Mô tả sản phẩm không được để trống")
    @Length(max = 500, message = "Mô tả sản phẩm có tối đa 500 kí tự")
    private String description; // Mô tả sản phẩm

    @Length(max = 1000, message = "Chi tiết sản phẩm có tối đa 1000 kí tự")
    private String detail; // Chi tiết sản phẩm

    @Length(max = 255, message = "Hình ảnh có tối đa 255 kí tự")
    private String images; // Hình ảnh sản phẩm

    @Length(max = 255, message = "Hình thu nhỏ có tối đa 255 kí tự")
    private String thumbnail; // Hình thu nhỏ sản phẩm

    @NotBlank(message = "Slug không được để trống")
    @Length(max = 100, message = "Slug có tối đa 100 kí tự")
    private String slug; // Slug sản phẩm

    @NotNull(message = "Số lượng không được để trống")
    @Positive(message = "Số lượng phải lớn hơn hoặc bằng 0")
    private Long quantity; // Số lượng sản phẩm

    @NotBlank(message = "Trạng thái không được để trống")
    private String status; // Trạng thái sản phẩm
}
