package com.tutorial.tutorial.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.tutorial.model.Tutorial;
import com.tutorial.tutorial.repository.TutorialRepository;

/**
 * This class represents the REST API endpoints for managing tutorials.
 * It provides methods for retrieving, creating, updating, and deleting
 * tutorials.
 * The endpoints support pagination and sorting by various fields.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TutorialController {
	@Autowired
	TutorialRepository tutorialRepository;

	/**
	 * An enum for specifying the direction of sorting.
	 * The values are ASC (ascending) and DESC (descending).
	 */
	private Sort.Direction getSortDirection(String direction) {
		if (direction.equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.equals("desc")) {
			return Sort.Direction.DESC;
		}

		return Sort.Direction.ASC;
	}

	/**
	 * Retrieves all tutorials sorted by the given fields and directions.
	 * 
	 * @param sort an array of strings representing the fields and directions to
	 *             sort by
	 * @return a ResponseEntity containing a list of sorted tutorials or a
	 *         NO_CONTENT status if the list is empty
	 */
	@GetMapping("/sortedtutorials")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(defaultValue = "id,desc") String[] sort) {

		try {
			List<Order> orders = new ArrayList<Order>();

			if (sort[0].contains(",")) {
				// will sort more than 2 fields
				// sortOrder="field, direction"
				for (String sortOrder : sort) {
					String[] _sort = sortOrder.split(",");
					orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
				}
			} else {
				// sort=[field, direction]
				orders.add(new Order(getSortDirection(sort[1]), sort[0]));
			}

			List<Tutorial> tutorials = tutorialRepository.findAll(Sort.by(orders));

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves a page of tutorials based on the given parameters.
	 * 
	 * @param title the title of the tutorial to search for (optional)
	 * @param page  the page number to retrieve (default: 1)
	 * @param size  the number of items per page (default: 3)
	 * @param sort  an array of fields to sort by, in the format "field,direction"
	 *              (default: "id,desc")
	 * @return a ResponseEntity containing a map with the retrieved tutorials,
	 *         current page number, total items, and total pages
	 */
	@GetMapping("/tutorials")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> getAllTutorialsPage(
			@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "3") int size,
			@RequestParam(defaultValue = "id,desc") String[] sort) {

		try {
			List<Order> orders = new ArrayList<Order>();

			if (sort[0].contains(",")) {
				// will sort more than 2 fields
				// sortOrder="field, direction"
				for (String sortOrder : sort) {
					String[] _sort = sortOrder.split(",");
					orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
				}
			} else {
				// sort=[field, direction]
				orders.add(new Order(getSortDirection(sort[1]), sort[0]));
			}

			List<Tutorial> tutorials = new ArrayList<Tutorial>();
			Pageable pagingSort = PageRequest.of(page - 1, size, Sort.by(orders));

			Page<Tutorial> pageTuts;
			if (title == null)
				pageTuts = tutorialRepository.findAll(pagingSort);
			else
				pageTuts = tutorialRepository.findByTitleContaining(title, pagingSort);

			tutorials = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", tutorials);
			response.put("currentPage", pageTuts.getNumber() + 1);
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves a list of published tutorials with pagination.
	 *
	 * @param page the page number to retrieve (default: 1)
	 * @param size the number of items per page (default: 3)
	 * @return a ResponseEntity containing a map with the list of tutorials, current
	 *         page number, total items, and total pages
	 */
	@GetMapping("/tutorials/published")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> findByPublished(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "3") int size) {

		try {
			List<Tutorial> tutorials = new ArrayList<Tutorial>();
			Pageable paging = PageRequest.of(page - 1, size);

			Page<Tutorial> pageTuts = tutorialRepository.findByPublished(true, paging);
			tutorials = pageTuts.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("tutorials", tutorials);
			response.put("currentPage", pageTuts.getNumber() + 1);
			response.put("totalItems", pageTuts.getTotalElements());
			response.put("totalPages", pageTuts.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieve a tutorial by its ID.
	 *
	 * @param id The ID of the tutorial to retrieve.
	 * @return A ResponseEntity containing the tutorial if it exists, or a NOT_FOUND
	 *         status if it does not.
	 *         If an error occurs, a INTERNAL_SERVER_ERROR status is returned.
	 */
	@GetMapping("/tutorials/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
		try {
			Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

			if (tutorialData.isPresent()) {
				return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Create a new tutorial.
	 *
	 * @param tutorial the tutorial to be created
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         tutorial, or with status 500 (Internal Server Error) if the tutorial
	 *         couldn't be created
	 */
	@PostMapping("/tutorials")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
		try {
			Tutorial _tutorial = tutorialRepository
					.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished()));
			return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Updates a tutorial with the given ID.
	 * 
	 * @param id
	 * @param tutorial
	 * @return a ResponseEntity with HTTP status code 200 (OK) if the tutorial was
	 *         updated successfully,
	 *         or HTTP status code 404 (NOT_FOUND) if the tutorial does not exist
	 *         or HTTP status code 500 (INTERNAL_SERVER_ERROR) if an error occurred
	 *         while updating the tutorial
	 */
	@PutMapping("/tutorials/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
		try {
			Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

			if (tutorialData.isPresent()) {
				Tutorial _tutorial = tutorialData.get();
				_tutorial.setTitle(tutorial.getTitle());
				_tutorial.setDescription(tutorial.getDescription());
				_tutorial.setPublished(tutorial.isPublished());
				return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Deletes a tutorial with the given ID.
	 *
	 * @param id the ID of the tutorial to delete
	 * @return a ResponseEntity with HTTP status code 204 (NO_CONTENT) if the
	 *         tutorial was deleted successfully,
	 *         or HTTP status code 500 (INTERNAL_SERVER_ERROR) if an error occurred
	 *         while deleting the tutorial
	 */
	@DeleteMapping("/tutorials/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			tutorialRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Deletes all tutorials from the database.
	 *
	 * @return ResponseEntity with HTTP status NO_CONTENT if successful,
	 *         INTERNAL_SERVER_ERROR if an error occurs
	 */
	@DeleteMapping("/tutorials")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
		try {
			tutorialRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
