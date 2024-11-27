package ua.com.owu.crm_programming_school.models;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.owu.crm_programming_school.views.Views;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaginated {
    @Schema(description = "Total number of items")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private int total_items;

    @Schema(description = "Total number of pages")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private int total_pages;

    @Schema(description = "Link to the previous page of orders")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String prev;

    @Schema(description = "List of orders on the current page")
    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private String next;

    @JsonView(value = {Views.Level1.class, Views.Level2.class, Views.Level3.class})
    private List<Order> items;
}
