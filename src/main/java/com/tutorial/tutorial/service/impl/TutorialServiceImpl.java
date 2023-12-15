package com.tutorial.tutorial.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tutorial.tutorial.model.Tutorial;
import com.tutorial.tutorial.repository.TutorialRepository;
import com.tutorial.tutorial.service.TutorialService;

@Service
public class TutorialServiceImpl implements TutorialService {

	@Autowired
	private TutorialRepository tutorialRepository;

	@Override
	public List<Tutorial> getAll() {
		List<Tutorial> allTutorials = tutorialRepository.findAll();
		return allTutorials;
	}

	@Override
	public List<Tutorial> getAll(Sort sort) {
		List<Tutorial> allTutorials = tutorialRepository.findAll(sort);
		return allTutorials;
	}

	@Override
	public Page<Tutorial> getAll(Pageable pageable) {
		Page<Tutorial> allTutorials = tutorialRepository.findAll(pageable);
		return allTutorials;
	}

	@Override
	public Optional<Tutorial> getById(Long idLong) {
		Optional<Tutorial> tutorial = tutorialRepository.findById(idLong);
		return tutorial;
	}

	@Override
	public Page<Tutorial> getPublished(boolean published, Pageable pageable) {
		Page<Tutorial> tutorial = tutorialRepository.findByPublished(published, pageable);
		return tutorial;
	}

	public Page<Tutorial> getByTitleContaining(String title, Pageable pageable) {
		Page<Tutorial> tutorial = tutorialRepository.findByTitleContaining(title, pageable);
		return tutorial;
	}

	public List<Tutorial> getPublished(String title, Sort sort) {
		List<Tutorial> tutorial = tutorialRepository.findByTitleContaining(title, sort);
		return tutorial;
	}

	@Override
	public Tutorial save(Tutorial tutorial) {
		Tutorial savedTutorial = tutorialRepository.save(tutorial);
		return savedTutorial;
	}

	@Override
	public Tutorial update(Tutorial tutorial) {
		Tutorial savedTutorial = tutorialRepository.save(tutorial);
		return savedTutorial;
	}

	@Override
	public Boolean delete(Long id) {
		try {
			tutorialRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public Boolean deleteAll() {
		try {
			tutorialRepository.deleteAll();
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

}
