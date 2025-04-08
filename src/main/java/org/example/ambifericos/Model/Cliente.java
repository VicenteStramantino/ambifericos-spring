    package org.example.ambifericos.Model;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import java.math.BigDecimal;
    import java.time.LocalDateTime;

    @Entity
    @Table(name = "Cliente")
    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    public class Cliente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 100)
        private String nome;

        @Column(nullable = false, unique = true, length = 100)
        private String email;

        @Column(length = 50)
        private String senha;

        @Column(columnDefinition = "TEXT")
        private String endereco;

        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt = LocalDateTime.now();
    }