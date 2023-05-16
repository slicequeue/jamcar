package com.slicequeue.jamcar.jamcar.command.domain.vo;

import lombok.Builder;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Embeddable
public class Address {

    @Comment("우편번호")
    @Column(length = 8)
    private String postalCode;

    @NotNull
    @Comment("주소")
    @Column(length = 1024, nullable = false)
    private String address;

    @Builder(builderMethodName = "newAddress")
    public Address(String postalCode, String address) {
        Assert.notNull(postalCode, "postalCode must not be null.");
        Assert.notNull(address, "address must not be null");

        this.postalCode = postalCode;
        this.address = address;
    }

    public Address() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address1)) return false;
        return Objects.equals(getPostalCode(), address1.getPostalCode()) && Objects.equals(getAddress(), address1.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostalCode(), getAddress());
    }

    @Override
    public String toString() {
        return "Address{" +
                "postalCode='" + postalCode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getAddress() {
        return address;
    }

    void updatePostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
