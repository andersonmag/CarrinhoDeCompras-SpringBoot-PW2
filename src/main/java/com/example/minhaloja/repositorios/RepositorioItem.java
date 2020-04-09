package com.example.minhaloja.repositorios;

import java.util.List;
import javax.transaction.Transactional;
import com.example.minhaloja.modelo.Item;
import com.example.minhaloja.modelo.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RepositorioItem extends JpaRepository<Item, Long>{

    Iterable<Item> findByNomeContaining(String q);
    List<Item> findByTime(Time time);
    
}