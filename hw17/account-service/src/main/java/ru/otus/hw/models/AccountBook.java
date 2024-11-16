package ru.otus.hw.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Audited
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_book")
public class AccountBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Column(name = "book_id")
    private long bookId;

}
