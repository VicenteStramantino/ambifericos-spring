    package org.example.ambifericos.Model;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import java.math.BigDecimal;

    @Entity
    @Table(name = "item_pedido")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class ItemPedido {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "pedido_id", nullable = false)
        @JsonBackReference
        private Pedido pedido;

        @ManyToOne
        @JoinColumn(name = "produto_id", nullable = false)
        private Produto produto;

        @Column(nullable = false)
        private int quantidade;

        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal subtotal;
    }

