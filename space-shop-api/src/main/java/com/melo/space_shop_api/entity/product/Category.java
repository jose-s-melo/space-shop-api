package com.melo.space_shop_api.entity.product;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private CategoryEnum name;

    @Column(nullable=false)
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<ProductCategory> products = new HashSet<>();

    public Category(Long id, @NotNull CategoryEnum name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = new HashSet<>();
    }

    public Category() {
        products = new HashSet<>();
    }

    public boolean addProductCategory(ProductCategory pc) {
        return products.add(pc);
    }

    public boolean removeProductCategory(ProductCategory pc) {
        return products.remove(pc);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryEnum getName() {
        return name;
    }

    public void setName(CategoryEnum name) {
        this.name = name;
    }

    public Set<ProductCategory> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductCategory> products) {
        this.products = products;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

}
