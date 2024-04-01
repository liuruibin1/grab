package com.grab.domain.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenDTO {
    @JsonAlias("name")
    private String name;
    @JsonAlias("chainId")
    private String chainId;
    @JsonAlias("symbol")
    private String symbol;
    @JsonAlias("decimals")
    private String decimals;
    @JsonAlias("address")
    private String address;
    @JsonAlias("logoURI")
    private String logoURI;
}
