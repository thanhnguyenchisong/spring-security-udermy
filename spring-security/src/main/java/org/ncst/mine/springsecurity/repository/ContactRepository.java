package org.ncst.mine.springsecurity.repository;

import org.ncst.mine.springsecurity.entity.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
	
	
}
