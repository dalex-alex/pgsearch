package tools.dlx.pgsearch.util;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class ClientEntity {
    @Id Integer id;
    @Column String name;
    @OneToOne AddressEntity address;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AddressEntity getAddress() {
        return address;
    }
}
