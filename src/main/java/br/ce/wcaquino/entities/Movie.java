package br.ce.wcaquino.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {

    private String name;
    private Integer inventory;
    private Double priceRent;

}