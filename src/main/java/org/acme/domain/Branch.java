package org.acme.domain;

import lombok.Data;

@Data
public class Branch {
    private Long id;
    private String name;
    private String address;
    private String city;
}
