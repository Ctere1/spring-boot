package com.tutorial.tutorial.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.tutorial.tutorial.model.Tutorial;

public interface TutorialService {

	List<Tutorial> getAll();

	List<Tutorial> getAll(Sort sort);

	Page<Tutorial> getAll(Pageable pageable);

	Optional<Tutorial> getById(Long idLong);

	Page<Tutorial> getPublished(boolean published, Pageable pageable);

	Tutorial save(Tutorial tutorial);

	Tutorial update(Tutorial tutorial);

	Boolean delete(Long id);

	Boolean deleteAll();
}
