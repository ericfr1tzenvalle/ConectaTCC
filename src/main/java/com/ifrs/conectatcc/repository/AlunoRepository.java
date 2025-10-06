/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ifrs.conectatcc.repository;

import com.ifrs.conectatcc.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Éric
 */
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>{
    
}
