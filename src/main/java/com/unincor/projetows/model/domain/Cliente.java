package com.unincor.projetows.model.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "O nome não foi informado")
    @Size(min = 5, max = 100)
    private String nome;
    @CPF
    @NotBlank
    private String cpf;

    @NotNull
    private LocalDate dataNascimento;
    
    @NotNull(message = "O endereço não foi informado")
    private Endereco endereco;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    

    public Cliente(Integer id, @NotBlank(message = "O nome não foi informado") @Size(min = 5, max = 100) String nome,
            @CPF @NotBlank String cpf, @NotNull LocalDate dataNascimento,
            @NotNull(message = "O endereço não foi informado") Endereco endereco) {
        this.id = id;
        this.nome = nome;
        setCpf(cpf);
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }



    public void setCpf(String cpf) {
        this.cpf = cpf == null ? cpf : cpf.replaceAll("\\D", "");
    }
    
}
