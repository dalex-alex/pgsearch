package tools.dlx.pgsearch.util;

import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class AddressEntity {
    @Id Integer id;
    @Column String addressLine;
    @Column String city;
    @Column String country;
    @Column Long zipCode;

    @Formula("addressLine || coalesce(city, '') || coalesce(country, '') || coalesce(zipCode, 0)")
    String document;

    public Integer getId() {
        return id;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Long getZipCode() {
        return zipCode;
    }
}
