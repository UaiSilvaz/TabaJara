package br.com.projetotabajara.tabajara.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPedido;

    private LocalDate dataPedido;

    private double totalPedido;

    @ManyToAny
    @JoinColumn(name = "idUsuario_fk")
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemDoPedido> itensPedido;

    // Metodo para calcular o total
    public double calcularTotal() {
        double total = 0.0;
        if (itensPedido != null) {
            for (ItemDoPedido item : itensPedido) {
                total += item.getSubtotal();
            }
        }
        return total;
    }

    // Metodo para atualizar o total
    public void atualizarTotal() {
        this.totalPedido = calcularTotal();
    }

}
