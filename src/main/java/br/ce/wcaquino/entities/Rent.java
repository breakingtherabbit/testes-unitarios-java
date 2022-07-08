package br.ce.wcaquino.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rent {

    private User user;
    private List<Movie> movies;
    private Date rentDate;
    private Date devolutionDate;
    private Double value;

}