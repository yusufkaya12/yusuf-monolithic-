package com.threepounds.caseproject.data.repository;

import com.threepounds.caseproject.data.entity.Messaging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MessagingRepository extends JpaRepository<Messaging, String> {

}
