package com.unincor.projetows.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "Enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Informe um CEP")
    private String cep;
    @NotBlank
    private String logradouro;
    private String complemento;
    @NotBlank
    private String bairro;
    private String numero;
    @NotBlank
    private String cidade;
    @NotBlank
    private String uf;

    @OneToOne(mappedBy = "endereco")
    private Pedido pedido;

    /**
     * Retorna um endereço formatado: Rua fulano de tal, 85, Vila das Hortências,
     * Três Corações - MG
     */
    @Transient
    public String getEnderecoLabel() {
        
        String enderecoBase = logradouro;
        if (numero != null) {
            enderecoBase += ", " + numero;
        }
        enderecoBase += ", " + bairro;
        enderecoBase += ", " + cidade;
        enderecoBase += " - " + uf;

        return enderecoBase;
    }

}
