package com.mgmetehan.productservice.model;

import com.mgmetehan.productservice.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "PRODUCTS")
@Entity
@SQLDelete(sql = "UPDATE PRODUCTS SET DELETED = true WHERE id = ? AND version = ?")
@SQLRestriction("deleted = false")
public class Product extends BaseEntity {
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String description;

    @Column(name = "DELETED", nullable = false)
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        this.deleted = false;
    }
}