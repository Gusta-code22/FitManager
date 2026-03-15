package com.gusta.academia.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Value;

import java.util.UUID;

@Entity
@Table(name = "tb_roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "role_id")

    private long roleId;

    private String name;

    public enum Values {
        USER(1L),
        PROFESSOR(2L),
        ADMIN(3L);

        long roleid;

        Values(long roleId){
            this.roleid = roleId;
        }

        public long roleId() {
            return roleid;
        }
    }
}
